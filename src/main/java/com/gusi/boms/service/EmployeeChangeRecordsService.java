package com.gusi.boms.service;

import com.gusi.activemq.helper.EmployeeSenderHelper;
import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.AdHelper;
import com.gusi.boms.helper.MailHelper;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.boms.model.EmployeeChangeRecords;
import com.gusi.boms.model.EmployeePosition;
import com.gusi.boms.model.Organization;
import com.gusi.boms.util.DateFormatUtil;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.web.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:24:37 ) 
 */
@Service
@Transactional
public class EmployeeChangeRecordsService extends BaseService<EmployeeChangeRecords> {

	@Autowired
	private EmployeeBaseInforService employeeBaseInforService;
	@Autowired
    private EmployeeService employeeService;
	@Autowired
    private OrganizationHeaderHistoryService organizationHeaderHistoryService;
	@Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private EmployeeSenderHelper employeeSenderHelper;
    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private AdHelper adHelper;
    @Autowired
    private Organization2Service organization2Service;
    @Autowired
    private EmployeeDetailsService employeeDetailsService;

	private static final Logger logger=Logger.getLogger(EmployeeBaseInforService.class);


	/**
	 * @param userCode 异动人工号
	 * @return 根据工号查询异动记录
	 */
	public List<EmployeeChangeRecords> findByUserCode(int userCode) {
		return queryForList(sqlId("findByUserCode"),userCode);
	}
	/**
	  * @since: 2.7.4
	  * @param userCode
	  * @return
	  * <p>
	  *  查询正常生效的异动记录
	  * </p>   
	 */
	public List<EmployeeChangeRecords> findNormalRecordsByUserCode(int userCode){
		return queryForList(sqlId("findNormalRecordsByUserCode"),userCode);
	}
	/**
	 * @param userCode 异动人的工号
	 * @return 是否可以给此人添加异动
	 */
	public boolean canAddChanges(int userCode)
	{
		return count(sqlId("canAddChanges"),userCode)==0;
	}
	/**
	 * @param userCode 异动人的工号
	 * @return 查找用于新增异动的记录
	 *  所有信息放置new里
	 */
	public EmployeeChangeRecords findForAddChanges(int userCode)
	{
		return findById(sqlId("findForAddChanges"), userCode);
	}
	/**
	 * @return 查找要生效的移动记录
	 */
	public List<EmployeeChangeRecords> findChangesForActive()
	{
		return queryForList(sqlId("findChangesForActive"), 0);
	}

    /**
     * @param records 异动实体
     * @return 处理异动
     */
	@ActiveMQTransactional
    public Integer processChanges(EmployeeChangeRecords records) {
        try {
            if (records == null) {
                return null;
            }
            String changeType = records.getChangeType();
            
            //如果不是离职，则需要校验newOrgId的生效时间早于异动生效日期。
            if (!changeType.equalsIgnoreCase(EmployeeChangeRecords.CHANGE_TYPE_LEAVE)) {
            	Organization org= organization2Service.findById(records.getNewOrgId());
            	if (org.getOpenDate()==null || org.getOpenDate().after(records.getActiveDate())) {
					throw new IllegalArgumentException(org.getOrgName()+"的开组日期为空或者异动生效日期早于组织开组日期，请确认。");
				}
            }
            switch (changeType) {
                case EmployeeChangeRecords.CHANGE_TYPE_LEAVE:
                    //离职
                    return processNewLeave(records);
                case EmployeeChangeRecords.CHANGE_TYPE_REGULAR:
                    //转正
                    return processNewRegular(records);
                case EmployeeChangeRecords.CHANGE_TYPE_REHIRE:
                    //回聘
                    return processNewRehire(records);
                case EmployeeChangeRecords.CHANGE_TYPE_PROMOTION:
                case EmployeeChangeRecords.CHANGE_TYPE_DEMOTION:
                case EmployeeChangeRecords.CHANGE_TYPE_APPOINT:
                case EmployeeChangeRecords.CHANGE_TYPE_RELEVEL:
                case EmployeeChangeRecords.CHANGE_TYPE_DEAL_DATA:
                    //晋升、降职、转调、数据处理、任命
                    return processNewPromotionDemotionRelevelAppoint(records);
            }
            return null;
        } catch (Throwable e) {
            logger.error("异动处理异常"+records, e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * job会调用此方法
	 * @param records 异动实体
	 * @return 异动生效，如果是遗留数据类型数据处理，则会失败。
	 */
	@ActiveMQTransactional
    public boolean activeChanges(EmployeeChangeRecords records) {
        try {
            if (records == null) {
                return false;
            }
            String changeType = records.getChangeType();
            switch (changeType) {
                case EmployeeChangeRecords.CHANGE_TYPE_LEAVE:
                    //离职
                    return activeNewLeave(records);
                case EmployeeChangeRecords.CHANGE_TYPE_REGULAR:
                    //转正
                    return activeNewRegular(records);
                case EmployeeChangeRecords.CHANGE_TYPE_REHIRE:
                    //回聘
                    return activeNewRehire(records);
                case EmployeeChangeRecords.CHANGE_TYPE_PROMOTION:
                case EmployeeChangeRecords.CHANGE_TYPE_DEMOTION:
                case EmployeeChangeRecords.CHANGE_TYPE_APPOINT:
                case EmployeeChangeRecords.CHANGE_TYPE_RELEVEL:
                case EmployeeChangeRecords.CHANGE_TYPE_DEAL_DATA:
                    //晋升，。平调，降职
                    return activeNewPromotionDemotionRelevelAppoint(records);
            }
            return false;
        } catch (Throwable e) {
            logger.error("异动处理异常:" + records, e);
            return false;
        }
    }

    /**
     * 1、处理回聘生效
     * 2、同步AD
     * 3、发送推送消息(因为有行业经验变更，所以还要发送员工基础信息的推送消息)
     * @param newRecords
     * @return 回聘生效
     */
    @ActiveMQTransactional
    private boolean activeNewRehire(EmployeeChangeRecords newRecords) {
        //更新档案状态为未完善
        employeeDetailsService.rollbackArchives(newRecords.getUserCode(), null, Constants.GUANLIYUAN);
        //保存异动之前的员工信息
        Employee oldEmployee = employeeService.getEmployee(newRecords.getUserCode(), EmployeeStatus.All);
        //首先变更userTitle
        employeeBaseInforService.updateUserTitileForTran(newRecords.getNewLevelId(), newRecords.getUserCode());
        //立即生效，处理员工信息
        EmployeeBaseInfor emp = new EmployeeBaseInfor();
        emp.setUserCode(newRecords.getUserCode());
        //首先更新员工状态为正式
        emp.setStatus(EmployeeStatus.Formal.toString());
        //设置newJoinDate
        emp.setNewJoinDate(newRecords.getLeaveDate());
        //设置有无经验
        emp.setExperience("有");
        //设置新部门
        emp.setOrgId(newRecords.getNewOrgId());
        //设置职位ID
        emp.setPositionId(newRecords.getNewPositionId());
        //设置职等职级
        emp.setLevelId(newRecords.getNewLevelId());
        emp.setUpdator(newRecords.getCreator());
        if (!employeeBaseInforService.updateBaseInfoForTran(emp)) {
            throw new IllegalArgumentException("新增回聘记录，变更员工记录失败:" + newRecords);
        }
        //重新设置职位 删除已有兼职职位，更新主要职位为newPositionId
        //删除所有职位
        boolean flag = false;
        if (employeePositionService.deleteAllByUserCode(newRecords.getUserCode())) {
            EmployeePosition ep = new EmployeePosition(newRecords.getUserCode(), newRecords.getNewPositionId(),
                    newRecords.getNewOrgId(), 0);
            if (employeePositionService.insert(ep)) {
                flag = true;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException("新增回聘记录，重新设置职位 删除所有职位，设置主要职位时发生异常：" + newRecords);
        }
        //重置密码
        if (!employeeBaseInforService.resetPwd(newRecords.getUserCode())) {
            throw new IllegalArgumentException("新增回聘记录，重置密码异常：" + newRecords);
        }
        employeeBaseInforService.updateEmpOrgPositionByUserCode(newRecords.getUserCode());

        //同步AD用户
        if (!adHelper.syncAD(newRecords.getUserCode())) {
            throw new RuntimeException("fail to sync the AD ......");
        }
        //发送推送消息
        employeeSenderHelper.sendEmployeeYiDong(newRecords.getUserCode(), newRecords.getActiveDate(), oldEmployee.getOrgId());
        //employeeSenderHelper.sendEmployeeBianJi(newRecords.getUserCode(), oldEmployee.getOrgId());
        //发送区域人数提醒短信
        try {
            Calendar c = Calendar.getInstance();
            //区域人数提醒短信会在8点半发送
            c.set(Calendar.HOUR_OF_DAY, 8);
            c.set(Calendar.MINUTE, 30);
            c.set(Calendar.SECOND, 0);
            employeeBaseInforService.sendWarnings(newRecords.getNewOrgId(), c.getTime());
        } catch (Exception e) {
            logger.error("发送区域人数提醒短信失败", e);
        }
        return true;
    }

    /**
	 * @param records
	 * @return  处理：回聘：
	 *    立即生效则
	 *      更新员工职位，新部门，新职等职级，员工状态，
	 *       删除所有EmployeePosition表里此人的数据
	 *      	重新设置职位 。如果新职位兼职有，删除兼职职位，将主要职位更改为兼职 待做
	 */
	private Integer processNewRehire(EmployeeChangeRecords records)
	{
		 EmployeeChangeRecords newRecords=findNewRecords(records);
		 if (newRecords==null) {
			return null;
		}
		 newRecords.setNewStatus(EmployeeStatus.Formal.toString());
		 //如果生效时间在当天以前,立即执行
	    if (newRecords.getActiveDate().before(new Date())) {
	    	 newRecords.setStatus(1);
	    	 activeNewRehire(newRecords);
	    }else
		{
	    	newRecords.setStatus(0);
		}
	    //新增异动记录
	    return insertAndReturnId(newRecords);
	}

    /**
     * 1、晋升、降职、转调、数据处理、任命
     * 2、同步AD
     * 3、发送推送消息
     * @param newRecords
     * @return
     */
    @ActiveMQTransactional
    private boolean activeNewPromotionDemotionRelevelAppoint(EmployeeChangeRecords newRecords) {
        //保存异动之前的员工信息
        Employee oldEmployee = employeeService.getEmployee(newRecords.getUserCode(), EmployeeStatus.All);
        //首先变更userTitle
        employeeBaseInforService.updateUserTitileForTran(newRecords.getNewLevelId(), newRecords.getUserCode());
        EmployeeBaseInfor emp = new EmployeeBaseInfor();
        emp.setUserCode(newRecords.getUserCode());
        //设置新部门
        emp.setOrgId(newRecords.getNewOrgId());
        //设置职位ID
        emp.setPositionId(newRecords.getNewPositionId());
        //设置职等职级
        emp.setLevelId(newRecords.getNewLevelId());
        emp.setUpdator(newRecords.getCreator());
        if (!employeeBaseInforService.updateBaseInfoForTran(emp)) {
            throw new IllegalArgumentException("异动变更员工记录失败");
        }
        //处理职位，如果其主要职位
        //重新设置职位。。。如果新职位兼职有，删除兼职职位，
        if (!employeePositionService.changePrimaryPosition(newRecords.getNewOrgId(), newRecords.getNewPositionId(), newRecords.getUserCode())) {
            throw new IllegalArgumentException("新增异动记录，重新设置职位 删除已有兼职职位，更新主要职位为newPositionId发生异常：" + newRecords);
        }
        employeeBaseInforService.updateEmpOrgPositionByUserCode(newRecords.getUserCode());

        //同步AD用户
        if (!adHelper.syncAD(newRecords.getUserCode())) {
            throw new RuntimeException("fail to sync the AD ......");
        }
        //发送异动推送消息
        employeeSenderHelper.sendEmployeeYiDong(newRecords.getUserCode(), newRecords.getActiveDate(), oldEmployee.getOrgId());
        return true;
    }

    /**
	 * @param records
	 * @return  处理：晋升、降职、转调、数据处理、任命
	 *    立即生效则
	 *      更新员工职位，新部门，新职等职级，
	 *      重新设置职位 。如果新职位兼职有，删除兼职职位，将主要职位更改为兼职 待做
	 */
    @ActiveMQTransactional
	private Integer processNewPromotionDemotionRelevelAppoint(EmployeeChangeRecords records)
	{
		 EmployeeChangeRecords newRecords=findNewRecords(records);
		 if (newRecords==null) {
			return null;
		}
		 //如果生效时间在当天以前,立即执行
	    if (newRecords.getActiveDate().before(new Date())) {
	    	 newRecords.setStatus(1);
			//立即生效，处理员工信息
			activeNewPromotionDemotionRelevelAppoint(newRecords);
	    }else
		{
	    	newRecords.setStatus(0);
		}
	    //新增异动记录
	    return insertAndReturnId(newRecords);
	}

    /**
     * 1、转正生效
     * 2、同步AD
     * 3、发送推送消息
     * @param newRecords
     * @return 转正生效
     */
    @ActiveMQTransactional
    private boolean activeNewRegular(EmployeeChangeRecords newRecords) {
        //保存异动之前的员工信息
        Employee oldEmployee = employeeService.getEmployee(newRecords.getUserCode(), EmployeeStatus.All);
        //变动之前判断oldUserTitle和levelName一样需要更新，不一样不必更新
        //首先变更userTitle
        employeeBaseInforService.updateUserTitileForTran(newRecords.getNewLevelId(), newRecords.getUserCode());
        //立即生效，处理员工信息
        EmployeeBaseInfor emp = new EmployeeBaseInfor();
        //首先更新员工状态
        emp.setUserCode(newRecords.getUserCode());
        emp.setStatus(newRecords.getNewStatus());
        //设置新部门
        emp.setOrgId(newRecords.getNewOrgId());
        //设置转正日期
        emp.setFormalDate(newRecords.getLeaveDate());
        //设置职位ID
        emp.setPositionId(newRecords.getNewPositionId());
        //设置职等职级
        emp.setLevelId(newRecords.getNewLevelId());
        emp.setUpdator(newRecords.getCreator());
        if (!employeeBaseInforService.updateBaseInfoForTran(emp)) {
            throw new IllegalArgumentException("新增转正记录，变更员工记录失败");
        }
        //处理职位，如果其主要职位
        //重新设置职位。。。如果新职位兼职有，删除兼职职位，
        if (!employeePositionService.changePrimaryPosition(newRecords.getNewOrgId(), newRecords.getNewPositionId(), newRecords.getUserCode())) {
            throw new IllegalArgumentException("新增异动记录，重新设置职位 删除已有兼职职位，更新主要职位为newPositionId发生异常：" + newRecords);
        }
        employeeBaseInforService.updateEmpOrgPositionByUserCode(newRecords.getUserCode());
        //同步AD用户
        if (!adHelper.syncAD(newRecords.getUserCode())) {
            throw new RuntimeException("fail to sync the AD ......userCode="+newRecords.getUserCode());
        }
        //发送异动推送消息
        employeeSenderHelper.sendEmployeeYiDong(newRecords.getUserCode(), newRecords.getActiveDate(), oldEmployee.getOrgId());
        return true;
    }

    /**
	 * @param records
	 * @return 处理转正
	 *    立即生效则
	 *      更新员工状态，员工职位，新部门，转正日期，新职等职级，
	 *      重新设置职位 。如果新职位兼职有，删除兼职职位，将主要职位更改为兼职 待做
	 */
	public Integer processNewRegular(EmployeeChangeRecords records)
	{
		 EmployeeChangeRecords newRecords=findNewRecords(records);
		 if (newRecords==null) {
			return null;
		}
		 newRecords.setNewStatus(EmployeeStatus.Formal.toString());
		 //如果转正生效时间在当天以前,立即执行
	    if (newRecords.getActiveDate().before(new Date())) {
	    	newRecords.setStatus(1);
	    	activeNewRegular(newRecords);
	    }else
		{
	    	newRecords.setStatus(0);
		}
	    //新增异动记录
	    return insertAndReturnId(newRecords);
	}

    /**
     * 1、离职生效
     * 2、同步AD
     * 3、发送异动推送消息
     * @param newRecords
     * @return 离职生效
     */
    @ActiveMQTransactional
    private boolean activeNewLeave(EmployeeChangeRecords newRecords) {
        //保存异动之前的员工信息
        Employee oldEmployee = employeeService.getEmployee(newRecords.getUserCode(), EmployeeStatus.All);
        //立即生效，处理员工信息
        //首先更新员工状态
        EmployeeBaseInfor emp = new EmployeeBaseInfor();
        emp.setUserCode(newRecords.getUserCode());
        emp.setStatus(newRecords.getNewStatus());
        emp.setLeaveDate(newRecords.getLeaveDate());
        emp.setLeaveType(newRecords.getLeaveType());
        emp.setLeaveReason(newRecords.getLeaveReason());
        emp.setUpdator(newRecords.getCreator());
        if (!employeeBaseInforService.updateBaseInfoForTran(emp)) {
            throw new IllegalArgumentException("新增离职异动，变更员工状态失败");
        }
        //将负责人历史表T_OMS_ORGANIZATION_HEADER_HISTORY的status更新为离任
        //organizationHeaderHistoryService.relieveByUserCode(newRecords.getUserCode());
        //处理离职交接记录
        updateLeaveRecords(newRecords.getUserCode());
        //同步AD用户
        if (!adHelper.syncAD(newRecords.getUserCode())) {
            throw new RuntimeException("fail to sync the AD ......");
        }
        //发送异动推送消息
        employeeSenderHelper.sendEmployeeYiDong(newRecords.getUserCode(), newRecords.getActiveDate(), oldEmployee.getOrgId());
        return true;
    }

    /**
	 * @param userCode 工号
	 * @param isBlack  是否黑名单
	 * @param leaveType 离职类型
	 * @param leaveReason 离职原因
	 * @param activeDate 生效时间
	 * @param leaveDate 离职日期
	 * @return 离职接口
	 */
	public boolean processNewLeaveInterface(int userCode,Integer isBlack,String leaveType,
			String leaveReason,Date activeDate,Date leaveDate)
	{
		EmployeeChangeRecords changeRecords=new EmployeeChangeRecords();
		changeRecords.setUserCode(userCode);
		changeRecords.setLeaveDate(leaveDate);
		changeRecords.setActiveDate(activeDate);
		changeRecords.setLeaveReason(leaveReason);
		changeRecords.setLeaveType(leaveType);
		changeRecords.setChangeType(EmployeeChangeRecords.CHANGE_TYPE_LEAVE);
		return processNewLeave(changeRecords) != null;
	}
	
	/**
	 * @param records
	 * @return 处理离职异动
	 */
	public Integer processNewLeave(EmployeeChangeRecords records)
	{	
		 EmployeeChangeRecords newLeave=findForNewLeave(records);
		 if (newLeave==null) {
			return null;
		}
		 //设置新状态
		 newLeave.setNewStatus(EmployeeStatus.Leaved.toString());
		 newLeave.setCreator(records.getCreator());
		 //如果离职异动生效时间在当天以前
	    if (newLeave.getActiveDate().before(new Date())) {
	    	newLeave.setStatus(1);
	    	activeNewLeave(newLeave);
	    }else
		{
			newLeave.setStatus(0);
		}
	    //新增异动记录
	    return insertAndReturnId(newLeave);
	}
	
	/**
	 * @param records
	 * @return 转正，晋升，降职，平调,回聘
	 */
	private EmployeeChangeRecords findNewRecords(EmployeeChangeRecords records)
	{
			 if (records.getLeaveDate()==null) {
				 records.setLeaveDate(records.getActiveDate());
			 }
			 EmployeeChangeRecords newRecords=findNewChangesToProcess(records);
			 if (newRecords==null) {
				return null;
			 }
			 newRecords.setCreator(records.getCreator());
			 return newRecords;
	}
	/**
	 * @param records
	 * @return 用于除离职外新增异动记录的查找
	 */
	private EmployeeChangeRecords findNewChangesToProcess(EmployeeChangeRecords records)
	{
		return findByBean(sqlId("findNewChangesToProcess"), records);
	}
	/**
	 * @param records
	 * @return 用于新增申请离职异动记录的查找
	 */
	private EmployeeChangeRecords findForNewLeave(EmployeeChangeRecords records)
	{
		return findByBean(sqlId("findForNewLeave"), records);
	}
	/**
	 * @param changeId
	 * @return 更新异动状态生效
	 */
	public boolean updateValid(int changeId)
	{
		return update(sqlId("updateValid"),changeId);
	}
	
	/**
	 * @param changeId
	 * @param updator
	 * @return 回滚删除
	 */
	public boolean updateInvalidForRollback(int changeId,int updator)
	{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("changeId", changeId);
		params.put("updator", updator);
		return update(sqlId("updateInvalid"), params);
	}
	/**
	 * @param changeId
	 * @param updator
	 * @return 删除异动记录，将status=-1
	 */
	public boolean doDelete(int changeId,int updator) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("changeId", changeId);
		params.put("updator", updator);
		return update(sqlId("doDelete"), params);
	}
	/**
	 * @param changeId
	 * @param userCode
	 * @return 回滚是否有效
	 */
	private boolean isRollbackValid(int changeId,int userCode)
	{
		Object obj=queryForObject(sqlId("findLastActiveChangeId"),userCode);
		if (obj==null) {
			return false;
		}
		return  changeId== Integer.valueOf(String.valueOf(obj));
	}

    /**
     * 1、回滚生效
     * 2、同步AD
     * @param changeId 异动记录
     * @param userCode 工号
     * @param updator  操作人
     * @return 首先检查changId是否是最后一次生效异动记录的ID
     */
    public boolean doRollback(int changeId, int userCode, int updator) {
        //校验回滚是否有效
         if (!isRollbackValid(changeId, userCode)) {
            return false;
        }
        //找到需要重新执行的异动记录，并且将当前的记录设为删除。
        //。如果只有一条生效的记录，则需要将这条记录的old值设为账号的状态
       /* int count = countActiveChanges(userCode);
        if (count == 0) {
            return false;
        } else if (count == 1) {*/
            //只有一条生效记录
            EmployeeChangeRecords er = findForOnlyOneRollback(changeId);
            if (er != null && processForOnlyOneRollback(er)) {
                //回滚后再根据异动类型处理员工状态
                if (!postHandleRollback(changeId, userCode, er.getOldStatus()) || !updateInvalidForRollback(changeId, updator)) {
                    throw new IllegalArgumentException("更新异动记录出错。");
                }
                employeeBaseInforService.updateEmpOrgPositionByUserCode(userCode);
            } else {
                throw new IllegalArgumentException("回滚异动记录出错。");
            }
        /*} else if (count > 1) {
            //回滚记录》一
            EmployeeChangeRecords er = findChangesForRollback(userCode, changeId);
            if (activeChanges(er)) {
                //回滚后根据异动类型处理员工状态
                if (!postHandleRollback(changeId, userCode, er.getNewStatus()) || !updateInvalidForRollback(changeId, updator)) {
                    throw new IllegalArgumentException("更新异动记录出错。");
                }
            } else {
                throw new IllegalArgumentException("回滚异动记录出错。");
            }
        }*/

        //同步AD用户
        if (!adHelper.syncAD(userCode)) {
            throw new RuntimeException("fail to sync the AD ......");
        }

        //发邮件
        mailHelper.sendRollBackMail(er,  updator);
        return true;
    }

    /**
	 * @return 后处理回滚
	 */
	private boolean postHandleRollback(int changeId,int userCode,String userStatus)
	{
		 EmployeeChangeRecords records=findById(changeId);
		 if (records==null) {
			return false;
		}
		 String changeType=records.getChangeType();
		   //处理离职交接记录
         updateLeaveRecords(userCode);
		 //如果晋升，降职，任命,平调不予处理
		 if (EmployeeChangeRecords.CHANGE_TYPE_PROMOTION.equals(changeType) || 
					EmployeeChangeRecords.CHANGE_TYPE_DEMOTION.equals(changeType) ||
					EmployeeChangeRecords.CHANGE_TYPE_RELEVEL.equals(changeType) || EmployeeChangeRecords.CHANGE_TYPE_APPOINT.equals(changeType)) {
			 return true;
		 }

        switch (changeType) {
            case EmployeeChangeRecords.CHANGE_TYPE_LEAVE:
                //离职回滚,需要将leaveDate,leaveType,leaveReason置空
                if (employeeBaseInforService.updateLeave(userCode)) {
                    EmployeeBaseInfor emp = new EmployeeBaseInfor();
                    emp.setUserCode(userCode);
                    emp.setStatus(userStatus);
                    if (!employeeBaseInforService.update(emp)) {
                        throw new IllegalArgumentException("重置isBlack出错。");
                    }
                } else {
                    throw new IllegalArgumentException("重置离职reason等。。date出错。");
                }
                break;
            case EmployeeChangeRecords.CHANGE_TYPE_REGULAR:
                //转正回滚,需要将formarlDate置空
                employeeBaseInforService.updateFormalDate(userCode);

                break;
            case EmployeeChangeRecords.CHANGE_TYPE_REHIRE:
                //回聘回滚，需要将newJoinDate更改为joinDate
                //回聘 设置newJoinDate=joinDate
                EmployeeBaseInfor emp = new EmployeeBaseInfor();
                emp.setUserCode(userCode);
                emp.setStatus(userStatus);
                emp.setNewJoinDate(employeeService.getEmployee(userCode, 0, 0, EmployeeStatus.All).getJoinDate());
                if (!employeeBaseInforService.update(emp)) {
                    throw new IllegalArgumentException("重置newJoinDate出错。");
                }
                break;
        }
		 return true;
	}

    /**
     * 回滚
     * 1、回滚生效
     * 2、同步AD
     * 3、发送推送消息
     *    如果是回滚则发送当前日期为生效时间
     * @param newRecords
     * @return
     */
    @ActiveMQTransactional
    private boolean processForOnlyOneRollback(EmployeeChangeRecords newRecords) {
        //保存异动之前的员工信息
        Employee oldEmployee = employeeService.getEmployee(newRecords.getUserCode(), EmployeeStatus.All);
        //修改头衔
        employeeBaseInforService.updateUserTitileForTran(newRecords.getOldLevelId(), newRecords.getUserCode());
        EmployeeBaseInfor emp = new EmployeeBaseInfor();
        emp.setUserCode(newRecords.getUserCode());
        //设置新部门
        emp.setOrgId(newRecords.getOldOrgId());
        //设置状态
        emp.setStatus(newRecords.getOldStatus());
        //设置职位ID
        emp.setPositionId(newRecords.getOldPositionId());
        //设置职等职级
        emp.setLevelId(newRecords.getOldLevelId());
        emp.setUpdator(newRecords.getCreator());
        if (!employeeBaseInforService.update(emp)) {
            throw new IllegalArgumentException("异动变更员工记录失败");
        }
        //重新设置职位。。。如果新职位兼职有，删除兼职职位，
        if (!employeePositionService.changePrimaryPosition(newRecords.getOldOrgId(), newRecords.getOldPositionId(), newRecords.getUserCode())) {
            throw new IllegalArgumentException("回滚异动记录，重新设置职位 删除已有兼职职位，更新主要职位为OldPositionId发生异常：" + newRecords);
        }
        employeeBaseInforService.updateEmpOrgPositionByUserCode(newRecords.getUserCode());

        //同步AD用户
        if (!adHelper.syncAD(newRecords.getUserCode())) {
            throw new RuntimeException("fail to sync the AD ......");
        }
        //发送推送消息
        employeeSenderHelper.sendEmployeeYiDong(newRecords.getUserCode(), DateFormatUtil.getNow(), oldEmployee.getOrgId());
        return true;
    }

    /**
	 * @param changeId
	 * @return 只有一条生效记录
	 */
	private EmployeeChangeRecords findForOnlyOneRollback(int changeId)
	{
		return findByBean(sqlId("findForOnlyOneRollback"), changeId);
	}
	/**
	 * @param userCode
	 * @param changeId
	 * @return 有多条生效记录，找次等生效的异动记录
	 */
//	private EmployeeChangeRecords findChangesForRollback(int userCode,int changeId)
//	{
//		Map<String,Object> params=new HashMap<>();
//		params.put("userCode", userCode);
//		params.put("changeId", changeId);
//		return findByBean(sqlId("findForRollback"), params);
//	}
	/**
	 * @param userCode
	 * @return 统计生效的异动记录
	 */
//	private int countActiveChanges(int userCode)
//	{
//		return count(sqlId("countActiveChanges"), userCode);
//	}
	/**
	 * @param userCode
	 * @return 更新离职交接记录
	 */
	private boolean updateLeaveRecords(int userCode)
	{
		return update(sqlId("updateLeaveRecords"), userCode);
	}

    /**
     * 更新oldOrgName
     * @since: 2014-06-20 16:32:50
     * @param changeIds
     * @return
     */
    public boolean updateOldOrgNameByChangeIds(String oldOrgName, String changeIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("oldOrgName", oldOrgName);
        param.put("changeIds", changeIds);
        return update(sqlId("updateOldOrgNameByChangeIds"), param);
    }

    /**
     * 根据组织id，为该部门下的员工生成一条异动记录
     * @since: 2014-06-23 09:37:43
     * @param orgId
     * @param orgOldName
     * @param activeDate
     */
    public void generateChanges(int orgId, String orgOldName, Date activeDate) {
        List<EmployeeBaseInfor> employeeBaseInforList = employeeBaseInforService.queryByOrgId(orgId);
        StringBuilder changeIds = new StringBuilder();
        for (EmployeeBaseInfor e : employeeBaseInforList) {
            EmployeeChangeRecords records = new EmployeeChangeRecords();
            records.setUserCode(e.getUserCode());
            records.setChangeType(EmployeeChangeRecords.CHANGE_TYPE_RELEVEL);
            records.setActiveDate(activeDate);
            records.setCreator(Constants.GUANLIYUAN);
            records.setNewOrgId(e.getOrgId());
            records.setNewPositionId(e.getPositionId());
            records.setNewLevelId(e.getLevelId());
            Integer changeId = this.processChanges(records);
            changeIds.append("," + changeId);
        }
        //更新oldOrgName
        this.updateOldOrgNameByChangeIds(orgOldName, changeIds.deleteCharAt(0).toString());
    }

}

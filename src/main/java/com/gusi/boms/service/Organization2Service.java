package com.gusi.boms.service;

import com.gusi.activemq.helper.OrganizationSenderHelper;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.AdHelper;
import com.gusi.boms.model.*;
import com.gusi.boms.util.GeneratorUtil;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
* @ClassName: Organization2Service
* @Description: 组织部门的业务逻辑
* @author fdj
* @date 2013-4-10 上午10:12:43
 */
@Service
public class Organization2Service extends BaseService<Organization> {

    private Log log = LogFactory.getLog(Organization.class);

    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
	@Autowired
	private OrganizationOperateHistoryService orgOperHisService;
	@Autowired
	private OrganizationHeaderHistoryService orgHeaderHisService;
	@Autowired
	private OrganizationNameService orgNameService;
    @Autowired
    private OrganizationLogService orgLogService;
    @Autowired
    private FreemarkerUtil fkUtil;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private OrganizationSenderHelper organizationSenderHelper;
    @Autowired
    private AdHelper adHelper;
    @Autowired
    private OrganizationOrgCountService organizationOrgCountService;
    @Autowired
    private OrganizationEmployeeCountService organizationEmployeeCountService;

    /**
     * 查找视图
     * @param id
     * @return
     */
    public Organization findViewById(int id) {
        return findById(sqlId("findViewById"), id);
    }

    /**
     * 查询组织人数相关信息
     * @since: 2014-06-09 11:13:28
     * @param id
     * @return
     */
    public Organization findBZForDetail(int id) {
        return findById(sqlId("findBZForDetail"), id);
    }

    /**
      * @since: 3.0.5 
      * @param id
      * @return
      * <p>
      *  查找正常组织视图，用于组织转调。
      * </p>   
     */
    public Organization findViewForTransfer(int id){
    	return findById(sqlId("findViewForTransfer"), id);
    }
    /**
      * @since: 3.0.5 
      * @param parentId
      * @return
      * <p>
      *  根据ParentId查询sub组织（视图）
      *   查询正常的有负责人的组织，并且没有未生效转调记录的组织
      * </p>   
     */
    public List<Organization> findSubOrgsWithView(int parentId) {
        return queryForList(sqlId("findSubOrgsWithView"), parentId);
    }
    /**
     * 根据组织部门获取上级组织部门
     * @param org
     * @return
     */
    public Organization getParentOrg(Organization org) {
        if(org != null && org.getParentId() != 0) {
            return findById(org.getParentId());
        }
        return null;
    }

    /**
     * 插入或更新组织
     * 同时更新组织负责人历史表
     * @param org 组织
     * @param emp 创建人或更新人
     * @return
     */
    @Transactional
    @ActiveMQTransactional
    public int insertOrUpdateOrg(Organization org, Employee emp, int maxCount) {
        int id;
        //新增
        if(org.getId() == 0) {
            org.setCreator(emp.getUserCode());
            org.setCompany(emp.getCompany());
            id = addOrg(org);
            //插入组数记录
            organizationOrgCountService.insert(id, maxCount, emp.getUserCode());
        //编辑
        } else {
            id = org.getId();
            org.setUpdator(emp.getUserCode());
            updateOrgAndInsertHistory(emp, org);
            //更新组织记录
            if(emp.checkPrivilege(Constants.OMS_OM_ORG_COUNT)) {
                organizationOrgCountService.update(id, maxCount, emp.getUserCode());
            }
        }
        return id;
    }

	/**
	 * 添加新的组织
     * 1、添加组织
     * 2、添加组织名称拼音
     * 3、插入组织负责人表
     * 4、插入员工公盘
     * 5、负责人兼职到本部门
     * 6、同步OU
     * 7、发送mq组织新增消息(@updateTime 2013-12-19)
	 * @param organization
	 * @return 是否添加成功
	 */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {Exception.class})
	private int addOrg(Organization organization) {
        Organization parentOrg = getParentOrg(organization);
        if(parentOrg != null) {
        	if (parentOrg.getOpenDate()==null || organization.getOpenDate().before(parentOrg.getOpenDate())) {
        		throw new IllegalArgumentException(parentOrg.getOrgName()+"没有设置开组日期或者"
        				+organization.getOrgName()+"的开组日期早于"+parentOrg.getOrgName()+"的开组日期，请确认。");
        	}
            organization.setOrgLevel(parentOrg.getOrgLevel() + 1);
            organization.setLocIndex(getLocIndex(parentOrg));
            organization.setOrgCode(GeneratorUtil.generateOrgCode());
            organization.setOrgLongCode(GeneratorUtil.generateOrgLongCode(parentOrg.getOrgLongCode(), GeneratorUtil.generateOrgCode()));
          //新增的时候如果生效日期小于当前时间，那么此组织状态为正常，否则为临时组织
            organization.setStatus(organization.getOpenDate().before(new Date())?Organization.STATUS_NORMAL:Organization.STATUS_TEMP);
            organization.setId(insertAndReturnId(organization));
            orgHeaderHisService.insertHeaderHistory(organization);
            employeeBaseInforService.createPublicoffer(findById(organization.getId()));
            orgNameService.insertOrUpdate(organization);
            //如果部门已经生效，负责人不在该部门，那么插入兼职
            if(organization.getStatus() == Organization.STATUS_NORMAL) {
                employeePositionService.insertEmployeePosition(organization.getId(), organization.getManager());
            }

            //创建OU，如果上级OU没有则报错
            if(!adHelper.syncOU(organization.getId())) {
                throw new RuntimeException("fail create OU");
            }

            //发送mq组织新增消息
            organizationSenderHelper.sendOrgXinZeng(organization.getId(), organization.getOrgName(), organization.getParentId());
            return organization.getId();
        }
        return 0;
	}

    /**
     * 更新组织信息
     * 1、先插入组织日志
     * 2、更新负责人表
     * 3、插入操作记录表
     * 4、更新组织，longCode，level等等
     * 5、更新组织名称
     * 6、更新员工表公盘
     * 7、新增临时组织概念，正常组织编辑，如果生效时间在当前时间之后，
     *    则变为临时组织，如果临时组织生效时间在现在之前，则变为正常
     * 8、负责人兼职到本部门，如果负责人变化，也不删除之前负责人兼职到本部门的记录
     * 9、同步OU
     * 10、发送mq组织编辑消息
     * @param emp 操作人
     * @param org 操作组织
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    @ActiveMQTransactional
    public boolean updateOrgAndInsertHistory(Employee emp, Organization org, String operateType, String logType) {
        //编辑前组织信息
        Organization oldOrg = findById(org.getId());

        OrganizationOperateHistory orgOperateHistory = new OrganizationOperateHistory();
        orgOperateHistory.setOrgId(org.getId());
        orgOperateHistory.setCreator(emp.getUserCode());
        orgOperateHistory.setCreateName(emp.getUserName());
        orgOperateHistory.setFiled(operateType);
        orgLogService.insertOrgLog(org, emp, logType);
        if(orgOperHisService.saveOperateHistory(org, orgOperateHistory)) {
            orgHeaderHisService.updateHeaderHistory(org);
            orgNameService.insertOrUpdate(org);
            Organization parentOrg = getParentOrg(org);
            Organization old = findById(org.getId()); //本组织
            if(parentOrg != null) {
                org.setLocIndex(getLocIndex(parentOrg));
            }
            update(org);
            dealTempOrg(org, old);
            updateLongCode(old,org,parentOrg);
            updateOrgLevel(old,org,parentOrg);
            employeeBaseInforService.updatePublicoffer(findById(org.getId()));
            employeeBaseInforService.updateEmpOrgPositionByOrgId(org.getId());
            employeePositionService.insertEmployeePosition(org.getId(), org.getManager());
            //更新
            log.info("update OU 开始。----------------------" + org.getId());
            if(!adHelper.syncOU(org.getId())) {
                throw new RuntimeException("fail update OU,组织信息："+org
                		+",operateType="+operateType+",logType="+logType+",操作人信息："+emp);
            }
            log.info("update OU end。----------------------" + org.getId());
            //发送组织编辑mq消息
            organizationSenderHelper.sendOrgBianJi(org.getId(), oldOrg.getOrgName(), oldOrg.getParentId());
            return true;
        }
        return false;
    }
    
    /**
     * 更新组织信息
     * @param emp 操作人
     * @param org 组织
     * @return
     */
    public boolean updateOrgAndInsertHistory(Employee emp, Organization org) {
        return updateOrgAndInsertHistory(emp, org, Constants.ORG_INFO_BASE, OrganizationLog.OPERATETYPE_EDIT);
    }

    /**
     * 处理临时组织
     * //原来状态正常组织编辑，如果生效时间在当前时间之后，
     //   则变为临时组织，如果临时组织生效时间在现在之前，则变为正常
     * @param org
     * @param old
     */
    private void dealTempOrg(Organization org, Organization old) {
        int status=old.getStatus();
        if (status == Organization.STATUS_NORMAL || status == Organization.STATUS_TEMP) {
            Date activeDate=org.getOpenDate();
            Organization temp=new Organization();
            temp.setId(old.getId());
            temp.setUpdator(org.getUpdator());
            if (status==Organization.STATUS_NORMAL) {
                 if (activeDate!= null && activeDate.after(new Date())) {
                     temp.setStatus(Organization.STATUS_TEMP);
                    updateStatusForEdit(temp);
                }
            }else{
                if (activeDate != null && activeDate.before(new Date())) {
                     temp.setStatus(Organization.STATUS_NORMAL);
                    updateStatusForEdit(temp);
                }
            }
        }
    }

    /**
      * @since: 3.0.5 
      * @param oldOrg
      * @param newOrgName 组织新名称
      * @return
      * <p>
      * 暂停分行、组织改名
      *  1,添加一条组织变更log
      *  2,更新T_OMS_ORGANIZATION_NAME这张表。
      *  3，更新组织信息。
      * @update: 2014-06-03 11:33:00 更新组织信息后，添加一个区域人数限制
      * @update: 2014-06-20 10:36:54 更新组织信息后，更新员工列表的组织和岗位信息
      *  4，同步AD
      *  5,发送组织编辑信息。
      * </p>   
     */
    @ActiveMQTransactional
    @Transactional
    public boolean stopFenHang(Organization oldOrg, Employee emp, String newOrgName) {
        //组织变动Log
        orgLogService.insertOrgLog(oldOrg, emp, OrganizationLog.OPERATETYPE_STOP);

        oldOrg.setUpdator(emp.getUserCode());
        oldOrg.setStatus(Organization.STATUS_STOP);
        //编辑前组织信息
        String oldOrgName = oldOrg.getOrgName();

        oldOrg.setOrgName(newOrgName);
        orgNameService.insertOrUpdate(oldOrg);
        //更新状态为暂停、同时修改名称
        update(sqlId("stopFenHang"), oldOrg);
        //更新员工列表的部门和岗位信息
        employeeBaseInforService.updateEmpOrgPositionByOrgId(oldOrg.getId());
        //插入一条区域人数限制
        organizationEmployeeCountService.insertByOrgId(oldOrg.getId(), oldOrg.getClosedDate(), oldOrg.getUpdator());

        log.info("组织改名，update OU 开始。----------------------" + oldOrg + ",newOrgName=" + newOrgName);
        if (!adHelper.syncOU(oldOrg.getId())) {
            throw new RuntimeException("fail update OU,组织信息：" + oldOrg);
        }
        log.info("update OU end。----------------------");
        //发送组织编辑mq消息
        organizationSenderHelper.sendOrgBianJi(oldOrg.getId(), oldOrgName, oldOrg.getParentId());
        organizationSenderHelper.sendOrgPause(oldOrg.getId(), newOrgName, oldOrg.getParentId());
        return true;
    }
    /**
     * @return
     * 将临时组织变为正常组织
     */
    public boolean updateTempOrgsToNormal()
    {
    	return update(sqlId("updateTempOrgsToNormal"),0);
    }

    //更新组织及其子组织的LongCode
    private void updateLongCode(Organization old,Organization org,Organization parent){
        if(parent ==null){
            return;
        }
        if(old.getParentId() == org.getParentId())
            return;
        updateLongCode(old.getOrgLongCode(), GeneratorUtil.generateOrgLongCode(parent.getOrgLongCode(), old.getOrgCode()));
    }

    //更新组织及其子组织的Level
    private void updateOrgLevel(Organization old,Organization org,Organization parent){
        if(parent ==null){
            return;
        }
        int dis = parent.getOrgLevel() + 1 - old.getOrgLevel();
        if(dis == 0)
            return;
        updateOrgLevel(dis, GeneratorUtil.generateOrgLongCode(parent.getOrgLongCode(), old.getOrgCode()));
    }

    private void updateLongCode(String oldCode, String newCode){
        Map<String, Object> param = new HashMap<>();
        param.put("oldCode", oldCode);
        param.put("newCode", newCode);
        this.update("updateOrgLongCode",param);
    }

    private void updateOrgLevel(int dis, String newCode){
        Map<String, Object> param = new HashMap<>();
        param.put("dis", dis);
        param.put("newCode", newCode);
        this.update("updateOrgLevel", param);
    }
	/**
	 * 根据组织部门的id，判断是否拥有负责人manager
	 * @param orgId 组织部门id
	 * @return 返回是否拥有manager
	 */
    private boolean hasManager(int orgId) {
    	return this.count(sqlId("countByManager"), orgId) > 0;
    }

    /**
     * 更新负责人历史记录表
     * @param org
     * @param employeeBaseInfor
     * @return
     */
    @Transactional
    private boolean updateManager(Organization org, EmployeeBaseInfor employeeBaseInfor) {
        if(update(sqlId("updateManager"), org)) {
            OrganizationHeaderHistory organizationHeaderHistory = new OrganizationHeaderHistory(employeeBaseInfor.getOrgId(), employeeBaseInfor.getPositionId(), employeeBaseInfor.getUserCode(), employeeBaseInfor.getUserNameCn());
            if(org.getManager() != null) {
                return orgHeaderHisService.add(organizationHeaderHistory);
            } else {
                return orgHeaderHisService.relieve(organizationHeaderHistory);
            }
        }
        return false;
    }

    /**
     *  新增组织负责人manager
     * @param employeeBaseInfor
     * @return
     */
    public boolean addManager(EmployeeBaseInfor employeeBaseInfor) {
        if(employeeBaseInfor != null) {
            if(!hasManager(employeeBaseInfor.getOrgId()) && employeeBaseInfor.getUserCode() != 0) {
                Organization org = new Organization();
                org.setId(employeeBaseInfor.getOrgId());
                org.setManager(employeeBaseInfor.getUserCode());
                return updateManager(org, employeeBaseInfor);
            }
        }
        return false;
    }

    /**
     * 撤销组织负责人manager
     * @param employeeBaseInfor
     * @return
     */
    public boolean deleteManager(EmployeeBaseInfor employeeBaseInfor) {
        if(employeeBaseInfor != null) {
            if(hasManager(employeeBaseInfor.getOrgId())) {
                Organization org = new Organization();
                org.setId(employeeBaseInfor.getOrgId());
                return updateManager(org, employeeBaseInfor);
            }
        }
        return false;
    }

    /**
     * 查询子公司数量
     * @param org 组织
     * @return
     */
    private int countSon(Organization org) {
    	return count(sqlId("countSon"), org);
    }

    /**
     * 获得位置编号
     * @param parentOrg 上级公司
     * @return 位置编号
     */
	public long getLocIndex(Organization parentOrg) {
		return (countSon(parentOrg) + 1);
	}

	/**
	 * 更新组织部门联系信息（地址，传真）
	 * @param org 组织部门
	 * @return
	 */
	public boolean updateContactInfo(Organization org) {
		return update(sqlId("updateContactInfo"), org);
	}

	/**
	 * 更新组织部门编号
	 * @param org 组织部门
	 * @return
	 */
	public boolean updateCode(Organization org) {
		return update(sqlId("updateCode"), org);
	}

	/**
	 * 更新组织部门位置编号,用于排序
	 * @param org
	 * @return
	 */
	public boolean updateLocIndex(Organization org) {
		return update(sqlId("updateLocIndex"), org);
	}

	/**
	 * 更新组织状态
	 * @param org
	 * @return
	 */
	private boolean updateStatus(Organization org) {
		return update(sqlId("updateStatus"), org);
	}

	/**
	 * @param org
	 * @return 编辑的时候更新组织状态,判定组织是临时还是正式
	 */
	public boolean updateStatusForEdit(Organization org)
	{
		return update(sqlId("updateStatusForEdit"),org);
	}
    /**
     * 更新组织传真
     * @param org
     * @return
     */
    public boolean updateOrgFax(Organization org) {
        return update(sqlId("updateOrgFax"), org);
    }

    /**
     * 启用组织
     * @param org
     * @return
     */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {Exception.class})
    public boolean openOrganization(Organization org, Employee emp) {
        if(org != null) {
            orgLogService.insertOrgLog(org, emp, OrganizationLog.OPERATETYPE_OPEN);
            org.setStatus(Organization.STATUS_NORMAL);
            org.setClosedDate(null);
            org.setUpdator(emp.getUserCode());
            boolean rs = updateStatus(org);
            //如果组织状态 从关闭到开启，需要新建OU
            log.info("create OU 开始。----------------------" + org.getId());
            if(!adHelper.syncOU(org.getId())) {
                throw new RuntimeException("fail create OU");
            }
            log.info("create OU 开始。----------------------" + org.getId());
            //发送组织编辑mq消息
            organizationSenderHelper.sendOrgBianJi(org.getId(), org.getOrgName(), org.getParentId());
            return rs;
        }
        return false;
    }

	/**
	 * 关闭组织
	 * @param org
	 * @return
	 */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {Exception.class})
	public boolean closeOrganization(Organization org, Employee emp) {
		if(org != null && org.getOpenDate().before(org.getClosedDate())) {
            orgLogService.insertOrgLog(org, emp, OrganizationLog.OPERATETYPE_CLOSE);
            org.setStatus(Organization.STATUS_CLOSE);
            org.setUpdator(emp.getUserCode());
            boolean rs = updateStatus(org);
            //删除OU
            log.info("delete OU 开始。----------------------" + org.getId());
            if(!adHelper.syncOU(org.getId())) {
                throw new RuntimeException("fail delete OU");
            }
            log.info("delete OU end。----------------------" + org.getId());
            //发送组织编辑mq消息
            organizationSenderHelper.sendOrgBianJi(org.getId(), org.getOrgName(), org.getParentId());
            organizationSenderHelper.sendOrgClose(org.getId(), org.getOrgName(), org.getParentId());
			return rs;
		}
		return false;
	}


    /**
      * @since: 3.0.5 
      * @param org
      * @param emp
      * @return
      * <p>
      *  暂停组织，是否改名。
     *  1,插入日志
     *  2,发送mq消息
     *  3,改状态
     * </p>
     */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {RuntimeException.class})
 	public boolean stopOrganization(Organization org, Employee emp) {
 		if(org != null && org.getOpenDate().before(org.getClosedDate())) {
             orgLogService.insertOrgLog(org, emp, OrganizationLog.OPERATETYPE_STOP);
             org.setUpdator(emp.getUserCode());
             org.setStatus(Organization.STATUS_STOP);
            //发送组织编辑mq消息
            organizationSenderHelper.sendOrgBianJi(org.getId(), org.getOrgName(), org.getParentId());
            organizationSenderHelper.sendOrgPause(org.getId(), org.getOrgName(), org.getParentId());
 			return updateStatus(org);
 		}
 		return false;
 	}
	/**
	 * 根据组织名称查找组织
	 * @param name 组织名称
	 * @return
	 */
	public Organization findByName(String name,String company) {
        Map<String,Object> m = new HashMap<>();
        m.put("orgName",name);
        m.put("company",company);
		return findByBean(sqlId("findByName"), m);
	}

    public boolean isExistName(String name) {
        return count(sqlId("isExistName"), name)>0;
    }

	/**
	 * 根据组织名称判断该组织是否存在
	 * @param name 组织名称
	 * @return
	 */
	public boolean isExistByName(String name,String company) {
        return findByName(name, company) != null;
    }

	/**
	 * 根据组织id和名称判断名称是否正确
	 * @param id 组织id
	 * @param name 组织名称
	 * @return
	 */
	public boolean checkName(int id, String name,String company) {
		if(StringUtils.isBlank(name)) {
			return false;
		}
		Organization org = findByName(name,company);
        return org == null || (id > 0 && id == org.getId());
    }

	/**
	 * 组织分页
	 * @param org
	 * @param pageNo
	 * @return
	 */
	public Paginate queryForPaginate(VOrganization org, int pageNo) {
		Organization params = new Organization();
		params.setColumns( " * ");
        params.setTable(" [v2_organization_list] ");
        params.setOrderBy( " parentId asc ");
        if(org.getOrderBy() != null) {
            params.setOrderBy(org.getOrderBy().replace("_", " "));
        }
        params.setPageNo(pageNo);
        params.setPageSize(Configuration.getInstance().getPageSize());
        params.setWhere(" company = '"+org.getCompany()+"'");
        if(org.getStatus() != Organization.STATUS_ALL) {
        	params.setWhere(params.getWhere() + " and status = " + org.getStatus());
        }
        if(!StringUtils.isBlank(org.getOrgName())) {
        	params.setWhere(params.getWhere() + " and orgName like '%" + org.getOrgName() + "%' ");
        }
        if(!StringUtils.isBlank(org.getOrgType())) {
        	params.setWhere(params.getWhere() + " and orgType = '" + org.getOrgType() + "' ");
        }
        if(org.getManager() != null && org.getManager() == Organization.MANAGER_NULL) {
        	params.setWhere(params.getWhere() + " and manager IS NULL ");
        }
        return queryForPaginate2(params);
	}

	/**
	 * 根据组织id查询子公司
     * 公司状态为启用
	 * @param orgId
	 * @return
	 */
	public List<Organization> querySonOrgs(int orgId) {
		return querySonOrgs(orgId, new int[]{Organization.STATUS_NORMAL});
	}

    /**
     *
     * @param orgId
     * @param status
     * @return
     */
    public List<Organization> querySonOrgs(int orgId, int[] status) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("orgId", orgId);
        return queryForList(sqlId("querySonOrgs"), param);
    }

	/**
	 * 更新组织位置编号，排序
	 * @param orgList
	 */
	@Transactional
	public void changeOrgsOrder(List<Organization> orgList, Employee emp) {
		if(orgList != null && orgList.size() >0) {
			for(Organization org : orgList) {
                orgLogService.insertOrgLog(org, emp, OrganizationLog.OPERATETYPE_SORT);
                org.setUpdator(emp.getUserCode());
				updateLocIndex(org);
			}
		}
	}

    /**
     * 获得公司
     * @param company
     * @return
     */
    public Organization getRoot(String company) {
        return findByBean(sqlId("getRoot"), company);
    }

    /**
     *
     * @param company
     * @return
     */
    public List<Organization> queryOrgTree(String company) {
        return queryForList(sqlId("queryOrgTree"), company);
    }

    /**
     * 获得前台营运中心下面的组织
     * @param company 公司
     * @return
     */
    public List<Organization> querySales(String company) {
        return queryForList(sqlId("getSales"), company);
    }

    /**
     *获得前台营运中心下不含门店的组织
     * @param company 公司
     * @return
     */
    public List<Organization> querySalesWithoutStore(String company) {
        return queryForList(sqlId("getSalesWithoutStore"), company);
    }

    /**
     *获得前台营运中心下不含分行的组织
     * @param company 公司
     * @return
     */
    public List<Organization> querySalesWithoutBranch(String company) {
        return queryForList(sqlId("getSalesWithoutBranch"), company);
    }

    /**
     * 获得后台组织
     * @param company 公司
     * @return
     */
    public List<Organization> querySupport(String company) {
        return queryForList(sqlId("getSupport"), company);
    }

    public Organization getSalesRoot(int id) {
        return findByBean(sqlId("getSalesRoot"), id);
    }

    /**
     * 获得基础信息详情模板
     * @param organization 组织
     * @return
     */
    public String getBaseDetailsHtml(Organization organization) {
        String htmlTemp = null;
        Map<String, Organization> fltMap= new HashMap<String, Organization>();
        fltMap.put("organization", organization);
        try {
            htmlTemp = fkUtil.writeTemplate("/org/orgBaseDetails.ftl", fltMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlTemp;
    }

    /**
     * 获得编辑组织信息模板
     * @param organization 组织
     * @param canEditOrgCount 是否有编辑组数权限
     * @param canEdit
     * @return
     */
    public String getBaseEditHtml(Organization organization, boolean canEditOrgCount
    		,boolean canEdit) {
        String htmlTemp = null;
        Map<String, Object> fltMap= new HashMap<String, Object>();
        if(organization.getId() != 0) {
            fltMap.put("orgMaxCount", organizationOrgCountService.findById(organization.getId()).getMaxCount());
        }
        fltMap.put("canEditOrgCount", canEditOrgCount);
        fltMap.put("organization", organization);
        fltMap.put("isAdd",  Constants.TEMP_ORG_PARENTID==organization.getParentId());
		fltMap.put("canEdit", canEdit);
        try {
            htmlTemp = fkUtil.writeTemplate("/org/orgBaseEdit.ftl", fltMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlTemp;
    }

    /**
     * 获得组织传真编辑模板
     * @param organization
     * @return
     */
    public String getOrgFaxEditHtml(Organization organization) {
        String htmlTemp = null;
        Map<String, Organization> fltMap= new HashMap<String, Organization>();
        fltMap.put("organization", organization);
        try {
            htmlTemp = fkUtil.writeTemplate("/org/orgFaxEdit.ftl", fltMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlTemp;
    }

    /**
     * 获得编辑组织电话模板
     * @param orgPhoneList 组织电话集合
     * @return
     */
    public String getPhoneEditHtml(List<OrganizationPhone> orgPhoneList) {
        String htmlTemp = null;
        Map<String, List<OrganizationPhone>> fltMap= new HashMap<String, List<OrganizationPhone>>();
        fltMap.put("orgPhoneList", orgPhoneList);
        try {
            htmlTemp = fkUtil.writeTemplate("/org/orgPhoneEdit.ftl", fltMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlTemp;
    }

    /**
     * 获取组织层级名称，例如：静安A组/静安店/中一区/大东区
     * @param id
     * @return
     */
    public String findorgLongNameById(int id) {
        return (String)queryForObject(sqlId("findorgLongNameById"), id);
    }

    /**
     * 查询区域orgLongCode，如果没有返回Null
     * @param id 组织id
     * @return
     */
    public VOrganization findAreaOrg(int id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            String sql = fkUtil.writeTemplate("/org/findAreaOrg.ftl", params);
            return (VOrganization)this.queryForObject(sqlId("findAreaOrg"), sql);
        } catch (Exception e) {
            log.error("查询区域orglongcode失败。id="+id, e);
            return null;
        }
    }

    /**
     * 查询员工数量（组织树下）
     * @param orgLongCode 组织orgLongCode
     * @return
     */
    public int findEmployeeCount(String orgLongCode) {
        return (int)queryForObject(sqlId("findEmployeeCount"), orgLongCode);
    }

    /**
     * 查询正常分行数量
     * @param orgLongCode
     * @return
     */
    public int findBranchCount(String orgLongCode) {
        return (int)queryForObject(sqlId("findBranchCount"), orgLongCode);
    }



}
package com.gusi.boms.service;

import com.gusi.activemq.helper.EmployeeSenderHelper;
import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.AttachHelper;
import com.gusi.boms.model.*;
import com.gusi.boms.util.HistoryInfoUtils;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-10 09:52)
 * Description: 员工职位组织信息的业务逻辑信息
 */
@Service
@Transactional
public class EmployeePositionService extends BaseService<EmployeePosition> {

   @Autowired
   private EmployeeOperateHistoryService employeeOperateHistoryService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private EmployeeSenderHelper employeeSenderHelper;

    /**
     * 新增员工对应的职位和组织信息
     * @param employeePosition  员工职位组织信息对象
     * @return boolean
     */
    public boolean insertEmployeePosition(EmployeePosition employeePosition) {
        if(count(sqlId("countByEntity"),employeePosition) == 0){
//            saveEmployeeOperateHistory(employeePosition);
            insert(employeePosition);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 插入兼职信息并修改员工列表展示信息
     * @param orgId
     * @param userCode
     */
    @Transactional
    @ActiveMQTransactional
    public boolean insertEmployeePosition(int orgId, Integer userCode) {
        EmployeeBaseInfor employee = employeeBaseInforService.findById(userCode);
        if(employee != null) {
            EmployeePosition employeePosition = new EmployeePosition(userCode, employee.getPositionId(), orgId, 1);
            if(insertEmployeePosition(employeePosition)) {
                employeeBaseInforService.updateEmpOrgPositionByUserCode(userCode);
                employeeSenderHelper.sendEmployeeParttimePositionChange(userCode,orgId);
            }
        }
        return false;
    }

    /**
     * @param orgId 部门ID
     * @param positionId 新职位
     * @param userCode 工号
     * @return 变更主要职位
     */
    @Transactional
    public boolean changePrimaryPosition(int orgId,int positionId,int userCode)
    {
    	//首先检查此人兼职里是有此主要职位，如果有，则删除
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("userCode", userCode);
    	params.put("orgId", orgId);
    	params.put("positionId", positionId);
    	delete(sqlId("deleteSamePosition"), params);
    	//更新主要职位
    	return update(sqlId("changePrimaryPosition"), params);
    }

    /**
     * 新增操作记录
     * @update: 2014-05-27 15:23:53
     * @param employeePosition
     * @return
     */
    public boolean saveEmployeeOperateHistory(EmployeePosition employeePosition){
        VEmployeePosition vEmployeePosition = (VEmployeePosition)employeePosition;
        EmployeeOperateHistory employeeOperateHistory = new EmployeeOperateHistory(Constants.JOB_INFORMATION,vEmployeePosition.getCreateName(),vEmployeePosition.getCreateor(),employeePosition.getUserCode());
        employeeOperateHistory.setRemark("新增岗位" + employeePosition.getPositionId() + ",部门" + employeePosition.getOrgId() + ",职等"+((VEmployeePosition) employeePosition).getTitleId());
        return employeeOperateHistoryService.insert(employeeOperateHistory);
    }

    /**
     * 新增兼职时保存操作记录
     * @param employeePosition 合同记录实体
     * @return  String
     */
    private String getNewRemark(EmployeePosition employeePosition){
        VEmployeePosition vEmployeePosition = (VEmployeePosition)employeePosition;
                StringBuilder remark = new StringBuilder();
        if (!StringUtils.isBlank(vEmployeePosition.getUserNameCn())){
            remark.append(HistoryInfoUtils.getChangeStr("员工姓名", HistoryInfoUtils.getNewRemark(vEmployeePosition.getUserNameCn())));
        }

        if (!StringUtils.isBlank(vEmployeePosition.getOrgName())){
            remark.append(HistoryInfoUtils.getChangeStr("组织名称", HistoryInfoUtils.getNewRemark(vEmployeePosition.getOrgName())));
        }

        if (!StringUtils.isBlank(vEmployeePosition.getPositionName())){
            remark.append(HistoryInfoUtils.getChangeStr("职位名称", HistoryInfoUtils.getNewRemark(vEmployeePosition.getPositionName())));
        }
        return StringBuilderHelper.trimLast(remark, "<br/>").toString();
    }

    /**
     * 删除兼职时记录操作记录
     * @update: 2014-05-27 15:24:08
     * @param id 兼职的id
     * @param employee 操作者对象
     */
    public void saveDelEmployeePositionHistroy(int id ,Employee employee){
        VEmployeePosition vEmployeePosition = (VEmployeePosition)findById(id);
        if(vEmployeePosition == null) {
            return;
        }
        EmployeeOperateHistory operateHistory = new EmployeeOperateHistory(Constants.JOB_INFORMATION,employee.getUserName(),employee.getUserCode(),vEmployeePosition.getUserCode());
        operateHistory.setRemark("删除操作==》"+ vEmployeePosition.getUserNameCn()+" 【"+vEmployeePosition.getOrgName()+","+vEmployeePosition.getPositionName()+"】");
        employeeOperateHistoryService.insert(operateHistory);
    }

    /**
     * 查找该岗位下的员工数量('正式','试用期')
     * @param positionId
     * @return
     */
    public int countEmployeeByPositionId(int positionId) {
        Map<String, Object> param = new HashMap<>();
        param.put("positionId", positionId);
        return countEmplyee(param);
    }

    /**
     * 查询该组织下的员工数量('正式','试用期')
     * @param orgId
     * @return
     */
    public int countEmployeeByOrgId(int orgId) {
        Map<String, Object> param = new HashMap<>();
        param.put("orgId", orgId);
        return countEmplyee(param);
    }

    private int countEmplyee(Map<String, Object> param) {
        return count(sqlId("countEmployee"), param);
    }

    public List<EmployeePosition> queryParttimePositions(int userCode){
        return this.queryForList(sqlId("queryParttimePositions"), userCode);
    }

    /**
     * 新增兼职信息
     * @param positions
     * @param userCode
     * @param creator
     * @return
     */
    @Transactional
    public boolean batchInsert(final VEmployeePosition[] positions,final int userCode,final int creator) {
        if (positions==null || positions.length==0) {
            return true;
        }
        boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
            @Override
            public Boolean doInSqlMapClient(SqlMapExecutor executor)
                    throws SQLException {
                executor.startBatch();
                int count = positions.length;
                for (VEmployeePosition employeePosition : positions) {
                    if (employeePosition.getOrgId() == 0 || employeePosition.getPositionId() == 0 || employeePosition.getTitleId() ==0 ) {
                        count--;
                        continue;
                    }
                    employeePosition.setUserCode(userCode);
                    employeePosition.setCreateor(creator);
                    employeePosition.setPartTime(EmployeePosition.PART_TIME);
                    executor.insert(sqlId("insert"),employeePosition);
                }
                return executor.executeBatch()==count;
            }
        });
        if (!flag) {
            throw new IllegalArgumentException("批量合同记录出错");
        }
        return true;
    }

    public boolean deleteByUserCode(int userCode){
        return this.delete(sqlId("deleteByUserCode"),userCode);
    }

    /**
     * 根据工号删除所有记录
     * @param userCode
     * @return
     */
    public boolean deleteAllByUserCode(int userCode){
        return this.delete(sqlId("deleteAllByUserCode"),userCode);
    }

    /**
     * 删除员工岗位信息
     * @update: 2014-05-27 14:17:31
     * @param delIds
     * @param employee
     */
    public void deleteParttimePosition(String delIds, Employee employee) {
        //先保存操作记录
        String[] ids = AttachHelper.getAttachIds(delIds);
        if(ids == null || ids.length == 0) {
            return;
        }
        for(String id : ids) {
            saveDelEmployeePositionHistroy(Integer.valueOf(id), employee);
        }
        //删除数据
        Map<String,Object> delMap = new HashMap<String, Object>();
        delMap.put("delIds", ids);
        this.delete(sqlId("delPs"),delMap);
    }

    /**
     * 新增员工岗位信息
     * @since: 2014-05-27 14:34:46
     * @param positions
     * @param userCode
     * @param emp
     */
    private void insertEmployeePostions(VEmployeePosition[] positions, int userCode, Employee emp) {
        if(positions == null || positions.length == 0) {
            return;
        }
        //先保存操作记录
        for(VEmployeePosition vp : positions) {
            if (vp.getOrgId() == 0 || vp.getPositionId() == 0 || vp.getTitleId() ==0 ) {
                continue;
            }
            vp.setUserCode(userCode);
            vp.setCreateor(emp.getUserCode());
            vp.setCreateName(emp.getUserName());
            saveEmployeeOperateHistory(vp);
        }
        //新增数据
        this.batchInsert(positions, userCode, emp.getUserCode());
    }

    /**
     * 更新员工职位信息
     * @since: 2014-05-27 14:05:53
     * @param vEmployeeDetails
     * @param userCode
     * @param emp
     */
    public void updateEmployeePositions(VEmployeeDetails vEmployeeDetails, int userCode, Employee emp) {
        //删除原有的
        this.deleteParttimePosition(vEmployeeDetails.getDelId(), emp);
        //添加新的
        this.insertEmployeePostions(vEmployeeDetails.getPositions(), userCode, emp);
        employeeBaseInforService.updateEmpOrgPositionByUserCode(userCode);
        //兼职变动发送通知
        employeeSenderHelper.sendEmployeeParttimePositionChange(userCode,0);
    }

}
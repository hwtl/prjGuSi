package com.gusi.boms.service;

import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.AttachHelper;
import com.gusi.boms.model.EmployeeContractRecords;
import com.gusi.boms.model.EmployeeOperateHistory;
import com.gusi.boms.model.VEmployeeDetails;
import com.gusi.boms.util.HistoryInfoUtils;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.util.DyDate;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-10 14:54)
 * Description: 合同记录业务逻辑
 */
@Service
@Transactional
public class EmployeeContractRecordsService extends BaseService<EmployeeContractRecords> {
    @Autowired
    EmployeeOperateHistoryService employeeOperateHistoryService;

    /**
     * 新增员工合同
     * @param employeeContractRecords 员工合同实体
     * @return boolean
     */
    public boolean insertEmployeeContract(EmployeeContractRecords employeeContractRecords){
        saveEmployeeOperateHistory(employeeContractRecords);
        return insert(employeeContractRecords);
    }

    /**
     * 更新员工合同信息
     * @param employeeContractRecords 员工合同实体
     * @return boolean
     */
    public boolean updateEmployeeContract(EmployeeContractRecords employeeContractRecords){
        saveEmployeeOperateHistory(employeeContractRecords);
        return update(employeeContractRecords);
    }

    /**
     * 保存合同记录的操作历史
     * @param employeeContractRecords 合同记录信息
     * @return boolean
     */
    private boolean saveEmployeeOperateHistory(EmployeeContractRecords employeeContractRecords){
        EmployeeContractRecords oldEmployeeContractRecords = findById(employeeContractRecords.getId());
        EmployeeOperateHistory employeeOperateHistory = new EmployeeOperateHistory(Constants.CONTRACT_RECORDS,employeeContractRecords.getUpdateName(),employeeContractRecords.getUpdator(),employeeContractRecords.getUserCode());
        if(oldEmployeeContractRecords == null){
            employeeOperateHistory.setCreator(employeeContractRecords.getUpdator());
            employeeOperateHistory.setRemark(getNewRemark(employeeContractRecords));
            return  employeeOperateHistoryService.insert(employeeOperateHistory);
        }else{
            employeeOperateHistory.setRemark(oldEmployeeContractRecords.getOperateRemark(employeeContractRecords));
            return StringUtils.isBlank(employeeOperateHistory.getRemark()) || employeeOperateHistoryService.insert(employeeOperateHistory);
        }
    }

    /**
     * 新增合同时保存操作记录
     * @param employeeContractRecords 合同记录实体
     * @return  String
     */
    private String getNewRemark(EmployeeContractRecords employeeContractRecords){
        StringBuilder remark = new StringBuilder();
        remark.append("新增：<br/>");
        if (!StringUtils.isBlank(employeeContractRecords.getContractType())){
            remark.append(HistoryInfoUtils.getChangeStr("合同类型", HistoryInfoUtils.getNewRemark(employeeContractRecords.getContractType())));
        }
        remark.append(HistoryInfoUtils.getChangeStr("合同开始时间", HistoryInfoUtils.getNewRemark(DyDate.format(employeeContractRecords.getStartTime(), "yyyy-MM-dd"))));
        if (employeeContractRecords.getEndTime() != null){
            remark.append(HistoryInfoUtils.getChangeStr("合同结束时间", HistoryInfoUtils.getNewRemark(DyDate.format(employeeContractRecords.getEndTime(), "yyyy-MM-dd"))));
        }
        return StringBuilderHelper.trimLast(remark, "<br/>").toString();
    }

    /**
     * 删除前保存操作记录
     * @param id 合同记录id
     * @param employeee 登录人信息
     */
    public void saveDelEmployeeOperateHistory(int id,Employee employeee){
        EmployeeContractRecords employeeContractRecords = findById(id);
        EmployeeOperateHistory employeeOperateHistory = new EmployeeOperateHistory(Constants.CONTRACT_RECORDS,employeee.getUserName(),employeee.getUserCode(),employeeContractRecords.getUserCode());
        employeeOperateHistory.setRemark("删除操作==》"+employeeContractRecords.getContractType()+"("+ DyDate.format(employeeContractRecords.getStartTime(), "yyyy-MM-dd")+"至"+ DyDate.format(employeeContractRecords.getEndTime(), "yyyy-MM-dd")+")");
        employeeOperateHistoryService.insert(employeeOperateHistory);
    }

    @Transactional
    public boolean batchInsert(final EmployeeContractRecords[] contracts,final int userCode,final int creator) {
        if (contracts==null || contracts.length==0) {
            return true;
        }
        boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
            @Override
            public Boolean doInSqlMapClient(SqlMapExecutor executor)
                    throws SQLException {
                executor.startBatch();
                int count = contracts.length;
                for (EmployeeContractRecords contract : contracts) {
                    if (StringUtils.isBlank(contract.getContractType()) || contract.getStartTime() == null ||
                    		(contract.getContractType().equals(EmployeeContractRecords.REGULAR_CONTRACT) && contract.getEndTime() == null )) {
                        count--;
                        continue;
                    }
                    contract.setUserCode(userCode);
                    contract.setCreator(creator);
                    contract.setUpdator(creator);
                    if(contract.getId() > 0){
                        executor.update(sqlId("update"),contract);
                    }else{
                        executor.insert(sqlId("insert"),contract);
                    }
                }
                return executor.executeBatch()==count;
            }
        });
        if (!flag) {
            throw new IllegalArgumentException("批量合同记录出错");
        }
        return true;
    }

    /**
     * 删除合同记录
     * @update: 2014-05-28 10:05:05 保存操作记录
     * @param delIds
     * @param employee
     */
    public void deleteContracts(String delIds, Employee employee) {
        if(StringUtils.isBlank(delIds)) {
            return;
        }
        //先保存操作记录
        String[] ids = AttachHelper.getAttachIds(delIds);
        if(ids == null || ids.length == 0) {
            return;
        }
        for(String id : ids) {
            saveDelEmployeeOperateHistory(Integer.valueOf(id), employee);
        }
        Map<String,Object> delMap = new HashMap<String, Object>();
        delMap.put("delIds", ids);
        this.delete(sqlId("delCs"),delMap);
    }

    /**
     * 插入员工合同记录
     * @since: 2014-05-28 10:02:51
     * @param contractRecordses
     * @param userCode
     * @param emp
     */
    private void insertEmployeeContracts(EmployeeContractRecords[] contractRecordses, int userCode, Employee emp) {
        if(contractRecordses == null || contractRecordses.length == 0) {
            return;
        }
        //先保存操作记录
        for(EmployeeContractRecords cr : contractRecordses) {
            cr.setUserCode(userCode);
            cr.setUpdator(emp.getUserCode());
            cr.setUpdateName(emp.getUserName());
            saveEmployeeOperateHistory(cr);
        }
        //新增数据
        this.batchInsert(contractRecordses, userCode, emp.getUserCode());
    }

    /**
     * 更新员工合同记录，并保存操作记录
     * @since: 2014-05-28 10:02:35
     * @param vEmployeeDetails
     * @param userCode
     * @param emp
     */
    public void udpateEmployeeContracts(VEmployeeDetails vEmployeeDetails, int userCode, Employee emp) {
        //删除旧的
        this.deleteContracts(vEmployeeDetails.getDelId(), emp);
        //插入新的
        this.insertEmployeeContracts(vEmployeeDetails.getContracts(), userCode, emp);
    }

}

package com.gusi.empTransfer.helper;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeChangeRecords;
import com.gusi.empTransfer.common.EmpTransferConstants;
import com.gusi.empTransfer.dto.ApprovalParameter;
import com.gusi.empTransfer.dto.EmpTransferOrg;
import com.gusi.empTransfer.dto.VEmployeeTransfer;
import com.gusi.empTransfer.model.EmployeeTransfer;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.model.Privilege;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: fdj
 * @Since: 2014-02-25 13:22
 * @Description: 员工转调辅助类
 */
@Component
public class EmployeeTransferHelper {

    /**
     * 根据不同转调类型初始化值
     * @param employeeTransfer
     */
    public static void init(EmployeeTransfer employeeTransfer) {
        String transferType = employeeTransfer.getTransferType();
        switch (transferType) {
            case EmpTransferConstants.ZJB_CODE:
                initZjbZlbTransfer(employeeTransfer);
                break;
            case EmpTransferConstants.ZLB_CODE:
                initZjbZlbTransfer(employeeTransfer);
                break;
            case EmpTransferConstants.XFXS_CODE:
                initXfxsTransfer(employeeTransfer);
                break;
            case EmpTransferConstants.DLXM_CODE:
                initDlxmHtTransfer(employeeTransfer);
                break;
            case EmpTransferConstants.HT_CODE:
                initDlxmHtTransfer(employeeTransfer);
                break;
            default:
                initDlxmHtTransfer(employeeTransfer);
                break;
        }
    }

    /**
     * 代理项目、后台初始化转调记录
     * @param employeeTransfer
     */
    public static void initDlxmHtTransfer(EmployeeTransfer employeeTransfer) {
        employeeTransfer.setFangyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_1);
        employeeTransfer.setKeyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_1);
    }

    /**
     * 新房销售初始化转调记录
     * @param employeeTransfer
     */
    public static void initXfxsTransfer(EmployeeTransfer employeeTransfer) {
        initOldToNew(employeeTransfer);
        employeeTransfer.setFangyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_1);
        employeeTransfer.setKeyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_0);
    }

    /**
     * 将旧的属性设置到新的对象属性上
     * @param employeeTransfer
     */
    public static void initOldToNew(EmployeeTransfer employeeTransfer) {
        employeeTransfer.setNewLevelId(employeeTransfer.getOldLevelId());
        employeeTransfer.setNewPositionId(employeeTransfer.getOldPositionId());
        employeeTransfer.setNewTitle(employeeTransfer.getOldTitle());
    }

    /**
     * 租赁、中介初始化转调记录
     * @param employeeTransfer
     */
    public static void initZjbZlbTransfer(EmployeeTransfer employeeTransfer) {
        initOldToNew(employeeTransfer);
        if(employeeTransfer.getTransferState() == EmpTransferConstants.EMPLOYEE_TRANSFER_STATE_1) {
            employeeTransfer.setFangyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_1);
            employeeTransfer.setKeyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_0);
        } else {
            employeeTransfer.setFangyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_0);
            employeeTransfer.setKeyuanStatus(EmpTransferConstants.EMPLOYEE_TRANSFER_STATUS_0);
        }
    }

    /**
     * 根据转调EmployeeTransfer的id,实例化一条异动记录
     * @return
     */
    public static EmployeeChangeRecords initChangeRecord(VEmployeeTransfer employeeTransfer) {
        EmployeeChangeRecords employeeChangeRecords = new EmployeeChangeRecords();
        employeeChangeRecords.setUserCode(employeeTransfer.getUserCode());
        employeeChangeRecords.setChangeType(EmployeeChangeRecords.CHANGE_TYPE_RELEVEL);
        employeeChangeRecords.setNewOrgId(employeeTransfer.getNewOrgId());
        employeeChangeRecords.setNewPositionId(employeeTransfer.getNewPositionId());
        employeeChangeRecords.setNewSerialId(employeeTransfer.getNewSerialId());
        employeeChangeRecords.setNewTitleId(employeeTransfer.getNewTitleId());
        employeeChangeRecords.setNewLevelId(employeeTransfer.getNewLevelId());
        employeeChangeRecords.setNewTitleLevelFullName(employeeTransfer.getNewTitle());
        employeeChangeRecords.setCreator(EmpTransferConstants.GUANLIYUAN_USERCODE);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        employeeChangeRecords.setActiveDate(c.getTime());
        return employeeChangeRecords;
    }

    /**
     * 获取审批参数
     * @param employeeTransfer
     * @return
     */
    public static ApprovalParameter initApprovalParam(VEmployeeTransfer employeeTransfer) {
        String type = employeeTransfer.getTransferType();
        ApprovalParameter approvalParameter = new ApprovalParameter();
        approvalParameter.setId(employeeTransfer.getId());
        approvalParameter.setCreator(employeeTransfer.getCreator());
        approvalParameter.setHtStatus(type.equals(EmpTransferConstants.HT_CODE));
        approvalParameter.setHtOrDlxmStatus(type.equals(EmpTransferConstants.HT_CODE) || type.equals(EmpTransferConstants.DLXM_CODE));
        approvalParameter.setTransferState(employeeTransfer.getTransferState());
        approvalParameter.setFangyuanStatus(employeeTransfer.getFangyuanStatus());
        approvalParameter.setKeyuanStatus(employeeTransfer.getKeyuanStatus());
        return approvalParameter;
    }

    /**
     * 拼接字符串("_")
     * @param a
     * @param b
     * @return
     */
    public static String splicePrivilege(int a, String b) {
        return spliceString(String.valueOf(a), EmpTransferConstants.DEFAULT_PREFIX, b);
    }

    /**
     * 连接字符串
     * @param a
     * @param b
     * @param c
     * @return a+b+c
     */
    public static String spliceString(String a, String b, String c) {
        return a + b + c;
    }

    /**
     * 根据任务名称返回审批状态
     * @param key
     * @return
     */
    public static int initApprovalStatus(String key) {
        switch (key) {
            case Constants.TASK_DEF_KEY_EXPORT_MANAGER_APPROVE:
                return EmpTransferConstants.STATUS_1;
            case Constants.TASK_DEF_KEY_EXPORT_SUPERVISOR_APPROVE:
                return EmpTransferConstants.STATUS_1;
            case Constants.TASK_DEF_KEY_EXPORT_DIRECTOR_APPROVE:
                return EmpTransferConstants.STATUS_1;
            case Constants.TASK_DEF_KEY_IMPORT_SUPERVISOR_APPROVE:
                return EmpTransferConstants.STATUS_1;
            case Constants.TASK_DEF_KEY_HR_APPROVE:
                return EmpTransferConstants.STATUS_5;
            case Constants.TASK_DEF_KEY_HOUSE_HANDOVER:
                return EmpTransferConstants.STATUS_2;
            case Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER:
                return EmpTransferConstants.STATUS_2;
            case Constants.TASK_DEF_KEY_MODIFY_APPLY:
                return EmpTransferConstants.STATUS_4;
            default:
                return EmpTransferConstants.STATUS_D;
        }
    }

    /**
     * 获取审批权限集合
     * @param employee
     * @return
     */
    public static HashMap<String, Privilege> getPrivilegeMapWithOrgId(Employee employee, List<EmpTransferOrg> partitionOrgs) {
        //获得员工权限
        HashMap<String, Privilege> privilegeMap = employee.getPrivilegeMap();
        HashMap<String, Privilege> tempPrivilegeMap = new HashMap<>();
        Iterator<Map.Entry<String, Privilege>> it = privilegeMap.entrySet().iterator();
        Map.Entry<String, Privilege> entry;
        Privilege p = new Privilege();
        while (it.hasNext()) {
            entry = it.next();
            p.setPrivilegeUrl(entry.getValue().getPrivilegeUrl());
            //员工所在部门权限拼接
            tempPrivilegeMap.put(employee.getOrgId() + "_" + entry.getKey(), p);
            //员工兼职部门权限拼装
            for (EmpTransferOrg o : partitionOrgs) {
                tempPrivilegeMap.put(o.getId() + "_" + entry.getKey(), p);
            }
        }
        return tempPrivilegeMap;
    }

    /**
     * 获取审批权限用,隔开
     * @param employee
     * @return
     */
    public static String getPrivilegeStrWithOrgId(Employee employee, List<EmpTransferOrg> partitionOrgs) {
        HashMap<String, Privilege> tempPrivilegeMap = EmployeeTransferHelper.getPrivilegeMapWithOrgId(employee,partitionOrgs);
        if(tempPrivilegeMap == null || tempPrivilegeMap.size() == 0) {
            return "";
        }
        Iterator<Map.Entry<String, Privilege>> it = tempPrivilegeMap.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            //员工所在部门权限拼接
            sb.append(it.next().getKey() + ',');

        }
        return sb.append(Constants.PRIVILEGE_URL_HR_APPROVE).toString();
    }

    /**
     * 用于sql查询
     * @param employee
     * @return
     */
    public static String getSelectPrivileges(Employee employee, List<EmpTransferOrg> partitionOrgs) {
        return "''" + EmployeeTransferHelper.getPrivilegeStrWithOrgId(employee,partitionOrgs).replace(",","'',''")+ "''";
    }

}

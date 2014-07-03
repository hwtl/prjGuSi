package com.gusi.boms.helper;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationPhone;
import com.dooioo.plus.oms.dyEnums.Range;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 
 * @ClassName: OrganizationHelper 
 * @Description: 组织部门辅助类
 * @author fdj
 * @date 2013-5-22 下午2:19:51
 */
public class OrganizationHelper {

	/**
	 * 根据,或空格获得字符串数组
	 * @param strs
	 * @return
	 */
	public static String[] getStrArray(String strs) {
		if (!StringUtils.isBlank(strs)) {
			if (strs.contains(",")) {
				return strs.split(",");
			} else {
				return strs.split(" ");
			}
		}
		return new String[] { "0" };
	}

    /**
     * 获取权限数据范围的枚举值
     * @param employee 员工对象
     * @author jerry.hu
     * @return 数据范围的枚举
     */
    public static Range getRange(Employee employee,String privilegeValue){
        switch (PrivilegeHelper.getDataScope(employee.getPrivileges(),privilegeValue)){
            case 1:
                return Range.Self;
            case 10:
                return Range.PartitionOrganization;
            case 100:
                return Range.Company;
            default:
                return Range.Self;
        }
    }

    /**
     * 根据不同状态获得不同按钮
     * @param org
     * @param employee
     * @return
     */
    public static String getOperateStr(Organization org, Employee employee) {
        StringBuilder sb = new StringBuilder();
        if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_ORG_ADD)) {
            sb.append("<a href=\"/organization/"+ org.getId() +"/edit\" class=\"btnOpH24 h24Silver in_block ml_5\">编辑</a>");
        }
        switch (org.getStatus()) {
            case 1:
                if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.HRM_EMPLOYEE_ADD)) {
                    sb.append("<a href=\"/employee/" + org.getId() + "/add\" class=\"btnOpH24 h24Silver in_block ml_5\">新增员工</a>");
                }
                if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_ORG_SWITCH)) {
                    sb.append("<a href=\"javascript:;\" orgId=\"" + org.getId() + "\" method=\"stop\" orgName=\"" + org.getOrgName() + "\"  url=\"/employee/list?keyWords=" + org.getOrgName() + "\" class=\"btnOpH24 h24Silver in_block ml_5 js_operateOrgStepOne\">暂停</a>");
                    sb.append("<a href=\"javascript:;\" orgId=\"" + org.getId() + "\" method=\"close\" orgName=\"" + org.getOrgName() + "\"  url=\"/employee/list?keyWords=" + org.getOrgName() + "\" class=\"btnOpH24 h24Red in_block ml_5 js_operateOrgStepOne\">停用</a>");
                }
                return sb.toString();
            case 2:
            	 if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.HRM_EMPLOYEE_ADD)) {
                     sb.append("<a href=\"/employee/" + org.getId() + "/add\" class=\"btnOpH24 h24Silver in_block ml_5\">新增员工</a>");
                 }
            case -1:
                if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_ORG_SWITCH)) {
                    sb.append("<a href=\"javascript:;\" orgId=\"" + org.getId() + "\" method=\"open\" orgName=\"" + org.getOrgName() + "\" class=\"btnOpH24 h24Green in_block ml_5 js_openOrgStepOne\">启用</a>");
                }
                return sb.toString();
            case 0:
                if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_ORG_SWITCH)) {
                    sb.append("<a href=\"javascript:;\" orgId=\"" + org.getId() + "\" method=\"open\" orgName=\"" + org.getOrgName() + "\" class=\"btnOpH24 h24Green in_block ml_5 js_openOrgStepOne\">启用</a>");
                    sb.append("<a href=\"javascript:;\" orgId=\"" + org.getId() + "\" method=\"close\" orgName=\"" + org.getOrgName() + "\"  url=\"/employee/list?keyWords=" + org.getOrgName() + "\" class=\"btnOpH24 h24Red in_block ml_5 js_operateOrgStepOne\">停用</a>");
                }
                return sb.toString();
        }
        return "";
    }

    /**
     * 设置组织电话
     * @param org
     * @param orgPhoneList
     * @return
     */
    public static Organization setOrgPhone(Organization org, List<OrganizationPhone> orgPhoneList) {
        if(orgPhoneList != null && orgPhoneList.size() > 0) {
            org.setOrgPhone(orgPhoneList.get(0).getPhone());
        }
        return org;
    }

}

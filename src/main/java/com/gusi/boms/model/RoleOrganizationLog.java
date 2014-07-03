package com.gusi.boms.model;

import java.util.Date;

/**
 * 权限 - 部门与角色关系
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class RoleOrganizationLog extends RoleOrganization{

	private static final long serialVersionUID = -1668477749739374401L;
    private String roleIdStr;
    private int creator;
    private Date createTime;
    private String operateType;

    public String getRoleIdStr() {
        return roleIdStr;
    }

    public void setRoleIdStr(String roleIdStr) {
        this.roleIdStr = roleIdStr;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Override
    public String toString() {
        return "RoleOrganizationLog{" +
                "roleIdStr='" + roleIdStr + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", operateType='" + operateType + '\'' +
                '}';
    }
}
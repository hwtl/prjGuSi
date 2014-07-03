package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-19 15:56)
 * Description:角色日志记录
 */
public class RoleLog extends Role{
    private static final long serialVersionUID = -2189086078220716028L;

    private int roleId;
    private String operateType;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}

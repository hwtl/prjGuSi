package com.gusi.boms.model;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-25 15:56)
 * Description:角色岗位日志记录
 */
public class RolePositionLog extends RolePosition{

    private static final long serialVersionUID = 321694490089297947L;
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
        return "RolePositionLog{" +
                "roleIdStr='" + roleIdStr + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", operateType='" + operateType + '\'' +
                '}';
    }
}
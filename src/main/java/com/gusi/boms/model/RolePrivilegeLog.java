package com.gusi.boms.model;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-25 15:56)
 * Description:角色权限配置的日志记录实体类
 */
public class RolePrivilegeLog extends RolePrivilege{
    private static final long serialVersionUID = -7682248197152137924L;
    private String privilegeIdStr;
    private String dataPrivilegeStr;
    private int creator;
    private Date createTime;
    private String operateType;

    public String getPrivilegeIdStr() {
        return privilegeIdStr;
    }

    public void setPrivilegeIdStr(String privilegeIdStr) {
        this.privilegeIdStr = privilegeIdStr;
    }

    public String getDataPrivilegeStr() {
        return dataPrivilegeStr;
    }

    public void setDataPrivilegeStr(String dataPrivilegeStr) {
        this.dataPrivilegeStr = dataPrivilegeStr;
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
        return "RolePrivilegeLog{" +
                "privilegeIdStr='" + privilegeIdStr + '\'' +
                ", dataPrivilegeStr='" + dataPrivilegeStr + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", operateType='" + operateType + '\'' +
                '}';
    }
}
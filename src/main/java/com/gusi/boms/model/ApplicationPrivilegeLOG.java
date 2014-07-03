package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-19 16:37)
 * Description:权限日志记录
 */
public class ApplicationPrivilegeLOG extends ApplicationPrivilege {
    private static final long serialVersionUID = 1928651873450024784L;
    private int privilegeId;
    private int creator;
    private String operateType;

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}

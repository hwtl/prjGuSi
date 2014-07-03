package com.gusi.boms.model;

import java.io.Serializable;

/**
 * 权限 - 员工与角色关系
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class RoleEmployee implements Serializable{
	private static final long serialVersionUID = -8851007398683321511L;

	private int userCode;
    private int roleId;

    public int getUserCode() {
        return userCode;
    }
    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public RoleEmployee(){
    	
    }
    public RoleEmployee(int userCode,int roleId){
    	this.userCode = userCode;
    	this.roleId = roleId;
    }
    
	@Override
	public String toString() {
		return "RoleEmployee [userCode=" + userCode + ", roleId=" + roleId
				+ "]";
	}
}
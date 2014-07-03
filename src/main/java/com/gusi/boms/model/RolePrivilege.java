package com.gusi.boms.model;

import java.io.Serializable;

/**
 * 权限 - 角色权限关系表
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class RolePrivilege implements Serializable{
	private static final long serialVersionUID = 7005186446528312444L;

	private int roleId; //角色Id
    private int privilegeId; //权限Id
    private int dataPrivilege; //数据范围 1,10，50,100
    
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	public int getDataPrivilege() {
		return dataPrivilege;
	}
	public void setDataPrivilege(int dataPrivilege) {
		this.dataPrivilege = dataPrivilege;
	}
	public RolePrivilege(){}
	public RolePrivilege(int roleId,int privilegeId,int dataPrivilege){
		this.roleId = roleId;
		this.privilegeId = privilegeId;
		this.dataPrivilege = dataPrivilege;
	}
	@Override
	public String toString() {
		return "RolePrivilege [roleId=" + roleId + ", privilegeId="
				+ privilegeId + ", dataPrivilege=" + dataPrivilege + "]";
	}
}
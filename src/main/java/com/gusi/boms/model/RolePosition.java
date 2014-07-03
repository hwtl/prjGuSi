package com.gusi.boms.model;

import java.io.Serializable;

/**
 * 权限 - 岗位与角色关系
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class RolePosition implements Serializable{
	private static final long serialVersionUID = 3979213009478227545L;
	private int positionId;
    private int roleId;

    public int getPositionId() {
        return positionId;
    }
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public RolePosition(){
    	
    }
    public RolePosition(int positionId,int roleId){
    	this.positionId = positionId;
    	this.roleId = roleId;
    }
    
	@Override
	public String toString() {
		return "RolePosition [positionId=" + positionId + ", roleId=" + roleId
				+ "]";
	}
}
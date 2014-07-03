package com.gusi.boms.model;

import java.io.Serializable;

/**
 * 权限 - 部门与角色关系
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class RoleOrganization implements Serializable{

	/**  
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	 */ 
	private static final long serialVersionUID = -1668477749739374401L;
	private int orgId;
    private int roleId;

    public int getOrgId() {
        return orgId;
    }
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public RoleOrganization(){
    	
    }
    public RoleOrganization(int orgId,int roleId){
    	this.orgId = orgId;
    	this.roleId = roleId;
    }
    
	@Override
	public String toString() {
		return "RoleOrganization [orgId=" + orgId + ", roleId=" + roleId
				+ "]";
	}
}
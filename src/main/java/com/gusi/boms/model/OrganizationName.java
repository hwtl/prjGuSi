package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * 
* @ClassName: OrganizationName 
* @Description: OrganizationName
* @author fdj
* @date 2013-4-11 下午1:50:03
 */
public class OrganizationName extends BaseEntity {
	private static final long serialVersionUID = -7700444865461372695L;
	
	private int orgId;	//组织Id
	private String py;	//组织名称拼音首字母大写
	
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	@Override
	public String toString() {
		return "OrganizationName [orgId=" + orgId + ", py=" + py + "]";
	}
	
}
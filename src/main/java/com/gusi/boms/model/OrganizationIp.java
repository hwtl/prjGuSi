package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * 
* @ClassName: OrganizationIp 
* @Description: 组织部门ip实体类
* @author fdj
* @date 2013-4-11 下午1:50:55
 */
public class OrganizationIp extends BaseEntity {
	private static final long serialVersionUID = -1214305012496759908L;

	private int orgId;

    private String ipPrefix;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getIpPrefix() {
        return ipPrefix;
    }

    public void setIpPrefix(String ipPrefix) {
        this.ipPrefix = ipPrefix;
    }

	@Override
	public String toString() {
		return "OrganizationIp [orgId=" + orgId + ", ipPrefix=" + ipPrefix
				+ "]";
	}
}
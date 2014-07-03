package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * 
* @ClassName: OrganizationPhone 
* @Description: 组织部门的电话实体类
* @author fdj
* @date 2013-4-10 下午2:22:49
 */
public class OrganizationPhone extends BaseEntity {
	private static final long serialVersionUID = -2749304540531725501L;

	private int id; //id

    private int orgId; //组织id

    private String phone; //电话号码

    private String line; //线路

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

	@Override
	public String toString() {
		return "OrganizationPhone [id=" + id + ", orgId=" + orgId + ", phone="
				+ phone + ", line=" + line + "]";
	}
}
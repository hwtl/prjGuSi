package com.gusi.boms.model;

import java.io.Serializable;

/**
 * 权限 - 具体权限
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class ApplicationPrivilege implements Serializable{
	private static final long serialVersionUID = -8549935003630708783L;
	private int id;
	private int appId;
	private String privilegeName;
	private String privilegeUrl;
    private String privilegeClass;
	
    private Boolean checked = false; //为应用提供的字段： 是否选中
    private int dataPrivilege = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getPrivilegeUrl() {
		return privilegeUrl;
	}
	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}

    public String getPrivilegeClass() {
        return privilegeClass;
    }

    public void setPrivilegeClass(String privilegeClass) {
        this.privilegeClass = privilegeClass;
    }

    public int getDataPrivilege() {
		return dataPrivilege;
	}
	public void setDataPrivilege(int dataPrivilege) {
		this.dataPrivilege = dataPrivilege;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

    @Override
    public String toString() {
        return "ApplicationPrivilege{" +
                "id=" + id +
                ", appId=" + appId +
                ", privilegeName='" + privilegeName + '\'' +
                ", privilegeUrl='" + privilegeUrl + '\'' +
                ", privilegeClass='" + privilegeClass + '\'' +
                ", checked=" + checked +
                ", dataPrivilege=" + dataPrivilege +
                '}';
    }
	
}
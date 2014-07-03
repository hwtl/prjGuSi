package com.gusi.boms.model;

import java.io.Serializable;

/**
 * 权限 - 系统
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class Application implements Serializable{
	private static final long serialVersionUID = 820996526673838120L;
	private int id; //主键
    private String applicationName; //系统名称
    private String applicationCode; //系统代号
    private int status;  //是否有效 1有效 0无效

    private Boolean checked = false; //为应用提供的字段： 是否选中
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", applicationName=" + applicationName
				+ ", applicationCode=" + applicationCode + ", status=" + status
				+ ", checked=" + checked + "]";
	}
}
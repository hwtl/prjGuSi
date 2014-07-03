package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限 - 系统权限
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class ApplicationEmployeeAccess implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)  
	 */ 
	private static final long serialVersionUID = 2412559384502654187L;
	private int id;
	private int userCode;
	private int appId;
	private Date createTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public ApplicationEmployeeAccess(){}
	public ApplicationEmployeeAccess(int userCode,int appId){
		this.userCode = userCode;
		this.appId = appId;
	}
	

	@Override
	public String toString() {
		return "ApplicationEmployeeAccess [id=" + id + ", userCode=" + userCode
				+ ", appId=" + appId + ", createTime=" + createTime+"]";
	}
}
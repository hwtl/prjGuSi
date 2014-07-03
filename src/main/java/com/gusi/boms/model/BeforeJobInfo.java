package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author "liuhui" 
 * @since 2.7.4
 * @createAt 2014-2-20 下午4:45:51
 * <p>
 *  员工入职之前的报到信息等
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
public class BeforeJobInfo implements Serializable{
	private static final long serialVersionUID = 2872148281744888392L;

	private Integer id;
	private Integer userCode;
	private Date predictRegisterDate;
	private Date createAt;
	private int status;
	public BeforeJobInfo() {
		super();
	}
	public BeforeJobInfo(Integer userCode, Date predictRegisterDate) {
		super();
		this.userCode = userCode;
		this.predictRegisterDate = predictRegisterDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserCode() {
		return userCode;
	}
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	public Date getPredictRegisterDate() {
		return predictRegisterDate;
	}
	public void setPredictRegisterDate(Date predictRegisterDate) {
		this.predictRegisterDate = predictRegisterDate;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BeforeJobInfo [id=" + id + ", userCode=" + userCode
				+ ", predictRegisterDate=" + predictRegisterDate
				+ ", createAt=" + createAt + ", status=" + status + "]";
	}
	
}



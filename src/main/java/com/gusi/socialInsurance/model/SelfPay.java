package com.gusi.socialInsurance.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: fdj
 * @Since: 2014-06-04 17:47
 * @Description: 自缴信息表
 */
public class SelfPay {
	private int id;
	private int userCode;
	private String userName;
	private Integer orgId;
	private String orgName;
	private Integer paymentStatus;
	private Integer paymentBase;
	private double paymentMoney;
	private double companyMoney;
	private double personalMoney;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payBeginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payEndDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date extraBeginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date extraEndDate;
	private Integer changeTypeId;

	private Integer status;
	private Integer sort;
	private Date createTime;
	private int creator;
	private Date updateTime;
	private Integer updator;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(Integer paymentBase) {
		this.paymentBase = paymentBase;
	}

	public double getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(double paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public double getCompanyMoney() {
		return companyMoney;
	}

	public void setCompanyMoney(double companyMoney) {
		this.companyMoney = companyMoney;
	}

	public double getPersonalMoney() {
		return personalMoney;
	}

	public void setPersonalMoney(double personalMoney) {
		this.personalMoney = personalMoney;
	}

	public Date getPayBeginDate() {
		return payBeginDate;
	}

	public void setPayBeginDate(Date payBeginDate) {
		this.payBeginDate = payBeginDate;
	}

	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public Date getExtraBeginDate() {
		return extraBeginDate;
	}

	public void setExtraBeginDate(Date extraBeginDate) {
		this.extraBeginDate = extraBeginDate;
	}

	public Date getExtraEndDate() {
		return extraEndDate;
	}

	public void setExtraEndDate(Date extraEndDate) {
		this.extraEndDate = extraEndDate;
	}

	public Integer getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(Integer changeTypeId) {
		this.changeTypeId = changeTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdator() {
		return updator;
	}

	public void setUpdator(Integer updator) {
		this.updator = updator;
	}

	@Override
	public String toString() {
		return "SelfPay [id=" + id + ", userCode=" + userCode + ", userName="
				+ userName + ", orgId=" + orgId + ", orgName=" + orgName
				+ ", paymentStatus=" + paymentStatus + ", paymentBase="
				+ paymentBase + ", paymentMoney=" + paymentMoney
				+ ", companyMoney=" + companyMoney + ", personalMoney="
				+ personalMoney + ", payBeginDate=" + payBeginDate
				+ ", payEndDate=" + payEndDate + ", extraBeginDate="
				+ extraBeginDate + ", extraEndDate=" + extraEndDate
				+ ", changeTypeId=" + changeTypeId + ", status=" + status
				+ ", sort=" + sort + ", createTime=" + createTime
				+ ", creator=" + creator + ", updateTime=" + updateTime
				+ ", updator=" + updator + "]";
	}
}

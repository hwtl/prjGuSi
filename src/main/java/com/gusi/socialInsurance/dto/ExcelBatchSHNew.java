package com.gusi.socialInsurance.dto;


/**
 * @Author: Alex
 * @Since: 2014-06-16 09:15
 * @Description: ExcelBatchSHNew
 */
public class ExcelBatchSHNew {
	private int userCode;
	private String userName;
	private String orgName;
	private String newJoinDate;
	private String extraBeginDate;
	private String extraEndDate;
	private String credentialsNo;
	private String censusName;
	private String serialName;
	private String titleLevelDegree;
	private int paymentBase;
	private String applyStatus;
	private String failureReason;
	private String comment;

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getNewJoinDate() {
		return newJoinDate;
	}

	public void setNewJoinDate(String newJoinDate) {
		this.newJoinDate = newJoinDate;
	}

	public String getExtraBeginDate() {
		return extraBeginDate;
	}

	public void setExtraBeginDate(String extraBeginDate) {
		this.extraBeginDate = extraBeginDate;
	}

	public String getExtraEndDate() {
		return extraEndDate;
	}

	public void setExtraEndDate(String extraEndDate) {
		this.extraEndDate = extraEndDate;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

	public String getCensusName() {
		return censusName;
	}

	public void setCensusName(String censusName) {
		this.censusName = censusName;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getTitleLevelDegree() {
		return titleLevelDegree;
	}

	public void setTitleLevelDegree(String titleLevelDegree) {
		this.titleLevelDegree = titleLevelDegree;
	}

	public int getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(int paymentBase) {
		this.paymentBase = paymentBase;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

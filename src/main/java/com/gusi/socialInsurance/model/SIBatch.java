/**
 * 
 */
package com.gusi.socialInsurance.model;

import java.util.Date;

import com.gusi.boms.model.BaseModel;

/**
 * @author Alex Yu
 * @Created 2014年6月5日下午3:34:22
 */
public class SIBatch extends BaseModel {
	private int id;
	private String batchId;
	private int userCode;
	private String userName;
	private String sex;
	private Date newJoinDate;
	private Date leaveDate;
	private int orgId;
	private String orgName;
	private int levelId;
	private String serialName;
	private String titleLevelDegree;
	private String credentialsNo;
	private Integer censusId;
	private String censusName;
	private String censusAddr;
	private int paymentTypeId;
	private int paymentBase;
	private int applyStatus;
	private String comment;
	private String failureReason;
	private Date extraBeginDate;
	private Date extraEndDate;
	private int contractEnd;
	private Date requireDate;
	private int status;
	private int sort;

	/**
	 * 空的构造方法
	 * 
	 * @since: 2014-06-13 09:45:15
	 */
	public SIBatch() {
	}

	/**
	 * 带参构造方法
	 * 
	 * @since: 2014-06-13 09:45:25
	 * @param userCode
	 */
	public SIBatch(int userCode, int paymentTypeId) {
		this.userCode = userCode;
		this.paymentTypeId = paymentTypeId;
	}

	/**
	 * 带参构造方法
	 * 
	 * @since: 2014-06-13 09:45:25
	 * @param userCode
	 */
	public SIBatch(int userCode, int paymentTypeId, int paymentBase) {
		this.userCode = userCode;
		this.paymentTypeId = paymentTypeId;
		this.paymentBase = paymentBase;
	}

	/**
	 * 带参构造方法
	 * 
	 * @param userCode
	 * @param paymentTypeId
	 * @param paymentBase
	 * @param extraBeginDate
	 * @param extraEndDate
	 */
	public SIBatch(int userCode, int paymentTypeId, int paymentBase,
			Date extraBeginDate, Date extraEndDate) {
		this.userCode = userCode;
		this.paymentTypeId = paymentTypeId;
		this.paymentBase = paymentBase;
		this.extraBeginDate = extraBeginDate;
		this.extraEndDate = extraEndDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getNewJoinDate() {
		return newJoinDate;
	}

	public void setNewJoinDate(Date newJoinDate) {
		this.newJoinDate = newJoinDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
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

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

    public Integer getCensusId() {
        return censusId;
    }

    public void setCensusId(Integer censusId) {
        this.censusId = censusId;
    }

    public String getCensusName() {
		return censusName;
	}

	public void setCensusName(String censusName) {
		this.censusName = censusName;
	}

	public String getCensusAddr() {
		return censusAddr;
	}

	public void setCensusAddr(String censusAddr) {
		this.censusAddr = censusAddr;
	}

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public int getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(int paymentBase) {
		this.paymentBase = paymentBase;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
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

	public Date getRequireDate() {
		return requireDate;
	}

	public void setRequireDate(Date requireDate) {
		this.requireDate = requireDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getContractEnd() {
		return contractEnd;
	}

	public void setContractEnd(int contractEnd) {
		this.contractEnd = contractEnd;
	}

	@Override
	public String toString() {
		return "SIBatch{" + "batchId='" + batchId + '\'' + ", userCode="
				+ userCode + ", userName='" + userName + '\'' + ", sex='" + sex
				+ '\'' + ", newJoinDate=" + newJoinDate + ", leaveDate="
				+ leaveDate + ", orgId=" + orgId + ", orgName='" + orgName
				+ '\'' + ", levelId=" + levelId + ", serialName='" + serialName
				+ '\'' + ", titleLevelDegree='" + titleLevelDegree + '\''
				+ ", credentialsNo='" + credentialsNo + '\'' + ", censusId="
				+ censusId + ", censusName='" + censusName + '\''
				+ ", censusAddr='" + censusAddr + '\'' + ", paymentTypeId="
				+ paymentTypeId + ", paymentBase=" + paymentBase
				+ ", applyStatus=" + applyStatus + ", comment='" + comment
				+ '\'' + ", failureReason='" + failureReason + '\''
				+ ", extraBeginDate=" + extraBeginDate + ", extraEndDate="
				+ extraEndDate + ", contractEnd=" + contractEnd
				+ ", requireDate=" + requireDate + ", status=" + status
				+ ", sort=" + sort + '}';
	}
}

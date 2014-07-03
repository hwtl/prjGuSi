package com.gusi.socialInsurance.dto;


/**
 * @Author: Alex
 * @Since: 2014-06-16 09:48
 * @Description: ExcelBatchSHQuit
 */
public class ExcelBatchSHQuit {
	private int userCode;
	private String userName;
	private String orgName;
	private String newJoinDate;
	private String leaveDate;
	private String credentialsNo;
	private String censusAddr;

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

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

	public String getCensusAddr() {
		return censusAddr;
	}

	public void setCensusAddr(String censusAddr) {
		this.censusAddr = censusAddr;
	}
}

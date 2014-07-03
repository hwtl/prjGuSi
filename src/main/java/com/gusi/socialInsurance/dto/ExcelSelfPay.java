/**
 * 
 */
package com.gusi.socialInsurance.dto;

/**
 * @author Alex Yu
 * @Created 2014年6月5日下午3:25:38
 */
public class ExcelSelfPay {
	private int userCode;
	private String userName;
	private String orgName;
	private int paymentBase;
	private float paymentMoney;
	private float companyMoney;
	private float personalMoney;
	private String payBeginDate;
	private String payEndDate;
	private String extraBeginDate;
	private String extraEndDate;
	private String createTime;

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

	public int getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(int paymentBase) {
		this.paymentBase = paymentBase;
	}

	public float getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(float paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	public float getCompanyMoney() {
		return companyMoney;
	}

	public void setCompanyMoney(float companyMoney) {
		this.companyMoney = companyMoney;
	}

	public float getPersonalMoney() {
		return personalMoney;
	}

	public void setPersonalMoney(float personalMoney) {
		this.personalMoney = personalMoney;
	}

	public String getPayBeginDate() {
		return payBeginDate;
	}

	public void setPayBeginDate(String payBeginDate) {
		this.payBeginDate = payBeginDate;
	}

	public String getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(String payEndDate) {
		this.payEndDate = payEndDate;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
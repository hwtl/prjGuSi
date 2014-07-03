/**
 * 
 */
package com.gusi.socialInsurance.dto;

import com.gusi.socialInsurance.model.SelfPay;

/**
 * @author Alex Yu
 * @Created 2014年6月18日下午2:11:50
 */
public class SelfPayWithLocation extends SelfPay {

	private Integer paymentLocationId;
	private String credentialsNo;

	public Integer getPaymentLocationId() {
		return paymentLocationId;
	}

	public void setPaymentLocationId(Integer paymentLocationId) {
		this.paymentLocationId = paymentLocationId;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

	@Override
	public String toString() {
		return "SelfPayWithLocation [paymentLocationId=" + paymentLocationId
				+ ", credentialsNo=" + credentialsNo + ", getId()=" + getId()
				+ ", getUserCode()=" + getUserCode() + ", getUserName()="
				+ getUserName() + ", getOrgId()=" + getOrgId()
				+ ", getOrgName()=" + getOrgName() + ", getPaymentStatus()="
				+ getPaymentStatus() + ", getPaymentBase()=" + getPaymentBase()
				+ ", getPaymentMoney()=" + getPaymentMoney()
				+ ", getCompanyMoney()=" + getCompanyMoney()
				+ ", getPersonalMoney()=" + getPersonalMoney()
				+ ", getPayBeginDate()=" + getPayBeginDate()
				+ ", getPayEndDate()=" + getPayEndDate()
				+ ", getExtraBeginDate()=" + getExtraBeginDate()
				+ ", getExtraEndDate()=" + getExtraEndDate()
				+ ", getChangeTypeId()=" + getChangeTypeId() + ", getStatus()="
				+ getStatus() + ", getSort()=" + getSort()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreator()="
				+ getCreator() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdator()=" + getUpdator() + "]";
	}
}

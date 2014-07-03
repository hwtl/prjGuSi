package com.gusi.socialInsurance.dto;

/**
 * @Author: fdj
 * @Since: 2014-06-13 18:05
 * @Description: ExcelBatchNBNew
 */
public class ExcelBatchNBNew {
    private int userCode;
    private String orgName;
    private String userName;
    private String sex;
    private String censusName;
    private String censusAddr;
    private String credentialsNo;
    private String companyPhone;
    private String paymentDate;
    private int paymentBase;
    private String requireDate;
    private String comment;
    private String applyStatus;
    private String failureReason;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public int getPaymentBase() {
        return paymentBase;
    }

    public void setPaymentBase(int paymentBase) {
        this.paymentBase = paymentBase;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(String requireDate) {
        this.requireDate = requireDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}

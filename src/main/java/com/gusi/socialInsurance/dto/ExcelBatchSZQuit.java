package com.gusi.socialInsurance.dto;

/**
 * @Author: fdj
 * @Since: 2014-06-16 10:09
 * @Description: ExcelBatchSZQuit
 */
public class ExcelBatchSZQuit {
    private String workCity;
    private String hopePayCity;
    private String nowPayCity;
    private String orgName;
    private int userCode;
    private String userName;
    private String credentialsNo;
    private String leaveReason;
    private String lastWorkDate;
    private String paymentDate;
    private String commnet;

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getHopePayCity() {
        return hopePayCity;
    }

    public void setHopePayCity(String hopePayCity) {
        this.hopePayCity = hopePayCity;
    }

    public String getNowPayCity() {
        return nowPayCity;
    }

    public void setNowPayCity(String nowPayCity) {
        this.nowPayCity = nowPayCity;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getLastWorkDate() {
        return lastWorkDate;
    }

    public void setLastWorkDate(String lastWorkDate) {
        this.lastWorkDate = lastWorkDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }

    @Override
    public String toString() {
        return "ExcelBatchSZQuit{" +
                "workCity='" + workCity + '\'' +
                ", hopePayCity='" + hopePayCity + '\'' +
                ", nowPayCity='" + nowPayCity + '\'' +
                ", orgName='" + orgName + '\'' +
                ", userCode=" + userCode +
                ", userName='" + userName + '\'' +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", lastWorkDate='" + lastWorkDate + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", commnet='" + commnet + '\'' +
                '}';
    }
}

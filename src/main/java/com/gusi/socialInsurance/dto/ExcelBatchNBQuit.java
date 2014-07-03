package com.gusi.socialInsurance.dto;

/**
 * @Author: fdj
 * @Since: 2014-06-16 10:30
 * @Description: ExcelBatchNBQuit
 */
public class ExcelBatchNBQuit {
    private int userCode;
    private String userName;
    private String credentialsNo;
    private String paymentDate;

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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "ExcelBatchNBQuit{" +
                "userCode=" + userCode +
                ", userName='" + userName + '\'' +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}

package com.gusi.socialInsurance.model;

import com.gusi.boms.model.BaseModel;

import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-06-12 15:32
 * @Description: 批次信息表
 */
public class SIBatchInfo extends BaseModel {

    /**
     * 名单未拉
     */
    public static final int GENERATE_LIST_CAN = 1;
    /**
     * 名单已拉
     */
    public static final int GENERATE_LIST_NOT = 0;

    /**
     * 批次未确认
     */
    public static final int CONFIRM_STATUS_NOT = 0;
    /**
     * 批次已确认
     */
    public static final int CONFIRM_STATUS_OK = 1;
    /**
     * 批次不可以确认
     */
    public static final int CONFIRM_STATUS_BAN = 2;

    /**
     * 状态正常
     */
    public static final int STATUS_NORMAL = 1;
    /**
     * 状态删除
     */
    public static final int STATUS_DELETE = 0;

    private int id;
    private String batchId;
    private String previousBatchId;
    private int paymentLocationId;
    private int changeTypeId;
    private int paymentYear;
    private int paymentMonth;
    private int paymentDay;
    private Date paymentDate;
    private int generateList; //0已拉，1未拉
    private int confirmStatus; //确认标识（0：未确认，1已确认，2不可以确认）
    private Integer confirmUser;
    private Date confirmTime;
    private int status;

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

    public String getPreviousBatchId() {
        return previousBatchId;
    }

    public void setPreviousBatchId(String previousBatchId) {
        this.previousBatchId = previousBatchId;
    }

    public int getPaymentLocationId() {
        return paymentLocationId;
    }

    public void setPaymentLocationId(int paymentLocationId) {
        this.paymentLocationId = paymentLocationId;
    }

    public int getChangeTypeId() {
        return changeTypeId;
    }

    public void setChangeTypeId(int changeTypeId) {
        this.changeTypeId = changeTypeId;
    }

    public int getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(int paymentYear) {
        this.paymentYear = paymentYear;
    }

    public int getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(int paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getGenerateList() {
        return generateList;
    }

    public void setGenerateList(int generateList) {
        this.generateList = generateList;
    }

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Integer getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Integer confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SIBatchInfo{" +
                "batchId='" + batchId + '\'' +
                ", previousBatchId='" + previousBatchId + '\'' +
                ", paymentLocationId=" + paymentLocationId +
                ", changeTypeId=" + changeTypeId +
                ", paymentYear=" + paymentYear +
                ", paymentMonth=" + paymentMonth +
                ", paymentDay=" + paymentDay +
                ", paymentDate=" + paymentDate +
                ", generateList=" + generateList +
                ", confirmStatus=" + confirmStatus +
                ", confirmUser=" + confirmUser +
                ", confirmTime=" + confirmTime +
                ", status=" + status +
                '}';
    }
}

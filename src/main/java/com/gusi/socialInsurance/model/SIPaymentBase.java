package com.gusi.socialInsurance.model;

import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-06-12 09:44
 * @Description: SIPaymentBase
 */
public class SIPaymentBase extends SIBaseType {
    private int paymentBase;
    private int selfPayFlag;
    private Date deleteTime;
    private Integer deleteUser;

    public int getPaymentBase() {
        return paymentBase;
    }

    public void setPaymentBase(int paymentBase) {
        this.paymentBase = paymentBase;
    }

    public int getSelfPayFlag() {
        return selfPayFlag;
    }

    public void setSelfPayFlag(int selfPayFlag) {
        this.selfPayFlag = selfPayFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }

    @Override
    public String toString() {
        return "SIPaymentBase{" +
                "paymentBase=" + paymentBase +
                ", selfPayFlag=" + selfPayFlag +
                ", deleteTime=" + deleteTime +
                ", deleteUser=" + deleteUser +
                '}';
    }
}

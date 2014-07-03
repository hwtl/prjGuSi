package com.gusi.boms.dto;

import java.io.Serializable;

/**
 * @Author: fdj
 * @Since: 2014-04-23 15:52
 * @Description: EmpToSendMsg
 */
public class EmpToSendMsg implements Serializable {
    private static final long serialVersionUID = 9120267816488415430L;

    private int userCode;
    private String userName;
    private int orgId;
    private String orgName;
    private String mobilePhone;

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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return "EmpToSendMsg{" +
                "userCode=" + userCode +
                ", userName='" + userName + '\'' +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}

package com.gusi.app.model;

import com.gusi.boms.common.Configuration;
import com.dooioo.plus.util.TextUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-18 下午5:25
 * @Description: 通讯录员工类
 * To change this template use File | Settings | File Templates.
 */
public class AppEmployee {
    private int userCode;
    private int orgId;
    private String userNameCn;
    private String sex;
    private String mobilePhone;
    private String orgName;
    private String positionName;
    private String userPicture;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getUserNameCn() {
        return userNameCn;
    }

    public void setUserNameCn(String userNameCn) {
        this.userNameCn = userNameCn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getUserPicture() {
        return TextUtil.format(Configuration.getInstance().getUserPicturePath(), String.valueOf(userCode));
    }

    @Override
    public String toString() {
        return "AppEmployee{" +
                "userCode=" + userCode +
                ", userNameCn='" + userNameCn + '\'' +
                ", sex='" + sex + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", orgName='" + orgName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", userPicture='" + userPicture + '\'' +
                '}';
    }
}

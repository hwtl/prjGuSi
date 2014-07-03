package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-22 19:12)
 * Description:员工的入职来源
 */
public class Recruitment extends BaseEntity {
    private static final long serialVersionUID = -8425117994235088673L;
    private int userCode;     //入职人
    private String comeFrom;  //渠道来源
    private Integer recommendUser; //推荐人
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public Integer getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(Integer recommendUser) {
        this.recommendUser = recommendUser;
    }

    @Override
    public String toString() {
        return "Recruitment{" +
                "userCode=" + userCode +
                ", comeFrom='" + comeFrom + '\'' +
                ", recommendUser=" + recommendUser +
                ", remark='" + remark + '\'' +
                '}';
    }
}

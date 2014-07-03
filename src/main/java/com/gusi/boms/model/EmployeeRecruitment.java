package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-8-27 上午11:41
 * @Description: 员工招聘渠道类
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeRecruitment {
    private int userCode;
    private String comeFrom;
    private int recommendUser;

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

    public int getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(int recommendUser) {
        this.recommendUser = recommendUser;
    }

    @Override
    public String toString() {
        return "EmployeeRecruitment{" +
                "userCode=" + userCode +
                ", comeFrom='" + comeFrom + '\'' +
                ", recommendUser=" + recommendUser +
                '}';
    }
}

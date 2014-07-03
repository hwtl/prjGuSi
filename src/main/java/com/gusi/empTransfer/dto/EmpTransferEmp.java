package com.gusi.empTransfer.dto;


import com.gusi.boms.model.EmployeeBaseInfor;

/**
 * @Author: fdj
 * @Since: 2014-02-20 13:38
 * @Description: 转调员工类
 */
public class EmpTransferEmp extends EmployeeBaseInfor {
    private String orgName;
    private String levelDegree;
    private String levelName;
    private String titleDegree;
    private int titleId;

    private String longName;
    private String titleLevelName;

    public String getTitleLevelName() {
        return this.getTitleDegree() + "-" + this.getLevelDegree() + this.getLevelName();
    }

    public void setTitleLevelName(String titleLevelName) {
        this.titleLevelName = titleLevelName;
    }

    public String getLongName() {
        return this.getUserNameCn() + "(" + this.getUserCode() + ")" + "-" + this.getOrgName();
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLevelDegree() {
        return levelDegree;
    }

    public void setLevelDegree(String levelDegree) {
        this.levelDegree = levelDegree;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getTitleDegree() {
        return titleDegree;
    }

    public void setTitleDegree(String titleDegree) {
        this.titleDegree = titleDegree;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    @Override
    public String toString() {
        return "EmpTransferEmp{" +
                "orgName='" + orgName + '\'' +
                ", levelDegree='" + levelDegree + '\'' +
                ", levelName='" + levelName + '\'' +
                ", titleDegree='" + titleDegree + '\'' +
                ", titleId=" + titleId +
                ", longName='" + longName + '\'' +
                ", titleLevelName='" + titleLevelName + '\'' +
                '}';
    }
}

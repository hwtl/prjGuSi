package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 * User: hw
 * Date: 12-10-15
 * Time: 下午6:01
 * 组织架构的具有搜索功能的结果集
 */
public class ApiTree {
    private String branchCode;
    private String branchName;
    private String pId;
    private String orgType;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public String toString() {
        return "ApiTree{" +
                "branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", pId='" + pId + '\'' +
                ", orgType='" + orgType + '\'' +
                '}';
    }
}

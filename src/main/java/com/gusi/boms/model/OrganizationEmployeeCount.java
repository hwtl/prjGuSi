package com.gusi.boms.model;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-11-27 下午1:23
 * @Description: 组织扩展表，保存该组织下允许的最大人数等等
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationEmployeeCount implements Serializable {

    private static final long serialVersionUID = 894812702480246597L;
    private int orgId;
    private Integer maxCount;
    private Date endTime;
    private int status;
    private Integer creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;
    private int limitType; //1手动修改区域限制，没有结束时间        2暂停分行时，系统自动添加的区域限制人数，3个月的限制时间

    private Integer branchId;
    private String branchName;

    public String getEndTimeStr() {
        return DateFormatUtils.format(endTime, "yyyy-MM-dd");
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getLimitType() {
        return limitType;
    }

    public void setLimitType(int limitType) {
        this.limitType = limitType;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrganizationEmployeeCount{" +
                "orgId=" + orgId +
                ", maxCount=" + maxCount +
                ", endTime=" + endTime +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                '}';
    }
}

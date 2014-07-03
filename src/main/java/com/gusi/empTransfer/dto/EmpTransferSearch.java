package com.gusi.empTransfer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-02-24 14:57
 * @Description: 转调记录查询对象
 */
public class EmpTransferSearch implements Serializable {
    private static final long serialVersionUID = 786612499064314510L;
    private String ydType;
    private Integer oldOrgId;
    private Integer newOrgId;
    private String newOrgName;
    private Date createTime;
    private Date createStartTime;
    private Date createEndTime;
    private Integer approvalStatus;
    private String keyword;

    public String getNewOrgName() {
        return newOrgName;
    }

    public void setNewOrgName(String newOrgName) {
        this.newOrgName = newOrgName;
    }

    public Integer getNewOrgId() {
        return newOrgId;
    }

    public void setNewOrgId(Integer newOrgId) {
        this.newOrgId = newOrgId;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getYdType() {
        return ydType;
    }

    public void setYdType(String ydType) {
        this.ydType = ydType;
    }

    public Integer getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(Integer oldOrgId) {
        this.oldOrgId = oldOrgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "EmpTransferSearch{" +
                "ydType='" + ydType + '\'' +
                ", oldOrgId=" + oldOrgId +
                ", newOrgId=" + newOrgId +
                ", newOrgName='" + newOrgName + '\'' +
                ", createTime=" + createTime +
                ", createStartTime=" + createStartTime +
                ", createEndTime=" + createEndTime +
                ", approvalStatus=" + approvalStatus +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}

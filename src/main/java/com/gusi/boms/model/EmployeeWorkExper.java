package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-5-28 下午04:13:33 ) 
  * 工作经历
 */
public class EmployeeWorkExper implements Serializable{
	private static final long serialVersionUID = 5844039891359861628L;

	private int id;

    private Integer userCode;

    private Date entryDate;

    private Date departureDate;

    private String companyName;

    private String positionName;

    private String leaveReason;

    private String prover;
    private String proverTel; //证明人电话

    private Integer creator;

    private Date createTime;

    private Integer updator;

    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getProver() {
        return prover;
    }

    public void setProver(String prover) {
        this.prover = prover;
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

    public String getProverTel() {
        return proverTel;
    }

    public void setProverTel(String proverTel) {
        this.proverTel = proverTel;
    }

    @Override
    public String toString() {
        return "EmployeeWorkExper{" +
                "id=" + id +
                ", userCode=" + userCode +
                ", entryDate=" + entryDate +
                ", departureDate=" + departureDate +
                ", companyName='" + companyName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", prover='" + prover + '\'' +
                ", proverTel='" + proverTel + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                '}';
    }
    
}
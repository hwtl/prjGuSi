package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeTrain  implements Serializable{
	private static final long serialVersionUID = -1263898381829687289L;

	private Integer trainId;

    private int userCode;

    private Date startDate;

    private Date endDate;

    private String trainName;

    private String certificate;

    private Integer creator;

    private Date createTime;

    private Integer updator;

    private Date updateTime;

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }
    

    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
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
		return "EmployeeTrain [trainId=" + trainId + ", userCode=" + userCode
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", trainName=" + trainName + ", certifiCate=" + certificate
				+ "]";
	}
    
}
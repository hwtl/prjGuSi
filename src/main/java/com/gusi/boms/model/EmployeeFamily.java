package com.gusi.boms.model;

import java.util.Date;

public class EmployeeFamily {
    private int familyId;

    private int userCode;

    private String alias;

    private String name;

    private Date bornDate;

    private String job;

    private String workplace;

    private String phone;

    private Integer creator;

    private Date createTime;

    private Integer updator;

    private Date updateTime;

   

    public int getFamilyId() {
		return familyId;
	}

	public void setFamilyId(int familyId) {
		this.familyId = familyId;
	}

	public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
		return "EmployeeFamily [familyId=" + familyId + ", userCode="
				+ userCode + ", alias=" + alias + ", name=" + name
				+ ", bornDate=" + bornDate + ", job=" + job + ", workplace="
				+ workplace + ", phone=" + phone + ", creator=" + creator
				+ ", createTime=" + createTime + ", updator=" + updator
				+ ", updateTime=" + updateTime + "]";
	}
    
}
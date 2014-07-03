package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-4-9 下午01:13:44 ) 
 */
public class EmployeeEducationExper extends BaseEntity{
	private static final long serialVersionUID = 5120702004745887744L;

	private Integer eduId;//教育经历ID
    private int userCode;//工号
    private String degree;//学位
    private Date startDate;//起始日期
    private Date endDate;//结束日期
    private String schoolName;//学校名称
    private String department;//部门
    private Integer graduation;//是否毕业
    private String eduLevel;//学历等级
    private Integer creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;
    private Integer universityId;
    private String universityName;
    private Integer great;

    public Integer getGreat() {
        return great;
    }

    public void setGreat(Integer great) {
        this.great = great;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

	public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(String eduLevel) {
        this.eduLevel = eduLevel;
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
        return "EmployeeEducationExper{" +
                "eduId=" + eduId +
                ", userCode=" + userCode +
                ", degree='" + degree + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", schoolName='" + schoolName + '\'' +
                ", department='" + department + '\'' +
                ", graduation=" + graduation +
                ", eduLevel='" + eduLevel + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                ", universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                ", great=" + great +
                '}';
    }
    
}
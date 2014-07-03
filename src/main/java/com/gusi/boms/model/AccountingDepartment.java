package com.gusi.boms.model;

import java.util.Date;

import com.dooioo.web.model.BaseEntity;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-4-9 上午11:59:23 ) 
 */
public class AccountingDepartment extends BaseEntity {
	private static final long serialVersionUID = 6476368884432960290L;

	private int id;

    private int orgId;//组织ID

    private String orgName;

    private Integer creator;

    private Date createTime;

    private Date startTime;

    private Date endTime;

    private Integer manager;//负责人
    
    private String managerName;
    private String creatorName;

    private String orgType;

    private String orgLongCode;

    private Long parentId;

    private Integer orgLevel;

    private Integer locIndex;

    private String orgCode;

    private int status;

    public AccountingDepartment() {
		super();
	}

	public AccountingDepartment(int orgId, String orgName, Integer manager,
			 Date startTime, Date endTime,Integer creator) {
		super();
		this.orgId = orgId;
		this.orgName = orgName;
		this.manager = manager;
		this.creator = creator;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "AccountingDepartment [id=" + id + ", orgId=" + orgId
				+ ", orgName=" + orgName + ", creator=" + creator
				+ ", createTime=" + createTime + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", manager=" + manager
				+ ", orgType=" + orgType + ", orgLongCode=" + orgLongCode
				+ ", parentId=" + parentId + ", orgLevel=" + orgLevel
				+ ", locIndex=" + locIndex + ", orgCode=" + orgCode
				+ ", status=" + status + "]";
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgLongCode() {
        return orgLongCode;
    }

    public void setOrgLongCode(String orgLongCode) {
        this.orgLongCode = orgLongCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Integer orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Integer getLocIndex() {
        return locIndex;
    }

    public void setLocIndex(Integer locIndex) {
        this.locIndex = locIndex;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

    
}
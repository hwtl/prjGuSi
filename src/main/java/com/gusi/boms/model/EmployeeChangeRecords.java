package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-4-9 上午11:46:22 ) 
 */
public class EmployeeChangeRecords extends BaseEntity{
	private static final long serialVersionUID = -2115776383665353865L;
	
	/**
	 * 任命
	 */
	public static final String CHANGE_TYPE_APPOINT="任命";
	/**
	 *  转正 
	 */
	public static final String CHANGE_TYPE_REGULAR="转正";
	/**
	 *  离职
	 */
	public static final String CHANGE_TYPE_LEAVE="离职";
	/**
	 * 回聘
	 */
	public static final String CHANGE_TYPE_REHIRE="回聘";
	/**
	 *  晋升
	 */
	public static final String CHANGE_TYPE_PROMOTION="晋升";
	/**
	 *  转调
	 */
	public static final String CHANGE_TYPE_RELEVEL="转调";
	/**
	 *  降职
	 */
	public static final String CHANGE_TYPE_DEMOTION="降职";
    /**
     * 数据处理
     * @since: 2014-06-16 13:24:31
     */
    public static final String CHANGE_TYPE_DEAL_DATA="数据处理";

	private Integer changeId; //异动ID

    private int userCode; //工号

    private String changeType; //异动类型  ： 转正，离职，回聘，晋升，转调，降职

    private String oldStatus; //当前员工状态 ： 正式，试用期，申请离职，离职等

    private String newStatus;//更改后员工状态 ： 正式，试用期，申请离职，离职等

    private String oldOrgName; //当前所在组织名称

    private String newOrgName; //更新后所在组织名称

    private Integer oldOrgId; //当前所在组织ID

    private Integer newOrgId; //geng新后的组织ID
    private Integer oldPositionId;//当前的职位编号

    private Integer newPositionId;//更新后的职位编号

    private String oldPositionName; //当前的职位名称

    private String newPositionName;//更新后的职位名称

    private Integer oldSerialId; //当前的职系ID

    private Integer newSerialId; //更新后的职系名称

    private String oldSerialName; //当前的职系名称

    private String newSerialName; //更新后的职系名称

    private Integer oldTitleDegree; //当前的职等职位

    private Integer newTitleDegree; //更新后的职等职位

    private String oldLevelRank;

    private String newLevelRank;

    private String oldTitleLevelFullName;

    private String newTitleLevelFullName;

    private Integer oldTitleId;

    private Integer newTitleId;

    private Integer oldLevelId;

    private Integer newLevelId;

    private Date leaveDate;

    private String leaveType;

    private String leaveReason;

    private Date activeDate;

    private Integer status; //-2回滚， -1删除，0未生效，1已生效
    private Integer creator;
    private String creatorName;
    private Date createTime;

    private Date updateTime;

    private Integer updator;
    
    private String updatorName;//操作人名称
    
    private boolean canRollback;//可以回滚

	public boolean isCanRollback() {
		return canRollback;
	}

	public void setCanRollback(boolean canRollback) {
		this.canRollback = canRollback;
	}

	

	public String getUpdatorName() {
		return updatorName;
	}

	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

    public Integer getChangeId() {
		return changeId;
	}

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getOldOrgName() {
        return oldOrgName;
    }

    public void setOldOrgName(String oldOrgName) {
        this.oldOrgName = oldOrgName;
    }

    public String getNewOrgName() {
        return newOrgName;
    }

    public void setNewOrgName(String newOrgName) {
        this.newOrgName = newOrgName;
    }

    public Integer getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(Integer oldOrgId) {
        this.oldOrgId = oldOrgId;
    }

    public Integer getNewOrgId() {
        return newOrgId;
    }

    public void setNewOrgId(Integer newOrgId) {
        this.newOrgId = newOrgId;
    }
    public Integer getOldPositionId() {
        return oldPositionId;
    }

    public void setOldPositionId(Integer oldPositionId) {
        this.oldPositionId = oldPositionId;
    }

    public Integer getNewPositionId() {
        return newPositionId;
    }

    public void setNewPositionId(Integer newPositionId) {
        this.newPositionId = newPositionId;
    }

    public String getOldPositionName() {
        return oldPositionName;
    }

    public void setOldPositionName(String oldPositionName) {
        this.oldPositionName = oldPositionName;
    }

    public String getNewPositionName() {
        return newPositionName;
    }

    public void setNewPositionName(String newPositionName) {
        this.newPositionName = newPositionName;
    }

    public Integer getOldSerialId() {
        return oldSerialId;
    }

    public void setOldSerialId(Integer oldSerialId) {
        this.oldSerialId = oldSerialId;
    }

    public Integer getNewSerialId() {
        return newSerialId;
    }

    public void setNewSerialId(Integer newSerialId) {
        this.newSerialId = newSerialId;
    }

    public String getOldSerialName() {
        return oldSerialName;
    }

    public void setOldSerialName(String oldSerialName) {
        this.oldSerialName = oldSerialName;
    }

    public String getNewSerialName() {
        return newSerialName;
    }

    public void setNewSerialName(String newSerialName) {
        this.newSerialName = newSerialName;
    }

    public Integer getOldTitleDegree() {
        return oldTitleDegree;
    }

    public void setOldTitleDegree(Integer oldTitleDegree) {
        this.oldTitleDegree = oldTitleDegree;
    }

    public Integer getNewTitleDegree() {
        return newTitleDegree;
    }

    public void setNewTitleDegree(Integer newTitleDegree) {
        this.newTitleDegree = newTitleDegree;
    }

    public String getOldLevelRank() {
        return oldLevelRank;
    }

    public void setOldLevelRank(String oldLevelRank) {
        this.oldLevelRank = oldLevelRank;
    }

    public String getNewLevelRank() {
        return newLevelRank;
    }

    public void setNewLevelRank(String newLevelRank) {
        this.newLevelRank = newLevelRank;
    }

    public String getOldTitleLevelFullName() {
        return oldTitleLevelFullName;
    }

    public void setOldTitleLevelFullName(String oldTitleLevelFullName) {
        this.oldTitleLevelFullName = oldTitleLevelFullName;
    }

    public String getNewTitleLevelFullName() {
        return newTitleLevelFullName;
    }

    public void setNewTitleLevelFullName(String newTitleLevelFullName) {
        this.newTitleLevelFullName = newTitleLevelFullName;
    }

    public Integer getOldTitleId() {
        return oldTitleId;
    }

    public void setOldTitleId(Integer oldTitleId) {
        this.oldTitleId = oldTitleId;
    }

    public Integer getNewTitleId() {
        return newTitleId;
    }

    public void setNewTitleId(Integer newTitleId) {
        this.newTitleId = newTitleId;
    }

    public Integer getOldLevelId() {
        return oldLevelId;
    }

    public void setOldLevelId(Integer oldLevelId) {
        this.oldLevelId = oldLevelId;
    }

    public Integer getNewLevelId() {
        return newLevelId;
    }

    public void setNewLevelId(Integer newLevelId) {
        this.newLevelId = newLevelId;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }
    @Override
	public String toString() {
		return "EmployeeChangeRecords [changeId=" + changeId + ", userCode="
				+ userCode + ", changeType=" + changeType + ", oldStatus="
				+ oldStatus + ", newStatus=" + newStatus + ", oldOrgName="
				+ oldOrgName + ", newOrgName=" + newOrgName + ", oldOrgId="
				+ oldOrgId + ", newOrgId=" + newOrgId + ", oldPositionId=" + oldPositionId + ", newPositionId="
				+ newPositionId + ", oldPositionName=" + oldPositionName
				+ ", newPositionName=" + newPositionName + ", oldSerialId="
				+ oldSerialId + ", newSerialId=" + newSerialId
				+ ", oldSerialName=" + oldSerialName + ", newSerialName="
				+ newSerialName + ", oldTitleDegree=" + oldTitleDegree
				+ ", newTitleDegree=" + newTitleDegree + ", oldLevelRank="
				+ oldLevelRank + ", newLevelRank=" + newLevelRank
				+ ", oldTitleLevelFullName=" + oldTitleLevelFullName
				+ ", newTitleLevelFullName=" + newTitleLevelFullName
				+ ", oldTitleId=" + oldTitleId + ", newTitleId=" + newTitleId
				+ ", oldLevelId=" + oldLevelId + ", newLevelId=" + newLevelId
				+ ", leaveDate=" + leaveDate + ", leaveType=" + leaveType
				+ ", leaveReason=" + leaveReason + ", activeDate=" + activeDate
				+ ", status=" + status + ", creator=" + creator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", updator=" + updator + "]";
	}

	
}
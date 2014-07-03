package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/**
 * 
 * @ClassName: OrganizationHeaderHistory
 * @Description: 组织部门负责人历史记录实体类
 * @author fdj
 * @date 2013-4-10 下午2:43:34
 */
public class OrganizationHeaderHistory extends BaseEntity {
	private static final long serialVersionUID = 3670344748902919693L;

	private int id; // id

	private int orgId; // 组织id

	private int positionId; // 职位id

	private int userCode; // 工号

	private String userNameCn; // 用户中文名

	private String status; // 状态

	private Date startTime; // 开始时间

	private Date endTime; // 结束时间

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

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getUserNameCn() {
		return userNameCn;
	}

	public void setUserNameCn(String userNameCn) {
		this.userNameCn = userNameCn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public OrganizationHeaderHistory() {
		super();
	}

	public OrganizationHeaderHistory(int orgId, int positionId, int userCode,
			String userNameCn) {
		super();
		this.orgId = orgId;
		this.positionId = positionId;
		this.userCode = userCode;
		this.userNameCn = userNameCn;
	}

	@Override
	public String toString() {
		return "OrganizationHeaderHistory [id=" + id + ", orgId=" + orgId
				+ ", positionId=" + positionId + ", userCode=" + userCode
				+ ", userNameCn=" + userNameCn + ", status=" + status
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
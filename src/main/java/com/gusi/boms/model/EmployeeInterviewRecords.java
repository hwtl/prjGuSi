package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-6-3 上午10:06:48 ) 
  *离职交接记录
 */
public class EmployeeInterviewRecords implements Serializable {
	
	private static final long serialVersionUID = 8575595284743739750L;
	/**
	 * 离职类型 主动离职
	 */
	public static final String LEAVE_TYPE_OF_ZHUDONG="主动离职";
	/**
	 * 离职类型 辞退
	 */
	public static final String LEAVE_TYPE_OF_CITUI="辞退";
	private Integer id;
	/**
	 * 助理确认
	 */
	private Integer assistantChecked;
	/**
	 * 面谈者是否确认
	 */
	private Integer interviewerChecked;
	/**
	 * 进行离职面谈的人
	 */
	private Integer userCode;
	private Integer interviewer;//面谈者
	private Integer assistant;//面谈的助理
	/**
	 * 申请单创建人
	 */
	private Integer creator;
	private Date createAt;
	private Date updateAt;
	/**
	 * 移动记录的ID
	 */
	private Integer changeId;
	/**
	 * 离职类型
	 */
	private String leaveType;
	
	/**
	 * 默认1 代表此条记录有效，-1删除
	 *  0代表无效
	 * 	 */
	private Integer status;
	/**
	 * 离职日期
	 */
	private Date leaveDate;
	private int fangyuanStatus;//房友状态 1，已交接，2，归公盘
	private int keyuanStatus;//客源状态 1，已交接，2，归公盘
	/**
	 * 离职原因
	 */
	private String reasons;
	/**
	 * 离职原因
	 */
	private List<EmployeeLeaveReasons> leaveReasons;
	
	/**
	 * 所有离职原因拼接成的字符串
	 */
	private String reasonString;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssistantChecked() {
		return assistantChecked;
	}

	public void setAssistantChecked(Integer assistantChecked) {
		this.assistantChecked = assistantChecked;
	}

	public Integer getInterviewerChecked() {
		return interviewerChecked;
	}

	public void setInterviewerChecked(Integer interviewerChecked) {
		this.interviewerChecked = interviewerChecked;
	}

	public Integer getUserCode() {
		return userCode;
	}

	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}

	public Integer getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(Integer interviewer) {
		this.interviewer = interviewer;
	}

	public Integer getAssistant() {
		return assistant;
	}

	public void setAssistant(Integer assistant) {
		this.assistant = assistant;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getChangeId() {
		return changeId;
	}

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}



	public int getFangyuanStatus() {
		return fangyuanStatus;
	}

	public void setFangyuanStatus(int fangyuanStatus) {
		this.fangyuanStatus = fangyuanStatus;
	}

	public int getKeyuanStatus() {
		return keyuanStatus;
	}

	public void setKeyuanStatus(int keyuanStatus) {
		this.keyuanStatus = keyuanStatus;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public List<EmployeeLeaveReasons> getLeaveReasons() {
		return leaveReasons;
	}

	public void setLeaveReasons(List<EmployeeLeaveReasons> leaveReasons) {
		this.leaveReasons = leaveReasons;
	}

	public String getReasonString() {
		return reasonString;
	}

	public void setReasonString(String reasonString) {
		this.reasonString = reasonString;
	}

	@Override
	public String toString() {
		return "EmployeeInterviewRecords [id=" + id + ", assistantChecked="
				+ assistantChecked + ", interviewerChecked="
				+ interviewerChecked + ", userCode=" + userCode
				+ ", interviewer=" + interviewer + ", assistant=" + assistant
				+ ", creator=" + creator + ", createAt=" + createAt
				+ ", updateAt=" + updateAt + ", changeId=" + changeId
				+ ", leaveType=" + leaveType + ", status=" + status
				+ ", leaveDate=" + leaveDate + ", fangyouStatus="
				+ fangyuanStatus + ", keyuanStatus=" + keyuanStatus
				+ ", reasons=" + reasons + ", leaveReasons=" + leaveReasons
				+ ", reasonString=" + reasonString + "]";
	}

	
}
/**
 *
 */
package com.gusi.empTransfer.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工转调流程详情
 * 
 * @author Alex Yu
 * @Created 2014年3月4日上午9:20:42
 */
public class EmployeeTransferTaskStatus implements Serializable {
	private static final long serialVersionUID = -6580650843874523879L;

	private int id; // 业务Id
	/**
	 * 流程id
	 */
	private String processInsId;
	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * 任务定义Key
	 */
	private String taskDefKey;
	/**
	 * 任务定义Key
	 */
	private String taskName;
	/**
	 * 任务名称
	 */
	private String taskNameCn;
	/**
	 * 操作所需权限Url
	 */
	private String operateUrl;
	/**
	 * 处理人工号
	 */
	private String handlerCode;
	/**
	 * 处理人
	 */
	private String handlerName;

	private Date createTime;
    private Integer creator;
	private Date updateTime;
	private Integer updator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
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

	/**
	 * @return the processInsId
	 */
	public String getProcessInsId() {
		return processInsId;
	}

	/**
	 * @param processInsId
	 *            the processInsId to set
	 */
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskDefKey
	 */
	public String getTaskDefKey() {
		return taskDefKey;
	}

	/**
	 * @param taskDefKey
	 *            the taskDefKey to set
	 */
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskNameCn
	 */
	public String getTaskNameCn() {
		return taskNameCn;
	}

	/**
	 * @param taskNameCn
	 *            the taskNameCn to set
	 */
	public void setTaskNameCn(String taskNameCn) {
		this.taskNameCn = taskNameCn;
	}

	/**
	 * @return the operateUrl
	 */
	public String getOperateUrl() {
		return operateUrl;
	}

	/**
	 * @param operateUrl
	 *            the operateUrl to set
	 */
	public void setOperateUrl(String operateUrl) {
		this.operateUrl = operateUrl;
	}

	/**
	 * @return the handlerCode
	 */
	public String getHandlerCode() {
		return handlerCode;
	}

	/**
	 * @param handlerCode
	 *            the handlerCode to set
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * @return the handlerName
	 */
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * @param handlerName
	 *            the handlerName to set
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Override
	public String toString() {
		return "EmployeeTransferTaskStatus{" + "id=" + id + ", processInsId='"
				+ processInsId + '\'' + ", taskId='" + taskId + '\''
				+ ", taskName='" + taskName + '\'' + ", operateUrl='"
				+ operateUrl + '\'' + ", handlerName='" + handlerName + '\''
				+ '}';
	}
}

package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author "liuhui"
 * create At 2013-8-28 下午1:41:20
 *  黑名单记录
 */
public class EmployeeBlackRecords implements Serializable{
	private static final long serialVersionUID = -5647710148127715062L;

	private Integer id;
	private String serialNo;
	private Integer userCode;
	private int status; //-- 黑名单状态 0,未生效， 1，当前有效，-2已删除，-1已打回，3撤出黑名单，2 黑名单历史记录
	private Date createAt;
	private String userName;
	private String orgName;
	private String positionName;
	private Integer creator;
	private String creatorName;
	private Date updateAt;
	private Integer updator;
	private String reasons;
	private Integer attachId;//null代表没有附件可以担保撤销
	
	private Integer operator;//撤销黑名单的操作人
	private Integer guaranteeAttachId;//保证书附件ID
	private String revocation;//撤销原因
	
	private List<EmployeeBlackReasons> blackReasons;
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public List<EmployeeBlackReasons> getBlackReasons() {
		return blackReasons;
	}
	public void setBlackReasons(List<EmployeeBlackReasons> blackReasons) {
		this.blackReasons = blackReasons;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Integer getUserCode() {
		return userCode;
	}
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Integer getCreator() {
		return creator;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Integer getUpdator() {
		return updator;
	}
	public void setUpdator(Integer updator) {
		this.updator = updator;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public EmployeeBlackRecords() {
		super();
	}
	
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getGuaranteeAttachId() {
		return guaranteeAttachId;
	}
	public void setGuaranteeAttachId(Integer guaranteeAttachId) {
		this.guaranteeAttachId = guaranteeAttachId;
	}
	public String getRevocation() {
		return revocation;
	}
	public void setRevocation(String revocation) {
		this.revocation = revocation;
	}
	/**
	 * @param serialNo 序列号
	 * @param userCode 黑名单工号
	 * @param status 状态 
	 * @param creator 创建人
	 * @param attachId 附件ID
	 */
	public EmployeeBlackRecords(String serialNo, Integer userCode, int status,
			Integer creator, Integer attachId) {
		super();
		this.serialNo = serialNo;
		this.userCode = userCode;
		this.status = status;
		this.creator = creator;
		this.attachId = attachId;
	}
	@Override
	public String toString() {
		return "EmployeeBlackRecords [id=" + id + ", serialNo=" + serialNo
				+ ", userCode=" + userCode + ", status=" + status
				+ ", createAt=" + createAt + ", creator=" + creator
				+ ", updateAt=" + updateAt + ", updator=" + updator
				+ ", attachId=" + attachId + "]";
	}
	
	
}

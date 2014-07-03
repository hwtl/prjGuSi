package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeBlackReasons implements Serializable{
 private static final long serialVersionUID = 4425490800581868528L;
 private Integer id;
 private Integer blackId;
 private Integer reasonId;
 private String reason;
 private String description;
 private Date createAt;
	public EmployeeBlackReasons() {
		super();
	}
	public EmployeeBlackReasons(Integer reasonId,String reason,
			String description) {
		super();
		this.reason=reason;
		this.reasonId = reasonId;
		this.description = description;
	}
	
	public EmployeeBlackReasons(Integer blackId, Integer reasonId,String reason,
			String description) {
		super();
		this.blackId = blackId;
		this.reasonId = reasonId;
		this.description = description;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBlackId() {
		return blackId;
	}
	public void setBlackId(Integer blackId) {
		this.blackId = blackId;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "EmployeeBlackReasons [id=" + id + ", blackId=" + blackId
				+ ", reasonId=" + reasonId + ", description=" + description
				+ ", createAt=" + createAt + "]";
	}
 
}

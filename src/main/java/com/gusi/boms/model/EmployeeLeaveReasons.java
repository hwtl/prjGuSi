package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;



public class EmployeeLeaveReasons implements Serializable {
	private static final long serialVersionUID = 4332512032240793093L;
	private Integer id;
	/**
	 * 面谈表记录
	 */
	private Integer interviewId;
	private Integer mainReasonId;//主要离职原因
	private String mainReason;
   private Integer subReasonId;//离职原因子分类
   private String subReason;
   private String description;
   private Date createAt;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getInterviewId() {
			return interviewId;
		}
		public void setInterviewId(Integer interviewId) {
			this.interviewId = interviewId;
		}
		public Integer getMainReasonId() {
			return mainReasonId;
		}
		public void setMainReasonId(Integer mainReasonId) {
			this.mainReasonId = mainReasonId;
		}
		
		public String getMainReason() {
			return mainReason;
		}
		public void setMainReason(String mainReason) {
			this.mainReason = mainReason;
		}
		public Integer getSubReasonId() {
			return subReasonId;
		}
		public void setSubReasonId(Integer subReasonId) {
			this.subReasonId = subReasonId;
		}
		public String getSubReason() {
			return subReason;
		}
		public void setSubReason(String subReason) {
			this.subReason = subReason;
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
			return "EmployeeLeaveReasons [id=" + id + ", interviewId="
					+ interviewId + ", mainReasonId=" + mainReasonId
					+ ", mainResaon=" + mainReason + ", subReasonId="
					+ subReasonId + ", subReason=" + subReason
					+ ", description=" + description + ", createAt=" + createAt
					+ "]";
		}
   
}
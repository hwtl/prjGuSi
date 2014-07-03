package com.gusi.socialInsurance.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: fdj
 * @Since: 2014-06-05 11:09
 * @Description: 自缴列表查询条件类
 */
public class SelfPaySearch {

	private int pageSize;
	private int pageNo;

	private Integer paymentBase;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payBeginDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payBeginDateEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payEndDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payEndDateEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date extraBeginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date extraEndDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTimeStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTimeEnd;
	private Integer orgId;
	private String keyword;
	private Integer leave;
	private Integer paymentStatus;
	private Integer changeTypeId;
	private Integer status;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(Integer paymentBase) {
		this.paymentBase = paymentBase;
	}

	public Date getPayBeginDateStart() {
		return payBeginDateStart;
	}

	public void setPayBeginDateStart(Date payBeginDateStart) {
		this.payBeginDateStart = payBeginDateStart;
	}

	public Date getPayBeginDateEnd() {
		return payBeginDateEnd;
	}

	public void setPayBeginDateEnd(Date payBeginDateEnd) {
		this.payBeginDateEnd = payBeginDateEnd;
	}

	public Date getPayEndDateStart() {
		return payEndDateStart;
	}

	public void setPayEndDateStart(Date payEndDateStart) {
		this.payEndDateStart = payEndDateStart;
	}

	public Date getPayEndDateEnd() {
		return payEndDateEnd;
	}

	public void setPayEndDateEnd(Date payEndDateEnd) {
		this.payEndDateEnd = payEndDateEnd;
	}

	public Date getExtraBeginDate() {
		return extraBeginDate;
	}

	public void setExtraBeginDate(Date extraBeginDate) {
		this.extraBeginDate = extraBeginDate;
	}

	public Date getExtraEndDate() {
		return extraEndDate;
	}

	public void setExtraEndDate(Date extraEndDate) {
		this.extraEndDate = extraEndDate;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getLeave() {
		return leave;
	}

	public void setLeave(Integer leave) {
		this.leave = leave;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(Integer changeTypeId) {
		this.changeTypeId = changeTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SelfPaySearch [pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", paymentBase=" + paymentBase + ", payBeginDateStart="
				+ payBeginDateStart + ", payBeginDateEnd=" + payBeginDateEnd
				+ ", payEndDateStart=" + payEndDateStart + ", payEndDateEnd="
				+ payEndDateEnd + ", extraBeginDate=" + extraBeginDate
				+ ", extraEndDate=" + extraEndDate + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", orgId=" + orgId + ", keyword=" + keyword + ", leave="
				+ leave + ", paymentStatus=" + paymentStatus
				+ ", changeTypeId=" + changeTypeId + ", status=" + status + "]";
	}
}

package com.gusi.socialInsurance.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author: fdj
 * @Since: 2014-06-06 13:26
 * @Description: SIBaseInfoSearch
 */
public class SIBaseInfoSearch {

	private int pageSize;
	private int pageNo;

	private Integer paymentLocationId;
	private Integer orgId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginDateEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDateEnd;
	private Integer possessSocialCard;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date socialCardActivateDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date socialCardActivateDateEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyFormDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyFormDateEnd;
	private Integer applyForm;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date extraBeginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date extraEndDate;
	private String levelIds;
	private Float paymentBase;
	private Integer censusId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date newJoinDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date newJoinDateEnd;
	private String keyword;
	private Integer paymentStatus;
	private Integer leave;

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

	public Integer getPaymentLocationId() {
		return paymentLocationId;
	}

	public void setPaymentLocationId(Integer paymentLocationId) {
		this.paymentLocationId = paymentLocationId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Date getBeginDateStart() {
		return beginDateStart;
	}

	public void setBeginDateStart(Date beginDateStart) {
		this.beginDateStart = beginDateStart;
	}

	public Date getBeginDateEnd() {
		return beginDateEnd;
	}

	public void setBeginDateEnd(Date beginDateEnd) {
		this.beginDateEnd = beginDateEnd;
	}

	public Date getEndDateStart() {
		return endDateStart;
	}

	public void setEndDateStart(Date endDateStart) {
		this.endDateStart = endDateStart;
	}

	public Date getEndDateEnd() {
		return endDateEnd;
	}

	public void setEndDateEnd(Date endDateEnd) {
		this.endDateEnd = endDateEnd;
	}

	public Integer getPossessSocialCard() {
		return possessSocialCard;
	}

	public void setPossessSocialCard(Integer possessSocialCard) {
		this.possessSocialCard = possessSocialCard;
	}

	public Date getSocialCardActivateDateStart() {
		return socialCardActivateDateStart;
	}

	public void setSocialCardActivateDateStart(Date socialCardActivateDateStart) {
		this.socialCardActivateDateStart = socialCardActivateDateStart;
	}

	public Date getSocialCardActivateDateEnd() {
		return socialCardActivateDateEnd;
	}

	public void setSocialCardActivateDateEnd(Date socialCardActivateDateEnd) {
		this.socialCardActivateDateEnd = socialCardActivateDateEnd;
	}

	public Integer getApplyForm() {
		return applyForm;
	}

	public void setApplyForm(Integer applyForm) {
		this.applyForm = applyForm;
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

	public String getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(String levelIds) {
		this.levelIds = levelIds;
	}

	public Float getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(Float paymentBase) {
		this.paymentBase = paymentBase;
	}

	public Integer getCensusId() {
		return censusId;
	}

	public void setCensusId(Integer censusId) {
		this.censusId = censusId;
	}

	public Date getNewJoinDateStart() {
		return newJoinDateStart;
	}

	public void setNewJoinDateStart(Date newJoinDateStart) {
		this.newJoinDateStart = newJoinDateStart;
	}

	public Date getNewJoinDateEnd() {
		return newJoinDateEnd;
	}

	public void setNewJoinDateEnd(Date newJoinDateEnd) {
		this.newJoinDateEnd = newJoinDateEnd;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getLeave() {
		return leave;
	}

	public void setLeave(Integer leave) {
		this.leave = leave;
	}

    public Date getApplyFormDateStart() {
        return applyFormDateStart;
    }

    public void setApplyFormDateStart(Date applyFormDateStart) {
        this.applyFormDateStart = applyFormDateStart;
    }

    public Date getApplyFormDateEnd() {
        return applyFormDateEnd;
    }

    public void setApplyFormDateEnd(Date applyFormDateEnd) {
        this.applyFormDateEnd = applyFormDateEnd;
    }

    @Override
    public String toString() {
        return "SIBaseInfoSearch{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", paymentLocationId=" + paymentLocationId +
                ", orgId=" + orgId +
                ", beginDateStart=" + beginDateStart +
                ", beginDateEnd=" + beginDateEnd +
                ", endDateStart=" + endDateStart +
                ", endDateEnd=" + endDateEnd +
                ", possessSocialCard=" + possessSocialCard +
                ", socialCardActivateDateStart=" + socialCardActivateDateStart +
                ", socialCardActivateDateEnd=" + socialCardActivateDateEnd +
                ", applyFormDateStart=" + applyFormDateStart +
                ", applyFormDateEnd=" + applyFormDateEnd +
                ", applyForm=" + applyForm +
                ", extraBeginDate=" + extraBeginDate +
                ", extraEndDate=" + extraEndDate +
                ", levelIds='" + levelIds + '\'' +
                ", paymentBase=" + paymentBase +
                ", censusId=" + censusId +
                ", newJoinDateStart=" + newJoinDateStart +
                ", newJoinDateEnd=" + newJoinDateEnd +
                ", keyword='" + keyword + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", leave=" + leave +
                '}';
    }
}

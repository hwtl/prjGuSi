/**
 * 
 */
package com.gusi.socialInsurance.model;

import java.util.Date;

import com.gusi.boms.model.BaseModel;

/**
 * @author Alex Yu
 * @Created 2014年6月5日下午3:25:38
 */
public class SIBaseInfo extends BaseModel {
    private int id;
	private int userCode;
	private String userName;
	private int orgId;
	private String orgName;
	private Date newJoinDate;
	private String serialName;
	private String titleLevelDegree;
	private int levelId;
	private String credentialsNo;
	private Integer censusId;
	private String censusName;
	private int paymentStatus;
	private int paymentLocationId;
	private String paymentLocationName;
	private int paymentTypeId;
	private String paymentTypeName;
	private int paymentBase;
	private Date beginDate;
	private Date endDate;
	private Integer possessSocialCard;
	private Date socialCardActivateDate;
	private Integer applyForm;
    private Date applyFormDate;
	private Date extraBeginDate;
	private Date extraEndDate;
	private String comment;
	private String applyFormAttachUrl;
	private String attachUrl;
	private int status;
	private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Date getNewJoinDate() {
		return newJoinDate;
	}

	public void setNewJoinDate(Date newJoinDate) {
		this.newJoinDate = newJoinDate;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getTitleLevelDegree() {
		return titleLevelDegree;
	}

	public void setTitleLevelDegree(String titleLevelDegree) {
		this.titleLevelDegree = titleLevelDegree;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

    public Integer getCensusId() {
        return censusId;
    }

    public void setCensusId(Integer censusId) {
        this.censusId = censusId;
    }

    public String getCensusName() {
		return censusName;
	}

	public void setCensusName(String censusName) {
		this.censusName = censusName;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getPaymentLocationId() {
		return paymentLocationId;
	}

	public void setPaymentLocationId(int paymentLocationId) {
		this.paymentLocationId = paymentLocationId;
	}

	public String getPaymentLocationName() {
		return paymentLocationName;
	}

	public void setPaymentLocationName(String paymentLocationName) {
		this.paymentLocationName = paymentLocationName;
	}

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public int getPaymentBase() {
		return paymentBase;
	}

	public void setPaymentBase(int paymentBase) {
		this.paymentBase = paymentBase;
	}

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPossessSocialCard() {
        return possessSocialCard;
    }

    public void setPossessSocialCard(Integer possessSocialCard) {
        this.possessSocialCard = possessSocialCard;
    }

    public Date getSocialCardActivateDate() {
        return socialCardActivateDate;
    }

    public void setSocialCardActivateDate(Date socialCardActivateDate) {
        this.socialCardActivateDate = socialCardActivateDate;
    }

    public Integer getApplyForm() {
        return applyForm;
    }

    public void setApplyForm(Integer applyForm) {
        this.applyForm = applyForm;
    }

    public Date getApplyFormDate() {
        return applyFormDate;
    }

    public void setApplyFormDate(Date applyFormDate) {
        this.applyFormDate = applyFormDate;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getApplyFormAttachUrl() {
		return applyFormAttachUrl;
	}

	public void setApplyFormAttachUrl(String applyFormAttachUrl) {
		this.applyFormAttachUrl = applyFormAttachUrl;
	}

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "SIBaseInfo [userCode=" + userCode + ", userName=" + userName
				+ ", orgId=" + orgId + ", orgName=" + orgName
				+ ", newJoinDate=" + newJoinDate + ", serialName=" + serialName
				+ ", titleLevelDegree=" + titleLevelDegree + ", levelId="
				+ levelId + ", credentialsNo=" + credentialsNo + ", censusId="
				+ censusId + ", censusName=" + censusName + ", paymentStatus="
				+ paymentStatus + ", paymentLocationId=" + paymentLocationId
				+ ", paymentLocationName=" + paymentLocationName
				+ ", paymentTypeId=" + paymentTypeId + ", paymentTypeName="
				+ paymentTypeName + ", paymentBase=" + paymentBase
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", possessSocialCard=" + possessSocialCard
				+ ", socialCardActivateDate=" + socialCardActivateDate
				+ ", applyForm=" + applyForm + ", extraBeginDate="
				+ extraBeginDate + ", extraEndDate=" + extraEndDate
				+ ", comment=" + comment + ", applyFormAttachUrl="
				+ applyFormAttachUrl + ", attachUrl=" + attachUrl + ", status="
				+ status + ", sort=" + sort + ", getUserCode()="
				+ getUserCode() + ", getUserName()=" + getUserName()
				+ ", getOrgId()=" + getOrgId() + ", getOrgName()="
				+ getOrgName() + ", getNewJoinDate()=" + getNewJoinDate()
				+ ", getSerialName()=" + getSerialName()
				+ ", getTitleLevelDegree()=" + getTitleLevelDegree()
				+ ", getLevelId()=" + getLevelId() + ", getCredentialsNo()="
				+ getCredentialsNo() + ", getCensusId()=" + getCensusId()
				+ ", getCensusName()=" + getCensusName()
				+ ", getPaymentStatus()=" + getPaymentStatus()
				+ ", getPaymentLocationId()=" + getPaymentLocationId()
				+ ", getPaymentLocationName()=" + getPaymentLocationName()
				+ ", getPaymentTypeId()=" + getPaymentTypeId()
				+ ", getPaymentTypeName()=" + getPaymentTypeName()
				+ ", getPaymentBase()=" + getPaymentBase()
				+ ", getBeginDate()=" + getBeginDate() + ", getEndDate()="
				+ getEndDate() + ", getPossessSocialCard()="
				+ getPossessSocialCard() + ", getSocialCardActivateDate()="
				+ getSocialCardActivateDate() + ", getApplyForm()="
				+ getApplyForm() + ", getExtraBeginDate()="
				+ getExtraBeginDate() + ", getExtraEndDate()="
				+ getExtraEndDate() + ", getComment()=" + getComment()
				+ ", getApplyFormAttachUrl()=" + getApplyFormAttachUrl()
				+ ", getAttachUrl()=" + getAttachUrl() + ", getStatus()="
				+ getStatus() + ", getSort()=" + getSort() + ", getCreator()="
				+ getCreator() + ", getCreateTime()=" + getCreateTime()
				+ ", getUpdator()=" + getUpdator() + ", getUpdateTime()="
				+ getUpdateTime() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
package com.gusi.boms.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-09 10:34)
 * Description: 员工列表页展示实体
 */
public class VEmployeeBaseInfor extends  EmployeeBaseInfor {
    private static final long serialVersionUID = -2678165437009189072L;
    private String phone;   //电话号码
    private String titleName; //职等的名称
    private String orgName;
    private String areaName;//区域名称
    private String positionName;
    private String serialName;
    private String comeFromName;
    private String officePhone;
    private String levelFull;
    private Integer titleDegree;
    private String levelDegree;
    private String mobilePhone;
    private Integer archiveStatus;
    private boolean shielded;
    private int serialId;
    private int titleId;
    private int levelId;
    private Integer interviewId;//离职面谈记录Id
    private String registerStatus;
    private Integer currentBlackId;//当前可以回滚黑名单ID
    private int years;
    private String lastDegree;

    public Integer getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(Integer archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(String registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public Integer getCurrentBlackId() {
		return currentBlackId;
	}

	public void setCurrentBlackId(Integer currentBlackId) {
		this.currentBlackId = currentBlackId;
	}

	public String getComeFromName() {
        return comeFromName;
    }

    public void setComeFromName(String comeFromName) {
        this.comeFromName = comeFromName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Integer interviewId) {
		this.interviewId = interviewId;
	}

	public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getOrgName() {
        return StringUtils.isBlank(this.orgName) ? "" : this.orgName.replace("+","<br/>");
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPositionName() {
        return StringUtils.isBlank(this.positionName) ? "" : this.positionName.replace("+","<br/>");
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getLevelFull() {
        return levelFull;
    }

    public void setLevelFull(String levelFull) {
        this.levelFull = levelFull;
    }

    public Integer getTitleDegree() {
        return titleDegree;
    }

    public void setTitleDegree(Integer titleDegree) {
        this.titleDegree = titleDegree;
    }

    public String getLevelDegree() {
        return levelDegree;
    }

    public void setLevelDegree(String levelDegree) {
        this.levelDegree = levelDegree;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public boolean isShielded() {
        return shielded;
    }

    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "VEmployeeBaseInfor{" +
                "phone='" + phone + '\'' +
                ", titleName='" + titleName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", serialName='" + serialName + '\'' +
                ", comeFromName='" + comeFromName + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", levelFull='" + levelFull + '\'' +
                ", titleDegree=" + titleDegree +
                ", levelDegree='" + levelDegree + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", archiveStatus=" + archiveStatus +
                ", shielded=" + shielded +
                ", serialId=" + serialId +
                ", titleId=" + titleId +
                ", levelId=" + levelId +
                ", interviewId=" + interviewId +
                ", registerStatus='" + registerStatus + '\'' +
                ", currentBlackId=" + currentBlackId +
                ", years=" + years +
                ", lastDegree='" + lastDegree + '\'' +
                '}';
    }
}

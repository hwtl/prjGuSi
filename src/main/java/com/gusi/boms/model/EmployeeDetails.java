package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;
/**
 * @author "liuhui" 
 * @since 2.7.5
 * @createAt 2014-3-6 上午9:43:21
 * <p>
 *   
 *   1，为解决农历转换与显示问题。
 *      birthDay 如果是农历，则存放当时农历对应的公历日期。
 *      bornYear,bornMonth,bornDaytime 
 *      存放birthDay分解后的年，月，日，
 *      如果是农历， 则存放农历生日分解后的年，月，日，
 *      一年12个月，闰月序号不变，如，闰9月，month=9。
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
public class EmployeeDetails extends BaseEntity{
	private static final long serialVersionUID = 6089219496215178385L;

	private int userCode;//工号 主键
	private String userNameCn;//用户名
	private String userNameEn;//英文名
	private String sex;//性别
	private String credentialsNo;//身份证
	private String positionName;//职位名称
	private String orgName;//部门名称
    private Integer userHight; //高度
    private String bloodType; //血型类型  a ab o b
    private String bloodTypeValue;//血型值
    
    private Date bornDay; //出生日期，取自身份证日期
    //以下取自身份证日期 bornDay
    private Integer officialMonth;//官方出生月
    private Integer officialDay;//官方出生日
    private Integer officialYear;//官方出生年
    
    
    private Integer lunarCalendar; //是否农历 0=农历，null or 1=阳历
    private Date birthday;//生日，用于生日提醒与筛选
     //这三个字段值来自BirthDay，如果是农历的话，分别对应公历农历的年，月，日
    private Integer bornYear; //生日年 
    private Integer bornMonth; //生日月
    private Integer bornDayTime; //生日天
    
    
    private String constellation; //星座
    private String constellationValue;//星座值
    private Integer health;//健康状况
    private String maritalStatus;//婚姻状态
    private String maritalStatusValue;//婚姻状态的值

    private int showPhone;//显示号码  1=mobilePhone  2=alternatePhone,3=officePhone,4=familyTel,0=不选
    private String mobilePhone;//移动电话
    private String alternatePhone;//其他手机
    private String usedMailBox;//常用邮箱
    private String accountLocation;//户口地址

    private String familyAddress;//家庭地址

    private String familyTel;//家庭电话

    private String homeTown;//籍贯

    private String nation;//民族
    private String nationValue;
    private String currAddress;//现住址

    private String politicalBackGround;//政治背景

    private String politicalBackGroundValue;//政治背景值

    private String lastDegree;//最高学历
    private String lastDegreeValue;//最高学历值

    private String lastStudyType;//最高学历类型
    private String lastStudyTypeValue;//最高学历类型

    private Date workTime;//工作时间

    private String emergencyContacts;//紧急联络人

    private String emergencyContactsPhone;//紧急联络人电话

    private Integer creator;//创建人

    private Date createTime;

    private Integer status;//0 为未确认，6000待确认，-1未完善

    private Integer updator;

    private Date updateTime;

    private String officePhone;//办公电话



    private String familyAddressProvince; //家庭地址省
    private String provinceValue;
    private String familyAddressCity;//家庭地址城市
    private String cityValue;
    private String familyAddressDetail;//家庭地址详情
    private Integer hasChildren;//是否有孩子
    private Integer census;   //户籍  1：城镇户籍， 0：农业户籍
    private Integer enlist;  //是否从军 1：是，0：否

    private String rollbackReason;  //打回原因


    /**
     * @param userCode 工号
     * @param bornDay 出生年份
     * @param lunarCalendar 默认阳历
     * @param mobilePhone 手机
     * @param usedMailBox 默认工号@dooioo.com
     * @param creator
     * @param status 默认-1
     * @param birthday 生日
     * @param showPhone 默认1，显示mobilePhone
     */
    public EmployeeDetails(int userCode,Date bornDay,
			Integer lunarCalendar, String mobilePhone, String usedMailBox,
			Integer creator, Integer status, Date birthday, int showPhone) {
		super();
		this.userCode = userCode;
		this.bornDay = bornDay;
		this.lunarCalendar = lunarCalendar;
		this.mobilePhone = mobilePhone;
		this.usedMailBox = usedMailBox;
		this.creator = creator;
		this.status = status;
		this.birthday = birthday;
		this.showPhone = showPhone;
	}

	public EmployeeDetails() {
		super();
	}

    public String getRollbackReason() {
        return rollbackReason;
    }

    public void setRollbackReason(String rollbackReason) {
        this.rollbackReason = rollbackReason;
    }

    public String getNationValue() {
		return nationValue;
	}

	public void setNationValue(String nationValue) {
		this.nationValue = nationValue;
	}

	public String getConstellationValue() {
		return constellationValue;
	}

	public void setConstellationValue(String constellationValue) {
		this.constellationValue = constellationValue;
	}

	public String getProvinceValue() {
		return provinceValue;
	}

	public void setProvinceValue(String provinceValue) {
		this.provinceValue = provinceValue;
	}

	public String getCityValue() {
		return cityValue;
	}

	public void setCityValue(String cityValue) {
		this.cityValue = cityValue;
	}

	public String getUserNameEn() {
		return userNameEn;
	}

	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}

	public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {


        this.userCode = userCode;
    }

    public Integer getUserHight() {
        return userHight;
    }

    public void setUserHight(Integer userHight) {
        this.userHight = userHight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Date getBornDay() {
        return bornDay;
    }

    public void setBornDay(Date bornDay) {
        this.bornDay = bornDay;
    }

    public Integer getLunarCalendar() {
        return lunarCalendar;
    }

    public void setLunarCalendar(Integer lunarCalendar) {
        this.lunarCalendar = lunarCalendar;
    }

    public Integer getBornYear() {
        return bornYear;
    }

    public void setBornYear(Integer bornYear) {
        this.bornYear = bornYear;
    }

    public Integer getBornMonth() {
        return bornMonth;
    }

    public void setBornMonth(Integer bornMonth) {
        this.bornMonth = bornMonth;
    }

    public Integer getBornDayTime() {
        return bornDayTime;
    }

    public void setBornDayTime(Integer bornDayTime) {
        this.bornDayTime = bornDayTime;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getUsedMailBox() {
        return usedMailBox;
    }

    public void setUsedMailBox(String usedMailBox) {
        this.usedMailBox = usedMailBox;
    }

    public String getAccountLocation() {
        return accountLocation;
    }

    public void setAccountLocation(String accountLocation) {
        this.accountLocation = accountLocation;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getFamilyTel() {
        return familyTel;
    }

    public void setFamilyTel(String familyTel) {
        this.familyTel = familyTel;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCurrAddress() {
        return currAddress;
    }

    public void setCurrAddress(String currAddress) {
        this.currAddress = currAddress;
    }

    public String getPoliticalBackGround() {
        return politicalBackGround;
    }

    public void setPoliticalBackGround(String politicalBackGround) {
        this.politicalBackGround = politicalBackGround;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }
    public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}

	public String getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(String emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public String getEmergencyContactsPhone() {
        return emergencyContactsPhone;
    }

    public void setEmergencyContactsPhone(String emergencyContactsPhone) {
        this.emergencyContactsPhone = emergencyContactsPhone;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public int getShowPhone() {
		return showPhone;
	}
	public void setShowPhone(int showPhone) {
		this.showPhone = showPhone;
	}
	public String getFamilyAddressProvince() {
        return familyAddressProvince;
    }
    public void setFamilyAddressProvince(String familyAddressProvince) {
        this.familyAddressProvince = familyAddressProvince;
    }

    public String getFamilyAddressCity() {
        return familyAddressCity;
    }

    public void setFamilyAddressCity(String familyAddressCity) {
        this.familyAddressCity = familyAddressCity;
    }

    public String getFamilyAddressDetail() {
        return familyAddressDetail;
    }

    public void setFamilyAddressDetail(String familyAddressDetail) {
        this.familyAddressDetail = familyAddressDetail;
    }

    public Integer getOfficialMonth() {
        return officialMonth;
    }

    public void setOfficialMonth(Integer officialMonth) {
        this.officialMonth = officialMonth;
    }

    public Integer getOfficialDay() {
        return officialDay;
    }

    public void setOfficialDay(Integer officialDay) {
        this.officialDay = officialDay;
    }

    public Integer getOfficialYear() {
        return officialYear;
    }

    public void setOfficialYear(Integer officialYear) {
        this.officialYear = officialYear;
    }

    public Integer getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Integer hasChildren) {
        this.hasChildren = hasChildren;
    }


	public String getUserNameCn() {
		return userNameCn;
	}

	public void setUserNameCn(String userNameCn) {
		this.userNameCn = userNameCn;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBloodTypeValue() {
		return bloodTypeValue;
	}

	public void setBloodTypeValue(String bloodTypeValue) {
		this.bloodTypeValue = bloodTypeValue;
	}

	public String getMaritalStatusValue() {
		return maritalStatusValue;
	}

	public void setMaritalStatusValue(String maritalStatusValue) {
		this.maritalStatusValue = maritalStatusValue;
	}

	public String getPoliticalBackGroundValue() {
		return politicalBackGroundValue;
	}

	public void setPoliticalBackGroundValue(String politicalBackGroundValue) {
		this.politicalBackGroundValue = politicalBackGroundValue;
	}

	public String getLastDegreeValue() {
		return lastDegreeValue;
	}

	public void setLastDegreeValue(String lastDegreeValue) {
		this.lastDegreeValue = lastDegreeValue;
	}

    public Integer getCensus() {
        return census;
    }

    public void setCensus(Integer census) {
        this.census = census;
    }

    public Integer getEnlist() {
        return enlist;
    }

    public void setEnlist(Integer enlist) {
        this.enlist = enlist;
    }

    public String getLastStudyType() {
        return lastStudyType;
    }

    public void setLastStudyType(String lastStudyType) {
        this.lastStudyType = lastStudyType;
    }

    public String getLastStudyTypeValue() {
        return lastStudyTypeValue;
    }

    public void setLastStudyTypeValue(String lastStudyTypeValue) {
        this.lastStudyTypeValue = lastStudyTypeValue;
    }

    @Override
    public String toString() {
        return "EmployeeDetails{" +
                "userCode=" + userCode +
                ", userNameCn='" + userNameCn + '\'' +
                ", userNameEn='" + userNameEn + '\'' +
                ", sex='" + sex + '\'' +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", positionName='" + positionName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", userHight=" + userHight +
                ", bloodType='" + bloodType + '\'' +
                ", bloodTypeValue='" + bloodTypeValue + '\'' +
                ", bornDay=" + bornDay +
                ", officialMonth=" + officialMonth +
                ", officialDay=" + officialDay +
                ", officialYear=" + officialYear +
                ", lunarCalendar=" + lunarCalendar +
                ", birthday=" + birthday +
                ", bornYear=" + bornYear +
                ", bornMonth=" + bornMonth +
                ", bornDayTime=" + bornDayTime +
                ", constellation='" + constellation + '\'' +
                ", constellationValue='" + constellationValue + '\'' +
                ", health=" + health +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", maritalStatusValue='" + maritalStatusValue + '\'' +
                ", showPhone=" + showPhone +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", alternatePhone='" + alternatePhone + '\'' +
                ", usedMailBox='" + usedMailBox + '\'' +
                ", accountLocation='" + accountLocation + '\'' +
                ", familyAddress='" + familyAddress + '\'' +
                ", familyTel='" + familyTel + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", nation='" + nation + '\'' +
                ", nationValue='" + nationValue + '\'' +
                ", currAddress='" + currAddress + '\'' +
                ", politicalBackGround='" + politicalBackGround + '\'' +
                ", politicalBackGroundValue='" + politicalBackGroundValue + '\'' +
                ", lastDegree='" + lastDegree + '\'' +
                ", lastDegreeValue='" + lastDegreeValue + '\'' +
                ", lastStudyType='" + lastStudyType + '\'' +
                ", lastStudyTypeValue='" + lastStudyTypeValue + '\'' +
                ", workTime=" + workTime +
                ", emergencyContacts='" + emergencyContacts + '\'' +
                ", emergencyContactsPhone='" + emergencyContactsPhone + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", status=" + status +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                ", officePhone='" + officePhone + '\'' +
                ", familyAddressProvince='" + familyAddressProvince + '\'' +
                ", provinceValue='" + provinceValue + '\'' +
                ", familyAddressCity='" + familyAddressCity + '\'' +
                ", cityValue='" + cityValue + '\'' +
                ", familyAddressDetail='" + familyAddressDetail + '\'' +
                ", hasChildren=" + hasChildren +
                ", census=" + census +
                ", enlist=" + enlist +
                ", rollbackReason='" + rollbackReason + '\'' +
                '}';
    }

}
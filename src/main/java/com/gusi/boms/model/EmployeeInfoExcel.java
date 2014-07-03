package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-17 14:24)
 * Description: 员工信息导出实体
 */
public class EmployeeInfoExcel implements Serializable{
    private static final long serialVersionUID = -3984548817010266553L;
    private String userCode;    // 工号
    private String userNameCn;  //中文姓名名称
    private String userNameEn;  //英文名称
    private String sex;         // 性别
    private String credentialsNo;  // 身份证号

    private String areaName;    // 区域
    private String orgName;    // 部门
    private String positionName;  //岗位名称
    private String titleLevel;    //职等职级
    private String userTitle;  // 头衔名称

    private String status;      // 员工状态
    private Date joinDate;  // 入职日期
    private Date formalDate;  // 转正日期
    private Date leaveDate;  // 离职日期
    private String experience;  // 有无行业经验

    private String bornDay;    //身份证出身日期
    private String birthday;   //生日
    private String constellationStr;  //星座
    private String lastDegreeStr;  //  最高学历
    private String accountLocation;  //户口地址

    private String orgAddress;  //办公地点
    private String orgPhone;  //办公地点电话
    private String mobilePhone;  //移动电话
    private String officePhone;  //办公电话
    private String orgFax;  //组织传真

    private String positionType;  //岗位类型
    private String positionDesc;  //岗位描述
    private String credentialsType;  //身份证类型
    private String enUserTitle;  // 头衔英文名称
    private String leaveReason;  //离职原因
    private String leaveType;  //离职类型
    private String black;  // 黑名单
    private String newJoinDate;  //最近入职日期
    private String closedDate;  //关组时间
    private String openDate;  //开组时间
    private String enOrgName;  //组织英文名称
    private String orgType;  //组织类型
    private String manager;  //负责人工号
    private String managerName;  //负责人姓名
    private String userHight;  //身高
    private String bloodTypeStr;  //血型
    private String lunarCalendar;  //是否农历
    private String health;  //健康状况
    private String maritalStatusStr;  //婚姻状况
    private String usedMailBox;  //常用邮箱
    private String familyAddress;  //家庭地址
    private String familyTel;  //家庭电话
    private String homeTown;  //籍贯
    private String nationStr;  // 民族
    private String currAddress;  // 先住址
    private String politicalBackGroundStr;  //政治背景
    private String workTime;  //工作时间
    private String emergencyContacts;  // 紧急联络人
    private String emergencyContactsPhone;  //紧急联络人电话
    private String familyInfo;  // 家庭成员信息
    private String contractInfo;  //员工合同信息
    private String educationExpreInfo;  //教育经历信息
    private String trainInfo;  // 培训经历
    private String workInfo;  //工作信息

    private String comeFromStr; //招聘渠道
    private String censusStr; //户籍性质

    

	public String getCensusStr() {
        return censusStr;
    }

    public void setCensusStr(String censusStr) {
        this.censusStr = censusStr;
    }

    public String getComeFromStr() {
        return comeFromStr;
    }

    public void setComeFromStr(String comeFromStr) {
        this.comeFromStr = comeFromStr;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserNameCn() {
        return userNameCn;
    }

    public void setUserNameCn(String userNameCn) {
        this.userNameCn = userNameCn;
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        this.userNameEn = userNameEn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCredentialsNo() {
        return credentialsNo+" ";
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getEnUserTitle() {
        return enUserTitle;
    }

    public void setEnUserTitle(String enUserTitle) {
        this.enUserTitle = enUserTitle;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getFormalDate() {
        return formalDate;
    }

    public void setFormalDate(Date formalDate) {
        this.formalDate = formalDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getNewJoinDate() {
        return newJoinDate;
    }

    public void setNewJoinDate(String newJoinDate) {
        this.newJoinDate = newJoinDate;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getEnOrgName() {
        return enOrgName;
    }

    public void setEnOrgName(String enOrgName) {
        this.enOrgName = enOrgName;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgFax() {
        return orgFax;
    }

    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getUserHight() {
        return userHight;
    }

    public void setUserHight(String userHight) {
        this.userHight = userHight;
    }

    public String getBloodTypeStr() {
        return bloodTypeStr;
    }

    public void setBloodTypeStr(String bloodTypeStr) {
        this.bloodTypeStr = bloodTypeStr;
    }

    public String getBornDay() {
        return bornDay;
    }

    public void setBornDay(String bornDay) {
        this.bornDay = bornDay;
    }

    public String getLunarCalendar() {
        return lunarCalendar;
    }

    public void setLunarCalendar(String lunarCalendar) {
        this.lunarCalendar = lunarCalendar;
    }

    public String getConstellationStr() {
        return constellationStr;
    }

    public void setConstellationStr(String constellationStr) {
        this.constellationStr = constellationStr;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getMaritalStatusStr() {
        return maritalStatusStr;
    }

    public void setMaritalStatusStr(String maritalStatusStr) {
        this.maritalStatusStr = maritalStatusStr;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public String getNationStr() {
        return nationStr;
    }

    public void setNationStr(String nationStr) {
        this.nationStr = nationStr;
    }

    public String getCurrAddress() {
        return currAddress;
    }

    public void setCurrAddress(String currAddress) {
        this.currAddress = currAddress;
    }

    public String getPoliticalBackGroundStr() {
        return politicalBackGroundStr;
    }

    public void setPoliticalBackGroundStr(String politicalBackGroundStr) {
        this.politicalBackGroundStr = politicalBackGroundStr;
    }

    public String getLastDegreeStr() {
        return lastDegreeStr;
    }

    public void setLastDegreeStr(String lastDegreeStr) {
        this.lastDegreeStr = lastDegreeStr;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
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

    public String getFamilyInfo() {
        return familyInfo;
    }

    public void setFamilyInfo(String familyInfo) {
        this.familyInfo = familyInfo;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }

    public String getEducationExpreInfo() {
        return educationExpreInfo;
    }

    public void setEducationExpreInfo(String educationExpreInfo) {
        this.educationExpreInfo = educationExpreInfo;
    }

    public String getTrainInfo() {
        return trainInfo;
    }

    public void setTrainInfo(String trainInfo) {
        this.trainInfo = trainInfo;
    }

    public String getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(String workInfo) {
        this.workInfo = workInfo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    @Override
    public String toString() {
        return "EmployeeInfoExcel{" +
                "userCode='" + userCode + '\'' +
                ", userNameCn='" + userNameCn + '\'' +
                ", userNameEn='" + userNameEn + '\'' +
                ", sex='" + sex + '\'' +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", areaName='" + areaName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", titleLevel='" + titleLevel + '\'' +
                ", userTitle='" + userTitle + '\'' +
                ", status='" + status + '\'' +
                ", joinDate=" + joinDate +
                ", formalDate=" + formalDate +
                ", leaveDate=" + leaveDate +
                ", experience='" + experience + '\'' +
                ", bornDay='" + bornDay + '\'' +
                ", birthday='" + birthday + '\'' +
                ", constellationStr='" + constellationStr + '\'' +
                ", lastDegreeStr='" + lastDegreeStr + '\'' +
                ", accountLocation='" + accountLocation + '\'' +
                ", orgAddress='" + orgAddress + '\'' +
                ", orgPhone='" + orgPhone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", orgFax='" + orgFax + '\'' +
                ", positionType='" + positionType + '\'' +
                ", positionDesc='" + positionDesc + '\'' +
                ", credentialsType='" + credentialsType + '\'' +
                ", enUserTitle='" + enUserTitle + '\'' +
                ", leaveReason='" + leaveReason + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", black='" + black + '\'' +
                ", newJoinDate='" + newJoinDate + '\'' +
                ", closedDate='" + closedDate + '\'' +
                ", openDate='" + openDate + '\'' +
                ", enOrgName='" + enOrgName + '\'' +
                ", orgType='" + orgType + '\'' +
                ", manager='" + manager + '\'' +
                ", managerName='" + managerName + '\'' +
                ", userHight='" + userHight + '\'' +
                ", bloodTypeStr='" + bloodTypeStr + '\'' +
                ", lunarCalendar='" + lunarCalendar + '\'' +
                ", health='" + health + '\'' +
                ", maritalStatusStr='" + maritalStatusStr + '\'' +
                ", usedMailBox='" + usedMailBox + '\'' +
                ", familyAddress='" + familyAddress + '\'' +
                ", familyTel='" + familyTel + '\'' +
                ", homeTown='" + homeTown + '\'' +
                ", nationStr='" + nationStr + '\'' +
                ", currAddress='" + currAddress + '\'' +
                ", politicalBackGroundStr='" + politicalBackGroundStr + '\'' +
                ", workTime='" + workTime + '\'' +
                ", emergencyContacts='" + emergencyContacts + '\'' +
                ", emergencyContactsPhone='" + emergencyContactsPhone + '\'' +
                ", familyInfo='" + familyInfo + '\'' +
                ", contractInfo='" + contractInfo + '\'' +
                ", educationExpreInfo='" + educationExpreInfo + '\'' +
                ", trainInfo='" + trainInfo + '\'' +
                ", workInfo='" + workInfo + '\'' +
                ", comeFromStr='" + comeFromStr + '\'' +
                ", censusStr='" + censusStr + '\'' +
                '}';
    }
}

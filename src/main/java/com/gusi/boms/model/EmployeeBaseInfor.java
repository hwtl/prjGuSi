package com.gusi.boms.model;

import com.gusi.boms.util.HistoryInfoUtils;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.util.DyDate;
import com.dooioo.web.model.BasePageEntity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu(2013-04-08 15:09)
 * Description: 员工基础信息业务逻辑处理
 */

public class EmployeeBaseInfor extends BasePageEntity {
    private static final long serialVersionUID = -6147963405620251534L;
    private int userCode;          //员工工号 主键

    private int orgId;             //组织id

    private String userNameCn;    //员工中文名称

    private String userNameEn;      //员工英文名称

    private String credentialsType; //身份类型

    private String credentialsNo; //身份证号

    private String experience;  //有无行业经验

    private String sex;         //性别
    private String status;     //员工状态 ： 特殊账号,正式,离职,试用期,申请离职,公司待报到,门店待报到,公司未报到,门店未报到
    private Date joinDate;      //入职时间

    private Date formalDate;    //转正日期

    private String password;     //密码，md5加密后的

    private Date createTime;    //创建时间

    private Integer creator;    //创建人

    private String userTitle;   //头衔

    private Date updateTime;    //更新时间

    private Integer isBlack;    //黑名单，1黑名单，0正常

    private Date newJoinDate;    //回聘时间

    private String leaveType;    //离职类型

    private Date leaveDate;     //离职时间

    private String leaveReason; //离职原因

    private int positionId;    //职位id

    private String company;     //公司

    private String eUserTitle;  //头衔的英文名称

    private int levelId;

    private Integer updator;

    public static final String SPACIAL = "特殊账号";
    public static final String BLACK = "黑名单";
    public static final String NORMALEAVE = "正常离职";


    public static final String SEX_BOY = "男";
    public static final String SEX_GIRL = "女";
    public static final String EXPERIENCE_HAVE = "有";
    public static final String EXPERIENCE_NO = "无";

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
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

    public String getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(String credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = StringUtils.isNotBlank(credentialsNo) ? credentialsNo.toUpperCase() : "";
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getNewJoinDate() {
        return newJoinDate;
    }

    public void setNewJoinDate(Date newJoinDate) {
        this.newJoinDate = newJoinDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
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

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer black) {
        isBlack = black;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String geteUserTitle() {
        return eUserTitle;
    }

    public void seteUserTitle(String eUserTitle) {
        this.eUserTitle = eUserTitle;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getOperateRemark(EmployeeBaseInfor that){
       StringBuilder remark = new StringBuilder();
       if (userNameCn != null ? !userNameCn.equals(that.userNameCn) : that.userNameCn != null){
           remark.append(HistoryInfoUtils.getChangeStr("姓名", HistoryInfoUtils.getRemarkStr(userNameCn, that.userNameCn)));

       }
       if (credentialsNo != null ? !credentialsNo.equals(that.credentialsNo) : that.credentialsNo != null){
           remark.append(HistoryInfoUtils.getChangeStr("身份证号", HistoryInfoUtils.getRemarkStr(credentialsNo, that.credentialsNo)));

       }
       if (experience != null ? !experience.equals(that.experience) : that.experience != null){
           remark.append(HistoryInfoUtils.getChangeStr("行业经验", HistoryInfoUtils.getRemarkStr(experience, that.experience)));

       }
       if (joinDate != null ? !joinDate.equals(that.joinDate) : that.joinDate != null){
           remark.append(HistoryInfoUtils.getChangeStr("入职时间", HistoryInfoUtils.getRemarkStr(DyDate.format(joinDate, "yyyy-MM-dd"), DyDate.format(that.joinDate, "yyyy-MM-dd"))));

       }
       if (userTitle != null ? !userTitle.equals(that.userTitle) : that.userTitle != null){
           remark.append(HistoryInfoUtils.getChangeStr("岗位名称", HistoryInfoUtils.getRemarkStr(userTitle, that.userTitle)));
       }
       return StringBuilderHelper.trimLast(remark, "<br/>").toString();
   }

   public static EmployeeBaseInfor init(){
       VEmployeeBaseInfor employeeBaseInfor = new VEmployeeBaseInfor();
       employeeBaseInfor.setSex(SEX_BOY);
       employeeBaseInfor.setExperience(EXPERIENCE_NO);
       employeeBaseInfor.setStatus(EmployeeStatus.UnFormal.toString());
       return employeeBaseInfor;
   }

    @Override
    public String toString() {
        return "EmployeeBaseInfor{" +
                "userCode=" + userCode +
                ", orgId=" + orgId +
                ", userNameCn='" + userNameCn + '\'' +
                ", userNameEn='" + userNameEn + '\'' +
                ", credentialsType='" + credentialsType + '\'' +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", experience='" + experience + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", joinDate=" + joinDate +
                ", formalDate=" + formalDate +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", userTitle='" + userTitle + '\'' +
                ", updateTime=" + updateTime +
                ", isBlack=" + isBlack +
                ", newJoinDate=" + newJoinDate +
                ", leaveType='" + leaveType + '\'' +
                ", leaveDate=" + leaveDate +
                ", leaveReason='" + leaveReason + '\'' +
                ", positionId=" + positionId +
                ", company='" + company + '\'' +
                ", eUserTitle='" + eUserTitle + '\'' +
                '}';
    }
}
package com.gusi.empTransfer.model;

import com.dooioo.web.model.BasePageEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-23 上午10:28
 * @Description: 员工转调
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeTransfer extends BasePageEntity {

    private int id;             //id
    private String transferNo;  //转调单号
    private String ydType;      //异动类型 转调
    private int userCode;       //转调员工的工号
    private String transferType;   //转调类型
    private int transferState;   //转调情况      0 ：无，   1：主动        2：被动
    private Date activeDate;    //生效时间
    private int oldOrgId;
    private Integer oldLevelId;
    private Integer oldPositionId;
    private String oldTitle;
    private int newOrgId;       //转调后组织Id
    private Integer newLevelId;     //新职级
    private Integer newPositionId;  //新岗位
    private String newTitle;
    private int status;         //状态      -1 删除     1 正常
    private Integer fangyuanStatus; //房源交接     0 不交接 1，交接 2完成
    private Integer keyuanStatus;   //客源状态     0 不交接 1，交接 2完成
    private Date createTime;
    private int creator;
    private Date updateTime;
    private Integer updator;

    private Integer approvalStatus; //审批状态  -1：已作废 1：审批中   2：待处理交接   3:已完成   4:打回修改

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    public String getYdType() {
        return ydType;
    }

    public void setYdType(String ydType) {
        this.ydType = ydType;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public int getTransferState() {
        return transferState;
    }

    public void setTransferState(int transferState) {
        this.transferState = transferState;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public int getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(int oldOrgId) {
        this.oldOrgId = oldOrgId;
    }

    public Integer getOldLevelId() {
        return oldLevelId;
    }

    public void setOldLevelId(Integer oldLevelId) {
        this.oldLevelId = oldLevelId;
    }

    public Integer getOldPositionId() {
        return oldPositionId;
    }

    public void setOldPositionId(Integer oldPositionId) {
        this.oldPositionId = oldPositionId;
    }

    public String getOldTitle() {
        return oldTitle;
    }

    public void setOldTitle(String oldTitle) {
        this.oldTitle = oldTitle;
    }

    public int getNewOrgId() {
        return newOrgId;
    }

    public void setNewOrgId(int newOrgId) {
        this.newOrgId = newOrgId;
    }

    public Integer getNewLevelId() {
        return newLevelId;
    }

    public void setNewLevelId(Integer newLevelId) {
        this.newLevelId = newLevelId;
    }

    public Integer getNewPositionId() {
        return newPositionId;
    }

    public void setNewPositionId(Integer newPositionId) {
        this.newPositionId = newPositionId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getFangyuanStatus() {
        return fangyuanStatus;
    }

    public void setFangyuanStatus(Integer fangyuanStatus) {
        this.fangyuanStatus = fangyuanStatus;
    }

    public Integer getKeyuanStatus() {
        return keyuanStatus;
    }

    public void setKeyuanStatus(Integer keyuanStatus) {
        this.keyuanStatus = keyuanStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return "EmployeeTransfer{" +
                "id=" + id +
                ", transferNo='" + transferNo + '\'' +
                ", ydType='" + ydType + '\'' +
                ", userCode=" + userCode +
                ", transferType='" + transferType + '\'' +
                ", transferState=" + transferState +
                ", activeDate=" + activeDate +
                ", oldOrgId=" + oldOrgId +
                ", oldLevelId=" + oldLevelId +
                ", oldPositionId=" + oldPositionId +
                ", oldTitle='" + oldTitle + '\'' +
                ", newOrgId=" + newOrgId +
                ", newLevelId=" + newLevelId +
                ", newPositionId=" + newPositionId +
                ", newTitle='" + newTitle + '\'' +
                ", status=" + status +
                ", fangyuanStatus=" + fangyuanStatus +
                ", keyuanStatus=" + keyuanStatus +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                ", approvalStatus=" + approvalStatus +
                '}';
    }
}

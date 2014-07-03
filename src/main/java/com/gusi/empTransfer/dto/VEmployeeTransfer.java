package com.gusi.empTransfer.dto;

import com.gusi.empTransfer.model.EmployeeTransfer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-23 下午2:05
 * @Description: 转调视图
 * To change this template use File | Settings | File Templates.
 */
public class VEmployeeTransfer extends EmployeeTransfer {
    private String userName;
    private String creatorName;
    private String approverName;

    private String oldOrgName;
    private Integer oldSerialId;
    private String oldSerialName;
    private String oldTitleDegree;
    private String oldLevelDegree;
    private String oldLevelName;
    private String oldPositionName;
    private String oldOrgLongCode;
    private Integer oldOrgParentId;
    private Integer oldAreaId;
    private String oldAreaName;

    private String newOrgName;
    private Integer newSerialId;
    private String newSerialName;
    private String newTitleDegree;
    private Integer newTitleId;
    private String newLevelDegree;
    private String newLevelName;
    private String newPositionName;
    private String newOrgLongCode;
    private Integer newOrgParentId;
    private Integer newAreaId;
    private String newAreaName;

    private String transferTypeStr;
    private String transferStateStr;
    private String approvalStr;

    /**
     * 流程id
     */
    private String processInsId;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 操作所需权限Url
     */
    private String operateUrl;
    /**
     * 处理人
     */
    private String handlerName;

    /**
     * @update fdj 2014-4-2
     * 将人事审批合并到审批中
     * @return
     */
    public String getApprovalStr() {
        switch (this.getApprovalStatus()) {
            case 1 :
                approvalStr = "审批中";
                break;
            case 2 :
                approvalStr = "待处理交接";
                break;
            case 3 :
                approvalStr = "已完成";
                break;
            case 4 :
                approvalStr = "打回修改";
                break;
            case 5 :
//                approvalStr = "人事审批";
                approvalStr = "审批中";
                break;
            case -1 :
                approvalStr = "已作废";
                break;
            default:
                approvalStr = "无";
                break;
        }
        return approvalStr;
    }

    public void setApprovalStr(String approvalStr) {
        this.approvalStr = approvalStr;
    }

    public String getTransferStateStr() {
        switch (this.getTransferState()) {
            case 1 :
                transferStateStr = "由经纪人主动提出";
                break;
            case 2 :
                transferStateStr = "被动发生转调";
                break;
            default:
                transferStateStr = "无";
                break;
        }
        return transferStateStr;
    }

    public void setTransferStateStr(String transferStateStr) {
        this.transferStateStr = transferStateStr;
    }

    public String getTransferTypeStr() {
        return transferTypeStr;
    }

    public void setTransferTypeStr(String transferTypeStr) {
        this.transferTypeStr = transferTypeStr;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getOldSerialId() {
        return oldSerialId;
    }

    public void setOldSerialId(Integer oldSerialId) {
        this.oldSerialId = oldSerialId;
    }

    public Integer getNewSerialId() {
        return newSerialId;
    }

    public void setNewSerialId(Integer newSerialId) {
        this.newSerialId = newSerialId;
    }

    public Integer getOldOrgParentId() {
        return oldOrgParentId;
    }

    public void setOldOrgParentId(Integer oldOrgParentId) {
        this.oldOrgParentId = oldOrgParentId;
    }

    public Integer getNewOrgParentId() {
        return newOrgParentId;
    }

    public void setNewOrgParentId(Integer newOrgParentId) {
        this.newOrgParentId = newOrgParentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldOrgName() {
        return oldOrgName;
    }

    public void setOldOrgName(String oldOrgName) {
        this.oldOrgName = oldOrgName;
    }

    public String getOldSerialName() {
        return oldSerialName;
    }

    public void setOldSerialName(String oldSerialName) {
        this.oldSerialName = oldSerialName;
    }

    public String getOldTitleDegree() {
        return oldTitleDegree;
    }

    public void setOldTitleDegree(String oldTitleDegree) {
        this.oldTitleDegree = oldTitleDegree;
    }

    public String getOldLevelDegree() {
        return oldLevelDegree;
    }

    public void setOldLevelDegree(String oldLevelDegree) {
        this.oldLevelDegree = oldLevelDegree;
    }

    public String getOldLevelName() {
        return oldLevelName;
    }

    public void setOldLevelName(String oldLevelName) {
        this.oldLevelName = oldLevelName;
    }

    public String getOldPositionName() {
        return oldPositionName;
    }

    public void setOldPositionName(String oldPositionName) {
        this.oldPositionName = oldPositionName;
    }

    public String getNewPositionName() {
        return newPositionName;
    }

    public void setNewPositionName(String newPositionName) {
        this.newPositionName = newPositionName;
    }

    public String getNewOrgName() {
        return newOrgName;
    }

    public void setNewOrgName(String newOrgName) {
        this.newOrgName = newOrgName;
    }

    public String getNewSerialName() {
        return newSerialName;
    }

    public void setNewSerialName(String newSerialName) {
        this.newSerialName = newSerialName;
    }

    public String getNewTitleDegree() {
        return newTitleDegree;
    }

    public void setNewTitleDegree(String newTitleDegree) {
        this.newTitleDegree = newTitleDegree;
    }

    public String getNewLevelDegree() {
        return newLevelDegree;
    }

    public void setNewLevelDegree(String newLevelDegree) {
        this.newLevelDegree = newLevelDegree;
    }

    public String getNewLevelName() {
        return newLevelName;
    }

    public void setNewLevelName(String newLevelName) {
        this.newLevelName = newLevelName;
    }

    public Integer getNewTitleId() {
        return newTitleId;
    }

    public void setNewTitleId(Integer newTitleId) {
        this.newTitleId = newTitleId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getOldOrgLongCode() {
        return oldOrgLongCode;
    }

    public void setOldOrgLongCode(String oldOrgLongCode) {
        this.oldOrgLongCode = oldOrgLongCode;
    }

    public String getNewOrgLongCode() {
        return newOrgLongCode;
    }

    public void setNewOrgLongCode(String newOrgLongCode) {
        this.newOrgLongCode = newOrgLongCode;
    }

    public Integer getOldAreaId() {
        return oldAreaId;
    }

    public void setOldAreaId(Integer oldAreaId) {
        this.oldAreaId = oldAreaId;
    }

    public String getOldAreaName() {
        return oldAreaName;
    }

    public void setOldAreaName(String oldAreaName) {
        this.oldAreaName = oldAreaName;
    }

    public Integer getNewAreaId() {
        return newAreaId;
    }

    public void setNewAreaId(Integer newAreaId) {
        this.newAreaId = newAreaId;
    }

    public String getNewAreaName() {
        return newAreaName;
    }

    public void setNewAreaName(String newAreaName) {
        this.newAreaName = newAreaName;
    }

    public String getProcessInsId() {
        return processInsId;
    }

    public void setProcessInsId(String processInsId) {
        this.processInsId = processInsId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOperateUrl() {
        return operateUrl;
    }

    public void setOperateUrl(String operateUrl) {
        this.operateUrl = operateUrl;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public String toString() {
        return "VEmployeeTransfer{" +
                "userName='" + userName + '\'' +
                ", oldOrgParentId=" + oldOrgParentId +
                ", oldOrgName='" + oldOrgName + '\'' +
                ", oldSerialId=" + oldSerialId +
                ", oldSerialName='" + oldSerialName + '\'' +
                ", oldTitleDegree='" + oldTitleDegree + '\'' +
                ", oldLevelDegree='" + oldLevelDegree + '\'' +
                ", oldLevelName='" + oldLevelName + '\'' +
                ", oldPositionName='" + oldPositionName + '\'' +
                ", newOrgParentId=" + newOrgParentId +
                ", newOrgName='" + newOrgName + '\'' +
                ", newSerialId=" + newSerialId +
                ", newSerialName='" + newSerialName + '\'' +
                ", newTitleDegree='" + newTitleDegree + '\'' +
                ", newTitleId=" + newTitleId +
                ", newLevelDegree='" + newLevelDegree + '\'' +
                ", newLevelName='" + newLevelName + '\'' +
                ", newPositionName='" + newPositionName + '\'' +
                '}';
    }
}

/**
 * 
 */
package com.gusi.socialInsurance.model;

import java.util.Date;

/**
 * @author Alex Yu
 * @Created 2014年6月5日下午4:51:48
 */
public class SIChangeDetail {

	/**
	 * 营运中心下的员工
	 * 
	 * @since: 2014-06-13 10:01:47
	 */
	public static final int YYZX_IN = 1;
	/**
	 * 不是营运中心下的员工
	 * 
	 * @since: 2014-06-13 10:02:01
	 */
	public static final int YYZX_NOT_IN = 0;

	/**
	 * 社保在缴
	 * 
	 * @since: 2014-06-13 10:15:47
	 */
	public static final int PAYMENT_STATUS_PAYING = 1;

	/**
	 * 社保停缴
	 * 
	 * @since: 2014-06-13 10:51:00
	 */
	public static final int PAYMENT_STATUS_PAYED = 0;

	/**
	 * 自缴在缴
	 * 
	 * @since: 2014-06-13 10:15:44
	 */
	public static final int SELF_PAYMENT_STATUS_PAYING = 1;

	private int userCode;
	private String credentialsNo;
	private String oldStatus;
	private String newStatus;
	private Date newJoinDate;
	private Date leaveDate;
	private Integer oldOrgId;
	private int newOrgId;
	private Integer oldPositionId;
	private int newPositionId;
	private Integer oldLevelId;
	private int newLevelId;
	private Integer oldSerialId;
	private int newSerialId;
	private Integer oldTitleDegree;
	private int newTitleDegree;
	private String oldLevelDegree;
	private String newLevelDegree;
	private Integer oldCensusId;
	private Integer newCensusId;
	private String oldContractType;
	private String newContractType;
	private Date oldContractStartTime;
	private Date newContractStartTime;
	private Date oldContractEndTime;
	private Date newContractEndTime;

	private int paymentStatus; // 1当前在缴,0历史
	private int paymentLocationId;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Date getNewJoinDate() {
        return newJoinDate;
    }

    public void setNewJoinDate(Date newJoinDate) {
        this.newJoinDate = newJoinDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(Integer oldOrgId) {
        this.oldOrgId = oldOrgId;
    }

    public int getNewOrgId() {
        return newOrgId;
    }

    public void setNewOrgId(int newOrgId) {
        this.newOrgId = newOrgId;
    }

    public Integer getOldPositionId() {
        return oldPositionId;
    }

    public void setOldPositionId(Integer oldPositionId) {
        this.oldPositionId = oldPositionId;
    }

    public int getNewPositionId() {
        return newPositionId;
    }

    public void setNewPositionId(int newPositionId) {
        this.newPositionId = newPositionId;
    }

    public Integer getOldLevelId() {
        return oldLevelId;
    }

    public void setOldLevelId(Integer oldLevelId) {
        this.oldLevelId = oldLevelId;
    }

    public int getNewLevelId() {
        return newLevelId;
    }

    public void setNewLevelId(int newLevelId) {
        this.newLevelId = newLevelId;
    }

    public Integer getOldSerialId() {
        return oldSerialId;
    }

    public void setOldSerialId(Integer oldSerialId) {
        this.oldSerialId = oldSerialId;
    }

    public int getNewSerialId() {
        return newSerialId;
    }

    public void setNewSerialId(int newSerialId) {
        this.newSerialId = newSerialId;
    }

    public Integer getOldTitleDegree() {
        return oldTitleDegree;
    }

    public void setOldTitleDegree(Integer oldTitleDegree) {
        this.oldTitleDegree = oldTitleDegree;
    }

    public int getNewTitleDegree() {
        return newTitleDegree;
    }

    public void setNewTitleDegree(int newTitleDegree) {
        this.newTitleDegree = newTitleDegree;
    }

    public String getOldLevelDegree() {
        return oldLevelDegree;
    }

    public void setOldLevelDegree(String oldLevelDegree) {
        this.oldLevelDegree = oldLevelDegree;
    }

    public String getNewLevelDegree() {
        return newLevelDegree;
    }

    public void setNewLevelDegree(String newLevelDegree) {
        this.newLevelDegree = newLevelDegree;
    }

    public Integer getOldCensusId() {
        return oldCensusId;
    }

    public void setOldCensusId(Integer oldCensusId) {
        this.oldCensusId = oldCensusId;
    }

    public Integer getNewCensusId() {
        return newCensusId;
    }

    public void setNewCensusId(Integer newCensusId) {
        this.newCensusId = newCensusId;
    }

    public String getOldContractType() {
        return oldContractType;
    }

    public void setOldContractType(String oldContractType) {
        this.oldContractType = oldContractType;
    }

    public String getNewContractType() {
        return newContractType;
    }

    public void setNewContractType(String newContractType) {
        this.newContractType = newContractType;
    }

    public Date getOldContractStartTime() {
        return oldContractStartTime;
    }

    public void setOldContractStartTime(Date oldContractStartTime) {
        this.oldContractStartTime = oldContractStartTime;
    }

    public Date getNewContractStartTime() {
        return newContractStartTime;
    }

    public void setNewContractStartTime(Date newContractStartTime) {
        this.newContractStartTime = newContractStartTime;
    }

    public Date getOldContractEndTime() {
        return oldContractEndTime;
    }

    public void setOldContractEndTime(Date oldContractEndTime) {
        this.oldContractEndTime = oldContractEndTime;
    }

    public Date getNewContractEndTime() {
        return newContractEndTime;
    }

    public void setNewContractEndTime(Date newContractEndTime) {
        this.newContractEndTime = newContractEndTime;
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

    @Override
    public String toString() {
        return "SIChangeDetail{" +
                "userCode=" + userCode +
                ", credentialsNo='" + credentialsNo + '\'' +
                ", oldStatus='" + oldStatus + '\'' +
                ", newStatus='" + newStatus + '\'' +
                ", newJoinDate=" + newJoinDate +
                ", leaveDate=" + leaveDate +
                ", oldOrgId=" + oldOrgId +
                ", newOrgId=" + newOrgId +
                ", oldPositionId=" + oldPositionId +
                ", newPositionId=" + newPositionId +
                ", oldLevelId=" + oldLevelId +
                ", newLevelId=" + newLevelId +
                ", oldSerialId=" + oldSerialId +
                ", newSerialId=" + newSerialId +
                ", oldTitleDegree=" + oldTitleDegree +
                ", newTitleDegree=" + newTitleDegree +
                ", oldLevelDegree='" + oldLevelDegree + '\'' +
                ", newLevelDegree='" + newLevelDegree + '\'' +
                ", oldCensusId=" + oldCensusId +
                ", newCensusId=" + newCensusId +
                ", oldContractType='" + oldContractType + '\'' +
                ", newContractType='" + newContractType + '\'' +
                ", oldContractStartTime=" + oldContractStartTime +
                ", newContractStartTime=" + newContractStartTime +
                ", oldContractEndTime=" + oldContractEndTime +
                ", newContractEndTime=" + newContractEndTime +
                ", paymentStatus=" + paymentStatus +
                ", paymentLocationId=" + paymentLocationId +
                '}';
    }
}

package com.gusi.boms.model;

import com.gusi.boms.util.HistoryInfoUtils;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.other.enums.DyDatePattern;
import com.dooioo.plus.util.DyDate;
import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-4-9 上午11:57:38 ) 
 */
public class EmployeeContractRecords  extends BaseEntity {
	private static final long serialVersionUID = 1873129475430296848L;
    public static final String REGULAR_CONTRACT = "固定期限合同";
    public static final String NOT_REGULAR_CONTRACT = "不固定期限";
	private int id;

    private int userCode; //工号

    private String contractType; //合同类型 in (固定期限合同，不固定期限)

    private Date startTime; //起始时间

    private Date endTime;//结束时间

    private int status; //状态

    private int creator; //创建人

    private Date createTime; //创建时间

    private Integer updator;  //更新人工号

    private Date updateTime; //更新时间

    private String updateName;  //更新人姓名 model冗余，便于操作

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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    public String getYaerCountStr(){
        return this.contractType.equals(REGULAR_CONTRACT) ? DyDate.dateDiff(DyDatePattern.Year, this.startTime, this.endTime)+"" : "-";
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getOperateRemark(EmployeeContractRecords that){
        StringBuilder remark = new StringBuilder();
        if (contractType != null ? !contractType.equals(that.contractType) : that.contractType != null){
            remark.append(HistoryInfoUtils.getChangeStr("合同类型", HistoryInfoUtils.getRemarkStr(contractType, that.contractType)));

        }

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null){
            remark.append(HistoryInfoUtils.getChangeStr("合同开始时间", HistoryInfoUtils.getRemarkStr(DyDate.format(startTime, "yyyy-MM-dd"), DyDate.format(that.startTime, "yyyy-MM-dd"))));

        }

        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null){
            remark.append(HistoryInfoUtils.getChangeStr("合同结束时间", HistoryInfoUtils.getRemarkStr(DyDate.format(endTime, "yyyy-MM-dd"), DyDate.format(that.endTime, "yyyy-MM-dd"))));

        }
        return StringBuilderHelper.trimLast(remark, "<br/>").toString();
    }
    @Override
    public String toString() {
        return "EmployeeContractRecords{" +
                "id=" + id +
                ", userCode=" + userCode +
                ", contractType='" + contractType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                '}';
    }
}
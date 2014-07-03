package com.gusi.boms.model;

import com.dooioo.web.model.BasePageEntity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-19 14:27)
 * @Description: 员工奖惩实体
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeRewardPunishment extends BasePageEntity {
    private static final long serialVersionUID = -4602017799312169142L;
	private int id;                     //id
    private int userCode;               //员工工号
    private String rewardtype;                //类型
    private String channel;             //渠道
    private String result;              //结果
    private Date effectiveTime;         //生效时间
    private String effectiveLength="0";     //有效时长
    private String rules;               //条例
    private String remark;              //原因备注
    private int status;                 //状态 1：正常  9：删除,0未生效。
    private int creator;                //创建人
    private Date createTime;            //创建时间
    private Integer updator;                //更新人
    private Date updateTime;            //更新时间
    private String userName;
    
    
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getEffectiveLength() {
        return effectiveLength;
    }

    public void setEffectiveLength(String effectiveLength) {
        this.effectiveLength = effectiveLength;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getFailTime() {
        if(this.effectiveTime != null && this.effectiveLength != null&&!"0".equals(effectiveLength)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.effectiveTime);
            calendar.add(Calendar.MONTH, Integer.valueOf(this.effectiveLength));
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            return calendar.getTime();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRewardtype() {
        return rewardtype;
    }

    public void setRewardtype(String rewardtype) {
        this.rewardtype = rewardtype;
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

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "EmployeeRewardPunishment{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", userCode=" + userCode +
                ", rewardtype='" + rewardtype + '\'' +
                ", result='" + result + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", effectiveLength='" + effectiveLength + '\'' +
                ", rules='" + rules + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                '}';
    }
}

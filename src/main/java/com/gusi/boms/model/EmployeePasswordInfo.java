package com.gusi.boms.model;

import com.gusi.boms.util.DateFormatUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: fdj
 * @Create: 14-1-21 上午11:41
 * @Description: 密码信息类
 */
public class EmployeePasswordInfo implements Serializable {
    private static final long serialVersionUID = 4148189789141449219L;
    private int id;
    private int userCode;
    private Date updateTime;
    private String updatePwd;
    private String updateIp;
    private Integer updateType; //1初始密码，2修改密码，3找回密码

    public EmployeePasswordInfo() {
    }

    public EmployeePasswordInfo(int userCode, String updatePwd, String updateIp, Integer updateType) {
        this.userCode = userCode;
        this.updatePwd = updatePwd;
        this.updateIp = updateIp;
        this.updateType = updateType;
    }

    /**
     * 获取格式化后的更新时间
     * @return
     */
    public String getUpdateTimeStr() {
        return DateFormatUtil.getString(this.updateTime);
    }

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePwd() {
        return updatePwd;
    }

    public void setUpdatePwd(String updatePwd) {
        this.updatePwd = updatePwd;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    @Override
    public String toString() {
        return "EmployeePasswordInfo{" +
                "id=" + id +
                ", userCode=" + userCode +
                ", updateTime=" + updateTime +
                ", updatePwd='" + updatePwd + '\'' +
                ", updateIp='" + updateIp + '\'' +
                ", updateType=" + updateType +
                '}';
    }
}

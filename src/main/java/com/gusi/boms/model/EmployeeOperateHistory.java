package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.com.dooioo.erp.erp.dao
 * Author: Jerry.hu
 * Create: Jerry.hu(2013-04-09 13:09)
 * Description:员工基础信息变更对应的历史记录实体
 */
public class EmployeeOperateHistory extends BaseEntity {

    private static final long serialVersionUID = -2799807623299487576L;
    private int id;             //主键id

    private String filed;       //类型

    private String creatorName; //创建人名称

    private Date createTime;    //创建时间

    private int userCode;       //更新人的工号

    private Integer creator;    //创建人工号

    private String remark;      //操作的记录信息

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EmployeeOperateHistory() {
    }

    public EmployeeOperateHistory(String filed, String creatorName, Integer creator, int userCode) {
        this.filed = filed;
        this.creatorName = creatorName;
        this.creator = creator;
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "EmployeeOperateHistory{" +
                "id=" + id +
                ", filed='" + filed + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                ", userCode=" + userCode +
                ", creator=" + creator +
                ", remark='" + remark + '\'' +
                '}';
    }
}
package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

public class EmployeeAccountStatus extends BaseEntity {
    private static final long serialVersionUID = 3446042435832358532L;
    private int userCode;

    private Date createTime;

    private Integer creator;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
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

    public EmployeeAccountStatus(int userCode, Integer creator) {
        this.userCode = userCode;
        this.creator = creator;
    }

    public EmployeeAccountStatus() {

    }
}
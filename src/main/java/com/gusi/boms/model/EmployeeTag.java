package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-05-21 12:00
 * @Description: EmployeeTag
 */
public class EmployeeTag implements Serializable {
    private static final long serialVersionUID = -9139787228537726396L;
    private Integer userCode;
    private String tagCode;
    private String tagName;
    private Integer creator;
    private Date createTime;

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EmployeeTag{" +
                "userCode=" + userCode +
                ", tagCode='" + tagCode + '\'' +
                ", tagName='" + tagName + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                '}';
    }
}

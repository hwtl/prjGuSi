package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-22 15:55)
 * Description: 职等
 */
public class Title extends BaseEntity {
    private static final long serialVersionUID = 6053683185392091060L;
    private int id;
    private int serialId;
    private int titleDegree;
    private String titleName;
    private Date createTime;
    private int creator;
    private int status;
    private String value;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public int getTitleDegree() {
        return titleDegree;
    }

    public void setTitleDegree(int titleDegree) {
        this.titleDegree = titleDegree;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Title{" +
                "id=" + id +
                ", serialId=" + serialId +
                ", titleDegree=" + titleDegree +
                ", titleName='" + titleName + '\'' +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", status=" + status +
                '}';
    }
}

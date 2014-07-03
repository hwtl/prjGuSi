package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-15 15:25)
 * Description:
 */
public class TitleLevel extends BaseEntity {
    private static final long serialVersionUID = 1263155769710011778L;
    private int id;

    private Long titleId;

    private String titleLevel;

    private String levelPre;

    private String levelFull;

    private Integer employeeCount;

    private Date createTime;

    private Integer creator;

    private String value;

    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getTitleLevel() {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel) {
        this.titleLevel = titleLevel;
    }

    public String getLevelPre() {
        return levelPre;
    }

    public void setLevelPre(String levelPre) {
        this.levelPre = levelPre;
    }

    public String getLevelFull() {
        return levelFull;
    }

    public void setLevelFull(String levelFull) {
        this.levelFull = levelFull;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
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
}
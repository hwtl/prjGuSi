package com.gusi.boms.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-11 下午4:18
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class LevelInfo implements Serializable {

    private static final long serialVersionUID = 362427790746771911L;
    private int serialId;
    private String serialName;
    private int titleId;
    private String titleDegree;
    private String titleName;
    private int levelId;
    private String levelDegree;
    private String levelName;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }


    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getTitleDegree() {
        return titleDegree;
    }

    public void setTitleDegree(String titleDegree) {
        this.titleDegree = titleDegree;
    }

    public String getLevelDegree() {
        return levelDegree;
    }

    public void setLevelDegree(String levelDegree) {
        this.levelDegree = levelDegree;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "LevelInfo{" +
                "serialId=" + serialId +
                ", serialName='" + serialName + '\'' +
                ", titleId=" + titleId +
                ", titleDegree=" + titleDegree +
                ", titleName='" + titleName + '\'' +
                ", levelId=" + levelId +
                ", levelDegree=" + levelDegree +
                ", levelName='" + levelName + '\'' +
                '}';
    }
}

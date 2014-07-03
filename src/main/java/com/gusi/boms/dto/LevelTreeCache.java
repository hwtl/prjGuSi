package com.gusi.boms.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-12 下午1:35
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class LevelTreeCache implements Serializable {

    private static final long serialVersionUID = -9101530835870770028L;
    private List<LevelTree> levelTreeList;
    private Date createTime;

    public List<LevelTree> getLevelTreeList() {
        return levelTreeList;
    }

    public void setLevelTreeList(List<LevelTree> levelTreeList) {
        this.levelTreeList = levelTreeList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LevelTreeCache{" +
                "levelTreeList=" + levelTreeList +
                ", createTime=" + createTime +
                '}';
    }
}

package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-06-08 15:17)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class PositionTitle extends BaseEntity {
    private int positionId; //岗位id
    private int titleId;    //职等id

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    @Override
    public String toString() {
        return "PositionTitle{" +
                "positionId=" + positionId +
                ", titleId=" + titleId +
                '}';
    }
}

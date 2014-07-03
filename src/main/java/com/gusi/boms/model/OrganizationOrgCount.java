package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-04-16 09:32
 * @Description: OrganizationOrgCount
 */
public class OrganizationOrgCount implements Serializable {
    private static final long serialVersionUID = -5255428795523156255L;
    private int orgId;
    private int maxCount;
    private int creator;
    private Date createTime;
    private int updator;
    private Date updateTime;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrganizationOrgCount{" +
                "orgId=" + orgId +
                ", maxCount=" + maxCount +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                '}';
    }
}

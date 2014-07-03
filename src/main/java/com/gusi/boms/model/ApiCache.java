package com.gusi.boms.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-31 16:23)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class ApiCache {
    String cacheData;
    Date createTime;

    public ApiCache() {
    }

    public ApiCache(String cacheData, Date createTime) {
        this.cacheData = cacheData;
        this.createTime = createTime;
    }

    public String getCacheData() {
        return cacheData;
    }

    public void setCacheData(String cacheData) {
        this.cacheData = cacheData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

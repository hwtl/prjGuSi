package com.gusi.socialInsurance.dto;

/**
 * @Author: fdj
 * @Since: 2014-06-12 14:11
 * @Description: BaseSearch
 */
public class BaseSearch {
    private int pageSize;
    private int pageNo;
    private String batchId;
    private String keyword;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

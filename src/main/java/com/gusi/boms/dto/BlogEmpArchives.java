package com.gusi.boms.dto;

/**
 * @Author: fdj
 * @Since: 2014-06-10 16:43
 * @Description: BolgEmpArchives
 */
public class BlogEmpArchives {
    private int userCode;
    private boolean showPop;
    private String title;
    private String remark;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public boolean isShowPop() {
        return showPop;
    }

    public void setShowPop(boolean showPop) {
        this.showPop = showPop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BolgEmpArchives{" +
                "userCode=" + userCode +
                ", showPop=" + showPop +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}

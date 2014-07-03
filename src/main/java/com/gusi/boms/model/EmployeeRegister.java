package com.gusi.boms.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-8-27 上午10:53
 * @Description: 员工报到类
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeRegister {

    private int userCode;        //工号
    private String remark;       //未报到原因
    private Date createTime;     //创建时间
    private int creator;         //创建人

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "EmployeeRegister{" +
                "userCode=" + userCode +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", creator=" + creator +
                '}';
    }
}

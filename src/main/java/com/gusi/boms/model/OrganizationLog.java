package com.gusi.boms.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * @Author: fdj
 * @Create: fdj (2013-05-29 16:56)
 * @Description: 组织日志
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationLog extends Organization{

    public static final String OPERATETYPE_EDIT = "编辑";
    public static final String OPERATETYPE_CLOSE = "停用";
    public static final String OPERATETYPE_OPEN = "启用";
    public static final String OPERATETYPE_STOP = "暂停";
    public static final String OPERATETYPE_SORT = "排序";
    public static final String OPERATETYPE_TRANSFER = "转调";

    private String operateType; //操作类型
    private int operator;       //操作人
    private Date operateTime;   //操作时间

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public String toString() {
        return "OrganizationLog{" +
                "OperateType='" + operateType + '\'' +
                ", operator=" + operator +
                ", operateTime=" + operateTime +
                '}';
    }
}

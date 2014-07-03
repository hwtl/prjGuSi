package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-19 14:52)
 * Description: 系统日志记录
 */
public class ApplicationLog extends Application {
    private static final long serialVersionUID = 2661417322982961496L;
    private int appId;
    private String operateType;
    private int creator;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "ApplicationLog{" +
                "appId=" + appId +
                ", operateType='" + operateType + '\'' +
                '}';
    }
}

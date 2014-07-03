package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-12 16:02)
 * 员工对应职位，组织的子类
 */
public class VEmployeePosition extends EmployeePosition {
    private static final long serialVersionUID = -1587287587587329379L;

    private String  positionName;     // 职位名称
    private String orgName; // 组织名称
    private String userNameCn;         // 员工姓名
    private int createor;       //创建人
    private String createName;     //创建人姓名
    private int titleId;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserNameCn() {
        return userNameCn;
    }

    public void setUserNameCn(String userNameCn) {
        this.userNameCn = userNameCn;
    }

    public int getCreateor() {
        return createor;
    }

    public void setCreateor(int createor) {
        this.createor = createor;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Override
    public String toString() {
        return "VEmployeePosition{" +
                "positionName='" + positionName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", userNameCn='" + userNameCn + '\'' +
                ", createor=" + createor +
                ", createName='" + createName + '\'' +
                '}';
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getTitleId() {
        return titleId;
    }
}


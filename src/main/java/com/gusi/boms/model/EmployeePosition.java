package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-10 09:52)
 * Description: 员工职位组织信息的实体
 */
public class EmployeePosition extends BaseEntity {

    private static final long serialVersionUID = -9204354077666965650L;
    private int userCode;       //员工工号

    private int positionId;     //职位Id

    private int orgId;          //组织Id     工号，职位，组织 3个为组合主键

    private int partTime;       //兼职标识符 0、非兼职，1、兼职

    private int id;             //伪列id
    public static final int PART_TIME = 1;
    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getPartTime() {
        return partTime;
    }

    public void setPartTime(int partTime) {
        this.partTime = partTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeePosition() {
    }

    public EmployeePosition(int userCode, int positionId, int orgId, int partTime) {
        this.userCode = userCode;
        this.positionId = positionId;
        this.orgId = orgId;
        this.partTime = partTime;
    }

    @Override
    public String toString() {
        return "EmployeePosition{" +
                "userCode=" + userCode +
                ", positionId=" + positionId +
                ", orgId=" + orgId +
                ", partTime=" + partTime +
                '}';
    }
}
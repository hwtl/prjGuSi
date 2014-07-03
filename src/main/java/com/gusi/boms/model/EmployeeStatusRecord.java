package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-8-30 上午10:00
 * @Description: 员工状态类（保存员工入职后的状态）
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeStatusRecord {
    private int userCode;
    private String status;

    public EmployeeStatusRecord() {
    }

    public EmployeeStatusRecord(int userCode, String status) {
        this.userCode = userCode;
        this.status = status;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeStatus{" +
                "userCode=" + userCode +
                ", status='" + status + '\'' +
                '}';
    }
}

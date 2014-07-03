package com.gusi.empTransfer.model;

import java.io.Serializable;

/**
 * @Author: fdj
 * @Since: 2014-02-25 11:31
 * @Description: 员工转调交接
 */
public class EmployeeTransferProcess implements Serializable {
    private static final long serialVersionUID = 1401008593554069877L;
    private int id;
    private Integer fangyuanStatus; //房源交接     0 不交接 1，交接 2完成
    private Integer keyuanStatus;   //客源状态     0 不交接 1，交接 2完成

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getFangyuanStatus() {
        return fangyuanStatus;
    }

    public void setFangyuanStatus(Integer fangyuanStatus) {
        this.fangyuanStatus = fangyuanStatus;
    }

    public Integer getKeyuanStatus() {
        return keyuanStatus;
    }

    public void setKeyuanStatus(Integer keyuanStatus) {
        this.keyuanStatus = keyuanStatus;
    }

    @Override
    public String toString() {
        return "EmployeeTransferProcess{" +
                "id=" + id +
                ", fangyuanStatus=" + fangyuanStatus +
                ", keyuanStatus=" + keyuanStatus +
                '}';
    }
}

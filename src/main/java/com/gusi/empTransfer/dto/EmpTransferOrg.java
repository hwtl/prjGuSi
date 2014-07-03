package com.gusi.empTransfer.dto;

import com.gusi.boms.model.Organization;

/**
 * @Author: fdj
 * @Since: 2014-02-19 17:20
 * @Description: 转调申请人组织类
 */
public class EmpTransferOrg extends Organization {

    private String transferType;
    private String transferTypeStr;

    public String getTransferTypeStr() {
        return transferTypeStr;
    }

    public void setTransferTypeStr(String transferTypeStr) {
        this.transferTypeStr = transferTypeStr;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    @Override
    public String toString() {
        return "EmpTransferOrg{" +
                "transferType='" + transferType + '\'' +
                ", transferTypeStr='" + transferTypeStr + '\'' +
                '}';
    }
}

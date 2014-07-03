package com.gusi.boms.dto;

import java.io.Serializable;

/**
 * @Author: fdj
 * @Since: 2014-05-08 14:18
 * @Description: SimpleOrg
 */
public class SimpleOrg implements Serializable {
    private static final long serialVersionUID = 2935603043591182824L;

    private int id;
    private String orgName;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SimpleOrg{" +
                "id=" + id +
                ", orgName='" + orgName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

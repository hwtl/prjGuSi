package com.gusi.boms.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-25 下午3:55
 * @Description: 员工转调扩展类
 * To change this template use File | Settings | File Templates.
 */
public class VOrganizationTransfer extends OrganizationTransfer {
    private String orgAParentName;
    private String orgBParentName;

    public String getOrgAParentName() {
        return orgAParentName;
    }

    public void setOrgAParentName(String orgAParentName) {
        this.orgAParentName = orgAParentName;
    }

    public String getOrgBParentName() {
        return orgBParentName;
    }

    public void setOrgBParentName(String orgBParentName) {
        this.orgBParentName = orgBParentName;
    }

    @Override
    public String toString() {
        return "VOrganizationTransfer{" +
                "orgAParentName='" + orgAParentName + '\'' +
                ", orgBParentName='" + orgBParentName + '\'' +
                '}';
    }
}

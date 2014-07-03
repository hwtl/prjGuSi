package com.gusi.app.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-18 下午5:25
 * @Description: 通讯录组织类
 * To change this template use File | Settings | File Templates.
 */
public class AppOrganization {
    private int id;
    private int parentId;
    private String orgName;
    private List<AppOrganization> organizations;
    private List<AppEmployee> employees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<AppEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<AppEmployee> employees) {
        this.employees = employees;
    }

    public List<AppOrganization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<AppOrganization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public String toString() {
        return "AppOrganization{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", orgName='" + orgName + '\'' +
                ", organizations=" + organizations +
                ", employees=" + employees +
                '}';
    }
}

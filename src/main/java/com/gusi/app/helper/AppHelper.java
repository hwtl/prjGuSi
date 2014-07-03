package com.gusi.app.helper;

import java.util.ArrayList;
import java.util.List;

import com.gusi.app.model.AppEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gusi.app.model.AppOrganization;
import com.gusi.app.service.AppEmployeeService;
import com.gusi.app.service.AppOrganizationService;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-21 上午10:25
 * @Description: 通讯录辅助类
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AppHelper {

    @Autowired
    private AppEmployeeService appEmployeeService;
    @Autowired
    private AppOrganizationService appOrganizationService;

    /**
     * 根据组织Id获得该组织树下的通讯录
     * @param orgId 组织id
     * @return
     */
    public AppOrganization  generateContacts(int orgId){
        AppOrganization root = appOrganizationService.findById(orgId);
        List<AppOrganization> orgs = appOrganizationService.queryForTreeByOrgId(orgId);
        List<AppEmployee> emps = appEmployeeService.queryForTreeByOrgId(orgId);
        getAppOrganization(root, orgs, emps);
        return root;
    }

    /**
     * 遍历组织树的通讯录
     * @param org 当前组织
     * @param orgs 该组织下的所有子组织
     * @param emps 该组织下的所有员工
     * @return
     */
    private void getAppOrganization(AppOrganization org, List<AppOrganization> orgs, List<AppEmployee> emps) {
        if(org != null) {
            AppOrganization appOrganization = this.generateOrgTree(org, orgs, emps);
            if(appOrganization.getOrganizations() != null) {
                for(AppOrganization a : appOrganization.getOrganizations()) {
                    this.getAppOrganization(a, orgs, emps);
                }
            }
        }
    }

    /**
     * 拼接父级组织信息
     * @param parentOrg 父级组织
     * @param orgs 组织集合
     * @return String
     */
    public AppOrganization generateOrgTree(AppOrganization parentOrg, List<AppOrganization> orgs, List<AppEmployee> emps){
        int i;
        AppEmployee appEmployee;
        AppOrganization organization;
        for(i=0; i<emps.size(); i++) {
            appEmployee = emps.get(i);
            if(appEmployee.getOrgId() == parentOrg.getId()) {
                if(parentOrg.getEmployees() != null) {
                    parentOrg.getEmployees().add(appEmployee);
                } else {
                    List<AppEmployee> list = new ArrayList();
                    list.add(appEmployee);
                    parentOrg.setEmployees(list);
                }
                emps.remove(i);
                i--;
            }
        }
        for(i=0; i<orgs.size(); i++) {
            organization= orgs.get(i);
            if(organization.getParentId() == parentOrg.getId()) {
                if(parentOrg.getOrganizations() != null) {
                    parentOrg.getOrganizations().add(organization);
                } else {
                    List<AppOrganization> list = new ArrayList();
                    list.add(organization);
                    parentOrg.setOrganizations(list);
                }
                orgs.remove(i);
                i--;
            }
        }
        return parentOrg;
    }
}

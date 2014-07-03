package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.dooioo.plus.oms.dyEnums.*;
import com.dooioo.plus.oms.dyUser.model.Organization;
import com.dooioo.plus.oms.dyUser.model.Sort;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-15 13:17)
 * Description: To change this template use File | Settings | File Templates.
 */
public class TestOrganizationTree extends BaseTest {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationTreeService orgTreeService;

    @Test
    public void testA(){
        List<Organization> organizations = organizationService.getOrgs(OrganizationMethods.getOrgs,null,new OrganizationStatus[]{OrganizationStatus.All},null,new Sort[]{new Sort(OrganizationSortColumn.OrgLevel, OrderBy.ASC)},null, Company.Dooioo);
        System.out.println(organizations.get(12));
    }

    @Test
    public void testIsHashTree(){
        System.out.println(orgTreeService.isHasUpdateCount(10,Company.Dooioo));
    }

}

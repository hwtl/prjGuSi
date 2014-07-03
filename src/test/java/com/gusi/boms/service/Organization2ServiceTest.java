package com.gusi.boms.service;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: fdj
 * @Since: 2014-04-23 14:45
 * @Description: Organization2ServiceTest
 */
public class Organization2ServiceTest extends BaseTest {

    @Autowired
    private Organization2Service organization2Service;

    @Test
    public void testFindAreaOrgLongCode() throws Exception {
        System.out.println(organization2Service.findAreaOrg(21376));
        System.out.println(organization2Service.findAreaOrg(51));
        System.out.println(organization2Service.findAreaOrg(51));
    }

    @Test
    public void testFindEmployeeCount() throws Exception {
        System.out.println(organization2Service.findEmployeeCount("12020001/120720495/12020003/130420639/12020015"));
        System.out.println(organization2Service.findBranchCount("12020001/120720495/12020003/130420639/12020015"));
        System.out.println(organization2Service.findBranchCount("12020001/120720495/12020003/140512095502"));
        System.out.println(organization2Service.findEmployeeCount(null));
        System.out.println(organization2Service.findBranchCount(""));
    }

    @Test
    public void testFindBZForDetails() throws Exception {
        System.out.println(organization2Service.findBZForDetail(20388));
    }
}

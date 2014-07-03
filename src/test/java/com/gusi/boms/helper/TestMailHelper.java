package com.gusi.boms.helper;

import com.gusi.BaseTest;
import com.gusi.boms.model.OrganizationEmployeeCount;
import com.gusi.boms.service.OrganizationEmployeeCountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-3 下午5:05
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class TestMailHelper extends BaseTest {

    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private OrganizationEmployeeCountService countService;

    private List<OrganizationEmployeeCount> organizationEmployeeCountList = new LinkedList<>();

    @Before
    public void init() {
//        organizationEmployeeCountList.add("");
    }

    @Test
    public void testSendOrgEmployeeCountMail() {
//        System.out.println(mailHelper.sendOrgEmployeeCountMail(countService.queryOverdue()));
    }

//    @Test
//    public void testGetOrgEmployeeCountMailContent() {
//        organizationEmployeeCountList = countService.queryOverdue();
//        for(OrganizationEmployeeCount count : organizationEmployeeCountList) {
//            System.out.println(mailHelper.getOrgEmployeeCountMailContent(count));
//        }
//
//    }

}

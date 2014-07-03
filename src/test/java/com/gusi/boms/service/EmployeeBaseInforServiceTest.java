package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.dto.EmpToSendMsg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-04-23 17:19
 * @Description: EmployeeBaseInforServiceTest
 */
public class EmployeeBaseInforServiceTest extends BaseTest {

    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private EmpToSendMsgService empToSendMsgService;

    @Test
    public void testFindUsers() throws Exception {
        List<EmpToSendMsg> empToSendMsgList = empToSendMsgService.findUsers("90378,89695");
        for(EmpToSendMsg empToSendMsg : empToSendMsgList) {
            System.out.println(empToSendMsg);
        }
    }

    @Test
    public void testIsMoreThanNormal() throws Exception {
        System.out.println(employeeBaseInforService.isMoreThanNormal(21613));
    }
}

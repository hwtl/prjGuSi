package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.empTransfer.service.EmployeeTransferService;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-25 下午2:39
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class TestTransfer extends BaseTest {

    @Autowired
    private OrganizationTransferService organizationTransferService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private Organization2Service organization2Service;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeTransferService employeeTransferService;

    @Test
    public void dotest() {
//        System.out.println(organizationTransferService.checkTransfer(1));
        EmployeeBaseInfor employee = employeeBaseInforService.findById(null);
        System.out.println(employee);
    }


   /* @Test
    public void testAccount() {
        organizationTransferService.updateAccount(organization2Service.findById(20828), new Date(), employeeService.getEmployee(90378));
    }*/

    @Test
    public void testParse() {
        Integer.parseInt("dasfh");
    }

    @Test
    public void list() {
//        employeeTransferService.findEmployeeTransfers(1, "王", "德佑");
        employeeBaseInforService.findInterviewEmployees(1, "王", "德佑");
    }
}

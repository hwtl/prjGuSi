
package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.boms.model.EmployeeOperateHistory;
import com.gusi.boms.model.VEmployeeBaseInfor;
import com.dooioo.plus.util.DyDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-09 10:57)
 * Description:员工基础信息业务逻辑的单体测试类
 */
public class TestEmployeeBaseInforService extends BaseTest {
    @Autowired
    EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private Organization2Service organization2Service;

    private VEmployeeBaseInfor employeeBaseInfor;
    @Before
    public void init(){
        employeeBaseInfor = new VEmployeeBaseInfor();
        employeeBaseInfor.setUserNameCn("test_update");
        employeeBaseInfor.setCreator(86101);
        employeeBaseInfor.setSex("女");
        employeeBaseInfor.setCredentialsNo("12334130343dddddddFDSF");
        employeeBaseInfor.setStatus("试用期");
        employeeBaseInfor.setExperience("有");
        employeeBaseInfor.setUserCode(91387);
        try {
            employeeBaseInfor.setJoinDate(DyDate.parse("2013-04-09", "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employeeBaseInfor.setPositionId(10);
        employeeBaseInfor.setOrgId(20495);
        employeeBaseInfor.setUserTitle("初级顾问");
        employeeBaseInfor.setTitleName(Constants.BRANCH_MANAGER);
    }

//    @Test
//    public void testSaveEmployee(){
//        employeeBaseInforService.saveEmployee(employeeBaseInfor);
//    }

    @Test
    public void testCreatePublicoffer() {
        employeeBaseInforService.createPublicoffer(organization2Service.findById(20700));
    }

    @Test
    public void testUpdateEmployee(){
        employeeBaseInforService.update(employeeBaseInfor);
    }

    @Test
    public void testSaveOperateHistory(){
       employeeBaseInforService.saveOperateHistory(employeeBaseInfor,new EmployeeOperateHistory());
    }

    @Test
    public  void testQueryEmployeeList(){
        System.out.println(employeeBaseInforService.queryForList());
    }

    @Test
    public void testAssociative(){
        System.out.println(employeeBaseInforService.associative("胡",""));
    }
    @Test
    public void testUpdateF(){
        System.out.println(employeeBaseInforService.updateFormalDate(86101));
    }
    @Test
    public void testUpdateLev(){
        System.out.println(employeeBaseInforService.updateLeave(86101));
    }
    @Test
    public void testUpdateByUserCode(){
        System.out.println(employeeBaseInforService.updateEmpOrgPositionByUserCode(92367));
    }

    @Test
    public void testUpdateByPositionId(){
        System.out.println(employeeBaseInforService.updateEmpOrgPositionByPositionId(1));
    }

    @Test
    public void testUpdateByOrgId(){
        System.out.println(employeeBaseInforService.updateEmpOrgPositionByOrgId(20587));
    }

    @Test
    public void testGetBirthdayEmployee() {
        List<EmployeeBaseInfor> list = employeeBaseInforService.getBirthdayEmployee();
        for(EmployeeBaseInfor e : list) {
            VEmployeeBaseInfor ve = (VEmployeeBaseInfor) e;
            System.out.print(ve.getUserCode() + "_________");
            System.out.print(ve.getUserNameCn() + "_________");
            System.out.print(ve.getMobilePhone() + "_________");
            System.out.print(ve.getOrgId() + "_________");
            System.out.println();
            List<EmployeeBaseInfor> otherEmployees = employeeBaseInforService.getTogetherEmployee(ve.getUserCode());
            for(EmployeeBaseInfor otherEmployee : otherEmployees) {
                System.out.println(otherEmployee.getUserCode());
            }
        }
    }

    @Test
    public void testGetOldEmployee() {
        List<EmployeeBaseInfor> list = employeeBaseInforService.getOldEmployee();
        for(EmployeeBaseInfor e : list) {
            VEmployeeBaseInfor ve = (VEmployeeBaseInfor) e;
            System.out.print(ve.getUserCode() + "_________");
            System.out.print(ve.getUserNameCn() + "_________");
            System.out.print(ve.getMobilePhone() + "_________");
            System.out.print(ve.getOrgId() + "_________");
            System.out.print(ve.getYears() + "_________");
            System.out.println();
            List<EmployeeBaseInfor> otherEmployees = employeeBaseInforService.getTogetherEmployee(ve.getOrgId());
            for(EmployeeBaseInfor otherEmployee : otherEmployees) {
                System.out.println(otherEmployee);
            }
        }
    }

}


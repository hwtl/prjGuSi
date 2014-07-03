package com.gusi.boms.service;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
* @ClassName: TestOrganizationService
* @Description: TestOrganizationService
* @author fdj
* @date 2013-4-10 上午10:54:09
 */
public class TestOrganizationEmployeeService extends BaseTest {
	@Autowired
	private RoleEmployeeService  roleEmployeeService ;


    @Test
    public void insertRoleEmployee(){
    	roleEmployeeService.insertRoleEmployee(0,30,1,"1,2,3,4,15,10");
    }
}

package com.gusi.boms.service;

import com.gusi.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
* @ClassName: TestOrganizationService
* @Description: TestOrganizationService
* @author fdj
* @date 2013-4-10 上午10:54:09
 */
public class TestRolePositionService extends BaseTest {
	@Autowired
	private RolePositionService  rolePositionService ;


    @Test
    public void insertRolePosition(){
    	Assert.assertTrue(rolePositionService.insertRolePosition(0,30,1,"1,2,3,4,5,10"));
    }
}

package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.ApplicationPrivilege;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
* @ClassName: TestOrganizationService 
* @Description: TestOrganizationService
* @author fdj
* @date 2013-4-10 上午10:54:09
 */
public class TestApplicationService extends BaseTest {
	@Autowired
	private ApplicationPrivilegeService  applicationPrivilegeService ;
    @Test
	public void getPrivilegesByAppId(){
    	Assert.assertEquals(applicationPrivilegeService.getPrivilegesByAppId(14).size(), 18);
    }

    @Test
    public void checkPrivilegeUrlExist(){
        Assert.assertTrue(applicationPrivilegeService.checkPrivilegeUrlExist("employee_tab"));
        Assert.assertFalse(applicationPrivilegeService.checkPrivilegeUrlExist("employee_tab222"));
    }
    @Test
    public void insertApp(){
    	ApplicationPrivilege privilege = new ApplicationPrivilege();
    	privilege.setPrivilegeName("测试权限");
    	privilege.setPrivilegeUrl("test_pri");
    	privilege.setAppId(14);
    	Assert.assertTrue(applicationPrivilegeService.insert(privilege));
    }
    @SuppressWarnings("unused")
	private void print(List<ApplicationPrivilege> list){
    	for(ApplicationPrivilege app:list){
    		System.out.println(app);
    	}
    	System.out.println("Totol Records:"+list.size()+"		========================");
    }
}

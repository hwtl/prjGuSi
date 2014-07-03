package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.ApplicationPrivilege;
import com.gusi.boms.model.RolePrivilege;

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
public class TestRolePrivilegeService extends BaseTest {
	@Autowired
	private RolePrivilegeService  rolePrivilegeService ;
    
    
    @Test
    public void insertRolePrivilege(){
    	RolePrivilege rp = new RolePrivilege();
    	rp.setRoleId(20);
    	rp.setPrivilegeId(30);
    	rp.setDataPrivilege(100);
    	Assert.assertTrue(rolePrivilegeService.insert(rp));
    }
    
    
    @Test
    public void deleteRolePrivilege(){
    	RolePrivilege rp = new RolePrivilege();
    	rp.setRoleId(20);
    	Assert.assertTrue(rolePrivilegeService.delete(rp));
    }
    
    @SuppressWarnings("unused")
	private void print(List<ApplicationPrivilege> list){
    	for(ApplicationPrivilege app:list){
    		System.out.println(app);
    	}
    	System.out.println("Totol Records:"+list.size()+"		========================");
    }
}

package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.Role;
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
public class TestRoleService extends BaseTest {
	@Autowired
	private RoleService  roleService ;
    @Test
	public void getRolesByAppId(){
    	Assert.assertEquals(roleService.getRolesByAppId(14).size(), 30);
    }
    
    
    @Test
    public void insertRole(){
    	Role role = new Role();
    	role.setAppId(14);
    	role.setRoleName("测试角色");
    	role.setRoleDesc("用来测试的角色");
    	role.setCreator(86455);
    	Assert.assertTrue(roleService.insert(role));
    }
    
    @Test
    public void updateApp(){
    	Role role = new Role();
    	role.setId(250);
    	role.setRoleName("测试角色2222");
    	role.setRoleDesc("用来测试的角色22222");
    	Assert.assertTrue(roleService.update(role));
    }
    
    @Test
    public void deleteApp(){
    	Assert.assertTrue(roleService.delete(250));
    }
    
    @SuppressWarnings("unused")
	private void print(List<Role> list){
    	for(Role role:list){
    		System.out.println(role);
    	}
    	System.out.println("Totol Records:"+list.size()+"		========================");
    }
}

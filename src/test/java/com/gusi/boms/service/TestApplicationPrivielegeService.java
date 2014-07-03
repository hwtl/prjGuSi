package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.Application;

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
public class TestApplicationPrivielegeService extends BaseTest {
	@Autowired
	private ApplicationService  applicationService ;
    @Test
	public void getAllowApps(){
    	Assert.assertEquals(applicationService.getAllowApps(80001).size(), 18);
    	Assert.assertEquals(applicationService.getAllowApps(86455).size(), 0);
    }
    
    
    @Test
    public void insertApp(){
    	Application app = new Application();
    	app.setApplicationCode("Test");
    	app.setApplicationName("测试系统");
    	Assert.assertTrue(applicationService.insert(app));
    }
    
    @Test
    public void deleteApp(){
    	Application app = new Application();
    	app.setId(21);
    	Assert.assertTrue(applicationService.delete(app));
    }
    
    @SuppressWarnings("unused")
	private void print(List<Application> list){
    	for(Application app:list){
    		System.out.println(app);
    	}
    	System.out.println("Totol Records:"+list.size()+"		========================");
    }
}

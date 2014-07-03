package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.Organization;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
* @ClassName: TestOrganizationNameService 
* @Description: 组织名称业务逻辑测试
* @author fdj
* @date 2013-4-18 下午4:17:38
 */
public class TestOrganizationNameService extends BaseTest {
	
	@Autowired
	private OrganizationNameService orgNameService; 
	
	private Organization org;
	
	@Before
	public void init() {
		org = new Organization();
		org.setId(30002);
		org.setOrgName("研发特别asfdasl部门");
	}
	
	@Test
	public void testAdd() {
		orgNameService.insertOrUpdate(org);
	}
	
	@Test
	public void testFindById() {
		System.out.println(orgNameService.findById(30002));
	}
}

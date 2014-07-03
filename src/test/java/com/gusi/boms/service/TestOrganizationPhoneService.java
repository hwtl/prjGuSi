package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.OrganizationPhone;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
* @ClassName: TestOrganizationPhoneService 
* @Description: TestOrganizationPhoneService
* @author fdj
* @date 2013-4-10 上午10:53:38
 */
public class TestOrganizationPhoneService extends BaseTest {
	@Autowired
	OrganizationPhoneService organizationPhoneService;
	
	private OrganizationPhone organizationPhone;
	
	@Before
	public void init() {
		organizationPhone = new OrganizationPhone();
		organizationPhone.setLine("1#");
		organizationPhone.setOrgId(99998);
		organizationPhone.setPhone("58241359");
	}
	
	@Test
	public void testFindById() {
		System.out.println(organizationPhoneService.findById(1743));
	}
	
	@Test
	public void testInsert() {
		System.out.println(organizationPhoneService.insert(organizationPhone));
	}
	
	@Test
	public void testDelete() {
		System.out.println(organizationPhoneService.delete(5470));
	}
	
	@Test
	public void testDeleteByOrgId() {
		System.out.println(organizationPhoneService.deleteByOrgId(99999));
	}
	
	@Test
	public void testUpdate() {
		organizationPhone.setId(5473);
		organizationPhone.setLine("2#");
		organizationPhone.setOrgId(99992);
		organizationPhone.setPhone("58241352");
		System.out.println(organizationPhoneService.update(organizationPhone));
	}
	
//	@Test
//	public void testAddPhoneList() {
//		List<OrganizationPhone> list = new ArrayList<OrganizationPhone>();
//		list.add(organizationPhone);
//		list.add(organizationPhone);
//		list.add(organizationPhone);
//		organizationPhoneService.addPhoneList(list, organizationPhone.getOrgId());
//	}
	
	@Test
	public void testQueryForListByOrgId() {
		List<OrganizationPhone> list = organizationPhoneService.queryForListByOrgId(99999);
		for(OrganizationPhone op : list) {
			System.out.println(op);
		}
	}
}

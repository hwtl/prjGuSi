package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationOperateHistory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 
* @ClassName: TestOrganizationOperateHistoryService 
* @Description: TestOrganizationOperateHistoryService
* @author fdj
* @date 2013-4-11 上午9:11:54
 */
public class TestOrganizationOperateHistoryService extends BaseTest {
	
	@Autowired
	OrganizationOperateHistoryService orgOpeHisService;
	
	private Organization organization;
	private OrganizationOperateHistory orgOperateHistory;
	
	@Before
	public void init() {
		organization =  new Organization();
		organization.setOrgName("特别部门");
		organization.setOrgCode("" + Math.random());
		organization.setParentId(22);
		organization.setOrgType("分行");
		organization.setStatus(1);
		organization.setOpenDate(new Date());
		
		orgOperateHistory = new OrganizationOperateHistory();
		orgOperateHistory.setOrgId(20513);
		orgOperateHistory.setCreateName("宋东风");
		orgOperateHistory.setCreator(81820);		
	}
	
	@Test
	public void testFindById() {
		List<OrganizationOperateHistory> list = orgOpeHisService.queryForListByOrgId(20079);
		for(OrganizationOperateHistory ooh : list) {
			System.out.println(ooh);
		}
	}
	
	@Test
	public void testSaveOperateHistory() {
		organization.setId(20646);
		System.out.println(orgOpeHisService.saveOperateHistory(organization, orgOperateHistory));
	}
}

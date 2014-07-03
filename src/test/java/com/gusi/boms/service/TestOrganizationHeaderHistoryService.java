package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.OrganizationHeaderHistory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
* @ClassName: TestOrganizationHeaderHistoryService 
* @Description: TestOrganizationHeaderHistoryService
* @author fdj
* @date 2013-4-11 下午5:59:31
 */
public class TestOrganizationHeaderHistoryService extends BaseTest {
	@Autowired
	OrganizationHeaderHistoryService orgHeaderHisService;
	
	@Test
	public void testQueryForListByOrgId() {
		List<OrganizationHeaderHistory> list = orgHeaderHisService.queryForListByOrgId(20008);
		for(OrganizationHeaderHistory ohh : list) {
			System.out.println(ohh);
		}
	}
}

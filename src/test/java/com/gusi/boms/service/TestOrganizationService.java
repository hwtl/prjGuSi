package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationOperateHistory;
import com.gusi.boms.model.VOrganization;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.web.common.Paginate;
import org.junit.Before;
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
public class TestOrganizationService extends BaseTest {
	@Autowired
    Organization2Service orgnizationService;

	private Organization organization;

	private OrganizationOperateHistory organizationOperateHistory;

	private EmployeeBaseInfor emplopeeBaseInfor;

	@Before
	public void init() {
		organization =  new Organization();
		organization.setOrgName("测试部门");
		organization.setParentId(1);
		organization.setOrgType("分行");
		organization.setStatus(1);

		organization.setCreator(90378);
		organization.seteOrgName("Test Department");
		organization.seteAddress("NanJin Road");
		organization.setAddress("南京西路");
		organization.setOrgClass("后台");

		organizationOperateHistory = new OrganizationOperateHistory();
		organizationOperateHistory.setCreator(81820);
		organizationOperateHistory.setCreateName("宋东风");
		organizationOperateHistory.setFiled(Constants.ORG_INFO_BASE);


		emplopeeBaseInfor = new EmployeeBaseInfor();
		emplopeeBaseInfor.setUserCode(80011);
		emplopeeBaseInfor.setOrgId(20684);
		emplopeeBaseInfor.setUserNameCn("张三");
		emplopeeBaseInfor.setPositionId(22);

	}

	@Test
	public void testFindById() {
		System.out.println(orgnizationService.findById(1));
	}

	@Test
	public void testInsertAndReturnId() {
		System.out.println(orgnizationService.insertAndReturnId(organization));
	}

	@Test
	public void testdelete() {
		System.out.println(orgnizationService.delete(20642));
	}

	@Test
	public void testUpdate() {
		organization.setId(20646);
		System.out.println(orgnizationService.update(organization));
	}

//	@Test
//	public void testGetParentOrg() {
//		System.out.println(orgnizationService.getParentOrg(organization));
//	}

//	@Test
//	public void testAddOrg() {
//		orgnizationService.addOrg(organization);
//	}

//	@Test
//	public void testAddManager() {
//		emplopeeBaseInfor.setOrgId(20684);
//		System.out.println(orgnizationService.addManager(emplopeeBaseInfor));
//	}
//
//	@Test
//	public void testDeleteManager() {
//		emplopeeBaseInfor.setOrgId(20684);
//		System.out.println(orgnizationService.deleteManager(emplopeeBaseInfor));
//	}

//	@Test
//	public void testUpdateBaseInfo() {
//		Organization organization2 = new Organization();
//		organization2.setId(20684);
//
//		organization2.setOrgName("测试部门_测试");
//		organization2.setParentId(1);
//		organization2.setOrgType("分行_测试");
//		organization2.setStatus(0);
//
//		organization2.setCreator(90378);
//		organization2.seteOrgName("Test Department_测试");
//		organization2.seteAddress("NanJin Road_测试");
//		organization2.setAddress("南京西路_测试");
//		organization2.setOrgClass("后台_测试");
//
//		organizationOperateHistory.setOrgId(20684);
//
//		System.out.println(orgnizationService.updateBaseInfo(organization2, organizationOperateHistory));
//	}

	@Test
	public void testUpdateContactInfo() {
		Organization organization2 = new Organization();
		organization2.setId(20682);
		organization2.setOrgFax("68001121");
		organization2.setAddress("不知道");
		organization2.setUpdator(80011);
		System.out.println(organization2);
		System.out.println(orgnizationService.updateContactInfo(organization2));
	}

	@Test
	public void testFindByName() {
		System.out.println(orgnizationService.findByName("上海德佑房地产经纪有限公司","德佑"));
	}

	@Test
	public void testIsExistByName() {
		System.out.println(orgnizationService.isExistByName("上海德佑房地产经纪有限公司","德佑"));
	}

	@Test
	public void testCheckName() {
		System.out.println(orgnizationService.checkName(1, "上海德佑房地产经纪有限公司","德佑")); //true
		System.out.println(orgnizationService.checkName(0, "上海德佑房地产经纪有限公司","德佑")); //false
		System.out.println(orgnizationService.checkName(1, "测试店名","德佑"));			  //true
		System.out.println(orgnizationService.checkName(0, "测试店名","德佑"));			  //true
		System.out.println(orgnizationService.checkName(0, "","德佑"));				  //false

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testQueryForPaginate() {
		VOrganization o = new VOrganization();
		o.setStatus(-9);
		o.setOrgName("部");
		Paginate p = orgnizationService.queryForPaginate(o, 1);
		System.out.println(p.getTotalCount());
		List<Organization> list = p.getPageList();
		for(Organization org : list) {
			System.out.println(org);
		}
	}

	@Test
	public void testQuerySonOrgs() {
		List<Organization> list = orgnizationService.querySonOrgs(1);
		for(Organization org : list) {
			System.out.println(org);
		}
	}

//	@Test
//	public void testQueryListByOrgIds() {
//		List<Organization> list = orgnizationService.queryListByOrgIds("1,4,5,22");
//		for(Organization org : list) {
//			System.out.println(org);
//		}
//	}

//	@Test
//	public void testUpdateStatus() {
//		System.out.println(orgnizationService.closeOrganization(20685));
//		System.out.println(orgnizationService.closeOrganization("20684,20683"));
//	}

//	@Test
//	public void testIsHasUpdateCount() {
//		System.out.println(orgnizationService.isHasUpdateCount(1));
//	}

//	@Test
//	public void testQueryForListByPy() {
//		List<Organization> list = orgnizationService.queryForListByPy("时间");
//		for(Organization o : list) {
//			System.out.println(o);
//		}
//	}

	@Test
	public void testGetRoot() {
		System.out.println(orgnizationService.getRoot("德佑"));
	}

	@Test
	public void testQueryDooiooTree() {
		List<Organization> list = orgnizationService.queryOrgTree("德佑");
		for(Organization o : list) {
			System.out.println(o);
		}
		System.out.println(list.size());
	}

    @Test
    public void testGetSales() {
        List<Organization> list = orgnizationService.querySales(Company.Dooioo.toString());
        for(Organization o : list) {
            System.out.println(o);
        }
        System.out.println(list.size());
    }

    @Test
    public void testGetSalesWithoutStore() {
        List<Organization> list = orgnizationService.querySalesWithoutStore(Company.Dooioo.toString());
        for(Organization o : list) {
            System.out.println(o);
        }
        System.out.println(list.size());
    }

    @Test
    public void testGetSalesWithoutBranch() {
        List<Organization> list = orgnizationService.querySalesWithoutBranch(Company.Dooioo.toString());
        for(Organization o : list) {
            System.out.println(o);
        }
        System.out.println(list.size());
    }

    @Test
    public void testGetSupport() {
        List<Organization> list = orgnizationService.querySupport(Company.Dooioo.toString());
        for(Organization o : list) {
            System.out.println(o);
        }
        System.out.println(list.size());
    }

    @Test
    public  void testGetSalesRoot() {
        System.out.println(orgnizationService.getSalesRoot(22));
    }

}

package com.gusi.boms.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.BaseTest;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.AccountingDepartment;
import com.gusi.boms.model.Organization;
import com.gusi.boms.util.GeneratorUtil;
import com.dooioo.plus.oms.dyEnums.Company;

/**
 * excel 导入生成空组
 * @author "liuhui"
 * create At 2013-11-29 下午5:09:33
 */
public class EmptyOrganizationHelper extends BaseTest {
	@Autowired
	Organization2Service organization2Service;
	@Autowired
	AccountingDepartmentService accountingDepartmentService;
	/**
	  * 创建空组* 
	  * 1，从excel读取空组名以及空组对应的门店ID ,列一： parentId,列二：orgName
	  * 2, 新生成空组，关组 status=-1,startTime=2013-11-01,endTime=2013-11-30,公司等等
	  * 3,根据空组生成的orgId生成核算组织
	  *    （1） 核算组织状态为2，负责人为空，务必赋值状态status=2,标示为空组。。
	  * 4，将global.properties改为production
	 */
	@Test
	public void createEmptyOrgFromExcel()
	{
		String fileName="C:\\Users\\Mr.Cactus\\Desktop\\data.xls";
		int sheetIndex=0;
		List<EmptyOrg> orgs=findEmptyOrgs(fileName,sheetIndex);
		int count=0;
		List<EmptyOrg> errors=new ArrayList<>();
		//excel数据 第一列时parentId,第二列时orgName
		for (EmptyOrg emptyOrg : orgs) {
			//System.out.println(emptyOrg);	
			if (saveOrg(emptyOrg)) {
			   count++;
			}else{
				errors.add(emptyOrg);
			}
		}
		System.out.println("总共需处理："+orgs.size()+",成功处理："+count);
		System.out.println("生成错误（parent是门店的不存在或者已经生成过的）的空组："+errors.size());
		for (EmptyOrg eo : errors) {
			System.out.println(eo);
		}
	}
	 @Transactional
     private boolean saveOrg(EmptyOrg emptyOrg){
    	 System.out.println("处理中："+emptyOrg);
    	 Map<String,Object> params=new HashMap<>();
    	 params.put("parentId", emptyOrg.getParentId());
    	 params.put("orgName", emptyOrg.getOrgName());
    	 if (organization2Service.count("Organization.ifEmptyOrgExists",params)>0) {
				//已经存在空组 
         	 System.out.println("已经存在空组，生成失败,门店ID："+emptyOrg.getParentId());
         	 return false;
    	 }
    	 Organization parentOrg = organization2Service.findById(emptyOrg.getParentId());
    	 Organization organization=new Organization();
    	 organization.setOrgName(emptyOrg.getOrgName());
    	 organization.setOrgType("分行");
    	 organization.setOrgClass("前台");
    	 organization.setRemark("系统生成空组");    	 
    	 organization.setParentId(emptyOrg.getParentId());
    	 organization.setStatus(-1);
    	 
    	 Calendar endDate=Calendar.getInstance();
    	 endDate.set(2013, 10, 30, 0, 0, 0);
    	 endDate.set(Calendar.MILLISECOND, 0);
    	 Calendar startDate=Calendar.getInstance();
    	 startDate.set(2013, 10, 1, 0, 0, 0);
    	 startDate.set(Calendar.MILLISECOND, 0);
    	 organization.setCompany(Company.Dooioo.toString());
    	 
    	 organization.setClosedDate(endDate.getTime());
    	 organization.setOpenDate(startDate.getTime());
    	 
    	 int creator=80001;
    	 organization.setCreator(creator);
         if(parentOrg != null && parentOrg.getOrgType().equals("门店")) {
             organization.setOrgLevel(parentOrg.getOrgLevel() + 1);
             organization.setLocIndex(organization2Service.getLocIndex(parentOrg));
             organization.setOrgCode(GeneratorUtil.generateOrgCode());
             organization.setOrgLongCode(GeneratorUtil.generateOrgLongCode(parentOrg.getOrgLongCode(), GeneratorUtil.generateOrgCode()));
             Integer orgId= organization2Service.insertAndReturnId(organization);
             emptyOrg.setOrgId(orgId);
			AccountingDepartment ad=new AccountingDepartment();
			ad.setCreator(creator);
			ad.setOrgId(orgId);
			ad.setOrgName(emptyOrg.getOrgName());
			ad.setStatus(2);
			ad.setStartTime(startDate.getTime());
			emptyOrg.setAccountingOrgName(emptyOrg.getOrgName());
			emptyOrg.setAccountingId(accountingDepartmentService.insertAndReturnId("AccountingDepartment.insertEmpty",ad)) ; 
			return true;
         }else{
        	 System.out.println("生成失败，父类组织："+parentOrg);
         }
         return false;
     }
     
	
	/**
	  * 
	  * @param fileName 文件名
	  * @param sheetIndex  sheetIndex,从0开始。
	  * @return
	  *  返回空组集合
	 */
	private List<EmptyOrg> findEmptyOrgs(String fileName,int sheetIndex)
	{
		List<EmptyOrg> list=new ArrayList<EmptyOrg>();
		try{  
	         FileInputStream is = new FileInputStream(new File(fileName));  
	         HSSFWorkbook wb=new HSSFWorkbook(is);  
	         //读取sheet 
	         HSSFSheet childSheet = wb.getSheetAt(sheetIndex);  
	         //childSheet.getLastRowNum() 从0开始算起
	         int rowNum =childSheet.getLastRowNum()+1; 
	         EmptyOrg org=null;
             for(int j=0;j<rowNum;j++)  
             {  
                 HSSFRow row = childSheet.getRow(j);   
                   org=new EmptyOrg();
                  org.setParentId(Double.valueOf(row.getCell(0).getNumericCellValue()).intValue());
                  org.setOrgName(row.getCell(1).getStringCellValue().replaceAll("（", "(")
                		  .replaceAll("）", ")"));
                  list.add(org);
             }  
	               
	     }catch(Exception e)  
	     {  
	         e.printStackTrace();  
	     } 
		return list;
	}
	
	private class EmptyOrg
	{
		private String orgName;//组织名称
		private Integer parentId;//父类ID
		private Integer orgId;//组织ID
		private Integer accountingId;//核算部门Id
		private String accountingOrgName;//核算部门名称
		public String getOrgName() {
			return orgName;
		}
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		public Integer getParentId() {
			return parentId;
		}
		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}
		public EmptyOrg() {
			super();
			
		}
		public Integer getOrgId() {
			return orgId;
		}
		public void setOrgId(Integer orgId) {
			this.orgId = orgId;
		}
		public Integer getAccountingId() {
			return accountingId;
		}
		public void setAccountingId(Integer accountingId) {
			this.accountingId = accountingId;
		}
		public String getAccountingOrgName() {
			return accountingOrgName;
		}
		public void setAccountingOrgName(String accountingOrgName) {
			this.accountingOrgName = accountingOrgName;
		}
		@Override
		public String toString() {
			return "EmptyOrg [orgName=" + orgName + ", parentId=" + parentId
					+ ", orgId=" + orgId + ", accountingId=" + accountingId
					+ ", accountingOrgName=" + accountingOrgName + "]";
		}
	}
	 
}

package com.gusi.boms.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.gusi.boms.service.AccountingDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.AccountingDepartment;
import com.gusi.boms.model.EASCustomer;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.model.Organization;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.helper.JsonResult;
/**
 * 
* @ClassName: OrganizationController 
* @Description:核算部门控制器
* @author fdj
* @date 2013-4-11 下午2:19:51
 */
@Controller
@RequestMapping(value="/accounting")
public class AccountingController extends BomsBaseController {

	@Autowired
	private OrganizationService organizationService;
    @Autowired
    private AccountingDepartmentService accountingDepartmentService;
    
    @RequestMapping(method=RequestMethod.GET)
    public String list(HttpServletRequest request,Model model,@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
    		EASCustomer customer)
    {
    	Employee emp=getSesionUser(request);
    	if (!emp.checkPrivilege(Constants.VIEW_ACCOUNTING_DEPT)) {
    		return errorNoPrivilege(model);
    	}
    	customer.setCompany(emp.getCompany());
    	Paginate paginate=accountingDepartmentService.findForPaginate(customer,pageNo);
    	model.addAttribute("paginate", paginate);
    	model.addAttribute("customer", customer);
    	return "/accounting/list";
    }
    /**
     * @param request
     * @param model
     * @param customer
     * @return 对应关系详情
     */
    @RequestMapping(value="/{customer}/relationDetail",method=RequestMethod.POST)
    public String relationDetail(HttpServletRequest request,Model model,
    		@PathVariable(value="customer")String customer)
    {
    	//没有权限
    	if (!getSesionUser(request).checkPrivilege(Constants.MANAGE_ACCOUNTING_DEPT)) {
    		return "";
		}
    	EASCustomer c=accountingDepartmentService.findByCustomer(customer,getSesionUser(request).getCompany());
    	if (c==null) {
			return "";
		}
    	model.addAttribute("customer",c);
    	return "/accounting/manage";
    }
    
    /**
   	 * @return 查找是否有客户对应关系。
   	 */
   	@RequestMapping(value="/existRelation/{customerEasCode}")
   	@ResponseBody
    public JsonResult existRelation(HttpServletRequest request,
    		@PathVariable(value="customerEasCode")String customerEasCode){
    	//首先根据对应关系金蝶编码查找对应关系
        return ok().put("customers",accountingDepartmentService.findByEASCustomerCode(customerEasCode,
        		getSesionUser(request).getCompany()));
   	}
    /**
	 * @return 保存客户对应关系。
	 */
	@RequestMapping(value="/saveCustomerRelation",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult saveCustomerRelation(EASCustomer customer,
			HttpServletRequest request)
	{
		 if (getSesionUser(request).checkPrivilege(Constants.MANAGE_ACCOUNTING_DEPT)) {
			try {
				 customer.setCompany(getSesionUser(request).getCompany());
				 customer.setOperator(getSesionUser(request).getUserCode());
				if (accountingDepartmentService.saveCustomerRelation(customer)){
					return ok();
				}
				return fail("操作失败，请及时联络相关负责人。");
			} catch (Throwable e) {
				return fail("操作失败，详情："+e.getMessage());
			}
		 }else
		 {
			 return fail("您没有操作权限。");
		 }
	}
    /**
     * @param accountingDepartment
     * @param request
     * @return 根据日期查询负责人
     */
    @RequestMapping(value="/findEASCustomers",method= RequestMethod.GET)
	public @ResponseBody String findEASCustomers(HttpServletRequest request) {
    	 String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
         String remark = WebUtils.findParamStr(request, "remark","");
         return jsoncallback + "(" + ok().put("customers", accountingDepartmentService.findEASCustomers(remark)) + ")";
	}
    /**
     * @param orgId
     * @param request
     * @return 新增核算部门
     */
    @RequestMapping(value="/{orgId}/addAccountingDept")
    public String addAccountingDept(@PathVariable(value="orgId")int orgId,Model model,
    		HttpServletRequest request)
    {
    	//check privilege
    	if (!getSesionUser(request).checkPrivilege(Constants.ADD_ACCOUNTING_DEPT)) {
			return errorNoPrivilege(model);
		}
    	//查找核算部门
    	 AccountingDepartment ad=accountingDepartmentService.findForAdd(orgId);
    	 if (ad==null) {
			return error(model, "组织不存在或者该组织没有负责人。");
		}
    	 model.addAttribute("accountingDepartment",ad);
    	//查找此部门所有负责人
    	 model.addAttribute("managers",accountingDepartmentService.findPrincipals(orgId));
    	 //核算部门记录
    	 model.addAttribute("histories", accountingDepartmentService.findDepartmentsByOrgId(orgId));
    	return "/accounting/transfer";
    }
    /**
     * @param request
     * @param accountingDepartment
     * @return 保存核算部门
     */
    @RequestMapping(value="/saveAccountingDept",method=RequestMethod.POST)
    public String saveAccountingDept(HttpServletRequest request,Model model,
    		AccountingDepartment dept)
    {
    	if (!getSesionUser(request).checkPrivilege(Constants.ADD_ACCOUNTING_DEPT)) {
			return errorNoPrivilege(model);
		}
    	int orgId=dept.getOrgId();
    	Organization org= organizationService.getOrgByOrgId(orgId);
    	if (!accountingDepartmentService.saveAccountingDepartment(
    			dept.getStartTime(),dept.getEndTime(),dept.getOrgName(),orgId,org.getOrgName(),
    			org.getOrgType(),dept.getManager(),getSesionUser(request).getUserCode(),false)) {
			return error(model, "新增核算部门出错。");
		}
    	return "redirect:/accounting/"+orgId+"/addAccountingDept";
    }
    /**
     * @param request
     * @return 验证核算部门名称是否重复
     */
    @RequestMapping(value="/checkAccountingDeptName",method= RequestMethod.GET)
	public @ResponseBody JsonResult checkAccountingDeptName(HttpServletRequest request) {
		int orgId = WebUtils.findParamInt(request, "orgId");
		String orgName = WebUtils.findParamStr(request, "orgName");
		if(accountingDepartmentService.existSameDepartment(orgId, orgName)) {
			return fail("核算部门名称重复。");
		}
		return ok();
	}
    
    /**
     * @param accountingDepartment
     * @param request
     * @return 根据日期查询负责人
     */
    @RequestMapping(value="/queryManagersByDate",method= RequestMethod.GET)
	public @ResponseBody JsonResult queryManagersByDate(AccountingDepartment accountingDepartment,
			HttpServletRequest request) {
    	Date pageStartTime=accountingDepartment.getStartTime();
		Date pageEndTime=accountingDepartment.getEndTime();
		if (pageEndTime!=null && pageStartTime !=null && pageEndTime.before(pageStartTime)) 
		{
			 return ok();
		}else{
			return ok().put("managers",accountingDepartmentService.findPrincipals(accountingDepartment.getOrgId(),
					pageStartTime, pageEndTime));
		}
	}
    /**
	 * @return 验证新添加核算部门的日期
	 */
    @RequestMapping(value="/validateAccountingDate",method= RequestMethod.GET)
	public @ResponseBody JsonResult validateAccountingDate(AccountingDepartment accountingDepartment,
			HttpServletRequest request){
		//页面日期返回
		Date pageStartTime=accountingDepartment.getStartTime();
		Date pageEndTime=accountingDepartment.getEndTime();
		if (pageStartTime==null) {
			return fail("核算部门起始时间不能为空。");
		}
		if (pageEndTime!=null && pageEndTime.before(pageStartTime)) {
			return fail("核算部门的起始时间不能大于截止时间。");
		}
		//获取起始日期返回
		AccountingDepartment ad=accountingDepartmentService.findMaxStartTime(accountingDepartment.getOrgId());
		if (ad ==null) {
			return fail("此组织关联的核算部门不存在。");
		}
		//根据orgId得到的最大起始时间
		Date maxStartTime=ad.getStartTime();
		if (pageStartTime.before(maxStartTime) && pageEndTime ==null){
			return fail("请选择核算部门的截止时间。");
		}
		//检查起始截止时间是否有效
		if (!accountingDepartmentService.canAddAccountingDept(accountingDepartment.getOrgId(),
				pageStartTime, pageEndTime)) {
			return fail("请检查核算部门的开始时间或结束时间。");
		}
		return ok("您所选择核算部门的日期符合约定。");
	}
}

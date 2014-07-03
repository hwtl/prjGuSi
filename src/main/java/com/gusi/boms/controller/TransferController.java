package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.MailHelper;
import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationTransfer;
import com.gusi.boms.service.Organization2Service;
import com.gusi.boms.service.OrganizationTransferProxyService;
import com.gusi.boms.service.OrganizationTransferService;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.util.GlobalConfigUtil;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-22 下午8:15
 * @Description: 组织转调控制器
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/transfer/*")
public class TransferController extends BomsBaseController {

    private static final Log log = LogFactory.getLog(TransferController.class);
    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private OrganizationTransferService orgTransferService;
    @Autowired
    private Organization2Service organization2Service;
    @Autowired
    private OrganizationTransferProxyService organizationTransferProxyService;
    

    @RequestMapping(value = "/org/list", method = RequestMethod.GET)
    public String orgTransferList(Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!PrivilegeHelper.checkPrivileges(employee.getPrivileges(), Constants.ORGANIZATION_TRANSFER)) {
            errorNoPrivilege(model);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", WebUtils.findParamInt(request, "pageNo", 1));
        params.put("transferMode", WebUtils.findParamStr(request, "transferMode"));
        params.put("status", WebUtils.findParamStr(request, "status"));
        params.put("company", employee.getCompany());
        Paginate paginate = orgTransferService.findOrgTransfers(params);
        model.addAttribute("paginate", paginate);
        return "/transfer/orgTransferList";
    }
    
    /**
      * @since: 3.0.5 
      * @param request
      * @param model
      * @return
      * <p>
      * 删除未生效的转调记录
      * </p>   
     */
    @RequestMapping(value="/{id}/del",method=RequestMethod.POST)
    @ResponseBody
	public JsonResult delTransfer(HttpServletRequest request,Model model,
			 @PathVariable(value="id")Integer id)
	{
		if(!getSesionUser(request).checkPrivilege(
				Constants.ORGANIZATION_TRANSFER)){
			return fail("您没有操作权限。");
		}	
		if (organizationTransferProxyService.delTransfer(id, getSesionUser(request).getUserCode())) {
			return ok("操作成功");
		}
		return fail("操作失败，组织转调已生效或已取消。");
	}
    /**
      * @since: 3.0.5
      * @param request
      * @param model
      * @return
      * <p>
      * 检查组织是否重名
      * </p>   
     */
    @RequestMapping(value="/checkOrgName",method=RequestMethod.GET)
	public @ResponseBody JsonResult checkOrgName(HttpServletRequest request,Model model)
	{
    	String orgName=WebUtils.findParamStr(request, "orgName", null);
    	if (orgName==null) {
			return fail("请输入组织名称。");
		}
    	if (organization2Service.findByName(orgName,this.getSesionUser(request).getCompany()) !=null ) {
    		return fail("该组织名称已被使用，请输入其他名称。");
		};
		return ok();
	}

    /**
     * 整组转调页面跳转
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public String singleOrg(Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!employee.checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
            errorNoPrivilege(model);
        }
        return "/transfer/singleOrg";
    }
    /**
     * 双组对调页面跳转
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/branchSwap", method = RequestMethod.GET)
    public String branchSwap(Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!employee.checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
            errorNoPrivilege(model);
        }
        return "/transfer/branchSwap";
    }
    /**
     * 门店换区页面跳转
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/store", method = RequestMethod.GET)
    public String store(Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!employee.checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
            errorNoPrivilege(model);
        }
        return "/transfer/store";
    }
    /**
     * 处理多个门店换区
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/handleStoreTransfer", method = RequestMethod.POST)
    public String handleStoreTransfer(Model model, HttpServletRequest request,
    		OrganizationTransfer organizationTransfer){
         Employee emp = this.getSesionUser(request);
        if(!emp.checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
            errorNoPrivilege(model);
        }
        //转调门店信息
        String orgAIds=organizationTransfer.getOrgAIds();
        if (StringUtils.isEmpty(orgAIds)) {
        	return errorNew(model, "请选择待转区的门店。");
		}
        String[] storeIds=orgAIds.split(",");
        if (storeIds==null || storeIds.length==0) {
        	return errorNew(model, "请选择待转区的门店。");
        }
        Date activeDate=organizationTransfer.getActiveDate();
        if (activeDate==null) {
        	return errorNew(model, "请选择转调生效日期。");
		}
        int areaId=organizationTransfer.getOrgBParentId();
        if (areaId==0) {
			return errorNew(model, "请选择门店转调后的区域。");
		}
        Organization area=organization2Service.findById(areaId);
    	if (area==null || !Organization.ORG_TYPE_AREA.equals(area.getOrgType())) {
			return errorNew(model, "门店转调后的区域不存在，请确认。");
		}
    	//生效日期不能早于门店以及区域开组日期。
    	if (area.getOpenDate()==null || activeDate.before(area.getOpenDate())) {
    		return errorNew(model, area.getOrgName()+"的开组日期为空或者转调生效日期早于"
    				+area.getOrgName()+"开组日期，请确认。");
		}
    	//检查是否有门店已经在新区里
    	if (!orgTransferService.checkStoreHasInArea(orgAIds,areaId)){
    		return errorNew(model, "您所选的门店已在"+area.getOrgName()+"下，请确认。");
		}
		//检查此组织orgAIds是否还有转调记录未生效。
	   	if(!orgTransferService.checkStoreTransfer(orgAIds)) {
	   		return errorNew(model, Constants.TRANSFER_WORDS_SORRY + "您所选门店" + Constants.TRANSFER_WORDS_INFO);
	   	}
        Set<Integer> storeSet=new HashSet<>();
        //防止重复StoreId
        for (String sid : storeIds) {
      	  storeSet.add(Integer.valueOf(sid));
        }
        Organization orgA =null;
        int parentBId=organizationTransfer.getOrgBParentId();
        int creator=emp.getUserCode();
        int mode=OrganizationTransfer.MODE_DEPT_TRANSFER;
        
        List<OrganizationTransfer> lts=new ArrayList<>();
        StringBuilder orgNames=new StringBuilder();
        for (Integer storeId : storeSet) {
      	  	orgA=organization2Service.findById(storeId);
      	  	if (orgA.getOpenDate()==null || activeDate.before(orgA.getOpenDate())) {
				orgNames.append(orgA.getOrgName()+",");
			}else{
      	  	//生效时间
      	  	lts.add(new OrganizationTransfer(mode, storeId, orgA.getOrgName(), orgA.getParentId(),
      			  storeId, orgA.getOrgName(), parentBId, activeDate,OrganizationTransfer.TRANSFER_TYPE_TEMP, creator));
			}
		 }
	   	try {
	   		 if (orgNames.length() ==0) {
	   			organizationTransferProxyService.saveStoreTransfer(lts,emp);
	   		  }else{
	   			return errorNew(model, "您所选的以下门店:"+orgNames+"开组日期为空或者开组日期早于转调生效日期，请确认。"); 
	   		 }
			} catch (Exception e) {
				log.error("门店转区转调记录："+organizationTransfer,e);
				mailHelper.sendMail("【"+GlobalConfigUtil.getCurrentEnv()+"】门店转区失败，请确认！！",e);
				return errorNew(model, "门店转区失败，请及时反馈。");
		}
	   	return "redirect:/transfer/org/list";
    }
    /**
     * @since: 3.0.5
     * @param request
     * @param model
     * @return
     * <p>
     * 处理两组对调
     * 1，校验对调组织是否存在
     * 2，校验生效日期是否早于对调组织生效日期
     * 3，新增核算组织，将之前的核算组织endTime赋值。
     * 4，发送AD信息以及MQ消息
     * 4，发送核算部门修改的MQ消息
     * </p>   
    */
   @RequestMapping(value="/handleBranchSwap",method=RequestMethod.POST)
	public String handleBranchSwap(HttpServletRequest request,Model model,
			OrganizationTransfer organizationTransfer){
   	   Employee emp=getSesionUser(request);
   	  if(!emp.checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
            errorNoPrivilege(model);
        }
 	    Date activeDate=organizationTransfer.getActiveDate();
 	   if (activeDate ==null) {
 		   return errorNew(model, "请选择转调生效日期。");
 	    }
		//单组转调的组织ID
		int orgAid=organizationTransfer.getOrgAId();
		if (orgAid == 0) {
			return errorNew(model, "对调的组织不存在。");
		}
		int orgBid=organizationTransfer.getOrgBId();
		if (orgBid == 0) {
			return errorNew(model, "对调的组织不存在。");
		}
		if (orgAid==orgBid) {
			return errorNew(model, "对调分行是同一个组织，请确认。");
		}
		//无负责人的分行不生成核算组织
		//查看OrgA是否存在
		Organization orgA= organization2Service.findById(orgAid);
		if (orgA==null || !orgA.getOrgType().equals(Organization.ORG_TYPE_FH)) {
			return errorNew(model, "对调的分行不存在。");
		}
		//查看OrgB是否存在
		Organization orgB= organization2Service.findById(orgBid);
		if (orgB==null || !orgB.getOrgType().equals(Organization.ORG_TYPE_FH)) {
			return errorNew(model, "对调的分行不存在。");
		}
		//检查生效时间，转调生效时候不能早于对调组织的生效日期
		if (orgA.getOpenDate()==null || orgB.getOpenDate() ==null 
				||activeDate.before(orgA.getOpenDate()) ||activeDate.before(orgB.getOpenDate())) {
			return errorNew(model, " 对调分行的开组日期为空，转调生效日期不能早于对调分行的开组日期，请确认。");
		}
		//检查此组织orgAId和orgBId是否还有记录未生效。
	   	if(!orgTransferService.checkTransfer(organizationTransfer.getOrgAId())) {
	   		return errorNew(model, Constants.TRANSFER_WORDS_SORRY + organizationTransfer.getOrgAName() + Constants.TRANSFER_WORDS_INFO);
	   	}
	   	if(!orgTransferService.checkTransfer(organizationTransfer.getOrgBId())) {
	   		return errorNew(model, Constants.TRANSFER_WORDS_SORRY + organizationTransfer.getOrgBName() + Constants.TRANSFER_WORDS_INFO);
	   	}
	   	try {
	   		 //设置转调模式
	   		organizationTransfer.setTransferMode(OrganizationTransfer.MODE_DOUBLE);
	   		organizationTransfer.setStatus(OrganizationTransfer.TRANSFER_TYPE_TEMP);
	   		organizationTransfer.setOrgAParentId(orgA.getParentId());
	   		organizationTransfer.setOrgBParentId(orgB.getParentId());
	   		organizationTransfer.setCreator(emp.getUserCode());
	   		
	   		organizationTransferProxyService.saveBranchTransfer(organizationTransfer,emp);
			} catch (Exception e) {
				log.error("两组对调失败,转调记录："+organizationTransfer,e);
				mailHelper.sendMail("【"+GlobalConfigUtil.getCurrentEnv()+"】两组对调失败，请确认！！",e);
				return errorNew(model, "两组对调失败，请及时反馈。");
			}
	   	return "redirect:/transfer/org/list";
	}
    
    /**
      * @since: 3.0.5
      * @param request
      * @param model
      * @return
      * <p>
      * 处理单组转调
      * 1，校验转调组织是否存在
      * 2，校验生效日期是否早于门店组织生效日期
      * 3，新增核算组织，将之前的核算组织endTime赋值。
      * 4，发送AD信息以及MQ消息
      * 4，发送核算部门修改的MQ消息
      * </p>   
     */
    @RequestMapping(value="/handleSingleOrg",method=RequestMethod.POST)
	public String handleSingle(HttpServletRequest request,Model model,
			OrganizationTransfer organizationTransfer){
    	 Employee emp=getSesionUser(request);
    	 if(!emp.checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
             errorNoPrivilege(model);
         }
    	 Date activeDate=organizationTransfer.getActiveDate();
    	 if (activeDate ==null) {
    		 return errorNew(model, "请选择转调生效日期。");
    	 }
		//单组转调的组织ID
		int orgAid=organizationTransfer.getOrgAId();
		if (orgAid == 0) {
			return errorNew(model, "待转调的组织不存在。");
		}
		
		int parentOrgId=organizationTransfer.getOrgBParentId();
		if (parentOrgId == 0) {
			return errorNew(model, "请选择转调后分行所在的门店。");
		}
		//检查组织是否重名
		String orgBName=organizationTransfer.getOrgBName();
		if (StringUtils.isEmpty(orgBName)) {
			return errorNew(model, "请输入转调分行名称。");
		}
		if (organization2Service.findByName(orgBName, emp.getCompany()) !=null) {
			return errorNew(model, "转调后分行名称已存在，请重新输入。");
		}    	
		//查看OrgA是否存在
		//可以转调临时组织，没有负责人则不生成核算组织。
		Organization orgA= organization2Service.findById(orgAid);
		if (orgA==null || !orgA.getOrgType().equals(Organization.ORG_TYPE_FH)) {
			return errorNew(model, "待转调的分行不存在。");
		}
		//检查生效时间，转调生效时候不能早于parent组织的生效日期
		if (orgA.getOpenDate()==null || activeDate.before(orgA.getOpenDate())) {
			return errorNew(model, orgA.getOrgName()+" 的开组日期为空或者转调生效日期早于分行开组日期，请确认。");
		}
		Organization parentOrg=organization2Service.findById(parentOrgId);
		if (parentOrg==null ) {
			return errorNew(model, "转调门店不存在，请确认。");
		}
		if (parentOrg.getOpenDate()==null || activeDate.before(parentOrg.getOpenDate())) {
			return errorNew(model, parentOrg.getOrgName()+" 的开组日期为空或转调生效日期早于门店开组日期，请确认。");
		}
		//检查此组织orgAId是否还有记录未生效。
    	if(!orgTransferService.checkTransfer(organizationTransfer.getOrgAId())) {
    		return errorNew(model, Constants.TRANSFER_WORDS_SORRY + organizationTransfer.getOrgAName() + Constants.TRANSFER_WORDS_INFO);
    	}
    	try {
    		 //设置转调模式
    		organizationTransfer.setTransferMode(OrganizationTransfer.MODE_SINGLE);
    		//转调状态
    		organizationTransfer.setStatus(OrganizationTransfer.TRANSFER_TYPE_TEMP);
    		organizationTransfer.setOrgAParentId(orgA.getParentId());
    		organizationTransfer.setCreator(emp.getUserCode());
    		organizationTransferProxyService.saveBranchTransfer(organizationTransfer,emp);
		} catch (Exception e) {
			log.error("单组转调失败,转调记录："+organizationTransfer,e);
			mailHelper.sendMail("【"+GlobalConfigUtil.getCurrentEnv()+"】单组转调失败，请确认！！",e);
			return errorNew(model, "单组转调失败，请及时反馈。");
		}
    	return "redirect:/transfer/org/list";
	}
    /**
      * @since: 3.0.5 
      * @param request
      * @return
      * <p>
      * 组织转调生效接口
      * </p>   
     */
    @RequestMapping(value = "/org/active", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult orgActive(HttpServletRequest request) {
    	if (getSesionUser(request).checkPrivilege(Constants.ORGANIZATION_TRANSFER)) {
    	   organizationTransferProxyService.doActiveOrgTransferJob();
      	   log.info("---------------------------结束处理整组转调生效--------------");
    	   return ok("所有当前时间生效的整组转调已经生效！");
		}
    	return fail("没有权限。");
    }
}

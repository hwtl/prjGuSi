
package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeChangeRecords;
import com.gusi.boms.model.Parameter;
import com.gusi.boms.service.*;
import com.gusi.empTransfer.service.EmployeeTransferService;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *	author:liuhui
 *	createTime: liuhui (2013-5-30 上午09:34:36 )
 * 异动记录
 */
@Controller
@RequestMapping(value="/changes")
public class ChangeController  extends BomsBaseController {
	private static final Logger logger=Logger.getLogger(ChangeController.class);
	@Autowired
    private EmployeeService employeeService;
	@Autowired
    private EmployeeChangeRecordsService employeeChangeRecordsService;
	@Autowired
    private ParameterService parameterService;
	@Autowired
    private TitleSerialService titleSerialService;
	@Autowired
    private SelectService selectService;
	@Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private EmployeeTransferService employeeTransferService;
	/**
	 * @param userCode
	 * @param model
	 * @param request
	 * @return 异动详情
	 */
	@RequestMapping(value="/{userCode}")
	public String details(@PathVariable(value="userCode")int userCode,Model model ,
			HttpServletRequest request)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        Employee emp = employeeService.getEmployee(userCode, 0, 0, EmployeeStatus.All);
		model.addAttribute("emp", emp);
        model.addAttribute("orgLongName", employeeBaseInforService.findorgLongNameByUserCode(userCode));
		model.addAttribute("userCode", userCode);
		if (getSesionUser(request).checkPrivilege(Constants.CHANGES_DETAIL)) {
			List<EmployeeChangeRecords> changes= employeeChangeRecordsService.findByUserCode(userCode);
			if (changes !=null && !changes.isEmpty()) {
				if (employeeChangeRecordsService.canAddChanges(userCode)) {
					for (EmployeeChangeRecords cs : changes) {
						//第一个生效的。
						if (cs.getStatus()==1) {
							cs.setCanRollback(true);
							break;
						}
					}
				}
			}
			model.addAttribute("changes",changes);
		}
		return "/changes/changes";
	}
	/**
	 * @param changeId
	 * @param userCode
	 * @param request
	 * @param model
	 * @return 删除未生效的异动记录
	 */
	@RequestMapping(value="/{userCode}/{changeId}/doDelete")
	public String doDelete(@PathVariable(value="changeId")int changeId,@PathVariable(value="userCode")int userCode,
			HttpServletRequest request,Model model)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
		//检查权限
	    if (ep.checkPrivilege(Constants.CHANGES_DELETE)) {
	    	if (employeeChangeRecordsService.doDelete(changeId,getSesionUser(request).getUserCode())) {
	    		return "redirect:/changes/"+userCode;
	    	}else
	    	{
	    		return error(model, "操作失败，异动记录无法删除。");
	    	}
		}else
		{
			return errorNoPrivilege(model);
		}

	}

	/**
	 * @param changeId
	 * @param userCode
	 * @param request
	 * @param model
	 * @return 回滚生效的异动记录
	 */
	@RequestMapping(value="/{userCode}/{changeId}/rollback")
	public String doRollback(@PathVariable(value="changeId")int changeId,@PathVariable(value="userCode") int userCode,
			HttpServletRequest request,Model model)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
		//检查权限
		 if (ep.checkPrivilege(Constants.CHANGES_ROLLBACK)) {
			//首先判断是否有未生效的异动
			try {
				if (employeeBaseInforService.isBlack(userCode)) {
					return error(model, "操作失败，工号为："+userCode+"的员工已被加入黑名单，请先撤销黑名单。");
				}
				if (employeeChangeRecordsService.canAddChanges(userCode) &&
						employeeChangeRecordsService.doRollback(changeId,userCode,
								getSesionUser(request).getUserCode())) {
					return "redirect:/changes/"+userCode;
				}
				return error(model, "操作失败，异动记录无法回滚，可能是本条记录不完整或回滚的前一条记录不完整，请及时联络相关负责人。");
			} catch (Throwable e) {
				logger.error("回滚失败。",e);
				return error(model, "操作失败，系统反馈："+e.getMessage());
			}
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取离职页面
	 */
	@RequestMapping(value="/{userCode}/newLeave")
	public String newLeave(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model, "对不起，无此员工信息，请联系管理员");
        }
		//离职类型
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
				 model.addAttribute("userCode",userCode);
				 model.addAttribute("changeType", EmployeeChangeRecords.CHANGE_TYPE_LEAVE);
				 model.addAttribute("leaveTypes", parameterService.findByTypeKey(Parameter.LEAVE_TYPE_KEY));
				 return "/changes/newLeave";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取转正页面
	 */
	@RequestMapping(value="/{userCode}/newRegular")
	public String newRegular(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			 processRequest(userCode, model, EmployeeChangeRecords.CHANGE_TYPE_REGULAR);
			 return "/changes/newRegular";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取转调页面
	 */
	@RequestMapping(value="/{userCode}/newRelevel")
	public String newRelevel(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			 processRequest(userCode,model,EmployeeChangeRecords.CHANGE_TYPE_RELEVEL);
			 return "/changes/newRelevel";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取晋升页面
	 */
	@RequestMapping(value="/{userCode}/newPromotion")
	public String newPromotion(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			 processRequest(userCode,model,EmployeeChangeRecords.CHANGE_TYPE_PROMOTION);
			 return "/changes/newPromotion";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取降职页面
	 */
	@RequestMapping(value="/{userCode}/newDemotion")
	public String newDemotion(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			 processRequest(userCode,model,EmployeeChangeRecords.CHANGE_TYPE_DEMOTION);
			 return "/changes/newCommon";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取任命页面
	 */
	@RequestMapping(value="/{userCode}/newAppoint")
	public String newAppiont(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			 processRequest(userCode,model,EmployeeChangeRecords.CHANGE_TYPE_APPOINT);
			 return "/changes/newAppoint";
		 }
		 return errorNoPrivilege(model);
	}

    /**
     * 数据处理
     * @since: 2014-06-16 13:25:26
     * @param request
     * @param model
     * @param userCode
     * @return
     */
	@RequestMapping(value="/{userCode}/dealData")
	public String dealData(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			 processRequest(userCode,model,EmployeeChangeRecords.CHANGE_TYPE_DEAL_DATA);
			 return "/changes/dealData";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 获取回聘页面
	 */
	@RequestMapping(value="/{userCode}/newRehire")
	public String newRehire(HttpServletRequest request,Model model,
			@PathVariable(value="userCode")int userCode)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error_new(model,"对不起，无此员工信息，请联系管理员");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
		 if (employeeBaseInforService.isBlack(userCode)) {
				return error_new(model, "操作失败，工号为："+userCode+"的员工已被加入黑名单，请先撤销黑名单。");
			}
			 processRequest(userCode,model,EmployeeChangeRecords.CHANGE_TYPE_REHIRE);
			 return "/changes/newRehire";
		 }
		 return errorNoPrivilege(model);
	}
	/**
	 * @return 处理异动
	 */
	@RequestMapping(value="/processChanges",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult processChanges(EmployeeChangeRecords changes,
			HttpServletRequest request)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(changes.getUserCode(),ep.getCompanyObject())){
            return fail("对不起，不允许跨部门操作");
        }
		 if (ep.checkPrivilege(Constants.CHANGES_ADD)) {
			try {
				if (!employeeChangeRecordsService.canAddChanges(changes.getUserCode())) {
					return  fail("操作失败，此人有异动未生效。");
				}
                if(employeeTransferService.isTransfering(changes.getUserCode())) {
                    return  fail("操作失败,员工处于转调审批中。");
                }
                //如果是回聘，那么判断区域人数
                String changeType = changes.getChangeType();
                if(StringUtils.isNotBlank(changeType) && EmployeeChangeRecords.CHANGE_TYPE_REHIRE.equals(changeType)) {
                    if(employeeBaseInforService.isMoreThanNormal(changes.getNewOrgId())) {
                        return fail("回聘的组织所在区域已经满员，无法回聘员工");
                    }
                }
				changes.setCreator(ep.getUserCode());
				if (employeeChangeRecordsService.processChanges(changes) != null){
					return ok();
				}
				return fail("操作失败，请及时联络相关负责人。");
			} catch (Throwable e) {
				logger.error("异动添加失败。", e);
				return fail("操作失败，系统反馈："+e.getMessage());
			}
		 }else
		 {
			 return fail("您没有操作权限。");
		 }
	}
	/**
	 *  处理异动请求
	 */
	private void processRequest(int userCode,Model model,String changeType)
	{
		EmployeeChangeRecords changes=employeeChangeRecordsService.findForAddChanges(userCode);
		if (changes==null) {
			return;
		}
		model.addAttribute("userCode", userCode);
		model.addAttribute("serials", titleSerialService.queryForList());
		//职系查询职等
		model.addAttribute("titles", selectService.queryTitlesBySerialId(changes.getNewSerialId()));
		//根据职等查询职级
		model.addAttribute("levels", selectService.queryTitleLevelByTitleId(changes.getNewTitleId()));
		//根据职等查询岗位
		model.addAttribute("positions",selectService.queryPositionByTitleId(changes.getNewTitleId()));
		changes.setChangeType(changeType);
		model.addAttribute("changes",changes);
	}
   /**
    * @return
    */
   @RequestMapping(value="/{userCode}/validateCanAddChange")
   @ResponseBody
   public JsonResult validateCanAddChange(@PathVariable(value="userCode")int userCode)
   {
	   return  employeeChangeRecordsService.canAddChanges(userCode)?ok():fail();
   }

  /**
 * @param msg
 * @param model
 * @return
 */
public String error_new(Model model,String msg)
  {
	  model.addAttribute("msg", msg);
	  return "/error/error_new";
  }
}

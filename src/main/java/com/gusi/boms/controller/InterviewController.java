package com.gusi.boms.controller;

import javax.servlet.http.HttpServletRequest;

import com.gusi.boms.model.EmployeeInterviewRecords;
import com.gusi.boms.service.EmployeeInterviewParameterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.gusi.boms.service.EmployeeInterviewRecordsService;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.common.WebUtils;
/**
 * @author "liuhui"
 * 离职面谈
 */
@Controller
@RequestMapping(value="/interview")
public class InterviewController extends BomsBaseController {
  
	@Autowired
	EmployeeBaseInforService employeeBaseInforService;
	@Autowired
	EmployeeInterviewParameterService employeeInterviewParameterService;
	@Autowired
	EmployeeInterviewRecordsService employeeInterviewRecordsService;
	private static final Logger log=Logger.getLogger(InterviewController.class);
	
	/**
	 * @return 查看离职面谈
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String list(HttpServletRequest request,Model model)
	{
		//判断有无权限
		if (!getSesionUser(request).checkPrivilege(Constants.INTERVIEW_LEAVE_EMP)) {
			return errorNoPrivilege(model);
		}
		int pageNo=WebUtils.findParamInt(request, "pageNo", 1);
		String keywords=WebUtils.findParamStr(request, "keywords");
		Paginate paginate=employeeBaseInforService.findInterviewEmployees(pageNo,keywords,getSesionUser(request).getCompany());
		model.addAttribute("paginate", paginate);
		return "/interview/list";
	}
	/**
	 * @param request
	 * @param model
	 * @return 完成面谈
	 */
	@RequestMapping(value="/complete",method=RequestMethod.POST)
	public String completeInterview(HttpServletRequest request,Model model,
			EmployeeInterviewRecords records)
	{
		if(!getSesionUser(request).checkPrivilege(Constants.INTERVIEW_LEAVE_EMP))
		{
			return errorNoPrivilege(model);
		}
		try {
			if (employeeInterviewRecordsService.interviewerHandle(getSesionUser(request),records)) {
				return "redirect:/interview";
			}
			return error(model, "离职面谈失败。");
		} catch (Exception e) {
			log.error("离职面谈出错。", e);
			return error(model, "离职面谈失败，系统反馈："+e.getMessage());
		}
	}
	/**
	 * @param request
	 * @param model
	 * @param id
	 * @return 离职面谈页
	 */
	@RequestMapping(value="/{userCode}/{id}",method=RequestMethod.GET)
	public String forwardInterview(HttpServletRequest request,Model model,
			@PathVariable(value="id")Integer id,@PathVariable(value="userCode")int userCode)
	{
		if(!getSesionUser(request).checkPrivilege(Constants.INTERVIEW_LEAVE_EMP))
		{
			return errorNoPrivilege(model);
		}		
		//离职记录
		 EmployeeInterviewRecords records=employeeInterviewRecordsService.findForInterview(id,getSesionUser(request).getCompany());
		 if (records==null) {
			return error(model, "记录无效，无法进行离职面谈,可能原因是关联异动记录已回滚或者公司不对或者面谈已被处理。");
		}
		model.addAttribute("records", records);
		//离职面谈的参数集合
		model.addAttribute("leaveList", employeeInterviewParameterService.findForInterviewParameter());
		model.addAttribute("leaveEmp", employeeBaseInforService.findEmpForInterview(userCode));
		return "interview/interview";
	}
	
}

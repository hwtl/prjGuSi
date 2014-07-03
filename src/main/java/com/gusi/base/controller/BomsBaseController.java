package com.gusi.base.controller;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Title: BomsBaseController.java
 * @Package com.dooioo.boms.controller
 * @Description: Controller基类,继承com.dooioo.web.controller.BaseController
 * @author Jail    E -Mail:86455@ dooioo.com
 * @date 2012-12-5 下午1:25:52
 * @version V1.0
 */
public abstract class BomsBaseController extends BaseController {

    @Autowired
    EmployeeService employeeService;
	/**
	 * 获得SESSION_USER
	 * @param request
	 * @return
	 */
	protected Employee getSesionUser(HttpServletRequest request) {
		return getSesionUser(request.getSession());
	}

	private Employee getSesionUser(HttpSession session) {
		if(session==null)
			return null;
		else
			return (Employee)session.getAttribute(Constants.SESSION_USER);
	}

	/**
	 * 设置当前用户到MODEL
	 * @param request
	 * @return
	 */
	@ModelAttribute(value="user")
	public Employee initUser(HttpServletRequest request){
		return getSesionUser(request);
	}

	/**
	 * 设置当前系统环境到MODEL
	 * @return
	 */
	@ModelAttribute(value="config")
	public Configuration instance(HttpServletRequest request){
        Configuration.getInstance().setLogoutUrl(employeeService.getLogoutUrl(this.getSesionUser(request).getCompanyObject()));
        Configuration.getInstance().initsuperAdminUserCode(this.getSesionUser(request).getCompanyObject());
        return Configuration.getInstance();
	}

	/**
	 * 设置页面错误 - 没有权限
	 * @param model 页面model
	 * @return 错误页面url
	 */
	public String errorNoPrivilege(Model model){
        model.addAttribute("title","违规操作");
		return error(model,"您没有权限执行相应的操作，请联系管理员。");
	}

	/**
	 * 设置页面错误提示信息
	 * @param model 页面model
	 * @param message 错误信息内容
	 * @return 错误页面url
	 */
	public String error(Model model, String message){
		model.addAttribute("msg", message);
		model.addAttribute("title", "错误提醒");
		return "/error/error";
	}
	
	/**
	 * 设置页面错误提示信息
	 * @param model 页面model
	 * @param message 错误信息内容
	 * @return 错误页面url
	 */
	public String errorNew(Model model, String message){
		model.addAttribute("msg", message);
		model.addAttribute("title", "错误提醒");
		return "/error/error_new";
	}

	/**
	 * 判断是否超级管理员
	 * @param employee
	 * @return
	 */
	public boolean isSuperAdmin(Employee employee){
		return employee.getUserCode() == Configuration.getInstance().getSuperAdminUserCode();
	}
}


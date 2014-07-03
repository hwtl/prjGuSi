package com.gusi.boms.inteceptor;

import com.dooioo.plus.oms.dyEnums.EmployeeInfoType;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInteceptor implements HandlerInterceptor {

	@Autowired
	private EmployeeService employeeService;
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o) throws Exception {
        return employeeService.getLoginEmployee(httpServletRequest, httpServletResponse,
               EmployeeInfoType.Privilege, EmployeeInfoType.PartitionOrgs) != null;
	}

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}
}

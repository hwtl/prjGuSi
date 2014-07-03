package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.VEmployeeDetails;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.gusi.boms.service.EmployeeOperateHistoryService;
import com.gusi.boms.service.EmployeePositionService;
import com.gusi.boms.service.TitleSerialService;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-10 14:01)
 * Description: 员工职位信息控制器
 */
@Controller
@RequestMapping(value = "/employeePosition")
public class EmployeePositionController extends BomsBaseController {

    private static final Logger logger=Logger.getLogger(EmployeePositionController.class);

    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private EmployeeOperateHistoryService employeeOperateHistoryService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private TitleSerialService titleSerialService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 编辑员工岗位信息
     * @update: 2014-05-27 14:08:56
     * @param vEmployeeDetails
     * @param userCode
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "{userCode}/update",method = RequestMethod.POST)
    public String update(VEmployeeDetails vEmployeeDetails,@PathVariable int userCode,HttpServletRequest request,Model model){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode, emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        try {
            employeePositionService.updateEmployeePositions(vEmployeeDetails, userCode, emp);
        } catch (Throwable e) {
            logger.error("更新兼职出错。", e);
        }
        return "redirect:/employeePosition/{userCode}/details";
    }

    @RequestMapping(value = "{userCode}/details",method = RequestMethod.GET)
    public String view(@PathVariable int userCode,HttpServletRequest request,Model model){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        model.addAttribute("userCode",userCode);
        model.addAttribute("employee",employeeBaseInforService.findById(userCode));
        model.addAttribute("partTimePositions",employeePositionService.queryParttimePositions(userCode));
        return "partTimePosition/positionDetails";
    }

    /**
     * 员工兼职信息的编辑
     * @param userCode
     * @param model
     * @return
     */
    @RequestMapping(value = "{userCode}/edit",method = RequestMethod.GET)
    public String edit(@PathVariable int userCode,Model model,HttpServletRequest request){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        if(!PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(), Constants.HRM_EMPLOYEE_PARTTIME_POSITION_EDIT)){
            return error(model,"对不起没有此权限操作");
        }
        model.addAttribute("userCode", userCode);
        model.addAttribute("employee",employeeBaseInforService.findById(userCode));
        model.addAttribute("titleSerialList",titleSerialService.queryTitleSerials());
        model.addAttribute("partTimePositions",employeePositionService.queryParttimePositions(userCode));
        return "partTimePosition/positionEdit";
    }

}

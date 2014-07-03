package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.VEmployeeDetails;
import com.gusi.boms.service.EmployeeContractRecordsService;
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
 * Create: Jerry.hu (2013-06-03 10:15)
 * 合同控制器
 */
@Controller
@RequestMapping(value = "/contract")
public class ContractsController extends BomsBaseController {

    private static final Logger logger=Logger.getLogger(ContractsController.class);

    @Autowired
    private EmployeeContractRecordsService employeeContractRecordsService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 更新员工合同
     * @udpate: 2014-05-28 09:33:19
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
            employeeContractRecordsService.udpateEmployeeContracts(vEmployeeDetails, userCode, emp);
        } catch (Throwable e) {
            logger.error("更新工作经验出错。", e);
        }
        return "redirect:/contract/{userCode}/details";
    }

    @RequestMapping(value = "{userCode}/details",method = RequestMethod.GET)
    public String view(@PathVariable int userCode,HttpServletRequest request,Model model){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode, emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        model.addAttribute("userCode",userCode);
        model.addAttribute("contracts",employeeContractRecordsService.queryForList(userCode));
        return "/contract/contractDetails";
    }

    /**
     * 员工合同详情
     * @param userCode
     * @param model
     * @return
     */
    @RequestMapping(value = "{userCode}/edit",method = RequestMethod.GET)
    public String edit(@PathVariable int userCode,Model model,HttpServletRequest request){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode, emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        if(!PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(), Constants.HRM_EMPLOYEE_CONTRACT_EDIT)){
            return error(model,"对不起没有此权限操作");
        }
        model.addAttribute("userCode", userCode);
        model.addAttribute("contracts",employeeContractRecordsService.queryForList(userCode));
        return "/contract/contractEdit";
    }
}

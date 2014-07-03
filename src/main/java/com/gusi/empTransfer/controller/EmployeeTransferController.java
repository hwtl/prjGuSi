package com.gusi.empTransfer.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.Select;
import com.gusi.boms.service.ActivitiService;
import com.gusi.boms.service.SelectService;
import com.gusi.empTransfer.dto.EmpTransferOrg;
import com.gusi.empTransfer.dto.EmpTransferSearch;
import com.gusi.empTransfer.dto.VEmployeeTransfer;
import com.gusi.empTransfer.helper.EmpTransferApiHelper;
import com.gusi.empTransfer.helper.STLHelper;
import com.gusi.empTransfer.model.EmployeeTransfer;
import com.gusi.empTransfer.service.EmpTransferEmpService;
import com.gusi.empTransfer.service.EmpTransferOrgService;
import com.gusi.empTransfer.service.EmployeeTransferService;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 员工转调控制器
 * @Author: fdj
 * @Since: 2014-02-19 15:50
 */
@Controller
@RequestMapping("/transfer/emp/**")
public class EmployeeTransferController extends BomsBaseController {

    private static final Log LOG = LogFactory.getLog(EmployeeTransferController.class);

    @Autowired
    private EmpTransferOrgService empTransferOrgService;
    @Autowired
    private EmpTransferEmpService empTransferEmpService;
    @Autowired
    private EmployeeTransferService employeeTransferService;
    @Autowired
    private STLHelper stlHelper;
    @Autowired
    private SelectService selectService;
    @Autowired
    private ActivitiService activitiService;

    /**
     * 列表页面跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(EmpTransferSearch empTransferSearch, @RequestParam(defaultValue = "1") int pageNo, HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        int scope = PrivilegeHelper.getDataScope(employee.getPrivilegeMap(), Constants.EMPLOYEE_TRANSFER_LIST);
        //没有权限
        if(scope == -1) {
            return errorNoPrivilege(model);
        }
        model.addAttribute("paginate", employeeTransferService.queryForPaginate(pageNo, employee, empTransferSearch, scope));
        model.addAttribute("empTransferSearch", empTransferSearch);
        return "/empTransfer/list";
    }

    /**
     * 新增页面跳转
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        if(!employee.checkPrivilege(Constants.PRIVILEGE_URL_TRANSFER_APPLY)) {
            return errorNoPrivilege(model);
        }
        List<EmpTransferOrg> acceptOrgs = empTransferOrgService.queryWithPartionOrgs(employee.getUserCode());
        model.addAttribute("acceptOrgs", acceptOrgs);
        model.addAttribute("optionType", "add");
        return "/empTransfer/form";
    }

    /**
     * 保存转调记录
     * @param employeeTransfer
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String added(EmployeeTransfer employeeTransfer, HttpServletRequest request, Model model) {
        if(employeeTransfer.getOldOrgId() == employeeTransfer.getNewOrgId()) {
            return error(model, "对不起，该员工已经在您需要转调到的组织中。");
        }
        Employee employee = this.getSesionUser(request);
        if(!employee.checkPrivilege(Constants.PRIVILEGE_URL_TRANSFER_APPLY)) {
            return errorNoPrivilege(model);
        }
        try {
            employeeTransferService.add(employeeTransfer, employee);
        } catch (Exception e) {
            LOG.error("插入记录失败,EmployeeTransfer:" + employeeTransfer, e);
            return error(model, "对不起，插入记录失败！");
        }
        return "redirect:/transfer/emp/list";
    }

    /**
     * 编辑保存
     * @param employeeTransfer
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}/{processInsId}/{taskId}", method = RequestMethod.POST)
    public String edited(@PathVariable int id, @PathVariable String processInsId, @PathVariable String taskId, EmployeeTransfer employeeTransfer, HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        try {
            employeeTransferService.updateTransfer(employeeTransfer, employee, processInsId, taskId);
        } catch (Exception e) {
            LOG.error("更新失败,EmployeeTransfer:" + employeeTransfer, e);
            return error(model, "对不起，更新失败！");
        }
        return "redirect:/transfer/emp/list";
    }

    /**
     * 详情
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail/{id}/{processInsId}", method = RequestMethod.GET)
    public String detail(@PathVariable int id, @PathVariable String processInsId, HttpServletRequest request, Model model) {
        try {
            Employee employee = this.getSesionUser(request);
            model.addAttribute("transfer", employeeTransferService.findViewById(id));
            model.addAttribute("processInsId", processInsId);
            model.addAttribute("taskList", activitiService.getTaskHisList(processInsId));
        } catch (Exception e) {
            LOG.error("获取详情页面失败。id=" + id + "。processId=" + processInsId, e);
            return error(model, "获取详情页面失败。");
        }
        return "/empTransfer/detail";
    }

    /**
     * 审批页面跳转
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/approval/{id}/{processInsId}/{taskId}", method = RequestMethod.GET)
    public String approval(@PathVariable int id, @PathVariable String processInsId, @PathVariable String taskId, HttpServletRequest request, Model model) {

        try {
            Employee employee = this.getSesionUser(request);
            //获取当前审批环节
            String approvalStep = activitiService.getProcessStatus(taskId);
            //如果没有记录，或者环节处理人不是当前登录人
            if(approvalStep == null || !activitiService.checkTaskOwner(taskId, String.valueOf(employee.getUserCode()))) {
                return error(model, "对不起，没有处理该审批请求的权限，或该审批请求已经处理。");
            }
            //查询转调记录
            VEmployeeTransfer vEmployeeTransfer = (VEmployeeTransfer)employeeTransferService.findViewById(id);
            if(vEmployeeTransfer == null) {
                return error(model, "没有该条记录。");
            }

            //如果当前环节是人事审批或者是打回修改，则需要职系职等职级
            if(approvalStep.equals(Constants.TASK_DEF_KEY_HR_APPROVE) || approvalStep.equals(Constants.TASK_DEF_KEY_MODIFY_APPLY)) {
                //获取转调记录对应的职系
                List<Select> serialList = stlHelper.initSerial(vEmployeeTransfer.getTransferType());
                //如果转调记录对应的职系不为空，需要出职系职等职级
                if(serialList != null) {
                    model.addAttribute("serialList", serialList);
                    model.addAttribute("titleList", selectService.queryTitlesBySerialId(vEmployeeTransfer.getNewSerialId()));
                    model.addAttribute("levelList", selectService.queryTitleLevelByTitleId(vEmployeeTransfer.getNewTitleId()));
                    model.addAttribute("positionList", selectService.queryPositionByTitleId(vEmployeeTransfer.getNewTitleId()));
                }
            }

            //如果当前环节是打回修改还需要登录人相关信息，以及打回原因
            if (approvalStep.equals(Constants.TASK_DEF_KEY_MODIFY_APPLY)) {
                //获取转调部门
                model.addAttribute("acceptOrgs", empTransferOrgService.queryWithPartionOrgs(employee.getUserCode()));
                model.addAttribute("optionType", "edit");
                model.addAttribute("remark", activitiService.getTaskComment(processInsId));
                model.addAttribute("transfer", vEmployeeTransfer);
                return "/empTransfer/form";
            }

            //如果当前环节是房源交接
            else if(approvalStep.equals(Constants.TASK_DEF_KEY_HOUSE_HANDOVER)) {
                //判断房源是否已经交接完成
                if(EmpTransferApiHelper.isFyTransfered(vEmployeeTransfer.getUserCode(), employee.getCompanyObject()) && employeeTransferService.updateFangyuanStatus(vEmployeeTransfer, employee.getUserCode())) {
                    if(activitiService.checkProcessFinished(processInsId)) {
                        return "redirect:/transfer/emp/detail/" + id + "/" + vEmployeeTransfer.getProcessInsId();
                    }
                    vEmployeeTransfer = (VEmployeeTransfer)employeeTransferService.findViewById(id);
                    approvalStep = activitiService.getProcessStatus(vEmployeeTransfer.getTaskId());
                }
            }

            //如果当前环节是客源交接
            if(approvalStep.equals(Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER) && EmpTransferApiHelper.isKyTransfered(vEmployeeTransfer.getUserCode(), employee.getCompanyObject()) && employeeTransferService.updateKeyuanStatus(vEmployeeTransfer, employee.getUserCode())) {
                if(activitiService.checkProcessFinished(processInsId)) {
                    return "redirect:/transfer/emp/detail/" + id + "/" + vEmployeeTransfer.getProcessInsId();
                }
            }

            model.addAttribute("processInsId", processInsId);
            model.addAttribute("taskId", taskId);
            model.addAttribute("taskList", activitiService.getTaskHisList(processInsId));

            model.addAttribute("transfer", vEmployeeTransfer);
            model.addAttribute("approvalStep", approvalStep);
            return "/empTransfer/approval";
        } catch (Exception e) {
            LOG.error("获取审批页面失败！id:"+id, e);
        }
        return error(model, "获取审批页面失败！");
    }

    /**
     * 审批通过
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/pass/{id}/{processInsId}/{taskId}", method = RequestMethod.POST)
    public String pass(@PathVariable int id, @PathVariable String processInsId, @PathVariable String taskId, @RequestParam String remark,@RequestParam String approvalStep, EmployeeTransfer employeeTransfer, HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        //获取当前审批环节
        String currentSetp = activitiService.getProcessStatus(taskId);
        if(approvalStep == null || !approvalStep.equals(currentSetp) || !activitiService.checkTaskOwner(taskId, String.valueOf(employee.getUserCode()))) {
            return error(model, "对不起，没有处理该审批请求的权限，或该审批请求已经处理。");
        }
        VEmployeeTransfer vemployeeTransfer = (VEmployeeTransfer)employeeTransferService.findViewById(id);
        if(vemployeeTransfer == null) {
            return error(model, "没有该条记录。");
        }
        //判断当前环节是否人事审批
        if(currentSetp.equals(Constants.TASK_DEF_KEY_HR_APPROVE)) {
            employeeTransferService.passByHr(employeeTransfer, remark, processInsId, taskId, employee.getUserCode());
        } else {
            employeeTransferService.pass(vemployeeTransfer, remark, processInsId, taskId, employee.getUserCode());
        }
        return "redirect:/transfer/emp/list";
    }

    /**
     * 审批不通过打回
     * @param id
     * @param remark
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/unPass/{id}/{processInsId}/{taskId}", method = RequestMethod.POST)
    public String unPass(@PathVariable int id, @PathVariable String processInsId, @PathVariable String taskId, @RequestParam String unpassRemark,@RequestParam String approvalStep, HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        //获取当前审批环节
        String currentSetp = activitiService.getProcessStatus(taskId);
        if(approvalStep == null || !approvalStep.equals(currentSetp) || !activitiService.checkTaskOwner(taskId, String.valueOf(employee.getUserCode()))) {
            return error(model, "对不起，没有处理该审批请求的权限，或该审批请求已经处理。");
        }
        EmployeeTransfer employeeTransfer = employeeTransferService.findViewById(id);
        if(employeeTransfer == null) {
            return error(model, "没有该条记录。");
        }
        employeeTransferService.unPass(employeeTransfer, unpassRemark, processInsId, taskId, employee.getUserCode());
        return "redirect:/transfer/emp/list";
    }

    /**
     * 根据关键字搜索员工
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult empSearch(@RequestParam String keyword, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(StringUtils.isBlank(keyword)) {
            return ok();
        }
        return ok().put("employeeList", empTransferEmpService.search(keyword, employee.getCompany()));
    }

    /**
     * 检查工号是否可以转调
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/check/{userCode}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult empCheck(@PathVariable int userCode) {
        if(employeeTransferService.isTransfering(userCode)) {
            return fail("该员工正在转调流程中，不允许申请转调。");
        }
        if(empTransferEmpService.isChanging(userCode)) {
            return fail("该员工有异动未生效，不允许申请转调。");
        }
        if(empTransferEmpService.isLeavingIn3month(userCode)) {
            return fail("离职人员回聘，3个月内不得转调其他分行。");
        }
        return ok();
    }

    /**
     * 作废
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/destroy/{id}/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult destroy(@PathVariable int id,@PathVariable String taskId, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        EmployeeTransfer employeeTransfer = employeeTransferService.findById(id);
        if(employeeTransfer == null || employeeTransfer.getCreator() != employee.getUserCode()) {
            return fail("您没有该条记录。");
        }
        if(employeeTransferService.destroy(id, taskId)) {
            return ok();
        }
        return fail("作废失败。");
    }

    /**
     * 删除转调记录
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/delete/{id}" )
    public JsonResult delete(@PathVariable int id, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if("90378,89695".contains(String.valueOf(employee.getUserCode()))) {
            return ok().put("rs", employeeTransferService.delete(id));
        }
        return fail("别走后门！");
    }

}

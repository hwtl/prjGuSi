package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.common.Constants;
import com.gusi.boms.dto.EmpToSendMsg;
import com.gusi.boms.helper.OrganizationHelper;
import com.gusi.empTransfer.service.EmployeeTransferService;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.plus.oms.dyUser.service.SerialService;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.helper.JsonResult;
import com.gusi.boms.model.*;
import com.gusi.boms.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-09 15:02)
 * 员工控制器
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController extends BomsBaseController {

    private static final Log LOG = LogFactory.getLog(EmployeeController.class);

    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private EmployeeInfoExcelService employeeInfoExcelService;
    @Autowired
    private TitleSerialService titleSerialService;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private EmployeeContractRecordsService employeeContractRecordsService;
    @Autowired
    private SerialService serialService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeInterviewParameterService employeeInterviewParameterService;
    @Autowired
    private EmployeeBlackRecordsService employeeBlackRecordsService;
    @Autowired
    private EmployeeStatusRecordService employeeStatusRecordService;
    @Autowired
    private EmployeeTransferService employeeTransferService;
    @Autowired
    private Organization2Service organization2Service;
    @Autowired
    private EmpToSendMsgService empToSendMsgService;
    @Autowired
    private EmployeeTagService employeeTagService;
    
    @RequestMapping(value = "/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                       EmployeeSearch employeeSearch,HttpServletRequest request,  Model model){
        employeeSearch.setCompany(this.getSesionUser(request).getCompany());
        Paginate paginate = employeeBaseInforService.queryForPaginate(employeeSearch,pageNo);
        if(PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(),Constants.EMPLOYEE_ADVANCED_SEARCH)){
            model.addAttribute("positions",serialService.getPositionList());
            model.addAttribute("constellations",parameterService.findByTypeKey(Parameter.CONSTELLATION_KEY));
            model.addAttribute("degrees",parameterService.findByTypeKey(Parameter.DEGREE_KEY));
            model.addAttribute("tagList",parameterService.findByTypeKey(Parameter.TAG_KEY));
            model.addAttribute("channels",parameterService.findByTypeKey(Constants.CHANNEL_KEY));
        }
        model.addAttribute("nowYear", Calendar.getInstance().get(Calendar.YEAR));
        model.addAttribute("paginate",paginate);
        model.addAttribute("employeeSearch",employeeSearch);
        return "/employee/list";
    }
    /**
     * @param request
     * @param userCode
     * @param model
     * @return
     *  加入黑名单
     *   不在黑名单里，并且是离职
     */
    @RequestMapping(value="/{userCode}/addBlack")
    public String addBlack(HttpServletRequest request,
    		@PathVariable(value="userCode")Integer userCode,Model model)
   {
	    Employee curEmp=getSesionUser(request);
	    if (!curEmp.checkPrivilege(Constants.ADD_EMPLOYEE_TO_BLACK)) {
			return errorNoPrivilege(model);
		}
	   EmployeeBaseInfor eb=  employeeBaseInforService.findEmpForAddBlack(userCode,curEmp.getCompany());
	   if (eb==null) {
		  return error(model, "员工不存在，只有离职并且不在黑名单里的人员才可以加入黑名单。");
	   }
	   model.addAttribute("blackEmployee", eb);
	   model.addAttribute("blackReasons",employeeInterviewParameterService.findForBlackParameter());
	  return "/employee/add_black";
   }
    /**
     * @param request
     * @param model
     * @return 保存黑名单
     */
    @RequestMapping(value="/addBlack",method=RequestMethod.POST)
    public String saveBlack(HttpServletRequest request,Model model,
    			EmployeeBlackRecords blackRecords)
   {
	    Employee curEmp=getSesionUser(request);
	    if (!curEmp.checkPrivilege(Constants.ADD_EMPLOYEE_TO_BLACK)) {
			return errorNoPrivilege(model);
		}
	   try {
		   if (employeeBlackRecordsService.addBlackRecords(blackRecords,curEmp)) {
			   return "redirect:/employee/list";
		   }
		  return error(model, "添加黑名单失败。");
		} catch (Throwable e) {
		  return error(model, "添加黑名单失败，系统反馈："+e.getMessage());
		}
   } 
    /**
     * @param request
     * @param model
     * @return 保存黑名单
     */
    @RequestMapping(value="/{blackId}/rollback")
    public String forwardRemoveBlack(HttpServletRequest request,Model model,
    			@PathVariable(value="blackId")Integer blackId)
   {
	    Employee curEmp=getSesionUser(request);
	    if (!curEmp.checkPrivilege(Constants.REMOVE_EMPLOYEE_FROM_BLACK)) {
			return errorNoPrivilege(model);
		}
	    EmployeeBaseInfor eb=  employeeBaseInforService.findEmpForRemoveBlack(blackId,curEmp.getCompany());
	   if (eb==null) {
		  return error(model, "员工不存在，只有离职并且在黑名单里的人员才可以取消黑名单。");
	   }
	   model.addAttribute("blackEmployee", eb);
	   model.addAttribute("blackId", blackId);
	   return "/employee/remove_black";
	    
   } 
    
    /**
     * @param request
     * @param model
     * @return 撤销黑名单
     */
    @RequestMapping(value="/removeBlack",method=RequestMethod.POST)
    public String removeBlack(HttpServletRequest request,Model model,
    			EmployeeBlackRecords blackRecords)
   {
	    Employee curEmp=getSesionUser(request);
	    if (!curEmp.checkPrivilege(Constants.REMOVE_EMPLOYEE_FROM_BLACK)) {
			return errorNoPrivilege(model);
		}
	    try {
			   if (employeeBlackRecordsService.revocationBlackRecords(blackRecords,curEmp)) {
				   return "redirect:/employee/list";
			   }
		  return error(model, "撤销黑名单失败。");
		} catch (Throwable e) {
		  return error(model, "撤销黑名单失败，系统反馈："+e.getMessage());
		}
	    
   } 
    /**
     * 新增员工跳转拦截
     * @param model 实体
     * @return url
     */
   @RequestMapping(value = "/add" , method = RequestMethod.GET)
   public String add(Model model,HttpServletRequest request){
       if(!PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(), Constants.HRM_EMPLOYEE_ADD)){
           model.addAttribute("title","无权限操作");
          return errorNoPrivilege(model);
       }
       model.addAttribute("titleSerialList",titleSerialService.queryTitleSerials());
       model.addAttribute("channels",parameterService.findByTypeKey(Constants.CHANNEL_KEY));
       model.addAttribute("degreeList", parameterService.findByTypeKey(Parameter.DEGREE_KEY));
       model.addAttribute("employee",EmployeeBaseInfor.init());
       return "/employee/form";
   }

    /**
     * 新增员工跳转拦截
     * @param orgId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/{orgId}/add",method = RequestMethod.GET)
    public String addByOrg(@PathVariable int orgId,Model model,HttpServletRequest request){
        if(!PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(),Constants.HRM_EMPLOYEE_ADD)){
            model.addAttribute("title","无权限操作");
            return errorNoPrivilege(model);
        }
        if(organizationService.checkOrg(this.getSesionUser(request),orgId, OrganizationHelper.getRange(this.getSesionUser(request),Constants.HRM_EMPLOYEE_ADD))){
          model.addAttribute("org",organizationService.getOrgByOrgId(orgId));
        }
        model.addAttribute("titleSerialList",titleSerialService.queryTitleSerials());
        model.addAttribute("channels",parameterService.findByTypeKey(Constants.CHANNEL_KEY));
        model.addAttribute("degreeList", parameterService.findByTypeKey(Parameter.DEGREE_KEY));
        model.addAttribute("employee",EmployeeBaseInfor.init());
        return "/employee/form";
    }

    /**
     * 保存员工拦截
     * @param employeeBaseInfor 员工对象子类
     * @param request 客户端请求
     * @param model 实体
     * @return 重定向
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public String save(@ModelAttribute VEmployeeBaseInfor employeeBaseInfor,
                       @ModelAttribute Recruitment recruitment, HttpServletRequest request,Model model){
        try {
            Employee emp  = this.getSesionUser(request);
            //判断是否拥有新增员工权限
            if(!emp.checkPrivilege(Constants.HRM_EMPLOYEE_ADD)) {
                return errorNoPrivilege(model);
            }
            //判断身份证是否有效
            if(employeeBaseInforService.queryEmployeeByIdc(employeeBaseInfor.getCredentialsNo(),emp.getCompany()) != null){
                return error(model,"员工身份证号重复，请确认后操作");
            }
            //判断是否达到区域人数上限
            if(employeeBaseInforService.isMoreThanNormal(employeeBaseInfor.getOrgId())) {
                return error(model, "本组织所在区域已经满员，无法新增员工");
            }
            //设置相关属性
            employeeBaseInfor.setCreator(emp.getUserCode());
            employeeBaseInfor.setCompany(emp.getCompany());
            //报到时间（发通知短信用）
            String registerDate=WebUtils.findParamStr(request, "registerDate")+" "+
                    WebUtils.findParamStr(request, "registerDateHour")+":"+WebUtils.findParamStr(request, "registerDateMinute");
            employeeBaseInfor = employeeBaseInforService.saveEmployee(employeeBaseInfor, recruitment,
                    getIpAddr(request),registerDate);
            if(employeeBaseInfor == null){
                return error(model, "新增员工失败");
            }
            try {
                //发送短信提醒
                employeeBaseInforService.sendWarnings(employeeBaseInfor.getOrgId(), new Date());
            } catch (Exception e) {
                LOG.error("发送分行员工数量提醒短信失败", e);
                return error(model, "发送分行员工数量提醒短信失败");
            }
            return "redirect:/employee/"+employeeBaseInfor.getUserCode()+"/details";
        } catch (Exception e) {
            LOG.error("新增员工失败", e);
            return error(model, "新增员工失败");
        }
    }

    /**
     * 员工详情页拦截
     * @param userCode 员工工号
     * @param model 实体
     * @param request 客户端请求
     * @return url
     */
    @RequestMapping(value = "/{userCode}/details", method = RequestMethod.GET)
    public String view(@PathVariable int userCode,Model model,HttpServletRequest request){
        if(!PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(), Constants.HRM_EMPLOYEE_VIEW)){
            return errorNoPrivilege(model);
        }
        EmployeeBaseInfor employeeBaseInfor = employeeBaseInforService.queryEmployeeDetails(this.getSesionUser(request).getCompany(),userCode);
        if(employeeBaseInfor == null){
            return error(model,"对不起，无此员工信息,请联系管理员");
        }else{
            model.addAttribute("employee",employeeBaseInfor);
            model.addAttribute("userCode",userCode);
            model.addAttribute("employeeTags",employeeTagService.queryByUserCode(userCode));
            model.addAttribute("partTimePositions",employeePositionService.queryParttimePositions(userCode));
            model.addAttribute("contracts",employeeContractRecordsService.queryForList(userCode));
            model.addAttribute("emp",employeeService.getEmployee(userCode, 0, 0, EmployeeStatus.All));
            model.addAttribute("orgLongName", employeeBaseInforService.findorgLongNameByUserCode(userCode));
            model.addAttribute("titleSerialList",titleSerialService.queryTitleSerials());
            return "/employee/details";
        }
    }

    /**
     * 编辑保存基础信息
     * @param userCode 员工工号
     * @param employeeBaseInfor 员工对象
     * @param request 客户端请求
     * @return 重定向
     */
    @RequestMapping(value = "/edit/{userCode}", method = RequestMethod.POST)
    public String update(@PathVariable int userCode,EmployeeBaseInfor employeeBaseInfor,HttpServletRequest request, Model model){
        Employee employee = this.getSesionUser(request);
        if(employeeTransferService.isTransfering(userCode)) {
            return error(model, "对不起，该员工处于转调审批中，不能编辑基本信息。");
        }
        employeeBaseInfor.setUserCode(userCode);
        employeeBaseInfor.setUpdator(employee.getUserCode());
        EmployeeOperateHistory  employeeOperateHistory = new EmployeeOperateHistory(Constants.BASIC_INFORMATION,employee.getUserName(),employee.getUserCode(),userCode);
        employeeBaseInforService.saveOperateHistory(employeeBaseInfor,employeeOperateHistory);
        employeeBaseInforService.updateInfo(employeeBaseInfor, OperateType.EmployeeBaseUpdate);
        return "redirect:/employee/" + userCode + "/details";
    }

    /**
      * @since: 3.0.1
      * @return
      * <p>
      *  员工照片打印
      * </p>   
     */
    @RequestMapping(value="/photoPrint")
    public String forwardPhotoPrint(){
      return "/employee/photoPrint";
    }
    /**
      * @since: 3.0.1 
      * @return
      * <p>
      * 
      * </p>   
     */
    @RequestMapping(value="/photoPrint/upload")
    @ResponseBody
    public JsonResult uploadExcels(HttpServletRequest request){
    	Employee emp=getSesionUser(request);
    	if (!emp.checkPrivilege(Constants.EMPLOYEE_PHOTO_PRINT)) {
			return fail("您没有权限上传文件。");
		}
    	 MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
         MultipartFile attachFile =  multipartRequest.getFile("fileObject");
         try {
			String userCodes=findUserCodeForPhotoPrint(attachFile.getInputStream(),0);
			return ok().put("list", employeeBaseInforService.findEmployeeByUsercodes(userCodes,emp.getCompany()));
		} catch (IOException e) {
			LOG.error("员工社保图片上传打印出错。操作人="+emp, e);
			return fail(e.getMessage());
		}
    }
    /**
     * @since: 3.0.1 
     * @param in
     * @param sheetIndex
     * @return
     * @throws IOException
     * <p>
     *   从excel文件中解析员工工号，拼成逗号隔开的String
     * </p>   
    */
   private String findUserCodeForPhotoPrint(InputStream in,int sheetIndex) throws IOException
	{
	         HSSFWorkbook wb=new HSSFWorkbook(in);  
	         //读取sheet 
	         HSSFSheet childSheet = wb.getSheetAt(sheetIndex);  
	         //childSheet.getLastRowNum() 从0开始算起
	         int rowNum =childSheet.getLastRowNum()+1; 
	         StringBuilder sb=new StringBuilder(500);
	         HSSFRow row=null;
	         HSSFCell cell=null;
            for(int j=0;j<rowNum;j++)  
            {  
                 row= childSheet.getRow(j); 
                 cell=row.getCell(0);
                 if (cell.getCellType()==HSSFCell.CELL_TYPE_STRING) {
					sb.append(","+cell.getStringCellValue().trim());
				 }else if (cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC) {
					sb.append(","+String.valueOf((int)cell.getNumericCellValue()));
				 }
            }  
	        if (sb.length()<1) {
				throw new IOException("excel中没有数据。");
			}   
            
		return sb.deleteCharAt(0).toString();
	} 
    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/associative",method = RequestMethod.GET)
    public @ResponseBody String associative(HttpServletRequest request){
      String key = WebUtils.findParamStr(request,"key");
      String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
      return jsoncallback + "(" + ok().put("employeeList", employeeBaseInforService.associative(key,this.getSesionUser(request).getCompany())) + ")";
    }
    /**
     * 员工信息导出
     * @param employeeSearch 员工查询条件实体
     * @param response 服务器响应
     * @param request 客户端请求
     */
    @RequestMapping(value = "/export")
    public void export( EmployeeSearch employeeSearch,HttpServletResponse response,HttpServletRequest request) {
        if(PrivilegeHelper.checkPrivilege(this.getSesionUser(request).getPrivileges(),Constants.HRM_EMPLOYEE_INFO_EXPORT)){
            try
            {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLEncoder.encode("员工信息.xls", "UTF-8"));
                employeeSearch.setCompany(this.getSesionUser(request).getCompany());
                employeeInfoExcelService.export(employeeSearch, response.getOutputStream());
            } catch (Exception e) {
                LOG.error("导出员工失败！", e);
            }
        }
    }

    /**
     * 员工相关材料
     * @param userCode
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/material/{userCode}",method = RequestMethod.GET)
    public String material(@PathVariable int userCode, HttpServletRequest request,Model model){
        Employee emp  = employeeService.getEmployee(userCode,EmployeeStatus.All);
        if(emp.getCompany().equals(this.getSesionUser(request).getCompany())){
             model.addAttribute("attachList",attachmentService.getAttachList(userCode));
             model.addAttribute("emp",emp);
            model.addAttribute("orgLongName", employeeBaseInforService.findorgLongNameByUserCode(userCode));
             model.addAttribute("residentType",getResidentType(userCode));
            return "/employee/related_materials";
        }else{
            return error(model,"对不起,无此员工信息，请联系管理员");
        }

    }

    @RequestMapping(value = "/{userCode}",method = RequestMethod.GET)
    public @ResponseBody
    JsonResult checkUser(@PathVariable int userCode){
        if(employeeService.getEmployee(userCode) == null){
            return fail();
        }else{
            return  ok();
        }
    }

    /**
     * 检测身份证号
     * @param idc 身份证号码
     * @return
     */
    @RequestMapping(value = "/checkIdc/{idc}")
    public @ResponseBody
    JsonResult checkIDC(@PathVariable String idc,HttpServletRequest request){
        EmployeeBaseInfor employeeBaseInfor =  employeeBaseInforService.queryEmployeeByIdc(idc,this.getSesionUser(request).getCompany());
        if(employeeBaseInfor != null){
            return fail().put("userCode",employeeBaseInfor.getUserCode())
                    .put("isBlack", employeeBaseInfor.getIsBlack());
        }else {
            return ok();
        }
    }
    @RequestMapping(value = "/shielded/{userCode}")
    public @ResponseBody JsonResult shielded(@PathVariable int userCode,HttpServletRequest request){
        Employee  emp = this.getSesionUser(request);
        if(!PrivilegeHelper.checkPrivilege(emp.getPrivileges(), Constants.USERCODE_DISABLED)) {
            return fail("对不起，你没有该权限，请联系管理员");
        }
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return fail("对不起，不允许跨公司操作");
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userCode",userCode);
        paramMap.put("creator",emp.getUserCode());
        if(employeeBaseInforService.shielded(paramMap, userCode)){
            return ok();
        }else{
            return fail("操作失败");
        }
    }

    @RequestMapping(value = "/enable/{userCode}")
    public @ResponseBody JsonResult enable(@PathVariable int userCode,HttpServletRequest request){
        Employee  emp = this.getSesionUser(request);
        if(!PrivilegeHelper.checkPrivilege(emp.getPrivileges(), Constants.USERCODE_ENABLED)) {
            return fail("对不起，你没有该权限，请联系管理员");
        }
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return fail("对不起，不允许跨公司操作");
        }
        if(employeeBaseInforService.enable(userCode)){
            return ok();
        }else{
            return fail("操作失败");
        }
    }

    /**
     * 员工报到处理
     * @param userCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/deal/{userCode}", method = RequestMethod.GET)
    public @ResponseBody JsonResult toDeal(@PathVariable int userCode,HttpServletRequest request){
        Employee  emp = this.getSesionUser(request);
        if(!PrivilegeHelper.checkPrivilege(emp.getPrivileges(),Constants.EMPLOYEE_REGISTER)){
            return fail("没有权限，请联系管理员");
        }
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return fail("对不起，不允许跨公司操作");
        }
        EmployeeBaseInfor employeeBaseInfor = employeeBaseInforService.findById(userCode);
        if(employeeBaseInfor != null) {
            return ok().put("employee", employeeBaseInfor);
        }
        return fail();
    }

    /**
     * 处理员工报到
     * @param request
     * @param employeeBaseInfor
     * @param employeeRegister 不报到信息
     * @param register 是否报到
     * @param change 是否修改了员工信息
     * @param userCode 报到的工号
     * @return
     */
    @RequestMapping(value = "/deal/{userCode}", method = RequestMethod.POST)
    public @ResponseBody JsonResult deal(HttpServletRequest request, @ModelAttribute EmployeeBaseInfor employeeBaseInfor, @ModelAttribute EmployeeRegister employeeRegister, @RequestParam(defaultValue = "false") boolean register, @RequestParam(defaultValue = "false") boolean change, @PathVariable int userCode) {
        Employee  emp = this.getSesionUser(request);
        if(!PrivilegeHelper.checkPrivilege(emp.getPrivileges(),Constants.EMPLOYEE_REGISTER)){
            return fail("没有权限，请联系管理员");
        }
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return fail("对不起，不允许跨公司操作");
        }
        employeeRegister.setCreator(emp.getUserCode());
        employeeBaseInfor.setUpdator(emp.getUserCode());
        if(!register && employeeBaseInforService.updateStatusToNotRegister(employeeBaseInfor, employeeRegister)) {
            return ok();
        } else {
            Employee unemp = employeeService.getEmployee(userCode, EmployeeStatus.ToRegisteHeadquarters, EmployeeStatus.ToRegisteBranch);
            if(unemp == null) {
                return fail("该员工不存在，请联系管理员");
            }
        /*    if(employeeBaseInforService.isExits13(unemp.getOrgId())){
                return fail("对不起，每个分行只允许存在员工13名");
            }*/
            // 是否经纪人
            boolean isAgency = unemp.getPositionId() == Position.BROKER_POSITION_ID ? true : false;
            if(register && change && employeeBaseInforService.updateStatusToJoinAndInfo(employeeBaseInfor, WebUtils.findParamStr(request, "mobilePhone"))) {
            	if (isAgency) {
            		// 返回经纪人的员工状态为'门店待报到'
                    return ok().put("empStatus", EmployeeStatus.ToRegisteBranch.toString());
            	} else {
                    return ok();
            	}
            } else if(register && !change && employeeBaseInforService.updateStatusToJoin(employeeBaseInfor.getUserCode(), emp.getUserCode())) {
              	if (isAgency) {
            		// 返回经纪人的员工状态为'门店待报到'
                    return ok().put("empStatus", EmployeeStatus.ToRegisteBranch.toString());
            	} else {
                    return ok();
            	}
            }
        }
        return fail("操作失败");
    }

    /**
     * 处理给定工号状态为门店已报到
     * @param request
     * @param userCode 给定工号
     * @return 处理结果
     */
    @RequestMapping(value="/dealBranchRegisted/{userCode}", method= RequestMethod.GET)
	public @ResponseBody
	String dealBranchRegisted(HttpServletRequest request, @PathVariable("userCode") int userCode) {
		String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
		Employee emp = this.getSesionUser(request);
		if (emp == null) {
			return jsoncallback + "(" + fail("请登录") + ")";
		}
		if (!PrivilegeHelper.checkPrivilege(emp.getPrivileges(), Constants.EMPLOYEE_REGISTER)) {
			return jsoncallback + "(" + fail("没有权限，请联系管理员") + ")";
		}
		if (!employeeService.checkEmployee(userCode, emp.getCompanyObject())) {
			return jsoncallback + "(" + fail("对不起，不允许跨公司操作") + ")";
		}
        EmployeeStatusRecord employeeStatusRecord = employeeStatusRecordService.findById(userCode);
        // 新增员工时设置的员工状态
        String empStatus = null;
        if (employeeStatusRecord != null) {
        	empStatus = employeeStatusRecord.getStatus();
        } else {
        	// 没有设置的场合, 默认为'试用期'
        	empStatus = EmployeeStatus.UnFormal.toString();
        }
		if (employeeBaseInforService.updateStatusToJoin(userCode, emp.getUserCode(), true)) {
			return jsoncallback + "(" + ok().put("updateStatus", true).put("empStatus", empStatus) + ")";
		}
		return jsoncallback + "(" + fail("员工状态更新失败, 请稍后操作或者后台提出反馈") + ")";
	}

    /**
     * 处理给定工号状态为门店未报到
     * @param request
     * @param userCode 给定工号
     * @return 处理结果
     */
    @RequestMapping(value="/dealBranchUnRegisted/{userCode}", method= RequestMethod.GET)
	public @ResponseBody String dealBranchUnRegisted(HttpServletRequest request, @PathVariable("userCode") int userCode) {
		String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
		Employee emp = this.getSesionUser(request);
		if (emp == null) {
			return jsoncallback + "(" + fail("请登录") + ")";
		}
		if (!PrivilegeHelper.checkPrivilege(emp.getPrivileges(), Constants.EMPLOYEE_REGISTER)) {
			return jsoncallback + "(" + fail("没有权限，请联系管理员") + ")";
		}
		if (!employeeService.checkEmployee(userCode, emp.getCompanyObject())) {
			return jsoncallback + "(" + fail("对不起，不允许跨公司操作") + ")";
		}
		if (employeeBaseInforService.updateStatusToJoin(userCode, emp.getUserCode(), false)) {
			return jsoncallback + "(" + ok().put("updateStatus", false) + ")";
		}
		return jsoncallback + "(" + fail("员工状态更新失败, 请稍后操作或者后台提出反馈") + ")";
	}

    /**
     * 恢复待报到状态
     * @param request
     * @param userCode 工号
     * @return
     */
    @RequestMapping(value = "/recover/{userCode}/{userStatus}", method = RequestMethod.POST)
    public @ResponseBody JsonResult recoverHeadquarters(HttpServletRequest request, @PathVariable int userCode, @PathVariable String userStatus) {
        try {
            Employee  emp = this.getSesionUser(request);
            if(!PrivilegeHelper.checkPrivilege(emp.getPrivileges(),Constants.EMPLOYEE_REGISTER)){
                return fail("没有权限，请联系管理员");
            }
            if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
                return fail("不允许跨公司操作");
            }
            EmpToSendMsg empToSendMsg = empToSendMsgService.findById(userCode);
            if(empToSendMsg == null) {
                return fail("该员工不存在");
            }
            //判断是否达到区域人数上限
            if(employeeBaseInforService.isMoreThanNormal(empToSendMsg.getOrgId())) {
                return fail("本组织所在区域已经满员，无法恢复员工状态");
            }
            //恢复状态
            employeeBaseInforService.recoverDelete(userCode, emp.getUserCode(), userStatus);
            try {
                //发送短信提醒
                employeeBaseInforService.sendWarnings(empToSendMsg.getOrgId(), new Date());
            } catch (Exception e) {
                LOG.error("发送分行员工数量提醒短信失败", e);
                return fail("发送分行员工数量提醒短信失败");
            }
            return ok();
        } catch (Exception e) {
            LOG.error("恢复状态失败，usercode = " + userCode, e);
            return fail();
        }
    }

    private String getResidentType(int userCode){
        String residentType = "";
        if(attachmentService.hasAttach(Attachment.TEMP_RESIDENCE_PERMIT_TYPE,userCode)){
            residentType = Attachment.TEMP_RESIDENCE_PERMIT_TYPE;
        }
        if(attachmentService.hasAttach(Attachment.LONG_TERM_RESIDENCE_PERMIT_TYPE,userCode)){
            residentType = Attachment.LONG_TERM_RESIDENCE_PERMIT_TYPE;
        }
        return residentType;
    }

    /**
     * 员工标签编辑页面请求
     * @since: 2014-05-21 15:17:15
     * @param userCode
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/{userCode}/edit",method = RequestMethod.GET)
    public String editTag(@PathVariable int userCode,Model model,HttpServletRequest request){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        if(!emp.checkPrivilege(Constants.EMPLOYEE_TAG)) {
            return errorNoPrivilege(model);
        }
        List<EmployeeTag> employeeTags = employeeTagService.queryByUserCode(userCode);
        Map<String, EmployeeTag> employeeTagMap = new HashMap<>();
        for(EmployeeTag e : employeeTags) {
            employeeTagMap.put(e.getTagCode(), e);
        }
        model.addAttribute("userCode", userCode);
        model.addAttribute("employeeTags",employeeTagMap);
        model.addAttribute("tagList",parameterService.findByTypeKey(Parameter.TAG_KEY));
        return "empTag/empTagEdit";
    }

    /**
     * 员工标签编辑提交
     * @since: 2014-05-21 15:17:30
     * @param employeeTagCodes
     * @param userCode
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/{userCode}/edit",method = RequestMethod.POST)
    public String saveTag(String[] employeeTagCodes, @PathVariable int userCode,Model model,HttpServletRequest request){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        if(!emp.checkPrivilege(Constants.EMPLOYEE_TAG)) {
            return errorNoPrivilege(model);
        }
        try {
            employeeTagService.update(employeeTagCodes, userCode, emp.getUserCode());
            model.addAttribute("userCode",userCode);
            model.addAttribute("employeeTags",employeeTagService.queryByUserCode(userCode));
            return "empTag/empTagDetails";
        } catch (Exception e) {
            LOG.error("更新标签出错。", e);
        }
        return "redirect:/employee/tag/{userCode}/details";
    }

    /**
     * 员工标签详情
     * @since: 2014-05-21 15:17:49
     * @param userCode
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/tag/{userCode}/details",method = RequestMethod.GET)
    public String detailTag(@PathVariable int userCode,HttpServletRequest request,Model model){
        Employee emp = this.getSesionUser(request);
        if(!employeeService.checkEmployee(userCode, emp.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        model.addAttribute("userCode",userCode);
        model.addAttribute("employeeTags",employeeTagService.queryByUserCode(userCode));
        return "empTag/empTagDetails";
    }


}

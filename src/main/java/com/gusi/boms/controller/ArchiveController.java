package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.model.ArchivePageModel;
import com.gusi.boms.model.EmployeeDetails;
import com.gusi.boms.model.EmployeeEducationExper;
import com.gusi.boms.model.Parameter;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.web.helper.JsonResult;
import com.gusi.boms.service.*;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *	author:liuhui
 *	createTime: liuhui (2013-5-23 上午10:14:45 )
 *  员工档案
 */
@Controller
@RequestMapping(value="/archives")
public class ArchiveController extends  BomsBaseController {

    private static final Logger logger=Logger.getLogger(ArchiveController.class);

    @Autowired
    private EmployeeDetailsService employeeDetailsService;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private EmployeeFamilyService employeeFamilyService;
    @Autowired
    private EmployeeEducationExperService employeeEducationExperService;
    @Autowired
    private EmployeeTrainService employeeTrainService;
    @Autowired
    private EmployeeWorkExperService employeeWorkExperService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;

    /**
	 * @param userCode
	 * @param request
	 * @param model
	 * @return 查看档案详情
	 */
	@RequestMapping(value="/{userCode}",method=RequestMethod.GET)
	public String detail(@PathVariable(value="userCode")int userCode,HttpServletRequest request,
			Model model)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
		 if(ep.checkPrivilege(Constants.ARCHIVES_DETAIL)){
			 //档案信息
			 EmployeeDetails details=employeeDetailsService.findForDetail(userCode);
			 if (details==null) {
				 //先创建档案
				 Employee emp=employeeService.getEmployee(userCode, 0, 0, EmployeeStatus.All);
				 if (employeeDetailsService.createArchives(emp.getUserCode(),emp.getCredential(),emp.getMobilePhone(),80001)) {
					 details=employeeDetailsService.findForDetail(userCode);
				 }
			 }
			 if (details !=null) {
				 initDetails(details);
				 model.addAttribute("details", details);
				 //导航信息
				 Employee employee=new Employee();
				 employee.setUserCode(userCode);
				 employee.setUserName(details.getUserNameCn());
				 employee.setOrgName(details.getOrgName());
				 employee.setPositionName(details.getPositionName());
				 model.addAttribute("emp", employee);
                 model.addAttribute("orgLongName", employeeBaseInforService.findorgLongNameByUserCode(userCode));
				 model.addAttribute("userCode", userCode);
				 //教育经历等等
				 model.addAttribute("educations", employeeEducationExperService.findByUserCode(userCode));
				 model.addAttribute("works", employeeWorkExperService.findByUserCode(userCode));
				 model.addAttribute("familyMembers", employeeFamilyService.findFamiliesByUserCode(userCode));
				 model.addAttribute("trains", employeeTrainService.findTrainsByUserCode(userCode));
			 }
			 return "/archives/archives";
		}else
		{
		 return errorNoPrivilege(model);
		}
	}
	/**
	 * @param userCode
	 * @param request
	 * @param model
	 * @return 查看档案详情
	 */
	@RequestMapping(value="/{userCode}/basic",method=RequestMethod.GET)
	public String basicDetail(@PathVariable(value="userCode")int userCode,HttpServletRequest request,
			Model model)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
		 if(getSesionUser(request).checkPrivilege(Constants.ARCHIVES_DETAIL)){
			EmployeeDetails details=employeeDetailsService.findForDetail(userCode);
			initDetails(details);
			model.addAttribute("details", details);
			model.addAttribute("userCode", userCode);
		 }
		return "/archives/basic";
	}

	/**
     * 确认档案
     * @update: 2014-05-21 11:44:17
	 * @param userCode
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{userCode}/checkArchives")
	public String checkArchives(@PathVariable(value="userCode")int userCode,Model model,
			HttpServletRequest request)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，不允许跨公司操作");
        }
		 if(ep.checkPrivilege(Constants.ARCHIVES_SURE)){
             //是否为员工打上百强高校标签
             String isBq = request.getParameter("isBq");
            if (employeeDetailsService.updateChecked(userCode, ep.getUserCode()
                    , StringUtils.isNotBlank(isBq) && "1".equalsIgnoreCase(isBq) ? true : false)) {
                return "redirect:/archives/"+userCode;
            }
			return error(model, "档案确认失败，请及时联络相关负责人。");
		 }else
		 {
			 return errorNoPrivilege(model);
		 }
	}

    /**
	 * @param userCode
	 * @param model
	 * @param request
	 * @return 确认档案
	 */
	@RequestMapping(value="/{userCode}/uncheckArchives")
	public String uncheckArchives(@PathVariable(value="userCode")int userCode,Model model,
			HttpServletRequest request)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，不允许跨公司操作");
        }
		 if(ep.checkPrivilege(Constants.ARCHIVES_SURE)){
				if (employeeDetailsService.updateUnchecked(userCode, getSesionUser(request).getUserCode())) {
					return "redirect:/archives/"+userCode;
				}
			return error(model, "撤销确认失败，请及时联络相关负责人。");
		 }else
		 {
			 return errorNoPrivilege(model);
		 }
	}

	/**
     * 打回修改档案,记录打回原因
     * @update: 2014-05-22 14:08:58
	 * @param userCode
	 * @param rollbackReason
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{userCode}/rollbackArchives", method = RequestMethod.POST)
    @ResponseBody
	public JsonResult rollbackArchives(@PathVariable int userCode,@RequestParam String rollbackReason,
			HttpServletRequest request)
	{
        Employee ep = getSesionUser(request);
        if (!employeeService.checkEmployee(userCode, ep.getCompanyObject())) {
            return fail("对不起，不允许跨公司操作");
        }
        if (ep.checkPrivilege(Constants.ARCHIVES_SURE)) {
            if (employeeDetailsService.rollbackArchives(userCode, rollbackReason, ep.getUserCode())) {
                return ok();
            }
            return fail("确认失败，请及时联络相关负责人。");
        } else {
            return fail("没有权限");
        }
    }

    /**
	 * @param userCode
	 * @param request
	 * @param model
	 * @return 查看档案详情
	 */
	@RequestMapping(value="/{userCode}/BasicEdit",method=RequestMethod.GET)
	public String BasicEdit(@PathVariable(value="userCode")int userCode,HttpServletRequest request,
			Model model)
	{
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
		 if(ep.checkPrivilege(Constants.ARCHIVES_BASIC_EDIT)){
			EmployeeDetails details=employeeDetailsService.findForEdit(userCode);
			model.addAttribute("bloodMap", parameterService.findBloodTypes());
			model.addAttribute("maritalMap", parameterService.findMarriages());
			model.addAttribute("constellationMap", parameterService.findConstellations());
			model.addAttribute("nationList", parameterService.findNationList());
//			model.addAttribute("degreeMap", parameterService.findEducationDegrees());
             model.addAttribute("degreeList",parameterService.findByTypeKey(Parameter.DEGREE_KEY));
             model.addAttribute("studyTypeList",parameterService.findByTypeKey(Parameter.STUDY_TYPE_KEY));
			model.addAttribute("politicalMap", parameterService.findPoliticalBackgrounds());
			model.addAttribute("provinceMap", parameterService.findProvinces());
			if (details.getFamilyAddressProvince() !=null) {
				model.addAttribute("cityMap", parameterService.findMapByTypeKey(details.getFamilyAddressProvince()));
			}

			model.addAttribute("userCode", userCode);
			model.addAttribute("details", details);
			return "/archives/BasicEdit";
		 }
		 return "redirect:/archives/"+userCode+"/basic";
	}

    /**
	 * @param details
	 *  初始化档案详情
	 */
	private void initDetails(EmployeeDetails details)
	{
		//血型
		 details.setBloodTypeValue(parameterService.findBloodTypes().get(details.getBloodType()));
		 //婚姻状态
		 details.setMaritalStatusValue(parameterService.findMarriages().get(details.getMaritalStatus()));
		 //星座
		 details.setConstellationValue(parameterService.findConstellations().get(details.getConstellation()));
		 //民族
		 details.setNationValue(parameterService.findNations().get(details.getNation()));
		 //最近学位
		 details.setLastDegreeValue(parameterService.findEducationDegrees().get(details.getLastDegree()));
		 //最近学位学习类型
		 details.setLastStudyTypeValue(parameterService.findStudyType().get(details.getLastStudyType()));
		 //政治背景
		 details.setPoliticalBackGroundValue(parameterService.findPoliticalBackgrounds().get(details.getPoliticalBackGround()));
	}

    /**
     * @param userCode
     * @param model
     * @return 获取家庭成员的详情页
     */
    @RequestMapping(value = "/{userCode}/FamilyMemberDetail")
    public String getFamilyMemeberDetailHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	 if(ep.checkPrivilege(Constants.ARCHIVES_DETAIL)){
	    	model.addAttribute("userCode", userCode);
	    	 model.addAttribute("familyMembers", employeeFamilyService.findFamiliesByUserCode(userCode));
    	 }
        return "/archives/FamilyMemberDetail";
    }

    /**
     * @param userCode
     * @param model
     * @return 获取编辑家庭成员的页面
     */
    @RequestMapping(value = "/{userCode}/FamilyMemberEdit")
    public String getFamilyMemberEditHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	 if(ep.checkPrivilege(Constants.ARCHIVES_FAMILY_EDIT)){
		    	model.addAttribute("userCode", userCode);
		    	model.addAttribute("familyMembers", employeeFamilyService.findFamiliesByUserCode(userCode));
		        return "/archives/FamilyMemberEdit";
    	 }
    	 return "redirect:/archives/"+userCode+"/FamilyMemberDetail";
    }

    /**
     * @param userCode
     * @param model
     * @return 获取培训经验的详情页
     */
    @RequestMapping(value = "/{userCode}/TrainingExperienceDetail")
    public String getTrainingExperienceDetailHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	 if(ep.checkPrivilege(Constants.ARCHIVES_DETAIL)){
	    	model.addAttribute("userCode", userCode);
	    	model.addAttribute("trains", employeeTrainService.findTrainsByUserCode(userCode));
    	 }
        return "/archives/TrainingExperienceDetail";
    }

    /**
     * @param userCode
     * @param model
     * @return 获取编辑培训经验的页面
     */
    @RequestMapping(value = "/{userCode}/TrainingExperienceEdit")
    public String getTrainingExperienceEditHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	if(ep.checkPrivilege(Constants.ARCHIVES_TRAIN_EDIT)){
    	model.addAttribute("userCode", userCode);
    	model.addAttribute("trains", employeeTrainService.findTrainsByUserCode(userCode));
        return "/archives/TrainingExperienceEdit";
    	}
    	return "redirect:/archives/"+userCode+"/TrainingExperienceDetail";
    }

    /**
     * @param userCode
     * @param model
     * @return 获取工作经验的详情页
     */
    @RequestMapping(value = "/{userCode}/WorkExperienceDetail")
    public String getWorkExperienceDetailHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	 if(ep.checkPrivilege(Constants.ARCHIVES_DETAIL)){
	    	model.addAttribute("userCode", userCode);
	    	model.addAttribute("works", employeeWorkExperService.findByUserCode(userCode));
    	 }
        return "/archives/WorkExperienceDetail";
    }

    /**
     * @param userCode
     * @param model
     * @return 获取编辑工作经验的页面
     */
    @RequestMapping(value = "/{userCode}/WorkExperienceEdit")
    public String getWorkExperienceEditHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	if(ep.checkPrivilege(Constants.ARCHIVES_WORK_EDIT)){
	    	model.addAttribute("userCode", userCode);
	    	model.addAttribute("works", employeeWorkExperService.findByUserCode(userCode));
	        return "/archives/WorkExperienceEdit";
    	}
    	return "redirect:/archives/"+userCode+"/WorkExperienceDetail";
    }

    /**
     * 获取教育经验的详情页
     * @update: 2014-05-27 16:25:17
     * @param userCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/{userCode}/EducationExperienceDetail")
    public String getEducationExperienceDetailHtml(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	 if(ep.checkPrivilege(Constants.ARCHIVES_DETAIL)){
	    	model.addAttribute("userCode", userCode);
	    	model.addAttribute("educations", employeeEducationExperService.findByUserCode(userCode));
             EmployeeDetails details = new EmployeeDetails();
             details.setStatus(0);
	    	model.addAttribute("details", details);
    	 }
        return "/archives/EducationExperienceDetail";
    }

    /**
     * 获取教育经历编辑页面
     * @since: 2014-05-22 14:07:49
     * @param userCode
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/{userCode}/eduEdit")
    public String eduEdit(@PathVariable(value="userCode")int userCode,Model model,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
    	if(ep.checkPrivilege(Constants.ARCHIVES_EDUCATION_EDIT)){
    	 List<EmployeeEducationExper> educations=employeeEducationExperService.findByUserCode(userCode);
    	 Map<String,EmployeeEducationExper> educationMap=new HashMap<String, EmployeeEducationExper>();
    	 for (EmployeeEducationExper exper : educations) {
			String degree=exper.getDegree();
			if (degree !=null) {
				if (degree.equals("高中及以下") || degree.isEmpty() || degree.equals("小学") || degree.equals("中学")
						|| degree.equals("中专") || degree.equals("技校") ) {
					educationMap.put("高中及以下", exper);
				}else if(degree.equals("大专") || degree.equals("高职"))
				{
					educationMap.put("大专", exper);
				}else if (degree.equals("本科")) {
					educationMap.put("本科", exper);
				}else if (degree.equals("硕士")) {
					educationMap.put("硕士", exper);
				}else if (degree.equals("博士")) {
					educationMap.put("博士", exper);
				}else
				{
					educationMap.put("高中及以下", exper);
				}
			}
		}
    	model.addAttribute("educationMap", educationMap);
    	model.addAttribute("userCode", userCode);
        return "/archives/eduEdit";
    	}
      return "redirect:/archives/"+userCode+"/EducationExperienceDetail";
    }


    /**
     * @param userCode
     * @param archivePageModel
     * @return 更新工作经验
     */
    @RequestMapping(value = "/{userCode}/updateWorkExperience",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult updateWorkExperience(ArchivePageModel archivePageModel,@PathVariable(value="userCode")int userCode,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return fail("对不起，无此员工信息，请联系管理员");
        }
    	try {
    		if(employeeWorkExperService.batchInsert(archivePageModel.getWorks(), userCode,getSesionUser(request).getUserCode()))
    		{
    			//档案状态，撤除确认
    			employeeDetailsService.updateUnchecked(userCode, getSesionUser(request).getUserCode());
    		}
		} catch (Throwable e) {
			logger.error("更新工作经验出错。", e);
            return fail("更新工作经验出错");
		}
    	return ok();
    }

    /**
     * @param userCode
     * @param archivePageModel
     * @return 更新教育经验
     */
    @RequestMapping(value = "/{userCode}/updateEducationExperience",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult updateEducationExperience(ArchivePageModel archivePageModel,@PathVariable(value="userCode")int userCode,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return fail("对不起，无此员工信息，请联系管理员");
        }
    	try {
    		if(employeeEducationExperService.batchInsert(archivePageModel.getEducations(), userCode,getSesionUser(request).getUserCode()))
    		{
    			//档案状态，撤除确认
    			employeeDetailsService.updateUnchecked(userCode, getSesionUser(request).getUserCode());
    		}
		} catch (Throwable e) {
			logger.error("更新教育经验", e);
            return fail("更新教育经验");
		}
    	return ok();
    }

    /**
     * @param userCode
     * @param archivePageModel
     * @return 更新培训经验
     */
    @RequestMapping(value = "/{userCode}/updateTrainingExperience",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult updateTrainingExperience(ArchivePageModel archivePageModel,@PathVariable(value="userCode")int userCode,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return fail("对不起，无此员工信息，请联系管理员");
        }
    	try {
    		if(employeeTrainService.batchInsert(archivePageModel.getTrains(), userCode,getSesionUser(request).getUserCode()))
    		{
    			//档案状态，撤除确认
    			employeeDetailsService.updateUnchecked(userCode, getSesionUser(request).getUserCode());
    		}
		} catch (Throwable e) {
			logger.error("更新培训经验出错。", e);
            return fail("更新培训经验出错");
		}
    	return ok();
    }

    /**
     * @param userCode
     * @param archivePageModel
     * @return 更新家庭成员
     */
    @RequestMapping(value = "/{userCode}/updateFamilyMember",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult updateFamilyMember(ArchivePageModel archivePageModel,@PathVariable(value="userCode")int userCode,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return fail("对不起，无此员工信息，请联系管理员");
        }
    	try {
    		if(employeeFamilyService.batchInsert(archivePageModel.getFamilies(), userCode,getSesionUser(request).getUserCode()))
    		{
    			//档案状态，撤除确认
    			employeeDetailsService.updateUnchecked(userCode, getSesionUser(request).getUserCode());
    		}
		} catch (Throwable e) {
			logger.error("更新家庭成员出错。", e);
            return fail("更新家庭成员出错");
		}
    	return ok();
    }

    /**
     * @param userCode
     * @param details
     * @update: 2014-05-29 10:40:07
     * @return 更新档案信息
     */
    @RequestMapping(value = "/{userCode}/updateArchivesDetails",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult updateArchivesDetails(@ModelAttribute EmployeeDetails details,@PathVariable(value="userCode")int userCode,
    		HttpServletRequest request)
    {
        Employee  ep = getSesionUser(request);
        if(!employeeService.checkEmployee(userCode,ep.getCompanyObject())){
            return fail("对不起，无此员工信息，请联系管理员");
        }
    	try {
    		employeeDetailsService.updateArchives(details, userCode,getSesionUser(request).getUserCode());
		} catch (Throwable e) {
			logger.error("编辑员工档案出错。",e);
            return fail("编辑员工档案出错");
        }
    	return ok();
    }

    /**
     * @param phone
     * @return
     * 验证mobilePhone中是否已经存在phone.
     */
    @RequestMapping(value="/{phone}/validate")
    @ResponseBody
    public JsonResult validatePhone(@PathVariable(value="phone")String phone,HttpServletRequest request)
    {
        return employeeDetailsService.validatePhone(phone,this.getSesionUser(request))?ok():fail();
    }
    /**
    * @return
    */
   @RequestMapping(value="/{provinceCode}/getCityByProvinceCode")
   @ResponseBody
   public JsonResult getCityByProvinceCode(@PathVariable(value="provinceCode")String provinceCode)
   {
	   return ok().put("cityList", JSONArray.fromObject(parameterService.findByTypeKey(provinceCode)));
   }

    /**
     * 查询档案未确认的数量
     * @since: 2014-05-22 14:07:29
     * @param request
     * @return
     */
    @RequestMapping(value="/getUnCheckNum")
    @ResponseBody
    public JsonResult getUnCheckNum(HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!employee.checkPrivilege(Constants.ARCHIVES_SURE)) {
            return fail("没有权限！ ");
        }
        return ok().put("unCheckNum", employeeDetailsService.countUnCheckNum(employee.getCompany()));
    }

}

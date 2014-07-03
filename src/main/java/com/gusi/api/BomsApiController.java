package com.gusi.api;

import com.gusi.app.helper.AppHelper;
import com.gusi.app.service.AppEmployeeService;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.helper.CacheHelper;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.plus.oms.dyEnums.OrganizationStatus;
import com.dooioo.plus.oms.dyEnums.OrganizationType;
import com.dooioo.plus.oms.dyEnums.Range;
import com.dooioo.plus.oms.dyHelper.CompanyHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.plus.util.TextUtil;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.controller.BaseController;
import com.dooioo.web.helper.JsonResult;
import com.gusi.boms.service.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-12 13:08)
 * 为外界提供的组织架构的api服务
 */
@Controller
@RequestMapping(value = "/oms/api")
public class BomsApiController extends BaseController{
	private Logger logger=Logger.getLogger(this.getClass());
    @Autowired
    private FreemarkerUtil fkUtil;
    @Autowired
    private ApiTreeService apiTreeService;
    @Autowired
    private AppHelper appHelper;
    @Autowired
    private AppEmployeeService appEmployeeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleEmployeeService roleEmployeeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SimpleOrgService simpleOrgService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private LevelInfoService levelInfoService;
    @Autowired
    private BlogEmpArchivesService blogEmpArchivesService;

    /**
     * 获取服务器时间
     * @since: 2014-06-12 10:02:45
     * @return
     */
    @RequestMapping(value = "/getServerTime")
    @ResponseBody
    public JsonResult getBaseInfo(@RequestParam(defaultValue = "0") int dateDiff) {
        try {
            Map<String, Object> data = new HashMap<>();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, dateDiff);
            data.put("serverTime", c.getTime());
            data.put("serverTimeStr", DateFormatUtils.format(c.getTime(),"yyyy-MM-dd HH:mm:ss"));
            return ok().put("data", data);
        } catch (Exception e) {
            logger.error("获取服务器时间失败。", e);
            return fail("获取服务器时间失败。" + e);
        }
    }

    /**
     * 员工查询接口
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String employeeSearch(HttpServletRequest request) {
        String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
        String keysWord  = WebUtils.findParameterValue(request, "keysWord");
        if(keysWord == null || keysWord.isEmpty()) {
            return jsoncallback + "(" + ok().put("tree", null) + ")";
        }
        String company  = WebUtils.findParameterValue(request, "company");
        String treeType = WebUtils.findParameterValue(request, "treeType");
        String roleType  = WebUtils.findParameterValue(request, "roleType");
        String userStatus  = WebUtils.findParameterValue(request, "userStatus");

        String htmlTemp = null;
        Map<String, String> fltMap= new HashMap<>();
        fltMap.put("company", company == null ? Company.Dooioo.toString() : company);
        fltMap.put("keysWord", keysWord);
        fltMap.put("treeType", treeType);
        fltMap.put("roleType", roleType);
        fltMap.put("userStatus", userStatus);
        try {
            htmlTemp = fkUtil.writeTemplate("/search/search.ftl", fltMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List list = apiTreeService.search(htmlTemp);
        return jsoncallback + "(" + ok().put("tree", list) + ")";
    }
    /* 组织根据拼音查询获取名称自动完成
   	 * @param request
   	 * @return 返回对应的组织名称集合
   	 */
   	@RequestMapping(value = "/getOrgNameByPy", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
   	public @ResponseBody String getNameByPy(HttpServletRequest request) {
          String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
          String orgName = WebUtils.findParamStr(request, "keyword");
          if(StringUtils.isNotEmpty(orgName)) {
              orgName = TextUtil.skipHack(orgName);
          }     
          List<com.dooioo.plus.oms.dyUser.model.Organization>   	OrgNameList =null;
          String orgType=WebUtils.findParamStr(request,"orgType");
          Employee emp=new Employee();
          emp.setCompany(CompanyHelper.getDomainCompany(request).toString());
          if (StringUtils.isEmpty(orgType)) {
       	  OrgNameList = organizationService.getOrgsBySearchKey(emp,
       			   orgName, Range.Company,new OrganizationStatus[]{OrganizationStatus.Normal},null,null);
          }else{
       	   OrganizationType otype=OrganizationType.All;
       	   switch (orgType) {
   			case "分行":
   				 otype=OrganizationType.Branch;
   				break;
   			case "门店":
   				 otype= OrganizationType.Store;
   				 break;
   			case "区域":
   				otype=OrganizationType.District;
   				break;
   			case "部门":
   				otype=OrganizationType.Department;
   			}
       	   OrgNameList = organizationService.getOrgsBySearchKey(emp,
       			   orgName, Range.Company,new OrganizationStatus[]{OrganizationStatus.Normal,OrganizationStatus.Temporary},
       			   new OrganizationType[]{otype},null); 
          }
          if (StringUtils.isNotEmpty(jsoncallback)) {
       	   return jsoncallback + "(" + ok().put("OrgNameList", OrgNameList) + ")";
          }
          return ok().put("OrgNameList", OrgNameList).toJson();
   	}
    /**
     * 通讯录查询
     * @return 返回通讯录json
     */
    @RequestMapping(value = "/getContacts/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getContacts(@PathVariable int id) {
        return ok().put("result", appHelper.generateContacts(id)).toString().replaceAll("null", "\"\"");
    }
   
    /**
      * @since: 3.1.2 
      * @return
      * <p>
      *  查找德佑所有在职员工
      * </p>   
     */
    @RequestMapping(value="/getAllEmployees")
    @ResponseBody
    public JsonResult findAllEmployees(HttpServletRequest request){
    	logger.info("请求查询所有在职员工。");
    	return ok("成功查询德佑所有在职员工").put("emps", appEmployeeService.findEmployeesByCompany(Company.Dooioo.toString()));
    }
   
    /**
      * @since: 3.3.4 
      * @return
      * <p>
      *  根据员工信息更新时间来查询员工
      * </p>   
     */
    @ResponseBody
	@RequestMapping(value = "/findEmployeesByUpdateTime")
	public JsonResult findEmployeesByUpdateTime(HttpServletRequest request) {
    	try {
    		logger.info("根据员工信息更新时间来查询员工" + ", IP: " + request.getRemoteAddr() + ", StartTime: " + request.getParameter("startTime") + ", endTime: " + request.getParameter("endTime"));
    		String startTime = WebUtils.findParamStr(request, "startTime");
    		String endTime = WebUtils.findParamStr(request, "endTime") + " 23:59:59:997";
    		Map<String, Object> variables = new HashMap<String, Object>();
    		variables.put("startTime", startTime);
    		variables.put("endTime", endTime);
    		variables.put("company", Company.Dooioo.toString());
    		return ok("成功查询德佑员工信息变更时间在 " + startTime + " 与 " + endTime + " 之间的记录").put("emps",
    				appEmployeeService.findEmployeesByUpdateTime(variables));
    	} catch (Exception ex) {
    		logger.error("IP: " + request.getRemoteAddr() + ", StartTime: " + request.getParameter("startTime") + ", endTime: " + request.getParameter("endTime") + ", 查询失败。", ex);
    		return fail("查询失败！");
    	}
	}
    
    /**
      * @since: 3.1.2 
      * @param userCode
      * @return
      * <p>
      * 根据工号查询德佑在职员工
      * </p>   
     */
    @RequestMapping(value="/employee/{userCode}")
    @ResponseBody
    public JsonResult findEmployee(@PathVariable(value="userCode")String userCode){
    	logger.info("请求查询在职员工。");
    	return ok("成功查询德佑在职员工").put("emps",appEmployeeService.findEmployeesByCompany(Company.Dooioo.toString(),userCode));
    }
    
    /**
      * @since: 3.1.2 
      * @param request
      * @return
      * <p>
      * 根据多个工号查询德佑在职员工
      * </p>   
     */
    @RequestMapping(value="/getEmployees")
    @ResponseBody
    public JsonResult findEmployees(HttpServletRequest request){
    	logger.info("请求查询在职员工。");
    	String userCodes=WebUtils.findParamStr(request, "userCodes", null);
    	if (null == userCodes) {
			return fail("请指定参数userCodes，多个以逗号隔开。");
		}
    	return ok("成功查询德佑在职员工").put("emps",appEmployeeService.findEmployeesByCompany(Company.Dooioo.toString(),userCodes));
    }
    /**
      * @since: 2.7.2 
      * @param request
      * @return
      * <p>
      *  新增员工角色
      * </p>   
     */
    @RequestMapping(value="/addEmpRole/{userCode}/{roleId}",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult addEmployeeRole(HttpServletRequest request,@PathVariable(value="userCode")Integer userCode,
    		@PathVariable(value="roleId")int roleId){
    	 String addEmpRoleToken=request.getParameter("token");
    	 if (StringUtils.isBlank(addEmpRoleToken)) {
			return fail("非法请求,token 为空");
		}
    	if (!addEmpRoleToken.equals(Configuration.getInstance().getOmsApiWriteToken())) {
			return fail("token不正确。");
		}
    	if (!roleService.roleExists(roleId)) {
    		return fail("角色不存在。");
		}
    	if (roleEmployeeService.empRoleExists(userCode,roleId)) {
    		return fail("员工已有此角色。");
		}
    	if (roleEmployeeService.apiAddEmpRole(userCode, roleId)) {
    		return ok("员工角色新增成功");
		}
       return fail();
    }
    /**
      * @since: 2.7.4 
      * @param request
      * @param userCode
      * @return
      * <p>
      *  token='d59129a71aa1d3a65a8f58809cdc2191'
      *  移除房源的所有角色。
      * </p>   
     */
    @RequestMapping(value="/removeRoleForFY/{userCode}",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult removeRoleForFY(HttpServletRequest request,
    		@PathVariable(value="userCode")int userCode){
    	 String addEmpRoleToken=request.getParameter("token");
    	 if (StringUtils.isBlank(addEmpRoleToken)) {
			return fail("非法请求,token 为空");
		}
    	if (!addEmpRoleToken.equals(Configuration.getInstance().getOmsApiWriteToken())) {
			return fail("token不正确。");
		}
    	if (roleEmployeeService.removeRoleForFY(userCode)) {
    		return ok("员工角色删除成功");
		}
       return fail();
    }
    
    /**
      * @since: 3.0.5 
      * @param request
      * @param userCode
      * @return
      * <p>
      *  客源删除角色
      * </p>   
     */
    @RequestMapping(value="/removeRoleForKY/{userCode}",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult removeRoleForKY(HttpServletRequest request,
    		@PathVariable(value="userCode")int userCode){
    	 String addEmpRoleToken=request.getParameter("token");
    	 if (StringUtils.isBlank(addEmpRoleToken)) {
			return fail("非法请求,token 为空");
		}
    	if (!addEmpRoleToken.equals(Configuration.getInstance().getOmsApiWriteToken())) {
			return fail("token不正确。");
		}
    	if (roleEmployeeService.removeRoleForKY(userCode)) {
    		return ok("员工角色删除成功");
		}
       return fail();
    }

    /**
     * 根据组织信息更新时间来查询组织
     * @since: 2014-05-08 14:42:33
     * @param request
     * @return
     */
    @RequestMapping(value="/org/list",method=RequestMethod.GET)
    @ResponseBody
    public JsonResult orgList(HttpServletRequest request) {
        try {
        	logger.info("根据组织信息更新时间来查询组织" + ", IP: " + request.getRemoteAddr() + ", StartTime: " + request.getParameter("startTime") + ", endTime: " + request.getParameter("endTime"));
            String company = WebUtils.findParamStr(request, "company", "德佑");
            String status = WebUtils.findParamStr(request, "status", "0,1");
            String startTime = WebUtils.findParamStr(request, "startTime");
            String endTime = WebUtils.findParamStr(request, "endTime") + " 23:59:59:997";
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("company", company);
            variables.put("status", status);
            variables.put("startTime", startTime);
            variables.put("endTime", endTime);
            return ok("成功查询德佑组织信息变更时间在 " + startTime + " 与 " + endTime + " 之间的记录").put("orgList", simpleOrgService.queryOrgsApi(variables));
        } catch (Exception e) {
            logger.error("IP: " + request.getRemoteAddr() + ", StartTime: " + request.getParameter("startTime") + ", endTime: " + request.getParameter("endTime") + ", 查询失败。", e);
            return fail("查询失败！");
        }
    }

    /**
     * 查询学校
     * @since: 2014-05-20 10:21:42
     * @param request
     * @return
     */
    @RequestMapping(value="/getUniversity", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getSchools(HttpServletRequest request) {
        String jsoncallback = request.getParameter("jsoncallback");
        String keyword = request.getParameter("keyword");
        String countryId = request.getParameter("countryId");
        try {
            return org.apache.commons.lang.StringUtils.isBlank(jsoncallback) ?
                    ok().put("schoolList", schoolService.queryByName(keyword, countryId)).toString()
                    : jsoncallback + "(" + ok().put("schoolList", schoolService.queryByName(keyword, countryId)).toString() + ")";
        } catch (Exception e) {
            logger.error("查询失败！keyword" + keyword + "  countryId" + countryId, e);
            return fail("查询失败！").toString();
        }
    }

    /**
     * 获取员工档案打回信息
     * @since: 2014-06-11 09:30:44
     * @param userCode
     * @return
     */
    @RequestMapping(value="/getEmpArchInfo/{userCode}")
    @ResponseBody
    public String getEmpArchInfo(@PathVariable int userCode, @RequestParam(defaultValue = "") String jsoncallback) {
        try {
            return org.apache.commons.lang.StringUtils.isBlank(jsoncallback) ?
                    ok().put("empArchInfo", blogEmpArchivesService.findById(userCode)).toString()
                    : jsoncallback + "(" + ok().put("empArchInfo", blogEmpArchivesService.findById(userCode)).toString() + ")";
        } catch (Exception e) {
            logger.error("获取员工档案打回信息失败！userCode=" + userCode, e);
            return fail("获取员工档案打回信息失败！").toString();
        }
    }

    /**
     * 获取职等职级树
     * @since: 2014-06-06 14:33:03
     * @return
     */
    @RequestMapping(value = "/getLevels", method = RequestMethod.GET)
    @ResponseBody
    public String getLevels(HttpServletRequest request) {
        String jsoncallback = request.getParameter("jsoncallback");
        try {
            Map<String, Object> responseData = new HashMap<String, Object>();
            responseData.put("levelList", levelInfoService.getTree());
            return org.apache.commons.lang.StringUtils.isBlank(jsoncallback) ?
                    ok().put("data", responseData).toJson().replace(",\"children\":null", "")
                    : jsoncallback + "(" + ok().put("data", responseData).toJson().replace(",\"children\":null", "") + ")";
        } catch (Exception e) {
            logger.error("调用职等职级树接口失败！", e);
            return fail().toJson();
        }
    }

    /**
     * 清除职等职级树缓存
     * @since: 2014-06-06 14:33:57
     * @return
     */
    @RequestMapping(value = "/clearLevelsCache", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult clearLevelsCache() {
        try {
            CacheHelper.clearLevelTreeCache();
        } catch (Exception e) {
            return fail();
        }
        return ok();
    }

}

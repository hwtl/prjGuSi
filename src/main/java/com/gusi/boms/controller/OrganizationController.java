package com.gusi.boms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gusi.boms.model.Organization;
import com.gusi.boms.model.VOrganization;
import com.gusi.boms.service.Organization2Service;
import com.gusi.boms.util.DateFormatUtil;
import com.gusi.boms.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.OrganizationHelper;
import com.gusi.boms.model.OrganizationEmployeeCount;
import com.gusi.boms.model.OrganizationPhone;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.gusi.boms.service.EmployeePositionService;
import com.gusi.boms.service.OrganizationEmployeeCountService;
import com.gusi.boms.service.OrganizationOrgCountService;
import com.gusi.boms.service.OrganizationPhoneService;
import com.gusi.boms.service.OrganizationTransferService;
import com.dooioo.plus.oms.dyEnums.OrganizationStatus;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.plus.util.TextUtil;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.helper.JsonResult;

import freemarker.template.TemplateException;

/**
 *
* @ClassName: OrganizationController
* @Description: 组织部门控制器
* @author fdj
* @date 2013-4-11 下午2:19:51
 */
@Controller
@RequestMapping(value="/organization")
public class OrganizationController extends BomsBaseController {
    private Logger logger=Logger.getLogger(this.getClass());
    private static final Log LOG = LogFactory.getLog(OrganizationController.class);

    @Autowired
    private Organization2Service organization2Service;
    @Autowired
	private OrganizationPhoneService orgPhoneService;
    @Autowired
	private OrganizationService organizationService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private OrganizationTransferService organizationTransferService;
    @Autowired
    private OrganizationEmployeeCountService organizationEmployeeCountService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private OrganizationOrgCountService organizationOrgCountService;
    @Autowired
    FreemarkerUtil freemarkerUtil;
    /**
     * 组织列表跳转拦截
     * @param model 实体
     * @return url
     */
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public String list(VOrganization org, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo, Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_BASE)){
            return errorNoPrivilege(model);
        }
        org.setCompany(employee.getCompany());
        model.addAttribute("paginate", organization2Service.queryForPaginate(org, pageNo));
        return "/organization/list";
    }

    /**
     * 组织详情
     * @param id
     * @param model
     * @return
     */
	@RequestMapping(value="/{id}/details",method= RequestMethod.GET)
	public String details(@PathVariable int id, Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!organizationService.checkOrg(id,employee.getCompanyObject())){
            return error(model, "对不起，无此组织信息,请联系管理员");
        }
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_DETAILS)){
            return errorNoPrivilege(model);
        }
        model.addAttribute("organization", organization2Service.findViewById(id));
        model.addAttribute("orgBZ", organization2Service.findBZForDetail(id));
        model.addAttribute("orgPhoneList", orgPhoneService.queryForListByOrgId(id));
        try {
            model.addAttribute("orgMaxCount", organizationOrgCountService.findById(id).getMaxCount());
        } catch (Exception e) {
            LOG.error("获取组织树失败!id="+ id,e);
            model.addAttribute("orgMaxCount", 0);
        }
        return "/organization/details";
	}

    /**
     * 获取暂停分行的浮动人数记录
     * @since: 2014-06-09 14:23:16
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/getExtraEmpNoList",method= RequestMethod.GET)
    @ResponseBody
    public JsonResult getExtraEmpNoList(@PathVariable int id) {
        try {
            return ok().put("extraEmpNoList",organizationEmployeeCountService.queryStopBranchEmpNo(id));
        } catch (Exception e) {
            return fail("获取失败！");
        }
    }

    /**
     * 新增组织跳转拦截
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.GET)
    public String add(HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_ADD)){
            return errorNoPrivilege(model);
        }
        Organization tempOrg = new Organization();
        tempOrg.setParentId(Constants.TEMP_ORG_PARENTID);
        model.addAttribute("baseHtml", organization2Service.getBaseEditHtml(tempOrg, false, false));
        model.addAttribute("faxHtml", organization2Service.getOrgFaxEditHtml(tempOrg));
        model.addAttribute("phoneHtml", organization2Service.getPhoneEditHtml(new ArrayList<OrganizationPhone>()));
        return "/organization/form";
    }

    /**
     * 保存新增组织拦截
     * @param org 新增的组织
     * @param model 实体
     * @param request 浏览器请求
     * @return 重定向
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/add",method= RequestMethod.POST)
    public String addPost(@ModelAttribute Organization org, Model model, HttpServletRequest request){
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_ORG_ADD, Constants.OMS_OM_ORG_PHONE)){
            return errorNoPrivilege(model);
        }
        int count = 0;
        String orgMaxCount = request.getParameter("orgMaxCount");
        if(StringUtils.isNotEmpty(orgMaxCount)) {
            try {
                count = Integer.parseInt(orgMaxCount);
            } catch (NumberFormatException e) {
                LOG.error("获取组数失败" + orgMaxCount, e);
            }
        }
        String orgPhoneListJson = WebUtils.findParamStr(request, "orgPhoneList");
        List<OrganizationPhone> orgPhoneList = JsonUtils.getDTOList(JsonUtils.jsonReplace(orgPhoneListJson), OrganizationPhone.class);
        org = OrganizationHelper.setOrgPhone(org, orgPhoneList);
        if(this.isSuperAdmin(employee) || PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_ADD)) {
        	try {
                //插入/编辑组织
        		 int id = organization2Service.insertOrUpdateOrg(org, employee, count);
                 if(id > 0){
                     orgPhoneService.addPhoneList(orgPhoneList, org);
                     return "redirect:/organization/"+id+"/details";
                 }
			} catch (Exception e) {
				logger.error("新增或编辑组织出错。"+org, e);
				return error(model, "操作失败，系统反馈："+e.getMessage());
			}
        }
        if(PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_PHONE)){
            if(org.getId() > 0){
                orgPhoneService.addPhoneList(orgPhoneList, org);
                return "redirect:/organization/"+org.getId()+"/details";
            }
        }
        return error(model,"对不起，操作失败，请联系管理员");
    }

    /**
     * 编辑组织跳转拦截
     * @param id 被编辑的组织id
     * @param model 实体
     * @param request 浏览器请求
     * @return url
     */
    @RequestMapping(value="/{id}/edit",method= RequestMethod.GET)
    public String editGet(@PathVariable int id, Model model, HttpServletRequest request){
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege( Constants.OMS_OM_ORG_ADD, Constants.OMS_OM_ORG_PHONE)){
            return errorNoPrivilege(model);
        }
        if(!organizationService.checkOrg(id,employee.getCompanyObject())){
            return error(model,"对不起，无此组织信息,请联系管理员");
        }
        if(!organizationTransferService.checkTransfer(id)) {
            return error(model, "对不起，该组织正在进行整组转调或对调中，并且尚未生效，请耐心等待生效后编辑。");
        }
        Organization organization = organization2Service.findViewById(id);
        List<OrganizationPhone> orgPhoneList = orgPhoneService.queryForListByOrgId(id);
        if(this.isSuperAdmin(employee) ||  PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(),
        		Constants.OMS_OM_ORG_ADD)) {
            boolean canEditOrgCount = employee.checkPrivilege(Constants.OMS_OM_ORG_COUNT);
			boolean canEdit=(organization.getOrgType().equals("部门") ||
					employee.checkPrivilege(Constants.OMS_OM_ORG_EDIT));
            model.addAttribute("baseHtml", organization2Service.getBaseEditHtml(organization, canEditOrgCount, canEdit));
        } else {
            model.addAttribute("baseHtml", organization2Service.getBaseDetailsHtml(organization));
        }
        model.addAttribute("faxHtml", organization2Service.getOrgFaxEditHtml(organization));
        model.addAttribute("phoneHtml", organization2Service.getPhoneEditHtml(orgPhoneList));
        model.addAttribute("org", organization);
        return "/organization/form";
    }

    /**
     * 组织排序页面跳转
     * @return
     */
    @RequestMapping(value="/sort",method= RequestMethod.GET)
    public String sort(HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_INDEX)){
            return errorNoPrivilege(model);
        }
        return "/organization/sort";
    }

    /**
     * 保存组织排序
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/saveOrgsSort",method= RequestMethod.POST)
    public @ResponseBody JsonResult saveOrgsSort(HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_INDEX)){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        String orgListJson = WebUtils.findParamStr(request, "orgList");
        List<Organization> orgList = JsonUtils.getDTOList(JsonUtils.jsonReplace(orgListJson), Organization.class);
        organization2Service.changeOrgsOrder(orgList, employee);
        return ok();
    }

    /**
     * 启用组织
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="/{id}/open",method= RequestMethod.GET)
    public @ResponseBody JsonResult openOrg(@PathVariable int id, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!organizationService.checkOrg(id,employee.getCompanyObject())){
            return fail("对不起，不允许跨部门操作");
        }
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_SWITCH)){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        Organization org = organization2Service.findById(id);
        if(org != null) {
            org.setOpenDate(DateFormatUtil.getDate(WebUtils.findParamStr(request, "openDate"), "yyyy-MM-dd"));
            org.setStatus(Organization.STATUS_NORMAL);
            if(organization2Service.openOrganization(org, employee)) {
                return ok().put("operateStr", OrganizationHelper.getOperateStr(org, employee));
            }
        }
        return fail("对不起，启用失败，不存在该组织！");
    }

    /**
     * 关闭组织
     * @param request
     * @return
     */
    @RequestMapping(value="/{id}/close",method= RequestMethod.GET)
    public @ResponseBody JsonResult closeOrg(@PathVariable int id, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!organizationService.checkOrg(id,employee.getCompanyObject())){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_SWITCH)){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        Organization org = organization2Service.findById(id);
        if(org != null) {
            org.setClosedDate(DateFormatUtil.getDate(WebUtils.findParamStr(request,"closedDate"), "yyyy-MM-dd"));
            org.setStatus(Organization.STATUS_CLOSE);
            if(organization2Service.closeOrganization(org, employee)) {
                return ok().put("operateStr", OrganizationHelper.getOperateStr(org, employee));
            }
        }
        return fail("关组时间小于开组时间,或不存在该组织");
    }
    /**
      * @since: 3.0.5 
      * @param id
      * @param request
      * @return
      * <p>
      *  获取暂停组织的模版。
      * </p>   
     */
    @RequestMapping(value="/{id}/getPauseHtml",method= RequestMethod.GET)
    public @ResponseBody JsonResult getPauseOrgHtml(@PathVariable int id,
    		HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_SWITCH)){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        Organization org = organization2Service.findViewById(id);
        if (org==null) {
			return fail("组织不存在，请确认。");
		}
        try {
        	//查询其下子组织
        	List<Organization> orgs=organization2Service.querySonOrgs(id);
        	Map<String,Object> fm=new HashMap<>();
        	fm.put("org", org);
        	if (orgs!=null && orgs.size() >0) {
        		//不能暂停，需要停用子组织。
        		return ok().put("html", freemarkerUtil.writeTemplate("org/layerStopNo.ftl",fm));
        	}
           //可以暂停组织
        	return ok().put("html", freemarkerUtil.writeTemplate("org/layerStopOk.ftl",fm));
		} catch (TemplateException | IOException e) {
			logger.error("暂停组织，加载freemarker模版失败，orgId="+id, e);
			return fail("系统异常，请及时反馈。");
		}
    }
    
    /**
     * 暂停组织
     * @update: 2014-06-03 10:55:37 添加一条区域人数限制
     * @param request
     * @return
     */
    @RequestMapping(value="/{id}/stop",method= RequestMethod.POST)
    public @ResponseBody JsonResult stopOrg(@PathVariable int id, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!organizationService.checkOrg(id,employee.getCompanyObject())){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.OMS_OM_ORG_SWITCH)){
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        Date closedDate=DateFormatUtil.getDate(WebUtils.findParamStr(request,"closedDate"), "yyyy-MM-dd");
        if (closedDate==null) {
			return fail("请选择关组日期。");
		}
        Organization org = organization2Service.findById(id);
        if (org==null) {
			return fail("组织不存在，请确认。");
		}
        if (org.getOpenDate()==null || closedDate.before(org.getOpenDate())) {
			return fail(org.getOrgName()+"的开组时间为空或者关组时间小于开组时间,请确认。");
		}
        org.setClosedDate(closedDate);
        org.setStatus(Organization.STATUS_STOP);
        //判断是否分行
        if(Organization.ORG_TYPE_FH.equals(org.getOrgType())) {
        	String newOrgName=WebUtils.findParamStr(request, "newOrgName", null);
        	if (StringUtils.isEmpty(newOrgName)) {
				return fail("请填写暂停组织的名称。");
			}
        	//检查是否重名
        	if (organization2Service.findByName(newOrgName,employee.getCompany()) !=null) {
				return fail("组织名称 "+newOrgName+"已存在，请确认。");
			}
        	if (organization2Service.stopFenHang(org, employee, newOrgName)) {
        		return ok().put("operateStr", OrganizationHelper.getOperateStr(org, employee));
			}
        }else{
        	if(organization2Service.stopOrganization(org, employee)) {
        		return ok().put("operateStr", OrganizationHelper.getOperateStr(org, employee));
        	}
        }
        return fail("组织暂停失败。");
    }

    
    
	/**
	 * 检查组织名称和上级组织名称是否正确
	 * @param request
	 * @return 验证信息，上级组织信息
	 */
	@RequestMapping(value="/checkName",method= RequestMethod.GET)
	public @ResponseBody JsonResult checkName(HttpServletRequest request) {
		int id = WebUtils.findParamInt(request, "id");
		String orgName = WebUtils.findParamStr(request, "orgName");
		String parentName = WebUtils.findParamStr(request, "parentName");
		if(!organization2Service.isExistByName(parentName,this.getSesionUser(request).getCompany())) {
			return fail("该上级组织不存在，请确认后提交。");
		} else if(!organization2Service.checkName(id, orgName,this.getSesionUser(request).getCompany())) {
			return fail("该组织名称已被使用，请输入其他名称。");
		} else {
			Organization parentOrg = organization2Service.findByName(parentName,this.getSesionUser(request).getCompany());
			return ok().put("parentOrg", parentOrg);
		}
	}

	/**
	 * 组织获取名称自动完成拦截
	 * @param request
	 * @return 返回对应的组织名称集合
	 */
	@RequestMapping(value = "/getOrgNameByPy", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String getNameByPy(HttpServletRequest request) {
        String jsoncallback = WebUtils.findParamStr(request, "jsoncallback");
        String orgName = WebUtils.findParamStr(request, "orgName");
        if(orgName != null && orgName.length() > 0) {
            orgName = TextUtil.skipHack(orgName);
        }
        String privilegeValue = WebUtils.findParamStr(request, "privilegeValue");
        String dataType = WebUtils.findParamStr(request, "dataType");
        List<com.dooioo.plus.oms.dyUser.model.Organization> OrgNameList = null;
        if(StringUtils.isBlank(dataType) || dataType.equals("NORMAL")) {
            OrgNameList = organizationService.getOrgsBySearchKey(this.getSesionUser(request), orgName, OrganizationHelper.getRange(this.getSesionUser(request), privilegeValue),new OrganizationStatus[]{OrganizationStatus.Normal},null,null);
        }else if (dataType.equalsIgnoreCase("NT")) {
        	//正常与临时组织
        	 OrgNameList = organizationService.getOrgsBySearchKey(this.getSesionUser(request), orgName,
        			 OrganizationHelper.getRange(this.getSesionUser(request), privilegeValue),
        			 new OrganizationStatus[]{OrganizationStatus.Temporary,OrganizationStatus.Normal},null,null);
		}else if(dataType.equals("ALL")) {
            OrgNameList = organizationService.getOrgsBySearchKey(this.getSesionUser(request), orgName, OrganizationHelper.getRange(this.getSesionUser(request), privilegeValue),new OrganizationStatus[]{OrganizationStatus.Disabled,OrganizationStatus.Pause,OrganizationStatus.Normal},null,null);
        }
        return jsoncallback + "(" + ok().put("OrgNameList", OrgNameList) + ")";
	}

    /**
     * 判断该组织下是否存在员工
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/countEmployee/{method}",method= RequestMethod.GET)
    public @ResponseBody JsonResult countEmployee(@PathVariable int id, @PathVariable String method) {
        if(method.equals("stop") && organization2Service.querySonOrgs(id).size()==0) {
            return ok();
        }
        if(method.equals("close") && organization2Service.querySonOrgs(id, new int[]{Organization.STATUS_NORMAL, Organization.STATUS_STOP, Organization.STATUS_TEMP}).size()==0 && employeePositionService.countEmployeeByOrgId(id) == 0) {
            return ok();
        }
//        if(employeePositionService.countEmployeeByOrgId(id) == 0) {
//            if(method.equals("stop") && organization2Service.querySonOrgs(id).size()==0) {
//                return ok();
//            } else if(method.equals("close") && organization2Service.querySonOrgs(id, new int[]{Organization.STATUS_NORMAL, Organization.STATUS_STOP, Organization.STATUS_TEMP}).size()==0) {
//                return ok();
//            }
//        }
        return fail();
    }

    /**
     * 获得下级组织
     * @param id
     * @return
     */
    @RequestMapping(value="/querySonOrgs/{id}",method= RequestMethod.GET)
    public @ResponseBody JsonResult querySonOrgs(@PathVariable int id) {
        List<Organization> orgList = organization2Service.querySonOrgs(id);
        if(orgList != null && orgList.size() > 0) {
            return ok().put("orgList", orgList);
        }
        return fail();
    }

    /**
     * 判断组织是否存在
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/check",method= RequestMethod.GET)
    public @ResponseBody JsonResult check(@PathVariable int id) {
        if(organization2Service.findById(id) != null) {
            return ok();
        }
        return fail();
    }

    @RequestMapping(value="/{id}/getSon",method= RequestMethod.GET)
    public @ResponseBody JsonResult getSon(@PathVariable int id) {
        return ok().put("orgs", organization2Service.querySonOrgs(id));
    }

    @RequestMapping(value="/checkName/{orgName}",method= RequestMethod.GET)
    public @ResponseBody JsonResult checkName(@PathVariable String orgName) {
        if(organization2Service.isExistName(orgName)) {
            return ok();
        }
        return fail();
    }

    /**
     * 请求分行所在区域是否满员
     * @param orgId
     * @return
     */
    @RequestMapping(value="/getEmployeeCount/{orgId}",method= RequestMethod.GET)
    public @ResponseBody JsonResult getEmployeeCount(@PathVariable int orgId) {
        try {
            return employeeBaseInforService.isMoreThanNormal(orgId) ? fail("本组织所在区域已经满员，无法新增员工") : ok();
        } catch (Exception e) {
            LOG.error("判断该区域是否满员失败！", e);
            return fail("判断该区域是否满员失败！" + e);
        }
    }

    /**
     * 修改编制
     * 现在不考虑现有多少人
     * @param organizationEmployeeCount
     * @param request
     * @return
     */
    @RequestMapping(value="/editEmployeeCount",method= RequestMethod.POST)
    public String editEmployeeCount(OrganizationEmployeeCount organizationEmployeeCount, Model model , HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.EMPLOYEE_COUNT_EDIT)){
            return errorNoPrivilege(model);
        }
        if(organizationEmployeeCount.getOrgId() == 0 && organizationEmployeeCount.getMaxCount() == null) {
            return error(model, "修改编辑失败！");
        }
        //如果已经存在则更新
        if(organizationEmployeeCountService.isExistByOrgId(organizationEmployeeCount.getOrgId())) {
            //取消了分行现有人数的判断
//            if(employeeBaseInforService.countEmployeeByOrgId(organizationEmployeeCount.getOrgId()) > organizationEmployeeCount.getMaxCount()) {
//                return fail("分行人数大于你要的编制，无法修改编制！");
//            }
            organizationEmployeeCount.setUpdator(employee.getUserCode());
            if(organizationEmployeeCountService.update(organizationEmployeeCount)) {
                return "redirect:/organization/" + organizationEmployeeCount.getOrgId() + "/details";
            }
        } else {
            organizationEmployeeCount.setCreator(employee.getUserCode());
            //设置类型为手动添加
            organizationEmployeeCount.setLimitType(1);
            if(organizationEmployeeCountService.insert(organizationEmployeeCount)) {
                return "redirect:/organization/" + organizationEmployeeCount.getOrgId() + "/details";
            }
        }
        return error(model, "修改编辑失败！");
    }

    /**
     * 更新组织组数
     * @param orgId
     * @param orgMaxCount
     * @param request
     * @return
     */
    @RequestMapping(value="/updateOrgCount/{orgId}/{orgMaxCount}",method= RequestMethod.POST)
    public @ResponseBody JsonResult updateOrgCount(@PathVariable int orgId,@PathVariable int orgMaxCount, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_ORG_COUNT)){
            return fail("没有权限！");
        }
        if(organizationOrgCountService.update(orgId, orgMaxCount, employee.getUserCode())) {
            return ok();
        }
        return fail("修改组织组数失败！");
    }

}

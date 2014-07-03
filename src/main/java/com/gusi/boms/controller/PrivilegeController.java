package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.plus.oms.dyUser.service.SerialService;
import com.dooioo.web.common.WebUtils;
import com.dooioo.web.helper.JsonResult;
import com.gusi.boms.model.*;
import com.gusi.boms.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * Create: Jail (2013-05-27 17:16)
 * 权限控制器
 */
@Controller
@RequestMapping(value = "/privilege/*")
public class PrivilegeController extends BomsBaseController {

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RolePositionService rolePositionService;
	@Autowired
	private RoleOrganizationService roleOrganizationService;
	@Autowired
	private RoleEmployeeService roleEmployeeService;
	@Autowired
	private ApplicationPrivilegeService applicationPrivilegeService;
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	@Autowired
	private ApplicationEmployeeAccessService applicationEmployeeAccessService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private SerialService serialService;
    @Autowired
    private ApplicationLogService applicationLogService;
    @Autowired
    private RoleLogService roleLogService;
    @Autowired
    private ApplicationPrivilegeLOGService applicationPrivilegeLOGService;

	@RequestMapping("/systemSet")
	protected String systemSet(HttpServletRequest request,Model model){
		Employee employee = this.getSesionUser(request);
		if(!isSuperAdmin(employee))
			return errorNoPrivilege(model);
		int userCode = WebUtils.findParamInt(request, "userCode");
        if (!employeeService.checkEmployee(userCode,employee.getCompanyObject()))
            return error(model,"对不起，不存在此员工信息，请联系管理员");
		List<Application> appList =	applicationService.getAllowApps(employee.getUserCode());
		List<ApplicationEmployeeAccess> accList = applicationEmployeeAccessService.getPrivilegesByAppId(userCode);
		for(ApplicationEmployeeAccess acc : accList){
			for(Application app : appList){
				if(acc.getAppId() == app.getId()){
					app.setChecked(true);
					break;
				}
			}
		}
		model.addAttribute("appList", appList);
		model.addAttribute("employee", employeeService.getEmployee(userCode));
		return "/privilege/systemSet";
	}

	/**
	 * 权限设置页面
	 */
	@RequestMapping(value = "/list")
	protected String list(HttpServletRequest request,Model model){
		Employee employee = this.getSesionUser(request);
		if(!this.isSuperAdmin(employee)&&!employee.checkPrivilege(Constants.PMS_BASE))
			return errorNoPrivilege(model);
		List<Application> appList =	applicationService.getAllowApps(employee.getUserCode());
		if(employee.getUserCode() != Configuration.getInstance().getSuperAdminUserCode() && appList.size() == 0)
			return errorNoPrivilege(model);
		model.addAttribute("appList", appList);
		return "/privilege/list";
	}

	/**
	 * 员工权限设置页面
	 */
	@RequestMapping("/userSet")
	protected String userSet(HttpServletRequest request,Model model){
		return setDefault(request,model,"user");
	}

	/**
	 * 员工权限设置页面
	 */
	@RequestMapping(value = "/userSet" ,method = RequestMethod.POST)
	protected String userSetPost(HttpServletRequest request,Model model){
		return setDefault(request,model,"user");
	}

	/**
	 * 部门权限设置页面
	 */
	@RequestMapping("/orgSet")
	protected String orgSet(HttpServletRequest request,Model model){
		model.addAttribute("ref", WebUtils.findParamStr(request, "ref"));
		return setDefault(request,model,"org");
	}

	/**
	 * 部门权限设置页面
	 */
	@RequestMapping(value = "/orgSet",method = RequestMethod.POST)
	protected String orgSetPost(HttpServletRequest request,Model model){
		return setDefault(request,model,"org");
	}

	/**
	 * 岗位权限设置页面
	 */
	@RequestMapping("/positionSet")
	protected String positionSet(HttpServletRequest request,Model model){
		return setDefault(request,model,"position");
	}

	/**
	 * 权限设置页面公共部分
	 */
	private String setDefault(HttpServletRequest request,Model model,String type ){
		Employee employee = this.getSesionUser(request);
		if(!this.isSuperAdmin(employee)&&!employee.checkPrivilege(Constants.PMS_BASE))
			return errorNoPrivilege(model);
        List<Application> appList =	applicationService.getAllowApps(employee.getUserCode());
        int key = WebUtils.findParamInt(request, "key");
        model.addAttribute("appList", appList);
        model.addAttribute("type", type);
        model.addAttribute("key", key);
        String label = null;
        switch(type){
        case "org" :
            if(!organizationService.checkOrg(key,employee.getCompanyObject())){
                return error(model,"对不起，不存在此组织信息，请联系管理员");
            }
            label = organizationService.getOrgByOrgId(key).getOrgName();
            break;
        case "user" :
        	Employee emp = employeeService.getEmployee(key);
        	if(emp==null)
				return error(model,"申请离职或已离职的员工不能配置权限");
        	else if (!employeeService.checkEmployee(key,employee.getCompanyObject()))
                 return error(model,"对不起，不存在此员工信息，请联系管理员");
            else
        		label = emp.getUserName() + " " + emp.getOrgName();
            break;
        case "position":
        	label = serialService.getPositionById(key).getPositionName();
        	break;
        }
        model.addAttribute("label", label);
		return "privilege/set";
	}


	/**
	 * 获得指定系统的角色
	 */
	@RequestMapping(value = "/getRolesByAppId" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult getRolesByAppId(HttpServletRequest request){
		int appId = WebUtils.findParamInt(request, "appId");
		List<Role> allRoleList = roleService.getRolesByAppId(appId);
		return ok("ok","roleList",allRoleList);
	}

	/**
	 * 获得指定系统的权限
	 */
	@RequestMapping(value = "/getPrivilegesByAppId" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult getPrivilegesByAppId(HttpServletRequest request){
		int appId = WebUtils.findParamInt(request, "appId");
		List<ApplicationPrivilege> priList = applicationPrivilegeService.getPrivilegesByAppId(appId);
		return ok("ok","priList",priList);
	}

	/**
	 * 获得指定系统的权限
	 */
	@RequestMapping(value = "/getPrivilegesByRoleId" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult getPrivilegesByRoleId(HttpServletRequest request){
		int appId = WebUtils.findParamInt(request, "appId");
		int roleId = WebUtils.findParamInt(request, "roleId");
		List<ApplicationPrivilege> priList = applicationPrivilegeService.getPrivilegesByAppId(appId);
		List<RolePrivilege> rolePriList = rolePrivilegeService.getRolePrivilegesByRoleId(roleId);

		for(RolePrivilege rp : rolePriList){
			for(ApplicationPrivilege ap : priList){
				if(rp.getPrivilegeId() == ap.getId())
				{
					ap.setChecked(true);
					ap.setDataPrivilege(rp.getDataPrivilege());
				}
			}
		}
		return ok("ok","priList",priList);
	}

	/**
	 * 获得已经设置的角色
	 */
	@RequestMapping(value = "/getRoles" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult getRoles(HttpServletRequest request){
		String type = WebUtils.findParamStr(request, "type");
		int appId = WebUtils.findParamInt(request, "appId");
		int key = WebUtils.findParamInt(request, "key");

		List<Role> allRoleList = roleService.getRolesByAppId(appId);
		List<Role> userRoleList = null;
		List<Role> orgRoleList = null;
		List<Role> positionRoleList = null;
        switch (type) {
            case "user":
                Employee employee = employeeService.getEmployee(key);
                userRoleList = roleService.getRolesByUserCode(key, appId);
                orgRoleList = roleService.getOrgRolesByUserCode(employee.getUserCode(), appId);
                positionRoleList = roleService.getPositionRolesByPositionId(employee.getUserCode(), appId);
                break;
            case "org":
                orgRoleList = roleService.getRolesByOrgId(key, appId);
                break;
            case "position":
                positionRoleList = roleService.getRolesByPositionId(key, appId);
                break;
        }

		if(userRoleList!=null){
			for(Role role : userRoleList){
				for(Role r1 : allRoleList){
					if(role.getId() == r1.getId()){
						r1.setChecked(true);
						if(!"user".equals(type))
							r1.setCanEdit(false);
						break;
					}
				}
			}
		}

		if(orgRoleList!=null){
			for(Role role : orgRoleList){
				for(Role r1 : allRoleList){
					if(role.getId() == r1.getId()){
						r1.setChecked(true);
						if(!"org".equals(type))
							r1.setCanEdit(false);
						break;
					}
				}
			}
		}

		if(positionRoleList!=null){
			for(Role role : positionRoleList){
				for(Role r1 : allRoleList){
					if(role.getId() == r1.getId()){
						r1.setChecked(true);
						if(!"position".equals(type))
							r1.setCanEdit(false);
						break;
					}
				}
			}
		}
		return ok("ok","roleList",allRoleList);
	}

	/**
	 * 设置角色
	 */
	@RequestMapping(value = "/setRoles" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult setRoles(HttpServletRequest request){
		Employee employee = this.getSesionUser(request);
		if(!this.isSuperAdmin(employee)&&!employee.checkPrivilege(Constants.PMS_BASE))
			return fail("你没有权限设置权限");
		switch(WebUtils.findParamStr(request, "type")){
		case "user": return setUserRoles(request);
		case "org": return setOrgRoles(request);
		case "position": return setPositionRoles(request);
		default:return fail("设置权限参数错误，请重试或联系管理员");
		}
	}

	/**
	 * 设置部门角色
	 */
	@RequestMapping(value = "/setOrgRoles" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult setOrgRoles(HttpServletRequest request){
		if(roleOrganizationService.insertRoleOrganization(this.getSesionUser(request).getUserCode(),WebUtils.findParamInt(request, "key"), WebUtils.findParamInt(request, "appId"), WebUtils.findParamStr(request, "roleIds")))
			return ok();
		else
			return fail("设置部门权限失败，请重试或联系管理员");
	}

	/**
	 * 设置员工角色
	 */
	@RequestMapping(value = "/setUserRoles" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult setUserRoles(HttpServletRequest request){
		if(roleEmployeeService.insertRoleEmployee(this.getSesionUser(request).getUserCode(),WebUtils.findParamInt(request, "key"), WebUtils.findParamInt(request, "appId"), WebUtils.findParamStr(request, "roleIds")))
			return ok();
		else
			return fail("设置员工权限失败，请重试或联系管理员");
	}

	/**
	 * 设置岗位角色
	 */
	@RequestMapping(value = "/setPositionRoles" , method = RequestMethod.POST )
	protected @ResponseBody JsonResult setPositionRoles(HttpServletRequest request){
		if(rolePositionService.insertRolePosition(this.getSesionUser(request).getUserCode(),WebUtils.findParamInt(request, "key"), WebUtils.findParamInt(request, "appId"), WebUtils.findParamStr(request, "roleIds")))
			return ok();
		else
			return fail("设置岗位权限失败，请重试或联系管理员");
	}

	/**
	 * 添加系统
	 */
	@RequestMapping(value = "/addApps" , method = RequestMethod.POST)
	protected @ResponseBody JsonResult addApps(Application app, HttpServletRequest request){
		Employee employee = this.getSesionUser(request);
		if(!this.isSuperAdmin(employee))
			return fail("你没有添加系统的权限");
        int appId = applicationService.insertAndReturnId(app);
		if(appId > 0){
            ApplicationLog applicationLog = new ApplicationLog();
            applicationLog.setCreator(employee.getUserCode());
            applicationLog.setOperateType(OperateType.insert.toString());
            applicationLog.setAppId(appId);
            applicationLogService.insert(applicationLog);
			return ok("ok","appList",applicationService.getAllowApps(employee.getUserCode()));
		}else
		{
			return fail("添加系统失败，请重试或联系管理员");
		}
	}


	/**
	 * 添加角色
	 */
	@RequestMapping(value = "/addRoles" , method = RequestMethod.POST)
	protected @ResponseBody JsonResult addRoles(Role role, HttpServletRequest request){
		Employee employee = this.getSesionUser(request);
		List<Application> appList = applicationService.getAllowApps(employee.getUserCode());
		if(!this.isSuperAdmin(employee))
		{
			if(!(employee.checkPrivilege(Constants.PMS_BASE)&&checkAppPrivilege(appList,WebUtils.findParamInt(request, "appId"))))
				return fail("你没有添加角色的权限");
		}
        role.setCreator(employee.getUserCode());
        int roleId = roleService.insertAndReturnId(role);
		if(roleId > 0){
            RoleLog roleLog = new RoleLog();
            roleLog.setRoleId(roleId);
            roleLog.setOperateType(OperateType.insert.toString());
            roleLogService.insert(roleLog);
			return ok();
		}else
		{
			return fail("添加角色失败，请重试或联系管理员");
		}
	}

	/**
	 * 添加权限
	 */
	@RequestMapping(value = "/addPrivileges" , method = RequestMethod.POST)
	protected @ResponseBody JsonResult addPrivileges(ApplicationPrivilege pri, HttpServletRequest request){
		Employee employee = this.getSesionUser(request);
		List<Application> appList = applicationService.getAllowApps(employee.getUserCode());
		if(!this.isSuperAdmin(employee))
		{
			if(!(employee.checkPrivilege(Constants.PMS_BASE)&&checkAppPrivilege(appList,WebUtils.findParamInt(request, "appId"))))
					return fail("你没有权限设置权限");
		}
        if(applicationPrivilegeService.checkPrivilegeUrlExist(pri.getPrivilegeUrl())){
            return fail("权限url与其他系统冲突，为了避免以后出现权限混乱，请更换一个权限url  ");
        }
        int privilegeId = applicationPrivilegeService.insertAndReturnId(pri);
		if(privilegeId > 0){
            ApplicationPrivilegeLOG applicationPrivilegeLOG = new ApplicationPrivilegeLOG();
            applicationPrivilegeLOG.setPrivilegeId(privilegeId);
            applicationPrivilegeLOG.setCreator(employee.getUserCode());
            applicationPrivilegeLOG.setOperateType(OperateType.insert.toString());
            applicationPrivilegeLOGService.insert(applicationPrivilegeLOG);
			return ok();
		}else
		{
			return fail("添加权限失败，请重试或联系管理员");
		}
	}

	/**
	 *添加角色与权限的关系
	 */
	@RequestMapping(value = "/{roleId}/addRolePrivileges" , method = RequestMethod.POST)
	protected @ResponseBody JsonResult addRolePrivileges(@PathVariable int roleId,HttpServletRequest request){
		Employee employee = this.getSesionUser(request);
		if(!this.isSuperAdmin(employee)&&!employee.checkPrivilege(Constants.PMS_BASE))
			return fail("你没有权限设置权限");
		String json = WebUtils.findParamStr(request, "rpList");
		List<RolePrivilege> rpList = new ArrayList<>();
		for(String r : json.split(";")){
			if(r.length() > 0)
				rpList.add((RolePrivilege)JSONObject.toBean(JSONObject.fromObject(r), RolePrivilege.class));
		}
		if(rolePrivilegeService.insertRolePrivileges(this.getSesionUser(request).getUserCode(),rpList,roleId)){
			return ok();
		}
		else
			return fail("保存失败，请重试或联系管理员");
	}

	@RequestMapping(value = "/setApplicationEmployeeAccess",method = RequestMethod.POST)
	protected @ResponseBody JsonResult addApplicationEmployeeAccess(HttpServletRequest request){
		if(!this.isSuperAdmin(this.getSesionUser(request)))
			return fail("你没有权限设置！");
		if(applicationEmployeeAccessService.insertApplicationEmployeeAccess(WebUtils.findParamInt(request, "userCode"), WebUtils.findParamStr(request, "appIds")))
			return ok();
		else
			return fail("设置系统权限失败，请重试或联系管理员");
	}

	/**
	 * 检查是否有处理当前系统的权限
	 * @param appList 允许处理的系统列表
	 * @param appId 检查的系统ID
	 * @return boolean
	 */
	private boolean checkAppPrivilege(List<Application> appList,int appId){
		for(Application app : appList){
			if(app.getId() == appId)
				return true;
		}
		return false;
	}


}

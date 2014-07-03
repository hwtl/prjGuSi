package com.gusi.boms.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeRewardPunishment;
import com.gusi.boms.model.RewardParameter;
import com.gusi.boms.service.EmployeeRewardPunishmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.gusi.boms.service.RewardParameterService;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.web.helper.JsonResult;
import com.dooioo.webplus.helper.TokenHelper;

import freemarker.template.TemplateException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-19 15:31)
 * @Description: 员工奖惩控制器
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/reward/*")
public class RewardPunishmentController extends BomsBaseController {

	private static final Logger logger=Logger.getLogger(RewardPunishmentController.class);
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRewardPunishmentService rewardService;
    @Autowired
    private RewardParameterService parameterService;
    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;

    /**
     * 列表页
     * @param userCode
     * @param request
     * @param model
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/{userCode}", method = RequestMethod.GET)
    public String list(@PathVariable int userCode, HttpServletRequest request, Model model,  @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        Employee employee = getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.REWARD_DETAIL)){
            return errorNoPrivilege(model);
        }
        if(!employeeService.checkEmployee(userCode, employee.getCompanyObject())) {
            return error(model,"对不起，无此员工信息，请联系管理员");
        }
        model.addAttribute("emp", employeeService.getEmployee(userCode, EmployeeStatus.All));
        model.addAttribute("orgLongName", employeeBaseInforService.findorgLongNameByUserCode(userCode));
        model.addAttribute("userCode", userCode);
        model.addAttribute("paginate", rewardService.queryPaginate(pageNo, userCode));
        return "/reward/list";
    }
   
    /**
      * @since: 2.7.1 
      * @param optionCode
      * @return
      * <p>
      *  根据奖惩类型查询通道以及奖励结果
      * </p>   
     */
    @RequestMapping(value="/queryChannelsWithResults/{optionCode}")
    @ResponseBody
    public JsonResult queryChannelsAndResults(@PathVariable(value="optionCode")String optionCode){
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("channelList",parameterService.
    			findByTypeKeyAndParentCode(RewardParameter.CHANNER_TYPE_KEY, optionCode));
    	params.put("resultList", parameterService.
    			findByTypeKeyAndParentCode(RewardParameter.RESULT_TYPE_KEY, optionCode));
    	return ok().put("data",params);
    }
   
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public @ResponseBody JsonResult add(HttpServletRequest request) {
        return ok().put("type", parameterService.findByTypeKey(RewardParameter.REWARD_TYPE))
                .put("channel", parameterService.findByTypeKey(RewardParameter.REWARD_CHANNEL))
                .put("punishmentResult", parameterService.findByTypeKey(RewardParameter.REWARD_RESULT_PUNISHMENT))
                .put("rewardResult", parameterService.findByTypeKey(RewardParameter.REWARD_RESULT_REWARD));
    }
    
    
    /**
      * @return
      * <p>
      * 奖惩新增或者删除
      * </p>   
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String rewardpunishAddOrUpdate(EmployeeRewardPunishment reward, HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.REWARD_ADD)){
            return errorNoPrivilege(model);
        }
        if (reward.getEffectiveTime().before(new Date())) {
        	reward.setStatus(1);
        }
        if(reward.getId() > 0) {
            reward.setUpdator(employee.getUserCode());
            rewardService.update(reward);
        } else {
            reward.setCreator(employee.getUserCode());
            reward.setId(rewardService.insertAndReturnId(reward));
        }
        //发送工资扣款消息。
        rewardService.sendSalaryMQMessage(reward);
        return "redirect:/reward/" + reward.getUserCode();
    }

    @RequestMapping(value = "/getRules/{typeKey}", method = RequestMethod.GET)
    public @ResponseBody JsonResult getRules(HttpServletRequest request, @PathVariable String typeKey) {
        return ok().put("rules", parameterService.findByTypeKey(typeKey));
    }
    /**
     * @since: 2.7.1 
     * @param request
     * @param id
     * @return
     * <p>
     *  编辑奖惩记录
     * </p>   
    */
   @RequestMapping(value = "/add/{userCode}", method = RequestMethod.GET)
   public @ResponseBody JsonResult getAddHtml(HttpServletRequest request, @PathVariable int userCode) {
       Employee emp=employeeService.getEmployee(userCode, EmployeeStatus.All);
       if (emp==null) {
		return fail("工号为"+userCode+"的员工不存在。");
       }
       Map<String,Object> params=new HashMap<String, Object>();
       params.put("rewardTypeList", parameterService.findByTypeKey(RewardParameter.REWARD_TYPE));
	   params.put("channelList",parameterService.
	   			findByTypeKeyAndParentCode(RewardParameter.CHANNER_TYPE_KEY,RewardParameter.REWARD_TYPE_PUNISH_OPTIONCODE));
	   params.put("resultList", parameterService.
	   			findByTypeKeyAndParentCode(RewardParameter.RESULT_TYPE_KEY,RewardParameter.REWARD_TYPE_PUNISH_OPTIONCODE));
	   params.put("emp", emp);
   	   params.put("ruleList", parameterService.findByTypeKey(RewardParameter.RULE_TYPE_KEY));
   	   params.put("punishOptionCode", RewardParameter.REWARD_TYPE_PUNISH_OPTIONCODE);
   	   params.put("token",TokenHelper.addToken(request));
       try {
			return ok().put("html",freemarkerUtil.writeTemplate("/rewardpunish/addReward.ftl", params));
		} catch (TemplateException | IOException e) {
		    logger.error("获取模版数据出错，新增奖惩记录："+userCode, e);
			return fail(e.getMessage());
		}
   }
    /**
      * @since: 2.7.1 
      * @param request
      * @param id
      * @return
      * <p>
      *  编辑奖惩记录
      * </p>   
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public @ResponseBody JsonResult edit(HttpServletRequest request, @PathVariable int id) {
        EmployeeRewardPunishment reward =  rewardService.findViewForEdit(id);
        if(reward == null) {
            return fail("只能修改未生效的记录。");
        }
        Map<String,Object> params=new HashMap<String, Object>();
    	params.put("channelList",parameterService.
    			findByTypeKeyAndParentCode(RewardParameter.CHANNER_TYPE_KEY, reward.getRewardtype()));
    	params.put("resultList", parameterService.
    			findByTypeKeyAndParentCode(RewardParameter.RESULT_TYPE_KEY, reward.getRewardtype()));
    	params.put("rewardTypeList", parameterService.findByTypeKey(RewardParameter.REWARD_TYPE));
    	params.put("reward", reward);
    	params.put("ruleList", parameterService.findByTypeKey(RewardParameter.RULE_TYPE_KEY));
    	params.put("failTime", reward.getFailTime());
    	params.put("token",TokenHelper.addToken(request));
        try {
			return ok().put("html",freemarkerUtil.writeTemplate("/rewardpunish/editReward.ftl", params));
		} catch (TemplateException | IOException e) {
		    logger.error("获取模版数据出错，编辑奖惩记录："+id, e);
			return fail(e.getMessage());
		}
    }

//    @RequestMapping(value = "/getList/{userCode}/{pageNo}", method = RequestMethod.GET)
//    public @ResponseBody JsonResult getList(HttpServletRequest request, @PathVariable int userCode, @PathVariable int pageNo) {
//        return ok().put("paginate", rewardService.queryPaginate(pageNo, userCode));
//    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody JsonResult delete(HttpServletRequest request, @PathVariable int id) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !PrivilegeHelper.checkPrivilege(employee.getPrivilegeMap(), Constants.REWARD_ADD)){
            return fail("没有权限，请联系管理员。");
        }
        if(id > 0 && rewardService.deleteById(id)) {
            return ok("删除成功。");
        } else {
            return fail("删除失败。");
        }
    }
}

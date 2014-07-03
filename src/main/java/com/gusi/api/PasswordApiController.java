package com.gusi.api;

import com.gusi.boms.service.EmployeePasswordInfoService;
import com.dooioo.web.controller.BaseController;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 密码相关api
 * @Author: fdj
 * @Since: 2014-01-22 10:51
 */
@Controller
@RequestMapping("/oms/api/password/*")
public class PasswordApiController extends BaseController {

    @Autowired
    private EmployeePasswordInfoService employeePasswordInfoService;

    /**
     * 根据工号获取需要密码更改提醒的员工密码信息
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/check/{userCode}", method = RequestMethod.GET)
    @ResponseBody
    public String checkPassword(@PathVariable int userCode, HttpServletRequest request) {
        String result = fail().toString();
        //判决该员工是否有过修改密码记录
        if(!employeePasswordInfoService.isExistsRecord(userCode)) {
            result = ok().toString();
        }
        //密码是否即将过期
        else if(employeePasswordInfoService.isExistsOutOfDate(userCode)) {
            result = ok().toString();
        }
        //是否跨域
        String jsonCallback = request.getParameter("jsonCallback");
        if(StringUtils.isNotBlank(jsonCallback)) {
            return jsonCallback + "(" + result + ")";
        }
        return result;
    }

    /**
     * 根据工号获取修改密码记录
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/list/{userCode}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(@PathVariable int userCode) {
        return ok().put("empPwdInfoList", employeePasswordInfoService.queryForList(userCode));
    }

    /**
     * 根据工号查找该员工最近一条密码操作记录
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/recent/{userCode}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult recent(@PathVariable int userCode) {
        return ok().put("empPwdInfo", employeePasswordInfoService.findRecent(userCode));
    }

}

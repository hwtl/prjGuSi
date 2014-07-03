package com.gusi.api;

import com.gusi.boms.helper.AdHelper;
import com.dooioo.plus.other.service.DyADService;
import com.dooioo.web.controller.BaseController;
import com.dooioo.web.helper.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: AdApiController
 * @Author: fdj
 * @Since: 2014-02-07 15:04
 */
@Controller
@RequestMapping("/oms/api/ad")
public class AdApiController extends BaseController {

    @Autowired
    private AdHelper adHelper;
    @Autowired
    private DyADService dyADService;

    /**
     * 同步OU
     * @param id
     * @return
     */
    @RequestMapping(value = "/syncOU/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult syncOU(@PathVariable int id) {
        return adHelper.syncOU(id) ? ok() : fail();
    }


    /**
     * 同步AD
     * @param userCode 员工工号
     * @return
     */
    @RequestMapping(value = "/syncAD/{userCode}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult syncAD(@PathVariable int userCode) {
        if(adHelper.syncAD(userCode)) {
            return ok();
        }
        return fail();
    }

    /**
     * 同步AD
     * @param company 公司
     * @return
     */
    @RequestMapping(value = "/syncCompany/{company}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult syncCompany(@PathVariable String company) {
        switch (company) {
            case "dooioo":
            case "德佑":
                this.syncDy();
                break;
            case "iderong":
            case "德融":
                this.syncDr();
                break;
            default:
                return fail("参数有问题，识别参数：德佑、德融、dooioo、iderong");
        }
        return ok("正在同步中。。。大约半小时。。。");
    }

    @Async
    public boolean syncDr() {
        return dyADService.syncDrOU(true).isSuss();
    }

    @Async
    public boolean syncDy() {
        return dyADService.syncDyOU(true).isSuss();
    }
}

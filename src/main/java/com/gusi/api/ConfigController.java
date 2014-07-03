package com.gusi.api;

import com.gusi.boms.common.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dooioo.web.controller.BaseController;
import com.dooioo.web.helper.JsonResult;

/**
 * @author "liuhui" 
 * @since 1.0.0
 * @createAt 2014-3-14 下午1:05:44
 * <p>
 *  刷新资源配置
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@RequestMapping(value="/config")
@Controller
public class ConfigController extends BaseController{
    @RequestMapping(value="/resource/newVersion")
    @ResponseBody
	public JsonResult refreshResourceVersion(){
    	Configuration.getInstance().setCurrentVersion(System.currentTimeMillis());
    	return ok(String.valueOf(Configuration.getInstance().getCurrentVersion()));	   
   }
}



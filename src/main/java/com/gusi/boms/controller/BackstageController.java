package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-23 19:47)
 * Description: To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/")
public class BackstageController extends BomsBaseController{

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String workspaceDefault(){
        return "index";
    }

    @RequestMapping(value = "/backstage/hrs",method = RequestMethod.GET)
    public String workspace(){
        return "index";
    }
}

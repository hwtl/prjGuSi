package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.model.Select;
import com.gusi.boms.service.SelectService;
import com.dooioo.web.helper.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-28 16:37)
 * Description: To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/titleLevel")
public class TitleLevelController extends BomsBaseController {

    @Autowired
    SelectService selectService;
    /**
     * 通过职系id获得职等拦截
     * @param serialId 职系名称
     * @return json
     */
    @RequestMapping(value="/queryTitle/{serialId}",method= RequestMethod.GET)
    public @ResponseBody
    JsonResult searchTitle(@PathVariable int serialId) {
        List<Select> titleList = selectService.queryTitlesBySerialId(serialId);
        return ok().put("selectList", titleList);
    }

    @RequestMapping(value = "/queryLevel/{titleId}",method = RequestMethod.GET)
    public @ResponseBody JsonResult getLevel(@PathVariable int titleId){
        List<Select> titleLevels = selectService.queryTitleLevelByTitleId(titleId);
        return ok().put("selectList",titleLevels);
    }
}

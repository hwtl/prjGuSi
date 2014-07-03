package com.gusi.empTransfer.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.model.Select;
import com.gusi.empTransfer.helper.STLHelper;
import com.dooioo.web.helper.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-02-20 15:53
 * @Description: 职系职等职级接口
 */
@Controller
@RequestMapping("/transfer/stl/**")
public class STLController extends BomsBaseController {

    @Autowired
    private STLHelper stlHelper;

    /**
     * 根据转调转调获取相应职系
     * @param transferType
     * @return
     */
    @RequestMapping(value = "/serial", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult empSearch(@RequestParam String transferType) {
        try {
            List<Select> selectList = stlHelper.initSerial(transferType);
            return ok().put("selectList", selectList);
        } catch (Exception e) {
            return fail("获取职系失败！");
        }
    }

}

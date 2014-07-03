package com.gusi.api;

import com.gusi.activemq.helper.EmployeeSenderHelper;
import com.dooioo.dymq.model.EmployeeMessage;
import com.dooioo.web.controller.BaseController;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: fdj
 * @Create: 13-12-19 下午3:48
 * @Description: 发送mq消息的接口
 */
@Controller
@RequestMapping("/oms/api/mq/*")
public class MqApiController extends BaseController {

    private final static Log LOG = LogFactory.getLog(MqApiController.class);

    @Autowired
    private EmployeeSenderHelper employeeSenderHelper;

    @RequestMapping(value = "/sendEmployeeBianJi", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendEmployeeBianJi(EmployeeMessage employeeMessage) {
        try {
            employeeSenderHelper.sendEmployeeBianJi(employeeMessage);
            return ok();
        } catch (Exception e) {
            LOG.error("发送编辑mq消失失败！", e);
            return fail();
        }

    }


    /**
      * @since: 3.1.2 
      * @param employeeMessage
      * @return
      * <p>
      *  用户账户暂停
      * </p>   
     */
    @RequestMapping(value = "/sendEmployeePause", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendEmployeePause(EmployeeMessage employeeMessage) {
        try {
            employeeSenderHelper.sendEmployeeBianJi(employeeMessage);
            return ok();
        } catch (Exception e) {
        	 LOG.error("员工帐号屏蔽："+employeeMessage, e);
            return fail();
        }

    }
    
    /**
     * @since: 3.1.2 
     * @param employeeMessage
     * @return
     * <p>
     *  用户账户暂停
     * </p>   
    */
   @RequestMapping(value = "/sendEmployeeActive", method = RequestMethod.POST)
   @ResponseBody
   public JsonResult sendEmployeeActive(EmployeeMessage employeeMessage) {
       try {
           employeeSenderHelper.sendEmployeeBianJi(employeeMessage);
           return ok();
       } catch (Exception e) {
           LOG.error("员工帐号启用："+employeeMessage, e);
           return fail();
       }

   }
    @RequestMapping(value = "/sendEmployeeYiDong", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendEmployeeYiDong(EmployeeMessage employeeMessage) {
        try {
            employeeSenderHelper.sendEmployeeYiDong(employeeMessage);
            return ok();
        } catch (Exception e) {
            LOG.error("发送异动mq消失失败！", e);
            return fail();
        }

    }

}

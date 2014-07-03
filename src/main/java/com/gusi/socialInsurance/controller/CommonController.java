package com.gusi.socialInsurance.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.service.LevelInfoService;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.gusi.socialInsurance.service.BaseTypeService;
import com.gusi.socialInsurance.service.SIBatchInfoService;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-06-11 17:54
 * @Description: 社保通用的url拦截处理
 */
@Controller
@RequestMapping("/si/common/*")
public class CommonController extends BomsBaseController {

    private static final Log LOG = LogFactory.getLog(CommonController.class);

    @Autowired
    private BaseTypeService baseTypeService;
    @Autowired
    private LevelInfoService levelInfoService;
    @Autowired
    private SIBatchInfoService siBatchInfoService;

    /**
     * 社保主入口
     * @since: 2014-06-16 17:28:27
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "/si/index";
    }

    /**
     * 社保配合前端路由拦截
     * @since: 2014-06-16 17:28:36
     * @return
     */
    @RequestMapping(value = "/index/*")
    public String rout() {
        return "/si/index";
    }

    /**
     * 根据前台请求的参数
     * 返回对应的通用数据集
     * @since: 2014-06-11 18:07:56
     * @param categoryType
     * @return
     */
    @RequestMapping(value = "/getbaseinfo")
    @ResponseBody
    public JsonResult getBaseInfo(@RequestParam(defaultValue = "") String categoryType) {
        try {
            String[] categoryTypeArr = categoryType.split(",");
            Map<String, Object> data = new HashMap<>();
            for (String type : categoryTypeArr) {
                switch (type.trim()) {
                    //1-缴纳地   paymentLocationList
                    case "1" :
                        data.put("paymentLocationList", baseTypeService.queryPaymentLocationList());
                        break;
                    //2-社保卡   socialCardList
                    case "2" :
                        data.put("socialCardList", Constants.SIBASE_TYPE_LIST);
                        break;
                    //3-异地申请表   applyFormList
                    case "3" :
                        data.put("applyFormList", Constants.SIBASE_TYPE_LIST);
                        break;
                    //4-户籍性质   censusList
                    case "4" :
                        data.put("censusList", baseTypeService.queryCensusList());
                        break;
                    //5-职等职级   titleLevelDegreeList
                    case "5" :
                        data.put("titleLevelDegreeList", levelInfoService.getTree());
                        break;
                    //6-缴费基数   paymentBaseList
                    case "6" :
                        data.put("paymentBaseList", baseTypeService.queryPaymentBaseList());
                        break;
                    //7-申请状态   applyStatusList  0-失败，1-成功
                    case "7" :
                        data.put("applyStatusList", Constants.SIBASE_APPLY_TYPE_LIST);
                        break;
                    default:
                        break;
                }
            }
            return ok().put("data", data);
        } catch (Exception e) {
            LOG.error("获取信息失败。" + categoryType, e);
            return fail("获取信息失败。" + e);
        }
    }

    /**
     * 删除某个批次
     * @since: 2014-06-17 14:42:52
     * @param batchId
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteBatch/{batchId}")
    @ResponseBody
    public JsonResult generateBatch(@PathVariable String batchId, HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_BATCH_DELETE)) {
                return fail("没有权限！");
            }
            return siBatchInfoService.deleteBatch(batchId, employee.getUserCode()) ? ok() : fail("删除批次失败。");
        } catch (Exception e) {
            LOG.error("删除批次失败。", e);
            return fail();
        }
    }

    /**
     * 初始化批次数据
     * @since: 2014-06-18 10:06:58
     * @param paymentDate
     * @param dataBaseName
     * @return
     */
    @RequestMapping(value = "/initBatchInfo")
    @ResponseBody
    public JsonResult initBatchInfo(@RequestParam Date paymentDate, @RequestParam String dataBaseName, HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!"90378,89695,93197".contains(String.valueOf(employee.getUserCode()))) {
                return fail("没有权限！");
            }
            return siBatchInfoService.initSIBaseBatchInfo(paymentDate, dataBaseName) ? ok() : fail("初始化批次数据失败。");
        } catch (Exception e) {
            LOG.error("初始化批次数据失败。", e);
            return fail();
        }
    }

}

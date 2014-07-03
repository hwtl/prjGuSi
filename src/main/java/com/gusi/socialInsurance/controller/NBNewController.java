package com.gusi.socialInsurance.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.gusi.socialInsurance.dto.BaseSearch;
import com.gusi.socialInsurance.model.SIBatch;
import com.gusi.socialInsurance.service.SIBatchInfoService;
import com.gusi.socialInsurance.service.SIBatchService;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-06-12 14:08
 * @Description: NBQuitController
 */
@Controller
@RequestMapping("/si/nbnew/**")
public class NBNewController extends BomsBaseController {

    private static final Log LOG = LogFactory.getLog(NBNewController.class);

    @Autowired
    private SIBatchService siBatchService;
    @Autowired
    private SIBatchInfoService siBatchInfoService;

    /**
     * 宁波退出列表
     * @since: 2014-06-12 15:23:44
     * @param nbSearch
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public JsonResult list(BaseSearch nbSearch, HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_VIEW)) {
                return fail("没有权限！");
            }
            Map<String, Object> data = new HashMap<>();
            data.put("resultList", siBatchService.queryForPaginateNBNew(nbSearch));
            data.put("totalCount", siBatchService.countForPaginateNBNew(nbSearch));
            data.put("pageNo", nbSearch.getPageNo());
            data.put("pageSize", nbSearch.getPageSize());
            return ok().put("data", data);
        } catch (Exception e) {
            LOG.error("查询失败。" + nbSearch, e);
            return fail();
        }
    }

    /**
     * 更新缴纳信息
     * @since: 2014-06-13 17:53:58
     * @param request
     * @param siBatch
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public JsonResult edit(HttpServletRequest request, SIBatch siBatch) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_OPERATE)) {
                return fail("没有权限！");
            }
            siBatchService.update(siBatch, employee.getUserCode());
            return ok();
        } catch (Exception e) {
            LOG.error("更新信息失败。" + siBatch, e);
            return fail();
        }
    }

    /**
     * 切换缴纳年月，联动缴纳批次，以及拉名称、确认退出状态
     * @since: 2014-06-12 16:15:45
     * @param paymentDate
     * @return
     */
    @RequestMapping(value = "/switchdate")
    @ResponseBody
    public JsonResult switchDate(@RequestParam Date paymentDate, HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_VIEW)) {
                return fail("没有权限！");
            }
            Map<String, Object> data = new HashMap<>();
            data.put("batchInfo", siBatchInfoService.findBatchInfoNBNew(paymentDate));
            return ok().put("data", data);
        } catch (Exception e) {
            LOG.error("查询失败。" + paymentDate, e);
            return fail();
        }
    }

    /**
     * 确认退出
     * @since: 2014-06-12 16:28:33
     * @return
     */
    @RequestMapping(value = "/confirmpayment")
    @ResponseBody
    public JsonResult confirmQuit(@RequestParam String batchId,HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_OPERATE)) {
                return fail("没有权限！");
            }
            return siBatchInfoService.confirmStatus(batchId, employee.getUserCode()) ? ok() : fail("确认退出失败。");
        } catch (Exception e) {
            LOG.error("确认退出失败。" + batchId, e);
            return fail();
        }
    }

    /**
     * 拉名单第一步：获取批次开始时间区间(即上一次批次生成的时间)
     * @since: 2014-06-13 10:59:35
     * @param request
     * @return
     */
    @RequestMapping(value = "/gbbegintime")
    @ResponseBody
    public JsonResult getBatchBeginTime(HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_OPERATE)) {
                return fail("没有权限！");
            }
            Map<String, Object> data = new HashMap<>();
            data.put("batchBeginTime", siBatchInfoService.getPreviousBatchDateNBNew());
            return  ok().put("data", data);
        } catch (Exception e) {
            LOG.error("拉名单失败。", e);
            return fail();
        }
    }

    /**
     * 拉名单
     * @since: 2014-06-13 10:42:14
     * @param request
     * @return
     */
    @RequestMapping(value = "/generatebatch")
    @ResponseBody
    public JsonResult generateBatch(@RequestParam Date paymentDate, HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_OPERATE)) {
                return fail("没有权限！");
            }
            return siBatchInfoService.generateNBNew(employee.getUserCode(), paymentDate) ? ok() : fail("拉名单失败。");
        } catch (Exception e) {
            LOG.error("拉名单失败。", e);
            return fail();
        }
    }

    /**
     * 导出批次
     * @since: 2014-06-13 18:38:23
     * @param baseSearch
     * @param request
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(BaseSearch baseSearch, HttpServletRequest request, HttpServletResponse response) {
        try {
            Employee employee = this.getSesionUser(request);
            if(!employee.checkPrivilege(Constants.HRM_SI_NB_OPERATE)) {
                response.getWriter().print("没有权限！");
            }
            siBatchService.exportExcelNBNew(response, baseSearch);
        } catch (Exception e) {
            LOG.error("导出Excel失败", e);
        }
    }

}

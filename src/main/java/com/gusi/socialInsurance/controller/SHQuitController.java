/**
 * 
 */
package com.gusi.socialInsurance.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gusi.socialInsurance.dto.BaseSearch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.gusi.socialInsurance.service.SIBatchInfoService;
import com.gusi.socialInsurance.service.SIBatchService;
import com.dooioo.web.helper.JsonResult;

/**
 * @author Alex Yu
 * @Created 2014年6月14日下午4:03:47
 */
@Controller
@RequestMapping("/si/shquit/**")
public class SHQuitController extends BomsBaseController {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private SIBatchService siBatchService;
    @Autowired
    private SIBatchInfoService siBatchInfoService;

	@ResponseBody
	@RequestMapping(value = "/list")
	public JsonResult list(HttpServletRequest request, BaseSearch baseSearch) {
		try {
            Employee employee = this.getSesionUser(request);
			if (!employee.checkPrivilege(Constants.HRM_SI_SH_VIEW)) {
				return fail("没有权限！");
			}
			Map<String, Object> data = new HashMap<>();
			data.put("resultList", siBatchService.queryForPaginateSHQuit(baseSearch));
			data.put("totalCount", siBatchService.countForPaginateSHQuit(baseSearch));
			data.put("pageNo", baseSearch.getPageNo());
			data.put("pageSize", baseSearch.getPageSize());
			return ok().put("data", data);
		} catch (Exception e) {
			log.error("查询失败。" + baseSearch, e);
			return fail();
		}
	}

    /**
     * 导出批次
     * @param request
     * @param response
     * @param baseSearch
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response, BaseSearch baseSearch) {
        try {
            Employee employee = this.getSesionUser(request);
            if (!employee.checkPrivilege(Constants.HRM_SI_SH_OPERATE)) {
                response.getWriter().print("没有权限！请后台反馈");
            }
            siBatchService.exportExcelSHQuit(response, baseSearch);
        } catch (Exception e) {
            log.error("导出Excel失败", e);
        }
    }

	/**
	 * 切换退出年月，联动缴纳批次，以及拉名称、确认退出状态
	 * 
	 * @param request
	 * @param paymentDate
	 * @return
	 */
    @RequestMapping(value = "/switchdate")
    @ResponseBody
    public JsonResult switchDate(HttpServletRequest request, @RequestParam Date paymentDate) {
        try {
            Employee employee = this.getSesionUser(request);
			if (!employee.checkPrivilege(Constants.HRM_SI_SH_VIEW)) {
				return fail("没有权限！");
			}
            Map<String, Object> data = new HashMap<>();
            data.put("batchInfo", siBatchInfoService.queryForListSHQuit(paymentDate));
            return ok().put("data", data);
        } catch (Exception e) {
            log.error("查询失败。" + paymentDate, e);
            return fail();
        }
    }

	/**
	 * 确认退出
	 * 
	 * @param request
	 * @param batchId
	 * @return
	 */
    @RequestMapping(value = "/confirmquit")
    @ResponseBody
    public JsonResult confirmQuit(HttpServletRequest request, @RequestParam String batchId) {
        try {
            Employee employee = this.getSesionUser(request);
            if (!employee.checkPrivilege(Constants.HRM_SI_SH_OPERATE)) {
                return fail("没有权限！");
            }
            return siBatchInfoService.confirmStatus(batchId, employee.getUserCode()) ? ok() : fail("确认退出失败。");
        } catch (Exception e) {
            log.error("确认退出失败。" + batchId, e);
            return fail();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/generatebatch")
    public JsonResult generateBatch(@RequestParam Date paymentDate) {
    	try {
    		siBatchInfoService.generateSHQuit(Constants.GUANLIYUAN, paymentDate);
    	} catch (Exception ex) {
    		return fail(ex.getMessage());
    	}
    	return ok();
    }
}

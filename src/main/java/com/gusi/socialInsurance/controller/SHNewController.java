/**
 * 
 */
package com.gusi.socialInsurance.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gusi.boms.common.Constants;
import com.gusi.socialInsurance.dto.BaseSearch;
import com.gusi.socialInsurance.model.SIBatch;
import com.gusi.socialInsurance.service.SIBatchInfoService;
import com.gusi.socialInsurance.service.SIBatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.base.controller.BomsBaseController;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.helper.JsonResult;

/**
 * @author Alex Yu
 * @Created 2014年6月14日下午4:03:27
 */
@Controller
@RequestMapping("/si/shnew/**")
public class SHNewController extends BomsBaseController {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private SIBatchService siBatchService;
    @Autowired
    private SIBatchInfoService siBatchInfoService;

	/**
	 * 根据批次信息获取上海新进名单
	 * 
	 * @param request
	 * @param shSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public JsonResult list(HttpServletRequest request, BaseSearch shSearch) {
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("resultList", siBatchService.queryForPaginateSHNew(shSearch));
			data.put("totalCount", siBatchService.countForPaginateSHNew(shSearch));
			data.put("pageNo", shSearch.getPageNo());
			data.put("pageSize", shSearch.getPageSize());
			return ok().put("data", data);
		} catch (Exception e) {
			log.error("查询失败。" + shSearch, e);
			return fail();
		}
	}

	/**
	 * 更新上海新进名单信息
	 * 
	 * @param request
	 * @param siBatch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public JsonResult submit(HttpServletRequest request, SIBatch siBatch) {
		try {
			Employee employee = getSesionUser(request);
			siBatchService.update(siBatch, employee.getUserCode());
			return ok();
		} catch (Exception e) {
			log.error("更新信息失败。" + siBatch, e);
			return fail();
		}
	}

	/**
	 * 切换缴纳年月，联动缴纳批次，以及拉名称、确认退出状态
	 * 
	 * @param paymentDate
	 * @return
	 */
    @RequestMapping(value = "/switchdate")
    @ResponseBody
    public JsonResult switchDate(@RequestParam Date paymentDate) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("batchInfo", siBatchInfoService.findBatchInfoSHNew(paymentDate));
            return ok().put("data", data);
        } catch (Exception e) {
            log.error("查询失败。" + paymentDate, e);
            return fail();
        }
    }

	/**
	 * 确认缴纳
	 * 
	 * @param request
	 * @param batchId
	 * @return
	 */
    @RequestMapping(value = "/confirmpayment")
    @ResponseBody
	public JsonResult confirmPayment(HttpServletRequest request, @RequestParam String batchId) {
		try {
			Employee employee = this.getSesionUser(request);
			return siBatchInfoService.confirmStatus(batchId, employee.getUserCode()) ? ok() : fail("确认缴纳失败。");
		} catch (Exception e) {
			log.error("确认缴纳失败。" + batchId, e);
			return fail();
		}
	}

	/**
	 * 导出上海新进批次名单
	 * 
	 * @param request
	 * @param response
	 * @param baseSearch
	 */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response, BaseSearch baseSearch) {
        try {
            siBatchService.exportExcelSHNew(response, baseSearch);
        } catch (Exception e) {
            log.error("导出Excel失败", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/generatebatch")
    public JsonResult generateBatch(@RequestParam Date paymentDate) {
    	try {
    		siBatchInfoService.generateSHNew(Constants.GUANLIYUAN, paymentDate);
    	} catch (Exception ex) {
    		return fail(ex.getMessage());
    	}
    	return ok();
    }

//	/**
//	 * 导出上海新进批次名单
//	 * 
//	 * @param request
//	 * @param response
//	 * @param baseSearch
//	 */
//    @RequestMapping(value = "/export", method = RequestMethod.GET)
//    public void export(HttpServletRequest request, HttpServletResponse response, BaseSearch baseSearch) {
//		try {
//			List<SIBatch> ars = new ArrayList<>();
//			SIBatch s = new SIBatch();
//			s.setOrgName("dsdds");
//			s.setNewJoinDate(new Date());
//			s.setApplyStatus(1);
//			s.setContractEnd(0);
//			ars.add(s);
//			WebExport.export(response, ars, "我是导出啊.xls",
//					new AbstractExportResolver() {
//
//						@Override
//						public int[] getIncludeOrderSet() {
//							return new int[] { 5, 3, 11, 1 };
//						}
//
//						@Override
//						public String doResolveCellVal(Object val,
//								int columnOrder, String cellHeader,
//								int rowIdex, int cellIndex) {
//							if (val != null && columnOrder == 5) {
//								int v = (int) val;
//								switch (v) {
//								case 1:
//									return "是成功";
//
//								default:
//									return "否不沉公告";
//								}
//							}
//							return super.doResolveCellVal(val, columnOrder,
//									cellHeader, rowIdex, cellIndex);
//						}
//
//					});
//		} catch (Exception e) {
//            log.error("导出Excel失败", e);
//        }
//    }
}

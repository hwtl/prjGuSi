package com.gusi.socialInsurance.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.helper.AttachHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.gusi.socialInsurance.dto.SIBaseInfoSearch;
import com.gusi.socialInsurance.model.SIBaseInfo;
import com.gusi.socialInsurance.service.SIBaseInfoService;
import com.dooioo.web.helper.JsonResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-06-06 13:21
 * @Description: SIBaseInfoController
 */
@Controller
@RequestMapping("/si/base/**")
public class SIBaseInfoController extends BomsBaseController {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private SIBaseInfoService siBaseInfoService;

	/**
	 * 列表
	 * 
	 * @param siBaseInfoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public JsonResult list(SIBaseInfoSearch siBaseInfoSearch) {
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("resultList", siBaseInfoService.queryForPaginate(siBaseInfoSearch));
			data.put("totalCount", siBaseInfoService.countForPaginate(siBaseInfoSearch));
			data.put("pageNo", siBaseInfoSearch.getPageNo());
			data.put("pageSize", siBaseInfoSearch.getPageSize());
			return ok().put("data", data);
		} catch (Exception e) {
			log.error("查询失败。" + siBaseInfoSearch, e);
			return fail("查询失败, 请稍后再试或后台反馈");
		}
	}

	/**
	 * 编辑提交
	 * 
	 * @param siBaseInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public JsonResult submit(HttpServletRequest request, SIBaseInfo siBaseInfo) {
		try {
			Employee employee = getSesionUser(request);
			siBaseInfoService.updateSIBaseInfo(siBaseInfo, employee.getUserCode());
			return ok();
		} catch (Exception e) {
			log.error("编辑提交失败。" + siBaseInfo, e);
			return fail("编辑提交失败, 请稍后再试或后台反馈");
		}
	}

	/**
	 * 上传附件(异地申请表和普通附件)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attach", method = RequestMethod.POST)
	public JsonResult attach(@RequestParam("fileObject") MultipartFile attachFile) {
		try {
            if(attachFile.isEmpty()) {
                return fail("上传文件为空。");
            }
            if(!AttachHelper.isImage(attachFile)) {
                return fail("上传照片格式有问题。");
            }
            if(attachFile.getSize() > AttachHelper.UPLOAD_FILE_MAX_SIZE) {
                return fail("上传大小不能超过2MB。");
            }
            Map<String, String> data = AttachHelper.uploadSIAttachAndResultParam(attachFile);
            return ok().put("data", data);
		} catch (Exception e) {
			log.error("上传附件失败。", e);
			return fail("上传附件失败, 请稍后再试或后台反馈");
		}
	}

	/**
	 * 首页社保信息导出
	 * 
	 * @param siBaseInfoSearch
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/export")
	public void export(SIBaseInfoSearch siBaseInfoSearch, HttpServletResponse response) {
		try {
			siBaseInfoService.exportExcel(siBaseInfoSearch, response);
		} catch (IOException e) {
			log.error("导出Excel失败", e);
		}
	}

	/**
	 * 首页缴纳信息导入
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/import")
    @ResponseBody
	public JsonResult excelImport(HttpServletRequest request) {
		try {
            MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
            MultipartFile attachFile =  multipartRequest.getFile("fileObject");
			siBaseInfoService.importExcel(attachFile);
            return ok();
		} catch (Exception e) {
			log.error("缴纳信息导入失败", e);
            return fail();
		}
	}
}

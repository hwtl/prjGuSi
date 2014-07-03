package com.gusi.socialInsurance.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.gusi.socialInsurance.dto.SelfPaySearch;
import com.gusi.socialInsurance.model.SelfPay;
import com.gusi.socialInsurance.service.SelfPayService;
import com.dooioo.web.helper.JsonResult;

/**
 * @Author: fdj
 * @Since: 2014-06-05 09:19
 * @Description: SelfPayController
 */
@Controller
@RequestMapping("/si/self/**")
public class SelfPayController extends BomsBaseController {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;

    @Autowired
    private SelfPayService selfPayService;

    /**
     * 自缴列表
     * @since: 2014-06-05 15:41:41
     * @param request
     * @param selfPaySearch
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public JsonResult list(HttpServletRequest request, SelfPaySearch selfPaySearch) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("resultList", selfPayService.queryForPaginate(selfPaySearch));
            data.put("totalCount", selfPayService.countForPaginate(selfPaySearch));
            data.put("pageNo", selfPaySearch.getPageNo());
            data.put("pageSize", selfPaySearch.getPageSize());
            return ok().put("data", data);
        } catch (Exception e) {
            log.error("查询失败。" + selfPaySearch, e);
            return fail(e.getMessage());
        }
    }

	/**
	 * 自缴新增中根据工号/姓名模糊查询对应公司的10条在职员工
	 * 
	 * @param request
	 * @param keyword
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "/searchEmpByCodeAndName")
    public JsonResult searchEmpByCodeAndName(HttpServletRequest request, String keyword) {
    	Employee employee = getSesionUser(request);
    	if (employee != null) {
    		Map<String, Object> variables = new HashMap<String, Object>();
    		variables.put("keyword", keyword);
    		variables.put("company", employee.getCompany());
    		List<Object> empList = employeeBaseInforService.searchEmpByCodeAndName(variables);
    		return ok().put("empList", empList);
    	} else {
    		return fail("请登录系统再做操作");
    	}
    }

    /**
     * 新增自缴
     * @since: 2014-06-05 16:24:13
     * @param request
     * @param selfPay
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResult add(HttpServletRequest request, SelfPay selfPay) {
        try {
            Employee employee = this.getSesionUser(request);
            selfPayService.save(selfPay, employee.getUserCode());
            return ok();
        } catch (Exception e) {
            log.error("保存自缴信息失败。" + selfPay, e);
            return fail(e.getMessage());
        }
    }

    /**
     * 更新自缴
     * @since: 2014-06-05 16:24:17
     * @param request
     * @param selfPay
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public JsonResult edit(HttpServletRequest request, SelfPay selfPay) {
        try {
            Employee employee = this.getSesionUser(request);
            selfPayService.update(selfPay, employee.getUserCode());
            return ok();
        } catch (Exception e) {
            log.error("更新自缴信息失败。" + selfPay, e);
            return fail(e.getMessage());
        }
    }

    /**
     * 删除拦截
     * @since: 2014-06-05 16:24:21
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/abolish/{id}", method = RequestMethod.POST)
    public JsonResult delete(@PathVariable int id, HttpServletRequest request) {
        try {
            Employee employee = this.getSesionUser(request);
            selfPayService.delete(id, employee.getUserCode());
            return ok();
        } catch (Exception e) {
            log.error("删除自缴信息失败。" + id, e);
            return fail(e.getMessage());
        }
    }

	/**
	 * 自缴信息导出
	 * 
	 * @param request
	 * @param response
	 * @param selfPaySearch
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String export(HttpServletRequest request, HttpServletResponse response, SelfPaySearch selfPaySearch) {

		try {
			selfPayService.exportExcel(response, selfPaySearch);
		} catch (IOException e) {
			log.error("导出Excel失败", e);
		}
		return null;
	}
}

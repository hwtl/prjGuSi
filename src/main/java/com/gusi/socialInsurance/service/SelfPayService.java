package com.gusi.socialInsurance.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.util.ExportExcelUtil;
import com.gusi.socialInsurance.dto.ExcelSelfPay;
import com.gusi.socialInsurance.dto.SelfPaySearch;
import com.gusi.socialInsurance.dto.SelfPayWithLocation;
import com.gusi.socialInsurance.enums.EnumChangeType;
import com.gusi.socialInsurance.enums.EnumPaymentStatus;
import com.gusi.socialInsurance.model.SelfPay;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-06-05 09:21
 * @Description: SelfPayService
 */
@Service
public class SelfPayService extends BaseService<SelfPay> {

    /**
     * 保存一条自缴信息
     * @since: 2014-06-05 10:28:09
     * @param selfPay
     * @param creator
     * @return
     */
    public boolean save(SelfPay selfPay, int creator) {
        selfPay.setCreator(creator);
        selfPay.setPaymentStatus(EnumPaymentStatus.未处理.ordinal());
        selfPay.setChangeTypeId(EnumChangeType.新进.ordinal());
        return this.insert(selfPay);
    }

    /**
     * 更新一条自缴信息
     * @since: 2014-06-05 11:02:47
     * @param selfPay
     * @param updator
     * @return
     */
    public boolean update(SelfPay selfPay, int updator) {
        selfPay.setCreator(updator);
        if (selfPay.getPayEndDate() == null) {
        	selfPay.setChangeTypeId(EnumChangeType.新进.ordinal());
        } else {
        	selfPay.setChangeTypeId(EnumChangeType.退出.ordinal());
        }
        return this.update(selfPay);
    }

    /**
     * 获取分页数据
     * @since: 2014-06-05 16:24:39
     * @param selfPaySearch
     * @return
     */
    public List<SelfPay> queryForPaginate(SelfPaySearch selfPaySearch) {
        Map<String, Object> param = new HashMap<>();
        if(selfPaySearch.getPageSize() <= 0) {
            selfPaySearch.setPageSize(Configuration.getInstance().getSiPageSize());
        }
        if(selfPaySearch.getPageNo() <= 0) {
            selfPaySearch.setPageNo(1);
        }
        //计算分页查询的起始位置和结束位置
        param.put("startNo", (selfPaySearch.getPageNo()-1) * selfPaySearch.getPageSize());
        param.put("endNo", selfPaySearch.getPageNo() * selfPaySearch.getPageSize());

        param.put("selfPaySearch", selfPaySearch);
        return this.queryForList(sqlId("queryForPaginate"), param);
    }

    /**
     * 获取分页记录总数
     * @since: 2014-06-05 15:52:00
     * @param selfPaySearch
     * @return
     */
    public int countForPaginate(SelfPaySearch selfPaySearch) {
        Map<String, Object> param = new HashMap<>();
        param.put("selfPaySearch", selfPaySearch);
        return this.count(sqlId("countForPaginate"), param);
    }

	/**
	 * 确认批次信息
	 * 
	 * @param batchId
	 * @param changeTypeId
	 * @param updator
	 * @return
	 */
    public boolean confirmStatus(String batchId, int changeTypeId, int updator) {
    	Map<String, Object> variables = new HashMap<String, Object>();
    	variables.put("batchId", batchId);
    	variables.put("changeTypeId", changeTypeId);
		variables.put("oldPaymentStatus",
				new int[] { EnumPaymentStatus.处理中.ordinal(), EnumPaymentStatus.在缴.ordinal() });
    	variables.put("updator", updator);
    	if (EnumChangeType.新进.ordinal() == changeTypeId) {
    		variables.put("newPaymentStatus", EnumPaymentStatus.在缴.ordinal());
    	} else {
    		variables.put("newPaymentStatus", EnumPaymentStatus.历史.ordinal());
    	}
    	return this.update(sqlId("confirmStatus"), variables);
    }

	/**
	 * 查找未进入任何批次的自缴信息
	 * 
	 * @param selfPaySearch
	 * @return
	 */
    public List<SelfPayWithLocation> queryForSelfPayByStatus(SelfPaySearch selfPaySearch) {
    	Map<String, Object> variables = new HashMap<String, Object>();
    	variables.put("changeTypeId", selfPaySearch.getChangeTypeId());
		variables.put("paymentStatus", selfPaySearch.getPaymentStatus());
		return queryDao.queryForList(sqlId("queryForSelfPayByStatus"), variables);
    }

    /**
     * 根据id删除自缴信息
     * @since: 2014-06-05 15:52:16
     * @param id
     * @param updator
     * @return
     */
    public boolean delete(int id, int updator) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("updator", updator);
        return this.update(sqlId("delete"), param);
    }

	/**
	 * 首页导出社保信息
	 * 
	 * @param response
	 * @param selfPaySearch
	 * @throws IOException
	 */
	public void exportExcel(HttpServletResponse response, SelfPaySearch selfPaySearch)
			throws IOException {
		ExportExcelUtil<ExcelSelfPay> excelUtil = new ExportExcelUtil<ExcelSelfPay>();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename="
				+ "自缴信息.xls");
		String[] headers = new String[] { "工号", "姓名", "部门", "自缴基数", "自缴总金额",
				"公司承担金额", "个人承担金额", "自缴起始月份", "自缴结束月份", "自缴补缴周期开始年月",
				"自缴补缴周期结束年月", "创建日期" };

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("selfPaySearch", selfPaySearch);
		List<ExcelSelfPay> excelData = queryDao.queryForList("SelfPay.exportExcel", variables);
		excelUtil.exportExcel(headers, excelData, response.getOutputStream());
	}
}

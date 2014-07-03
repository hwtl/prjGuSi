package com.gusi.socialInsurance.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.util.ExportExcelUtil;
import com.dooioo.plus.excel.processor.AbstractExcelExecutor;
import com.dooioo.plus.excel.processor.ExcelProcessor;
import com.gusi.socialInsurance.dto.ExcelBaseInfo;
import com.gusi.socialInsurance.dto.SIBaseInfoSearch;
import com.gusi.socialInsurance.model.SIBaseInfo;
import com.dooioo.web.service.BaseService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-06-06 13:22
 * @Description: SIBaseInfoService
 */
@Service
public class SIBaseInfoService extends BaseService<SIBaseInfo> {

	/**
	 * 获取分页数据
	 * 
	 * @since: 2014-06-06 13:32:18
	 * @param siBaseInfoSearch
	 * @return
	 */
	public List<SIBaseInfo> queryForPaginate(SIBaseInfoSearch siBaseInfoSearch) {
		Map<String, Object> param = new HashMap<>();
		if (siBaseInfoSearch.getPageSize() <= 0) {
			siBaseInfoSearch.setPageSize(Configuration.getInstance()
					.getSiPageSize());
		}
		if (siBaseInfoSearch.getPageNo() <= 0) {
			siBaseInfoSearch.setPageNo(1);
		}
		// 计算分页查询的起始位置和结束位置
		param.put("startNo", (siBaseInfoSearch.getPageNo() - 1) * siBaseInfoSearch.getPageSize());
		param.put("endNo", siBaseInfoSearch.getPageNo() * siBaseInfoSearch.getPageSize());

		param.put("siBaseInfoSearch", siBaseInfoSearch);
		return this.queryForList(sqlId("queryForPaginate"), param);
	}

	/**
	 * 获取分页记录总数
	 * 
	 * @since: 2014-06-06 13:32:22
	 * @param siBaseInfoSearch
	 * @return
	 */
	public int countForPaginate(SIBaseInfoSearch siBaseInfoSearch) {
		Map<String, Object> param = new HashMap<>();
		param.put("siBaseInfoSearch", siBaseInfoSearch);
		
		return this.count(sqlId("countForPaginate"), param);
	}

    /**
     * 更新社保记录信息
     * @since: 2014-06-25 14:35:24
     * @param siBaseInfo
     * @param updator
     * @return
     */
    public boolean updateSIBaseInfo(SIBaseInfo siBaseInfo, int updator) {
        siBaseInfo.setUpdator(updator);
        siBaseInfo.setApplyForm(StringUtils.isBlank(siBaseInfo.getApplyFormAttachUrl()) ? 0 : 1);
        return this.updateSIBaseInfo(siBaseInfo);
    }

    /**
     * 更新社保个人信息
     *
     * @param siBaseInfo
     * @return
     */
    private boolean updateSIBaseInfo(SIBaseInfo siBaseInfo) {
        return update(sqlId("updateSIBaseInfo"), siBaseInfo);
    }

	/**
	 * 首页导出社保信息
	 * 
	 * @param siBaseInfoSearch
	 * @param response
	 * @throws IOException
	 */
	public void exportExcel(SIBaseInfoSearch siBaseInfoSearch, HttpServletResponse response)
			throws IOException {
		ExportExcelUtil<ExcelBaseInfo> excelUtil = new ExportExcelUtil<ExcelBaseInfo>();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename="
				+ URLEncoder.encode("社保缴纳信息.xls", "UTF-8"));
		String[] headers = new String[] { "缴纳地", "工号", "姓名", "部门", "入职时间",
				"参保起始月份", "参保结束月份", "补缴周期开始年月", "补缴周期结束年月", "身份证号码", "户籍性质", "缴费基数", "职系",
				"职等职级", "社保卡有无", "异地申请表有无", "异地申请表发放时间", "备注" };

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("siBaseInfoSearch", siBaseInfoSearch);
		List<ExcelBaseInfo> excelData = queryDao.queryForList("SIBaseInfo.exportExcel", variables);
		excelUtil.exportExcel(headers, excelData, response.getOutputStream());
	}

	/**
	 * 首页缴纳信息导入
	 * @throws Exception
	 */
	public void importExcel(MultipartFile multipartFile) throws Exception {
		ExcelProcessor.process(multipartFile.getInputStream(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()),
				new AbstractExcelExecutor() {
					@Override
					public boolean onReadRow(int sheetIndex, int rowIndex,
							List<Object> cells,int totalRows) {
                        if(rowIndex == 0) {
                            return true;
                        }
						int userCode = Integer.valueOf((cells.get(0)).toString());
						String applyFormDate = cells.get(1).toString();
						String possessSocialCard = cells.get(2).toString();
						ExcelBaseInfo excelModel = new ExcelBaseInfo();
                        //工号
                        excelModel.setUserCode(userCode);
                        //异地申请表发放时间(如果有发放时间，那么就把有无异地申请表自动设置成有)
                        if(StringUtils.isNotBlank(applyFormDate)) {
                            excelModel.setApplyFormDate(applyFormDate);
                            excelModel.setApplyForm("1");
                        }
                        //社保卡有无
                        if(StringUtils.isNotBlank(possessSocialCard)) {
                            excelModel.setPossessSocialCard(possessSocialCard.equals("有") ? "1" : "0");
                        }
						queryDao.update("SIBaseInfo.updateSIBaseFromExcel", excelModel);
                        return true;
					}
				});
	}
}

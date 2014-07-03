package com.gusi.socialInsurance.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.util.ExportExcelUtil;
import com.gusi.socialInsurance.dto.*;
import com.gusi.socialInsurance.helper.SIHelper;
import com.gusi.socialInsurance.model.SIBatch;
import com.gusi.socialInsurance.model.SIBatchInfo;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author: fdj
 * @Since: 2014-06-12 14:26
 * @Description: NBSIBatchService
 */
@Service
public class SIBatchService extends BaseService<SIBatch> {

    @Autowired
    private SIBatchInfoService siBatchInfoService;

    /**
     * 获取宁波退出列表
     * @since: 2014-06-12 15:19:37
     * @param nbQuitSearch
     * @return
     */
    public List<SIBatch> queryForPaginateNBQuit(BaseSearch nbQuitSearch) {
        Map<String, Object> param = new HashMap<>();
        if(nbQuitSearch.getPageSize() <= 0) {
            nbQuitSearch.setPageSize(Configuration.getInstance().getSiPageSize());
        }
        if(nbQuitSearch.getPageNo() <= 0) {
            nbQuitSearch.setPageNo(1);
        }
        //计算分页查询的起始位置和结束位置
        param.put("startNo", (nbQuitSearch.getPageNo()-1) * nbQuitSearch.getPageSize());
        param.put("endNo", nbQuitSearch.getPageNo() * nbQuitSearch.getPageSize());

        param.put("nbQuitSearch", nbQuitSearch);
        return this.queryForList(sqlId("queryForPaginateNBQuit"), param);
    }

    /**
     * 获取宁波退出列表总数
     * @since: 2014-06-12 15:20:39
     * @param nbQuitSearch
     * @return
     */
    public int countForPaginateNBQuit(BaseSearch nbQuitSearch) {
        Map<String, Object> param = new HashMap<>();
        param.put("nbQuitSearch", nbQuitSearch);
        return this.count(sqlId("countForPaginateNBQuit"), param);
    }

    /**
     * 获取宁波新进列表
     * @since: 2014-06-12 15:19:37
     * @param nbSearch
     * @return
     */
    public List<SIBatch> queryForPaginateNBNew(BaseSearch nbSearch) {
        Map<String, Object> param = new HashMap<>();
        if(nbSearch.getPageSize() <= 0) {
            nbSearch.setPageSize(Configuration.getInstance().getSiPageSize());
        }
        if(nbSearch.getPageNo() <= 0) {
            nbSearch.setPageNo(1);
        }
        //计算分页查询的起始位置和结束位置
        param.put("startNo", (nbSearch.getPageNo()-1) * nbSearch.getPageSize());
        param.put("endNo", nbSearch.getPageNo() * nbSearch.getPageSize());

        param.put("nbSearch", nbSearch);
        return this.queryForList(sqlId("queryForPaginateNBNew"), param);
    }

    /**
     * 获取宁波新进列表总数
     * @since: 2014-06-12 15:20:39
     * @param nbSearch
     * @return
     */
    public int countForPaginateNBNew(BaseSearch nbSearch) {
        Map<String, Object> param = new HashMap<>();
        param.put("nbSearch", nbSearch);
        return this.count(sqlId("countForPaginateNBNew"), param);
    }

    /**
     * 获取深圳退出列表
     * @since: 2014-06-12 15:19:37
     * @param szSearch
     * @return
     */
    public List<SIBatch> queryForPaginateSZQuit(BaseSearch szSearch) {
        Map<String, Object> param = new HashMap<>();
        if(szSearch.getPageSize() <= 0) {
            szSearch.setPageSize(Configuration.getInstance().getSiPageSize());
        }
        if(szSearch.getPageNo() <= 0) {
            szSearch.setPageNo(1);
        }
        //计算分页查询的起始位置和结束位置
        param.put("startNo", (szSearch.getPageNo()-1) * szSearch.getPageSize());
        param.put("endNo", szSearch.getPageNo() * szSearch.getPageSize());

        param.put("szSearch", szSearch);
        return this.queryForList(sqlId("queryForPaginateSZQuit"), param);
    }

    /**
     * 获取深圳退出列表总数
     * @since: 2014-06-12 15:20:39
     * @param szSearch
     * @return
     */
    public int countForPaginateSZQuit(BaseSearch szSearch) {
        Map<String, Object> param = new HashMap<>();
        param.put("szSearch", szSearch);
        return this.count(sqlId("countForPaginateSZQuit"), param);
    }

	/**
	 * 根据批次信息获取上海新进名单
	 * 
	 * @param shNewSearch
	 * @return
	 */
    public List<SIBatch> queryForPaginateSHNew(BaseSearch shNewSearch) {
        Map<String, Object> param = new HashMap<>();
        if(shNewSearch.getPageSize() <= 0) {
        	shNewSearch.setPageSize(Configuration.getInstance().getSiPageSize());
        }
        if(shNewSearch.getPageNo() <= 0) {
        	shNewSearch.setPageNo(1);
        }
        //计算分页查询的起始位置和结束位置
        param.put("startNo", (shNewSearch.getPageNo()-1) * shNewSearch.getPageSize());
        param.put("endNo", shNewSearch.getPageNo() * shNewSearch.getPageSize());

        param.put("shNewSearch", shNewSearch);
        return this.queryForList(sqlId("queryForPaginateSHNew"), param);
    }

	/**
	 * 根据批次信息获取上海新进名单条数
	 * 
	 * @param shNewSearch
	 * @return
	 */
    public int countForPaginateSHNew(BaseSearch shNewSearch) {
        Map<String, Object> param = new HashMap<>();
        param.put("shNewSearch", shNewSearch);
        return this.count(sqlId("countForPaginateSHNew"), param);
    }

	/**
	 * 根据批次信息获取上海退出名单
	 * 
	 * @param shQuitSearch
	 * @return
	 */
    public List<SIBatch> queryForPaginateSHQuit(BaseSearch shQuitSearch) {
        Map<String, Object> param = new HashMap<>();
        if(shQuitSearch.getPageSize() <= 0) {
        	shQuitSearch.setPageSize(Configuration.getInstance().getSiPageSize());
        }
        if(shQuitSearch.getPageNo() <= 0) {
        	shQuitSearch.setPageNo(1);
        }
        //计算分页查询的起始位置和结束位置
        param.put("startNo", (shQuitSearch.getPageNo()-1) * shQuitSearch.getPageSize());
        param.put("endNo", shQuitSearch.getPageNo() * shQuitSearch.getPageSize());

        param.put("shQuitSearch", shQuitSearch);
        return this.queryForList(sqlId("queryForPaginateSHQuit"), param);
    }

	/**
	 * 根据批次信息获取上海退出名单条数
	 * 
	 * @param shQuitSearch
	 * @return
	 */
    public int countForPaginateSHQuit(BaseSearch shQuitSearch) {
        Map<String, Object> param = new HashMap<>();
        param.put("shQuitSearch", shQuitSearch);
        return this.count(sqlId("countForPaginateSHQuit"), param);
    }

    /**
     * 批量插入批次的数据
     * @since: 2014-06-13 09:14:49
     * @param siBatchList
     * @param batchId
     * @param creator
     * @return
     */
    public boolean batchInsert(final Collection<SIBatch> siBatchList
            , final String batchId
            , final Date paymentDate
            , final int creator
        ) {
        return this.queryDao.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (SIBatch siBatch : siBatchList) {
                    siBatch.setBatchId(batchId);
                    if(siBatch.getRequireDate() == null) {
                        siBatch.setRequireDate(paymentDate);
                    }
                    siBatch.setCreator(creator);
                    siBatch.setUpdator(creator);
                    executor.insert(sqlId("batchInsert"), siBatch);
                }
                return executor.executeBatch();
            }
        }) == siBatchList.size();
    }

    /**
     * 根据id,更新单挑批次里的记录
     * @since: 2014-06-13 17:49:59
     * @param siBatch
     * @param updator
     * @return
     */
    public boolean update(SIBatch siBatch, int updator) {
        siBatch.setUpdator(updator);
        return this.update(sqlId("update"), siBatch);
    }

    /**
     * 导出宁波新进批次
     * @since: 2014-06-13 18:37:21
     * @param response
     * @param baseSearch
     * @throws Exception
     */
    public void exportExcelNBNew(HttpServletResponse response, BaseSearch baseSearch) throws Exception{
        SIBatchInfo siBatchInfo = siBatchInfoService.findById(baseSearch.getBatchId());
        ExportExcelUtil<ExcelBatchNBNew> excelUtil = new ExportExcelUtil<>();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + SIHelper.getExportFileName(siBatchInfo) + ".xls");
        String[] headers = new String[] { "工号", "部门", "姓名", "性别", "户籍性质",
                "异地地址", "身份证号码", "联系电话", "委托时间", "社保基数",
                "要求参保起始月份", "备注", "是否失败", "失败原因" };
        List<ExcelBatchNBNew> excelData = queryDao.queryForList("SIBatch.exportExcelNBNew", baseSearch.getBatchId());
        excelUtil.exportExcel(headers, excelData, response.getOutputStream());
    }

    /**
     * 导出宁波退出批次
     * @since: 2014-06-13 18:37:21
     * @param response
     * @param baseSearch
     * @throws Exception
     */
    public void exportExcelNBQuit(HttpServletResponse response, BaseSearch baseSearch) throws Exception{
        SIBatchInfo siBatchInfo = siBatchInfoService.findById(baseSearch.getBatchId());
        ExportExcelUtil<ExcelBatchNBQuit> excelUtil = new ExportExcelUtil<>();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + SIHelper.getExportFileName(siBatchInfo) + ".xls");
        String[] headers = new String[] {"工号", "姓名", "身份证号码", "截止时间"};
        List<ExcelBatchNBQuit> excelData = queryDao.queryForList("SIBatch.exportExcelNBQuit", baseSearch.getBatchId());
        excelUtil.exportExcel(headers, excelData, response.getOutputStream());
    }

    /**
     * 导出深圳退出批次
     * @since: 2014-06-13 18:37:21
     * @param response
     * @param baseSearch
     * @throws Exception
     */
    public void exportExcelSZQuit(HttpServletResponse response, BaseSearch baseSearch) throws Exception{
        SIBatchInfo siBatchInfo = siBatchInfoService.findById(baseSearch.getBatchId());
        ExportExcelUtil<ExcelBatchSZQuit> excelUtil = new ExportExcelUtil<>();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + SIHelper.getExportFileName(siBatchInfo) + ".xls");
        String[] headers = new String[] { "工作城市", "期望缴纳城市", "实际缴纳城市", "部门", "工号",
                "姓名", "身份证号码", "离职原因", "最后工作日期", "停保年月", "备注"};
        List<ExcelBatchSZQuit> excelData = queryDao.queryForList("SIBatch.exportExcelSZQuit", baseSearch.getBatchId());
        excelUtil.exportExcel(headers, excelData, response.getOutputStream());
    }

    /**
     * 导出上海新进批次
     *
     * @param response
     * @param baseSearch
     * @throws Exception
     */
    public void exportExcelSHNew(HttpServletResponse response,
                                 BaseSearch baseSearch) throws Exception {
        SIBatchInfo siBatchInfo = siBatchInfoService.findById(baseSearch.getBatchId());
        ExportExcelUtil<ExcelBatchSHNew> excelUtil = new ExportExcelUtil<ExcelBatchSHNew>();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + SIHelper.getExportFileName(siBatchInfo) + ".xls");
        String[] headers = new String[] { "工号", "姓名", "部门", "入职日期", "补缴周期开始日期",
                "补缴周期结束日期", "身份证号码", "户籍性质", "职系", "职等职级", "缴费基数", "是否失败",
                "失败原因", "备注" };
        List<ExcelBatchSHNew> excelData = queryDao.queryForList("SIBatch.exportExcelSHNew", baseSearch.getBatchId());
        excelUtil.exportExcel(headers, excelData, response.getOutputStream());
    }

    /**
     * 导出上海退出批次
     *
     * @param response
     * @param baseSearch
     * @throws Exception
     */
    public void exportExcelSHQuit(HttpServletResponse response,
                                 BaseSearch baseSearch) throws Exception {
        SIBatchInfo siBatchInfo = siBatchInfoService.findById(baseSearch.getBatchId());
        ExportExcelUtil<ExcelBatchSHQuit> excelUtil = new ExportExcelUtil<ExcelBatchSHQuit>();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=" + SIHelper.getExportFileName(siBatchInfo) + ".xls");
        String[] headers = new String[] { "工号", "姓名", "部门", "入职日期", "离职日期",
                "身份证号码", "户口所在地"};
        List<ExcelBatchSHQuit> excelData = queryDao.queryForList("SIBatch.exportExcelSHQuit", baseSearch.getBatchId());
        excelUtil.exportExcel(headers, excelData, response.getOutputStream());
    }

    /**
     * 确认缴纳、退出时将现有的缴纳记录更新为结束
     * @since: 2014-06-16 09:37:37
     * @param batchId
     * @param paymentDate
     * @return
     */
    public boolean confirmEnd(String batchId, Date paymentDate, int updator) {
        Map<String,Object> param = new HashMap<>();
        param.put("batchId", batchId);
        param.put("paymentDate", paymentDate);
        param.put("updator", updator);
        return update(sqlId("confirmEnd"), param);
    }

    /**
     * 确认缴时，插入新的记录
     * @since: 2014-06-16 10:00:36
     * @param batchId
     * @param creator
     * @return
     */
    public boolean confirmStart(String batchId, int creator) {
        Map<String,Object> param = new HashMap<>();
        param.put("batchId", batchId);
        param.put("creator", creator);
        return update(sqlId("confirmStart"), param);
    }

    /**
     * 查找某一批次失败的记录
     * @since: 2014-06-17 16:10:45
     * @param batchId
     * @return
     */
    public List<SIBatch> queryFailureList(String batchId) {
        return queryForList(sqlId("queryFailureList"), batchId);
    }

}

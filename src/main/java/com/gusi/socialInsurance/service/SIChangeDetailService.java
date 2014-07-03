package com.gusi.socialInsurance.service;

import com.gusi.socialInsurance.model.SIChangeDetail;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: fdj
 * @Since: 2014-06-06 16:54
 * @Description: SIChangeDetailService
 */
@Service
@Transactional
public class SIChangeDetailService extends BaseService<SIChangeDetail> {

    /**
     * 根据当前批次的id,获取当前批次与上一批次的差异数据
     * @since: 2014-06-12 10:11:38
     * @param batchId
     * @return
     */
    public List<SIChangeDetail> querySIChangeDetailList(String batchId, String previousBatchId) {
        if(StringUtils.isBlank(previousBatchId) || StringUtils.isBlank(previousBatchId)) {
            return new ArrayList<>();
        }
        Map<String, Object> param = new HashMap<>();
        param.put("batchId", batchId);
        param.put("previousBatchId", previousBatchId);
        return queryForList(sqlId("querySIChangeDetailList"), param);
    }

    /**
     * 将员工信息移到历史表
     * @since: 2014-06-13 09:20:04
     * @param batchId
     */
    @Transactional
    public void moveDataToHistory(String batchId) {
        this.copyDataToHistory(batchId);
        this.deleteData(batchId);
    }

    /**
     * 将员工信息移到当前表
     * @since: 2014-06-17 14:38:17
     * @param batchId
     * @param previousBatchId
     */
    @Transactional
    public boolean moveDataToNow(String batchId, String previousBatchId) {
        if(StringUtils.isNotBlank(previousBatchId)) {
            this.copyDataToNow(previousBatchId);
        }
        return this.deleteData(batchId);
    }

    /**
     * 备份数据到历史表
     * @since: 2014-06-17 14:31:22
     * @param batchId
     * @return
     */
    public boolean copyDataToHistory(String batchId) {
        return this.update(sqlId("copyDataToHistory"), batchId);
    }

    /**
     * 备份数据到当前表
     * @since: 2014-06-17 14:31:22
     * @param batchId
     * @return
     */
    public boolean copyDataToNow(String batchId) {
        return this.update(sqlId("copyDataToNow"), batchId);
    }

    /**
     * 删除数据
     * @since: 2014-06-17 14:31:37
     * @param batchId
     * @return
     */
    public boolean deleteData(String batchId) {
        return this.update(sqlId("deleteData"), batchId);
    }

    /**
     * 查询有离职回聘记录的员工
     * @since: 2014-06-23 15:57:13
     * @param paymentDate       查询起始时间
     * @param lastPaymentDate   查询结束时间
     * @return
     */
    public List<SIChangeDetail> queryForLeaveReturn(Date paymentDate, Date lastPaymentDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("paymentDate", paymentDate);
        param.put("lastPaymentDate", lastPaymentDate);
        return this.queryForList(sqlId("queryForLeaveReturn"), param);
    }

}

package com.gusi.socialInsurance.service;

import com.gusi.boms.common.Constants;
import com.gusi.socialInsurance.dto.SelfPaySearch;
import com.gusi.socialInsurance.dto.SelfPayWithLocation;
import com.gusi.socialInsurance.enums.EnumChangeType;
import com.gusi.socialInsurance.enums.EnumPaymentLocation;
import com.gusi.socialInsurance.enums.EnumPaymentStatus;
import com.gusi.socialInsurance.enums.EnumPaymentType;
import com.gusi.socialInsurance.helper.SIHelper;
import com.gusi.socialInsurance.model.SIBaseType;
import com.gusi.socialInsurance.model.SIBatch;
import com.gusi.socialInsurance.model.SIBatchInfo;
import com.gusi.socialInsurance.model.SIChangeDetail;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: fdj
 * @Since: 2014-06-12 15:40
 * @Description: SIBatchInfoService
 */
@Service
@Transactional
public class SIBatchInfoService extends BaseService<SIBatchInfo> {

    private static final Log LOG = LogFactory.getLog(SIBatchInfoService.class);

    @Autowired
    private SIChangeDetailService siChangeDetailService;
    @Autowired
    private SIBatchService siBatchService;
    @Autowired
    private SelfPayService selfPayService;

    /**
     * 根据条件查询批次集合
     * @since: 2014-06-12 16:02:20
     * @param paymentDate       批次日期
     * @param paymentLocationId 批次缴纳地id
     * @param changeTypeId      批次缴纳类型
     * @return
     */
    public List<SIBatchInfo> queryForList(Date paymentDate, int paymentLocationId, int changeTypeId) {
        Map<String, Object> param = new HashMap<>();
        param.put("paymentDate", paymentDate);
        param.put("paymentLocationId", paymentLocationId);
        param.put("changeTypeId", changeTypeId);
        return queryForList(sqlId("queryForList"), param);
    }

    /**
     * 根据缴纳月份获取上海退出的批次列表
     * @param paymentDate
     * @return
     */
    public List<SIBatchInfo> queryForListSHQuit(Date paymentDate) {
        return queryForList(paymentDate, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_QUIT);
    }

    /**
     * 根据缴纳月份获取深圳退出的批次列表
     * @since: 2014-06-13 15:01:48
     * @param paymentDate
     * @return
     */
    public List<SIBatchInfo> queryForListSZQuit(Date paymentDate) {
        return queryForList(paymentDate, SIBaseType.PAYMENT_LOCATION_ID_SZ, SIBaseType.CHANGE_TYPE_ID_QUIT);
    }

    /**
     * 根据条件查询单条批次
     * @since: 2014-06-12 16:08:25
     * @param paymentDate
     * @param paymentLocationId
     * @param changeTypeId
     * @return
     */
    public SIBatchInfo findBatchInfo(Date paymentDate, int paymentLocationId, int changeTypeId) {
        Map<String, Object> param = new HashMap<>();
        param.put("paymentDate", paymentDate);
        param.put("paymentLocationId", paymentLocationId);
        param.put("changeTypeId", changeTypeId);
        return findByBean(sqlId("findBatchInfo"), param);
    }

    /**
     * 获取宁波退出批次单条
     * @since: 2014-06-12 16:10:32
     * @param paymentDate
     * @return
     */
    public SIBatchInfo findBatchInfoNBQuit(Date paymentDate) {
        return findBatchInfo(paymentDate, SIBaseType.PAYMENT_LOCATION_ID_NB, SIBaseType.CHANGE_TYPE_ID_QUIT);
    }

    /**
     * 获取宁波新进批次单条
     * @since: 2014-06-12 16:10:32
     * @param paymentDate
     * @return
     */
    public SIBatchInfo findBatchInfoNBNew(Date paymentDate) {
        return findBatchInfo(paymentDate, SIBaseType.PAYMENT_LOCATION_ID_NB, SIBaseType.CHANGE_TYPE_ID_NEW);
    }

	/**
	 * 获取上海新进批次概况
	 * 
	 * @param paymentDate
	 * @return
	 */
    public SIBatchInfo findBatchInfoSHNew(Date paymentDate) {
        return findBatchInfo(paymentDate, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_NEW);
    }

	/**
	 * 获取上海退出批次概况
	 * 
	 * @param paymentDate
	 * @return
	 */
    public SIBatchInfo findBatchInfoSHQuit(Date paymentDate) {
        return findBatchInfo(paymentDate, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_QUIT);
    }

    /**
     * 1 更新社保缴纳情况主表信息
     * 2 更新批次信息表确认状态
     * @since: 2014-06-13 18:36:02
     * @param batchId
     * @param confirmUser
     * @return
     */
    @Transactional
    public boolean confirmStatus(String batchId, int confirmUser) {
        //获取批次信息
        SIBatchInfo siBatchInfo = this.findById(batchId);
        if(siBatchInfo != null) {
            //将主表信息的对应员工，设置参保月份成结束
            siBatchService.confirmEnd(batchId, siBatchInfo.getPaymentDate(), confirmUser);
            //如果是新进，那么插入主表记录
            if(siBatchInfo.getChangeTypeId() == SIBaseType.CHANGE_TYPE_ID_NEW) {
                siBatchService.confirmStart(batchId, confirmUser);
            }
            int paymentLocationId = siBatchInfo.getPaymentLocationId();
            int changeTypeId = siBatchInfo.getChangeTypeId();
            // 自缴信息更新
            if (EnumPaymentLocation.上海.ordinal() == paymentLocationId) {
            	selfPayService.confirmStatus(batchId, changeTypeId, Constants.GUANLIYUAN);
            }
        }
        //更新批次信息表
        return confirmBatchStatus(batchId, confirmUser);
    }

    /**
     * 确认状态(确认退出、确认新进）
     * @since: 2014-06-12 16:18:59
     * @param batchId
     * @return
     */
    public boolean confirmBatchStatus(String batchId, int confirmUser) {
        SIBatchInfo siBatchInfo = new SIBatchInfo();
        siBatchInfo.setBatchId(batchId);
        siBatchInfo.setConfirmUser(confirmUser);
        siBatchInfo.setConfirmStatus(SIBatchInfo.CONFIRM_STATUS_OK);
        return update(sqlId("confirmStatus"), siBatchInfo);
    }

	/**
	 * 生成上海新进批次
	 * 
	 * @param creator
	 * @return
	 */
    public boolean generateSHNew(int creator, Date paymentDate) {
        return this.generateBatch(creator, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_NEW, paymentDate);
    }

	/**
	 * 生成上海退出批次
	 * 
	 * @param creator
	 * @return
	 */
    public boolean generateSHQuit(int creator, Date paymentDate) {
        return this.generateBatch(creator, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_QUIT, paymentDate);
    }

    /**
     * 生成宁波退出批次
     * @since: 2014-06-13 17:25:55
     * @param creator
     * @return
     */
    public boolean generateNBQuit(int creator, Date paymentDate) {
        return this.generateBatch(creator, SIBaseType.PAYMENT_LOCATION_ID_NB, SIBaseType.CHANGE_TYPE_ID_QUIT, paymentDate);
    }

    /**
     * 生成宁波新进批次
     * @since: 2014-06-13 17:26:15
     * @param creator
     * @return
     */
    public boolean generateNBNew(int creator, Date paymentDate) {
        return this.generateBatch(creator, SIBaseType.PAYMENT_LOCATION_ID_NB, SIBaseType.CHANGE_TYPE_ID_NEW, paymentDate);
    }

    /**
     * 生成深圳退出批次
     * @since: 2014-06-13 17:26:27
     * @param creator
     * @return
     */
    public boolean generateSZQuit(int creator, Date paymentDate) {
        return this.generateBatch(creator, SIBaseType.PAYMENT_LOCATION_ID_SZ, SIBaseType.CHANGE_TYPE_ID_QUIT, paymentDate);
    }

    /**
     * 生成一个批次
     * @since: 2014-06-12 16:44:27
     * @return
     */
    @Transactional
    public boolean generateBatch(int creator, int paymentLocationId, int changeTypeId, Date paymentDate) {
        SIBatchInfo siBatchInfo = new SIBatchInfo();
        //获取批次生成时间
        Calendar now = Calendar.getInstance();
        //获取页面的批次生成时间
        Calendar paymentDateTime = Calendar.getInstance();
        paymentDateTime.setTime(paymentDate);
        //如果不是选择当前月份那么则不能生成批次
        if(now.get(Calendar.YEAR) != paymentDateTime.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) != paymentDateTime.get(Calendar.MONTH)) {
            return false;
        }
        //生成批次batchId
        String batchId = paymentLocationId + "-" + changeTypeId + "-"+ DateFormatUtils.format(now, "yyyyMMdd-HHmmss");
        //获取上一批次信息
        SIBatchInfo previousBatch = this.getPreviousBatch(paymentLocationId, changeTypeId);
        //获取上一次批次的batchId
        String previousBatchId = (previousBatch == null ? null : previousBatch.getBatchId());
        //初始化批次信息
        initSIBatchInfo(creator, siBatchInfo, now, batchId, previousBatchId, paymentLocationId, changeTypeId);
        //插入批次信息
        this.insert(siBatchInfo);
        //备份员工信息
        this.backUpEmployeeData(batchId, creator, now.getTime());
        //获取上一批次与这个批次之间，需要比较的员工集合
        List<SIChangeDetail> siChangeDetailList = siChangeDetailService.querySIChangeDetailList(batchId, previousBatchId);
        //①根据参保规则，过滤出本批次部分名单
        LOG.info("-----------------begin①根据参保规则，过滤出本批次部分名单begin-----------------");
        Map<Integer, SIBatch> siBatchMap = SIHelper.filter(siChangeDetailList, paymentLocationId, changeTypeId);
        //②合并自缴的名单
        LOG.info("-----------------begin②合并自缴的名单begin-----------------");
        Map<String, Object> selfPayMap = new HashMap<String, Object>();
        mergeSelfPayToBatchMap(siBatchMap, selfPayMap, paymentLocationId, changeTypeId);
        //③合并上一批失败的名单
        LOG.info("-----------------begin③合并上一批失败的名单begin-----------------");
        SIHelper.filterWithPrevious(siBatchMap, siBatchService.queryFailureList(previousBatchId), now);
        //④合并离职回聘的名单
        LOG.info("-----------------begin④合并离职回聘的名单begin-----------------");
        SIHelper.filterWithLeaveReturn(siBatchMap, siChangeDetailService.queryForLeaveReturn(now.getTime()
                , previousBatch.getPaymentDate()), paymentLocationId, changeTypeId);
        //将①②③④名单合并后，插入本批次员工
        LOG.info("-----------------begin将①②③④名单合并后，插入本批次员工begin-----------------");
        siBatchService.batchInsert(siBatchMap.values(), batchId, now.getTime(), creator);
        //移除历史备份数据
        siChangeDetailService.moveDataToHistory(previousBatchId);
        return true;
    }

    /**
     * 初始化批次信息
     * @since: 2014-06-13 09:03:18
     * @param creator
     * @param siBatchInfo
     * @param now
     * @param batchId
     */
    private void initSIBatchInfo(int creator, SIBatchInfo siBatchInfo, Calendar now, String batchId, String previousBatchId, int paymentLocationId, int changeTypeId) {
        siBatchInfo.setBatchId(batchId);
        siBatchInfo.setPreviousBatchId(previousBatchId);
        siBatchInfo.setPaymentLocationId(paymentLocationId);
        siBatchInfo.setChangeTypeId(changeTypeId);
        siBatchInfo.setPaymentYear(now.get(Calendar.YEAR));
        siBatchInfo.setPaymentMonth(now.get(Calendar.MONTH) + 1);
        siBatchInfo.setPaymentDay(now.get(Calendar.DAY_OF_MONTH));
        siBatchInfo.setPaymentDate(now.getTime());
        siBatchInfo.setGenerateList(SIBatchInfo.GENERATE_LIST_CAN);
        siBatchInfo.setConfirmStatus(SIBatchInfo.CONFIRM_STATUS_NOT);
        siBatchInfo.setStatus(SIBatchInfo.STATUS_NORMAL);
        siBatchInfo.setCreator(creator);
        siBatchInfo.setCreateTime(now.getTime());
        siBatchInfo.setUpdator(creator);
        siBatchInfo.setUpdateTime(now.getTime());
    }

    /**
     * 获取上一批次的批次id
     * @since: 2014-06-12 16:58:37
     * @param paymentLocationId
     * @param changeTypeId
     * @return
     */
    public SIBatchInfo getPreviousBatch(int paymentLocationId, int changeTypeId) {
        Map<String, Object> param = new HashMap<>();
        param.put("paymentLocationId", paymentLocationId);
        param.put("changeTypeId", changeTypeId);
        return findByBean(sqlId("getPreviousBatch"), param);
    }

    /**
     * 获取上一批次的id
     * @param paymentLocationId
     * @param changeTypeId
     * @return
     */
    public String getPreviousBatchId(int paymentLocationId, int changeTypeId) {
        SIBatchInfo siBatchInfo = getPreviousBatch(paymentLocationId,changeTypeId);
        return siBatchInfo == null ? null : siBatchInfo.getBatchId();
    }

    /**
     * 获取宁波退出的上一批次的时间
     * @since: 2014-06-12 17:00:00
     * @return
     */
    public Date getPreviousBatchDateNBQuit() {
        SIBatchInfo siBatchInfo = getPreviousBatch(SIBaseType.PAYMENT_LOCATION_ID_NB,SIBaseType.CHANGE_TYPE_ID_QUIT);
        return siBatchInfo == null ? null : siBatchInfo.getPaymentDate();
    }

    /**
     * 获取宁波新进的上一批次的时间
     * @since: 2014-06-12 17:00:00
     * @return
     */
    public Date getPreviousBatchDateNBNew() {
        SIBatchInfo siBatchInfo = getPreviousBatch(SIBaseType.PAYMENT_LOCATION_ID_NB,SIBaseType.CHANGE_TYPE_ID_NEW);
        return siBatchInfo == null ? null : siBatchInfo.getPaymentDate();
    }

    /**
     * 获取深圳退出的上一批次的时间
     * @since: 2014-06-12 17:00:00
     * @return
     */
    public Date getPreviousBatchDateSZQuit() {
        SIBatchInfo siBatchInfo = getPreviousBatch(SIBaseType.PAYMENT_LOCATION_ID_SZ,SIBaseType.CHANGE_TYPE_ID_QUIT);
        return siBatchInfo == null ? null : siBatchInfo.getPaymentDate();
    }

    /**
     * 备份员工信息
     * @since: 2014-06-12 18:23:34
     * @param batchId
     * @param creator
     * @param createTime
     * @return
     */
    public boolean backUpEmployeeData(String batchId, int creator, Date createTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("batchId", batchId);
        param.put("creator", creator);
        param.put("createTime", createTime);
        return insert(sqlId("backUpEmployeeData"), param);
    }

    /**
     * 删除某个批次，同时将员工镜像还原到对应的数据
     * @since: 2014-06-17 14:40:26
     * @param batchId
     * @param updator
     * @return
     */
    @Transactional
    public boolean deleteBatch(String batchId, int updator) {
        LOG.info("------------------begin删除批次-------------------");
        SIBatchInfo siBatchInfo = this.findById(batchId);
        LOG.info("------------------1、查询该批次信息，判断是否存在-------------------");
        if(siBatchInfo != null) {
            LOG.info("------------------2、查询下一批次是否存在，判断是否最新一批-------------------");
            SIBatchInfo nextSIBatchInfo = this.findByPreviousBatchId(batchId);
            //如果不是最新的一条记录，那么只要修改previousBatchId
            if(nextSIBatchInfo != null) {
                LOG.info("------------------3、不是最新一批则只需要修改previousBatchId-------------------");
                this.updatePreviousBatchId(nextSIBatchInfo.getBatchId(), updator, siBatchInfo.getPreviousBatchId());
            } else {
                //还原历史数据
                LOG.info("------------------3、是最新一批则那么移动备份数据------------------");
                siChangeDetailService.moveDataToNow(batchId, siBatchInfo.getPreviousBatchId());
            }
            //将批次状态改为删除
            LOG.info("------------------4、最后更新批次状态------------------");
            return this.updateToDelete(batchId, updator);
        }
        return false;
    }

    /**
     * 将批次信息状态置为删除
     * @since: 2014-06-17 14:41:12
     * @param batchId
     * @param updator
     * @return
     */
    public boolean updateToDelete(String batchId, int updator) {
        Map<String, Object> param = new HashMap<>();
        param.put("batchId", batchId);
        param.put("updator", updator);
        return update(sqlId("updateToDelete"), param);
    }

    /**
     * 取得自缴变化名单
     * 
     * @param siBatchMap
     * @param paymentLocationId
     * @param changeTypeId
     * @return
     */
    public void mergeSelfPayToBatchMap(Map<Integer, SIBatch> siBatchMap, Map<String, Object> selfPayMap, int paymentLocationId, int changeTypeId) {
    	List<SelfPayWithLocation> selfPayList = new ArrayList<SelfPayWithLocation>();

		SelfPaySearch selfPaySearch = new SelfPaySearch();
		if (EnumPaymentLocation.上海.ordinal() == paymentLocationId) {
			selfPaySearch.setChangeTypeId(changeTypeId);
			if (EnumChangeType.新进.ordinal() == changeTypeId) {
	    		// 生成【新增】名单, 只关心自缴状态为处理中的数据
	    		selfPaySearch.setPaymentStatus(EnumPaymentStatus.处理中.ordinal());
	    	} else {
	    		// 生成【退出】名单, 只关心自缴状态为在缴的数据
	    		selfPaySearch.setPaymentStatus(EnumPaymentStatus.在缴.ordinal());
	    	}
		} else if (EnumPaymentLocation.宁波.ordinal() == paymentLocationId) {
			if (EnumChangeType.新进.ordinal() == changeTypeId) {
				selfPaySearch.setChangeTypeId(EnumChangeType.退出.ordinal());
	    		// 生成【新增】名单, 只关心自缴状态为在缴的数据
	    		selfPaySearch.setPaymentStatus(EnumPaymentStatus.在缴.ordinal());
	    	} else {
				selfPaySearch.setChangeTypeId(EnumChangeType.新进.ordinal());
	    		// 生成【退出】名单, 只关心自缴状态为处理中的数据
	    		selfPaySearch.setPaymentStatus(EnumPaymentStatus.处理中.ordinal());
	    	}
		} else if (EnumPaymentLocation.深圳.ordinal() == paymentLocationId) {
			if (EnumChangeType.退出.ordinal() == changeTypeId) {
				selfPaySearch.setChangeTypeId(EnumChangeType.新进.ordinal());
	    		// 生成【退出】名单, 只关心自缴状态为处理中的数据
	    		selfPaySearch.setPaymentStatus(EnumPaymentStatus.处理中.ordinal());
	    	}
		}

		selfPayList = selfPayService.queryForSelfPayByStatus(selfPaySearch);

		Calendar now = Calendar.getInstance();
		// 上海新增名单的场合
		if (EnumPaymentLocation.上海.ordinal() == paymentLocationId) {
			if (EnumChangeType.新进.ordinal() == changeTypeId) {
				for (SelfPayWithLocation selfPay : selfPayList) {
					// 自缴开始月份
					Date beginDate = selfPay.getPayBeginDate();
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.setTime(beginDate);
					// 工号
					int userCode = selfPay.getUserCode();
					// 自缴开始月份是当前月份
					if (now.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR) && now.get(Calendar.MONTH) == beginCalendar.get(Calendar.MONTH)) {
						// 如果已经在上海在缴, 则跳过
						if (selfPay.getPaymentLocationId() != null && EnumPaymentLocation.上海.ordinal() == selfPay.getPaymentLocationId().intValue()) {
							continue;
						}
						// 缴纳基数
						int paymentBase = selfPay.getPaymentBase().intValue();
						// 新近名单中包含该员工的情况下, 保留缴纳基数, 从名单中移除该员工
						if (siBatchMap.containsKey(userCode)) {
							// 保留缴纳基数
							paymentBase = siBatchMap.get(userCode).getPaymentBase();
							// 移除该员工
							siBatchMap.remove(userCode);
						}
						siBatchMap.put(
								userCode,
								new SIBatch(userCode, EnumPaymentType.员工自缴
										.ordinal(), paymentBase,
										selfPay.getExtraBeginDate(), selfPay
										.getExtraEndDate()));
					}
				}
			} else {
				for (SelfPayWithLocation selfPay : selfPayList) {
					// 自缴结束月份
					Date endDate = selfPay.getPayEndDate();
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.setTime(endDate);
					// 工号
					int userCode = selfPay.getUserCode();
					// 自缴结束月份是当前月份
					if (now.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && now.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH)) {
						// 已经在上海参保, 并且不在上海退出名单的才退出
						if (selfPay.getPaymentLocationId() != null && EnumPaymentLocation.上海.ordinal() == selfPay.getPaymentLocationId().intValue() && !siBatchMap.containsKey(userCode)) {
							siBatchMap.put(
									userCode,
									new SIBatch(userCode, EnumPaymentType.员工自缴
											.ordinal(), selfPay.getPaymentBase(),
											selfPay.getExtraBeginDate(), selfPay
											.getExtraEndDate()));
						}
					}
				}
			}
		} else if (EnumPaymentLocation.宁波.ordinal() == paymentLocationId) {
			if (EnumChangeType.新进.ordinal() == changeTypeId) {
				for (SelfPayWithLocation selfPay : selfPayList) {
					// 自缴结束月份
					Date endDate = selfPay.getPayEndDate();
					Calendar endCalendar = Calendar.getInstance();
					endCalendar.setTime(endDate);
					// 工号
					int userCode = selfPay.getUserCode();
					// 自缴结束月份是当前月份
					if (now.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR) && now.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH)) {
						// 不在宁波缴纳, 并且不在宁波新增名单中的才进入
						if ((selfPay.getPaymentLocationId() == null || EnumPaymentLocation.宁波.ordinal() != selfPay.getPaymentLocationId().intValue()) && !siBatchMap.containsKey(userCode)) {
							// 计算缴纳基数
							int paymentBase = SIHelper.getPaymentBase(null, EnumPaymentLocation.宁波, selfPay.getCredentialsNo());
							siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal(), paymentBase));
						}
					}
				}
			} else {
				for (SelfPayWithLocation selfPay : selfPayList) {
					// 自缴开始月份
					Date beginDate = selfPay.getPayBeginDate();
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.setTime(beginDate);
					// 工号
					int userCode = selfPay.getUserCode();
					// 自缴开始月份是当前月份
					if (now.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR) && now.get(Calendar.MONTH) == beginCalendar.get(Calendar.MONTH)) {
						if (selfPay.getPaymentLocationId() != null && EnumPaymentLocation.宁波.ordinal() == selfPay.getPaymentLocationId().intValue() && !siBatchMap.containsKey(userCode)) {
							siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal()));
						}
					}
				}
			}
		} else if (EnumPaymentLocation.深圳.ordinal() == paymentLocationId) {
			if (EnumChangeType.退出.ordinal() == changeTypeId) {
				for (SelfPayWithLocation selfPay : selfPayList) {
					// 自缴开始月份
					Date beginDate = selfPay.getPayBeginDate();
					Calendar beginCalendar = Calendar.getInstance();
					beginCalendar.setTime(beginDate);
					// 工号
					int userCode = selfPay.getUserCode();
					// 自缴开始月份是当前月份
					if (now.get(Calendar.YEAR) == beginCalendar.get(Calendar.YEAR) && now.get(Calendar.MONTH) == beginCalendar.get(Calendar.MONTH)) {
						if (selfPay.getPaymentLocationId() != null && EnumPaymentLocation.深圳.ordinal() == selfPay.getPaymentLocationId().intValue() && !siBatchMap.containsKey(userCode)) {
							siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.委托代缴.ordinal()));
						}
					}
				}
			}
		}
    }

    /**
     * 更新previousBatchId
     * @since: 2014-06-18 09:15:04
     * @param batchId
     * @param updator
     * @param previousBatchId
     * @return
     */
    public boolean updatePreviousBatchId(String batchId, int updator, String previousBatchId) {
        Map<String, Object> param = new HashMap<>();
        param.put("batchId", batchId);
        param.put("updator", updator);
        param.put("previousBatchId", previousBatchId);
        return update(sqlId("updatePreviousBatchId"), param);
    }

    /**
     * 根据上一批次的previousBatchId查询批次
     * @since: 2014-06-18 09:08:42
     * @param batchId
     * @return
     */
    public SIBatchInfo findByPreviousBatchId(String batchId) {
        return this.findByBean(sqlId("findByPreviousBatchId"), batchId);
    }

    /**
     * 初始化5份数据
     * @since: 2014-06-18 10:00:28
     * @param paymentDate
     * @param dataBaseName
     * @return
     */
    @Transactional
    public boolean initSIBaseBatchInfo(Date paymentDate, String dataBaseName) {
        if(this.countData() > 0) {
            return false;
        }
        this.generateBatch(Constants.GUANLIYUAN, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_NEW, paymentDate, dataBaseName);
        this.generateBatch(Constants.GUANLIYUAN, SIBaseType.PAYMENT_LOCATION_ID_SH, SIBaseType.CHANGE_TYPE_ID_QUIT, paymentDate, dataBaseName);
        this.generateBatch(Constants.GUANLIYUAN, SIBaseType.PAYMENT_LOCATION_ID_NB, SIBaseType.CHANGE_TYPE_ID_NEW, paymentDate, dataBaseName);
        this.generateBatch(Constants.GUANLIYUAN, SIBaseType.PAYMENT_LOCATION_ID_NB, SIBaseType.CHANGE_TYPE_ID_QUIT, paymentDate, dataBaseName);
        this.generateBatch(Constants.GUANLIYUAN, SIBaseType.PAYMENT_LOCATION_ID_SZ, SIBaseType.CHANGE_TYPE_ID_QUIT, paymentDate, dataBaseName);
        return true;
    }

    /**
     * 初始化数据，生成批次
     * @since: 2014-06-18 10:00:53
     * @param creator
     * @param paymentLocationId
     * @param changeTypeId
     * @param paymentDate
     * @param dataBaseName
     * @return
     */
    @Transactional
    public boolean generateBatch(int creator, int paymentLocationId, int changeTypeId, Date paymentDate, String dataBaseName) {
        SIBatchInfo siBatchInfo = new SIBatchInfo();
        //获取批次生成时间
        Calendar now = Calendar.getInstance();
        now.setTime(paymentDate);
        //生成批次batchId
        String batchId = paymentLocationId + "-" + changeTypeId + "-"+ DateFormatUtils.format(now, "yyyyMMdd-HHmmss");
        //获取上一次批次的batchId
        String previousBatchId = this.getPreviousBatchId(paymentLocationId, changeTypeId);
        //初始化批次信息
        initSIBatchInfo(creator, siBatchInfo, now, batchId, previousBatchId, paymentLocationId, changeTypeId);
        //插入批次信息
        this.insert(siBatchInfo);
        //备份员工信息
        this.backUpEmployeeDataFromOtherDataBase(dataBaseName, batchId, creator, now.getTime());
        return true;
    }

    /**
     * 备份数据
     * @since: 2014-06-18 10:01:11
     * @param dataBaseName
     * @param batchId
     * @param creator
     * @param createTime
     * @return
     */
    public boolean backUpEmployeeDataFromOtherDataBase(String dataBaseName, String batchId, int creator, Date createTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("dataBaseName", dataBaseName);
        param.put("batchId", batchId);
        param.put("creator", creator);
        param.put("createTime", createTime);
        return insert(sqlId("backUpEmployeeDataFromOtherDataBase"), param);
    }

    /**
     * 查询批次数量
     * @since: 2014-06-18 10:10:43
     * @return
     */
    public int countData() {
        return count(sqlId("countData"), 0);
    }
}

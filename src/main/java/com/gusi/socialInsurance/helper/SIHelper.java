package com.gusi.socialInsurance.helper;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.boms.bomsEnum.EnumSerial;
import com.gusi.boms.model.Position;
import com.gusi.socialInsurance.enums.EnumChangeType;
import com.gusi.socialInsurance.enums.EnumPaymentLocation;
import com.gusi.socialInsurance.model.SIBatch;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.gusi.socialInsurance.enums.EnumPaymentType;
import com.gusi.socialInsurance.model.SIBaseType;
import com.gusi.socialInsurance.model.SIBatchInfo;
import com.gusi.socialInsurance.model.SIChangeDetail;

/**
 * @Author: fdj
 * @Since: 2014-06-09 09:25
 * @Description: 社保辅助类
 */
public class SIHelper {

    public static final String ID_CARD_PRE_330 = "330";
    public static final String ID_CARD_PRE_310 = "310";

    public static final String CONTRACT_TYPE_SXXY = "实习协议";
    public static final String CONTRACT_TYPE_QCWYWBXY = "前程无忧外包协议";

    /**
     * 根据不同缴纳地、缴纳类型过滤批次名单
     * @since: 2014-06-13 17:08:35
     * @param siChangeDetailList
     * @param paymentLocationId
     * @param changeTypeId
     * @return
     */
    public static Map<Integer, SIBatch> filter(List<SIChangeDetail> siChangeDetailList,int paymentLocationId, int changeTypeId) {
        if(CollectionUtils.isEmpty(siChangeDetailList)) {
            return new HashMap<Integer, SIBatch>();
        }
    	// 上海, 新进的场合
        if(SIBaseType.PAYMENT_LOCATION_ID_SH == paymentLocationId && SIBaseType.CHANGE_TYPE_ID_NEW == changeTypeId) {
            return shNewFilter(siChangeDetailList);
        }
        // 上海, 退出的场合
        if(SIBaseType.PAYMENT_LOCATION_ID_SH == paymentLocationId && SIBaseType.CHANGE_TYPE_ID_QUIT == changeTypeId) {
            return shQuitFilter(siChangeDetailList);
        }
        // 宁波, 新进的场合
        if(SIBaseType.PAYMENT_LOCATION_ID_NB == paymentLocationId && SIBaseType.CHANGE_TYPE_ID_NEW == changeTypeId) {
            return nbNewFilter(siChangeDetailList);
        }
        // 宁波, 退出的场合
        if(SIBaseType.PAYMENT_LOCATION_ID_NB == paymentLocationId && SIBaseType.CHANGE_TYPE_ID_QUIT == changeTypeId) {
            return nbQuitFilter(siChangeDetailList);
        }
        // 深圳, 退出的场合
        if(SIBaseType.PAYMENT_LOCATION_ID_SZ == paymentLocationId && SIBaseType.CHANGE_TYPE_ID_QUIT == changeTypeId) {
            return szQuitFilter(siChangeDetailList);
        }
        return new HashMap<Integer, SIBatch>();
    }

    /**
     * 上海新进
     * @param siChangeDetailList
     * @return
     */
    public static Map<Integer, SIBatch> shNewFilter(List<SIChangeDetail> siChangeDetailList) {
    	Map<Integer, SIBatch> siBatchMap = new HashMap<Integer, SIBatch>();
        for (SIChangeDetail siChangeDetail : siChangeDetailList) {
        	int userCode = siChangeDetail.getUserCode();
        	// 已离职, 签订实习协议期间, 不缴纳社保
        	if (isLeaved(siChangeDetail) || isShiXiSheng(siChangeDetail) || isPayingInSH(siChangeDetail)) {
        		continue;
        	}
        	// 1、入职时间是上月16日（含）至当月15日（含）[例如4月16日（含）至5月15日（含15日）缴纳月份为5月]岗位除经纪人以外的正式、试用期人员（已包含的回聘人员）
        	if (!isBroker(siChangeDetail)) {
        		if (!siBatchMap.containsKey(userCode)) {
        			siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal(), getPaymentBase(siChangeDetail, EnumPaymentLocation.上海, null)));
        		}
        	// 2、岗位是经纪人的，上海户籍（即身份证号是310开头的）的，在职状态为正式的人员（只有上海户籍才补缴，非上海户籍不补缴）
        	// 4、回聘：所有正式人员经纪人名单但是没有缴社保的 ，身份证号辨别上海户籍（身份证号310开头），入职时间是上月16日（含）至当月15日（含）[4月16日（含）至5月15日（含15日）缴纳月份为5月]
        	} else if (isBroker(siChangeDetail) && isShangHaiRen(siChangeDetail) && isFormal(siChangeDetail)) {
        		Map<String, Date> periodMap = getExtraPeriod(siChangeDetail.getNewJoinDate());
        		if (periodMap != null && !periodMap.isEmpty()) {
            		if (!siBatchMap.containsKey(userCode)) {
						siBatchMap.put(
								userCode,
								new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal(),
										getPaymentBase(siChangeDetail, EnumPaymentLocation.上海, null),
										periodMap.get("extraBeginDate"),
										periodMap.get("extraEndDate")));
            		}
        		} else {
        			if (!siBatchMap.containsKey(userCode)) {
            			siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal(), getPaymentBase(siChangeDetail, EnumPaymentLocation.上海, null)));
            		}
        		}
        		
        	// 3、所有分行经理在缴纳深圳、宁波的退出人员（系统判断，不补缴）
        	} else if (isBranchManager(siChangeDetail) && isFormal(siChangeDetail)) {
    			if (!siBatchMap.containsKey(userCode)) {
        			siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal(), getPaymentBase(siChangeDetail, EnumPaymentLocation.上海, null)));
        		}
        	// 5、所有经纪人转调后台（原先岗位为经纪人现在是非经纪人），生效时间是上月16日（含）至当月15日（含）[4月16日（含）至5月15日（含15日）缴纳月份为5月]
        	} else if (isBrokerToBackstage(siChangeDetail) && isFormal(siChangeDetail)) {
    			if (!siBatchMap.containsKey(userCode)) {
        			siBatchMap.put(userCode, new SIBatch(userCode, EnumPaymentType.公司代缴.ordinal(), getPaymentBase(siChangeDetail, EnumPaymentLocation.上海, null)));
        		}
        	}
        }
        return siBatchMap;
    }

	/**
	 * 上海退出名单生成规则<br>
	 * 1、所有离职还在缴上海的退出<br>
	 * 2、后台异动到前台的：在上海在缴名单里由其他非经纪人岗位变更为经纪人岗位<br>
	 * 3、上海自缴退出人员
	 * 
	 * @param siChangeDetailList
	 * @return
	 */
    public static Map<Integer, SIBatch> shQuitFilter(List<SIChangeDetail> siChangeDetailList) {
        Map<Integer, SIBatch> siBatchMap = new HashMap<Integer, SIBatch>();
        for (SIChangeDetail siChangeDetail : siChangeDetailList) {
        	int userCode = siChangeDetail.getUserCode();
            // 1、所有离职还在缴上海的退出
            if(isLeaved(siChangeDetail) && isPayingInSH(siChangeDetail)) {
            	if (!siBatchMap.containsKey(userCode)) {
            		siBatchMap.put(userCode, new SIBatch(siChangeDetail.getUserCode(), SIBaseType.PAYMENT_TYPE_ID_COMPANY));
            	}
            // 2、后台异动到前台的：在上海在缴名单里由其他非经纪人岗位变更为经纪人岗位
            } else if(isBackstageToBroker(siChangeDetail) && !isManagerToBroker(siChangeDetail) && isPayingInSH(siChangeDetail) && !isShangHaiRen(siChangeDetail)) {
            	if (!siBatchMap.containsKey(userCode)) {
            		siBatchMap.put(userCode, new SIBatch(siChangeDetail.getUserCode(), SIBaseType.PAYMENT_TYPE_ID_COMPANY));
            	}
            }
        }
        return siBatchMap;
    }

    /**
     * 获取宁波新进名单
     * 1、所有正式人员经纪人名单但是没有缴社保的;
     *    身份证号330开头，社保基数为2447，其他缴纳基数1470
       2、排除所有 实习生（合同类型是实习协议）
       3、深圳外包协议到期（即合同类型为前程无忧外包协议，到了合同结束时间）需从深圳退，宁波进
         例如合同结束时间为2016-10-31（合同结束时间都是月底）的即11月的社保需从深圳退，宁波进
     ************上一批次新进失败的会在下个规则中统一合并上**********
     * @since: 2014-06-13 13:41:43
     * @param siChangeDetailList
     * @return
     */
    public static Map<Integer, SIBatch> nbNewFilter(List<SIChangeDetail> siChangeDetailList) {
    	Map<Integer, SIBatch> siBatchMap = new HashMap<Integer, SIBatch>();
        for (SIChangeDetail siChangeDetail : siChangeDetailList) {
            int userCode = siChangeDetail.getUserCode();
            if (siBatchMap.containsKey(userCode)) {
                continue;
            }
            //正式，经纪人，非上海户籍，非实习生，没有在缴
            if(isFormal(siChangeDetail)
                    && isBroker(siChangeDetail)
                    && !isShangHaiRen(siChangeDetail)
                    && !isShiXiSheng(siChangeDetail)) {
                if(!isPaying(siChangeDetail)) {
                    //前程无忧协议到期
                    if(isQCWYContractEnd(siChangeDetail)) {
                        SIBatch siBatch = new SIBatch(siChangeDetail.getUserCode(), SIBaseType.PAYMENT_TYPE_ID_COMPANY, getPaymentBase(siChangeDetail, EnumPaymentLocation.宁波, siChangeDetail.getCredentialsNo()));
                        siBatch.setContractEnd(1);
                        siBatchMap.put(userCode, siBatch);
                    } else {
                        siBatchMap.put(userCode, new SIBatch(siChangeDetail.getUserCode(), SIBaseType.PAYMENT_TYPE_ID_COMPANY, getPaymentBase(siChangeDetail, EnumPaymentLocation.宁波, siChangeDetail.getCredentialsNo())));
                    }
                    //后台转前台
                } else if (isBackstageToBroker(siChangeDetail)  && !isManagerToBroker(siChangeDetail)) {
                    siBatchMap.put(userCode, new SIBatch(siChangeDetail.getUserCode(), SIBaseType.PAYMENT_TYPE_ID_COMPANY, getPaymentBase(siChangeDetail, EnumPaymentLocation.宁波, siChangeDetail.getCredentialsNo())));
                }
            }
        }
        return siBatchMap;
    }

    /**
     * 获取宁波退出名单
     * 宁波退出名单生成规则：
     1、所有离职还在缴宁波的经纪人退出
     2、所有分行经理在缴纳宁波的退出
     3、所有除营运中心以外的在缴宁波的人员退出（不分岗位）
     4、经纪人上海自缴的原先已有缴纳的需从宁波退出
     ************自缴退出会在下个规则中统一补上**********
     * @since: 2014-06-13 09:32:04
     * @param siChangeDetailList
     * @return
     */
    public static Map<Integer, SIBatch> nbQuitFilter(List<SIChangeDetail> siChangeDetailList) {
        Map<Integer, SIBatch> siBatchMap = new HashMap<Integer, SIBatch>();
        for (SIChangeDetail siChangeDetail : siChangeDetailList) {
            int userCode = siChangeDetail.getUserCode();
            if (siBatchMap.containsKey(userCode)) {
                continue;
            }
            //首要条件：宁波在缴
            if(isPayingInNB(siChangeDetail)) {
                //1、经纪人、离职
                if(isBroker(siChangeDetail) && isLeaved(siChangeDetail)) {
                    siBatchMap.put(userCode, new SIBatch(userCode, SIBaseType.PAYMENT_TYPE_ID_COMPANY));
                    //2、除经纪人以外的人员
                } else if(!isBroker(siChangeDetail)) {
                    siBatchMap.put(userCode, new SIBatch(userCode, SIBaseType.PAYMENT_TYPE_ID_COMPANY));
                }
            }

        }
        return siBatchMap;
    }

    /**
     * 获取深圳退出名单
     *   1、所有离职还在缴深圳的经纪人退出
         2、所有分行经理在缴纳深圳的退出
         3、所有除营运中心以外的在缴深圳的人员退出（不分岗位）
         5、经纪人提出自缴的原先已有缴纳的需从原先深圳退出
         4、深圳外包协议到期（即合同类型为前程无忧外包协议，到了合同结束时间）需从深圳退，宁波进
            例如合同结束时间为2016-10-31（合同结束时间都是月底）的即11月的社保需从深圳退，宁波进
     ************自缴退出会在下个规则中统一补上**********
     * @since: 2014-06-13 15:07:24
     * @param siChangeDetailList
     * @return
     */
    public static Map<Integer, SIBatch> szQuitFilter(List<SIChangeDetail> siChangeDetailList) {
    	Map<Integer, SIBatch> siBatchMap = new HashMap<Integer, SIBatch>();
        for (SIChangeDetail siChangeDetail : siChangeDetailList) {
        	int userCode = siChangeDetail.getUserCode();
            if (siBatchMap.containsKey(userCode)) {
                continue;
            }
            //首要条件：深圳在缴
            if(isPayingInSZ(siChangeDetail)) {
                //1、经纪人、离职
                if(isBroker(siChangeDetail) && isLeaved(siChangeDetail)) {
                    siBatchMap.put(userCode, new SIBatch(userCode, SIBaseType.PAYMENT_TYPE_ID_INTRUST));
                    //2、除经纪人以外的人员
                } else if(!isBroker(siChangeDetail)) {
                    siBatchMap.put(userCode, new SIBatch(userCode, SIBaseType.PAYMENT_TYPE_ID_INTRUST));
                    //3、前程无忧外包协议到期
                } else if(isQCWYContractEnd(siChangeDetail)) {
                    SIBatch siBatch = new SIBatch(userCode, SIBaseType.PAYMENT_TYPE_ID_INTRUST);
                    siBatch.setContractEnd(1);
                    siBatchMap.put(userCode, siBatch);
                }
            }

        }
        return siBatchMap;
    }

    /**
     * 合并本次过滤的名单，以及上次失败的名单
     * @since: 2014-06-17 17:23:14
     * @param siBatchMap
     * @param previousFailureList
     * @param now
     */
    public static void filterWithPrevious(Map<Integer,SIBatch> siBatchMap, List<SIBatch> previousFailureList, Calendar now) {
        if(CollectionUtils.isEmpty(previousFailureList)) {
            return;
        }
        for (SIBatch lastSIbatch : previousFailureList) {
            int userCode = lastSIbatch.getUserCode();
            //如果没有包含在Map中，就加入一条
            if(!siBatchMap.containsKey(userCode)) {
                siBatchMap.put(userCode, lastSIbatch);
            //如果已经存在map中，那么就要合并两条数据
            //合并：补缴开始时间、补缴结束时间、要求参保起始时间
            } else {
                SIBatch siFromRule = siBatchMap.get(userCode);
                Calendar lastMonth = now;
                lastMonth.add(Calendar.MONTH, 1);
                // 设置补缴开始时间
                if(lastSIbatch.getExtraBeginDate() != null) {
                    siFromRule.setExtraBeginDate(lastSIbatch.getExtraBeginDate());
                } else if (siFromRule.getExtraBeginDate() == null) {
                    siFromRule.setExtraBeginDate(lastMonth.getTime());
                }
                // 设置补缴结束时间
                siFromRule.setExtraBeginDate(lastMonth.getTime());
                //设置要求参保起始时间
                if(lastSIbatch.getRequireDate() != null) {
                    siFromRule.setRequireDate(lastSIbatch.getRequireDate());
                }
                siBatchMap.put(userCode, siFromRule);
            }
        }
    }

    /**
     * 合并本次过滤的名单，以及本月离职回聘的人员
     * @since: 2014-06-23 16:47:40
     * @param siBatchMap
     * @param leaveReturnList
     * @param paymentLocationId
     * @param changeTypeId
     */
    public static void filterWithLeaveReturn(Map<Integer, SIBatch> siBatchMap
            , List<SIChangeDetail> leaveReturnList
            , int paymentLocationId, int changeTypeId) {
        if(CollectionUtils.isEmpty(leaveReturnList)) {
            return;
        }
        for(SIChangeDetail leaveReturn : leaveReturnList) {
            int userCode = leaveReturn.getUserCode();
            //不存在本批次中
            if(!siBatchMap.containsKey(userCode)) {
                //深圳退出批次、在缴深圳的退出
                if(EnumPaymentLocation.深圳.ordinal() == paymentLocationId
                        && EnumChangeType.退出.ordinal() == changeTypeId
                        && isPayingInSZ(leaveReturn)) {
                    siBatchMap.put(userCode, new SIBatch(userCode, SIBaseType.PAYMENT_TYPE_ID_INTRUST));
                    //宁波新进批次、未缴宁波的新进（不考虑在缴上海的）
                } else if(EnumPaymentLocation.宁波.ordinal() == paymentLocationId
                        && EnumChangeType.新进.ordinal() == changeTypeId
                        && !isPayingInNB(leaveReturn)
                        && !isPayingInSH(leaveReturn)) {
                    if(isBroker(leaveReturn) && (!isFormal(leaveReturn))) {
                        continue;
                    } else if(isShiXiSheng(leaveReturn)) {
                        continue;
                    } else {
                        siBatchMap.put(userCode, new SIBatch(leaveReturn.getUserCode(), SIBaseType.PAYMENT_TYPE_ID_COMPANY, getPaymentBase(leaveReturn, EnumPaymentLocation.宁波, leaveReturn.getCredentialsNo())));
                    }
                }
            }
        }
    }

    /**
     * 判断员工状态是否为正式
     * @since: 2014-06-09 09:29:09
     * @param siChangeDetail
     * @return
     */
    public static boolean isFormal(SIChangeDetail siChangeDetail) {
        return EmployeeStatus.Formal.toString().equals(siChangeDetail.getNewStatus());
    }

    /**
     * 判断员工状态是否为离职
     * @since: 2014-06-13 09:28:36
     * @param siChangeDetail
     * @return
     */
    public static boolean isLeaved(SIChangeDetail siChangeDetail) {
        return EmployeeStatus.Leaved.toString().equals(siChangeDetail.getNewStatus());
    }

    /**
     * 判断员工职位是否为经纪人
     * @since: 2014-06-09 09:34:59
     * @param siChangeDetail
     * @return
     */
    public static boolean isBroker(SIChangeDetail siChangeDetail) {
        return Position.BROKER_POSITION_ID == siChangeDetail.getNewPositionId();
    }

    /**
     * 判断是否分行经理降职为经纪人岗位
     * @since: 2014-06-24 10:15:49
     * @param siChangeDetail
     * @return
     */
    public static boolean isManagerToBroker(SIChangeDetail siChangeDetail) {
        return siChangeDetail.getOldPositionId() != null
                && siChangeDetail.getOldPositionId() == Position.BRANCH_MANAGER_ID
                && Position.BROKER_POSITION_ID == siChangeDetail.getNewPositionId();
    }

    /**
     * 判断由其他非经纪人岗位变更为经纪人岗位
     * @param siChangeDetail
     * @return
     */
	public static boolean isBackstageToBroker(SIChangeDetail siChangeDetail) {
		return siChangeDetail.getOldPositionId() != null && Position.BROKER_POSITION_ID != siChangeDetail.getOldPositionId().intValue()
				&& Position.BROKER_POSITION_ID == siChangeDetail.getNewPositionId();
	}

    /**
     * 判断由经纪人岗位变更为其他非经纪人岗位
     * @param siChangeDetail
     * @return
     */
	public static boolean isBrokerToBackstage(SIChangeDetail siChangeDetail) {
		return siChangeDetail.getOldPositionId() != null && Position.BROKER_POSITION_ID == siChangeDetail.getOldPositionId().intValue()
				&& Position.BROKER_POSITION_ID != siChangeDetail.getNewPositionId();
	}

    /**
     * 判断员工职位是否为分行经理
     * @since: 2014-06-13 09:40:41
     * @param siChangeDetail
     * @return
     */
    public static boolean isBranchManager(SIChangeDetail siChangeDetail) {
        return Position.BRANCH_MANAGER_ID == siChangeDetail.getNewPositionId();
    }

    /**
     * 判断员工社保是否为在缴状态
     * @since: 2014-06-09 10:13:33
     * @param siChangeDetail
     * @return
     */
    public static boolean isPaying(SIChangeDetail siChangeDetail) {
        return SIChangeDetail.PAYMENT_STATUS_PAYING == siChangeDetail.getPaymentStatus();
    }

    /**
     * 判断是否上海在缴状态
     * @param siChangeDetail
     * @return
     */
    public static boolean isPayingInSH(SIChangeDetail siChangeDetail) {
        return SIBaseType.PAYMENT_LOCATION_ID_SH == siChangeDetail.getPaymentLocationId();
    }

    /**
     * 判断是否宁波在缴状态
     * @since: 2014-06-12 14:49:07
     * @param siChangeDetail
     * @return
     */
    public static boolean isPayingInNB(SIChangeDetail siChangeDetail) {
        return SIBaseType.PAYMENT_LOCATION_ID_NB == siChangeDetail.getPaymentLocationId();
    }

    /**
     * 判断是否深圳在缴状态
     * @since: 2014-06-12 14:49:07
     * @param siChangeDetail
     * @return
     */
    public static boolean isPayingInSZ(SIChangeDetail siChangeDetail) {
        return SIBaseType.PAYMENT_LOCATION_ID_SZ == siChangeDetail.getPaymentLocationId();
    }

    /**
     * 判断员工身份证是否310开头
     * @since: 2014-06-13 13:50:50
     * @param siChangeDetail
     * @return
     */
    public static boolean isBeginWith310(SIChangeDetail siChangeDetail) {
        return siChangeDetail.getCredentialsNo().startsWith(ID_CARD_PRE_310);
    }

    /**
     * 判断员工是不是上海人（身份证是否310开头）
     * @since: 2014-06-13 13:50:50
     * @param siChangeDetail
     * @return
     */
    public static boolean isShangHaiRen(SIChangeDetail siChangeDetail) {
        return isBeginWith310(siChangeDetail);
    }

    /**
     * 判断员工身份证是否330开头
     * @since: 2014-06-13 13:50:50
     * @param siChangeDetail
     * @return
     */
    public static boolean isBeginWith330(SIChangeDetail siChangeDetail) {
        return isBeginWith330(siChangeDetail.getCredentialsNo());
    }

    /**
     * 判断员工身份证是否330开头
     * @since: 2014-06-13 13:50:50
     * @param credentialsNo
     * @return
     */
    public static boolean isBeginWith330(String credentialsNo) {
        return credentialsNo.startsWith(ID_CARD_PRE_330);
    }

    /**
     * 判断员工是否为实习生
     * @since: 2014-06-13 14:08:28
     * @param siChangeDetail
     * @return
     */
    public static boolean isShiXiSheng(SIChangeDetail siChangeDetail) {
        return CONTRACT_TYPE_SXXY.equals(siChangeDetail.getNewContractType());
    }

    /**
     * 判断员工合同是否为前程无忧外包协议
     * @since: 2014-06-13 14:08:28
     * @param siChangeDetail
     * @return
     */
    public static boolean isContractTypeQCWYWBXY(SIChangeDetail siChangeDetail) {
        return CONTRACT_TYPE_QCWYWBXY.equals(siChangeDetail.getNewContractType());
    }

    /**
     * 判断员工是否前程无忧合同到期
     * @since: 2014-06-13 15:21:15
     * @param siChangeDetail
     * @return
     */
    public static boolean isQCWYContractEnd(SIChangeDetail siChangeDetail) {
        return CONTRACT_TYPE_QCWYWBXY.equals(siChangeDetail.getOldContractType())
                && !CONTRACT_TYPE_QCWYWBXY.equals(siChangeDetail.getNewContractType());
    }

    /**
     * 根据职系, 职等取得上海社保基数
     * @param siChangeDetail
     * @return
     */
    public static int getPaymentBase(SIChangeDetail siChangeDetail, EnumPaymentLocation enumPaymentEnum, String credentialsNo) {
    	int paymentBase = 0;
    	if (enumPaymentEnum.equals(EnumPaymentLocation.上海)) {
        	int serialId = siChangeDetail.getNewSerialId();
        	int titleDegree = siChangeDetail.getNewTitleDegree();
        	// 营销业务职系所有职等（上海人）、行政技术职系1、2职等、行政管理职系2职等、营销职系（代理部）1职等（上海人）
    		if (EnumSerial.营销业务职系.ordinal() == serialId
    				|| (EnumSerial.行政技术职系.ordinal() == serialId && titleDegree <= 2)
    				|| (EnumSerial.行政管理职系.ordinal() == serialId && titleDegree <= 2)
    				|| (EnumSerial.营销职系_代理部.ordinal() == serialId && titleDegree == 1)) {
    			paymentBase = 3022;
    		// 营销管理职系2职等、营销职系（代理部）2职等
    		} else if ((EnumSerial.营销管理职系.ordinal() == serialId && titleDegree == 2)
    				|| (EnumSerial.营销职系_代理部.ordinal() == serialId && titleDegree == 2)) {
    			paymentBase = 4050;
    		// 行政管理职系3职等、行政技术职系3职等
        	} else if ((EnumSerial.行政管理职系.ordinal() == serialId && titleDegree == 3)
    				|| (EnumSerial.行政技术职系.ordinal() == serialId && titleDegree == 3)) {
        		paymentBase = 5050;
        	// 行政管理职系4职等、行政技术4职等、营销管理职系3职等、营销职系（代理部）3职等
        	} else if ((EnumSerial.行政管理职系.ordinal() == serialId && titleDegree == 4)
    				|| (EnumSerial.行政技术职系.ordinal() == serialId && titleDegree == 4)
    				|| (EnumSerial.营销管理职系.ordinal() == serialId && titleDegree == 3)
    				|| (EnumSerial.营销职系_代理部.ordinal() == serialId && titleDegree == 3)) {
        		paymentBase = 10100;
        	// 行政管理职系5职等、行政技术职系5职等、营销管理职系4职等、营销职系（代理部）4职等及以上
        	} else if ((EnumSerial.行政管理职系.ordinal() == serialId && titleDegree >= 5)
    				|| (EnumSerial.行政技术职系.ordinal() == serialId && titleDegree >= 5)
    				|| (EnumSerial.营销管理职系.ordinal() == serialId && titleDegree >= 4)
    				|| (EnumSerial.营销职系_代理部.ordinal() == serialId && titleDegree >= 4)) {
        		paymentBase = 15108;
        	}
    	} else if (enumPaymentEnum.equals(EnumPaymentLocation.宁波)) {
    		paymentBase = isBeginWith330(credentialsNo) ? 2447 : 1470;
    	}
    	return paymentBase;
    }

    /**
     * 根据入职日期, 返回补缴周期, 本月不在补缴周期内
     * @param newJoinDate
     * @return
     */
    public static Map<String, Date> getExtraPeriod(Date newJoinDate) {
    	Map<String, Date> extraPeriodMap = new HashMap<String, Date>();
    	Calendar tmpCalendar = Calendar.getInstance();
    	tmpCalendar.setTime(newJoinDate);
    	if (tmpCalendar.get(Calendar.DAY_OF_MONTH) > 15) {
    		tmpCalendar.add(Calendar.MONTH, 1);
    	}
    	tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
    	Calendar now = Calendar.getInstance();
    	now.set(Calendar.DAY_OF_MONTH, 1);
    	now.set(Calendar.HOUR_OF_DAY, 0);
    	now.set(Calendar.MINUTE, 0);
    	now.set(Calendar.SECOND, 0);
    	now.set(Calendar.MILLISECOND, 0);
    	if (now.after(tmpCalendar)) {
        	now.add(Calendar.MONTH, -1);
    		extraPeriodMap.put("extraBeginDate", tmpCalendar.getTime());
    		extraPeriodMap.put("extraEndDate", now.getTime());
    	}
    	return extraPeriodMap;
    }

    /**
     * 获取导出excel名称
     * @since: 2014-06-20 11:39:01
     * @param siBatchInfo
     * @return
     */
    public static String getExportFileName(SIBatchInfo siBatchInfo) throws Exception{
        String name = "";
        if(siBatchInfo.getPaymentLocationId() == EnumPaymentLocation.上海.ordinal()) {
            //"201405上海新进人员名单";
            name = DateFormatUtils.format(siBatchInfo.getPaymentDate(), "yyyyMM")
                    + EnumPaymentLocation.上海.name()
                    + (siBatchInfo.getChangeTypeId() == EnumChangeType.新进.ordinal() ? EnumChangeType.新进.name() : EnumChangeType.退出.name())
                    + "人员名单";
        } else if (siBatchInfo.getPaymentLocationId() == EnumPaymentLocation.宁波.ordinal()) {
            //201405德佑社保缴纳名单（新进）
            name = DateFormatUtils.format(siBatchInfo.getPaymentDate(), "yyyyMM")
                    + "德佑社保缴纳名单（"
                    + (siBatchInfo.getChangeTypeId() == EnumChangeType.新进.ordinal() ? EnumChangeType.新进.name() : EnumChangeType.退出.name())
                    + "）";
        } else if (siBatchInfo.getPaymentLocationId() == EnumPaymentLocation.深圳.ordinal()) {
            //德佑人员增减表-合派20140407-152341
            name = "德佑人员增减表-合派"
                    + DateFormatUtils.format(siBatchInfo.getPaymentDate(), "yyyyMMdd-HHmmss");
        }

        return URLEncoder.encode(name, "UTF-8");
    }

}
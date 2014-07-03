/**
 * 
 */
package com.gusi.socialInsurance.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gusi.boms.common.Constants;
import com.gusi.socialInsurance.service.SIBatchInfoService;

import java.util.Date;

/**
 * @author Alex Yu
 * @Created 2014年6月16日下午1:45:15
 */
@Component
public class JobSiShBatch {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private SIBatchInfoService siBatchInfoService;

	/**
	 * 生成上海新进名单
	 */
	@Scheduled(cron = "0 59 23 15 * ?")
	public void generateShNewList() {
		try {
			log.info("生成本月上海社保新进名单: >>>>>>>>>>>>>>>>>>>>>>Begin<<<<<<<<<<<<<<<<<<<<");
			siBatchInfoService.generateSHNew(Constants.GUANLIYUAN, new Date());
			log.info("生成本月上海社保新进名单: >>>>>>>>>>>>>>>>>>>>>>End<<<<<<<<<<<<<<<<<<<<");
		} catch (Exception ex) {
			log.error("生成本月上海社保新进名单失败. " + ex.getMessage(), ex);
		}
	}

	/**
	 * 生成上海退出第一批次名单
	 */
	@Scheduled(cron = "0 59 23 10 * ?")
	public void generateShQuitListPartOne() {
		try {
			log.info("生成本月第一批上海社保退出名单: >>>>>>>>>>>>>>>>>>>>>>Begin<<<<<<<<<<<<<<<<<<<<");
			siBatchInfoService.generateSHQuit(Constants.GUANLIYUAN, new Date());
			log.info("生成本月第一批上海社保退出名单: >>>>>>>>>>>>>>>>>>>>>>End<<<<<<<<<<<<<<<<<<<<");
		} catch (Exception ex) {
			log.error("生成本月第一批上海社保退出名单失败. " + ex.getMessage(), ex);
		}
	}

	/**
	 * 生成上海退出第二批次名单
	 */
	@Scheduled(cron = "0 59 23 15 * ?")
	public void generateShQuitListPartTwo() {
		try {
			log.info("生成本月第二批上海社保退出名单: >>>>>>>>>>>>>>>>>>>>>>Begin<<<<<<<<<<<<<<<<<<<<");
			siBatchInfoService.generateSHQuit(Constants.GUANLIYUAN, new Date());
			log.info("生成本月第二批上海社保退出名单: >>>>>>>>>>>>>>>>>>>>>>End<<<<<<<<<<<<<<<<<<<<");
		} catch (Exception ex) {
			log.error("生成本月第二批上海社保退出名单失败. " + ex.getMessage(), ex);
		}
	}
}

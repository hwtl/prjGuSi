package com.gusi.activemq.sender;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gusi.boms.helper.MailHelper;
import com.dooioo.dymq.annotation.ActivemqSender;
import com.dooioo.dymq.model.SalaryMessage;
import com.dooioo.dymq.sender.common.QueueAbstractSender;

/**
 * @author "liuhui" 
 * @since 3.0.4
 * @createAt 2014-4-2 下午3:01:12
 * <p>
 *   奖惩发送消息记录
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@Service
@ActivemqSender("boms.employee.rewardpunish")
public class EmployeeRewardPunishSender extends QueueAbstractSender{
	private static Logger logger=Logger.getLogger(EmployeeRewardPunishSender.class);
	@Autowired
	private MailHelper mailHelper;
	@Override
	protected void log(String destinationName, String message) {
		logger.info("destination:"+destinationName);
		logger.info("messageStr："+message);
	}
	/**
	  * @since: 3.0.4 
	  * @param salaryMessage
	  * <p>
	  *  奖惩发送扣除工资的信息。
	  * </p>   
	 */
	public void sendMessage(SalaryMessage salaryMessage){
		try {
			super.sendMessage(salaryMessage);
		} catch (Exception e) {
			logger.error("奖惩发送MQ消息异常，"+salaryMessage,e);
			mailHelper.sendMQErrorMessage("奖惩发送MQ消息异常，"+salaryMessage);
		}
	}

	
}



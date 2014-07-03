package com.gusi.activemq.sender;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gusi.boms.helper.MailHelper;
import com.dooioo.dymq.annotation.ActivemqSender;
import com.dooioo.dymq.model.AccountingDeptMessage;
import com.dooioo.dymq.sender.common.VirtualAbstractSender;

/**
 * @author "liuhui" 
 * @since 3.0.5
 * <p>
 *   核算组织新增
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@Service
@ActivemqSender("boms.accountingdept.add")
public class AccountingDeptAddSender extends VirtualAbstractSender{
	private static Logger logger=Logger.getLogger(EmployeeRewardPunishSender.class);
	@Autowired
	private MailHelper mailHelper;
	@Override
	protected void log(String destinationName, String message) {
		logger.info("destination:"+destinationName);
		logger.info("messageStr："+message);
	}
	/**
	  * @since: 3.0.5
	  * @param accountingDeptMessage 核算部门新增消息。
	  * <p>
	  *  核算组织新增发送MQ。
	  * </p>   
	 */
	public void sendMessage(int accountingId,String accountingOrgName,
			int orgId,String orgName,String orgType,Integer oldId){
		AccountingDeptMessage msg=new AccountingDeptMessage(accountingId, accountingOrgName, orgId, orgName,orgType);
		msg.setOldId(oldId);
		try {
			super.sendMessage(msg);
		} catch (Exception e) {
			logger.error("核算组织新增发送MQ消息异常，"+msg,e);
			mailHelper.sendMQErrorMessage("核算组织新增发送MQ消息异常，"+msg);
		}
	}

	
}



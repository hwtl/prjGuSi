package com.gusi.activemq.sender;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gusi.boms.helper.MailHelper;
import com.dooioo.dymq.annotation.ActivemqSender;
import com.dooioo.dymq.model.OrganizationMessage;
import com.dooioo.dymq.sender.common.VirtualAbstractSender;
/**
 * @author "liuhui" 
 * @since 2.0.0
 * @createAt 2014-6-4 上午10:16:37
 * <p>
 *  组织暂停
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@Service
@ActivemqSender("boms.organization.pause")
public class OrganizationPauseSender extends VirtualAbstractSender {

    private Logger logger = Logger.getLogger(OrganizationBianJi.class);
    @Autowired
	private MailHelper mailHelper;
    @Override
    protected void log(String destinationName, String messageStr) {
        logger.info("send message to " + destinationName);
        logger.info("messageStr : " + messageStr);
    }
    /**
     *	<p>
     * 发送MQ消息，异常时发送邮件
     * </p>
     * @since: 2.0.0
     * @param orgMsg    
    */
    public void sendMessage(int orgId,String orgName,Integer parentId){
    	OrganizationMessage orgMsg=new OrganizationMessage(orgId, orgName, parentId);
   	try {
			super.sendMessage(orgMsg);
		} catch (Exception e) {
			logger.error("组织暂停时候发送MQ消息异常，"+orgMsg,e);
			mailHelper.sendMQErrorMessage("组织暂停时候发送MQ消息异常，"+orgMsg);
		}
   }
}

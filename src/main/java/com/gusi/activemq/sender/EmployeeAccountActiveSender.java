package com.gusi.activemq.sender;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dooioo.dymq.annotation.ActivemqSender;
import com.dooioo.dymq.model.EmployeeMessage;
import com.dooioo.dymq.sender.common.VirtualAbstractSender;


/**
 * @author "liuhui" 
 * @since 3.1.2
 * @createAt 2014-4-29 下午2:41:13
 * <p>
 * 员工激活的时候发送消息
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@Service
@ActivemqSender("boms.employee.account.active")
public class EmployeeAccountActiveSender extends VirtualAbstractSender {

    private Logger log = Logger.getLogger(EmployeeAccountActiveSender.class);

    @Override
    protected void log(String destinationName, String messageStr) {
        log.info("send message to " + destinationName);
        log.info("messageStr : " + messageStr);
    }
   /**
	  * @since: 3.1.2 
	  * @param em
	  * <p>
	  *  员工暂停发送消息
	  * </p>   
	 */
   public void sendMessage(EmployeeMessage em){
	   try {
			super.sendMessage(em);
		} catch (Exception e) {
			log.error("员工取消暂停发送MQ消息异常，"+em,e);
		}
   }
}

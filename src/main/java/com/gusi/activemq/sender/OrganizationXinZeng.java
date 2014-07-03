package com.gusi.activemq.sender;

import com.dooioo.dymq.annotation.ActivemqSender;
import com.dooioo.dymq.sender.common.VirtualAbstractSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-19 下午1:55
 * @Description: 组织新增消息发送类
 * To change this template use File | Settings | File Templates.
 */
@Service
@ActivemqSender("boms.organization.xinzeng")
public class OrganizationXinZeng extends VirtualAbstractSender {

    private Log log = LogFactory.getLog(OrganizationXinZeng.class);

    @Override
    protected void log(String destinationName, String messageStr) {
        log.info("send message to " + destinationName);
        log.info("messageStr : " + messageStr);
    }

}

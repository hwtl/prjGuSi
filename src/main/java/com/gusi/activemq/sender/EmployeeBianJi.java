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
 * @Create: 13-10-22 上午10:05
 * @Description: 员工编辑发送mq消息类
 * To change this template use File | Settings | File Templates.
 */
@Service
@ActivemqSender("boms.employee.bianji")
public class EmployeeBianJi extends VirtualAbstractSender {

    private Log log = LogFactory.getLog(EmployeeBianJi.class);

    @Override
    protected void log(String destinationName, String messageStr) {
        log.info("send message to " + destinationName);
        log.info("messageStr : " + messageStr);
    }

}

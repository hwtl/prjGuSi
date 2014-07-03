package com.gusi.activemq.sender;

import com.dooioo.dymq.annotation.ActivemqSender;
import com.dooioo.dymq.sender.common.VirtualAbstractSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 员工兼职变动发送mq消息类
 */
@Service
@ActivemqSender("boms.employee.parttimePositionChange")
public class EmployeeParttimePositionChange extends VirtualAbstractSender {

    private Log log = LogFactory.getLog(EmployeeParttimePositionChange.class);

    @Override
    protected void log(String destinationName, String messageStr) {
        log.info("send message to " + destinationName);
        log.info("messageStr : " + messageStr);
    }

}

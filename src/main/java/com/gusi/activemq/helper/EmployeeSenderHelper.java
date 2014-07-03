package com.gusi.activemq.helper;

import com.gusi.activemq.sender.EmployeeBianJi;
import com.gusi.activemq.sender.EmployeeParttimePositionChange;
import com.gusi.activemq.sender.EmployeeXinZeng;
import com.gusi.activemq.sender.EmployeeYiDong;
import com.gusi.boms.helper.MailHelper;
import com.dooioo.dymq.model.EmployeeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: fdj
 * @Create: 13-10-22 下午1:29
 * @Description: mq发送端辅助类
 * 涉及到员工发送mq消息的地方目前为：
 * 1、员工处理报到完成后，会发送新增消息
 * 2、员工基础信息编辑后，会发送编辑消息
 * 3、员工任何异动的方法，都会发送异动消息；
 *    员工回聘异动的时候会发送异动、编辑消息（由于行业经验的变化，交易管家需求）；
 *    员工任何异动回滚是，也会发送异动消息
 */
@Component
public class EmployeeSenderHelper {

    private static final Log LOG = LogFactory.getLog(EmployeeSenderHelper.class);

    @Autowired
    private EmployeeXinZeng employeeXinZeng;
    @Autowired
    private EmployeeBianJi employeeBianJi;
    @Autowired
    private EmployeeYiDong employeeYiDong;
    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private EmployeeParttimePositionChange employeeParttimePositionChange;
    /**
     * 发送新增mq消息
     * @param employeeMessage 消息类
     */
    public void sendEmployeeXinZeng(EmployeeMessage employeeMessage) {
        try {
            employeeXinZeng.sendMessage(employeeMessage);
        } catch (Exception e) {
            LOG.error("发送新增mq消息失败!"+employeeMessage, e);
            mailHelper.sendMQErrorMessage(employeeMessage.toString());
        }
    }

    /**
     * 发送新增mq消息
     * @param userCode 员工工号
     * @param orgId    员工部门id
     */
    @Async
    public void sendEmployeeParttimePositionChange(int userCode, int orgId) {
        EmployeeMessage employeeMessage = new EmployeeMessage();
        employeeMessage.setUserCode(userCode);
        employeeMessage.setOrgId(orgId);
        employeeMessage.setActiveDate(new Date());
        try {
			employeeParttimePositionChange.sendMessage(employeeMessage);
		} catch (Exception e) {
			  LOG.error("兼职职位变动发送mq消息失败!"+employeeMessage, e);
	            mailHelper.sendMQErrorMessage(employeeMessage.toString());
		}
    }
    /**
     * 发送新增mq消息
     * @param userCode 员工工号
     * @param orgId    员工部门id
     */
    public void sendEmployeeXinZeng(int userCode, int orgId) {
        EmployeeMessage employeeMessage = new EmployeeMessage();
        employeeMessage.setUserCode(userCode);
        employeeMessage.setOrgId(orgId);
        this.sendEmployeeXinZeng(employeeMessage);
    }

    /**
     * 发送编辑mq消息
     * @param employeeMessage 消息类
     */
    public void sendEmployeeBianJi(EmployeeMessage employeeMessage) {
        try {
            employeeBianJi.sendMessage(employeeMessage);
        } catch (Exception e) {
            LOG.error("发送编辑mq消息失败!"+employeeMessage, e);
            mailHelper.sendMQErrorMessage(employeeMessage.toString());
        }
    }

    /**
     * 发送编辑mq消息
     * @param userCode 员工工号
     * @param orgId    员工部门id
     */
    public void sendEmployeeBianJi(int userCode, int orgId) {
        EmployeeMessage employeeMessage = new EmployeeMessage();
        employeeMessage.setUserCode(userCode);
        employeeMessage.setOrgId(orgId);
        this.sendEmployeeBianJi(employeeMessage);
    }

    /**
     * 发送异动mq消息
     * @param employeeMessage 消息类
     */
    public void sendEmployeeYiDong(EmployeeMessage employeeMessage) {
        try {
            employeeYiDong.sendMessage(employeeMessage);
        } catch (Exception e) {
            LOG.error("发送异动mq消息失败!"+employeeMessage, e);
            mailHelper.sendMQErrorMessage(employeeMessage.toString());
        }
    }

    /**
     * 发送异动mq消息
     * @param userCode      员工工号
     * @param activeDate    异动生效时间
     * @param orgId         员工异动生效之前的部门id
     */
    public void sendEmployeeYiDong(int userCode, Date activeDate, int orgId) {
        EmployeeMessage employeeMessage = new EmployeeMessage();
        employeeMessage.setUserCode(userCode);
        employeeMessage.setActiveDate(activeDate);
        employeeMessage.setOrgId(orgId);
        this.sendEmployeeYiDong(employeeMessage);
    }

}

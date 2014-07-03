package com.gusi.boms.helper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dooioo.plus.util.GlobalConfigUtil;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.model.EmployeeChangeRecords;
import com.gusi.boms.model.OrganizationEmployeeCount;
import com.gusi.boms.util.MailUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.plus.util.HttpClientUtil;
import com.dooioo.webplus.httpclient.HttpClientUtils;

import freemarker.template.TemplateException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-11-20 下午3:20
 * @Description: 发邮件辅助类
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MailHelper {

    public static final String ADD_EMPLOYEE_TITLE = "【人力资源管理系统】分行人数提醒邮件" + (GlobalConfigUtil.isProductionEnv() ? "" : GlobalConfigUtil.getCurrentEnv());

    private static final Log log = LogFactory.getLog(MailHelper.class);
    private static final String ROLLBACKMAIL_TITLE = "【人力资源管理系统】员工异动回滚提醒邮件" + (GlobalConfigUtil.isProductionEnv() ? "" : GlobalConfigUtil.getCurrentEnv());
    private static final String ORGEMPCOUNT_TITLE = "【人力资源管理系统】增加编制到期提醒邮件" + (GlobalConfigUtil.isProductionEnv() ? "" : GlobalConfigUtil.getCurrentEnv());
    private static final String MQMESSAGE_TITLE = "【人力资源管理系统】MQ消息发送失败提醒邮件" + (GlobalConfigUtil.isProductionEnv() ? "" : GlobalConfigUtil.getCurrentEnv());
    private static final String AD_TITLE = "【人力资源管理系统】AD同步提醒邮件" + (GlobalConfigUtil.isProductionEnv() ? "" : GlobalConfigUtil.getCurrentEnv());
    private static final String EMAIL_ADDRESS = "@dooioo.com";
    private static final String MAIL_URL = "http://youjian.dooioo.com/lms/send";
    private static final String DEFAUT_USERCODE = "90378";

    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeService employeeService;


    /**
     * 发邮件
     * @param subject 主题
     * @param content 内容
     * @param receivers 收件人工号，用逗号隔开。如：90378,89695
     */
    @Async
    public void sendMail(String subject,String content, String receivers) {
        Map<String,String> params=new HashMap<String, String>();
        params.put("subject",subject);
        params.put("content", content);
        params.put("receivers", receivers);
        HttpClientUtil.doPost(MAIL_URL, params);
    }

    @Async
    public void sendADMail(String content) {
        try {
            Map<String,String> param = new HashMap<String, String>();
            param.put("subject", AD_TITLE);
            param.put("content", content);
            param.put("receivers", DEFAUT_USERCODE);
            param.put("signature", "这个工号同步AD有问题。");
            HttpClientUtils.doPost(MAIL_URL, param);
        } catch (IOException e) {
            log.error("发送邮件失败！", e);
        }
        log.info("发送邮件成功！");
    }

    /**
     * 发送MQ消息失败邮件
     * @param content 邮件内容
     */
    public void sendMQErrorMessage(String content) {
        try {
            List<String> list = new ArrayList<>();
            list.add(90378 + EMAIL_ADDRESS);
            list.add(89695 + EMAIL_ADDRESS);
            MailUtil.sendMail(list, null, MQMESSAGE_TITLE, content);
        } catch (Exception e) {
            log.error("发送【发送MQ消息失败邮件】失败！",e);
        } 
    }

    /**
     * 发送异动回滚邮件
     * @param content 邮件内容
     */
    public void sendRollBackMail(String content) {
        try {
            MailUtil.sendMail(getRollBackReceivers(), null,  ROLLBACKMAIL_TITLE, content);
        } catch (Exception e) {
            log.error("发送【员工异动回滚提醒邮件】失败！",e);
        } 
    }

    /**
	  * @since: 1.0.0 
	  * @param e
	  * @return
	  * <p>
	  *  打印堆栈信息
	  * </p>   
	 */
	 private String stackTrace2Str(Throwable e){
		StringWriter sw=new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}	

	/**
	  * @since: 3.0.5 
	  * @param subject 邮件主题
	  * @param content 邮件内容
	  * <p>
	  *  发送邮件
	  * </p>   
	 */
	public void sendMail(String subject,String content)
	{
        this.sendMail(subject, content, Configuration.getInstance().getMailReceiveUsers().replace(";",","));
		/*Map<String,String> params=new HashMap<String, String>();
		params.put("subject",subject);
		params.put("content", content);
		params.put("receivers",Configuration.getInstance().getMailReceiveUsers().replace(";",","));
		HttpClientUtil.doPost(MAIL_URL, params);*/
	}

	/**
	  * @since: 3.0.5 
	  * @param subject
	  * @param e 堆栈信息
	  * <p>
	  *  发送邮件
	  * </p>   
	 */
	@Async
	public void sendMail(String subject,Throwable e)
	{
	  sendMail(subject, stackTrace2Str(e));
	}
    /**
     * 发送异动回滚邮件
     * @param employeeChangeRecords 异动记录对象
     */
    public void sendRollBackMail(EmployeeChangeRecords employeeChangeRecords, Integer updator) {
        try {
            MailUtil.sendMail(getRollBackReceivers(), null,  ROLLBACKMAIL_TITLE, getRollbackmailContent(employeeChangeRecords, updator));
        } catch (Exception e) {
            log.error("发送【员工异动回滚提醒邮件】失败！",e);
        } 
    }

    /**
     * 获得异动回滚需要通知的员工集合
     * @return
     */
    private List<String> getRollBackReceivers() {
        List<String> list = new ArrayList<>();
        String[] userCodeArr = Configuration.getInstance().getMailReceiveUsers().split(";");
        for(String userCode : userCodeArr) {
            list.add(userCode + EMAIL_ADDRESS);
        }
        return list;
    }

    /**
     * 获取发送回滚消息的邮件内容
     * @param employeeChangeRecords
     * @return
     */
    private String getRollbackmailContent(EmployeeChangeRecords employeeChangeRecords, Integer updator) {
        Map<String, Object> param = new HashMap<>();
        param.put("change", employeeChangeRecords);
        param.put("updator", updator);
        param.put("now", new Date());
        String temp = "";
        try {
            temp = freemarkerUtil.writeTemplate(File.separator + "eMail" + File.separator +"rollBackTemplet.ftl", param);
        } catch (TemplateException e) {
            log.error("获取发送回滚消息的邮件内容",e);
        } catch (IOException e) {
            log.error("获取发送回滚消息的邮件内容",e);
        } 
        return temp;
    }

    /**
     * 发送分行编制到期提醒邮件
     * @param organizationEmployeeCountList
     */
    public boolean sendOrgEmployeeCountMail(List<OrganizationEmployeeCount> organizationEmployeeCountList) {
        int num = organizationEmployeeCountList.size();
        if(organizationEmployeeCountList != null && num > 0) {
            for (OrganizationEmployeeCount organizationEmployeeCount : organizationEmployeeCountList) {
                try {
                    MailUtil.sendMail(getOrgEmployeeCountMailReceivers(organizationEmployeeCount), null , ORGEMPCOUNT_TITLE, getOrgEmployeeCountMailContent(organizationEmployeeCount));
                    num --;
                } catch (Exception e) {
                    log.error("发送邮件报错！", e);
                } 
            }
        }
        return num == 0;

    }

    /**
     * 获取发送分行员工编制到期提醒消息的邮件内容
     * @return
     */
    private String getOrgEmployeeCountMailContent(OrganizationEmployeeCount organizationEmployeeCount) {
        Map<String, Object> param = new HashMap<>();
        param.put("organization", organizationService.getOrgByOrgId(organizationEmployeeCount.getOrgId()));
        param.put("orgEmployeeCount", organizationEmployeeCount);
        String temp = "";
        try {
            temp = freemarkerUtil.writeTemplate(File.separator + "eMail" + File.separator +"orgEmpCountTemplet.ftl", param);
        } catch (TemplateException e) {
            log.error(" 获取发送分行员工编制到期提醒消息的邮件内容",e);
        } catch (IOException e) {
            log.error(" 获取发送分行员工编制到期提醒消息的邮件内容",e);
        } 
       return temp;
    }

    /**
     * 获得组织编制到期需要通知的员工集合
     * @return
     */
    private List<String> getOrgEmployeeCountMailReceivers(OrganizationEmployeeCount organizationEmployeeCount) {
        List<String> list = new ArrayList<>();
        if(organizationEmployeeCount.getCreator() != null) {
            list.add(organizationEmployeeCount.getCreator() + EMAIL_ADDRESS);
        }
        Employee manager = employeeService.getManagerByOrgId(organizationEmployeeCount.getOrgId());
        if(manager != null) {
            list.add(manager.getUserCode() + EMAIL_ADDRESS);
        }
        //下面两个是特殊处理，可以删除
        list.add("89695" + EMAIL_ADDRESS);
        list.add("90378" + EMAIL_ADDRESS);
        return list;
    }

}

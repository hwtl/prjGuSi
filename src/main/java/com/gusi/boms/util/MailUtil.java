package com.gusi.boms.util;

import com.gusi.boms.common.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.HtmlEmail;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件发送工具类
 * 
 * @author Alex Yu
 * @version 1.0
 */
public class MailUtil {

	// log对象
	private static final Log log = LogFactory.getLog(MailUtil.class);

	/**
	 * 邮件发送方法
	 * 
	 * @param toList
	 *            收件人一览, 元素实例"xxxx@dooioo.com"
	 * @param replyList
	 *            回复一览, 元素实例"xxxx@dooioo.com"
	 * @param mailSubject
	 *            邮件标题
	 * @param mailContent
	 *            邮件正文
	 * @throws Exception
	 */
	public static void sendMail(List<String> toList, List<String> replyList, String mailSubject, String mailContent) throws Exception {
		// 收件人列表
		List<InternetAddress> internetAddressList = new ArrayList<InternetAddress>();

		// 收件人列表
		for (String str : toList) {
			// String对象转换成InternetAddress对象
			internetAddressList.add(new InternetAddress(str));
		}

		log.info("系统发送邮件===============begin");
		// 创建一个eMail对象
		HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setDebug(true);
		htmlEmail.setCharset("UTF-8");
		// 邮件服务器地址
		htmlEmail.setHostName(Configuration.getInstance().getMailHostName());
		// 收件人列表
		htmlEmail.setTo(internetAddressList);
		// 发件人
		htmlEmail.setFrom(Configuration.getInstance().getMailFrom());
		// 回复人
        if(replyList != null && replyList.size() > 0) {
            for (String reply : replyList) {
                htmlEmail.addReplyTo(reply);
            }
        }
		// 发件人登录信息
		htmlEmail.setAuthentication(Configuration.getInstance().getMailFrom(),	Configuration.getInstance().getMailFromPwd());
		// 邮件标题
		htmlEmail.setSubject(mailSubject);
		// 邮件正文
		htmlEmail.setMsg(mailContent);

		// 发送邮件
		htmlEmail.send();
		log.info("系统发送邮件===============end");
	}

}
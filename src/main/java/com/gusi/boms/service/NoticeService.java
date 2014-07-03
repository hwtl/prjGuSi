package com.gusi.boms.service;

import com.gusi.boms.common.Configuration;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.plus.util.GlobalConfigUtil;
import com.dooioo.plus.util.HttpClientUtil;
import com.dooioo.plusSMS.enums.CompanyType;
import com.dooioo.plusSMS.service.PSMSSendService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author "liuhui" 
 * @since 2.7.4
 * @createAt 2014-2-20 下午3:38:42
 * <p>
 *  发送工作站消息
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@Service
public class NoticeService {

    private static final Log LOG = LogFactory.getLog(NoticeService.class);

	@Autowired
	private PSMSSendService psmsSendService;
    @Autowired
    private FreemarkerUtil freemarkerUtil;
  
	 /**
	  * @since: 2.7.4
	  * @param phones
	  * @param message
	  * @param company
	  * <p>
	  *  发送短信息
	  * </p>   
	 */
	@Async
	public void sendSms(String phones,String message,String company){
		if (GlobalConfigUtil.isProductionEnv()) {
			psmsSendService.sendMsg(phones,message ,GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(company));
		} else {
            //发给测试
            psmsSendService.sendMsg("18217581496",message + "(" + GlobalConfigUtil.getCurrentEnv() + "_to_" + phones + ")"
                    ,GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(company));
        }
	  }

    /**
     * 发送短信给报到人
     * @param phones    发送的手机号码
     * @param company   公司
     * @param userCode  发送的员工工号
     * @param registerDate  发送的内容时间参数
     */
    @Async
    public void sendSmsToRegist(String phones,String company, int userCode, String registerDate){
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("registerDate", registerDate);
            String msg = freemarkerUtil.writeTemplate("/register/registerTemplet.ftl", params);
            psmsSendService.sendMsg(phones,msg , Configuration.getInstance().getHrmsAppCode(), null, userCode,
                    CompanyType.valueOf(company));
        } catch (Exception e) {
            LOG.error("发送失败! phones=" + phones, e);
        }
    }

	/**
	  * @since: 2.7.4 
	  * @param userCode
	  * @param message
	  * <p>
	  * 发送头部消息
	  * </p>   
	 */
	public void sendOneHeadNotice(int userCode,String message){
		Map<String,String> params=new HashMap<>();
		params.put("empNo",String.valueOf(userCode));
		params.put("source","oms");
		params.put("type", "notice");
		JSONObject json=new JSONObject();
		json.put("relationId", userCode);
		json.put("content", message);
		params.put("content",json.toString());
		HttpClientUtil.doPost(Configuration.getInstance().getNoticeUrl(), params);
	}
	
	@Async
	public void sendBatchHeadNotice(String userCodes,String message){
		String[] userCodeArr=userCodes.split(",");
		if (userCodeArr==null || userCodeArr.length==0) {
			return;
		}
		for (String userCode : userCodeArr) {
			sendOneHeadNotice(Integer.valueOf(userCode), message);
		}
	}
}



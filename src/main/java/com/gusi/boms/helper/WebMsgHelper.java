package com.gusi.boms.helper;


import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.gusi.empTransfer.jstl.UrlJstl;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.webplus.httpclient.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-03-24 16:42
 * @Description: WebMsgHelper
 */
public class WebMsgHelper {

    private static final Log LOG = LogFactory.getLog(WebMsgHelper.class);

    /**
     * 发送网站头部消息
     * @param url
     * @param params
     * @return
     */
    public static boolean sendMsg(String url, Map<String,String> params) {
        try {
            JSONObject jo = JSONObject.fromObject(HttpClientUtils.doPost(url, params));
            if(Constants.OK.equalsIgnoreCase(jo.getString(Constants.STATUS))) {
                return true;
            }
        } catch (Exception e) {
            LOG.error("发送网站头部消息失败！url = " + url + ", params = " + params.toString(), e);
        }
        return false;
    }

    /**
     * 发送网站头部消息
     * @param userCode
     * @param type
     * @param source
     * @param content
     */
    public static void sendWebMsg(int userCode, String type, String source, String content, Company company) {
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("empNo", String.valueOf(userCode));
        parms.put("type", type);
        parms.put("source", source);
        JSONObject json=new JSONObject();
        json.put("relationId", userCode);
        json.put("content", content);
        parms.put("content",json.toString());
        WebMsgHelper.sendMsg(UrlJstl.formatUrl(Configuration.getInstance().getWebMsgUrl(), company.toENLower()),  parms);
    }

    /**
     * 发送网站头部消息
     * @param userCode 工号
     * @param content 内容
     */
    public static void sendWebMsg(int userCode, String content, Company company) {
        WebMsgHelper.sendWebMsg(userCode, Configuration.getInstance().getSendWebMsgType(), Configuration.getInstance().getSendWebMsgSource(), content, company);
    }

    /**
     * 发送网站头部消息
     * @param userCodes
     * @param content
     * @param company
     */
    public static void sendWebMsg(String userCodes, String content, Company company) {
        String[] us =userCodes.trim().split(",");
        for(String s : us) {
            WebMsgHelper.sendWebMsg(Integer.parseInt(s), Configuration.getInstance().getSendWebMsgType(), Configuration.getInstance().getSendWebMsgSource(), content, company);
        }
    }

    /**
     * 发送网站头部消息
     * @param content
     * @param company
     */
    public static void sendWebMsg(String content, Company company) {
        sendWebMsg(Configuration.getInstance().getZpxxReceivers(), content, company);
    }



}

package com.gusi.boms.helper;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.gusi.boms.dto.SmsReports;
import com.gusi.boms.model.EmployeeDetails;
import com.gusi.boms.util.JsonUtils;
import com.dooioo.webplus.httpclient.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-04-03 09:30
 * @Description: 短信是否发送成功接口
 */
public class SmsApiHelper {

    private static final Log LOG = LogFactory.getLog(SmsApiHelper.class);

    /**
     * 请求接口
     * @param url
     * @param phones
     * @return
     */
    public static List<SmsReports> sendMsg(String url, String phones) {
        try {
            JSONObject jo = JSONObject.fromObject(HttpClientUtils.doGet(url+phones));
            if(Constants.OK.equalsIgnoreCase(jo.getString(Constants.STATUS))) {
                List<SmsReports> smsReportsList = JsonUtils.getDTOList(jo.getString("reports"), SmsReports.class);
                return smsReportsList;
            }
        } catch (Exception e) {
            LOG.error("请求短信接口失败！url = " + url + ", params = " + phones, e);
            throw new RuntimeException("请求短信接口失败！url = " + url + ", params = " + phones, e);
        }
        return null;
    }

    /**
     * 请求接口
     * @param phones
     * @return
     */
    public static List<SmsReports> sendMsg(String phones) {
        return SmsApiHelper.sendMsg(Configuration.getInstance().getSmsApiUrl(), phones);
    }

    /**
     * 请求接口
     * @param employeeDetailses
     * @return
     */
    public static List<SmsReports> sendMsg(List<EmployeeDetails> employeeDetailses) {
        return sendMsg(PhoneHelper.format(employeeDetailses));
    }

    /**
     * 获取没有正常收到短信的记录
     * @param smsReportsList
     * @return
     */
    public static List<SmsReports> getNotOk(List<SmsReports> smsReportsList) {
        List<SmsReports> rt = new ArrayList<>();
        if(CollectionUtils.isEmpty(smsReportsList)) {
            return rt;
        }
        for(SmsReports s : smsReportsList) {
            if(StringUtils.isNotEmpty(s.getReportStatus()) && "1".equals(s.getReportStatus().trim())) {
                LOG.info(s.getPhone() + "，正常收到短信！");
            } else {
                rt.add(s);
                LOG.info(s.getPhone() + "，没有正常收到短信！");
            }
        }
        return rt;
    }

}

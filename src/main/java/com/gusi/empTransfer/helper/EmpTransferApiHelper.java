package com.gusi.empTransfer.helper;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.gusi.empTransfer.jstl.UrlJstl;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.webplus.httpclient.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: fdj
 * @Since: 2014-03-04 13:28
 * @Description: 接口请求相关方法
 */
public class EmpTransferApiHelper {

    private static final Log LOG = LogFactory.getLog(EmpTransferApiHelper.class);

    /**
     * 判断是否所有交接已经完成
     * @return
     */
    public static boolean isAllTransfered(int userCode, Company company) {
        return isFyTransfered(userCode, company) && isKyTransfered(userCode, company);
    }

    /**
     * 判断客源是否交接完成
     * @return
     */
    public static boolean isKyTransfered(int userCode, Company company) {
        return isReturnOk(Configuration.getInstance().getKyTransferApi(), userCode, company);
    }

    /**
     * 判断房源是否交接完成
     * @return
     */
    public static boolean isFyTransfered(int userCode, Company company) {
        try {
            JSONObject rs = JSONObject.fromObject(HttpClientUtils.doGet(UrlJstl.formatUrl(Configuration.getInstance().getFyTransferApi(), company.toENLower(), String.valueOf(userCode))));
            String status = rs.getString(Constants.DATA);
            return status.equalsIgnoreCase(Constants.TRUE) ? true : false;
        } catch (Exception e) {
            LOG.error("请求Url失败！URL:" + UrlJstl.formatUrl(Configuration.getInstance().getFyTransferApi(), company.toENLower(), String.valueOf(userCode)), e);
            return false;
        }
    }

    /**
     * 判断返回状态是否为ok
     * @param url
     * @return
     */
    public static boolean isReturnOk(String url, int userCode, Company company) {
        try {
            JSONObject rs = JSONObject.fromObject(HttpClientUtils.doGet(UrlJstl.formatUrl(url, company.toENLower(), String.valueOf(userCode))));
            String status = rs.getString(Constants.STATUS);
            return status.equalsIgnoreCase(Constants.OK) ? true : false;
        } catch (Exception e) {
            LOG.error("请求Url失败！url:" + UrlJstl.formatUrl(url, company.toENLower(), String.valueOf(userCode)), e);
            return false;
        }

    }

}

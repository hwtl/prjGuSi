package com.gusi.empTransfer.jstl;

import com.dooioo.plus.util.TextUtil;

/**
 * @Description: 公司域名相关处理
 * @Author: fdj
 * @Since: 2014-02-11 10:24
 */
public final class UrlJstl {

    /**
     * 拼接url
     * @param url
     * @param domain
     * @param userCode
     * @return
     */
    public static String formatUrl(String url, String domain, String userCode) {
        return TextUtil.format(url, domain, userCode);
    }

    /**
     * 拼接url
     * @param url
     * @param domain
     * @return
     */
    public static String formatUrl(String url, String domain) {
        return TextUtil.format(url, domain);
    }

}

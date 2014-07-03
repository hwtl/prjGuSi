package com.gusi.boms.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: hw
 * Date: 13-1-18
 * Time: 下午5:02
 * 获取当前项目的路径
 */
public class WebPathUtils {
    /**
     * 获取服务器地址
     * @param request 客户端请求
     * @return  String
     */
    public static String getServerPath(HttpServletRequest request){
      return request.getSession().getServletContext().getRealPath("/").replace("ROOT\\","");
    }

}

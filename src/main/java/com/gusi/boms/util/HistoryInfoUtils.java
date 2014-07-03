package com.gusi.boms.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.util
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-09 13:45)
 * 历史记录的工具类
 */
public class HistoryInfoUtils {
    /**
     * 数据类型的记录
     * @param typeStr 数据类型
     * @param remarkStr 操作记录
     * @return String
     */
    public static String getChangeStr(String typeStr,String remarkStr){
        return typeStr + "：" + remarkStr + "<br/>";
    }

    /**
     * 拼装操作记录
     * @param oldStr 旧值
     * @param newStr 新值
     * @return String
     */
    public static String getRemarkStr(String oldStr,String newStr){
        return " 【 "+(StringUtils.isBlank(oldStr)?"":oldStr) + " 】 => 【 " + (StringUtils.isBlank(newStr)?"":newStr)+" 】 " ;
    }

    public static String getNewRemark(String newStr){
        return "=> 【 " + (StringUtils.isBlank(newStr)?"":newStr)+" 】 " ;
    }
}

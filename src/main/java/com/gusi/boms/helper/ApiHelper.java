package com.gusi.boms.helper;

import com.gusi.boms.common.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-31 14:34)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class ApiHelper {

    /**
     * 清除档案api缓存
     * @param userCode
     */
    public static void releaseArchivesCache(int userCode) {
        Constants.EMPLOYEEDETAILS.remove(userCode);
    }

    /**
     * 清除异动api缓存
     * @param userCode
     */
    public static void releaseChangesCache(int userCode) {
        Constants.EMPLOYEECHANGES.remove(userCode);
    }

    /**
     * 清除奖惩api缓存
     * @param userCode
     */
    public static void releaseRewardsCache(int userCode) {
        Constants.EMPLOYEEREWARDS.remove(userCode);
    }

    /**
     * 清空所有api缓存
     */
    public static void releaseAll() {
        Constants.EMPLOYEEREWARDS.clear();
        Constants.EMPLOYEECHANGES.clear();
        Constants.EMPLOYEEDETAILS.clear();
    }

    /**
     * 与当前时间进行比较
     * @param date
     * @param hour 更新时间
     * @return
     */
    public static boolean compareWithNow(Date date, int hour) {
        long diff = new Date().getTime() - date.getTime();
        long hours = diff / (1000 * 60 * 60);
        if(hours < hour) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse("2004-03-26 13:31:40");
            Date d2 = df.parse("2013-08-01 14:30:24");
//            long diff = d1.getTime() - d2.getTime();
//            long hours = diff / (1000 * 60 * 60);
//            System.out.println(hours);
            System.out.println(compareWithNow(d1, 1));
            System.out.println(compareWithNow(d2, 2));
        } catch (Exception e) {
        }
    }

}

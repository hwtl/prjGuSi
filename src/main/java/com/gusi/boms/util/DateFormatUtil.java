/**
 *
 */
package com.gusi.boms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zjs
 *
 */
public class DateFormatUtil {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String CHINESE_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String DAY = "天";
    public static final String HOUR = "小时";
	public static final String MINUTE = "分";
	public static final String SECOND = "秒";

	/**
	 * @param date
	 * @return 获取合同结束时间
	 */
	public static Date getConstractEndTime(Date date)
	{
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, 3);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	/**
	 * 获得当前日期的字符串格式，字符串格式为默认格式yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getCurrent() {
		return getString(new Date());
	}

	/**
	 * 以给定的日期格式返回当前日期的字符串格式
	 *
	 * @param pattern
	 * @return
	 */
	public static String getCurrent(String pattern) {
		if (null == pattern || "".equals(pattern.trim())) {
			return null;
		}
		return getString(new Date(), pattern);
	}

    /**
     * 获得当前时间(年月日 例如：2013-10-24)
     * @return
     */
    public static Date getNow() {
        String now = getCurrent(YMD_FORMAT);
        return getDate(now, YMD_FORMAT);
    }

    /**
     * 获得当前时间(年月日 例如：2013-10-24)
     * @return
     */
    public static Calendar getNowCalendarWithoutHMS() {
    	Calendar now = Calendar.getInstance();
    	now.set(Calendar.HOUR, 0);
    	now.set(Calendar.MINUTE, 0);
    	now.set(Calendar.SECOND, 0);
    	now.set(Calendar.MILLISECOND, 0);
        return now;
    }

	/**
	 * 把默认格式的字符串转换成日期
	 *
	 * @param dateString
	 * @return
	 */
	public static Date getDate(String dateString) {
		if (null == dateString || "".equals(dateString.trim()))
			return null;
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
			date = sdf.parse(dateString);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	/**
	 * 把指定格式的字符串转换成日期
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date getDate(String dateString, String pattern) {
		if (null == dateString || "".equals(dateString.trim())
				|| null == pattern || "".equals(pattern.trim()))
			return null;
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(dateString);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	/**
	 * 把日期转换成默认格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getString(Date date) {
		return getString(date,DEFAULT_FORMAT);
	}

	/**
	 * 把日期转换成YYYY-MM-DD的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getYMDString(Date date) {
		return getString(date,YMD_FORMAT);
	}

	/**
	 * 把日期转换成指定格式的字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getString(Date date, String pattern) {
		if (null == date || null == pattern || "".equals(pattern.trim())) {
			return "";
		}
		String str = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			str = sdf.format(date);
		} catch (Exception e) {
			str = e.getMessage();
		}
		return str;
	}

	/**
	 * 返回date到现在的时间差
	 *
	 * 格式：XX日XX小时XX分XX秒
	 *
	 * @param date
	 * @return
	 */
	public static String getBetweenTime(Date date) {

		Date now = new Date();
		String result;
		if (now.after(date)) {
			result = getBetweenTime(date, now);
		} else {
			result = getBetweenTime(now, date);
		}
		return result;
	}

	public static String getBetweenTime(Date begin, Date end) {
		if(null == begin || null == end)
			return "";
		long l = -1;
		if (end.after(begin)) {
			l = end.getTime() - begin.getTime();
		} else {
			l = begin.getTime() - end.getTime();
		}
		String result = getDurationString(l);
		return result;
	}

	/**
	 * 给定毫秒返回格式化的时间段. eg. xx天xx小时xx分xx秒
	 * @param durationInMillis 毫秒
	 * @return 格式化的时间段
	 */
	public static String getDurationString(Long durationInMillis) {
		long day = durationInMillis / (24 * 60 * 60 * 1000);
		long hour = (durationInMillis / (60 * 60 * 1000) - day * 24);
		long min = ((durationInMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (durationInMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

		StringBuffer buffer = new StringBuffer();
		if (day > 0) {
			buffer.append(day).append(DAY);
		}
		if (hour > 0) {
			buffer.append(hour).append(HOUR);
		}
		if (min > 0) {
			buffer.append(min).append(MINUTE);
		}
		if (s > 0) {
			buffer.append(s).append(SECOND);
		}
		return buffer.toString();
	}

	/**
	 * 用于返回指定日期的下一天的日期
	 *
	 * @param date
	 *            指定日期
	 * @return 指定日期的下一天的日期
	 */
	public static Date getNextDay(Date date) {
		return getFutureDay(date, 1);
	}

	/**
	 * 用于返回指定日期格式的日期增加指定天数的日期
	 *
	 * @param date
	 *            指定日期
	 * @param days
	 *            指定天数
	 * @return 指定日期格式的日期增加指定天数的日期
	 */
	public static Date getFutureDay(Date date, int days) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 取得24小时后的Date实例
	 *
	 * @param date
	 */
	public static Date add24hour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public static int getCurrentMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
	}

	public static String getChinaDateFormmat(Date date){
		if(null == date)
			return "";
		String str = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(CHINESE_DATE_FORMAT);
			str = sdf.format(date);
		} catch (Exception e) {
			str = e.getMessage();
		}
		return str;
	}

	public static int getCompareTo(Date firstDate, Date lastDate){
        return firstDate.compareTo(lastDate);
	}
	public static boolean Equals(Date firstDate,Date lastDate){
        return !(null == firstDate && null != lastDate) && !(null != firstDate && null == lastDate) && (null == firstDate || getCompareTo(firstDate, lastDate) == 0);
    }
	public static boolean before(Date firstDate,Date lastDate){
        return !(null == firstDate || null == lastDate) && getCompareTo(firstDate, lastDate) < 0;
    }

    public static Date getDateFormat(Date d,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if(d == null){
            return null;
        }
        String dd =format.format(d);
        Date d2;
        try {
            d2 = format.parse(dd);
            return d2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  d;
    }
	public static void main(String[] args) {
//		System.out.println("dddddd");
        System.out.println(DateFormatUtil.getNow());
	}
}

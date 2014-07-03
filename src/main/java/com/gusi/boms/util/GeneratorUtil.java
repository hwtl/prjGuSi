package com.gusi.boms.util;

import java.util.Date;

/**
 * 
* @ClassName: GeneratorUtil 
* @Description: 组织编号生成工具类
* @author fdj
* @date 2013-4-11 上午11:24:02
 */
public class GeneratorUtil {
	
	/**
	 * 生成组织编号（将orgId与日期组合成orgCode）
	 * @param orgId 组织ID
	 * @return
	 */
	public static String generateOrgCode(){
		return DateFormatUtil.getString(new Date(), "yyMMddHHmmss");
	}
	
	/**
	 * 生成组织长编号
	 * @param parentLongCode 上级组织长编号
	 * @param orgCode 该组织编号
	 * @return
	 */
	public static String generateOrgLongCode(String parentLongCode, String orgCode){
		return parentLongCode + "/" + orgCode;
	}
	
	/**
	 * 将数字转成指定位数的字符串
	 * @param num
	 * @param digit
	 * @return
	 */
	public static String toString(long num, int digit){
		String str = "";
		String s1 = String.valueOf(num);
		for(int i = 0; i < digit - s1.length(); i++){
			str = str + "0";
		}
		return str + s1;
	}

}

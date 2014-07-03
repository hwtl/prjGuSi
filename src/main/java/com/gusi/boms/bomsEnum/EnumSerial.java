/**
 * 
 */
package com.gusi.boms.bomsEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Alex Yu
 * @Created 2014年6月17日上午9:29:24
 */
public enum EnumSerial {

	占位, 行政管理职系, 营销业务职系, 行政技术职系, 营销职系_代理部, 营销管理职系;

	/**
	 * 根据类别返回对应的文字类别
	 * 
	 * @param serialId
	 * @return
	 */
	public static String getSerialStr(int serialId) {
		try {
			return valueOf(serialId).toString();
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 根据类别返回对应的文字类别
	 * 
	 * @param eid
	 *            参数
	 * @return 对应的文字类别
	 * @throws Exception
	 */
	public static EnumSerial valueOf(int eid) throws Exception {
		for (EnumSerial s : values()) {
			if (s.ordinal() == eid)
				return s;
		}
		throw new java.lang.IllegalArgumentException("类型没有找到");
	}

	/**
	 * 取得所有枚举类型
	 * 
	 * @return 所有枚举类型
	 */
	public static List<Map<String, Object>> getEnumsAllListMap() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (EnumSerial s : values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", s.ordinal());
			map.put("name", s.name());
			list.add(map);
		}
		return list;
	}

	/**
	 * 返回枚举对应的字符值("0", "1"....)
	 */
	@Override
	public String toString() {
		String strValue = String.valueOf(this.ordinal());
		return strValue;
	}
}

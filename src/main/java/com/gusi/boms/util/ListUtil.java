/**
 * 
 */
package com.gusi.boms.util;

import java.util.List;

/**
 * @author Alex Yu
 * @Created 2014年3月12日下午1:09:54
 */
public class ListUtil {

	public static boolean isEmpty(List<?> list) {
		boolean result = false;
		if (list == null || list.size() == 0) {
			result = true;
		}
		return result;
	}

	public static boolean isNotEmpty(List<?> list) {
		boolean result = false;
		if (list != null && list.size() > 0) {
			result = true;
		}
		return result;
	}
}

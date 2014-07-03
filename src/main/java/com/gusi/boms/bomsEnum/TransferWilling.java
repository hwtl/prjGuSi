/**
 * 
 */
package com.gusi.boms.bomsEnum;

/**
 * 员工转调意愿枚举
 * @author Alex Yu
 * @Created 2014年3月4日下午3:54:32
 */
public enum TransferWilling {

	无, 主动, 被动;

	/**
	 * 根据转调意愿, 判断是否是被动转调
	 * @param type 转调意愿
	 * @return 是否是被动转调
	 */
	public static boolean isPassiveTransfer(int type) {
		boolean result = false;
		if (type == TransferWilling.被动.ordinal()) {
			result = true;
		} else if (type == TransferWilling.无.ordinal() || type == TransferWilling.主动.ordinal()) {
			result = false;
		}
		return result;
	}
}

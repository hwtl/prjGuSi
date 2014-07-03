package com.gusi.boms.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.util
 * Author: Jerry.hu
 * Create: Jerry.hu(2013-04-08 15:09)
 * Description: MD5加密
 */
public class MD5Utils {

	private static final Log log = LogFactory.getLog(MD5Utils.class);
	/**
	 * 验证码长度
	 */
	private static final int VERTIFY_CODE_LENTH = 6;

	/**
	 * 验证码字符来源
	 */
	private static final String CODE_STRING = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklmnbvcxz0123456789";

	/**
	 * MD5 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String str2Md5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * @return 生成验证码
	 */
	public static String generateVertifyCode() {
		Random random = new Random();
		int len = CODE_STRING.length();
		StringBuffer code = new StringBuffer();
		for (int i = 0; i < VERTIFY_CODE_LENTH; i++) {
			code.append(CODE_STRING.charAt(random.nextInt(len)));
		}
		return code.toString();
	}

	public static void main(String[] args) {
		//System.out.println(generateVertifyCode());
		System.out.println(str2Md5("123456"));
	}
}

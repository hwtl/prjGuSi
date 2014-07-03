package com.gusi.boms.util;

import com.gusi.boms.model.EmployeeInfoExcel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author   huwei E-mail: 86101@dooioo.com
 * @version  2012-6-26 下午04:03:18
 * 反射
 */
public class ReflectionUtil {
	/**
	 * 获取对象属性，返回一个字符串数组
	 * @param o 对象
	 * @return String[] 字符串数组
	 */
	public static String[] getFiledName(Object o) {
		try {
			Field[] fields = o.getClass().getDeclaredFields();
			String[] fieldNames = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();
			}
			return fieldNames;
		} catch (SecurityException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return null;
	}

	/**
	 * 使用反射根据属性名称获取属性值
	 * @param fieldName 属性名称
	 * @param o  操作对象
	 * @return Object 属性值
	 */
	public static Object getFieldValueByName(String fieldName, Object o,String pattern) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o);
			if(value == null){
				return "";
			}
			else if (getFieldType(fieldName,o).equals("java.util.Date")){
				 return DateFormatUtil.getString((java.util.Date)value,pattern);
			}
			else{
				return value;
			}
		} catch (Exception e) {
			System.out.println("属性不存在"+fieldName);
			return "";
		}
	}

	public static String getFieldType(String fieldName, Object o){
		try {
			Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    return field.getType().getName();
                }
            }
			return null;
		} catch (SecurityException e) {
            System.out.println(e.getMessage());
		}
		return null;
	}
	public static void main(String[] args) {
		EmployeeInfoExcel e = new EmployeeInfoExcel();
		e.setUserCode("ssssss");
        System.out.println(getFieldValueByName("leaveDate", e,DateFormatUtil.YMD_FORMAT));
	}

}

package com.gusi.boms.util;

import com.gusi.boms.model.OrganizationPhone;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * json字符串转map
 * 
 * @author
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonUtils {

	/**
	 * 将json格式转换成一个javabean
	 * 
	 * @param json
	 * @return
	 */
	public static DynaBean toBean(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return (DynaBean) JSONObject.toBean(jsonObject);
	}

	/**
	 * 将json格式转换成指写的javabean
	 * 
	 * @param json
	 * @param classPath
	 *            javabean name
	 * @return
	 */
	public static Object toBean(String json, String beanName) {
		try {
			return toBean(json, Class.forName(beanName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json格式转换成指写的javabean
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object toBean(String json, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * 将javabean转换成json 并且在json中添加clazz属性，用于在再转换成javabean时自动再组装 e.g.
	 * {"clazz":"com.dooioo.property.model.Property"}
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static String toJson(Object obj, Class clazz) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		jsonObject.element("clazz", clazz.getName());
		return jsonObject.toString();
	}

	/**
	 * 将javabean转换成json
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		return jsonObject.toString();
	}

	/**
	 * 将map转换成json格式
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		if (obj == null)
			return "";
		JSONArray json = JSONArray.fromObject(obj);
		return json.toString().replaceAll("^\\[", "").replaceAll("\\]$", "");
	}

	public static List JSONArrayConvertList(String json) {
		ArrayList list = new ArrayList();
		JSONArray ja = JSONArray.fromObject(json);
		populateArray(ja, list);
		return list;
	}

	private static void populateArray(JSONArray jsonArray, List list) {
		// 循环遍历jsonarray
		for (int i = 0; i < jsonArray.size(); i++) {
			if (jsonArray.get(i).getClass().equals(JSONArray.class)) { // 如果元素是JSONArray类型
				ArrayList _list = new ArrayList();
				list.add(_list);
				// 递归遍历，此为深度遍历，先把最内层的jsonobject给遍历了
				populateArray(jsonArray.getJSONArray(i), _list);
			} else if (jsonArray.get(i).getClass().equals(JSONObject.class)) { // 如果是JSONObject类型
				HashMap _map = new HashMap();
				list.add(_map);
				// 遍历JSONObject
				populate(jsonArray.getJSONObject(i), _map);
			} else { // 如果都不是的话就直接加入到list中
				list.add(jsonArray.get(i));
			}
		}
	}

	private static Map populate(JSONObject jsonObject, Map map) {
		for (Iterator iterator = jsonObject.entrySet().iterator(); iterator.hasNext();) {
			String entryStr = String.valueOf(iterator.next());
			String key = entryStr.substring(0, entryStr.indexOf("="));
			if (jsonObject.get(key).getClass().equals(JSONObject.class)) {
				HashMap _map = new HashMap();
				map.put(key, _map);
				populate(jsonObject.getJSONObject(key), _map);
			} else if (jsonObject.get(key).getClass().equals(JSONArray.class)) {
				ArrayList list = new ArrayList();
				map.put(key, list);
				populateArray(jsonObject.getJSONArray(key), list);
			} else {
				map.put(key, jsonObject.get(key));
			}
		}
		return map;
	}

	public static List getDTOList(String jsonString, Class clazz) {
		if(StringUtils.isBlank(jsonString)){
			return null;
		}
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}
	
    public static Object[] getObjectArrayFromJson(String jsonString) {   
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();   
    }   
    
    public static String jsonReplace(String json){
    	return  json.replace("''","null");
    }
    
	public static void main(String[] args) {
		/*String json2 ="[{\"name\":\"置业顾问\",\"code\":\"10\"},{\"name\":\"分行经理\",\"code\":\"100\"}]";
		List<OrderView> js = getDTOList(json2, OrderView.class);
		System.out.println(js.size());
		System.out.println(js);*/
		
		/*String json = "[{'result':'0'}]";
		JSONObject jsonObject = JSONObject.fromObject(json);
		String status = jsonObject.getString("result");
		System.out.println(status);*/
		String json = "[{'orgId':'1','phoneNumber':'32520690','line':'1'},{'orgId':'1','phoneNumber':'32520711','line':'2'},{'orgId':'1','phoneNumber':'32520712','line':'3'},{'orgId':'1','phoneNumber':'32520725','line':'4'},{'orgId':'1','phoneNumber':'32520728','line':''},{'orgId':'1','phoneNumber':'32520698','line':''},{'orgId':'1','phoneNumber':'32520696','line':''},{'orgId':'1','phoneNumber':'32520699','line':''},{'orgId':'1','phoneNumber':'32520708','line':''},{'orgId':'1','phoneNumber':'52395711','line':''},{'orgId':'1','phoneNumber':'52395835','line':''},{'orgId':'1','phoneNumber':'52396509','line':''},{'orgId':'1','phoneNumber':'52396298','line':''},{'orgId':'1','phoneNumber':'32520721','line':''},{'orgId':'1','phoneNumber':'52396255','line':''},{'orgId':'1','phoneNumber':'52395833','line':''},{'orgId':'1','phoneNumber':'52396507','line':''},{'orgId':'1','phoneNumber':'52395620','line':''},{'orgId':'1','phoneNumber':'52396508','line':''},{'orgId':'1','phoneNumber':'32520722','line':''},{'orgId':'1','phoneNumber':'52395830','line':''},{'orgId':'1','phoneNumber':'32520691','line':''}]";
		List<OrganizationPhone> appList = getDTOList(jsonReplace(json),OrganizationPhone.class);
		System.out.println(appList.size());
		System.out.println(appList);
		
	}
}

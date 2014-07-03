package com.gusi.boms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.boms.model.Parameter;
import org.springframework.stereotype.Service;

import com.dooioo.web.service.BaseService;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-10 上午08:49:51 ) 
 */
@Service
public class ParameterService extends BaseService<Parameter>{
	/**
	 * @param key
	 * @return 根据typeKey返回map结果
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> findMapByTypeKey(String key)
    {
		return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findMapByTypeKey"), key,"optionCode","optionValue");
    }
	@SuppressWarnings("unchecked")
	public Map<String,String> findProvinces()
    {
		return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findProvinces"), Parameter.PROVINCE_KEY,"optionCode","optionValue");
    }
	
	/**
	 * @return 查找所有民族
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> findNations()
    {
		return  queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findNations"), Parameter.NATION_KEY,"optionCode","optionValue");
    }
	/**
	 * @return 民族列表
	 */
	public List<Parameter> findNationList()
	{
		return findByTypeKey(Parameter.NATION_KEY);
	}
	/**
	 * @return 婚姻状态
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> findMarriages()
    {
		return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findMarriages"), Parameter.MARRIAGE_KEY,"optionCode","optionValue");
    }
	/**
	 * @return 查找所有学位
	 */
	@SuppressWarnings("unchecked")
	public  Map<String,String> findEducationDegrees()
    {
       return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findEducationDegrees"), Parameter.DEGREE_KEY,"optionCode","optionValue");
    }

	@SuppressWarnings("unchecked")
	public  Map<String,String> findStudyType()
    {
       return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findStudyType"), Parameter.STUDY_TYPE_KEY,"optionCode","optionValue");
    }

	/**
	 * @return 查找所有星座
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> findConstellations()
    {
      return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findConstellations"), Parameter.CONSTELLATION_KEY,"optionCode","optionValue");
    }
	
	/**
	 * @return
	 *  查询所有血型
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> findBloodTypes()
	{
		return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findBloodTypes"),Parameter.BLOOD_TYPE_KEY, "optionCode","optionValue");
	}
	
	/**
	 * @return
	 *  查询所有政治背景
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> findPoliticalBackgrounds()
	{
		return queryDao.getSqlMapClientTemplate().queryForMap(sqlId("findPoliticalBackgrounds"),Parameter.POLITICAL_KEY, "optionCode","optionValue");
	}
	/**
	 * @param code
	 * @return 根据optionCode查找参数
	 */
	public  Parameter findByOptionCode(String optionCode) {
		return findById(sqlId("findByOptionCode"), optionCode);
	}

	/**
	 * @param typeKey
	 * @return 根据参数typeKey查找
	 */
	public List<Parameter> findByTypeKey(String typeKey) {
		return queryForList(sqlId("findByTypeKey"), typeKey);  
	}
    private static Map<String,String> parametersMap =null;
    /**
     * @return 返回所有参数map  key=optionCode,value=optionValue
     */
	public Map<String, String> findParameterMap() {
		 if (parametersMap==null) {
			 List<Parameter> parameters = queryForList();
			 parametersMap= new HashMap<String, String>();
			 for (Parameter parameter : parameters) {
				 parametersMap.put(parameter.getOptionCode(), parameter.getOptionValue());
			 }
		}
		return parametersMap;
	}
	

}

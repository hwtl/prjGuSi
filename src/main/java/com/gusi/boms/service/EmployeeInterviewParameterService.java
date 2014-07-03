package com.gusi.boms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gusi.boms.model.EmployeeInterviewParameter;
import com.dooioo.web.service.BaseService;

/**
 * @author liuhui
 * @createTime: 2013-7-23
 * 离职参数。。。
 */
@Service
public class EmployeeInterviewParameterService  extends BaseService<EmployeeInterviewParameter>{
	/**
	 * @return 查找用于离职面谈的问题和原因,即包含浅谈的原因，也包含面谈的表
	 */
	public List<EmployeeInterviewParameter> findForInterviewParameter()
	{
		List<EmployeeInterviewParameter> list=queryForList(sqlId("findForInterviewParameter"),
				EmployeeInterviewParameter.TYPE_LEAVE+","+EmployeeInterviewParameter.TYPE_INTERVIEW);
		return processSubs(list);
	}
	
	/**
	 * @param list
	 * @return 分类汇总子原因
	 */
	private List<EmployeeInterviewParameter> processSubs(List<EmployeeInterviewParameter> list)
	{
			if (list==null || list.isEmpty()) {
				return list;
			}
		 Map<Integer,List<EmployeeInterviewParameter>> tMap=new HashMap<Integer, List<EmployeeInterviewParameter>>();
		 Integer pid=null;
		 List<EmployeeInterviewParameter> parents=new ArrayList<EmployeeInterviewParameter>();
		 for (EmployeeInterviewParameter p : list) {
			  pid=p.getParentId(); 
			 if (pid==0) {
				 parents.add(p);
			  }else
			  {
				  //如果不是一级问题，则需要将子分类按parentId分类，
				  if (tMap.containsKey(pid)) {
					  tMap.get(pid).add(p);
					}else
					{
						//
						List<EmployeeInterviewParameter> tList=new ArrayList<EmployeeInterviewParameter>();
						tList.add(p);
						tMap.put(pid, tList);
					}
			  }
		 }
		 //设置子分类
		 for (EmployeeInterviewParameter pv : parents) {
			pv.setSubs(tMap.get(pv.getId()));
		}
	 return parents;
	}
	/**
	 * @return 查找用于离职面谈黑名单的原因
	 */
	public List<EmployeeInterviewParameter> findForBlackParameter()
	{
		return queryForList(sqlId("findForBlackParameter"),EmployeeInterviewParameter.TYPE_BLACK);
	}
}

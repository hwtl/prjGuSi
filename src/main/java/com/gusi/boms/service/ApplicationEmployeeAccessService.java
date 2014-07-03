package com.gusi.boms.service;

import com.gusi.boms.model.ApplicationEmployeeAccess;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限 - 员工对应管理系统Service
 */
@Service
public class ApplicationEmployeeAccessService extends BaseService<ApplicationEmployeeAccess> {
	
	/**
	 * 根据员工工号获得ta可以管理的系统编号
	 * @param int userCode
	 * @return List<ApplicationEmployeeAccess>
	 */
	public List<ApplicationEmployeeAccess> getPrivilegesByAppId(int userCode){
		return queryForList(sqlId("findApplicationEmployeeAccessByUserCode"), userCode);
	}
	
	
	
	
	/**
	 * 插入系统管理员权限
	 * @param userCode 员工工号
	 * @param appIds 系统编号集合 
	 * @return
	 */
	@Transactional
	public boolean insertApplicationEmployeeAccess(int userCode,String appIds){
		if(appIds.length() == 0)
			return true;
		List<ApplicationEmployeeAccess> list = new ArrayList<ApplicationEmployeeAccess>();
		String[] appIdList = appIds.split(",");
		for(String appId : appIdList){
			if(appId.length() > 0)
				list.add(new ApplicationEmployeeAccess(userCode,Integer.valueOf(appId)));
		}
		return insertApplicationEmployeeAccess(list);
	}
	
	
	/**
	 * 插入系统管理员权限
	 * @param list 对象集合
	 * @return
	 */
	@Transactional
	public boolean insertApplicationEmployeeAccess(List<ApplicationEmployeeAccess> list){
		if(list.size()==0)
			return true;
		this.delete(list.get(0).getUserCode());
		return this.batchInsert(sqlId("insert"),list);
	}
}

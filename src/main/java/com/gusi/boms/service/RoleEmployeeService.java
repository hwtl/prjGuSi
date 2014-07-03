package com.gusi.boms.service;

import com.gusi.boms.model.RoleEmployee;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleEmployeeService extends BaseService<RoleEmployee> {
	@Autowired
    RoleEmployeeLogService roleEmployeeLogService;
    @Transactional
	public boolean insertRoleEmployee(int creator,int userCode,int appId,String roleIds){
		Map<String,Object> map = new HashMap<>();
		map.put("userCode", userCode);
		map.put("appId", appId);
		map.put("creator", creator);
        map.put("roleIds",roleIds);
        roleEmployeeLogService.insertLog(map);
		this.delete(sqlId("delete"), map);
		if(roleIds.length() == 0)
			return true;
		List<RoleEmployee> list = new ArrayList<RoleEmployee>();
		String[] rIds = roleIds.split(",");
		for(String roleId : rIds){
			if(roleId.length()>0)
				list.add(new RoleEmployee(userCode,Integer.valueOf(roleId)));
		}
		return this.batchInsert(sqlId("insert"), list);
	}
    /**
      * @since: 2.7.2 
      * @param userCode
      * @param roleId
      * @return
      * <p>
      * 员工角色是否存在。
      * </p>   
     */
    public boolean empRoleExists(int userCode,int roleId){
    	Map<String,Object> params=new HashMap<>();
    	params.put("userCode", userCode);
    	params.put("roleId", roleId);
    	return count(sqlId("empRoleExists"), params)>0;
    }
	/**
	  * @since: 2.7.4
	  * @param userCode
	  * @param roleId
	  * @return
	  * <p>
	  *  api新增角色
	  * </p>   
	 */
	public boolean apiAddEmpRole(Integer userCode, Integer roleId) {
		boolean flag=this.insert(new RoleEmployee(userCode, roleId));
		if (flag) {
			this.roleEmployeeLogService.apiInsertLog(userCode, "api新增", roleId);
		}
		return flag;
	}
	/**
	  * @since: 2.7.4
	  * @param userCode
	  * @return
	  * <p>
	  *  房源删除此人所有角色
	  * </p>   
	 */
	public boolean removeRoleForFY(int userCode) {
		 delete(sqlId("removeRoleForFY"), userCode);
		 return true;
	}
	/**
	  * @since: 3.0.5 
	  * @param userCode
	  * @return
	  * <p>
	  * 删除客源此人角色
	  * </p>   
	 */
	public boolean removeRoleForKY(int userCode) {
		delete(sqlId("removeRoleForKY"), userCode);
		 return true;
	}
}
package com.gusi.boms.service;

import com.gusi.boms.model.Role;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 权限 - 角色Service
 */
@Service
public class RoleService extends BaseService<Role> {

	/**
	 * 根据系统ID 获得该系统的所有角色
	 * @param appId 系统ID
	 * @return List<Role>
	 */
	public List<Role> getRolesByAppId(int appId){
		return queryForList(sqlId("findRolesByAppId"), appId);
	}


	/**
	 * 根据员工工号与系统ID获得该员工的所有角色
	 * @param userCode 员工工号
	 * @param appId 系统ID
	 * @return List<Role>
	 */
	public List<Role> getRolesByUserCode(int userCode,int appId){
		Map<String,Object> map = new HashMap<>();
		map.put("userCode", userCode);
		map.put("appId",appId);
		return queryForList(sqlId("findRolesByUserCode"),map);
	}


	/**
	 * 根据员工工号与系统ID获得该他所有有关部门的角色
	 * @param userCode 员工工号
	 * @param appId 系统ID
	 * @return List<Role>
	 */
	public List<Role> getOrgRolesByUserCode(int userCode,int appId){
		Map<String,Object> map = new HashMap<>();
		map.put("userCode", userCode);
		map.put("appId",appId);
		return queryForList(sqlId("findOrgRolesByUserCode"),map);
	}

	/**
	 * 根据部门ID与系统ID获得该部门的所有角色
	 * @param orgId 部门ID
	 * @param appId 系统ID
	 * @return List<Role>
	 */
	public List<Role> getRolesByOrgId(int orgId,int appId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", orgId);
		map.put("appId",appId);
		return queryForList(sqlId("findRolesByOrgId"),map);
	}

	/**
	 * 根据员工工号与系统ID获得该他所有有关岗位的角色
	 * @param userCode 员工工号
	 * @param appId 系统ID
	 * @return List<Role>
	 */
	public List<Role> getPositionRolesByPositionId(int userCode,int appId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", userCode);
		map.put("appId",appId);
		return queryForList(sqlId("findPositionRolesByPositionId"),map);
	}

	/**
	 * 根据岗位ID与系统ID获得该部门的所有角色
	 * @param positionId 岗位ID
	 * @param appId 系统ID
	 * @return List<Role>
	 */
	public List<Role> getRolesByPositionId(int positionId,int appId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("positionId", positionId);
		map.put("appId",appId);
		return queryForList(sqlId("findRolesByPositionId"),map);
	}
	
    /**
      * @since: 2.7.1 
      * @param roleId
      * @return
      * <p>
      *  角色是否存在。
      * </p>   
     */
    public boolean roleExists(int roleId){
    	return count(sqlId("roleExists"), roleId)>0;
    }
}

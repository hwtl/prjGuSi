package com.gusi.boms.service;

import com.gusi.boms.model.RolePrivilege;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *  权限 - 角色与权限的关系Service
 */
@Service
public class RolePrivilegeService extends BaseService<RolePrivilege> {

    @Autowired
    RolePrivilegeLogService rolePrivilegeLogService;
	/**
	 * 根据角色ID获取角色与权限的关系
	 * @param roleId 角色ID
	 * @return List<RolePrivilege>
	 */
	public List<RolePrivilege> getRolePrivilegesByRoleId(int roleId){
		return queryForList(sqlId("findRolePrivilegesByRoleId"), roleId);
	}

	@Transactional
	public boolean insertRolePrivileges(int creator,List<RolePrivilege> list,int roleId){
        rolePrivilegeLogService.insertLog(creator,list,roleId);
		this.delete(roleId);
        return list.size() == 0 || batchInsert(sqlId("insert"), list);
    }
}

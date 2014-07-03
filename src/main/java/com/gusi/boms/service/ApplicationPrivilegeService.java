package com.gusi.boms.service;

import com.gusi.boms.model.ApplicationPrivilege;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 权限 - 具体权限Service
 */
@Service
public class ApplicationPrivilegeService extends BaseService<ApplicationPrivilege> {
	
	/**
	 * 根据系统编号获得权限列表
	 * @param appId 系统编号
	 * @return List<RolePrivilege>
	 */
	public List<ApplicationPrivilege> getPrivilegesByAppId(int appId){
		return queryForList(sqlId("findPrivilegesByAppId"), appId);
	}

    /**
     * 检查是有已经存在相同的权限url
     * @param url 权限url
     * @return boolean
     */
    public boolean checkPrivilegeUrlExist(String url){
        return  (Integer)queryForObject(sqlId("findPrivilegeUrlExist"),url)>0;
    }
}

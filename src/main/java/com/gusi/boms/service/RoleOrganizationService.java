package com.gusi.boms.service;

import com.gusi.boms.model.RoleOrganization;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleOrganizationService extends BaseService<RoleOrganization> {
    @Autowired
    RoleOrganizationLogService roleOrganizationLogService;
	@Transactional
	public boolean insertRoleOrganization(int creator,int orgId,int appId,String roleIds){
		Map<String,Object> map = new HashMap<>();
		map.put("orgId", orgId);
		map.put("appId", appId);
		map.put("creator",creator);
        map.put("roleIds",roleIds);
        roleOrganizationLogService.insertLog(map);
		this.delete(sqlId("delete"), map);
		if(roleIds.length() == 0)
			return true;
		List<RoleOrganization> list = new ArrayList<>();
		String[] rIds = roleIds.split(",");
		for(String roleId : rIds){
			if(roleId.length()>0)
				list.add(new RoleOrganization(orgId,Integer.valueOf(roleId)));
		}
		return this.batchInsert(sqlId("insert"), list);
	}
}
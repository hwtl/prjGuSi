package com.gusi.boms.service;

import com.gusi.boms.model.RolePosition;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RolePositionService extends BaseService<RolePosition> {
    @Autowired
    RolePositionLogService rolePositionLogService;
	@Transactional
	public boolean insertRolePosition(int creator,int positionId,int appId,String roleIds){
		Map<String,Object> map = new HashMap<>();
		map.put("positionId", positionId);
		map.put("appId", appId);
		map.put("creator", creator);
        map.put("roleIds",roleIds);
        rolePositionLogService.insertLog(map);
		this.delete(sqlId("delete"), map);
		if(roleIds.length() == 0)
			return true;
		List<RolePosition> list = new ArrayList<>();
		String[] rIds = roleIds.split(",");
		for(String roleId : rIds){
			if(roleId.length()>0)
				list.add(new RolePosition(positionId,Integer.valueOf(roleId)));
		}
		return this.batchInsert(sqlId("insert"), list);
	}
}
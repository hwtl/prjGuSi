package com.gusi.boms.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.model.Application;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 权限 - 系统Service
 */
@Service
public class ApplicationService extends BaseService<Application> {

	public List<Application> getAllowApps(int userCode){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userCode",userCode);
        paramMap.put("admin", Configuration.getInstance().getSuperAdminUserCode());
		return queryForList(sqlId("findAllowApps"), paramMap);
	}
}

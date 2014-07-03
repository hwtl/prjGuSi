package com.gusi.boms.service;

import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationName;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 
* @ClassName: OrganizationNameService 
* @Description: 组织名称业务逻辑处理
* @author fdj
* @date 2013-4-18 下午4:00:40
 */
@Service
public class OrganizationNameService extends BaseService<OrganizationName> {
	
	/**
	 * 根据组织门店的名称入库
	 * @param org 组织名称
	 */
	public void insertOrUpdate(Organization org) {
		OrganizationName organizationName = findById(org.getId());
        if(!StringUtils.isBlank(org.getOrgName()) && organizationName == null) {
			OrganizationName orgName = new OrganizationName();
			orgName.setOrgId(org.getId());
			orgName.setPy(org.getOrgName());
			insert(orgName);
		}
        if(organizationName != null){
            organizationName.setPy(org.getOrgName());
           this.update(organizationName);
        }
	}
	
}
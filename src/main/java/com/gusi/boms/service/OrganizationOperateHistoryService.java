package com.gusi.boms.service;

import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationOperateHistory;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
* @ClassName: OrganizationOperateHistoryService 
* @Description: OrganizationOperateHistory业务逻辑
* @author fdj
* @date 2013-4-10 下午3:18:56
 */
@Service
public class OrganizationOperateHistoryService extends BaseService<OrganizationOperateHistory> {
	
	@Autowired
	private Organization2Service organization2Service;
	
	/**
	 * 根据组织orgId查询操作记录
	 * @param orgId
	 * @return
	 */
	public List<OrganizationOperateHistory> queryForListByOrgId(int orgId) {
		return queryForList(sqlId("queryForListByOrgId"), orgId);
	}
	
    /**
     * 保存组织信息变更记录
     * @param organization 编辑后的组织基础信息对象
     * @param organizationOperateHistory 组织操作记录对象
     * @return
     */
    public boolean saveOperateHistory(Organization organization, OrganizationOperateHistory organizationOperateHistory){
    	Organization oldOrganization = organization2Service.findById(organization.getId());
    	organizationOperateHistory.setRemark(oldOrganization.getOperateRemark(organization));
        return StringUtils.isBlank(organizationOperateHistory.getRemark()) || insert(organizationOperateHistory);
    }
}

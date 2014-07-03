package com.gusi.boms.service;

import com.gusi.boms.model.OrganizationOrgCount;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @Author: fdj
 * @Since: 2014-04-16 09:48
 * @Description: 组织组数的业务
 */
@Service
public class OrganizationOrgCountService extends BaseService<OrganizationOrgCount> {

    /**
     * 是否存在
     * @param orgId
     * @return
     */
    public boolean isExistByOrgId(int orgId) {
        return count(sqlId("isExistByOrgId"), orgId)>0;
    }

    /**
     * 插入记录
     * @param orgId
     * @param maxCount
     * @param creator
     */
    public void insert(int orgId, int maxCount, int creator) {
        OrganizationOrgCount organizationOrgCount = new OrganizationOrgCount();
        organizationOrgCount.setOrgId(orgId);
        organizationOrgCount.setMaxCount(maxCount);
        organizationOrgCount.setCreator(creator);
        this.insert(organizationOrgCount);
    }

    /**
     * 更新记录
     * @param orgId
     * @param maxCount
     * @param updator
     */
    public boolean update(int orgId, int maxCount, int updator) {
        OrganizationOrgCount organizationOrgCount = new OrganizationOrgCount();
        organizationOrgCount.setOrgId(orgId);
        organizationOrgCount.setMaxCount(maxCount);
        organizationOrgCount.setUpdator(updator);
        return this.update(organizationOrgCount);
    }

}

package com.gusi.app.service;

import com.gusi.app.model.AppOrganization;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-18 下午5:37
 * @Description: 通讯录组织业务逻辑
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AppOrganizationService extends BaseService<AppOrganization> {

    /**
     * 根据组织id查找该组织树下的所有组织
     * @param orgId 部门id
     * @return
     */
    public List<AppOrganization> queryForTreeByOrgId(int orgId) {
        return queryForList(sqlId("queryForTreeByOrgId"), orgId);
    }

}

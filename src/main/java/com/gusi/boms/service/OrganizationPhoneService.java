package com.gusi.boms.service;

import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationPhone;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
* @ClassName: OrganizationPhoneService 
* @Description: 组织部门电话的业务逻辑
* @author fdj
* @date 2013-4-10 上午10:13:24
 */
@Service
public class OrganizationPhoneService extends BaseService<OrganizationPhone> {

    @Autowired
    private Organization2Service organization2Service;

	/**
	 * 添加电话号码
     * 1、更新组织表中的电话和传真
     * 2、删除电话表中原有的电话
     * 3、插入组织表中的电话
	 * @param list 要插入的电话集合
	 */
	@Transactional
	public void addPhoneList(List<OrganizationPhone> list, Organization organization) {
        organization2Service.updateContactInfo(organization);
        deleteByOrgId(organization.getId());
        if(list != null && list.size() > 0) {
            for (OrganizationPhone orgPhone : list) {
                if(orgPhone.getPhone() != null) {
                    orgPhone.setOrgId(organization.getId());
                    insert(orgPhone);
                }
			}
		}
	}
	
	/**
	 * 根据组织部门的id删除电话
	 * @param orgId 组织id
	 * @return
	 */
	public boolean deleteByOrgId(int orgId) {
		return delete(sqlId("deleteByOrgId"), orgId);
	}

	/**
	 * 根据组织部门id查找电话
	 * @param orgId
	 * @return
	 */
	public List<OrganizationPhone> queryForListByOrgId(int orgId) {
		return queryForList(sqlId("queryForListByOrgId"), orgId);
	}
	
}
package com.gusi.boms.service;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationHeaderHistory;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: OrganizationHeaderHistoryService 
* @Description: OrganizationHeaderHistory业务逻辑
* @author fdj
* @date 2013-4-10 下午3:18:32
 */
@Service
public class OrganizationHeaderHistoryService extends BaseService<OrganizationHeaderHistory> {

    @Autowired
    private EmployeeService employeeService;

	/**
	 * 根据organizationHeaderHistory相关信息，将状态更改为离任
	 * @param organizationHeaderHistory
	 * @return
	 */
	public boolean relieve(OrganizationHeaderHistory organizationHeaderHistory) {
		organizationHeaderHistory.setStatus(Constants.ORGOPERATEHISTORY_STATUS_RELIEVE);
		return update(sqlId("relieve"), organizationHeaderHistory);
	}
	
	/**
	 * @param userCode
	 * @return 根据工号将此人的所有负责人记录更改为离任
	 */
	public boolean relieveByUserCode(int userCode)
	{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userCode", userCode);
		params.put("status", Constants.ORGOPERATEHISTORY_STATUS_RELIEVE);
		return update(sqlId("relieveByUserCode"), params);
	}
	/**
	 * 根据organizationHeaderHistory相关信息，新增一条负责人记录
	 * @param organizationHeaderHistory
	 * @return
	 */
	public boolean add(OrganizationHeaderHistory organizationHeaderHistory) {
		organizationHeaderHistory.setStatus(Constants.ORGOPERATEHISTORY_STATUS_NORMAL);
		return insert(organizationHeaderHistory);
	}
	
	/**
	 * 根据组织orgId查询负责人记录
	 * @param orgId
	 * @return
	 */
	public List<OrganizationHeaderHistory> queryForListByOrgId(int orgId) {
		return queryForList(sqlId("queryForListByOrgId"), orgId);
	}

    /**
     * 添加负责人历史
     * @param org 组织
     */
    public void insertHeaderHistory(Organization org) {
        if(org.getManager() != null && !org.getManager().equals(0)) {
            Employee emp = employeeService.getEmployee(org.getManager());
            OrganizationHeaderHistory ohh = new OrganizationHeaderHistory(org.getId(), emp.getPositionId(), emp.getUserCode(), emp.getUserName());
            add(ohh);
        }
    }

    /**
     * 更新负责人历史
     * @param org 组织
     */
    public void updateHeaderHistory(Organization org) {
        OrganizationHeaderHistory ohh = findNormalByOrgId(org.getId());
        if(org.getManager() != null && !org.getManager().equals(0)) {
            if(ohh == null) {
                insertHeaderHistory(org);
            } else if(org.getManager() != ohh.getUserCode()){
                Employee emp = employeeService.getEmployee(org.getManager());
                relieve(new OrganizationHeaderHistory(ohh.getOrgId(), ohh.getPositionId(), ohh.getUserCode(), ohh.getUserNameCn()));
                add(new OrganizationHeaderHistory(org.getId(), emp.getPositionId(), emp.getUserCode(), emp.getUserName()));
            }
        } else {
            if(ohh != null) {
                relieve(new OrganizationHeaderHistory(ohh.getOrgId(), ohh.getPositionId(), ohh.getUserCode(), ohh.getUserNameCn()));
            }
        }

    }

    /**
     * 查找现任正常的负责人
     * @param orgId 组织id
     * @return
     */
    private OrganizationHeaderHistory findNormalByOrgId(int orgId) {
        return this.findByBean(sqlId("findNormalByOrgId"), orgId);
    }

}

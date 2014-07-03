package com.gusi.boms.service;

import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationLog;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * @Author: fdj
 * @Create: fdj (2013-05-29 17:05)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrganizationLogService extends BaseService<OrganizationLog> {

    @Autowired
    private Organization2Service org2Service;

    /**
     * 插入组织日志
     * @param org 组织
     * @param emp 操作人
     * @param operateType 操作类型
     */
    public void insertOrgLog(Organization org, Employee emp, String operateType) {
        OrganizationLog orgLog = getOrgLogByOrg(org2Service.findById(org.getId()));
        orgLog.setOperator(emp.getUserCode());
        orgLog.setOperateType(operateType);
        insert(orgLog);
    }

    public OrganizationLog getOrgLogByOrg(Organization org) {
        OrganizationLog orgLog = new OrganizationLog();
        orgLog.setId(org.getId());
        orgLog.setParentId(org.getParentId());
        orgLog.setOrgCode(org.getOrgCode());
        orgLog.setOrgLongCode(org.getOrgLongCode());
        orgLog.setOrgName(org.getOrgName());
        orgLog.setOrgLevel(org.getOrgLevel());
        orgLog.setOrgType(org.getOrgType());
        orgLog.setStatus(org.getStatus());
        orgLog.setLocIndex(org.getLocIndex());
        orgLog.setAddress(org.getAddress());
        orgLog.setOrgPhone(org.getOrgPhone());
        orgLog.setOrgFax(org.getOrgFax());
        orgLog.setUpdateTime(org.getUpdateTime());
        orgLog.setUpdator(org.getUpdator());
        orgLog.setCreateTime(org.getCreateTime());
        orgLog.setCreator(org.getCreator());
        orgLog.setOpenDate(org.getOpenDate());
        orgLog.setClosedDate(org.getClosedDate());
        orgLog.seteOrgName(org.geteOrgName());
        orgLog.setManager(org.getManager());
        orgLog.setOrgClass(org.getOrgClass());
        orgLog.setCompany(org.getCompany());
        orgLog.setRemark(org.getRemark());
        return orgLog;
    }

}

package com.gusi.boms.service;

import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.model.RoleOrganizationLog;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-25 16:06)
 * Description: To change this template use File | Settings | File Templates.
 */
@Service
public class RoleOrganizationLogService extends BaseService<RoleOrganizationLog> {
    public void insertLog(Map<String,Object> m){
        RoleOrganizationLog roleOrganizationLog = new RoleOrganizationLog();
        roleOrganizationLog.setCreator(Integer.parseInt(m.get("creator").toString()));
        roleOrganizationLog.setOrgId(Integer.parseInt(m.get("orgId").toString()));
        List<String> roleIds = this.queryForListStr(sqlId("queryRoleIdByAppId"),m);
        if(roleIds == null || roleIds.isEmpty()){
            if(StringUtils.isBlank(m.get("roleIds").toString())){
                return ;
            }
            roleOrganizationLog.setRoleIdStr("");
            roleOrganizationLog.setOperateType(OperateType.insert.toString());
        }else{
            roleOrganizationLog.setRoleIdStr(roleIds.toString());
            if(StringUtils.isBlank(m.get("roleIds").toString())){
                roleOrganizationLog.setOperateType(OperateType.delete.toString());
            }else{
                roleOrganizationLog.setOperateType(OperateType.update.toString());
            }
        }
        this.insert(roleOrganizationLog);
    }

}

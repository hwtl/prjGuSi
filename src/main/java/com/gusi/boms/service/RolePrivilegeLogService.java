package com.gusi.boms.service;

import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.model.RolePrivilege;
import com.gusi.boms.model.RolePrivilegeLog;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-25 16:48)
 * Description: 角色对应权限操作的日志记录业务
 */
@Service
public class RolePrivilegeLogService extends BaseService<RolePrivilegeLog> {
    @Autowired
    RolePrivilegeService rolePrivilegeService;
    @Transactional
    public void insertLog(int creator,List<RolePrivilege> rps,int roleId){
        RolePrivilegeLog rolePrivilegeLog  = new RolePrivilegeLog();
        rolePrivilegeLog.setCreator(creator);
        rolePrivilegeLog.setRoleId(roleId);
        List<RolePrivilege> rps2  =rolePrivilegeService.getRolePrivilegesByRoleId(roleId);
        if(rps2 == null || rps2.isEmpty()){
            if(rps.isEmpty()){
                return ;
            }
            rolePrivilegeLog.setDataPrivilegeStr("");
            rolePrivilegeLog.setPrivilegeIdStr("");
            rolePrivilegeLog.setOperateType(OperateType.insert.toString());
        }else{
            rolePrivilegeLog.setDataPrivilegeStr(getDataPrivilegeStr(rps2));
            rolePrivilegeLog.setPrivilegeIdStr(getPrivilegeIdStr(rps2));
            if(rps.isEmpty()){
                rolePrivilegeLog.setOperateType(OperateType.delete.toString());
            }else{
                rolePrivilegeLog.setOperateType(OperateType.update.toString());
            }
        }
        this.insert(rolePrivilegeLog);
    }

    private String getPrivilegeIdStr(List<RolePrivilege> rolePrivileges){
        StringBuilder sb = new StringBuilder("[");
        for (RolePrivilege rolePrivilege : rolePrivileges){
            sb.append(rolePrivilege.getPrivilegeId()).append(",");
        }
        StringBuilderHelper.trimLast(sb,",").append("]");
        if("[]".equals(sb.toString())){
            return "";
        }else{
            return sb.toString();
        }
    }

    private String getDataPrivilegeStr(List<RolePrivilege> rolePrivileges){
        StringBuilder sb = new StringBuilder("[");
        for (RolePrivilege rolePrivilege : rolePrivileges){
            sb.append(rolePrivilege.getDataPrivilege()).append(",");
        }
        StringBuilderHelper.trimLast(sb,",").append("]");
        if("[]".equals(sb.toString())){
            return "";
        }else{
            return sb.toString();
        }
    }

}

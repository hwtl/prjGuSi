package com.gusi.boms.service;

import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.model.RolePositionLog;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-25 15:24)
 * Description:角色岗位日志记录操作业务逻辑
 */
@Service
public class RolePositionLogService extends BaseService<RolePositionLog> {

    public void insertLog(Map<String,Object> m){
        RolePositionLog rolePositionLog = new RolePositionLog();
        rolePositionLog.setCreator(Integer.parseInt(m.get("creator").toString()));
        rolePositionLog.setPositionId(Integer.parseInt(m.get("positionId").toString()));
        List<String> roleIds = this.queryForListStr(sqlId("queryRoleIdByAppId"),m);
        if(roleIds == null || roleIds.isEmpty()){
            if(StringUtils.isBlank(m.get("roleIds").toString())){
                return ;
            }
            rolePositionLog.setRoleIdStr("");
            rolePositionLog.setOperateType(OperateType.insert.toString());
        }else{
            rolePositionLog.setRoleIdStr(roleIds.toString());
            if(StringUtils.isBlank(m.get("roleIds").toString())){
                rolePositionLog.setOperateType(OperateType.delete.toString());
            }else{
                rolePositionLog.setOperateType(OperateType.update.toString());
            }
        }
        this.insert(rolePositionLog);
    }
}

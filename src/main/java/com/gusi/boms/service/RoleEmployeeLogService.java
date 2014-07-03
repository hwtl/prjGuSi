package com.gusi.boms.service;

import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.model.RoleEmployeeLog;
import com.dooioo.web.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-19 17:50)
 * Description: To change this template use File | Settings | File Templates.
 */
@Service
public class RoleEmployeeLogService extends BaseService<RoleEmployeeLog> {
    @Transactional
    public void insertLog(Map<String,Object> m){
        RoleEmployeeLog roleEmployeeLog  = new RoleEmployeeLog();
        roleEmployeeLog.setCreator(Integer.parseInt(m.get("creator").toString()));
        roleEmployeeLog.setUserCode(Integer.parseInt(m.get("userCode").toString()));
        List<String> roleIds = this.queryForListStr(sqlId("queryRoleIdByAppId"),m);
        if(roleIds == null || roleIds.isEmpty()){
            if(StringUtils.isBlank(m.get("roleIds").toString())){
                return ;
            }
            roleEmployeeLog.setOperateType(OperateType.insert.toString());
            roleEmployeeLog.setRoleIdStr("[appId:" + m.get("appId") + "][" + m.get("roleIds").toString()+"]");
        }else{
            roleEmployeeLog.setRoleIdStr(roleIds.toString());
            if(StringUtils.isBlank(m.get("roleIds").toString())){
                roleEmployeeLog.setOperateType(OperateType.delete.toString());
            }else{
                roleEmployeeLog.setOperateType(OperateType.update.toString());
            }
            roleEmployeeLog.setRoleIdStr("[appId:" + m.get("appId") + "]" + roleEmployeeLog.getRoleIdStr());
        }
        this.insert(roleEmployeeLog);
    }
    /**
      * @since: 2.7.4
      * @param userCode 工号
      * @param operateType 操作类型
      * @param roleId 角色ID
      * <p>
      *  api调用新增日志
      * </p>   
     */
    @Async
    public void apiInsertLog(int userCode,String operateType,int roleId){
    	RoleEmployeeLog log=new RoleEmployeeLog();
    	log.setCreator(80001);
    	log.setOperateType(operateType);
    	log.setRoleIdStr(roleId+"");
    	log.setUserCode(userCode);
    	this.insert(log);
    }
}

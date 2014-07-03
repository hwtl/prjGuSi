package com.gusi.boms.service;

import com.gusi.boms.model.OrganizationEmployeeCount;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-11-27 下午1:49
 * @Description: 员工编制表业务
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrganizationEmployeeCountService extends BaseService<OrganizationEmployeeCount> {

    /**
     * 根据id判断是否手动添加的记录存在记录
     * @update: 2014-06-03 11:46:00 添加了条件 limitType = 1 and status = 1
     * @param orgId
     * @return
     */
    public boolean isExistByOrgId(int orgId) {
        return this.count(sqlId("isExistByOrgId"), orgId) > 0;
    }

    /**
     * 根据该部门下面的在职人数，添加人数限制
     * @since: 2014-06-03 11:08:34
     * @param orgId
     * @return
     */
    public boolean insertByOrgId(int orgId, Date orgEndTime, int creator) {
        Map<String, Object> param = new HashMap<>();
        param.put("orgId", orgId);
        param.put("creator", creator);
        param.put("orgEndTime", orgEndTime);
        return insert(sqlId("insertByOrgId"), param);
    }

    /**
     * 更新过期数据的状态
     * @since: 2014-06-03 11:58:55
     * @return
     */
    public boolean updateOverdue() {
        return this.update(sqlId("updateOverdue"), 0);
    }

    /**
     * 查询暂停分行的浮动人数列表
     * @since: 2014-06-09 14:20:01
     * @param id
     * @return
     */
    public List<OrganizationEmployeeCount> queryStopBranchEmpNo(int id) {
        return this.queryForList(sqlId("queryStopBranchEmpNo"), id);
    }

}

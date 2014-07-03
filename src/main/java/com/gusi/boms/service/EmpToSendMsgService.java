package com.gusi.boms.service;

import com.gusi.boms.dto.EmpToSendMsg;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-04-23 17:55
 * @Description: EmpToSendMsgService
 */
@Service
public class EmpToSendMsgService extends BaseService<EmpToSendMsg> {

    /**
     * 查询区总
     * @param areaOrgLongCode
     * @return
     */
    public EmpToSendMsg findAreaUser(String areaOrgLongCode) {
        return (EmpToSendMsg)queryForObject(sqlId("findAreaUser"), areaOrgLongCode);
    }

    /**
     * 根据工号查询员工信息
     * @param users 例如：90378,89695
     * @return
     */
    public List<EmpToSendMsg> findUsers(String users) {
        return queryForList(sqlId("findUsers"), users);
    }

}

package com.gusi.boms.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.model.EmployeePasswordInfo;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fdj
 * @Create: 14-1-21 上午11:45
 * @Description: 密码信息操作业务
 */
@Service
public class EmployeePasswordInfoService extends BaseService<EmployeePasswordInfo> {

    /**
     * 根据工号查找，返回需要更新的密码对象
     * @param userCode
     * @return
     */
    public EmployeePasswordInfo checkPwd(int userCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("warningDate", Configuration.getInstance().getPwdOutOfDate() - Configuration.getInstance().getPwdNoticeDate());
        return findByBean(sqlId("checkPwd"), param);
    }

    /**
     * 根据工号查找，密码是否将要过期
     * @param userCode
     * @return
     */
    public boolean isExistsOutOfDate(int userCode) {
        return this.checkPwd(userCode) != null;
    }

    /**
     * 根据工号查找该员工最近一条密码操作记录
     * @param userCode
     * @return
     */
    public EmployeePasswordInfo findRecent(int userCode) {
        return findByBean(sqlId("findRecent"), userCode);
    }

    /**
     * 根据工号查询是否存在该员工的密码操作记录
     * @param userCode
     * @return
     */
    public boolean isExistsRecord(int userCode) {
        return findByBean(sqlId("isExistsRecord"), userCode) != null;
    }

}

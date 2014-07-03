package com.gusi.empTransfer.service;

import com.gusi.empTransfer.dto.EmpTransferEmp;
import com.dooioo.web.service.BaseService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-02-20 11:40
 * @Description: 转调员工业务
 */
@Service
public class EmpTransferEmpService extends BaseService<EmpTransferEmp> {

    private static final Log LOG = LogFactory.getLog(EmpTransferEmpService.class);

    public List<EmpTransferEmp> search(String keyword, String company) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("company", company);
        return this.queryForList(sqlId("search"), params);
    }

    /**
     * 是否有异动未生效
     * @param userCode
     * @return
     */
    public boolean isChanging(int userCode) {
        return count(sqlId("isChanging"), userCode) > 0;
    }

    /**
     * 是否离职三个月内
     * @param userCode
     * @return
     */
    public boolean isLeavingIn3month(int userCode) {
        return count(sqlId("isLeavingIn3month"), userCode) > 0;
    }

}

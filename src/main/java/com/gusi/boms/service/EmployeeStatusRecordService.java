package com.gusi.boms.service;

import com.gusi.boms.model.EmployeeDetails;
import com.gusi.boms.model.EmployeeStatusRecord;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-8-30 上午10:03
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EmployeeStatusRecordService extends BaseService<EmployeeStatusRecord> {

    /**
     * 更新状态为已发送头部消息
     * @param employeeDetailsList
     * @return
     */
    public boolean updateByUserCodesToOver(List<EmployeeDetails> employeeDetailsList) {
        Map<String,Object> params=new HashMap<>();
        params.put("employeeDetailsList", employeeDetailsList);
        return this.update(sqlId("updateByUserCodesToOver"), params);
    }

}

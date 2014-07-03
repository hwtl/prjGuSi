package com.gusi.empTransfer.service;

import java.util.List;
import java.util.Map;

import com.gusi.empTransfer.model.EmployeeTransferTaskStatus;
import org.springframework.stereotype.Service;

import com.dooioo.web.service.BaseService;

/**
 * @Author: fdj
 * @Since: 2014-03-05 10:57
 * @Description: EmployeeTransferTaskStatusService
 */
@Service
public class EmployeeTransferTaskStatusService extends BaseService<EmployeeTransferTaskStatus> {

    public List<EmployeeTransferTaskStatus> findByMap(Map<String, Object> variables) {
    	List<EmployeeTransferTaskStatus> employeeTransfers = queryForList(sqlId("findProcessByMap"), variables);
    	return employeeTransfers;
    }
}
package com.gusi.empTransfer.service;

import com.gusi.BaseTest;
import com.gusi.boms.util.DateFormatUtil;
import com.gusi.empTransfer.helper.EmployeeTransferHelper;
import com.gusi.empTransfer.model.EmployeeTransfer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-03-31 17:45
 * @Description: EmployeeTransferServiceTest
 */
public class EmployeeTransferServiceTest extends BaseTest {

    @Autowired
    private EmployeeTransferService employeeTransferService;

    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < 100; i ++) {
            EmployeeTransfer employeeTransfer = new EmployeeTransfer();
            employeeTransfer.setUserCode(90378);
            employeeTransfer.setOldOrgId(1);
            employeeTransfer.setNewOrgId(1);
            employeeTransfer.setStatus(-1);
            employeeTransfer.setYdType("转调");
            employeeTransfer.setTransferType("201402201");
            employeeTransfer.setTransferState(1);
            //初始化各个属性值
            EmployeeTransferHelper.init(employeeTransfer);
            employeeTransfer.setCreator(90378);
            employeeTransfer.setCreateTime(new Date());
            employeeTransfer.setTransferNo(employeeTransfer.getUserCode() + "-" + DateFormatUtil.getString(employeeTransfer.getCreateTime(), "yyMMdd") + "-" + DateFormatUtil.getString(employeeTransfer.getCreateTime(), "HHmmss"));
            //插入转调信息
            employeeTransfer.setId(employeeTransferService.insertAndReturnId(employeeTransfer));
            //插入转调交接信息
            employeeTransferService.insertProcess(employeeTransfer);
        }

    }
}

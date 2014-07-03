package com.gusi.empTransfer.service;

import com.gusi.BaseTest;
import com.dooioo.plus.oms.dyEnums.Company;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: fdj
 * @Since: 2014-03-11 13:43
 * @Description: EmpTransferEmpServiceTest
 */
public class EmpTransferEmpServiceTest extends BaseTest {

    @Autowired
    private EmpTransferEmpService empTransferEmpService;

    @Test
    public void testSearch() {
        System.out.println(empTransferEmpService.search("86", Company.Dooioo.toString()));
    }
}

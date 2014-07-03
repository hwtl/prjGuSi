package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.model.EmployeeDetails;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: fdj
 * @Since: 2014-04-03 09:26
 * @Description: EmployeeDetailsServiceTest
 */
public class EmployeeDetailsServiceTest extends BaseTest {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @Test
    public void testQueryByWebFlag() throws Exception {
        for(EmployeeDetails d : employeeDetailsService.queryByWebFlag(1)) {
            System.out.println(d);
        }
    }
}

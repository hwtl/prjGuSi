package com.gusi.activemq.helper;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: fdj
 * @Since: 2014-04-24 09:59
 * @Description: EmployeeSenderHelperTest
 */
public class EmployeeSenderHelperTest extends BaseTest {

    @Autowired
    private EmployeeSenderHelper employeeSenderHelper;

    @Test
    public void testSendEmployeeBianJi() throws Exception {
        employeeSenderHelper.sendEmployeeBianJi(104923, 21389);
    }
}

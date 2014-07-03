package com.gusi.boms.service;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-2 下午2:47
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class TestChangeService extends BaseTest {

    @Autowired
    private EmployeeChangeRecordsService employeeChangeService;

    @Test
    public void test() {
        employeeChangeService.doRollback(15732, 93373,81637);
    }

}

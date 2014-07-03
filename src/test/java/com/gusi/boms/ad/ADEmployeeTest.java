package com.gusi.boms.ad;

import com.gusi.BaseTest;
import com.dooioo.plus.other.service.DyADService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: ADEmployeeTest
 * @Author: fdj
 * @Since: 2014-02-08 10:55
 */
public class ADEmployeeTest extends BaseTest {

    @Autowired
    private DyADService dyADService;

    @Test
    public void testCheck() {
        boolean rs = dyADService.checkUser(87670, "35652u");
        System.out.println(rs);
    }

}

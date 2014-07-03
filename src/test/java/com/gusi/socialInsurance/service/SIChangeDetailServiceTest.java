package com.gusi.socialInsurance.service;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: fdj
 * @Since: 2014-06-12 12:00
 * @Description: SIChangeDetailServiceTest
 */
public class SIChangeDetailServiceTest extends BaseTest {

    @Autowired
    private SIChangeDetailService siChangeDetailService;

    @Test
    public void testQuerySIChangeDetailList() throws Exception {
        System.out.println(siChangeDetailService.querySIChangeDetailList("2016", "2015"));
    }
}

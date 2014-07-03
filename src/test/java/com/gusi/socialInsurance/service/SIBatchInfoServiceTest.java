package com.gusi.socialInsurance.service;

import com.gusi.BaseTest;
import com.gusi.boms.common.Constants;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-06-12 16:24
 * @Description: SIBatchInfoServiceTest
 */
public class SIBatchInfoServiceTest extends BaseTest {

    @Autowired
    private SIBatchInfoService siBatchInfoService;

    @Test
    public void testSHNew001() {
    	
    }

    @Test
    public void testSHQuit001() {
		siBatchInfoService.generateSHQuit(Constants.GUANLIYUAN,  new Date());
    }

    @Test
    public void testQueryForList() throws Exception {

    }

    @Test
    public void testFindBatchInfo() throws Exception {

    }

    @Test
    public void testFindBatchInfoNBQuit() throws Exception {

    }

    @Test
    public void testConfirmStatus() throws Exception {
//        System.out.println(siBatchInfoService.confirmStatus("1"));
    }
}

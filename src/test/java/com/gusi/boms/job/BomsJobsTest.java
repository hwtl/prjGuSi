package com.gusi.boms.job;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: fdj
 * @Since: 2014-04-03 10:28
 * @Description: BomsJobsTest
 */
public class BomsJobsTest extends BaseTest {

    @Autowired
    private BomsJobs bomsJobs;

    @Test
    public void testDoJobSendWebMsg() throws Exception {
        bomsJobs.doJobSendWebMsg();
    }

    @Test
    public void testDoActiveChangeRecordsJob() {
        bomsJobs.doActiveChangeRecordsJob();
    }

}

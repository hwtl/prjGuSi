package com.gusi.empTransfer.service;

import com.gusi.BaseTest;
import org.junit.Test;

import java.util.Calendar;

/**
 * @Author: fdj
 * @Since: 2014-03-20 18:24
 * @Description: testDate
 */
public class testDate extends BaseTest {

    @Test
    public void test() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        System.out.println(c.getTime());
    }

}

package com.gusi.boms.helper;

import com.gusi.BaseTest;
import com.gusi.boms.dto.SmsReports;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-04-03 10:07
 * @Description: SmsApiHelperTest
 */
public class SmsApiHelperTest extends BaseTest {
    @Test
    public void testSendMsg() throws Exception {
        List<SmsReports> smsReportsList = SmsApiHelper.sendMsg("15618669753,13917849112,13564208820,13816614208");
        for(SmsReports s : smsReportsList) {
            System.out.println(s);
        }
    }

    @Test
    public void testCalendar() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 0);
        System.out.println(DateFormatUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd 09:30:00"));
    }

}

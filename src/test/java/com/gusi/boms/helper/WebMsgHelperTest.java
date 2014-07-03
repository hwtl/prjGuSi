package com.gusi.boms.helper;

import com.gusi.BaseTest;
import com.dooioo.plus.oms.dyEnums.Company;
import org.junit.Test;

/**
 * @Author: fdj
 * @Since: 2014-04-03 10:39
 * @Description: WebMsgHelperTest
 */
public class WebMsgHelperTest extends BaseTest {
    @Test
    public void testSendWebMsg() throws Exception {
        WebMsgHelper.sendWebMsg(90378, "试一试", Company.Dooioo);
    }
}

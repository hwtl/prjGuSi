package com.gusi.boms.helper;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: AdHelperTest
 * @Author: fdj
 * @Since: 2014-02-12 17:44
 */
public class AdHelperTest extends BaseTest {

    @Autowired
    private AdHelper adHelper;

    @Test
    public void testSyncOU() {
        adHelper.syncOU(20540);
    }
}

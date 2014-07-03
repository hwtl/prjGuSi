package com.gusi;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
 * @Title: TestBase.java  
 * @Package com.dooioo 
 * @Description: 测试用基类
 * @author Jail    E -Mail:86455@ dooioo.com 
 * @date 2012-12-5 下午3:57:58 
 * @version V1.0 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml",
        "classpath*:plusOMSContext.xml",
        "classpath:plus/smsContext.xml",
        "classpath*:activitiSpringContext.xml",
        "classpath*:activiti.cfg.xml"})
public class BaseTest {

}
  
    
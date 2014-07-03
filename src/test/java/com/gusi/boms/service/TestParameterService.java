package com.gusi.boms.service;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
* @ClassName: TestOrganizationService 
* @Description: TestOrganizationService
* @author fdj
* @date 2013-4-10 上午10:54:09
 */
public class TestParameterService extends BaseTest {
	@Autowired
    ParameterService  parameterService ;
    @Test
	  public  void testA(){
//         ParameterService parameterService = new ParameterService();
         System.out.println(parameterService.findParameterMap().size());
     }
}

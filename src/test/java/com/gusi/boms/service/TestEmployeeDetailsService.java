package com.gusi.boms.service;

import java.util.List;

import com.gusi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gusi.boms.model.EmployeeDetails;
import com.dooioo.webplus.convenient.LunarSolarUtil;

/**
 *
* @ClassName: TestOrganizationService
* @Description: TestOrganizationService
* @author fdj
* @date 2013-4-10 上午10:54:09
 */
public class TestEmployeeDetailsService extends BaseTest {
	@Autowired
	private EmployeeDetailsService  employeeDetailsService ;

    /**
      * @since: 2.7.1
      * <p>
      *  将档案农历生日转换为公历生日保存起来
      * </p>   
     */
    @Test
    public void syncNongliBirthToGongli(){
    	List<EmployeeDetails> emps=employeeDetailsService.findNongliBirthEmployee();
    	if (emps==null) {
			return;
		}
    	for (EmployeeDetails ed : emps) {
    		try {
    			employeeDetailsService.updateNongliBirthdayToGongli(ed.getUserCode(),
    					LunarSolarUtil.lunarToSolar(ed.getBornYear(), ed.getBornMonth(), ed.getBornDayTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}

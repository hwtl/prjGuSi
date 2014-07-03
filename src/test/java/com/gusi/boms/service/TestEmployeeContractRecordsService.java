package com.gusi.boms.service;

import com.gusi.BaseTest;
import com.gusi.boms.helper.EmployeeHelper;
import com.gusi.boms.model.EmployeeContractRecords;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-10 17:20)
 * Description: 合同记录单体测试
 */
public class TestEmployeeContractRecordsService extends BaseTest {
    @Autowired
    EmployeeContractRecordsService employeeContractRecordsService;
    private EmployeeContractRecords employeeContractRecords;
    @Before
    public void init(){
        employeeContractRecords = new EmployeeContractRecords();
        employeeContractRecords.setContractType(EmployeeContractRecords.NOT_REGULAR_CONTRACT);
        employeeContractRecords.setStartTime(new Date());
        employeeContractRecords.setCreator(86101);
        employeeContractRecords.setEndTime(EmployeeHelper.getConstractEndTime(new Date()));
        employeeContractRecords.setUserCode(91387);
        employeeContractRecords.setUpdator(80011);
        employeeContractRecords.setUpdateName("test_tessr");
        employeeContractRecords.setId(2393);
    }
    @Test
    public void testInsert(){
         assertTrue(employeeContractRecordsService.insert(employeeContractRecords));
    }

    @Test
    public void testSaveEmployeeContract(){
        assertTrue(employeeContractRecordsService.insertEmployeeContract(employeeContractRecords));
    }

    @Test
    public void updateEmployeeContract(){
        assertTrue(employeeContractRecordsService.updateEmployeeContract(employeeContractRecords));
    }
    @Test
    public void testDeteleEmployeeContract(){
        assertTrue(employeeContractRecordsService.delete(2392));
    }
}

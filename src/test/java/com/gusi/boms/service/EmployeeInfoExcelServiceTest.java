package com.gusi.boms.service;

import java.io.File;
import java.io.FileOutputStream;

import com.gusi.BaseTest;
import com.gusi.boms.model.EmployeeSearch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeInfoExcelServiceTest extends BaseTest {

	@Autowired
	private EmployeeInfoExcelService employeeInfoExcelService;

	@Test
	public void testExport() throws Exception {
		EmployeeSearch employeeSearch=new EmployeeSearch();
		employeeSearch.setKeyWords("95473");
        employeeSearch.setCompany("德佑");
        employeeSearch.setExcelHeads("hi");
        employeeSearch.setExportKey("comeFromStr");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\员工信息.xls"));
        employeeInfoExcelService.export(employeeSearch,fileOutputStream);
	}
}

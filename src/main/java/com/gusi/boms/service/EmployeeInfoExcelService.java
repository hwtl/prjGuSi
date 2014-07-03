package com.gusi.boms.service;

import java.io.OutputStream;
import java.util.List;

import com.gusi.boms.model.EmployeeInfoExcel;
import com.gusi.boms.model.EmployeeSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gusi.boms.helper.EmployeeHelper;
import com.gusi.boms.util.CommonUtils;
import com.gusi.boms.util.ExportExcelUtil;
import com.dooioo.web.service.BaseService;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-17 15:27)
 * Description: 员工信息导出业务
 */
@Service
public class EmployeeInfoExcelService extends BaseService<EmployeeInfoExcel> {
    @Autowired
    ParameterService parameterService;
    public void export(EmployeeSearch employeeSearch,OutputStream outputStream) throws Exception{
        ExportExcelUtil<EmployeeInfoExcel> ex = new ExportExcelUtil<EmployeeInfoExcel>();
        List<EmployeeInfoExcel> list = queryForList(sqlId("export"), EmployeeHelper.getWhere(employeeSearch).replace("''","'"));
       ex.writeExcel(list,outputStream,"员工信息", CommonUtils.getArray(employeeSearch.getExcelHeads()),CommonUtils.getArray(employeeSearch.getExportKey()));
    }
}

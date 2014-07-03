package com.gusi.boms.helper;

import com.gusi.boms.model.EmployeeDetails;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-04-03 09:45
 * @Description: PhoneHelper
 */
public class PhoneHelper {

    public static final String DOU_HAO = ",";

    /**
     * 拼接手机号码，逗号,隔开
     * @param employeeDetailsList
     * @return
     */
    public static String format(List<EmployeeDetails> employeeDetailsList) {
        if(CollectionUtils.isEmpty(employeeDetailsList)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(EmployeeDetails d : employeeDetailsList) {
            if(!StringUtils.isEmpty(d.getMobilePhone())) {
                sb.append(DOU_HAO + d.getMobilePhone());
            }
        }
        return sb.deleteCharAt(0).toString();
    }

}

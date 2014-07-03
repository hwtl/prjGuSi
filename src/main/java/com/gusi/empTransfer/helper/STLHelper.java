package com.gusi.empTransfer.helper;

import com.gusi.boms.model.Select;
import com.gusi.boms.service.TitleSerialService;
import com.gusi.empTransfer.common.EmpTransferConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-02-26 10:11
 * @Description: 职系职等职级帮助类
 */
@Component
public class STLHelper {

    @Autowired
    private TitleSerialService titleSerialService;

    /**
     * 根据转调类型获取不同职级
     * @param transferType
     * @return
     */
    public List<Select> initSerial(String transferType) {
        List<Select> selectList = new ArrayList<>();
        switch (transferType) {
            case EmpTransferConstants.ZJB_CODE:
                selectList.add(new Select(String.valueOf(EmpTransferConstants.SERIALID_2), titleSerialService.findById(EmpTransferConstants.SERIALID_2).getSerialName()));
                break;
            case EmpTransferConstants.ZLB_CODE:
                selectList.add(new Select(String.valueOf(EmpTransferConstants.SERIALID_2), titleSerialService.findById(EmpTransferConstants.SERIALID_2).getSerialName()));
                break;
            case EmpTransferConstants.XFXS_CODE:
                selectList.add(new Select(String.valueOf(EmpTransferConstants.SERIALID_2), titleSerialService.findById(EmpTransferConstants.SERIALID_2).getSerialName()));
                break;
            case EmpTransferConstants.DLXM_CODE:
                selectList.add(new Select(String.valueOf(EmpTransferConstants.SERIALID_4) ,titleSerialService.findById(EmpTransferConstants.SERIALID_4).getSerialName()));
                break;
            case EmpTransferConstants.HT_CODE:
                selectList.add(new Select(String.valueOf(EmpTransferConstants.SERIALID_1), titleSerialService.findById(EmpTransferConstants.SERIALID_1).getSerialName()));
                selectList.add(new Select(String.valueOf(EmpTransferConstants.SERIALID_3), titleSerialService.findById(EmpTransferConstants.SERIALID_3).getSerialName()));
                break;
        }
        return selectList;
    }

}

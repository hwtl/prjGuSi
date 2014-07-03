package com.gusi.boms.helper;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.Position;
import com.gusi.boms.model.PositionTitle;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 
 * @ClassName: OrganizationHelper 
 * @Description: 组织部门辅助类
 * @author fdj
 * @date 2013-5-22 下午2:19:51
 */
public class PositionHelper {

    /**
     * 根据不同状态获得不同按钮
     * @param position
     * @param employee
     * @return
     */
    public static String getOperateStr(Position position, Employee employee) {
        StringBuilder sb = new StringBuilder();
        if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_POSITION_ADD)) {
            sb.append("<a href=\"/position/" + position.getId() + "/edit\" class=\"btnOpH24 h24Silver in_block ml_5\">编辑</a>");
        }
        switch (position.getStatus()) {
            case 1:
                if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_POSITION_SWITCH)) {
                    sb.append("<a href=\"javascript:;\" positionId=\"" + position.getId() + "\" positionName=\"" + position.getPositionName() + "\" class=\"btnOpH24 h24Red in_block ml_5 mb_5 js_closePositionStepOne\">停用</a>");
                }
                return sb.toString();
            case 0:
                if (PrivilegeHelper.checkPrivilege(employee.getPrivileges(), Constants.OMS_OM_POSITION_SWITCH)) {
                    sb.append("<a href=\"javascript:;\" positionId=\"" + position.getId() + "\" positionName=\"" + position.getPositionName() + "\" class=\"btnOpH24 h24Green in_block ml_5 mb_5 js_openPositionStepOne\">启用</a>");
                }
                return sb.toString();
        }
        return "";
    }

    /**
     *
     * @param strs
     * @return
     */
    public static String[] getStrArray(String strs){
        if(!StringUtils.isBlank(strs)){
            if(strs.contains(",")){
                return  strs.split(",");
            }else{
                return  strs.split(" ");
            }
        }
        return new String[]{"0"};

    }

    public static String format(List<PositionTitle> positionTitles) {
        StringBuilder sb = new StringBuilder();
        if(positionTitles != null && positionTitles.size() > 0){
            for(PositionTitle positionTitle : positionTitles){
                sb.append(positionTitle.getTitleId()).append(",");
            }
            StringBuilderHelper.trimLast(sb,",");
        }
        return sb.toString();
    }
}

package com.gusi.boms.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.util
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-12 13:19)
 * Description: To change this template use File | Settings | File Templates.
 */
public class CommonUtils {
    public static String[] getArray(String strs){
        if(!StringUtils.isBlank(strs)){
            if(strs.contains(",")){
                return  strs.split(",");
            }else{
                return  strs.split(" ");
            }
        }
        return new String[]{"0"};
    }
}

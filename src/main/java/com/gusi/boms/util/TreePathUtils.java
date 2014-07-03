package com.gusi.boms.util;

import com.dooioo.plus.oms.dyEnums.Company;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.util
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-13 16:24)
 * Description: To change this template use File | Settings | File Templates.
 */
public class TreePathUtils {

    public static final String TREE_PREFIX ="js/tree/";
    public static final String TREE_SUFFIX ="Tree.js";
    public static final String ALL = "All";
    public static final String SALES ="Sales";
    public static final String SALESWITHOUTSTORE="SalesWithoutStore";
    public static final String SALESWITHOUTBRANCH = "SalesWithoutBranch";
    public static final String SUPPORT = "Support";

    public static String getPath(){
        String resourcePath = TreePathUtils.class.getResource("/").getPath() ;
        return resourcePath.replace("WEB-INF/classes/", "");
    }

    public static String getFilePrefix(String str){
        return "var "+str + " = ";
    }

    public static String getJsFileName(Company company, String str){
        return "/"+company.name().toLowerCase()+str+".js";
    }
}

package com.gusi.boms.bomsEnum;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.helper
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-08 14:25)
 * Description: To change this template use File | Settings | File Templates.
 */
public enum OperateType {
    /**
     * 员工基础信息变更
     */
    EmployeeBaseUpdate,
    /**
     * 员工异动信息操作
     */
    EmployeeBaseTransaction,
    /**
     * 新增操作
     */
    insert,
    /**
     * 删除操作
     */
    delete,
    /**
     * 更新操作
     */
    update;


    private static final String EMPLOYEEBASEUPDATE = "员工基础信息变更";
    private static final String EMPLOYEEBASETRANSACTION = "员工异动信息操作";
    private static final String INSERT = "新增操作";
    private static final String DELETE = "删除操作";
    private static final String UPDATE = "更新操作";

    @Override
    public String toString() {
        switch(this){
            case EmployeeBaseUpdate:return EMPLOYEEBASEUPDATE;
            case EmployeeBaseTransaction:return EMPLOYEEBASETRANSACTION;
            case insert:return INSERT;
            case delete:return DELETE;
            case update:return UPDATE;
            default:return "";
        }
    }
}

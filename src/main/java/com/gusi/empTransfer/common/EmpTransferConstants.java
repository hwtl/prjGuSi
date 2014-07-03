package com.gusi.empTransfer.common;

/**
 * @Author: fdj
 * @Since: 2014-03-07 13:56
 * @Description: 员工转调相关常量
 */
public class EmpTransferConstants {

    /**
     * 管理员工号
     */
    public static final int GUANLIYUAN_USERCODE = 80001;


    /**
     * 拼接审批权限字符
     */
    public static final String DEFAULT_PREFIX = "_";


    /**
     * 转调情况0 不选
     */
    public static final int EMPLOYEE_TRANSFER_STATE_0 = 0;
    /**
     * 转调情况1 主动
     */
    public static final int EMPLOYEE_TRANSFER_STATE_1 = 1;
    /**
     * 转调情况2 被动
     */
    public static final int EMPLOYEE_TRANSFER_STATE_2 = 2;


    /**
     * 交接情况0 不需要交接
     */
    public static final int EMPLOYEE_TRANSFER_STATUS_0 = 0;
    /**
     * 交接情况1 需要交接（未完成）
     */
    public static final int EMPLOYEE_TRANSFER_STATUS_1 = 1;
    /**
     * 交接情况2 需要交接（已完成）
     */
    public static final int EMPLOYEE_TRANSFER_STATUS_2 = 2;


    /**
     * 中介部参数对应的Code
     */
    public static final String ZJB_CODE = "201402201";
    /**
     * 租赁部参数对应的Code
     */
    public static final String ZLB_CODE = "201402202";
    /**
     * 新房销售部参数对应的Code
     */
    public static final String XFXS_CODE = "201402203";
    /**
     * 代理项目部参数对应的Code
     */
    public static final String DLXM_CODE = "201402204";
    /**
     * 后台部门参数对应的Code
     */
    public static final String HT_CODE = "201402205";


    /**
     * 转调记录对应的审批状态
     * 已作废
     */
    public static final int STATUS_D = -1;      //已作废
    /**
     * 转调记录对应的审批状态
     * 审批中
     */
    public static final int STATUS_1 = 1;       //审批中
    /**
     * 转调记录对应的审批状态
     * 待交接
     */
    public static final int STATUS_2 = 2;       //待交接
    /**
     * 转调记录对应的审批状态
     * 已完成
     */
    public static final int STATUS_3 = 3;       //已完成
    /**
     * 转调记录对应的审批状态
     * 被打回
     */
    public static final int STATUS_4 = 4;       //被打回
    /**
     * 转调记录对应的审批状态
     * 人事审批中
     */
    public static final int STATUS_5 = 5;       //人事审批中


    /**
     * 职系id
     * 行政管理职系
     */
    public static final int SERIALID_1 = 1; //行政管理职系
    /**
     * 职系id
     * 营销业务职系
     */
    public static final int SERIALID_2 = 2; //营销业务职系
    /**
     * 职系id
     * 行政技术职系
     */
    public static final int SERIALID_3 = 3; //行政技术职系
    /**
     * 职系id
     * 营销职系（代理部）
     */
    public static final int SERIALID_4 = 4; //营销职系（代理部）
    /**
     * 职系id
     * 营销管理职系
     */
    public static final int SERIALID_5 = 5; //营销管理职系


    /**
     * 审批意见（默认：同意）
     */
    public static final String DEFAULT_REMARK = "同意";

}

package com.gusi.boms.common;

import com.gusi.boms.model.ApiCache;
import com.gusi.socialInsurance.model.SIBaseType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: Constants
 * @Description: 系统常量相关配置类
 * @author Jail    E -Mail:86455@dooioo.com
 * @date 2012-12-5 下午1:37:49
 */
public class Constants {
	public static final int USERCODEMASK = 997;
	public static final String SESSION_USER = "SESSION_USER";

    /**
     * 系统管理员工号
     */
    public static final int GUANLIYUAN = 80001;

    public static final String BASIC_INFORMATION = "基本信息";
    public static final String JOB_INFORMATION = "职位信息";
    public static final String CONTRACT_RECORDS = "合同记录";
    public static final String PEOPLE_TAG = "员工标签";
    public static final String PEOPLE_TAG_ADD = "新增标签";
    public static final String PEOPLE_TAG_DEL = "删除标签";

    public static final String LEAVE = "离职";
    public static final String APPLY_LEAVE = "申请离职";
    public static final String FORMAL = "正式";
    public static final String JOIN = "试用期";
    public static final String SPACIAL = "特殊账号";
    public static final String BLACK = "黑名单";
    public static final String NORMALEAVE = "正常离职";

    public static final String ORGOPERATEHISTORY_STATUS_NORMAL = "正常";
    public static final String ORGOPERATEHISTORY_STATUS_RELIEVE = "离任";
    public static final String ORG_INFO_BASE = "基础信息";
    public static final String ORG_INFO_CONTACT = "联系信息";
    public static final String ORG_INFO_TRRANSFER = "转调信息";
    public static final String TO_FILE = "/public/json/";

    public static final int TEMP_ORG_PARENTID = 100000000;

    public static final String TRANSFER_WORDS_SORRY = "对不起，";
    public static final String TRANSFER_WORDS_INFO = "正在进行转调中，请在转调生效后进行操作。";
    public static final String TRANSFER_TEMP_ORGNAME = "转调成";

    public static final String AD_REGISTER = "员工报到";
    public static final String AD_RECOVER = "员工恢复删除状态";

    /**
     * 默认页数
     */
    public static final int DEFAULT_PAGENO = 1;

    /**
     * 添加到黑名单
     */
    public static final String ADD_EMPLOYEE_TO_BLACK="add_employee_to_black";
    
    /**
     * 移除黑名单
     */
    public static final String REMOVE_EMPLOYEE_FROM_BLACK="remove_employee_from_black";
    /**
     * 员工档案接口缓存
     */
    public static final Map<Integer, ApiCache> EMPLOYEEDETAILS = new HashMap<Integer, ApiCache>();
    /**
     * 员工奖惩接口缓存
     */
    public static final Map<Integer, ApiCache> EMPLOYEEREWARDS = new HashMap<Integer, ApiCache>();
    /**
     * 员工异动接口缓存
     */
    public static final Map<Integer, ApiCache> EMPLOYEECHANGES = new HashMap<Integer, ApiCache>();
    /**
     * 缓存时间1小时
     */
    public static final int CACHE_HOUR = 1;

    /**
     * 正常职位
     */
    public static final int NORMAL_POSITION = 0;
    /**
     * 兼职职位
     */
    public static final int PARTTIME_POSITION = 1;
    /**
     * 职等为分行经理
     */
    public static final String BRANCH_MANAGER = "分行经理";


    public static final int TITLE_SERIAL_DEAL_ID = 2;  //营销职系
    public static final int TITLE_BROKER_ID = 19; // 物业顾问ID
    public static final int TITLE_LEVEL_JUNIOR_BROKER_ID = 47; //职等职级初级物业顾问
    public static final int TITLE_LEVEL_BROKER_ID = 48; //职等职级物业顾问
    public static final String CHANNEL_KEY = "14033";//招聘渠道的key值


    public static final String STATUS = "status";
    public static final String OK = "ok";
    public static final String DATA = "data";
    public static final String TRUE = "true";




    /**********************下面是权限**********************/


    /**
     * 离职面谈
     */
    public static final String INTERVIEW_LEAVE_EMP="interview_leave_emp";
    /**
     *  人事管理-查看档案详情
     */
    public static final String ARCHIVES_DETAIL="archives_detail";

    /**
     * 人事管理-档案基本信息编辑
     */
    public static final String ARCHIVES_BASIC_EDIT="archives_basic_edit";

    /**
     *  人事管理-档案工作经验编辑
     */
    public static final String ARCHIVES_WORK_EDIT="archives_work_edit";

    /**
     * 人事管理-档案教育经历编辑
     */
    public static final String ARCHIVES_EDUCATION_EDIT="archives_education_edit";

    /**
     * 档案确认
     */
    public static final String ARCHIVES_SURE="archives_sure";

    /**
     * 人事管理- 档案家庭成员编辑
     */
    public static final String ARCHIVES_FAMILY_EDIT="archives_family_edit";

    /**
     *  人事管理-档案培训编辑
     */
    public static final String ARCHIVES_TRAIN_EDIT="archives_train_edit";

    /**
     * 人事管理- 查看异动详情
     */
    public static final String CHANGES_DETAIL="changes_detail";
    /**
     * 人事管理-未生效异动删除
     */
    public static final String CHANGES_DELETE="changes_delete";
    /**
     * 人事管理-异动回滚
     */
    public static final String CHANGES_ROLLBACK="changes_rollback";
    /**
     *  人事管理-添加异动
     */
    public static final String CHANGES_ADD="changes_add";
    /**
     * 人事管理-添加奖惩
     */
    public static final String REWARD_ADD = "reward_add";
    /**
     * 人事管理-查看奖惩
     */
    public static final String REWARD_DETAIL = "reward_detail";

    /**
     *  新增核算部门
     */
    public static final String ADD_ACCOUNTING_DEPT="oms_add_accountingDept";

    /**
     *  查看核算部门列表
     */
    public static final String VIEW_ACCOUNTING_DEPT="oms_view_accountingDept";

    /**
     *  管理维护核算部门
     */
    public static final String MANAGE_ACCOUNTING_DEPT="oms_manage_accountingDept";


    /**
     * 新增员工
     * 老系统权限废弃
     * @update: 2014-05-27 10:59:48
     */
    @Deprecated
    public static final String EMPLOYEE_ADD = "employee_add";

    /**
     * 员工报到处理权限
     */
    public static final String EMPLOYEE_REGISTER = "employee_register";

    public static final String BACKSTAGE_PRIVILEGES = "backstage_privileges";

    /**
     * 员工高级搜索权限
     */
    public static final String EMPLOYEE_ADVANCED_SEARCH = "employee_advanced_search";

    /**
     * 编辑员工标签
     */
    public static final String EMPLOYEE_TAG = "employee_tag";


    /**
    组织管理系统 - 核心权限
    */
    public static final String OMS_BASE = "oms_base"; //组织管理系统 - 核心权限
    /**
    组织管理系统 - 组织管理- 核心权限
     */
    public static final String OMS_OM_BASE = "oms_om_base"; //组织管理系统 - 组织管理 - 核心权限
    /**
    组织管理系统 - 组织管理 - 新增组织
     */
    public static final String OMS_OM_ORG_ADD = "oms_om_org_add"; //组织管理系统 - 组织管理 - 新增组织
    
    /**
     * 人资系统 组织管理 组织编辑 
     */
    public static final String OMS_OM_ORG_EDIT="oms_om_org_edit";
    /**
    组织管理系统 - 组织管理 - 组织排序
     */
    public static final String OMS_OM_ORG_INDEX = "oms_om_org_index"; //组织管理系统 - 组织管理 - 组织排序
    /**
     组织管理系统 - 组织管理 - 组织开关
     */
    public static final String OMS_OM_ORG_SWITCH = "oms_om_org_switch"; //组织管理系统 - 组织管理 - 组织开关
    /**
     组织管理系统 - 组织管理 - 组织详情
     */
    public static final String OMS_OM_ORG_DETAILS = "oms_om_org_details"; //组织管理系统 - 组织管理 - 组织详情
    /**
     组织管理系统 - 组织管理 - 组织电话编辑
     */
    public static final String OMS_OM_ORG_PHONE = "oms_om_org_phone"; //组织管理系统 - 组织管理 - 组织电话编辑
    /**
     * 组织管理系统 - 组织管理 - 组织组数新增编辑
     */
    public static final String OMS_OM_ORG_COUNT = "oms_om_org_count"; //组织管理系统 - 组织管理 - 组织电话编辑




    /**
   组织管理系统 - 岗位管理 - 核心权限
    */
    public static final String OMS_OM_POSITION_BASE = "oms_om_position_base"; // 组织管理系统 - 岗位管理 - 核心权限
    /**
    组织管理系统 - 岗位管理 - 新增岗位
    */
    public static final String OMS_OM_POSITION_ADD = "oms_om_position_add"; // 组织管理系统 - 岗位管理 - 新增岗位
    /**
     * 组织管理系统 - 岗位管理 - 岗位开关
     */
    public static final String OMS_OM_POSITION_SWITCH = "oms_om_position_switch"; // 组织管理系统 - 岗位管理 - 岗位开关

    /**
   组织管理系统 - 权限管理 - 核心权限;
    */
    public static final String PMS_BASE = "pms_base"; //组织管理系统 - 权限管理 - 核心权限;

    /**
   人力资源管理系统 - 核心权限
    */
    public static final String HRM_BASE = "hrm_base";// 人力资源管理系统 - 核心权限
    /**
   人力资源管理系统 - 人事管理 - 核心权限
    */
    public static final String HRM_EMPLOYEE_BASE = "hrm_employee_base"; //人力资源管理系统 - 人事管理 - 核心权限
    /**
    人力资源管理系统 - 人事管理 - 新增员工
     */
    public static final String HRM_EMPLOYEE_ADD = "hrm_employee_add"; //人力资源管理系统 - 人事管理 - 新增员工

    /**
     *  人力资源管理系统 - 人事管理 - 员工详情
     */
    public static final String HRM_EMPLOYEE_VIEW = "hrm_employee_view";
    /**
     *  人力资源管理系统 - 人事管理 - 员工基础信息编辑
     */
    public static final String HRM_EMPLOYEE_BASE_EDIT = "hrm_employee_base_edit";
    /**
     *  人力资源管理系统 - 人事管理 - 员工兼职岗位的编辑
     */
    public static final String HRM_EMPLOYEE_PARTTIME_POSITION_EDIT ="hrm_employee_parttime_position_edit";
    /**
     *  人力资源管理系统 - 人事管理 - 员工合同信息的编辑
     */
    public static final String HRM_EMPLOYEE_CONTRACT_EDIT = "hrm_employee_contract_edit";

    /**
     * 人力资源管理系统 - 人事管理 - 员工信息导出
     */
    public static final String HRM_EMPLOYEE_INFO_EXPORT ="hrm_employee_info_export";

    /**
     * 账号停用
     */
    public static final String USERCODE_DISABLED = "usercode_disabled";
    /**
     * 账号启用
     */
    public static final String USERCODE_ENABLED = "usercode_enabled";

    /**
     * 整组转调权限
     */
    public static final String ORGANIZATION_TRANSFER = "organization_transfer";
    /**
     * 员工申请转调记录列表查看权限
     */
    public static final String EMPLOYEE_TRANSFER_LIST = "employee_transfer_list";

    /**
     * 修改分行编制权限
     */
    public static final String EMPLOYEE_COUNT_EDIT = "employee_count_edit";

    /**
     * 社保员工图片打印
     */
    public static final String EMPLOYEE_PHOTO_PRINT="hrm_employee_photo_print";



    /**************************************begin 社保相关常量及权限*************************************/

    /**
     * 基础的有无条件集合
     * @since: 2014-06-12 09:17:25
     */
    public static final List<SIBaseType> SIBASE_TYPE_LIST = Arrays.asList(new SIBaseType(1, "有"), new SIBaseType(0, "无"));
    /**
     * 申请状态集合
     * @since: 2014-06-13 15:20:22
     */
    public static final List<SIBaseType> SIBASE_APPLY_TYPE_LIST = Arrays.asList(new SIBaseType(1, "成功"), new SIBaseType(0, "失败"));

    /**
     * 上海社保页面查看权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_SH_VIEW ="hrm_si_sh_view";
    /**
     * 上海社保页面操作权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_SH_OPERATE = "hrm_si_sh_operate";
    /**
     * 宁波社保页面查看权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_NB_VIEW = "hrm_si_nb_view";
    /**
     * 宁波社保页面操作权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_NB_OPERATE = "hrm_si_nb_operate";
    /**
     * 深圳社保页面查看权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_SZ_VIEW = "hrm_si_sz_view";
    /**
     * 深圳社保页面操作权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_SZ_OPERATE = "hrm_si_sz_operate";
    /**
     * 员工社保记录页面查看权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_VIEW = "hrm_si_view";
    /**
     * 员工社保记录页面操作权限
     * @since: 2014-06-11 17:49:18
     */
    public static final String HRM_SI_OPERATE = "hrm_si_operate";

    /**
     * 社保批次删除权限
     * @since: 2014-06-19 11:32:48
     */
    public static final String HRM_SI_BATCH_DELETE = "hrm_si_batch_delete";

    /**************************************end 社保相关常量及权限***************************************/



    /**
	 * 工作流常量部分
	 */
	/** 转调流程定义Key<br>positionTransfer */
	public static final String PROCESS_DEFINITION_KEY = "positionTransfer";

	/** 是否<br>是 */
	public static final String IS_TRUE = "true";

	/** 是否<br>否 */
	public static final String IS_FALSE = "false";

	/** 审批通过显示结果 */
	public static final String APPROVED = "审批通过";

	/** 审批不通过显示结果 */
	public static final String DENIED = "打回修改";

	/**
	 * 流程网关判断条件Key
	 */
	/** 流程网关判断条件Key:是否转后台Key<br>isToBackStage */
	public static final String CONDITION_KEY_IS_TO_BACKSTAGE = "isToBackStage";

	/** 流程网关判断条件Key:是否被动转调Key<br>isPassiveTransfer */
	public static final String CONDITION_KEY_IS_PASSIVE_TRANSFER = "isPassiveTransfer";

	/** 流程网关判断条件Key:是否转代理项目或者后台Key<br>isToDelegateOrBackStage */
	public static final String CONDITION_KEY_IS_TO_DELEGATE_OR_BACKSTAGE = "isToDelegateOrBackStage";

	/** 流程网关判断条件Key:是否取消申请key<br>isAbort */
	public static final String CONDITION_KEY_IS_ABORT = "isAbort";

	/** 流程网关判断条件Key:是否需要房源交接key<br>needHouseHandover */
	public static final String CONDITION_KEY_NEED_HOUSE_HANDOVER = "needHouseHandover";

	/** 流程网关判断条件Key:是否需要客源交接key<br>needCustomerHandover */
	public static final String CONDITION_KEY_NEED_CUSTOMER_HANDOVER = "needCustomerHandover";

	/** 流程网关判断条件Key:转出方组织审批结果key<br>isExportManagerApprove */
	public static final String CONDITION_KEY_IS_EXPORT_MANAGER_APPROVE = "isExportManagerApprove";

	/** 流程网关判断条件Key:转出方上级审批结果key<br>isExportSupervisorApprove */
	public static final String CONDITION_KEY_IS_EXPORT_SUPERVISOR_APPROVE = "isExportSupervisorApprove";

	/** 流程网关判断条件Key:转出方高层审批结果key<br>isExportDirectorApprove */
	public static final String CONDITION_KEY_IS_EXPORT_DIRECTOR_APPROVE = "isExportDirectorApprove";

	/** 流程网关判断条件Key:转入方上级审批结果key<br>isImportSupervisorApprove */
	public static final String CONDITION_KEY_IS_IMPORT_SUPERVISOR_APPROVE = "isImportSupervisorApprove";

	/** 流程网关判断条件Key:人事审批结果key<br>isHrApprove */
	public static final String CONDITION_KEY_IS_HR_APPROVE = "isHrApprove";

	/**
	 * 任务定义Key
	 */
	/** 任务定义Key: 转出方组织审批<br>exportManagerApprove */
	public static final String TASK_DEF_KEY_EXPORT_MANAGER_APPROVE = "exportManagerApprove";

	/** 任务定义Key: 转出方上级审批<br>exportSupervisorApprove */
	public static final String TASK_DEF_KEY_EXPORT_SUPERVISOR_APPROVE = "exportSupervisorApprove";

	/** 任务定义Key: 转出方高层审批<br>exportDirectorApprove */
	public static final String TASK_DEF_KEY_EXPORT_DIRECTOR_APPROVE = "exportDirectorApprove";

	/** 任务定义Key: 转入方上级审批<br>importSupervisorApprove */
	public static final String TASK_DEF_KEY_IMPORT_SUPERVISOR_APPROVE = "importSupervisorApprove";

	/** 任务定义Key: 人事审批<br>hrApprove */
	public static final String TASK_DEF_KEY_HR_APPROVE = "hrApprove";

	/** 任务定义Key: 房源交接<br>houseHandover */
	public static final String TASK_DEF_KEY_HOUSE_HANDOVER = "houseHandover";

	/** 任务定义Key: 客源交接<br>customerHandover */
	public static final String TASK_DEF_KEY_CUSTOMER_HANDOVER = "customerHandover";

	/** 任务定义Key: 打回修改<br>modifyApply */
	public static final String TASK_DEF_KEY_MODIFY_APPLY = "modifyApply";

	/**
	 * 任务定义名称
	 */
	/** 任务定义名称: 转出方组织审批<br>转出方组织审批 */
	public static final String TASK_DEF_NAME_EXPORT_MANAGER_APPROVE = "转出方组织审批";

	/** 任务定义名称: 转出方上级审批<br>转出方上级审批 */
	public static final String TASK_DEF_NAME_EXPORT_SUPERVISOR_APPROVE = "转出方上级审批";

	/** 任务定义名称: 转出方高层审批<br>转出方高层审批 */
	public static final String TASK_DEF_NAME_EXPORT_DIRECTOR_APPROVE = "转出方高层审批";

	/** 任务定义名称: 转入方上级审批<br>转入方上级审批 */
	public static final String TASK_DEF_NAME_IMPORT_SUPERVISOR_APPROVE = "转入方上级审批";

	/** 任务定义名称: 人事审批<br>人事审批 */
	public static final String TASK_DEF_NAME_HR_APPROVE = "人事审批";

	/** 任务定义名称: 房源交接<br>房源交接 */
	public static final String TASK_DEF_NAME_HOUSE_HANDOVER = "房源交接";

	/** 任务定义名称: 客源交接<br>客源交接 */
	public static final String TASK_DEF_NAME_CUSTOMER_HANDOVER = "客源交接";

	/** 任务定义名称: 打回修改<br>打回修改 */
	public static final String TASK_DEF_NAME_MODIFY_APPLY = "打回修改";

	/**
	 * 权限URLKey
	 */
	/** 权限URLkey: 转调申请<br>transferApplyURL */
	public static final String PRIVILEGE_URL_KEY_TRANSFER_APPLY = "transferApplyURL";

	/** 权限URLkey: 转出方组织审批<br>exportManagerApproveURL */
	public static final String PRIVILEGE_URL_KEY_EXPORT_MANAGER_APPROVE = "exportManagerApproveURL";

	/** 权限URLkey: 转出方上级审批<br>exportSupervisorApproveURL */
	public static final String PRIVILEGE_URL_KEY_EXPORT_SUPERVISOR_APPROVE = "exportSupervisorApproveURL";

	/** 权限URLkey: 转出方高层审批<br>exportDirectorApproveURL */
	public static final String PRIVILEGE_URL_KEY_EXPORT_DIRECTOR_APPROVE = "exportDirectorApproveURL";

	/** 权限URLkey: 转入方上级审批<br>importSupervisorApproveURL */
	public static final String PRIVILEGE_URL_KEY_IMPORT_SUPERVISOR_APPROVE = "importSupervisorApproveURL";

	/** 权限URLkey: 人事审批<br>hrApproveURL */
	public static final String PRIVILEGE_URL_KEY_HR_APPROVE = "hrApproveURL";

	/** 权限URLkey: 房源交接<br>houseHandoverURL */
	public static final String PRIVILEGE_URL_KEY_HOUSE_HANDOVER = "houseHandoverURL";

	/** 权限URLkey: 客源交接<br>customerHandoverURL */
	public static final String PRIVILEGE_URL_KEY_CUSTOMER_HANDOVER = "customerHandoverURL";

	/**
	 * 权限URL
	 */
	/** 权限URL: 转调申请<br>transferApply */
	public static final String PRIVILEGE_URL_TRANSFER_APPLY = "transferApply";

	/** 权限URL: 转出方组织审批<br>exportManagerApprove */
	public static final String PRIVILEGE_URL_EXPORT_MANAGER_APPROVE = "exportManagerApprove";

	/** 权限URL: 转出方上级审批<br>exportSupervisorApprove */
	public static final String PRIVILEGE_URL_EXPORT_SUPERVISOR_APPROVE = "exportSupervisorApprove";

	/** 权限URL: 转出方高层审批<br>exportDirectorApprove */
	public static final String PRIVILEGE_URL_EXPORT_DIRECTOR_APPROVE = "exportDirectorApprove";

	/** 权限URL: 转入方上级审批<br>importSupervisorApprove */
	public static final String PRIVILEGE_URL_IMPORT_SUPERVISOR_APPROVE = "importSupervisorApprove";

	/** 权限URL: 人事审批<br>hrApprove */
	public static final String PRIVILEGE_URL_HR_APPROVE = "hrApprove";

	/** 权限URL: 房源交接<br>houseHandover */
	public static final String PRIVILEGE_URL_HOUSE_HANDOVER = "houseHandover";

	/** 权限URL: 客源交接<br>customerHandover */
	public static final String PRIVILEGE_URL_CUSTOMER_HANDOVER = "customerHandover";
}

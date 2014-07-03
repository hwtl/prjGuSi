package com.gusi.empTransfer.service;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeChangeRecords;
import com.gusi.boms.service.ActivitiService;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.gusi.boms.service.EmployeeChangeRecordsService;
import com.gusi.boms.util.DateFormatUtil;
import com.gusi.empTransfer.callback.CallBackInterface;
import com.gusi.empTransfer.common.EmpTransferConstants;
import com.gusi.empTransfer.dto.EmpTransferSearch;
import com.gusi.empTransfer.dto.VEmployeeTransfer;
import com.gusi.empTransfer.helper.EmployeeTransferHelper;
import com.gusi.empTransfer.model.EmployeeTransfer;
import com.gusi.empTransfer.model.EmployeeTransferTaskStatus;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.service.BaseService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-23 下午1:41
 * @Description: 整组转调业务逻辑
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EmployeeTransferService extends BaseService<EmployeeTransfer> {

    private static final Log LOG = LogFactory.getLog(EmployeeTransferService.class);

    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private EmployeeChangeRecordsService employeeChangeRecordsService;
    @Autowired
    private EmpTransferOrgService empTransferOrgService;
    @Autowired
    private EmployeeTransferTaskStatusService employeeTransferTaskStatusService;
    @Autowired
    private ActivitiService<String> activitiService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;

    /**
     * 新增转调记录
     * @param employeeTransfer
     * @param creator
     */
    @Transactional
    public int add(EmployeeTransfer employeeTransfer, Employee creator) {
        //初始化各个属性值
        EmployeeTransferHelper.init(employeeTransfer);
        employeeTransfer.setCreator(creator.getUserCode());
        employeeTransfer.setCreateTime(new Date());
        employeeTransfer.setTransferNo(employeeTransfer.getUserCode() + "-" + DateFormatUtil.getString(employeeTransfer.getCreateTime(), "yyMMdd") + "-" + DateFormatUtil.getString(employeeTransfer.getCreateTime(), "HHmmss"));
        //插入转调信息
        employeeTransfer.setId(this.insertAndReturnId(employeeTransfer));
        //插入转调交接信息
        this.insertProcess(employeeTransfer);
        //启动流程
        VEmployeeTransfer vEmployeeTransfer = (VEmployeeTransfer)this.findViewById(employeeTransfer.getId());
		activitiService.startProcess(
                creator.getUserCode(),
                String.valueOf(employeeTransfer.getId()),
                //获取审批权限
                this.getPrivilegeMap(vEmployeeTransfer),
                EmployeeTransferHelper.initApprovalParam(vEmployeeTransfer),
                new CallBackInterface<String>() {

                    @Override
                    public String callBack(EmployeeTransferTaskStatus empTaskStatus, Integer approvalUserCode, String remark) {
                        employeeTransferTaskStatusService.insert(empTaskStatus);
                        // 更新对应工作流的审批状态
                        updateApprovalStatus(empTaskStatus.getId(), EmployeeTransferHelper.initApprovalStatus(empTaskStatus.getTaskName()));
                        return null;
                    }
                });
        return employeeTransfer.getId();
    }

    /**
     * 修改转调记录
     * @param employeeTransfer
     * @param updator
     * @return
     */
    @Transactional
    public int updateTransfer(EmployeeTransfer employeeTransfer, Employee updator, String processInsId, String taskId) {
        //初始化各个属性值
        EmployeeTransferHelper.init(employeeTransfer);
        employeeTransfer.setUpdator(updator.getUserCode());
        employeeTransfer.setUpdateTime(new Date());
        //编辑转调信息
        this.update(employeeTransfer);
        //插入转调交接信息
        this.updateProcess(employeeTransfer);
        //调用工作流
        VEmployeeTransfer vEmployeeTransfer = (VEmployeeTransfer)this.findViewById(employeeTransfer.getId());
        activitiService.modifyApply(
                processInsId,
                taskId,
                updator.getUserCode(),
                this.getPrivilegeMap(vEmployeeTransfer),
                EmployeeTransferHelper.initApprovalParam(vEmployeeTransfer),
                new CallBackInterface<String>() {
                    @Override
                    public String callBack(EmployeeTransferTaskStatus empTaskStatus, Integer approvalUserCode, String remark) {
                        employeeTransferTaskStatusService.update(empTaskStatus);
                        updateApprovalStatus(empTaskStatus.getId(), EmployeeTransferHelper.initApprovalStatus(empTaskStatus.getTaskName()));
                        return null;
                    }
                }
        );
        //更新对应工作流的审批状态
//        rs.setId(employeeTransfer.getId());
//        employeeTransferTaskStatusService.update(rs);
//        this.updateApprovalStatus(employeeTransfer.getId(), EmployeeTransferHelper.initApprovalStatus(rs.getTaskName()));
        //返回id
        return employeeTransfer.getId();
    }

    /**
     * 审批通过
     * @param vEmployeeTransfer
     */
    @Transactional
    public void pass(VEmployeeTransfer vEmployeeTransfer, String remark, String processInsId, String taskId, int approvalUserCode) {
        //调用工作流
//        EmployeeTransferTaskStatus rs =
        activitiService.approvePassed(
                processInsId,
                taskId,
                remark,
                approvalUserCode,
                new CallBackInterface<String>() {
                    @Override
                    public String callBack(EmployeeTransferTaskStatus empTaskStatus, Integer approvalUserCode, String remark) {
                        if(activitiService.checkProcessFinished(empTaskStatus.getProcessInsId())) {
                            employeeTransferTaskStatusService.update(empTaskStatus);
                            //更新审批状态为完成
                            updateApprovalStatusToComplete(empTaskStatus.getId());
                            //加一条异动
                            createChangeRecords((VEmployeeTransfer) findViewById(empTaskStatus.getId()));
                        } else {
                            EmployeeTransferTaskStatus lastRs = employeeTransferTaskStatusService.findById(empTaskStatus.getId());
                            employeeTransferTaskStatusService.update(empTaskStatus);
                            //更新对应工作流的审批状态
                            updateApprovalStatus(empTaskStatus.getId(), EmployeeTransferHelper.initApprovalStatus(empTaskStatus.getTaskName()));
                            //如果返回的下一步处理人和本次处理人相同，那么直接再调一次审批通过
                            if(!empTaskStatus.getTaskName().equals(Constants.TASK_DEF_KEY_HOUSE_HANDOVER)
                                    && !empTaskStatus.getTaskName().equals(Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER)
                                    && StringUtils.isNotBlank(empTaskStatus.getHandlerName())
                                    && StringUtils.isNotBlank(lastRs.getHandlerName())
                                    && lastRs.getHandlerName().equals(empTaskStatus.getHandlerName())) {
                                VEmployeeTransfer vnew  = (VEmployeeTransfer)findViewById(empTaskStatus.getId());
                                pass(vnew, remark, empTaskStatus.getProcessInsId(), empTaskStatus.getTaskId(), approvalUserCode);
                            }
                        }
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                }
        );
        //更新工作流状态记录表
//        if(rs == null) {
//            rs = new EmployeeTransferTaskStatus();
//            rs.setProcessInsId(processInsId);
//        }
//        rs.setId(vEmployeeTransfer.getId());
       //调用工作流 如果审批所有审批都通过了，那么生成一条异动记录
//        if(activitiService.checkProcessFinished(processInsId)) {
//            employeeTransferTaskStatusService.update(rs);
//            //更新审批状态为完成
//            this.updateApprovalStatusToComplete(vEmployeeTransfer.getId());
////            加一条异动
//            this.createChangeRecords(vEmployeeTransfer);
//        } else {
//            EmployeeTransferTaskStatus lastRs = employeeTransferTaskStatusService.findById(vEmployeeTransfer.getId());
//            employeeTransferTaskStatusService.update(rs);
//            //更新对应工作流的审批状态
//            this.updateApprovalStatus(vEmployeeTransfer.getId(), EmployeeTransferHelper.initApprovalStatus(rs.getTaskName()));
//            //如果返回的下一步处理人和本次处理人相同，那么直接再调一次审批通过
//            if(!lastRs.getTaskName().equals(Constants.TASK_DEF_KEY_HOUSE_HANDOVER) && !rs.getTaskName().equals(Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER) && StringUtils.isNotBlank(rs.getHandlerName()) && StringUtils.isNotBlank(lastRs.getHandlerName()) && lastRs.getHandlerName().equals(rs.getHandlerName())) {
//                VEmployeeTransfer vnew  = (VEmployeeTransfer)this.findViewById(vEmployeeTransfer.getId());
//                this.pass(vnew, remark, rs.getProcessInsId(), rs.getTaskId(), approvalUserCode);
//            }
//        }
    }

    /**
     * 人事审批通过
     */
    @Transactional
    public void passByHr(EmployeeTransfer employeeTransfer, String remark, String processInsId, String taskId, int approvalUserCode) {
        //初始化各个属性值
        employeeTransfer.setUpdator(approvalUserCode);
        employeeTransfer.setUpdateTime(new Date());
        //编辑转调信息
        this.hrUpdate(employeeTransfer);
        //调用工作流
//        VEmployeeTransfer vEmployeeTransfer = (VEmployeeTransfer)this.findViewById(employeeTransfer.getId());
//        EmployeeTransferTaskStatus rs =
        activitiService.approvePassed(processInsId, taskId, remark, approvalUserCode
            ,new CallBackInterface<String>() {
            @Override
            public String callBack(EmployeeTransferTaskStatus empTaskStatus, Integer approvalUserCode, String remark) {
                //调用工作流 如果审批所有审批都通过了，那么生成一条异动记录
                if(activitiService.checkProcessFinished(empTaskStatus.getProcessInsId())) {
                    employeeTransferTaskStatusService.update(empTaskStatus);
                    updateApprovalStatus(empTaskStatus.getId(), EmpTransferConstants.STATUS_3);
//            加一条异动
                    createChangeRecords((VEmployeeTransfer) findViewById(empTaskStatus.getId()));
                } else {
                    employeeTransferTaskStatusService.update(empTaskStatus);
                    //更新对应工作流的审批状态
                    updateApprovalStatus(empTaskStatus.getId(), EmployeeTransferHelper.initApprovalStatus(empTaskStatus.getTaskName()));
                }
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }
                );
        //更新工作流状态记录表
//        if(rs == null) {
//            rs = new EmployeeTransferTaskStatus();
//            rs.setProcessInsId(processInsId);
//        }
//        rs.setId(vEmployeeTransfer.getId());
//        //调用工作流 如果审批所有审批都通过了，那么生成一条异动记录
//        if(activitiService.checkProcessFinished(processInsId)) {
//            employeeTransferTaskStatusService.update(rs);
//            this.updateApprovalStatus(vEmployeeTransfer.getId(), EmpTransferConstants.STATUS_3);
////            加一条异动
//            this.createChangeRecords(vEmployeeTransfer);
//        } else {
//            employeeTransferTaskStatusService.update(rs);
//            //更新对应工作流的审批状态
//            this.updateApprovalStatus(vEmployeeTransfer.getId(), EmployeeTransferHelper.initApprovalStatus(rs.getTaskName()));
//        }
    }

    /**
     * 人事编辑
     * @param employeeTransfer
     */
    private void hrUpdate(EmployeeTransfer employeeTransfer) {
        this.update(sqlId("hrUpdate"), employeeTransfer);
    }

    /**
     * 房源审批通过
     * @param employeeTransfer
     */
    @Transactional
    public boolean updateFangyuanStatus(VEmployeeTransfer employeeTransfer, int approvalUserCode) {
        this.pass(employeeTransfer, EmpTransferConstants.DEFAULT_REMARK, employeeTransfer.getProcessInsId(), employeeTransfer.getTaskId(), approvalUserCode);
        return this.updateFangyuanStatus(employeeTransfer.getId());
    }

    /**
     * 客源审批通过
     * @param employeeTransfer
     * @return
     */
    @Transactional
    public boolean updateKeyuanStatus(VEmployeeTransfer employeeTransfer, int approvalUserCode) {
        this.pass(employeeTransfer, EmpTransferConstants.DEFAULT_REMARK, employeeTransfer.getProcessInsId(), employeeTransfer.getTaskId(), approvalUserCode);
        return this.updateKeyuanStatus(employeeTransfer.getId());
    }

    /**
     * 审批不通过
     * @param employeeTransfer
     * @param remark
     * @param processInsId
     * @param taskId
     * @param approvalUserCode
     */
    @Transactional
    public void unPass(EmployeeTransfer employeeTransfer, String remark, String processInsId, String taskId, int approvalUserCode) {
        //调用工作流
        activitiService.approveDenied(processInsId, taskId, remark, approvalUserCode,
                new CallBackInterface<String>() {
                    @Override
                    public String callBack(EmployeeTransferTaskStatus empTaskStatus, Integer approvalUserCode, String remark) {
//                        rs.setId(employeeTransfer.getId());
                        //更新工作流状态记录表
                        employeeTransferTaskStatusService.update(empTaskStatus);
                        //更新工作流记录
                        updateApprovalStatus(empTaskStatus.getId(), EmployeeTransferHelper.initApprovalStatus(empTaskStatus.getTaskName()));
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                });

    }

    /**
     * 新增一条异动记录
     * @param vEmployeeTransfer
     * @return
     */
    @Transactional
    public boolean createChangeRecords(VEmployeeTransfer vEmployeeTransfer) {
        try {
            //初始化异动记录字段
            EmployeeChangeRecords employeeChangeRecords = EmployeeTransferHelper.initChangeRecord(vEmployeeTransfer);
            //生成异动记录
            employeeChangeRecordsService.processChanges(employeeChangeRecords);
            //更新头衔
            employeeBaseInforService.updateUserTitle(vEmployeeTransfer.getNewTitle(), vEmployeeTransfer.getUserCode());
            return true;
        } catch (Exception e) {
            LOG.error("异动添加时失败。转调记录id是:" + vEmployeeTransfer.getId(), e);
            return false;
        }
    }

    /**
     * 插入交接信息
     * @param employeeTransfer
     */
    public void insertProcess(EmployeeTransfer employeeTransfer) {
        this.insert(sqlId("insertProcess"), employeeTransfer);
    }

    /**
     * 插入交接信息
     * @param employeeTransfer
     */
    public void updateProcess(EmployeeTransfer employeeTransfer) {
        this.update(sqlId("updateProcess"), employeeTransfer);
    }

    /**
     * 更新审批状态
     * @param id
     * @param approvalStatus
     * @return
     */
    public boolean updateApprovalStatus(int id, int approvalStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("approvalStatus", approvalStatus);
        return this.update(sqlId("updateApprovalStatus"), params);
    }

    /**
     * 更新审批状态为完成
     * @param id
     * @return
     */
    public boolean updateApprovalStatusToComplete(int id) {
        return this.update(sqlId("updateApprovalStatusToComplete"), id);
    }

    /**
     * 完成所有审批通过，更新记录状态
     * @param id
     */
    @Transactional
    public boolean finishAppravol(int id) {
        VEmployeeTransfer employeeTransfer = (VEmployeeTransfer)this.findViewById(id);
        //判断房客源是否交接完成
        if((employeeTransfer.getFangyuanStatus() == 2 || employeeTransfer.getFangyuanStatus() == 0)
                && (employeeTransfer.getKeyuanStatus() == 2 || employeeTransfer.getKeyuanStatus() == 0)
                ) {
            //审批通过，生成异动记录
            this.createChangeRecords(employeeTransfer);
            return this.updateApprovalStatus(id, EmpTransferConstants.STATUS_3);
        }
        return false;
    }

    /**
     * 更新房源交接状态为交接完成
     * @param id
     * @return
     */
    public boolean updateFangyuanStatus(int id) {
        return this.update(sqlId("updateFangyuanStatus"), id);
    }

    /**
     * 更新客源交接状态为交接完成
     * @param id
     * @return
     */
    public boolean updateKeyuanStatus(int id) {
        return this.update(sqlId("updateKeyuanStatus"), id);
    }

    /**
     * 根据id查找视图
     *
     * @param id
     * @return
     */
    public EmployeeTransfer findViewById(int id) {
        return findByBean(sqlId("findViewById"), id);
    }

    /**
     * 查找交接状态
     * @param id
     * @return
     */
    public EmployeeTransfer findProcessById(int id) {
        return findByBean(sqlId("findProcessById"), id);
    }

    /**
     * 是否存在转调申请
     *
     * @param userCode
     * @return
     */
    public boolean isTransfering(int userCode) {
        return count(sqlId("isTransfering"), userCode) > 0;
    }

    /**
     * 作废
     * @param id
     * @param taskId
     * @return
     */
    @Transactional
    public boolean destroy(int id, String taskId) {
        //工作流作废
        activitiService.abortApply(taskId,
                new CallBackInterface<String>() {
                    @Override
                    public String callBack(EmployeeTransferTaskStatus empTransfer, Integer userCode, String opinion) {
                        //更新相关状态
//                        EmployeeTransferTaskStatus rs = new EmployeeTransferTaskStatus();
//                        rs.setId(id);
                        employeeTransferTaskStatusService.update(empTransfer);
                        updateApprovalStatus(empTransfer.getId(), EmployeeTransferHelper.initApprovalStatus(""));
                        destroy(empTransfer.getId());
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                });
        return true;
    }

    /**
     * 作废
     * @param id
     * @return
     */
    public boolean destroy(int id) {
        return update(sqlId("destroy"), id);
    }

    /**
     * 根据工号查找需要
     * @param userCode
     * @param taskName
     * @return
     */
    public EmployeeTransfer findByUserCode(int userCode, String taskName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userCode", userCode);
        params.put("taskName", taskName);
        return this.findByBean(sqlId("findByUserCode"), params);
    }

    /**
     * 分页查询
     * @param pageNo
     * @param employee
     * @param empTransferSearch
     * @return Paginate
     */
    public Paginate queryForPaginate(int pageNo, Employee employee, EmpTransferSearch empTransferSearch, int scope) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("employee", employee);
            params.put("empTransferSearch", empTransferSearch);
            params.put("scope", scope);
            params.put("privileges", EmployeeTransferHelper.getSelectPrivileges(employee, empTransferOrgService.queryPartOrgs(employee.getUserCode())));
            VEmployeeTransfer vp = new VEmployeeTransfer();
            vp.setColumns(freemarkerUtil.writeTemplate("/employeeTransfer/columns.ftl"));
            vp.setTable(freemarkerUtil.writeTemplate("/employeeTransfer/table.ftl"));
            vp.setOrderBy(freemarkerUtil.writeTemplate("/employeeTransfer/order.ftl", params));
            vp.setPageSize(Integer.parseInt(freemarkerUtil.writeTemplate("/employeeTransfer/pageSize.ftl")));
            vp.setPageNo(pageNo);
            vp.setWhere(freemarkerUtil.writeTemplate("/employeeTransfer/where.ftl", params));
            return this.queryForPaginate2("queryForPaginate", vp);
        } catch (Exception e) {
            LOG.error("查询转调记录失败！ 查询参数为:" + params.toString(), e);
            return null;
        }
    }

    /**
     * 获取审批权限集合
     * @param id
     * @return 需要审批权限的集合
     */
    public Map<String, Object> getPrivilegeMap(int id) {
        VEmployeeTransfer vEmployeeTransfer = (VEmployeeTransfer)this.findViewById(id);
        return this.getPrivilegeMap(vEmployeeTransfer);
    }

    /**
     * 获取审批权限集合
     * @param vEmployeeTransfer
     * @return 需要审批权限的集合
     */
    public Map<String, Object> getPrivilegeMap(VEmployeeTransfer vEmployeeTransfer) {
        try {
            Map<String, Object> map = new HashMap<>();
            //打回修改作废权限
            map.put(Constants.PRIVILEGE_URL_KEY_TRANSFER_APPLY, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getNewOrgId(), Constants.PRIVILEGE_URL_TRANSFER_APPLY));
            //转出方组织审批
            map.put(Constants.PRIVILEGE_URL_KEY_EXPORT_MANAGER_APPROVE, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getOldOrgId(), Constants.PRIVILEGE_URL_EXPORT_MANAGER_APPROVE));
            //转出方上级审批
            map.put(Constants.PRIVILEGE_URL_KEY_EXPORT_SUPERVISOR_APPROVE, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getOldAreaId(), Constants.PRIVILEGE_URL_EXPORT_SUPERVISOR_APPROVE));
            //转入方上级审批
            //如果是转后台则网上找一级，否则找区域
            if(vEmployeeTransfer.getTransferType().equals(EmpTransferConstants.HT_CODE)) {
                map.put(Constants.PRIVILEGE_URL_KEY_IMPORT_SUPERVISOR_APPROVE, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getNewOrgParentId(), Constants.PRIVILEGE_URL_IMPORT_SUPERVISOR_APPROVE));
            } else {
                map.put(Constants.PRIVILEGE_URL_KEY_IMPORT_SUPERVISOR_APPROVE, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getNewAreaId(), Constants.PRIVILEGE_URL_IMPORT_SUPERVISOR_APPROVE));
            }
            //人事审批
            map.put(Constants.PRIVILEGE_URL_KEY_HR_APPROVE, Constants.PRIVILEGE_URL_HR_APPROVE);
            //房源交接
            map.put(Constants.PRIVILEGE_URL_KEY_HOUSE_HANDOVER, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getOldOrgId(), Constants.PRIVILEGE_URL_HOUSE_HANDOVER));
            //客源交接
            map.put(Constants.PRIVILEGE_URL_KEY_CUSTOMER_HANDOVER, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getOldOrgId(), Constants.PRIVILEGE_URL_CUSTOMER_HANDOVER));
            //高层审批
            empTransferOrgService.initDirectorApprove(vEmployeeTransfer, map);
            return map;
        } catch(Exception e) {
            LOG.error("获取权限失败！vEmployeeTransfer：" + vEmployeeTransfer, e);
            return null;
        }
    }
}

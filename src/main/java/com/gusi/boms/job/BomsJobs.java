package com.gusi.boms.job;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.dto.SmsReports;
import com.gusi.boms.helper.AttachHelper;
import com.gusi.boms.helper.MailHelper;
import com.gusi.boms.helper.SmsApiHelper;
import com.gusi.boms.helper.WebMsgHelper;
import com.gusi.boms.model.*;
import com.gusi.boms.service.*;
import com.gusi.boms.util.FileUtil;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.util.GlobalConfigUtil;
import com.dooioo.plus.util.TextUtil;
import com.dooioo.plusSMS.enums.CompanyType;
import com.dooioo.plusSMS.service.PSMSSendService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-3 下午3:06
 * @Description: 后台组织架构的各种计划任务
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BomsJobs {

    private static final Log log = LogFactory.getLog(BomsJobs.class);

    @Autowired
    private EmployeeChangeRecordsService employeeChangeRecordsService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private PSMSSendService psmsSendService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private Organization2Service orgnizationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrganizationEmployeeCountService organizationEmployeeCountService;
    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private EmployeeDetailsService employeeDetailsService;
    @Autowired
    private EmployeeStatusRecordService employeeStatusRecordService;
    @Autowired
    private OrganizationTransferProxyService organizationTransferProxyService;
    @Autowired
    private AccountingDepartmentService accountingDepartmentService;

//    /**
//     * 处理未报到的员工为删除
//     */
//    @Scheduled(cron = "0 30 0 ? * *")
//    public void doRegisterJob() {
//        log.info("---------------------------开始处理员工未报到的状态--------------------------------");
//        log.info("---------------------------如果未报到的员工" + Configuration.getInstance().getRegisterOutDay() + "天内没有处理，状态变为【删除】--------------------------------");
//        employeeBaseInforService.updateStatusToDelete();
//        log.info("---------------------------结束处理员工未报到的状态--------------------------------");
//    }
    @Autowired
    private EmployeeRewardPunishmentService employeeRewardPunishmentService;
   
    @Scheduled(cron="0 0/30 8-23 * * ?")
    public void doAddAccountingDeptJob(){
    	log.info("开始执行初始化新增核算组织。。。");
    	List<AccountingDepartment> depts=accountingDepartmentService.findForAddAccountDeptJob();
    	if (depts==null || depts.isEmpty()) {
			log.info("暂无核算组织需要初始新增。。");
    		return;
		}
    	for (AccountingDepartment ad : depts) {
			try {
				accountingDepartmentService.initAddAccountingDept(ad);
			} catch (Exception e) {
				log.error("初始化新增核算组织时出错："+ad,e);
			}
		}
    }
    
    /**
     * 处理奖惩记录生效
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void doActiveRewardPunishRecordsJob() {
    	log.info("凌晨3点执行奖惩记录生效：start");
    	List<EmployeeRewardPunishment> list=employeeRewardPunishmentService.queryForActiveJobs();
    	for (EmployeeRewardPunishment rp : list) {
			log.info("更改奖惩记录为有效："+rp);
			try {
				if (employeeRewardPunishmentService.changeNormal(rp.getId())) {
					rp.setStatus(1);
					employeeRewardPunishmentService.sendSalaryMQMessage(rp);
				}
			} catch (Exception e) {
				log.info("奖惩记录更新为有效的时候出错："+rp,e);
			}
		}
    }

    /**
     * 处理异动记录生效
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void doActiveChangeRecordsJob() {
        log.info("凌晨3点执行异动记录生效：start");
        List<EmployeeChangeRecords> changes = employeeChangeRecordsService.findChangesForActive();
        if (changes == null || changes.isEmpty()) {
            return;
        }
        for (EmployeeChangeRecords records : changes) {
            try {
                if (employeeChangeRecordsService.activeChanges(records)) {
                    //更新异动状态为生效
                    employeeChangeRecordsService.updateValid(records.getChangeId());
                } else {
                    log.error("异动作业未生效" + records);
                }
            } catch (Throwable e) {
                log.error("异动作业未生效:" + records, e);
            }
        }
        log.info("凌晨3点执行异动记录生效：end");
    }

    /**
     * 处理临时组织变成正式组织
     */
    @Scheduled(cron = "0 0 2 ? * *")
    public void doTempOrgToNormalJob() {
        log.info("==================>凌晨2点，临时组织转换为正常组织job");
        orgnizationService.updateTempOrgsToNormal();
        log.info("==================>凌晨2点，临时组织转换为正常组织job执行完毕");
    }

    /**
     * 处理整组转调生效
     */
    @Scheduled(cron = "0 30 1 ? * *")
    public void doTransferJob() {
    	organizationTransferProxyService.doActiveOrgTransferJob();
    }

    /**
     * 处理员工转正
     * 已修改为所有员工6个月自动转正
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void doBrokerChangeToNormalJob() {
        log.info("凌晨3点执行员工转正：start");
        List<EmployeeBaseInfor> ebs = employeeBaseInforService.findEmployeeToNormal();
        //生成一条异动记录
        Calendar activeDate = Calendar.getInstance();
        activeDate.set(Calendar.MILLISECOND, 0);
        activeDate.set(Calendar.HOUR_OF_DAY, 0);
        activeDate.set(Calendar.MINUTE, 0);
        activeDate.set(Calendar.SECOND, 0);
        EmployeeChangeRecords ecr = null;
        for (EmployeeBaseInfor eb : ebs) {
            try {
                ecr = new EmployeeChangeRecords();
                ecr.setUserCode(eb.getUserCode());
                ecr.setNewLevelId(eb.getLevelId());
                ecr.setNewPositionId(eb.getPositionId());
                ecr.setNewOrgId(eb.getOrgId());
                ecr.setActiveDate(activeDate.getTime());
                ecr.setLeaveDate(activeDate.getTime());
                ecr.setChangeType(EmployeeChangeRecords.CHANGE_TYPE_REGULAR);
                ecr.setCreator(80001);
                employeeChangeRecordsService.processNewRegular(ecr);
            } catch (Throwable e) {
                log.error("凌晨3点执行员工转正失败:" + eb, e);
            }
        }
        log.info("凌晨3点执行员工转正：end");
    }

    /**
     * 删除临时文件
     * @throws Exception
     */
    @Scheduled(cron = "0 35 3 ? * *")
    public void doSyncAttachByValidJob() throws Exception {
        String resourcePath = Configuration.getInstance().getAttachPath();
        List<Attachment> attachmentList = attachmentService.findByValid();
        if (attachmentList != null && !attachmentList.isEmpty()) {
            for (Attachment attachment : attachmentList) {
                log.info("开始删除物理文件======" + resourcePath + attachment.getAttachPath() + "======begin");
                if (FileUtil.deleteFile(resourcePath + attachment.getAttachPath())) {
                    if (AttachHelper.isImage(attachment.getAttachFilename())) {
                        FileUtil.deleteFile(resourcePath + attachment.getSmallAttachPath());
                    }
                    log.info("开始删除物理文件======" + resourcePath + attachment.getAttachPath() + "======end");
                    log.info("删除数据库垃圾数据=============begin");
                    attachmentService.deleteById(attachment.getId());
                    log.info("删除数据库垃圾数据=============end");
                }
            }

        } else {
            log.info("没有垃圾图片处理===============");
        }
    }


    /**
     * 处理过期的区域限制
     * @since: 2014-06-03 12:01:28
     */
    @Scheduled(cron = "0 0 5 ? * *")
    public void doOrgEmployeeCountJob() {
        log.info("---------------------开始：处理过期的区域限制。");
        organizationEmployeeCountService.updateOverdue();
        log.info("---------------------结束：处理过期的区域限制。");
    }

    /**
     * 发送生日祝福短信
     */
    @Scheduled(cron = "0 30 8 ? * *")
    public void doJobSendBirthDayMsg() {
        if (GlobalConfigUtil.isProductionEnv()) {
            sendBirthdayMsg();
        }
    }

    /**
     * 发送老员工祝福短信
     */
    @Scheduled(cron = "0 35 8 ? * *")
    public void doJobSendOldEmployeeMsg() {
        log.info("---------------------开始：发送老员工祝福短信");
        if (GlobalConfigUtil.isProductionEnv()) {
            sendOldEmployeeMsg();
        }
        log.info("---------------------结束：发送老员工祝福短信");
    }

    /**
     * 发送生日祝福短信方法
     */
    private void sendBirthdayMsg() {
        List<EmployeeBaseInfor> list = employeeBaseInforService.getBirthdayEmployee();
        for (EmployeeBaseInfor e : list) {
            VEmployeeBaseInfor ve = (VEmployeeBaseInfor) e;
            try {
            	log.info("开始发送生日祝福短信，员工信息："+ve);
                 psmsSendService.sendMsg(ve.getMobilePhone(), TextUtil.format(Configuration.getInstance().getBirthdaySelfMsg(), ve.getUserNameCn()), GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(ve.getCompany()));
             } catch (Exception ex) {
            	log.error("发送生日祝福短信出错，"+ve, ex);
                continue;
            }
            List<EmployeeBaseInfor> otherEmployees = employeeBaseInforService.getTogetherEmployee(ve.getUserCode());
            if (otherEmployees==null || otherEmployees.isEmpty()) {
            	 log.info("---------------------结束：发送生日祝福短信，otherEmployees is null。");
				return;
			}
            StringBuilder sb=new StringBuilder(200);
            for (EmployeeBaseInfor other : otherEmployees) {
                VEmployeeBaseInfor otherEmployee = (VEmployeeBaseInfor) other;
                sb.append(","+otherEmployee.getMobilePhone());
             }
            try {
            	psmsSendService.sendMsg(sb.deleteCharAt(0).toString(), TextUtil.format(Configuration.getInstance().getBirthdayOtherMsg(), ve.getUserNameCn()),
            				GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(ve.getCompany()));
            } catch (Exception ex) {
            	log.error("告诉其他人祝福短信出错。", ex);
            }
        }
        log.info("---------------------结束：发送生日祝福短信");
    }

    /**
     * 发送老员工祝福短信方法
     */
    private void sendOldEmployeeMsg() {
        List<EmployeeBaseInfor> list = employeeBaseInforService.getOldEmployee();
        for (EmployeeBaseInfor e : list) {
            VEmployeeBaseInfor ve = (VEmployeeBaseInfor) e;
            if (ve.getYears() == 2) {
                try {
                    psmsSendService.sendMsg(ve.getMobilePhone(), TextUtil.format(Configuration.getInstance().getOldEmpSelfMsg(), ve.getUserNameCn()), GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(ve.getCompany()));
                } catch (Exception ex) {
                    continue;
                }
                List<EmployeeBaseInfor> otherEmployees = employeeBaseInforService.getTogetherEmployee(ve.getUserCode());
                for (EmployeeBaseInfor other : otherEmployees) {
                    VEmployeeBaseInfor otherEmployee = (VEmployeeBaseInfor) other;
                    try {
                        psmsSendService.sendMsg(otherEmployee.getMobilePhone(), TextUtil.format(Configuration.getInstance().getOldEmpOtherMsg(), ve.getUserNameCn()),GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(otherEmployee.getCompany()));
                    } catch (Exception ex) {
                        continue;
                    }
                }
            } else if (ve.getYears() > 2) {
                try {
                    psmsSendService.sendMsg(ve.getMobilePhone(), TextUtil.format(Configuration.getInstance().getOlderEmpSelfMsg(), ve.getUserNameCn(), String.valueOf(ve.getYears()), ve.getCompany()),GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(ve.getCompany()));
                } catch (Exception ex) {
                    continue;
                }
                List<EmployeeBaseInfor> otherEmployees = employeeBaseInforService.getTogetherEmployee(ve.getUserCode());
                for (EmployeeBaseInfor other : otherEmployees) {
                    VEmployeeBaseInfor otherEmployee = (VEmployeeBaseInfor) other;
                    try {
                        psmsSendService.sendMsg(otherEmployee.getMobilePhone(), TextUtil.format(Configuration.getInstance().getOlderEmpOtherMsg(), ve.getUserNameCn(), String.valueOf(ve.getYears()), ve.getCompany()),GlobalConfigUtil.getCurrentAppCode(), CompanyType.valueOf(otherEmployee.getCompany()));
                    } catch (Exception ex) {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 发送网站头部消息
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void doJobSendWebMsg() {
        log.info("---------------------开始：招聘短信接收失败的员工，发送网站头部消息通知人资");
        //查询需要验证招聘短信是否收到的员工
        List<EmployeeDetails> employeeDetailsList = employeeDetailsService.queryByWebFlag();
        try {
            log.info(employeeDetailsList.size() + "个人需要验证是否收到招聘短信。");
            if(CollectionUtils.isEmpty(employeeDetailsList)) {
                log.info("---------------------结束：招聘短信接收失败的员工，发送网站头部消息通知人资");
                return;
            }
            //请求短信接口，返回没有收到短信的员工
            List<SmsReports> smsReportsList = SmsApiHelper.sendMsg(employeeDetailsList);
            if(CollectionUtils.isEmpty(smsReportsList)) {
                log.info("---------------------结束：招聘短信接收失败的员工，发送网站头部消息通知人资");
                return;
            }
            //获取失败的
            List<SmsReports> smsReports = SmsApiHelper.getNotOk(smsReportsList);
            if(CollectionUtils.isEmpty(smsReports)) {
                employeeStatusRecordService.updateByUserCodesToOver(employeeDetailsList);
                log.info("---------------------结束：招聘短信接收失败的员工，发送网站头部消息通知人资");
                return;
            }
            //查询没有收到短信的员工的信息
            List<EmployeeBaseInfor> notReceivedEmpList = employeeBaseInforService.queryBySmsReports(smsReports);
            if(CollectionUtils.isEmpty(notReceivedEmpList)) {
                log.info("---------------------结束：招聘短信接收失败的员工，发送网站头部消息通知人资");
                return;
            }
            //将这些人发送网站头部消息，给人资相关人员
            for (EmployeeBaseInfor r : notReceivedEmpList) {
                log.info(r.getUserNameCn() + "（" + r.getUserCode() + "），手机号" + ((VEmployeeBaseInfor) r).getMobilePhone() + "，没有收到入职通知短信。");
                //发送给配置的员工，如管理员
                WebMsgHelper.sendWebMsg(r.getUserNameCn() + "（" + r.getUserCode() + "），手机号" + ((VEmployeeBaseInfor) r).getMobilePhone() + "，没有收到入职通知短信。", Company.Dooioo);
                //发送给创建人
                WebMsgHelper.sendWebMsg(r.getCreator(), r.getUserNameCn() + "（" + r.getUserCode() + "），手机号" + ((VEmployeeBaseInfor) r).getMobilePhone() + "，没有收到入职通知短信。", Company.Dooioo);
            }
            //将已经请求过的员工状态置为2
            employeeStatusRecordService.updateByUserCodesToOver(employeeDetailsList);
        } catch (Exception e) {
            log.error("发送网站消息失败。 ", e);
        }
        log.info("---------------------结束：招聘短信接收失败的员工，发送网站头部消息通知人资");
    }


}

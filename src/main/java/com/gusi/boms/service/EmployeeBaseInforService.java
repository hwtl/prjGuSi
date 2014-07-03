package com.gusi.boms.service;

import com.gusi.activemq.helper.EmployeeSenderHelper;
import com.gusi.activemq.sender.EmployeeAccountActiveSender;
import com.gusi.activemq.sender.EmployeeAccountPauseSender;
import com.gusi.boms.bomsEnum.OperateType;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.gusi.boms.dto.EmpToSendMsg;
import com.gusi.boms.dto.SmsReports;
import com.gusi.boms.helper.AdHelper;
import com.gusi.boms.helper.EmployeeHelper;
import com.gusi.boms.helper.MailHelper;
import com.gusi.boms.util.MD5Utils;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.dymq.model.EmployeeMessage;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.other.model.DyResult;
import com.dooioo.plus.other.service.DyADService;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.plus.util.GlobalConfigUtil;
import com.dooioo.plusSMS.enums.CompanyType;
import com.dooioo.plusSMS.service.PSMSSendService;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.service.BaseService;
import com.dooioo.webplus.convenient.LunarSolarUtil;
import com.gusi.boms.model.*;
import freemarker.template.TemplateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.com.dooioo.erp.erp.dao
 * Author: Jerry.hu
 * Create: Jerry.hu(2013-04-08 15:09)
 * Description: 员工基础信息业务逻辑处理
 */
@Service
@Transactional
public class EmployeeBaseInforService extends BaseService<EmployeeBaseInfor> {

    private Log log = LogFactory.getLog(EmployeeBaseInfor.class);

   
    @Autowired
    private EmployeeOperateHistoryService employeeOperateHistoryService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private Organization2Service organization2Service;
    @Autowired
    private EmployeeContractRecordsService employeeContractRecordsService;
    @Autowired
    private EmployeeDetailsService employeeDetailsService;
    @Autowired
    private SyncRtxService rtxService;
    @Autowired
    private EmployeeCompanyService employeeCompanyService;
    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private EmployeeRegisterService registerService;
    @Autowired
    private EmployeeStatusRecordService employeeStatusRecordService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DyADService dyADService;
    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private EmployeeSenderHelper employeeSenderHelper;
    @Autowired
    private EmployeePasswordInfoService employeePasswordInfoService;
    @Autowired
    private AdHelper adHelper;
    @Autowired
    private BeforeJobService beforeJobService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private MailHelper mailHelper;
    @Autowired
    private PSMSSendService psmsSendService;
    @Autowired
    private EmpToSendMsgService empToSendMsgService;
    @Autowired
    private EmployeeAccountActiveSender employeeAccountActiveSender;
    @Autowired
    private EmployeeAccountPauseSender employeeAccountPauseSender;

    /**
     * 保存员工
     * @param employeeBaseInfor     员工类
     * @param recruitment           招聘渠道类
     * @param registerDate 预计报到日期
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public synchronized VEmployeeBaseInfor saveEmployee(VEmployeeBaseInfor employeeBaseInfor, 
    		Recruitment recruitment, String ip,String registerDate) {
        //密码字母后六位小写
        String password = EmployeeHelper.generatePassword(employeeBaseInfor.getCredentialsNo());
        if(employeeBaseInfor.getStatus().equals(Constants.FORMAL)){
            employeeBaseInfor.setFormalDate(employeeBaseInfor.getJoinDate());
        }
        //产生MD5密码
        employeeBaseInfor.setPassword(MD5Utils.str2Md5(password));
        //保存员工并返回工号
        employeeBaseInfor.setUserCode(insertAndReturnId(employeeBaseInfor));
        //插入密码记录表
        employeePasswordInfoService.insert(new EmployeePasswordInfo(employeeBaseInfor.getUserCode(), employeeBaseInfor.getPassword(), ip, 1));
        //插入员工状态记录表
        employeeStatusRecordService.insert(new EmployeeStatusRecord(employeeBaseInfor.getUserCode(), employeeBaseInfor.getStatus()));
        //更新新员工状态为:'总部待报到'
        updateStatus(employeeBaseInfor.getUserCode(), EmployeeStatus.ToRegisteHeadquarters.toString(), employeeBaseInfor.getCreator());
        //设置员工状态
        employeeBaseInfor.setStatus(EmployeeStatus.ToRegisteHeadquarters.toString());
        //添加主要岗位
        employeePositionService.insert(new EmployeePosition(employeeBaseInfor.getUserCode(),employeeBaseInfor.getPositionId(),employeeBaseInfor.getOrgId(),Constants.NORMAL_POSITION));
        //设置分行的负责人信息
        saveBranchManager(employeeBaseInfor);
        //生成员工档案
        employeeDetailsService.createArchives(employeeBaseInfor);
        //添加员工公司表
        employeeCompanyService.insert(new EmployeeCompany(Company.getCompanyByName(employeeBaseInfor.getCompany()).companyIdentity(),employeeBaseInfor.getUserCode()));
        if (employeeBaseInfor.getUserCode() > 0) {
            //添加合同
            saveEmployeeContractRecords(employeeBaseInfor);
            //更新员工职位
            updateEmpOrgPositionByUserCode(employeeBaseInfor.getUserCode());
            //插入招聘渠道
            recruitment.setUserCode(employeeBaseInfor.getUserCode());
            recruitmentService.insert(recruitment);
            //同步AD用户
            if (!adHelper.syncAD(employeeBaseInfor.getUserCode())) {
                throw new RuntimeException("fail to sync the AD ......");
            }
            //发短信
//            if(!EmployeeBaseInfor.NOT_REGISTER.equals(employeeBaseInfor.getStatus())) {
//                sendMsg(employeeBaseInfor);
//            }
            //发短信告知分行经理和助理
            recordBeforeJobInfoAndSendNotice(employeeBaseInfor, registerDate);
            return employeeBaseInfor;
        } else {
            return null;
        }
    }

    /**
     * 保存员工基础信息变更记录
     * @param employeeBaseInfor 员工基础信息对象
     * @param employeeOperateHistory 员工操作记录对象
     * @return boolean
     */
    public boolean saveOperateHistory(EmployeeBaseInfor employeeBaseInfor,EmployeeOperateHistory employeeOperateHistory){
        EmployeeBaseInfor oldEmployeeBaseInfor = findByBean(employeeBaseInfor.getUserCode());
        updateEmpOrgPositionByUserCode(employeeBaseInfor.getUserCode());
        employeeOperateHistory.setRemark(oldEmployeeBaseInfor.getOperateRemark(employeeBaseInfor));
        return StringUtils.isBlank(employeeOperateHistory.getRemark()) || employeeOperateHistoryService.insert(employeeOperateHistory);
    }

    /**
      * @since: 2.7.4 
      * @param ep
      * @param registerDate 预计报到日期
      * <p>
      * 记录notice并且发布消息
      * </p>   
     */
    private void recordBeforeJobInfoAndSendNotice(EmployeeBaseInfor ep,String registerDate){
    	try {
            if(!ep.getCompany().equals(Company.Dooioo.toString())) {
                return;
            }
    		VEmployeeBaseInfor vp=(VEmployeeBaseInfor)ep;
    		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date=df.parse(registerDate);
			SimpleDateFormat sd=new SimpleDateFormat("M月dd日aH:mm");
            if (ep.getPositionId()==Position.BROKER_POSITION_ID) {
                String msg="新人"+vp.getUserNameCn()+"将于"+sd.format(date)+"到总部办理入职，完毕后再到各门店报到，请分行经理及助理知悉并做好接待，并及时为新人指定带教师傅。";
                beforeJobService.addInfo(vp.getUserCode(), date);

                List<EmployeeBaseInfor> listEmps=findBranchManagerWithAssistant(vp.getUserCode());
                if (listEmps !=null && !listEmps.isEmpty()) {
                    StringBuilder sb=new StringBuilder(23);
                    StringBuilder codes=new StringBuilder(12);
                    for (EmployeeBaseInfor t : listEmps) {
                        sb.append(((VEmployeeBaseInfor)t).getMobilePhone()+",");
                        codes.append(t.getUserCode()+",");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    codes.deleteCharAt(codes.length()-1);
                    //发给经理和助理
                    noticeService.sendSms(sb.toString(), msg, vp.getCompany());
                    //发送头部消息
                    noticeService.sendBatchHeadNotice(codes.toString(), msg);
                }
            }
            //发短信给当事人
            noticeService.sendSmsToRegist(vp.getMobilePhone(), vp.getCompany(), vp.getUserCode(),sd.format(date));
		} catch (ParseException e) {
			log.error("员工入职，转换预计报到日期报错。registerDate="+registerDate+","+ep, e);
		}
    }
    /**
      * @since: 2.7.4
      * @param userCode
      * @return
      * <p>
      * 根据工号查找分行经理和助理
      * </p>   
     */
    public List<EmployeeBaseInfor> findBranchManagerWithAssistant(int userCode){
    	return this.queryForList(sqlId("findBranchManagerWithAssistant"), userCode);
    }
    
    /**
     * 新增员工发送短信告知工号和密码
     * @param employeeBaseInfor  员工对象
     */
    private void sendMsg(EmployeeBaseInfor employeeBaseInfor){
        VEmployeeBaseInfor vEmployeeBaseInfor = (VEmployeeBaseInfor) employeeBaseInfor;
       noticeService.sendSms(vEmployeeBaseInfor.getMobilePhone(),generateSmsMessage(employeeBaseInfor) 
        		, employeeBaseInfor.getCompany());
    }

   
    /**
     * 生成短信内容
     * @param employeeBaseInfor 员工对象
     * @return 短信内容
     */
    private String generateSmsMessage(EmployeeBaseInfor employeeBaseInfor){
        return employeeBaseInfor.getUserNameCn() + "，您的系统账号已开通。登录账号为" + employeeBaseInfor.getUserCode() + (StringUtils.isBlank(employeeBaseInfor.getCredentialsNo()) ? "，默认密码为123456" : "，默认密码为身份证后6位（字母小写）。");
    }

    /**
     * 设置分行负责人
     * @param employeeBaseInfor 员工基础信息实体
     */
    private void saveBranchManager(VEmployeeBaseInfor employeeBaseInfor){
        VEmployeeBaseInfor vEmployeeBaseInfor = (VEmployeeBaseInfor) employeeBaseInfor;
        if(vEmployeeBaseInfor.getPositionId() == Position.BRANCH_MANAGER_ID){
            organization2Service.addManager(employeeBaseInfor);
        }
    }

    /**
     * 新增员工时添加合同记录
     * @param employeeBaseInfor 员工基础信息
     */
    private void saveEmployeeContractRecords(EmployeeBaseInfor employeeBaseInfor){
        EmployeeContractRecords employeeContractRecords = new EmployeeContractRecords();
        employeeContractRecords.setContractType(EmployeeContractRecords.REGULAR_CONTRACT);
        employeeContractRecords.setCreator(employeeBaseInfor.getCreator());
        employeeContractRecords.setUserCode(employeeBaseInfor.getUserCode());
        employeeContractRecords.setStartTime(employeeBaseInfor.getJoinDate());
        employeeContractRecords.setEndTime(EmployeeHelper.getConstractEndTime(employeeBaseInfor.getJoinDate()));
        employeeContractRecords.setStatus(0);
        employeeContractRecordsService.insert(employeeContractRecords);

    }

    public Paginate queryForPaginate(EmployeeSearch employeeSearch, int pageNo) {
        VEmployeeBaseInfor  vp  = new VEmployeeBaseInfor();
        vp.setColumns(" * ");
        vp.setTable(" v2_employee_baseInfo_List l  WITH(NOLOCK) ");
//        vp.setOrderBy("userCode desc");
        vp.setOrderBy("titleDegree desc,levelDegree desc, userCode");
        vp.setPageSize(Configuration.getInstance().getPageSize());
        vp.setPageNo(pageNo);
        vp.setWhere(EmployeeHelper.getWhere(employeeSearch));
        return this.queryForPaginate2(vp);
    }

    /**
     * 是否超过区域人数限制
     * @param orgId
     * @return
     * @throws Exception
     */
    public boolean isMoreThanNormal(int orgId) throws Exception {
        //获取区域
        VOrganization areaOrg = organization2Service.findAreaOrg(orgId);
        //判断是否是前台新增员工
        if(areaOrg != null && areaOrg.getBranchRequireEmpNo() != 0 && StringUtils.isNotBlank(areaOrg.getOrgLongCode())) {
            //该区域下的员工数量
            int employeeCount = organization2Service.findEmployeeCount(areaOrg.getOrgLongCode());
            //该区域下的分行数量
            int branchCount = organization2Service.findBranchCount(areaOrg.getOrgLongCode());
            if (employeeCount >= (branchCount * areaOrg.getBranchRequireEmpNo() + areaOrg.getMaxCount())) {
                //超过区域人数
                return true;
            }
        }
        return false;
    }

    /**
     * 发送短信
     * @param orgId
     * @throws Exception
     */
    public void sendWarnings(int orgId, Date sendTime) throws Exception{
        VOrganization areaOrg = organization2Service.findAreaOrg(orgId);
        if(areaOrg != null && areaOrg.getBranchRequireEmpNo() != 0 && StringUtils.isNotBlank(areaOrg.getOrgLongCode())) {
            //查找区总
            EmpToSendMsg areaUser = empToSendMsgService.findAreaUser(areaOrg.getOrgLongCode());
            if(areaUser != null) {
                //该区域下的员工数量
                int employeeCount = organization2Service.findEmployeeCount(areaOrg.getOrgLongCode());
                //该区域下的分行数量
                int branchCount = organization2Service.findBranchCount(areaOrg.getOrgLongCode());
                //是否符合第一次发送条件
                if(employeeCount == (int)(branchCount * areaOrg.getBranchRequireEmpNo() * Configuration.getInstance().getEmpNumWarning1())) {
                    this.sendWarning1(areaUser, employeeCount, Configuration.getInstance().getEmpNumWarning1(), sendTime);
                //是否符合第二次发送条件
                } else if (employeeCount == (int)(branchCount * areaOrg.getBranchRequireEmpNo() * Configuration.getInstance().getEmpNumWarning2())) {
                    this.sendWarning2(areaUser, employeeCount, Configuration.getInstance().getEmpNumWarning2(), sendTime);
                }
            }
        }
    }

    /**
     * 第一次发送分行人数提醒
     * @param areaUser 区域orgLongCode
     * @param employeeCount 区域人数
     * @param empPercent 提醒百分比，如：0.85
     */
    public void sendWarning1(EmpToSendMsg areaUser, int employeeCount, double empPercent, Date sendTime) throws Exception {
        try {
            String msg = getSendMsg(areaUser, employeeCount, empPercent, "branchEmpWarning1");
            String sendTimeStr = DateFormatUtils.format(sendTime, "yyyy-MM-dd HH:mm:ss");
            //为了避免混淆，如果不是正式环境，则不发送
            if(GlobalConfigUtil.isProductionEnv()) {
                //发送邮件提醒区总
                mailHelper.sendMail(MailHelper.ADD_EMPLOYEE_TITLE, msg, String.valueOf(areaUser.getUserCode()));
                //发送短信提醒区总
                psmsSendService.sendMsg(areaUser.getMobilePhone(), msg, GlobalConfigUtil.getCurrentAppCode(),sendTimeStr, CompanyType.德佑);
            } else {
                log.info(GlobalConfigUtil.getCurrentEnv() + "环境不发送分行人数提醒邮件，区总工号:" + areaUser.getUserCode());
                log.info(GlobalConfigUtil.getCurrentEnv() + "环境不发送分行人数提醒短信，区总手机:" + areaUser.getMobilePhone());
            }
            //下面是特殊处理，与业务无关
            mailHelper.sendMail(MailHelper.ADD_EMPLOYEE_TITLE, msg, Configuration.getInstance().getEmpNumWarning1Users());
//            psmsSendService.sendMsg("13917849112", msg + GlobalConfigUtil.getCurrentEnv(), GlobalConfigUtil.getCurrentAppCode(), sendTimeStr, CompanyType.德佑);
            psmsSendService.sendMsg("18217581496", msg + GlobalConfigUtil.getCurrentEnv(), GlobalConfigUtil.getCurrentAppCode(), sendTimeStr, CompanyType.德佑);
        } catch (Exception e) {
            log.error("发送通知区总分行人数短信失败，areaUser=" + areaUser, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 第2次发送分行人数提醒
     * @param areaUser 区域orgLongCode
     * @param employeeCount 区域人数
     * @param empPercent 提醒百分比，如：0.85
     */
    public void sendWarning2(EmpToSendMsg areaUser, int employeeCount, double empPercent, Date sendTime) throws Exception{
        try {
            String msg = getSendMsg(areaUser, employeeCount, empPercent, "branchEmpWarning2");
            String sendTimeStr = DateFormatUtils.format(sendTime, "yyyy-MM-dd HH:mm:ss");
            //为了避免混淆，如果不是正式环境，则不发送
            if(GlobalConfigUtil.isProductionEnv()) {
                mailHelper.sendMail(MailHelper.ADD_EMPLOYEE_TITLE, msg, String.valueOf(areaUser.getUserCode()));
                mailHelper.sendMail(MailHelper.ADD_EMPLOYEE_TITLE, msg, Configuration.getInstance().getEmpNumWarning2Users());
                //发短信给区总
                psmsSendService.sendMsg(areaUser.getMobilePhone(), msg, GlobalConfigUtil.getCurrentAppCode(), sendTimeStr, CompanyType.德佑);
                //发短信给人事
                List<EmpToSendMsg> empToSendMsgList = empToSendMsgService.findUsers(Configuration.getInstance().getEmpNumWarning2Users());
                if(CollectionUtils.isNotEmpty(empToSendMsgList)) {
                    for(EmpToSendMsg empToSendMsg : empToSendMsgList) {
                        psmsSendService.sendMsg(empToSendMsg.getMobilePhone(), msg, GlobalConfigUtil.getCurrentAppCode(), sendTimeStr, CompanyType.德佑);
                    }
                }
            } else {
                log.info(GlobalConfigUtil.getCurrentEnv() + "环境不发送分行人数提醒邮件，区总工号:" + areaUser.getUserCode());
                log.info(GlobalConfigUtil.getCurrentEnv() + "环境不发送分行人数提醒短信，区总手机:" + areaUser.getMobilePhone());
            }
            mailHelper.sendMail(MailHelper.ADD_EMPLOYEE_TITLE, msg, Configuration.getInstance().getEmpNumWarning2Users());
            psmsSendService.sendMsg("13917849112", msg + GlobalConfigUtil.getCurrentEnv(), GlobalConfigUtil.getCurrentAppCode(), sendTimeStr, CompanyType.德佑);
            psmsSendService.sendMsg("18217581496", msg + GlobalConfigUtil.getCurrentEnv(), GlobalConfigUtil.getCurrentAppCode(), sendTimeStr, CompanyType.德佑);
        } catch (Exception e) {
            log.error("发送通知区总分行人数短信失败，areaUser=" + areaUser, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取区域人数限制消息模板
     * @param areaUser
     * @param employeeCount
     * @param empPercent
     * @param template 模板名称
     * @return
     * @throws TemplateException
     * @throws IOException
     */
    private String getSendMsg(EmpToSendMsg areaUser, int employeeCount, double empPercent, String template) throws TemplateException, IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("areaUser", areaUser);
        params.put("employeeCount", employeeCount);
        params.put("empPercent", empPercent);
        //获取短信和邮件的内容
        return freemarkerUtil.writeTemplate("/employee/" + template + ".ftl", params);
    }

    /**
     * 检查身份证
     * @param idc 身份证号
     * @return employeeBaseinfor
     */
    public EmployeeBaseInfor queryEmployeeByIdc(String idc,String company) {
        Map<String,Object> m = new HashMap<>();
        m.put("idc",idc);
        m.put("company",company);
        return  this.findByBean(sqlId("queryEmployeeByIdc"), m);
    }

    public EmployeeBaseInfor queryEmployeeDetails(String company,int userCode){
        Map<String,Object> m = new HashMap<>();
        m.put("userCode",userCode);
        m.put("company",company);
        return this.findByBean(sqlId("queryEmployeeDetails"),m);
    }

    public List<EmployeeBaseInfor> associative(String key,String company) {
        Map<String,Object> m  = new HashMap<>();
        m.put("key",key);
        m.put("company",company);
        return this.queryForList(sqlId("associative"),m);
    }

    @Transactional(rollbackFor = {Exception.class})
    @ActiveMQTransactional
    public boolean shielded(Map<String,Object> paramMap, int userCode){
        DyResult dyResult = dyADService.pauseUser(userCode);
        if(!dyResult.isSuss()) {
            throw new RuntimeException("暂停AD账号失败！");
        }
        employeeAccountPauseSender.sendMessage(new EmployeeMessage(0, new Date(), userCode));
        return  this.insert(sqlId("shieldedUserCoed"),paramMap);
    }

    @Transactional(rollbackFor = {Exception.class})
    @ActiveMQTransactional
    public boolean enable(int userCode){
        DyResult dyResult = dyADService.resumeUser(userCode);
        if(!dyResult.isSuss()) {
            throw new RuntimeException("启用AD账号失败！");
        }
        employeeAccountActiveSender.sendMessage(new EmployeeMessage(0, new Date(), userCode));
        return this.delete(sqlId("enableUserCode"),userCode);
    }

    public boolean updateFormalDate(int userCode){
        return this.update(sqlId("updateformalDate"),userCode);
    }

    public boolean updateLeave(int userCode){
        return this.update(sqlId("updateLeave"),userCode);
    }

    public boolean createPublicoffer(Organization organization){
        EmployeeBaseInfor employeeBaseInfor = new EmployeeBaseInfor();
        employeeBaseInfor.setUserNameCn(organization.getOrgName()+"公盘");
        employeeBaseInfor.setStatus(EmployeeBaseInfor.SPACIAL);
        employeeBaseInfor.setPositionId(Position.BROKER_POSITION_ID);
        employeeBaseInfor.setCompany(organization.getCompany());
        employeeBaseInfor.setLevelId(Constants.TITLE_LEVEL_JUNIOR_BROKER_ID);
        employeeBaseInfor.setCreator(organization.getCreator());
        employeeBaseInfor.setOrgId(organization.getId());
        int gpUserCode = this.insertAndReturnId(employeeBaseInfor);
        employeeBaseInfor.setUserCode(gpUserCode);
        employeePositionService.insertEmployeePosition(new EmployeePosition(gpUserCode, Position.BROKER_POSITION_ID, organization.getId(), 0));
        return employeeCompanyService.insert(new EmployeeCompany(Company.getCompanyByName(employeeBaseInfor.getCompany()).companyIdentity(),employeeBaseInfor.getUserCode()));
    }

    public boolean updatePublicoffer(Organization organization){
        return this.update(sqlId("updatePublicoffer"),organization.getId());
    }

    /**
     *  根据工号更新列表展示的表信息
     * @param userCode 需要更新的工号
     * @return boolean
     */
    public boolean updateEmpOrgPositionByUserCode(int userCode){
        if(this.count(sqlId("checkUserCode"),userCode) == 0 ){
            EmployeeBaseInfor employeeBaseInfor = new EmployeeBaseInfor();
            employeeBaseInfor.setUserCode(userCode);
         return    this.insert(sqlId("insertEmpOrgPosition"),employeeBaseInfor);
        }else{
        return this.update(sqlId("updateEmpOrgPositionByUserCode"),userCode);
        }
    }

    /**
     * 根据岗位变更的id更新列表页展示的表信息
     * @param positionId 岗位变更的id
     * @return boolean
     */
    public boolean updateEmpOrgPositionByPositionId(int positionId){
        return this.update(sqlId("updateEmpOrgPositionByPositionId"),positionId);
    }

    /**
     * 根据组织变更的id更新列表页展示的表信息
     * @param orgId 组织变更的id
     * @return boolean
     */
    public boolean updateEmpOrgPositionByOrgId(int orgId){
        return this.update(sqlId("updateEmpOrgPositionByOrgId"),orgId);
    }

    /**
     * 编辑员工信息
     * 1 修改相关基本信息
     * 2 同步ad
     * 3 发送mq消息
     * @param employeeBaseInfor
     * @param operateType
     * @return
     */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateInfo(EmployeeBaseInfor employeeBaseInfor,OperateType operateType) {
        EmployeeBaseInfor oldEmployeeBaseInfor = findByBean(employeeBaseInfor.getUserCode());
        if(employeeBaseInfor.getJoinDate() != null){  //修改首次入职日期的操作
           if(oldEmployeeBaseInfor.getJoinDate().equals(oldEmployeeBaseInfor.getNewJoinDate())){
            employeeBaseInfor.setNewJoinDate(employeeBaseInfor.getJoinDate());
            }
        }
        if(employeeBaseInfor.getJoinDate() != null && employeeBaseInfor.getNewJoinDate() != null){
            if(employeeBaseInfor.getNewJoinDate().equals(oldEmployeeBaseInfor.getNewJoinDate())){
                employeeBaseInfor.setNewJoinDate(employeeBaseInfor.getJoinDate());
            }
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("operateType",operateType.toString());
        paramMap.put("userCode",employeeBaseInfor.getUserCode());
        try{
            this.insert(sqlId("insertLog"), paramMap);
            this.update(employeeBaseInfor);
            this.updateEmployeePosition(employeeBaseInfor);
            updateEmpOrgPositionByUserCode(employeeBaseInfor.getUserCode());

            //同步AD用户
            if(!adHelper.syncAD(employeeBaseInfor.getUserCode())) {
                throw new RuntimeException("fail to sync the AD ......");
            }
            this.insert(sqlId("insertLog"), paramMap);
            //发送员工编辑mq消息
            employeeSenderHelper.sendEmployeeBianJi(employeeBaseInfor.getUserCode(), oldEmployeeBaseInfor.getOrgId());
        }catch (Exception e){
            log.error("编辑基本信息报错"+employeeBaseInfor, e);
            throw e;
        }
        return true;
    }

    /**
     * 更新员工的主要岗位信息
     * @param employeeBaseInfor 员工对象
     * @return boolean
     */
    private boolean updateEmployeePosition(EmployeeBaseInfor employeeBaseInfor){
        EmployeePosition employeePosition = new EmployeePosition();
        employeeBaseInfor.setPositionId(employeePosition.getPositionId());
        employeeBaseInfor.setUserCode(employeeBaseInfor.getUserCode());
        employeeBaseInfor.setOrgId(employeeBaseInfor.getOrgId());
        return employeePositionService.update(employeePosition);
    }
    /**
     * 异动操作日志记录
     * @param employeeBaseInfor 员工基础信息对象
     * @return boolean
     */
    public boolean updateBaseInfoForTran(EmployeeBaseInfor employeeBaseInfor){
       return  updateInfo( employeeBaseInfor,OperateType.EmployeeBaseTransaction);
    }

    /**
     * 根据员工新的职级id 更新此员工的头衔信息
     * @param newLevelId 新的levelId
     * @param userCode 更新人的工号
     * @return boolean
     */
    public boolean updateUserTitileForTran(int newLevelId,int userCode){
        Map<String,Object> pamam = new HashMap<>();
        pamam.put("newLevelId",newLevelId);
        pamam.put("userCode",userCode);
        return this.update(sqlId("updateUserTitile"),pamam);
    }

    /**
     * 更新头衔
     * @param userTitle 头衔名称
     * @param userCode 工号
     * @return
     */
    public boolean updateUserTitle(String userTitle,int userCode){
        Map<String,Object> pamam = new HashMap<>();
        pamam.put("userTitle",userTitle);
        pamam.put("userCode",userCode);
        return this.update(sqlId("updateUserTitle"),pamam);
    }

    /**
     * 重置密码
     * @param userCode
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean resetPwd(int userCode){
        //查询员工信息
        EmployeeBaseInfor employeeBaseInfor = this.findById(sqlId("findIdcByUserCode"),userCode);
        //产生MD5密码
        String pwd = EmployeeHelper.generatePassword(employeeBaseInfor.getCredentialsNo());
        //设置员工密码
        employeeBaseInfor.setPassword(MD5Utils.str2Md5(pwd));
        //同步rtx、邮箱密码（老）
        rtxService.synchDooiooErp(pwd, userCode);
        rtxService.updateDooerpPassword(employeeBaseInfor.getPassword(), userCode);
        //修改AD密码
        DyResult dyResult = dyADService.changePassword(userCode, pwd);
        if(!dyResult.isSuss()) {
            throw new RuntimeException("fail to change the AD password ......");
        }
        //修改员工表密码
        if(update(sqlId("resetPwd"),employeeBaseInfor)) {
            //插入密码信息表
            employeePasswordInfoService.insert(new EmployeePasswordInfo(userCode, employeeBaseInfor.getPassword(), "", 1));
            return true;
        }
        return false;
    }

	/**
	 * @param pageNo
	 * @param keywords
	 * @return 查找需要离职面谈的人
	 */
	public Paginate findInterviewEmployees(int pageNo,
			String keywords,String company) {
		try {
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("company", company);
			params.put("keywords", keywords);
			VEmployeeBaseInfor  vp  = new VEmployeeBaseInfor();
			vp.setColumns(freemarkerUtil.writeTemplate("/interview/interview_columns.ftl"));
			vp.setTable(freemarkerUtil.writeTemplate("/interview/interview_table.ftl", params));
			vp.setOrderBy("t.Id");
			vp.setPageSize(Configuration.getInstance().getPageSize());
			vp.setPageNo(pageNo);
			vp.setWhere(freemarkerUtil.writeTemplate("/interview/interview_where.ftl", params));
			return this.queryForPaginate2("findInterviewEmployees",vp);
		} catch (Exception e) {
			e.printStackTrace();
			return new Paginate(pageNo, Configuration.getInstance().getPageSize());
		}
	}

    /**
     * @param userCode
     * @return 查找需要面谈人的信息，包含。。。area等信息
     */
    public EmployeeBaseInfor findEmpForInterview(int userCode) {
        return findByBean(sqlId("findEmpForInterview"), userCode);
    }
    /**
	 * @param userCode
	 * @return 查找用于添加黑名单的员工
	 */
	public EmployeeBaseInfor findEmpForAddBlack(Integer userCode,String company) {
		 Map<String,Object> params=new HashMap<>();
		 params.put("userCode", userCode);
		 params.put("company", company);
		return findByBean(sqlId("findEmpForAddBlack"),params);
	}

	/**
	 * @param blackId
	 * @param company
	 * @return 查找用于移除黑名单的员工
	 */
	public EmployeeBaseInfor findEmpForRemoveBlack(Integer blackId,
			String company) {
		 Map<String,Object> params=new HashMap<>();
		 params.put("blackId", blackId);
		 params.put("company", company);
		return findByBean(sqlId("findEmpForRemoveBlack"),params);
	}
	
	/**
	 * @param userCode
	 * @return 是否是黑名单呢
	 */
	public boolean isBlack(Integer userCode)
	{
		return count(sqlId("isBlack"),userCode) !=0;
	}
	
	/**
	 * @return
	 *  所有员工，6个月自动转正
	 */
	public List<EmployeeBaseInfor> findEmployeeToNormal()
	{
		return queryForList(sqlId("findEmployeeToNormal"), 0);
	}

    /**
     * 员工不报到，将员工状态设置为总部未报到
     * @param employeeBaseInfor
     * @param employeeRegister 员工不报到对象
     * @return
     */
    @Transactional
    public boolean updateStatusToNotRegister(EmployeeBaseInfor employeeBaseInfor, EmployeeRegister employeeRegister) {
        if (updateStatusToDelete(employeeBaseInfor.getUserCode(), employeeBaseInfor.getUpdator())) {
            return registerService.insert(employeeRegister);
        }
        return false;
    }

    /**
     * 更新状态为'总部未报到'
     * @param userCode 工号
     * @return
     */
    public boolean updateStatusToDelete(int userCode, int updator) {
        return updateStatus(userCode, EmployeeStatus.UnRegistedHeadquarters.toString(), updator);
    }

    /**
     * 更新员工状态和信息
     * @param employeeBaseInfor
     */
    @Transactional
    public boolean updateStatusToJoinAndInfo(EmployeeBaseInfor employeeBaseInfor, String mobilePhone) {
        return updateForRegister(employeeBaseInfor, mobilePhone) && updateStatusToJoin(employeeBaseInfor.getUserCode(), employeeBaseInfor.getUpdator());
    }

    /**
     * 报到成功(员工工号开通方法)
     * 更新状态为创建工号时的状态
     * 1、更改员工状态
     * 2、同步AD用户
     * 3、发送密码短信提醒
     * 4、发送新增员工推送mq消息
     * @param userCode 工号
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateStatusToJoin(int userCode, int updator) {
    	return updateStatusToJoin(userCode, updator, null);
    }

    /**
     * 报到成功(员工工号开通方法)
     * 更新状态为创建工号时的状态
     * 1、更改员工状态
     * 2、同步AD用户
     * 3、发送密码短信提醒
     * 4、发送新增员工推送mq消息
     * @param userCode 工号
     * @param updator 操作人
     * @param isBranchRegisted 门店是否已报到, 如果是组织架构前台处理经纪人报到的场合, 则传值为null, 如果是处理门店报到的场合, 如果是门店已报到则为true, 否则为false
     * @return
     */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateStatusToJoin(int userCode, int updator, Boolean isBranchRegisted) {
        boolean flag = false;
        EmployeeStatusRecord employeeStatusRecord = employeeStatusRecordService.findById(userCode);
        // 获取员工信息
        Employee newEmp = employeeService.getEmployee(userCode, EmployeeStatus.All);
        // 是否经纪人
        boolean isAgency = newEmp != null ? (newEmp.getPositionId() == 1 ? true : false) : false;
        if (employeeStatusRecord != null) {
        	if (!isAgency) {
        		// 非经纪人的场合, 直接更新员工状态为开工号时设定的值
                flag = updateStatus(userCode, employeeStatusRecord.getStatus(), updator);
        	} else {
        		if (isBranchRegisted == null) {
            		// 组织架构后台处理经纪人报到的场合, 更新为门店待报到
                    flag = updateStatus(userCode, EmployeeStatus.ToRegisteBranch.toString(), updator);
        		} else if (isBranchRegisted) {
        			// 组织架构前台处理经纪人已报到的场合, 更新为开工号时设定的值
                    flag = updateStatus(userCode, employeeStatusRecord.getStatus(), updator);
        		} else if (!isBranchRegisted) {
        			// 组织架构前台处理经纪人未报到的场合, 更新为门店未报到 
                    flag = updateStatus(userCode, EmployeeStatus.UnRegistedBranch.toString(), updator);
        			
        		}
        	}
        } else {
        	if (!isAgency) {
        		// 非经纪人的场合, 直接更新员工状态为'试用期'
                flag = updateStatus(userCode, EmployeeStatus.UnFormal.toString(), updator);
        	} else {
        		// 经纪人的场合, 更新员工状态为'门店待报到'
                flag = updateStatus(userCode, EmployeeStatus.ToRegisteBranch.toString(), updator);
        	}
        }
        //同步AD用户
        if (flag) {
            if (adHelper.syncAD(userCode, Constants.AD_REGISTER)) {
                EmployeeBaseInfor employeeBaseInfor = findById(userCode);
                if (employeeBaseInfor != null) {
                    //发送新增推送消息
                    if (!isAgency || (isBranchRegisted != null && isBranchRegisted && isAgency)) {
                        //正式环境 发送短信
//                        if (Configuration.getInstance().getEnv().equals(DyPlusConstants.PRODUCTION)) {
                            sendMsg(employeeBaseInfor);
//                        }
                            // 非经纪人的场合, 发送推送消息
                            employeeSenderHelper.sendEmployeeXinZeng(userCode, employeeBaseInfor.getOrgId());
                    }
                    return flag;
                }
            } else {
                throw new RuntimeException("fail to sync the AD ......"+newEmp);
            }
        }
        return false;
    }

    /**
     * 恢复待报到状态
     * @param userCode  恢复工号
     * @param updator   操作人
     * @param userStatus 用户状态
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean recoverDelete(int userCode, int updator, String userStatus) {
    	boolean updateSucessFlg = false;
    	if (EmployeeStatus.UnRegistedHeadquarters.toString().equals(userStatus)) {
    		// 恢复总部待报到的场合
    		updateSucessFlg = updateStatus(userCode, EmployeeStatus.ToRegisteHeadquarters.toString(), updator);
    	} else if (EmployeeStatus.UnRegistedBranch.toString().equals(userStatus)) {
    		// 恢复门店待报到的场合
    		updateSucessFlg = updateStatus(userCode, EmployeeStatus.ToRegisteBranch.toString(), updator);
    	}

    	// 更新是否成功
        if (updateSucessFlg) {
        	// 删除记录中未报到的用户
            registerService.delete(userCode);
            // 同步AD用户
            if (!adHelper.syncAD(userCode, Constants.AD_RECOVER)) {
                throw new RuntimeException("fail to sync the AD ......userCode="+userCode);
            }
            return true;
        }
        return false;
    }

    /**
     * 更新员工状态
     * @param userCode 工号
     * @param status   状态
     * @return
     */
    private boolean updateStatus(int userCode, String status, int updator) {
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("status", status);
        param.put("updator", updator);
        return update(sqlId("updateStatus"), param);
    }

    /**
     * 更改报到信息
     * @param employeeBaseInfor
     * @return
     */
    @Transactional
    public boolean updateForRegister(EmployeeBaseInfor employeeBaseInfor, String mobilePhone) {
        if(update(sqlId("updateForRegister"), employeeBaseInfor) && employeeDetailsService.updateMobilePhone(employeeBaseInfor.getUserCode(), mobilePhone)) {
            return true;
        }
        return false;
    }

//    /**
//     * 批量将员工状态置为删除
//     * @return
//     */
//    public boolean updateStatusToDelete() {
//        Map<String, Object> param = new HashMap<>();
//        param.put("day", Configuration.getInstance().getRegisterOutDay());
//        return update(sqlId("updateStatusToDelete"), param);
//    }

    /**
     * 获得当天生日员工
     *  根据当前时间转换农历
     * @return
     */
    public List<EmployeeBaseInfor> getBirthdayEmployee() {
    	Map<String,Object> params=new HashMap<>();
    	if (LunarSolarUtil.isLunarLeapMonth(new Date())) {
			params.put("lunar", null);
			log.info("获取当天要过生日的员工=================》农历是闰月不查农历生日");
		}else{
			params.put("lunar",Integer.valueOf(1));
			int lunarinfo[] =LunarSolarUtil.solarToLunar(new Date());
			params.put("month",lunarinfo[1] );
			params.put("day", lunarinfo[2]);
			log.info("获取当天要过生日的员工=================》农历： "+lunarinfo[1]+"月"+lunarinfo[2]+"日");
		}
        return queryForList(sqlId("getBirthdayEmployee") , params);
    }

    /**
     * 获得当天入职满2年以上员工
     * @return
     */
    public List<EmployeeBaseInfor> getOldEmployee() {
        return queryForList(sqlId("getOldEmployee"), 0);
    }

    /**
     * 获得同部门员工（不包含自己）
     * @param userCode
     * @return
     */
    public List<EmployeeBaseInfor> getTogetherEmployee(int userCode) {
        return queryForList(sqlId("getTogetherEmployee"), userCode);
    }

	/**
	  * @since: 3.0.1 
	  * @param userCodes
	  * @param company
	  * @return
	  * <p>
	  * 根据工号查询员工信息
	  * </p>   
	 */
	public List<EmployeeBaseInfor> findEmployeeByUsercodes(String userCodes, String company) {
		Map<String,Object> params=new HashMap<>();
		params.put("userCodes", userCodes);
		params.put("company", company);
		return queryForList(sqlId("findEmployeeByUsercodes"),params);
	}

    /**
     * 根据工号查询部门带有层级关系的名称
     * @param userCode
     * @return
     */
    public String findorgLongNameByUserCode(int userCode) {
        return (String)queryForObject(sqlId("findorgLongNameByUserCode"), userCode);
    }

    /**
     * 根据短信接口返回的数据查询员工信息
     * @param smsReportsList
     * @return
     */
    public List<EmployeeBaseInfor> queryBySmsReports(List<SmsReports> smsReportsList) {
        Map<String,Object> params=new HashMap<>();
        params.put("smsReportsList", smsReportsList);
        return this.queryForList(sqlId("queryBySmsReports"), params);
    }

	/**
	 * 根据工号姓名模糊查询对应公司在职的10个员工
	 * 
	 * @param variables
	 * @return
	 */
	public List<Object> searchEmpByCodeAndName(Map<String, Object> variables) {
		return queryDao.queryForListObj(sqlId("searchEmpByCodeAndName"), variables);
	}

    /**
     * 根据OrgId查询员工
     * @since: 2014-06-20 15:45:55
     * @param orgId
     * @return
     */
    public List<EmployeeBaseInfor> queryByOrgId(int orgId) {
        return queryForList(sqlId("queryByOrgId"), orgId);
    }

}

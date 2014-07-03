package com.gusi.boms.helper;

import com.gusi.boms.service.EmployeeBaseInforService;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.model.Organization;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.oms.dyUser.service.OrganizationService;
import com.dooioo.plus.other.model.DyResult;
import com.dooioo.plus.other.service.DyADService;
import com.dooioo.plus.util.ADUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: AdHelper
 * @Author: fdj
 * @Since: 2014-02-07 15:49
 */
@Component
public class AdHelper {

    public static final Log LOG = LogFactory.getLog(AdHelper.class);

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private DyADService dyADService;
    @Autowired
    private MailHelper mailHelper;

    /**
     * 根据id同步AD上的OU
     * @param id
     * @return
     */
    public boolean syncOU(int id) {
        LOG.info("同步 OU 开始。----------------------" + id);
        try {
            //根据id获取组织信息
            Organization organization = organizationService.getOrgByOrgId(id);
            //获取数据库中是否存在组织
            boolean isOrgExist = isOrgExist(organization);
            //获取AD服务器上是否存在组织
            boolean isOUExist = ADUtil.isOUExist(id);
            //数据库中不存在，AD上也不存在，那么不做操作
            if(!isOrgExist && !isOUExist) {
                return true;
            //数据库中不存在，AD上存在，那么删除AD上的OU
            } else if(!isOrgExist && isOUExist) {
                return ADUtil.deleteOU(id).isSuss();
            //数据库中存在，AD上不存在，那么新增OU
            } else if(isOrgExist && !isOUExist) {
                return dyADService.createOU(organization).isSuss();
            //数据库中存在，AD上存在，那么更新OU
            } else if(isOrgExist && isOUExist) {
                return dyADService.updateOU(id).isSuss();
            }
            return false;
        } catch (Exception e) {
            LOG.error("同步ou失败,id为" + id, e);
            mailHelper.sendMQErrorMessage(id + "这个组织同步OU没有成功！");
            return false;
        } finally {
            LOG.info("同步 OU 结束。----------------------" + id);
        }
    }

    /**
     * 组织架构中是否存在（排除状态为停用的组织）
     * @param organization
     * @return
     */
    private boolean isOrgExist(Organization organization) {
        boolean isOrgExist = organization != null;
        if(isOrgExist) {
            switch (organization.getStatus()) {
                case -1:
                    isOrgExist = false;
                    break;
                case 0:
                    isOrgExist = true;
                    break;
                case 1:
                    isOrgExist = true;
                    break;
                case 2:
                    isOrgExist = true;
                    break;
            }
        }
        return isOrgExist;
    }


    /**
     * 同步AD用户
     * @param userCode 员工工号
     * @return         是否同步成功
     */
    public boolean syncAD(int userCode) {
        LOG.info("-------------------" + userCode +"同步AD开始");
        Employee employee = employeeService.getEmployee(userCode);
        DyResult dyResult = new DyResult();
        if(employee == null && dyADService.exist(userCode)) {
            dyResult = dyADService.deleteUser(userCode);
            //如果同步有问题发邮件提醒
            if(dyADService.exist(userCode)) {
                LOG.error("应该从AD删除，但是没有删除成功。");
                mailHelper.sendADMail(userCode + "这个工号应该从AD上删除，但是没有删除成功！");
            }
        } else if(employee != null && dyADService.exist(userCode)) {
            dyResult = dyADService.updateUser(userCode);
        } else if(employee != null && !dyADService.exist(userCode)) {
            dyResult = dyADService.createUser(employee, EmployeeHelper.generatePassword(employee.getCredential()));
            employeeBaseInforService.resetPwd(userCode);
//            psmsSendService.sendMsg(employee.getMobilePhone(),"您的新密码是身份证后六位，请及时修改密码。" ,Configuration.getInstance().getAppCode());
            //如果同步有问题发邮件提醒
            if(!dyADService.exist(userCode)) {
                LOG.error("应该从生成AD，但是没有生成成功。");
                mailHelper.sendADMail(userCode + "这个工号应该从AD上生成，但是没有生成成功！");
            }
        }
        LOG.info("-------------------" + userCode +"同步AD结束，结果是" + dyResult.isSuss());
        return dyResult.isSuss();
    }

    /**
     * 同步AD，并打印相关操作动作
     * @param userCode 员工工号
     * @param operateType 操作
     * @return              是否同步成功
     */
    public boolean syncAD(int userCode, String operateType) {
        LOG.info("----------------------------" + operateType);
        return this.syncAD(userCode);
    }


}

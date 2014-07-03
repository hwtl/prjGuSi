package com.gusi.empTransfer.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.gusi.boms.service.ParameterService;
import com.gusi.empTransfer.common.EmpTransferConstants;
import com.gusi.empTransfer.dto.EmpTransferOrg;
import com.gusi.empTransfer.dto.VEmployeeTransfer;
import com.gusi.empTransfer.helper.EmployeeTransferHelper;
import com.dooioo.web.service.BaseService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-02-20 11:40
 * @Description: 员工转调组织相关业务
 */
@Service
public class EmpTransferOrgService extends BaseService<EmpTransferOrg> {

    private static final Log LOG = LogFactory.getLog(EmpTransferOrgService.class);

    @Autowired
    private ParameterService parameterService;

    /**
     * 根据工号查找员工所在部门（包括兼职部门）
     *
     * @param userCode
     * @return
     */
    public List<EmpTransferOrg> queryWithPartionOrgs(int userCode) {
        List<EmpTransferOrg> list = null;
        try {
            list = queryForList(sqlId("queryWithPartionOrgs"), userCode);
            for (EmpTransferOrg empTransferOrg : list) {
                //判断是否中介部
                if (empTransferOrg.getOrgLongCode().startsWith(this.findById(Configuration.getInstance().getZhongJieBu()).getOrgLongCode())) {
                    empTransferOrg.setTransferType(EmpTransferConstants.ZJB_CODE);
                    empTransferOrg.setTransferTypeStr(parameterService.findByOptionCode(EmpTransferConstants.ZJB_CODE).getOptionValue());
                }
                //判断是否租赁部
                else if (empTransferOrg.getOrgLongCode().startsWith(this.findById(Configuration.getInstance().getZuLingBu()).getOrgLongCode())) {
                    empTransferOrg.setTransferType(EmpTransferConstants.ZLB_CODE);
                    empTransferOrg.setTransferTypeStr(parameterService.findByOptionCode(EmpTransferConstants.ZLB_CODE).getOptionValue());
                }
                //判断是否新房部
                else if (empTransferOrg.getOrgLongCode().startsWith(this.findById(Configuration.getInstance().getXinFangXiaoShouBu()).getOrgLongCode())) {
                    empTransferOrg.setTransferType(EmpTransferConstants.XFXS_CODE);
                    empTransferOrg.setTransferTypeStr(parameterService.findByOptionCode(EmpTransferConstants.XFXS_CODE).getOptionValue());
                }
                //判断是否代理部
                else if (empTransferOrg.getOrgLongCode().startsWith(this.findById(Configuration.getInstance().getDaiLiXiangMuBu()).getOrgLongCode())) {
                    empTransferOrg.setTransferType(EmpTransferConstants.DLXM_CODE);
                    empTransferOrg.setTransferTypeStr(parameterService.findByOptionCode(EmpTransferConstants.DLXM_CODE).getOptionValue());
                }
                //其他都默认为后台
                else {
                    empTransferOrg.setTransferType(EmpTransferConstants.HT_CODE);
                    empTransferOrg.setTransferTypeStr(parameterService.findByOptionCode(EmpTransferConstants.HT_CODE).getOptionValue());
                }
            }
        } catch (Exception e) {
            LOG.error("获取转入部门失败！userCode：" + userCode, e);
        }
        return list;
    }

    /**
     * 获取高层权限
     * @param vEmployeeTransfer
     * @param map
     */
    public void initDirectorApprove(VEmployeeTransfer vEmployeeTransfer, Map<String, Object> map) {
        try {
            // /转大西区
            EmpTransferOrg exportOrg = this.findById(Configuration.getInstance().getDaXiQu());
            if (initDirectorApprove(vEmployeeTransfer, map, exportOrg)) {
                return;
            }
            //转大东区
            exportOrg = this.findById(Configuration.getInstance().getDaDongQu());
            if (initDirectorApprove(vEmployeeTransfer, map, exportOrg)) {
                return;
            }
            //转大南区
            exportOrg = this.findById(Configuration.getInstance().getDaNanQu());
            if (initDirectorApprove(vEmployeeTransfer, map, exportOrg)) {
                return;
            }
            //转大北区
            exportOrg = this.findById(Configuration.getInstance().getDaBeiQu());
            if (initDirectorApprove(vEmployeeTransfer, map, exportOrg)) {
                return;
            }
            //转租赁部
            exportOrg = this.findById(Configuration.getInstance().getZuLingBu());
            if (initDirectorApprove(vEmployeeTransfer, map, exportOrg)) {
                return;
            }
            //转营销代理部
            exportOrg = this.findById(Configuration.getInstance().getYingXiaoDaiLiBu());
            if (initDirectorApprove(vEmployeeTransfer, map, exportOrg)) {
                return;
            }
            //其他高层权限
            map.put(Constants.PRIVILEGE_URL_KEY_EXPORT_DIRECTOR_APPROVE, EmployeeTransferHelper.splicePrivilege(vEmployeeTransfer.getOldAreaId(),Constants.PRIVILEGE_URL_EXPORT_DIRECTOR_APPROVE));
        } catch (Exception e) {
            LOG.error("获取高层权限失败！vEmployeeTransfer：" + vEmployeeTransfer, e);
        }
    }

    /**
     * 设置高层权限
     * @param vEmployeeTransfer
     * @param map
     * @param exportOrg
     * @return
     */
    private boolean initDirectorApprove(VEmployeeTransfer vEmployeeTransfer, Map<String, Object> map, EmpTransferOrg exportOrg) {
        if (vEmployeeTransfer.getOldOrgLongCode().startsWith(exportOrg.getOrgLongCode())) {
            map.put(Constants.PRIVILEGE_URL_KEY_EXPORT_DIRECTOR_APPROVE, EmployeeTransferHelper.splicePrivilege(exportOrg.getId(),Constants.PRIVILEGE_URL_EXPORT_DIRECTOR_APPROVE));
            return true;
        }
        return false;
    }

    /**
     * 根据工号获取兼职部门
     * @param userCode
     * @return
     */
    public List<EmpTransferOrg> queryPartOrgs(int userCode) {
        return this.queryForList(sqlId("queryPartOrgs"), userCode);
    }

}

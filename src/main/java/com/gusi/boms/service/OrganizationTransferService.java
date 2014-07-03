package com.gusi.boms.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.common.Constants;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.service.BaseService;
import com.gusi.boms.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-23 上午10:03
 * @Description: 整组转调业务
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrganizationTransferService extends BaseService<OrganizationTransfer> {
	private final static Logger LOGGER=Logger.getLogger(OrganizationTransferService.class);
	
    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private Organization2Service organization2Service;
    @Autowired
    private AccountingDepartmentService accountingDepartmentService;
    @Autowired
    private EmployeeChangeRecordsService employeeChangeRecordsService;
    
    /**
	  * @since: 3.0.5 
	  * @param organizationTransfer
	  * @param emp
	  * @return
	  * <p>
	  *  门店转区操作。
	  * </p>   
	 */
    @Transactional(value = "transactionManager")
    @ActiveMQTransactional
	public boolean activeStoreTransfer(OrganizationTransfer storeTransfer, Employee emp) {
		 if(storeTransfer.getStatus()==OrganizationTransfer.TRANSFER_TYPE_TEMP &&
				 storeTransfer.getActiveDate().before(new Date())) {
    		 LOGGER.info("开始执行门店转调生效的操作："+storeTransfer+",操作人："+emp);
    		  //更新门店的信息，添加门店以及分行的核算组织。
    		 //更新orgA 门店的基本信息
             Organization orgA =organization2Service.findById(storeTransfer.getOrgAId());
             if (!this.updateOrg(orgA, storeTransfer.getOrgBParentId(), 
            		 storeTransfer.getOrgBName(), emp)) {
            	 throw new IllegalArgumentException("门店转区时，更新门店出错，"+orgA+",转调信息："+storeTransfer+",操作人："+emp);
             }
             //更新转调信息的状态。
             if (!updateStatus(storeTransfer.getId(),
            		 OrganizationTransfer.TRANSFER_TYPE_OK,OrganizationTransfer.TRANSFER_TYPE_TEMP,
            		 emp.getUserCode())) {
            	 throw new IllegalArgumentException("门店转区时更新转调记录状态出错，"+storeTransfer+",操作人："+emp);
             }
             //新增门店核算组织
            if (orgA.getStatus()==Organization.STATUS_NORMAL &&
            		!accountingDepartmentService.saveAccountingDepartment(
            				storeTransfer.getActiveDate(),null,orgA.getOrgName(),orgA.getId(),orgA.getOrgName(),
            				orgA.getOrgType(),orgA.getManager(), Constants.GUANLIYUAN,true)) {
            	 throw new IllegalArgumentException("门店转区时，新增门店的核算组织出错，转调记录："+
            			 storeTransfer+",操作人："+emp+",门店信息："+orgA);
			} 
            List<Organization> subOrgs=organization2Service.findSubOrgsWithView(orgA.getId());
            if (subOrgs!=null &&!subOrgs.isEmpty()) {
            	//保存分行核算组织
				for (Organization branch : subOrgs) {
					accountingDepartmentService.saveAccountingDepartment(storeTransfer.getActiveDate(),
							null, branch.getOrgName()+"("+((VOrganization)branch).getManagerName()+")", branch.getId(),branch.getOrgName(),
							branch.getOrgType(), branch.getManager(), Constants.GUANLIYUAN, true);
				}
			}
            //转区的时候添加空组
            List<AccountingDepartment> emptyOrgs=accountingDepartmentService.findEmptyOrgsByStoreId(storeTransfer.getOrgAId());
            if (emptyOrgs !=null && !emptyOrgs.isEmpty()) {
				for (AccountingDepartment empty : emptyOrgs) {
					empty.setStartTime(storeTransfer.getActiveDate());
					empty.setEndTime(storeTransfer.getActiveDate());
					accountingDepartmentService.addEmptyOrg(empty);
				}
			}
    	 }
		return true;
	}

    
	/**
     * 查找当日之前需求生效的转调
     * @return 需要转调的记录集合
     */
    public List<OrganizationTransfer> queryActiveToday() {
        return queryForList(sqlId("queryActiveToday"), 0);
    }

    /**
      * @since: 3.0.5 
      * @param organizationTransfer
      * @param employee 操作人
      * @return
      * <p>
      *  单组转调生效
      * </p>   
     */
    @Transactional(value = "transactionManager")
    @ActiveMQTransactional
    public boolean activeSingleTransfer(OrganizationTransfer organizationTransfer, 
    		Employee employee){
    	 if(organizationTransfer.getStatus()==OrganizationTransfer.TRANSFER_TYPE_TEMP &&
    			 organizationTransfer.getActiveDate().before(new Date())) {
    		 LOGGER.info("开始执行单组转调生效的操作："+organizationTransfer+",操作人："+employee);
    		 //更新orgA的基本信息
             VOrganization orgA = (VOrganization)organization2Service.findViewForTransfer(organizationTransfer.getOrgAId());
             if (!this.updateOrg(orgA, organizationTransfer.getOrgBParentId(), 
            		 organizationTransfer.getOrgBName(), employee)) {
            	 throw new IllegalArgumentException("单组转调，更新组织出错，"+orgA+",转调信息："+organizationTransfer);
             }
             //更新转调信息的状态。
             if (!updateStatus(organizationTransfer.getId(),
            		 OrganizationTransfer.TRANSFER_TYPE_OK,OrganizationTransfer.TRANSFER_TYPE_TEMP,
            		 employee.getUserCode())) {
            	 throw new IllegalArgumentException("单组转调更新转调组织状态出错，"+organizationTransfer);
             }
             //新增核算组织(正常的分行组织)
             if (orgA.getStatus()==Organization.STATUS_NORMAL 
            		 &&orgA.getManager() !=null && orgA.getManager() !=0) {
            	 accountingDepartmentService.saveAccountingDepartment(
            			 organizationTransfer.getActiveDate(),null,organizationTransfer.getOrgBName()+"("+orgA.getManagerName()+")", 
            			 orgA.getId(),organizationTransfer.getOrgBName(),orgA.getOrgType(),orgA.getManager(),Constants.GUANLIYUAN,false);
			}
             //分行下面的员工添加一条转调异动记录
             employeeChangeRecordsService.generateChanges(organizationTransfer.getOrgAId(), organizationTransfer.getOrgAName(), organizationTransfer.getActiveDate());
    	 }
    	 return true;
    }
    /**
     * 两组对调
     * @param organizationTransfer 转调记录
     * @param employee             转调创建人
     * @return                     是否生效
     */
    @Transactional(value = "transactionManager")
    @ActiveMQTransactional
    public boolean activeBranchExchange(OrganizationTransfer organizationTransfer, Employee employee) {
        if(organizationTransfer.getStatus()==OrganizationTransfer.TRANSFER_TYPE_TEMP &&
        		organizationTransfer.getActiveDate().before(new Date())) {
        	VOrganization  orgA = (VOrganization) organization2Service.findViewForTransfer(organizationTransfer.getOrgAId());
        	VOrganization  orgB = (VOrganization) organization2Service.findViewForTransfer(organizationTransfer.getOrgBId());
        	//先将其中一组名称临时变更
        	if (!updateOrgToTempName(employee, orgB, orgA.getOrgName())) {
				throw new IllegalArgumentException("两组对调时，将组B名称临时变更失败,orgB信息："+orgB+",orgA信息："
							+orgA+",转调信息："+organizationTransfer);
			}
        	//开始更新orgA的基本信息
        	if (!this.updateOrg(orgA, organizationTransfer.getOrgBParentId(), 
        			organizationTransfer.getOrgBName(), employee)) {
        		throw new IllegalArgumentException("两组对调时，更新orgA信息出错："+orgA+",转调信息："+organizationTransfer);
			}
        	//开始更新orgB的基本信息
        	if (!this.updateOrg(orgB, organizationTransfer.getOrgAParentId(), 
        			organizationTransfer.getOrgAName(), employee)) {
        		throw new IllegalArgumentException("两组对调时，更新orgA信息出错："+orgA+",转调信息："+organizationTransfer);
			}
            //更新转调记录状态
        	if (!updateStatus(organizationTransfer.getId(),OrganizationTransfer.TRANSFER_TYPE_OK,
            		OrganizationTransfer.TRANSFER_TYPE_TEMP, employee.getUserCode())){
            	throw new IllegalArgumentException("两组对调时，更新转调信息出错："+organizationTransfer);
			}
        	//新增核算组织A
           if ((orgA.getStatus()==Organization.STATUS_NORMAL&&orgA.getManager() !=null) 
        		   &&!accountingDepartmentService.saveAccountingDepartment(
        				   organizationTransfer.getActiveDate(),null,organizationTransfer.getOrgBName()+"("+orgA.getManagerName()+")",
        				   orgA.getId(),organizationTransfer.getOrgBName(),orgA.getOrgType(),orgA.getManager(),Constants.GUANLIYUAN,false)) {
        	   throw new IllegalArgumentException("两组对调时添加核算部门orgA出错，"+orgA+",转调信息："+organizationTransfer);
           } 
           //新增核算组织B
           if ((orgB.getStatus()==Organization.STATUS_NORMAL&&orgB.getManager() !=null) &&
        		   !accountingDepartmentService.saveAccountingDepartment(organizationTransfer.getActiveDate(),null,
                     		 organizationTransfer.getOrgAName()+"("+orgB.getManagerName()+")",orgB.getId(),organizationTransfer.getOrgAName(),
             		     		 orgB.getOrgType(),orgB.getManager(),Constants.GUANLIYUAN,false)) {
        	   throw new IllegalArgumentException("两组对调时添加核算部门orgB出错，"+orgB+",转调信息："+organizationTransfer);
           }
            //分行下面的员工添加一条转调异动记录
            employeeChangeRecordsService.generateChanges(organizationTransfer.getOrgAId(), organizationTransfer.getOrgAName(), organizationTransfer.getActiveDate());
            employeeChangeRecordsService.generateChanges(organizationTransfer.getOrgBId(), organizationTransfer.getOrgBName(), organizationTransfer.getActiveDate());
        }
        return true;
    }

    /**
     * 更新组织信息(不更新核算部门)
     * @param org         需要更新的组织
     * @param parentId    修改后的parentId
     * @param orgName     修改后的组织名称
     * @param updator     修改人
     * @return            是否修改成功
     */
    private boolean updateOrg(Organization org, int parentId, String orgName, Employee updator) {
        org.setParentId(parentId);
        org.setOrgName(orgName);
        return organization2Service.updateOrgAndInsertHistory(updator, org, Constants.ORG_INFO_TRRANSFER, OrganizationLog.OPERATETYPE_TRANSFER);
    }
    /**
     * 整组对调时，需要先把一个组织名称换成临时的名字
     * @param employee 转调创建人
     * @param orgB     需要设成临时名称的组织
     * @param toName   转调后的名字
     * @return         是否修改成功
     */
    private boolean updateOrgToTempName(Employee employee, Organization orgB, String toName) {
        orgB.setOrgName(orgB.getOrgName() + Constants.TRANSFER_TEMP_ORGNAME + toName);
        return organization2Service.updateOrgAndInsertHistory(employee, orgB);
    }

    /**
     * 更新状态
     * @param id     需要更新的记录id
     * @param status 状态
     * @return       是否更新成功
     */
    public boolean updateStatus(int id, int newStatus,int oldStatus, int userCode) {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("id", id);
    	params.put("newStatus", newStatus);
    	params.put("oldStatus", oldStatus);
    	params.put("updator", userCode);
        return update(sqlId("updateStatus"),params);
    }

    /**
     * 是否没有未生效的记录
     * @param orgId         组织id
     * @return              是否可以进行转调（没有未生效的转调记录）
     */
    public boolean checkTransfer(int orgId) {
        return count(sqlId("checkTransfer"), orgId) == 0;
    }
    /**
      * @since: 3.0.5 
      * @param storeId
      * @return
      * <p>
      *  检查门店转区是否可以进行。如果其下分行又在转调中，则不能转调。
      * </p>   
     */
    public boolean checkStoreTransfer(String storeIds) {
        return count(sqlId("checkStoreTransfer"),storeIds) == 0;
    }
    /**
     * 查找整组转调记录
     * @param params
     * @return
     */
    public Paginate findOrgTransfers(Map<String, Object> params) {
        int pageNo = Constants.DEFAULT_PAGENO;
        try {
            pageNo = (Integer)params.get("pageNo");
            VOrganizationTransfer vp = new VOrganizationTransfer();
            vp.setColumns(freemarkerUtil.writeTemplate("/orgTransfer/columns.ftl"));
            vp.setTable(freemarkerUtil.writeTemplate("/orgTransfer/table.ftl"));
            vp.setOrderBy(freemarkerUtil.writeTemplate("/orgTransfer/order.ftl"));
            vp.setPageSize(Configuration.getInstance().getPageSize());
            vp.setPageNo(pageNo);
            vp.setWhere(freemarkerUtil.writeTemplate("/orgTransfer/where.ftl", params));
            return this.queryForPaginate2("findOrgTransfers", vp);
        } catch (Exception e) {
           LOGGER.error("组织转调记录查询出错",e);
            return new Paginate(pageNo, Configuration.getInstance().getPageSize());
        }
    }

	/**
	  * @since: 3.0.5 
	  * @param orgAIds
	  * @param areaId
	  * @return
	  * <p>
	  *  检查门店是否已经在区域里。
	  * </p>   
	 */
	public boolean checkStoreHasInArea(String orgAIds, int areaId) {
		Map<String,Object> params=new HashMap<>();
		params.put("orgIds", orgAIds);
		params.put("areaId", areaId);
		 return count(sqlId("checkStoreHasInArea"),params) == 0;
	}


}

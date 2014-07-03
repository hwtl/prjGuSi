package com.gusi.boms.service;

import java.util.List;

import com.gusi.boms.model.OrganizationTransfer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.helper.MailHelper;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;
import com.dooioo.plus.util.GlobalConfigUtil;

/**
 * @author "liuhui" 
 * @since 3.0.5
 * @createAt 2014-4-18 上午9:29:21
 * <p>
 *  组织转调Servic代理类，解决Service内方法调用事务无法传播的问题。
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
@Service
public class OrganizationTransferProxyService {
	private static Logger LOGGER=Logger.getLogger(OrganizationTransferService.class);
	@Autowired
	MailHelper mailHelper;
	@Autowired
	private OrganizationTransferService organizationTransferService;
	@Autowired
	private EmployeeService employeeService;
	  /**
     * @since: 3.0.5 
     * @param organizationTransfer
     * @param emp
     * @return
     * <p>
     *  处理转调
     * </p>   
    */
  private boolean doTransfer(OrganizationTransfer organizationTransfer,Employee emp)
   {
   	switch (organizationTransfer.getTransferMode()) {
		case OrganizationTransfer.MODE_SINGLE:
			 return organizationTransferService.activeSingleTransfer(organizationTransfer, emp);
		case OrganizationTransfer.MODE_DOUBLE:
			return organizationTransferService.activeBranchExchange(organizationTransfer, emp);
		case OrganizationTransfer.MODE_DEPT_TRANSFER:
			return organizationTransferService.activeStoreTransfer(organizationTransfer,emp);
		}
   	return true;
   }
   
   /**
    * @since: 3.0.5 
    * @param organizationTransfer 转调记录
    * @param employee 操作人
    * @param area 区域
    * @return
    * <p>
    *  //解析orgAids，将多个门店拆分成多条记录。
    * </p>   
   */
  @Transactional(value="transactionManager")
  @ActiveMQTransactional
  public boolean saveStoreTransfer(List<OrganizationTransfer> lts,Employee emp) { 
  	for (OrganizationTransfer ott : lts) {
  			ott.setId(organizationTransferService.insertAndReturnId(ott));
  			doTransfer(ott, emp);
		}
  	return true;
  }
  /**
   * 保存转调记录
   * @param organizationTransfer 转调记录
   * @param employee             创建人
   * @return
   */
  @Transactional(value="transactionManager")
  @ActiveMQTransactional
  public boolean saveBranchTransfer(OrganizationTransfer organizationTransfer,Employee emp){
  	organizationTransfer.setId(organizationTransferService.insertAndReturnId(organizationTransfer));
  	doTransfer(organizationTransfer, emp);
  	return true;
  }
  
  /**
  * @since: 3.0.5 
  * @param id
  * @param userCode更新人。
  * @return
  * <p>
  * 删除未生效的转调记录
  * </p>   
 */
  @Transactional
  public boolean delTransfer(int id,int userCode){
	  return organizationTransferService.updateStatus(id,OrganizationTransfer.TRANSFER_TYPE_DELETE,
			  OrganizationTransfer.TRANSFER_TYPE_TEMP, userCode);
  }
  /**
  * @since: 3.0.5 
  * <p>
  *  执行今天生效的JOB
  * </p>   
 */
  public void doActiveOrgTransferJob(){
	  List<OrganizationTransfer> organizationTransfers=organizationTransferService.queryActiveToday();
	  LOGGER.info("---------------------------开始处理整组转调生效--------------一共有" + organizationTransfers.size() + "条待处理------------------");
	    if (organizationTransfers == null || organizationTransfers.size() == 0) {
	        return;
	    }
	    for (OrganizationTransfer ot : organizationTransfers) {
	    	try {
	    		doTransfer(ot,employeeService.getEmployee(ot.getCreator(), EmployeeStatus.All));
	    		LOGGER.info("转调成功，转调记录："+ot);
	    	 } catch (Exception e) {
	    		 LOGGER.error("组织转调失败,转调记录："+ot, e);
	    		 mailHelper.sendMail("【JOB-"+GlobalConfigUtil.getCurrentEnv()+"】组织转调失败,转调信息："+ot, e);
	    		 continue;
	    	 }
	    }
	   LOGGER.info("---------------------------结束处理整组转调生效--------------");
  }
}



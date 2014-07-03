package com.gusi.boms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.activemq.sender.AccountingDeptAddSender;
import com.gusi.activemq.sender.AccountingDeptSyncRelationSender;
import com.gusi.boms.model.AccountingDepartment;
import com.gusi.boms.model.EASCustomer;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyEnums.OrganizationType;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.dao.QueryDao;
import com.dooioo.web.service.BaseService;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:21:20 ) 
 */
@Service
public class AccountingDepartmentService extends BaseService<AccountingDepartment> {
	private static final Logger log= Logger.getLogger(AccountingDepartmentService.class);
	@Autowired
	private QueryDao accountDao;
	@Autowired
	private QueryDao easDao;
	@Autowired
	private AccountingDeptAddSender accountingDeptAddSender;
	@Autowired
	private AccountingDeptSyncRelationSender accountingDeptSyncRelationSender;
	
    /**
     * @param org
     * @return 是否存在同名同OrgId的组织
     */
    public boolean existSameDepartment(int orgId,String orgName){
    	 Map<String,Object> params=new HashMap<String, Object>();
    	 params.put("orgId", orgId);
    	 params.put("orgName", orgName);
    	 return queryDao.count(sqlId("existSameDepartment"),params) >0 ;
    }
    
    /**
     * @param orgId
     * @return 根据OrgId查找所有核算部门
     */
   public List<AccountingDepartment> findDepartmentsByOrgId(int orgId)
    {
    	return queryForList(sqlId("findDepartmentsByOrgId"),orgId);
    }

   /**
	  *	<p>
	  *  查找用于初始新增的核算组织
	  * </p>
	  * @since: 2.0.0
	  * @return    
	 */
   public List<AccountingDepartment> findForAddAccountDeptJob(){
	   return queryForList(sqlId("findForAddAccountDeptJob"), 0);
   }
   
   /**
	  *	<p>
	  *  初始新增核算组织
	  * </p>
	  * @since: 2.0.0
	  * @param department
	  * @return    
	 */
   @Transactional
   @ActiveMQTransactional
   public void initAddAccountingDept(AccountingDepartment department){
	   String orgName=department.getOrgName();
	   String accountingOrgName=department.getOrgName();
	   String orgType=department.getOrgType();
	   if (OrganizationType.Branch.toString().equals(orgType) && null !=department.getManagerName()) {
		   //如果是分行，则需要拼上xxx（managerName）
		  accountingOrgName=orgName+"("+department.getManagerName()+")";
	   }
	   department.setOrgName(accountingOrgName);
	   int id=insertAndReturnId(department);
	   accountingDeptAddSender.sendMessage(id, accountingOrgName,department.getOrgId(), orgName, orgType,null);
   }
   
   /**
	  *	<p>
	  * 新增空组
	  * </p>
	  * @since: 2.0.0
	  * @param department    
	 */
   @Transactional
   @ActiveMQTransactional
   public void addEmptyOrg(AccountingDepartment department){
	   int oldId=department.getId();
	   int newId=insertAndReturnId(sqlId("insertEmpty"),department);
	   accountingDeptSyncRelationSender.sendMessage(newId, department.getOrgName(), department.getOrgId(), department.getOrgName(),
			   department.getOrgType(), oldId);
   }
   /**
	  *	<p>
	  * 查找最近一条 endTime is null 的核算组织
	  * </p>
	  * @since: 2.0.0
	  * @param orgId
	  * @return    
	 */
   public Integer findLatestAccountingId(int orgId){
	   return (Integer)queryForObject(sqlId("findLatestAccountingId"), orgId);
   }
   /**
	  *	<p>
	  *  根据门店获取空组
	  * </p>
	  * @since: 2.0.0
	  * @param storeId
	  * @return    
  */
   public List<AccountingDepartment> findEmptyOrgsByStoreId(int storeId){
	   return queryForList(sqlId("findEmptyOrgsByStoreId"), storeId);
   }
  /**
   *  <p>
   *   新增核算部门。
   *  </p>
   * @since: 2.0.0
   * @param orgId 组织编号
   * @param orgName 组织名称
   * @param startTime 开始时间
   * @param endTime 结束时间
   * @param accountingOrgName 核算组织名称
   * @param orgType 组织类型
   * @param manager 负责人名称
   * @param creator 创建人
   * @param storeTransfer 是否门店转区
   * @return    
  */
   @Transactional
   @ActiveMQTransactional
   public boolean saveAccountingDepartment(Date startTime,
		   Date endTime,String accountingOrgName,int orgId,String orgName,String orgType,
		   Integer manager,int creator,boolean storeTransfer){
       AccountingDepartment ad=findMaxStartTime(orgId);
       if (ad==null ) {
	    	 log.error("获取核算部门最大起始日期时出错，一般情况下是该组织不存在核算部门。orgId："+orgId);
			throw new IllegalArgumentException("获取核算部门最大起始日期时出错，orgId="+orgId);
		}
       //如果结束日期不为null,则将结束日期加一天，减去三秒，结果类似：yyyy-MM-dd 23:59:59:997
       //核算部门EndTime统一添加 时分秒 23：59：59：997
       if (endTime !=null) {
    	   	//将endTime day+1,ms减3毫秒
      		Calendar tc=Calendar.getInstance();
      		tc.setTime(endTime);
      		tc.add(Calendar.DAY_OF_MONTH, 1);
      		tc.add(Calendar.MILLISECOND, -3);
      		endTime=tc.getTime();
       }
       //校验时间段是否正确
       Date maxStartTime=ad.getStartTime();
       //验证日期是否符合规范
       if (isValidDateRange(orgId,startTime,endTime,maxStartTime)) {
    	   //最近一条endTime为null的记录ID
    	    Integer oldId=null;
	       	//如果日期晚于最晚的起始日期则需要更新前一阶段的结束日期
	       	if (startTime.after(maxStartTime)) {
	       		//查询最近一条记录
	       		//将截至时间减3毫秒
	       		Calendar calendar=Calendar.getInstance();
	       		calendar.setTime(startTime);
	       		calendar.add(Calendar.MILLISECOND, -3);
	       		 oldId=findLatestAccountingId(orgId);
				if (!updateEndTime(calendar.getTime(),oldId)) {
						//确保更新了一行,只能有一个ID对应部门的endTime=null
						throw new IllegalArgumentException("更新核算部门【"+orgId+"】截至时间有多个为空。");
					}
	       	}
	       	//新增核算部门
	       	int accountingId=insertAndReturnId(new AccountingDepartment(orgId,accountingOrgName,
	       			manager,startTime,endTime,creator));
	       	if (storeTransfer) {
	       		//门店转区新增的核算部门只需要同步对应关系
				accountingDeptSyncRelationSender.sendMessage(accountingId, accountingOrgName, orgId, orgName, orgType, oldId);
			}else{
				//发送新增MQ消息
				accountingDeptAddSender.sendMessage(accountingId, accountingOrgName, orgId, orgName, orgType, oldId);
			}
	       	return true;
		}else{
		 throw new IllegalArgumentException("添加核算部门出错，核算部门起止日期有误，请确认。orgId="+orgId);
	   }
   }
	/**
	 * @param department
	 * @return 更新核算部门的结束时间
	 */
	@Transactional
	public boolean updateEndTime(Date endTime,int id) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("endTime", endTime);
		params.put("id", id);
		return queryDao.update(sqlId("updateEndTime"),params)>0;
	}
	/**
	 * @param orgId
	 * @return 用于新增核算部门
	 */
	public AccountingDepartment findForAdd(int orgId)
	{
		return findById(sqlId("findForAdd"), orgId);
	}
	/**
	 * @param orgId
	 * @return
	 *  查询日期最大起始日期,如果核算部门起始时间小于最大起始时间，那么必须选择结束时间。
	 */
	public AccountingDepartment findMaxStartTime(int orgId) {
		return findByBean(sqlId("findMaxStartTime"), orgId);
	}   
	
	/**
	  * @since: 2.7.1
	  * @param orgId
	  * @param startTime
	  * @param endTime
	  * @return
	  * <p>
	  *  判断是否可以添加核算部门
	  * </p>   
	 */
	public boolean canAddAccountingDept(int orgId,Date startTime,Date endTime){
		Map<String,Object> params=new HashMap<>();
		params.put("orgId", orgId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return count(sqlId("canAddAccountingDept"), params) ==0;
	}
	/**
     * @param orgId 部门ID
     * @param startTime 结束时间
     * @param endTime 核算部门起始时间
     * @return 查找组织负责人 (核算部门)
     */
    public List<AccountingDepartment> findPrincipals(int orgId,Date startTime,Date endTime)
    {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("orgId", orgId);
    	params.put("startTime", startTime);
    	params.put("endTime", endTime);
    	return queryForList(sqlId("findPrincipals"), params);
    }
    /**
     * @param orgId
     * @return 查找组织负责人 (核算部门)
     */
    public List<AccountingDepartment> findPrincipals(int orgId)
    {
    	return findPrincipals(orgId,null,null);
    }
	/**
	 * @param pageStartTime
	 * @param pageEndTime
	 * @param minStartTime
	 * @param maxStartTime
	 * @return 验证核算部门的日期
	 */
	private boolean isValidDateRange(int orgId,Date pageStartTime,Date pageEndTime,Date maxStartTime)
	{
		//页面日期返回
		if (pageStartTime==null) {
			return false;
		}
		if (pageEndTime!=null && pageEndTime.before(pageStartTime)) {
			return false;
		}
		//根据orgId最大起始时间,如果起始时间小于最大起始时间，则需要pageEndTime
		if (pageStartTime.equals(maxStartTime) ||
				(pageStartTime.before(maxStartTime) && pageEndTime ==null)){
			return false;
		}
	  return this.canAddAccountingDept(orgId, pageStartTime, pageEndTime);	
	}

	/**
	 * @param customer
	 * @return 查询分页
	 */
	public Paginate findForPaginate(EASCustomer customer,int pageNo) {
		//分页 30条
		customer.setColumns("ec.id,de.id AS customer,de.orgName,ec.customerEASCode,ec.remark,ec.createAt");
		customer.setPageNo(pageNo);
		customer.setPageSize(30);
		customer.setTable("oms..v_accountingDepartment_all de  LEFT JOIN dbo.T_EAS_Customer ec ON de.id=ec.customer AND ec.customerEASCode is not null AND ec.customerEASCode !=''''");
	    customer.setOrderBy("ec.createAt desc");
	    
	    StringBuilder whereClause=new StringBuilder();
	    whereClause.append(" de.company = ''"+customer.getCompany()+"''");
	    if (customer.getOrgName() !=null && !customer.getOrgName().isEmpty()) {
			whereClause.append(" AND de.orgName like ''"+customer.getOrgName()+"%''");
		}
	    if (customer.getRemark()!=null && !customer.getRemark().isEmpty()) {
	    	whereClause.append(" AND ec.remark like ''"+customer.getRemark()+"%''");
		}
	    if (customer.getRelation() !=null && customer.getRelation()==1) {
			whereClause.append(" AND ec.id is null ");
		}
	    customer.setWhere(whereClause.toString());
	    Paginate paginate=new Paginate(pageNo, 30);
	    paginate.setPageList(accountDao.queryForList(sqlId("queryForPaginate"), customer));
	    paginate.setTotalCount(accountDao.count(sqlId("count"), customer));
		return paginate;
	}

	/**
	 * @param customer
	 * @return 根据客户编号查找信息
	 */
	public EASCustomer findByCustomer(String customer,String company) {
		 Map<String,Object> params=new HashMap<String, Object>();
		 params.put("customer", customer);
		 params.put("company", company);
		return accountDao.queryForBean(sqlId("findByCustomer"), params);
	}
	
	/**
	 * @param remark
	 * @return 
	 */
	public List<EASCustomer> findEASCustomers(String remark)
	{
		return easDao.queryForList(sqlId("findEASCustomers"), remark);
	}

	/**
	 * @param customer
	 * @return 保存客户对应关系
	 */
	@Transactional
	public boolean saveCustomerRelation(EASCustomer customer) {
		if (customer==null) {
			return false;
		}
		String remark=customer.getRemark();
		if (remark==null || remark.isEmpty()) {
			throw new IllegalArgumentException("请选择金蝶客户名称。");
		}
		String customerEasCode=customer.getCustomerEASCode();
		if (customerEasCode==null || customerEasCode.isEmpty()) {
			throw new IllegalArgumentException("请选择金蝶客户名称。");
		}		
		if (customer.getId()==null) {
			//新增
			accountDao.insert(sqlId("insertRelation"), customer);
		}else
		{
			accountDao.update(sqlId("updateRelation"), customer);
		}
		//新增日志
		accountDao.insert(sqlId("insertLog"), customer);
		return true;
	}
	
  /**
 * @param code
 * @param company
 * @return 根据金蝶编码查询
 */
  public List<EASCustomer> findByEASCustomerCode(String code,String company){
	   Map<String,Object> params=new HashMap<String, Object>();
	   params.put("customerEASCode", code);
	   params.put("company", company);
	  return accountDao.queryForList(sqlId("findByEASCustomerCode"),params);
  }

    /**
     * 根据组织Id查找最新的一条核算部门信息
     * @param orgId
     * @return
     */
    public AccountingDepartment findLatestOrgId(int orgId) {
        return findById(sqlId("findLatestOrgId"), orgId);
    }
}

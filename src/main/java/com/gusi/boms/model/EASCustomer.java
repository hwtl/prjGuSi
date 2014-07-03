package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

import com.dooioo.web.model.BasePageEntity;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-6-17 下午12:04:50 ) 
 * 金蝶客户对应关系
 */
public class EASCustomer extends BasePageEntity implements Serializable{
	private static final long serialVersionUID = 2642439973344367185L;
	private String company;//所属公司
	private Integer id;//对应关系ID
	private String orgName;//部门名称
	private String customer;
	private String customerEASCode;
	private Date createAt;
	private String remark;//金蝶编码分行备注
	private Integer relation;//是否有对应关系，如果！=null && =1，则为所有
	private int operator;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCustomerEASCode() {
		return customerEASCode;
	}
	public void setCustomerEASCode(String customerEASCode) {
		this.customerEASCode = customerEASCode;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "[公司：" + company + ",核算部门名称："
				+ orgName + ",核算部门编号：" + customer + ",金蝶客户编号："
				+ customerEASCode + ",金蝶客户名称："
				+ remark + "]";
	}
	

}

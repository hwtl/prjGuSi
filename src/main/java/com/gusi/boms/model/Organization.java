package com.gusi.boms.model;

import com.gusi.boms.util.HistoryInfoUtils;
import com.dooioo.plus.oms.dyEnums.OrganizationStatus;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.util.DyDate;
import com.dooioo.web.model.BasePageEntity;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: Organization
 * @Description: 组织部门实体类
 * @author fdj
 * @date 2013-4-9 上午9:52:23
 */
public class Organization extends BasePageEntity {
	private static final long serialVersionUID = -5630679406897897896L;

    public final static int STATUS_ALL = -9;	//全选
    public final static int STATUS_CLOSE = -1;	//停用
    public final static int STATUS_TEMP=2;     // 临时组织
    public final static int STATUS_STOP = 0;	//暂停
    public final static int STATUS_NORMAL = 1;	//正常
    public final static int MANAGER_NULL = 1;	//没有负责人
    public static final int SalesOrgId = 20495;  //营运中心id
    public final static int TEMP_PARENTEID = 999999999;
    public static final String ORG_TYPE_FH = "分行";
    public static final String ORG_TYPE_AREA="区域";
    public static final String ORG_TYPE_STORE="门店";
    public static final String ORG_TYPE_DEPT="部门";

    private int id;                 // 部门id
	private int parentId;           // 上级部门id
	private String orgCode;         // 编号Code
	private String orgLongCode;     // 组织部门的LongCode
	private String orgName;         // 名称
	private Integer orgLevel;       // 等级
	private String orgType;         // 类型
	private int status = STATUS_ALL;// 状态 (exa:0:暂停;  1:正常;  -1:停用)
	private Long locIndex;          // 位置编号，用于排序
	private String address;         // 地址
	private String orgPhone;        // 电话
	private String orgFax;          // 传真
	private Date updateTime;        // 更新时间
	private Integer updator;        // 更新人
	private Date createTime;        // 创建时间
	private Integer creator;        // 创建人
	private Date openDate;          // 开组时间
	private Date closedDate;        // 关组时间
	private String eOrgName;        // 组织英文名称
	private Integer manager;        // 用来存储主要负责人工号，默认为NULL
	private String orgClass;        // 标示部门前/后台关系
	private String eAddress;        // 组织英文地址
	private String company;         // 公司
	private String remark; 	        //组织描述



    public String getStatusStr(){
        switch (status){
            case STATUS_CLOSE : return OrganizationStatus.Disabled.toCHSString();
            case STATUS_STOP: return OrganizationStatus.Pause.toCHSString();
            case STATUS_NORMAL: return OrganizationStatus.Normal.toCHSString();
            case STATUS_TEMP: return OrganizationStatus.Temporary.toCHSString();
        }
        return "未知";
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgLongCode() {
		return orgLongCode;
	}

	public void setOrgLongCode(String orgLongCode) {
		this.orgLongCode = orgLongCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getLocIndex() {
		return locIndex;
	}

	public void setLocIndex(Long locIndex) {
		this.locIndex = locIndex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgFax() {
		return orgFax;
	}

	public void setOrgFax(String orgFax) {
		this.orgFax = orgFax;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdator() {
		return updator;
	}

	public void setUpdator(Integer updator) {
		this.updator = updator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String geteOrgName() {
		return eOrgName;
	}

	public void seteOrgName(String eOrgName) {
		this.eOrgName = eOrgName;
	}

	public Integer getManager() {
		return manager;
	}

	public void setManager(Integer manager) {
		this.manager = manager;
	}

	public String getOrgClass() {
		return orgClass;
	}

	public void setOrgClass(String orgClass) {
		this.orgClass = orgClass;
	}

	public String geteAddress() {
		return eAddress;
	}

	public void seteAddress(String eAddress) {
		this.eAddress = eAddress;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean hasSonTree(List<Organization> orgList,int addLeavel){
    	for(Organization sonOrg : orgList){
    		if(sonOrg.isSonOf(this, addLeavel))
    			return true;
    	}
    	return false;
    }
    
    public boolean isSonOf(Organization parentOrg, int addLeavel){
        /*if( orgType.equals("分行") && !orgName.contains("代理")){
            addLeavel = 2;
        }*/
        if(parentOrg.getOrgName().contains("代理") || parentOrg.getOrgType() == null || !parentOrg.getOrgType().equals("区域")){
            addLeavel = 1;
        }

    	if(orgLongCode.contains(parentOrg.getOrgLongCode() + "/") && orgLevel == (parentOrg.getOrgLevel() + addLeavel))
    		return true;
    	return false;
    }
    
	
	/**
	 * 获得组织变更记录字符串
	 * @param that
	 * @return
	 */
	public String getOperateRemark(Organization that){
	       StringBuilder record = new StringBuilder();
	       if (parentId >= 0 ? !(parentId == that.parentId) : that.parentId >= 0){
	    	   record.append(HistoryInfoUtils.getChangeStr("上级组织", HistoryInfoUtils.getRemarkStr(String.valueOf(parentId), String.valueOf(that.parentId))));
	       }
	       if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织名称", HistoryInfoUtils.getRemarkStr(orgName, that.orgName)));
	       }
	       if (eOrgName != null ? !eOrgName.equals(that.eOrgName) : that.eOrgName != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织英文名称", HistoryInfoUtils.getRemarkStr(eOrgName, that.eOrgName)));
	       }
	       if (orgType != null ? !orgType.equals(that.orgType) : that.orgType != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织类别", HistoryInfoUtils.getRemarkStr(orgType, that.orgType)));
	       }
	       if (orgClass != null ? !orgClass.equals(that.orgClass) : that.orgClass != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织属性", HistoryInfoUtils.getRemarkStr(orgClass, that.orgClass)));
	       }
	       if (manager != null ? !manager.equals(that.manager) : that.manager != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织负责人", HistoryInfoUtils.getRemarkStr(String.valueOf(manager), String.valueOf(that.manager))));
	       }
	       if (remark != null ? !remark.equals(that.remark) : that.remark != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织描述", HistoryInfoUtils.getRemarkStr(remark, that.remark)));
	       }
	       if (address != null ? !address.equals(that.address) : that.address != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("组织办公地点", HistoryInfoUtils.getRemarkStr(address, that.address)));
	       }
           if (orgFax != null ? !orgFax.equals(that.orgFax) : that.orgFax != null){
               record.append(HistoryInfoUtils.getChangeStr("组织传真", HistoryInfoUtils.getRemarkStr(orgFax, that.orgFax)));
           }
	       if (openDate != null ? !openDate.equals(that.openDate) : that.openDate != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("开组时间", HistoryInfoUtils.getRemarkStr(DyDate.format(openDate, "yyyy-MM-dd"), DyDate.format(that.openDate, "yyyy-MM-dd"))));
	       }
	       if (closedDate != null ? !closedDate.equals(that.closedDate) : that.closedDate != null){
	    	   record.append(HistoryInfoUtils.getChangeStr("关组时间", HistoryInfoUtils.getRemarkStr(DyDate.format(closedDate, "yyyy-MM-dd"), DyDate.format(that.closedDate, "yyyy-MM-dd"))));
	       }
	       return StringBuilderHelper.trimLast(record, "<br/>").toString();
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", parentId=" + parentId
				+ ", orgCode=" + orgCode + ", orgLongCode=" + orgLongCode
				+ ", orgName=" + orgName + ", orgLevel=" + orgLevel
				+ ", orgType=" + orgType + ", status=" + status + ", locIndex="
				+ locIndex + ", address=" + address + ", orgPhone=" + orgPhone
				+ ", orgFax=" + orgFax + ", updateTime=" + updateTime
				+ ", updator=" + updator + ", createTime=" + createTime
				+ ", creator=" + creator + ", openDate=" + openDate
				+ ", closedDate=" + closedDate + ", eOrgName=" + eOrgName
				+ ", manager=" + manager + ", orgClass=" + orgClass
				+ ", eAddress=" + eAddress + ", company=" + company
				+ ", remark=" + remark + "]";
	}

}
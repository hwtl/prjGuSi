package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

import java.util.Date;

/**
 * 
* @ClassName: OrganizationOperateHistory 
* @Description: 组织部门操作记录实体类
* @author fdj
* @date 2013-4-10 下午2:22:05
 */
public class OrganizationOperateHistory extends BaseEntity {
	private static final long serialVersionUID = 4404434372953859786L;

	private int id; //id

    private int orgId; //被操作的组织id

    private String filed; //标示是 信息变更，状态变更等类型

    private Date createTime; //创建时间

    private Integer creator; //创建人工号

    private String createName; //创建人姓名

    private String remark; //一次编辑行为只产生一条操作记录 （记录的信息格式，如	状态： 正常 => 暂停 <Br> 组织名称：枫景C组 => 静安A组）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Override
	public String toString() {
		return "OrganizationOperateHistory [id=" + id + ", orgId=" + orgId
				+ ", filed=" + filed + ", createTime=" + createTime
				+ ", creator=" + creator + ", createName=" + createName
				+ ", remark=" + remark + "]";
	}
}
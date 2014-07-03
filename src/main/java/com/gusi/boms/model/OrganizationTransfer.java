package com.gusi.boms.model;

import com.dooioo.web.model.BasePageEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-23 上午9:55
 * @Description: 整组转调实体
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationTransfer extends BasePageEntity {
    private static final long serialVersionUID = -1723980864434184752L;
	/**
	 * 删除
	 */
	public static final int TRANSFER_TYPE_DELETE = -1;
    /**
     * 临时未生效
     */
    public static final int TRANSFER_TYPE_TEMP = 0;
    /**
     * 生效
     */
    public static final int TRANSFER_TYPE_OK = 1;

    /**
     * 单组转调
     */
    public static final int MODE_SINGLE = 1;
    /**
     * 两组对调
     */
    public static final int MODE_DOUBLE = 2;
    /**
     * 门店转区
     */
    public static final int MODE_DEPT_TRANSFER=3;

    private int id;             //id
    private int transferMode;   //转调类型  1:单组转调    2：整组转调,3,门店转区
    private int orgAId;
    private int orgAParentId;       //
    private String orgAName;
    private Integer orgBId;       //
    private int orgBParentId;       //
    private String orgBName;
    private Date activeDate; //生效时间
    private int status;         //状态      -1：无效     0：未生效       1：已生效
    private Date createTime;    //创建时间
    private int creator;        //创建人
    private Date updateTime;    //创建时间
    private Integer updator;        //创建人
    private String orgAIds;//门店转区时候，多个门店的ID
    
    
    
    public OrganizationTransfer() {
		super();
	}

	public OrganizationTransfer(int transferMode, int orgAId, String orgAName,
			int orgAParentId, Integer orgBId, String orgBName,
			int orgBParentId, Date activeDate, int status, int creator) {
		super();
		this.transferMode = transferMode;
		this.orgAId = orgAId;
		this.orgAName = orgAName;
		this.orgAParentId = orgAParentId;
		this.orgBId = orgBId;
		this.orgBName = orgBName;
		this.orgBParentId = orgBParentId;
		this.activeDate = activeDate;
		this.status = status;
		this.creator = creator;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(int transferMode) {
        this.transferMode = transferMode;
    }

    public String getOrgAIds() {
		return orgAIds;
	}

	public void setOrgAIds(String orgAIds) {
		this.orgAIds = orgAIds;
	}

	public int getOrgAId() {
        return orgAId;
    }

    public void setOrgAId(int orgAId) {
        this.orgAId = orgAId;
    }

    public int getOrgAParentId() {
        return orgAParentId;
    }

    public void setOrgAParentId(int orgAParentId) {
        this.orgAParentId = orgAParentId;
    }

    public String getOrgAName() {
        return orgAName;
    }

    public void setOrgAName(String orgAName) {
        this.orgAName = orgAName;
    }

    public Integer getOrgBId() {
        return orgBId;
    }

    public void setOrgBId(Integer orgBId) {
        this.orgBId = orgBId;
    }

    public int getOrgBParentId() {
        return orgBParentId;
    }

    public void setOrgBParentId(int orgBParentId) {
        this.orgBParentId = orgBParentId;
    }

    public String getOrgBName() {
        return orgBName;
    }

    public void setOrgBName(String orgBName) {
        this.orgBName = orgBName;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
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

	@Override
	public String toString() {
		return "OrganizationTransfer [id=" + id + ", transferMode="
				+ transferMode + ", orgAId=" + orgAId + ", orgAParentId="
				+ orgAParentId + ", orgAName=" + orgAName + ", orgBId="
				+ orgBId + ", orgBParentId=" + orgBParentId + ", orgBName="
				+ orgBName + ", activeDate=" + activeDate + ", status="
				+ status + ", createTime=" + createTime + ", creator="
				+ creator + ", updateTime=" + updateTime + ", updator="
				+ updator + ", orgAIds=" + orgAIds + "]";
	}

  
}

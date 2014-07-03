package com.gusi.boms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限 - 角色
 * @author Jail    E -Mail:86455@dooioo.com
 */
public class Role implements Serializable{
	private static final long serialVersionUID = -8569832049345816318L;
	private int id;   //主键ID
    private String roleName; //角色名称
    private String roleDesc; //角色备注
    private int status; //角色状态
    private Date createTime;  //创建时间
    private Integer creator;  //创建人工号
    private int appId;  //角色所属系统ID

    
    private Boolean canEdit = true; //为应用提供的字段： 是否可编辑
    private Boolean checked = false; //为应用提供的字段： 是否选中
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
	public Boolean getCanEdit() {
		return canEdit;
	}
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleDesc="
				+ roleDesc + ", status=" + status + ", createTime="
				+ createTime + ", creator=" + creator + ", appId=" + appId
				+ ", canEdit=" + canEdit + ", checked=" + checked + "]";
	}
}
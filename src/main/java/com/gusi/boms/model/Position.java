package com.gusi.boms.model;

import com.dooioo.web.model.BasePageEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu(2013-04-10 9:09)
 * Description: 职位信息实体
 */
public class Position extends BasePageEntity {
    private static final long serialVersionUID = -891812202153404055L;
   
    public final static int AREA_MANAGER_ID=6;		//区域总监
    public final static int BROKER_POSITION_ID = 1; //置业顾问
    public final static int BRANCH_MANAGER_ID = 2; 	//分行经理
    public final static int BRANCH_ASSISTANT_ID = 3;//分行助理
    public final static int AREA_ASSISTANT_ID = 4; 	//区域助理

    public final static int STATUS_STOP = 0;	//停用
    public final static int STATUS_NORMAL = 1;	//正常
    
    private int id;                 //主键id
    private String positionName;    //职位名称
    private String ePositionName;   //职位对应的英文名称
    private int titleId;            //职等id
    private String positionType;    //职位类型
    private String positionDesc;    //职位描述
    private int status;        		//职位的状态  1、正常        0、停用
    private int employeeCount; 		//该岗位员工数量
    private Date createTime;        //创建时间
    private int creator;            //创建人工号
    private Integer updator;
    private Date updateTime;

    public String getStatusStr() {
        switch (this.status) {
            case STATUS_STOP : return "停用";
            case STATUS_NORMAL : return "正常";
        }
        return "未知";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
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

    public String getePositionName() {
        return ePositionName;
    }

    public void setePositionName(String ePositionName) {
        this.ePositionName = ePositionName;
    }

    public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", positionName='" + positionName + '\'' +
                ", ePositionName='" + ePositionName + '\'' +
                ", titleId=" + titleId +
                ", positionType='" + positionType + '\'' +
                ", positionDesc='" + positionDesc + '\'' +
                ", status=" + status +
                ", employeeCount=" + employeeCount +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updator=" + updator +
                ", updateTime=" + updateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (id != position.id) return false;
        if (positionName != null ? !positionName.equals(position.positionName) : position.positionName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (positionName != null ? positionName.hashCode() : 0);
        return result;
    }
}
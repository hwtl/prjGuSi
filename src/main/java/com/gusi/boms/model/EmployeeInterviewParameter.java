package com.gusi.boms.model;

import java.io.Serializable;
import java.util.List;

/** 
  *	author:liuhui
  *	离职面谈参数
 */
public class EmployeeInterviewParameter  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4617719613210761832L;
	/**
	 *  辞退参数
	 */
	public static final int TYPE_CITUI=3;
	/**
	 * 离职参数
	 */
	public static final int TYPE_LEAVE=1;
	/**
	 * 面谈参数
	 */
	public static final int TYPE_INTERVIEW=2;
	
	/**
	 * 黑名单参数
	 */
	public static final int TYPE_BLACK=4;
	
	private Integer id;
	/**
	 * 父类ID
	 */
	private Integer parentId;
	/**
	 * 文本
	 */
	private String parameterValue;
	/**
	 * 1,是主动离职和面谈的离职参数，2，是辞退离职原因
	 */
	private int parameterType;
	/**
	 * 父类文本
	 */
	private String parentValue;
	private int status;
	private int other;
	private int order;
	private List<EmployeeInterviewParameter> subs;
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }


	public void setId(Integer id) {
		this.id = id;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public int getParameterType() {
		return parameterType;
	}

	public void setParameterType(int parameterType) {
		this.parameterType = parameterType;
	}

	public String getParentValue() {
		return parentValue;
	}

	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}

	public List<EmployeeInterviewParameter> getSubs() {
		return subs;
	}

	public void setSubs(List<EmployeeInterviewParameter> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "EmployeeInterviewParameter [id=" + id + ", parentId="
				+ parentId + ", parameterValue=" + parameterValue
				+ ", parameterType=" + parameterType + ", parentValue="
				+ parentValue + ", status=" + status + ", other=" + other
				+ ", order=" + order + "]";
	}
    
}
package com.gusi.boms.model;

import java.io.Serializable;

/** 
  *	author:liuhui
  *	createTime: liuhui (2013-4-10 上午08:55:04 ) 
 */
public class Parameter implements Serializable {
	private static final long serialVersionUID = -4762658076916613574L;
    /**
     * 学历
     */
    public static final String DEGREE_KEY = "1002";
    /**
     * 学习类型
     */
    public static final String STUDY_TYPE_KEY = "10021";
    /**
     * 星座
     */
    public static final String CONSTELLATION_KEY="1003";
    /**
     * 民族
     */
    public static final String NATION_KEY = "1004";
    /**
     * 黑名单
     */
    public static final String BLACK_KEY = "1001";
    
    /**
     *  血型
     */
    public static final String BLOOD_TYPE_KEY="1007";
    
    /**
     *  婚姻状态
     */
    public static final String MARRIAGE_KEY="1008";
    
    /**
     *  省份
     */
    public static final String PROVINCE_KEY="1011";
    
    /**
     *  政治背景
     */
    public static final String POLITICAL_KEY="1006";

    /**
     * 人员标签
     */
    public static final String TAG_KEY = "140520";
    public static final String LEAVE_TYPE_KEY = "1009";  //离职类型
    public static final String LEAVE_REASON_KEY = "1010";  //离职原因

	private int id;
    private String typeKey;
    private String typeDesc;
    private String optionCode;
    private String optionValue;
    private Byte status;
    private int sort;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTypeKey() {
        return typeKey;
    }
    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }
   
    public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getOptionCode() {
        return optionCode;
    }
    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "Parameter [id=" + id + ", typeKey=" + typeKey + ", typeDesc="
				+ typeDesc + ", optionCode=" + optionCode + ", optionValue="
				+ optionValue + ", status=" + status + ", sort=" + sort + "]";
	}

	

   
}
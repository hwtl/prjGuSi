/**
 * 
 */
package com.gusi.boms.model;

import java.util.Date;

/**
 * @author Alex Yu
 * @Created 2014年6月5日下午3:32:00
 */
public class BaseModel {

	private int creator;
	private Date createTime;
	private int updator;
	private Date updateTime;

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getUpdator() {
		return updator;
	}

	public void setUpdator(int updator) {
		this.updator = updator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

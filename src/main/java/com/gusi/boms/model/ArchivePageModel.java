package com.gusi.boms.model;

import java.io.Serializable;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-5-28 上午10:51:45 ) 
 *  档案编辑页面model
 */
public class ArchivePageModel implements Serializable {
	private static final long serialVersionUID = 5744919739232462896L;
	private EmployeeFamily[] families;
	private EmployeeWorkExper[] works;
	private EmployeeTrain[] trains;
	private EmployeeEducationExper[] educations;
	
	public EmployeeFamily[] getFamilies() {
		return families;
	}
	public void setFamilies(EmployeeFamily[] families) {
		this.families = families;
	}
	public EmployeeWorkExper[] getWorks() {
		return works;
	}
	public void setWorks(EmployeeWorkExper[] works) {
		this.works = works;
	}
	public EmployeeTrain[] getTrains() {
		return trains;
	}
	public void setTrains(EmployeeTrain[] trains) {
		this.trains = trains;
	}
	public EmployeeEducationExper[] getEducations() {
		return educations;
	}
	public void setEducations(EmployeeEducationExper[] educations) {
		this.educations = educations;
	}
	
	
}

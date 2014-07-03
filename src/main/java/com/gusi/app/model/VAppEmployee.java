package com.gusi.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author "liuhui" 
 * @since 3.0.5
 * @createAt 2014-4-25 上午10:31:19
 * <p>
 *  AppEmployee 视图
 * </p>
 * Copyright (c) 2014, Dooioo All Rights Reserved. 
 */
public class VAppEmployee extends AppEmployee implements Serializable {
	    private static final long serialVersionUID = -4262873964100714570L;
	    private String orgAddress;
	    private String email;
	    private Date joinDate;
	    private Date latestJoinDate;
	    private String userTitle;
	    private String alternatePhone;
	    private String showedPhone;
	    private String orgClass;
	    private String status;
	    private List<String> supportEmployeePhotos;
		
	    public void setSupportEmployeePhotos(List<String> supportEmployeePhotos) {
			this.supportEmployeePhotos = supportEmployeePhotos;
		}

		public List<String> getSupportEmployeePhotos() {
	    	if (this.getUserCode()!=0) {
	    		List<String> phothos=new ArrayList<>();
	    		phothos.add("/photos/"+this.getUserCode()+"_150x200.jpg");
	    		phothos.add("/photos/"+this.getUserCode()+"_60x80.jpg");
	    		return phothos;
			}
	    	return null;
		}
		public String getOrgAddress() {
			return orgAddress;
		}
		public void setOrgAddress(String orgAddress) {
			this.orgAddress = orgAddress;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Date getJoinDate() {
			return joinDate;
		}
		public void setJoinDate(Date joinDate) {
			this.joinDate = joinDate;
		}
		
		public Date getLatestJoinDate() {
			return latestJoinDate;
		}

		public void setLatestJoinDate(Date latestJoinDate) {
			this.latestJoinDate = latestJoinDate;
		}

		public String getUserTitle() {
			return userTitle;
		}
		public void setUserTitle(String userTitle) {
			this.userTitle = userTitle;
		}
		public String getAlternatePhone() {
			return alternatePhone;
		}
		public void setAlternatePhone(String alternatePhone) {
			this.alternatePhone = alternatePhone;
		}
		public String getShowedPhone() {
			return showedPhone;
		}
		public void setShowedPhone(String showedPhone) {
			this.showedPhone = showedPhone;
		}
		
		public String getOrgClass() {
			return orgClass;
		}

		public void setOrgClass(String orgClass) {
			this.orgClass = orgClass;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "VAppEmployee [orgAddress="
					+ orgAddress + ", email=" + email 
					+ ", joinDate=" + joinDate + ", latestJoinDate=" + latestJoinDate
					+ ", userTitle=" + userTitle + ", alternatePhone="
					+ alternatePhone + ", showedPhone=" + showedPhone
					+"supportEmployeePhotos:"+supportEmployeePhotos
					+ ", toString()=" + super.toString() + "]";
		}
	    
	    
	    
}



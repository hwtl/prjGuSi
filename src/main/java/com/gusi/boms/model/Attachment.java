package com.gusi.boms.model;

import com.gusi.boms.helper.AttachHelper;
import com.dooioo.web.model.BaseEntity;

import java.util.Date;
/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.controller
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-09 15:02)
 * 员工附件实体类
 */
public class Attachment extends BaseEntity{

    public static final String ORG_PHOTO = "1"; //部门照片
    public static final String EMPLOYEE_CREDENTIALS_NO = "2"; //员工身份证
    public static final String GRADUATION_CERTIFICATE_TYPE="3";//毕业证
    public static final String TEMP_RESIDENCE_PERMIT_TYPE="4";//临时居住证
    public static final String LONG_TERM_RESIDENCE_PERMIT_TYPE="5";//长期居住证
    private static final long serialVersionUID = 8962935005921868489L;

    private int id;

    private int userCode;

    private String attachType;

    private String attachFilename;

    private String attachPath;

    private Date createTime;

    private Integer creator;

    private String creatorName;

    private Integer valid;
    
    private String attachPreviewUrl;
    
    public String getAttachPreviewUrl() {
		return attachPreviewUrl;
	}
	public void setAttachPreviewUrl(String attachPreviewUrl) {
		this.attachPreviewUrl = attachPreviewUrl;
	}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    public String getAttachFilename() {
        return attachFilename;
    }

    public void setAttachFilename(String attachFilename) {
        this.attachFilename = attachFilename;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getSmallAttachPath() {
        return AttachHelper.getSmallImage(this.getAttachPath());
    }

    public Attachment(int userCode, String attachType, Integer creator, String creatorName) {
        this.userCode = userCode;
        this.attachType = attachType;
        this.creator = creator;
        this.creatorName = creatorName;
    }

    public Attachment() {
    }
}
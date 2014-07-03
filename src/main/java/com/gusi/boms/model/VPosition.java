package com.gusi.boms.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-06-09 14:29)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class VPosition extends Position {

    private String titleIds;

    private int titleDegree; 		//职等
    private String titleName;       //职等名称(视图)
    private String serialName;      //职系名称(视图)
    private int serialId;           //职系id(视图)
    private int employeeNo;
    private int partTimeNo;

    private String serialTitleInfo;

    public int getPartTimeNo() {
        return partTimeNo;
    }

    public void setPartTimeNo(int partTimeNo) {
        this.partTimeNo = partTimeNo;
    }

    public int getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(int employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getSerialTitleInfo() {
        if( !StringUtils.isBlank(this.serialTitleInfo)) {
            StringBuilder sb = new StringBuilder(serialTitleInfo.substring(1));
            sb.append(")");
            return sb.toString().replace("+", "<br/>");
        }
        return "";
    }

    public void setSerialTitleInfo(String serialTitleInfo) {
        this.serialTitleInfo = serialTitleInfo;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public int getTitleDegree() {
        return titleDegree;
    }

    public void setTitleDegree(int titleDegree) {
        this.titleDegree = titleDegree;
    }

    public String getTitleIds() {
        return titleIds;
    }

    public void setTitleIds(String titleIds) {
        this.titleIds = titleIds;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Override
    public String toString() {
        return "VPosition{" +
                "titleIds='" + titleIds + '\'' +
                ", titleDegree=" + titleDegree +
                ", titleName='" + titleName + '\'' +
                ", serialName='" + serialName + '\'' +
                ", serialId=" + serialId +
                ", employeeNo=" + employeeNo +
                ", partTimeNo=" + partTimeNo +
                ", serialTitleInfo='" + serialTitleInfo + '\'' +
                '}';
    }


}

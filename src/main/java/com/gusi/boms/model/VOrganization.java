package com.gusi.boms.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-8-22 下午5:05
 * @update: 2014-05-12 10:31:54
 * @Description: 组织架构视图
 * To change this template use File | Settings | File Templates.
 */
public class VOrganization extends Organization {
    private static final long serialVersionUID = 3553880138180245031L;
	private String managerName;     //负责人名字
    private String parentName;      //上级组织名称
    private String areaName;        //区域名称
    private int employeeNo;         //下面的员工数（正式，试用期）
    private int partTimeNo;         //下面的员工数（正式，试用期）
    private Integer maxCount;           //下面的员工限制数
    private Date maxCountEndTime;       //编制到期时间
    private int branchRequireEmpNo;     //每个分行规定的人数


    private int allFormalNo;
    private int allRegisterNo;
    private int branchNo;
    private int qzNo;
    private int fdNo;
    private int gzfdNo;

    /**
     * 获得编制人数
     * @since: 2014-06-09 13:47:29
     * @return
     */
    public int getBianZhiNo() {
        return branchNo * branchRequireEmpNo + qzNo + fdNo + gzfdNo;
    }

    /**
     * 获取可入职人数
     * @since: 2014-06-09 16:06:25
     * @return
     */
    public int getKeRuZhiNo() {
        /*int keRuZhiNo = branchNo * 10 + qzNo + fdNo + gzfdNo - allFormalNo - allRegisterNo;
        return keRuZhiNo < 0 ? 0 : keRuZhiNo;*/
        return branchNo * branchRequireEmpNo + qzNo + fdNo + gzfdNo - allFormalNo - allRegisterNo;
    }

    /**
     * 是否中介部
     * @since: 2014-06-09 16:08:19
     * @return
     */
    public boolean isZhongJieBu() {
        return getOrgLongCode().startsWith("12020001/120720495/12020003");
    }

    /**
     * 是否新房销售
     * @since: 2014-06-09 16:08:33
     * @return
     */
    public boolean isXinFang() {
        return getOrgLongCode().startsWith("12020001/120720495/140207133236/140207133359");
    }


    public int getBranchRequireEmpNo() {
        return branchRequireEmpNo;
    }

    public void setBranchRequireEmpNo(int branchRequireEmpNo) {
        this.branchRequireEmpNo = branchRequireEmpNo;
    }

    public int getAllFormalNo() {
        return allFormalNo;
    }

    public void setAllFormalNo(int allFormalNo) {
        this.allFormalNo = allFormalNo;
    }

    public int getAllRegisterNo() {
        return allRegisterNo;
    }

    public void setAllRegisterNo(int allRegisterNo) {
        this.allRegisterNo = allRegisterNo;
    }

    public int getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(int branchNo) {
        this.branchNo = branchNo;
    }

    public int getQzNo() {
        return qzNo;
    }

    public void setQzNo(int qzNo) {
        this.qzNo = qzNo;
    }

    public int getFdNo() {
        return fdNo;
    }

    public void setFdNo(int fdNo) {
        this.fdNo = fdNo;
    }

    public int getGzfdNo() {
        return gzfdNo;
    }

    public void setGzfdNo(int gzfdNo) {
        this.gzfdNo = gzfdNo;
    }

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

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Date getMaxCountEndTime() {
        return maxCountEndTime;
    }

    public void setMaxCountEndTime(Date maxCountEndTime) {
        this.maxCountEndTime = maxCountEndTime;
    }

    @Override
    public String toString() {
        return "VOrganization{" +
                "managerName='" + managerName + '\'' +
                ", parentName='" + parentName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", employeeNo=" + employeeNo +
                ", partTimeNo=" + partTimeNo +
                ", maxCount=" + maxCount +
                ", maxCountEndTime=" + maxCountEndTime +
                '}';
    }
}

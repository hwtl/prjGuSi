package com.gusi.boms.model;

import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.web.model.BaseEntity;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-18 11:25)
 * Description:员工高级搜索的条件实体
 */
public class EmployeeSearch extends BaseEntity {
    private static final long serialVersionUID = 8958627942941955688L;
    private String orgIds;              //组织ids
    private String positionIds;         //岗位ids
    private int black;               //黑名单
    private String joinDateStart;              //入职日期 起始日
    private String joinDateEnd;              //入职日期  截止日
    private String leaveDateStart;             //离职日期 起始日
    private String leaveDateEnd;             //离职日期 截止日
    private Integer officialYear;           //身份证出生年
    private Integer officialMonth;          //身份证出生月
    private Integer officialDay;            //身份证出生日
    private int bornYear;               //生日年
    private int bornMonth;              //生日月
    private int bornDay;                //生日日
    private Date birthDateFrom; //生日日期起始
    private Date birthDateTo;   //生日日期结束
    private String comeFrom;            //招聘渠道
    private String constellation;       //星座
    private String lastDegree;          //学历
    private String keyWords;            //关键字
    private int advancedSearch;         //高级搜索
    private String employeeStatus;          //状态列表
    private String excelHeads;           //导出的title字段
    private String exportKey;            //导出的key
    private String company = Company.Dooioo.toString();            //公司
    private String archiveStatus;  //档案类型

    private String tags;    //员工标签

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(String archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public Date getBirthDateFrom() {
		return birthDateFrom;
	}

	public void setBirthDateFrom(Date birthDateFrom) {
		this.birthDateFrom = birthDateFrom;
	}

	public Date getBirthDateTo() {
		return birthDateTo;
	}

	public void setBirthDateTo(Date birthDateTo) {
		this.birthDateTo = birthDateTo;
	}

	public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }


    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

	public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public String getJoinDateStart() {
        return joinDateStart;
    }

    public void setJoinDateStart(String joinDateStart) {
        this.joinDateStart = joinDateStart;
    }

    public String getJoinDateEnd() {
        return joinDateEnd;
    }

    public void setJoinDateEnd(String joinDateEnd) {
        this.joinDateEnd = joinDateEnd;
    }

    public String getLeaveDateStart() {
        return leaveDateStart;
    }

    public void setLeaveDateStart(String leaveDateStart) {
        this.leaveDateStart = leaveDateStart;
    }

    public String getLeaveDateEnd() {
        return leaveDateEnd;
    }

    public void setLeaveDateEnd(String leaveDateEnd) {
        this.leaveDateEnd = leaveDateEnd;
    }

    public Integer getOfficialYear() {
        return officialYear;
    }

    public void setOfficialYear(Integer officialYear) {
        this.officialYear = officialYear;
    }

    public Integer getOfficialMonth() {
        return officialMonth;
    }

    public void setOfficialMonth(Integer officialMonth) {
        this.officialMonth = officialMonth;
    }

    public Integer getOfficialDay() {
        return officialDay;
    }

    public void setOfficialDay(Integer officialDay) {
        this.officialDay = officialDay;
    }

    public int getBornYear() {
        return bornYear;
    }

    public void setBornYear(int bornYear) {
        this.bornYear = bornYear;
    }

    public int getBornMonth() {
        return bornMonth;
    }

    public void setBornMonth(int bornMonth) {
        this.bornMonth = bornMonth;
    }

    public int getBornDay() {
        return bornDay;
    }

    public void setBornDay(int bornDay) {
        this.bornDay = bornDay;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getLastDegree() {
        return lastDegree;
    }

    public void setLastDegree(String lastDegree) {
        this.lastDegree = lastDegree;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(int advancedSearch) {
        this.advancedSearch = advancedSearch;
    }


    public String getExcelHeads() {
        return excelHeads;
    }

    public void setExcelHeads(String excelHeads) {
        this.excelHeads = excelHeads;
    }

    public String getExportKey() {
        return exportKey;
    }

    public void setExportKey(String exportKey) {
        this.exportKey = exportKey;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 根据状态值, 返回对应的状态
     * @return 状态集
     */
	public String getStatus() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(this.employeeStatus)) {
			String[] _st = this.employeeStatus.split(",");
			for (String st : _st) {
				int status = Integer.valueOf(st);
				if (EmployeeStatus.Formal.ordinal() == status) {
					// 正式
					sb.append(getFormatKey(EmployeeStatus.Formal.toString()));
					sb.append(",");
				} else if (EmployeeStatus.UnFormal.ordinal() == status) {
					// 试用期
					sb.append(getFormatKey(EmployeeStatus.UnFormal.toString()));
					sb.append(",");
				} else if (EmployeeStatus.Leaving.ordinal() == status) {
					// 申请离职
					sb.append(getFormatKey(EmployeeStatus.Leaving.toString()));
					sb.append(",");
				} else if (EmployeeStatus.Leaved.ordinal() == status) {
					// 离职
					sb.append(getFormatKey(EmployeeStatus.Leaved.toString()));
					sb.append(",");
				} else if (EmployeeStatus.UnRegistedHeadquarters.ordinal() == status) {
					// 总部未报到
					sb.append(getFormatKey(EmployeeStatus.UnRegistedHeadquarters.toString()));
					sb.append(",");
				} else if (EmployeeStatus.UnRegistedBranch.ordinal() == status) {
					// 门店未报到
					sb.append(getFormatKey(EmployeeStatus.UnRegistedBranch.toString()));
					sb.append(",");
				} else if (EmployeeStatus.ToRegisteHeadquarters.ordinal() == status) {
					// 总部待报到
					sb.append(getFormatKey(EmployeeStatus.ToRegisteHeadquarters.toString()));
					sb.append(",");
				} else if (EmployeeStatus.ToRegisteBranch.ordinal() == status) {
					// 门店待报到
					sb.append(getFormatKey(EmployeeStatus.ToRegisteBranch.toString()));
					sb.append(",");
				}
			}
			return StringBuilderHelper.trimLast(sb, ",").toString();
		}
		return getNormalStatus();
	}

	/**
	 * 返回默认的员工所有状态集
	 * @return 员工所有状态集
	 */
    private String getNormalStatus() {
        StringBuilder sb = new StringBuilder();
        // 正式
        sb.append(getFormatKey(EmployeeStatus.Formal.toString()));
        sb.append(",");
        // 试用期
        sb.append(getFormatKey(EmployeeStatus.UnFormal.toString()));
        sb.append(",");
        // 申请离职
        sb.append(getFormatKey(EmployeeStatus.Leaving.toString()));
        sb.append(",");
        // 离职
        sb.append(getFormatKey(EmployeeStatus.Leaved.toString()));
        sb.append(",");
    	// 总部未报到
    	sb.append(getFormatKey(EmployeeStatus.UnRegistedHeadquarters.toString()));
    	sb.append(",");
    	// 门店未报到
    	sb.append(getFormatKey(EmployeeStatus.UnRegistedBranch.toString()));
    	sb.append(",");
    	// 总部待报到
    	sb.append(getFormatKey(EmployeeStatus.ToRegisteHeadquarters.toString()));
    	sb.append(",");
    	// 门店待报到
    	sb.append(getFormatKey(EmployeeStatus.ToRegisteBranch.toString()));
        return sb.toString();
    }

    public String getLastDegreeStr() {
        if(StringUtils.isBlank(this.getLastDegree())){
            return null;
        }else{
            String temp = getFormatKey(lastDegree);
            return temp.contains(",") ? temp.replace(",","'',''") : temp;
        }

    }

    public static String getFormatKey(String str){
         return "''"+str+"''";
    }

    @Override
    public String toString() {
        return "EmployeeSearch{" +
                "orgIds='" + orgIds + '\'' +
                ", positionIds='" + positionIds + '\'' +
                ", black=" + black +
                ", joinDateStart='" + joinDateStart + '\'' +
                ", joinDateEnd='" + joinDateEnd + '\'' +
                ", leaveDateStart='" + leaveDateStart + '\'' +
                ", leaveDateEnd='" + leaveDateEnd + '\'' +
                ", officialYear=" + officialYear +
                ", officialMonth=" + officialMonth +
                ", officialDay=" + officialDay +
                ", bornYear=" + bornYear +
                ", bornMonth=" + bornMonth +
                ", bornDay=" + bornDay +
                ", birthDateFrom=" + birthDateFrom +
                ", birthDateTo=" + birthDateTo +
                ", comeFrom='" + comeFrom + '\'' +
                ", constellation='" + constellation + '\'' +
                ", lastDegree='" + lastDegree + '\'' +
                ", keyWords='" + keyWords + '\'' +
                ", advancedSearch=" + advancedSearch +
                ", employeeStatus='" + employeeStatus + '\'' +
                ", excelHeads='" + excelHeads + '\'' +
                ", exportKey='" + exportKey + '\'' +
                ", company='" + company + '\'' +
                ", archiveStatus=" + archiveStatus +
                '}';
    }
}

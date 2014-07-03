package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-07-02 10:34)
 * Description: 组织公司的中间键实体
 */
public class EmployeeCompany extends BaseEntity {
    private static final long serialVersionUID = -141528550671213247L;

    private int userCode;
    private int companyId;

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public EmployeeCompany(int companyId, int userCode) {
        this.companyId = companyId;
        this.userCode = userCode;
    }

    public EmployeeCompany() {
    }

    @Override
    public String toString() {
        return "EmployeeCompany{" +
                "userCode=" + userCode +
                ", companyId=" + companyId +
                '}';
    }
}

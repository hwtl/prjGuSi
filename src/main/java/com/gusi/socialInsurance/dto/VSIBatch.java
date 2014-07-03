package com.gusi.socialInsurance.dto;

import com.gusi.socialInsurance.model.SIBatch;

import java.util.Date;

/**
 * @Author: fdj
 * @Since: 2014-06-12 15:02
 * @Description: VSIBatch
 */
public class VSIBatch extends SIBatch {
    private Date paymentDate;
    private String companyAddr;
    private String companyPhone;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}

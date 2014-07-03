package com.gusi.boms.dto;

import java.io.Serializable;

/**
 * @Author: fdj
 * @Since: 2014-04-03 09:59
 * @Description: SmsReports
 */
public class SmsReports implements Serializable {

    private static final long serialVersionUID = 2035870125971335363L;
    public String phone;
    public String reportStatus;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public String toString() {
        return "SmsReports{" +
                "phone='" + phone + '\'' +
                ", reportStatus='" + reportStatus + '\'' +
                '}';
    }
}

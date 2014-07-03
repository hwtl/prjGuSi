package com.gusi.boms.model;

import java.io.Serializable;

/**
 * @Author: fdj
 * @Since: 2014-05-19 15:27
 * @Description: School
 */
public class School implements Serializable {
    private static final long serialVersionUID = 3388299248962282730L;
    private int id;
    private String name;
    private int great;
    private int provinceId;
    private String provinceName;
    private int countryId;
    private String countryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGreat() {
        return great;
    }

    public void setGreat(int great) {
        this.great = great;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", great=" + great +
                ", provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}

package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class Centers implements Serializable {
    private int id;
    private String name;
    private String address;
    private String province;
    private String district;
    private String ward;
    private String phone;

    public Centers() {
    }

    public Centers(int id, String name, String address, String province, String district, String ward, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
    }

    public Centers(String name, String address, String province, String district, String ward, String phone) {
        this.name = name;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

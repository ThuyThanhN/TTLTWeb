package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.sql.Date;

public class Patients implements Serializable {
    private int id;
    private String fullname;
    private Date dateOfBirth;
    private String gender;
    private String identification;
    private String address;
    private String province;
    private String district;
    private String ward;

    public Patients() {
    }

    public Patients(String fullname, Date dateOfBirth, String gender, String identification, String address, String province, String ward, String district) {
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.identification = identification;
        this.address = address;
        this.province = province;
        this.ward = ward;
        this.district = district;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getIdentification() {
        return identification;
    }

    public String getAddress() {
        return address;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
}

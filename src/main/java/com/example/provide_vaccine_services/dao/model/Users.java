package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.sql.Date;

public class Users implements Serializable {
    private int id;
    private String fullname;
    private String gender;
    private String identification;
    private Date dateOfBirth;
    private String address;
    private String province;
    private String district;
    private String ward;
    private String phone;
    private String email;
    private String password;
    private int role;

    public Users() { }

    public Users(int id, String fullname, String gender, String identification, Date dateOfBirth, String address, String province, String district, String ward, String phone, String email, String password, int role) {
        this.id = id;
        this.fullname = fullname;
        this.gender = gender;
        this.identification = identification;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Users(String fullname, String gender, String identification, Date dateOfBirth, String address, String province, String district, String ward, String phone, String email, String password, int role) {
        this.fullname = fullname;
        this.gender = gender;
        this.identification = identification;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}

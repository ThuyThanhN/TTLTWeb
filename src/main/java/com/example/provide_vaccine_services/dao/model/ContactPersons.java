package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class ContactPersons implements Serializable {
    private int id;
    private int idUser;
    private int idPatient;
    private String fullname;
    private String relationship;
    private String phone;

    public ContactPersons(int idUser, int idPatient, String fullname, String relationship, String phone) {
        this.idUser = idUser;
        this.idPatient = idPatient;
        this.fullname = fullname;
        this.relationship = relationship;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

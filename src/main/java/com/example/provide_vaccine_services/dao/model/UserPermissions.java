package com.example.provide_vaccine_services.dao.model;

public class UserPermissions {
    private int id;
    private int idUser;
    private int idPermission;

    public UserPermissions(int idUser, int idPermission) {
        this.idUser = idUser;
        this.idPermission = idPermission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }
}

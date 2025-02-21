package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class PackageAges implements Serializable {
    private int id;
    private int idAge;
    private int idPackage;

    public PackageAges(int id, int idAge, int idPackage) {
        this.id = id;
        this.idAge = idAge;
        this.idPackage = idPackage;
    }

    public PackageAges(int idPackage, int idAge) {
        this.idPackage = idPackage;
        this.idAge = idAge;
    }

    public int getIdAge() {
        return idAge;
    }

    public int getId() {
        return id;
    }

    public int getIdPackage() {
        return idPackage;
    }
}

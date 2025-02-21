package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class VaccinePMappings implements Serializable {
    private int id;
    private int idVaccine;
    private int idPackage;
    private int dosage;

    public VaccinePMappings(int id, int idVaccine, int idPackage, int dosage) {
        this.id = id;
        this.idVaccine = idVaccine;
        this.idPackage = idPackage;
        this.dosage = dosage;
    }

    public VaccinePMappings(int idVaccine, int idPackage, int dosage) {
        this.idVaccine = idVaccine;
        this.idPackage = idPackage;
        this.dosage = dosage;
    }

    public int getId() {
        return id;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public int getDosage() {
        return dosage;
    }
}

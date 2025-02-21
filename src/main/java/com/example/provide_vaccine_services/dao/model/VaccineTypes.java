package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class VaccineTypes implements Serializable {
    private int id;
    private int idVaccine;
    private int idAgeGroup;
    private int idDisaseGroup;

    public VaccineTypes(int idVaccine, int idAgeGroup, int idDisaseGroup) {
        this.idVaccine = idVaccine;
        this.idAgeGroup = idAgeGroup;
        this.idDisaseGroup = idDisaseGroup;
    }

    public VaccineTypes(int idDisaseGroup) {;
        this.idDisaseGroup = idDisaseGroup;
    }

    public VaccineTypes(String age, String disase, int id) {
    }

    public int getId() {
        return id;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public int getIdAgeGroup() {
        return idAgeGroup;
    }

    public int getIdDisaseGroup() {
        return idDisaseGroup;
    }
}

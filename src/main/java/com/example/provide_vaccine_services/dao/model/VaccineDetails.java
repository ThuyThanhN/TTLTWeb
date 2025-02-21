package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class VaccineDetails implements Serializable {
    private int id;
    private int idVaccine;
    private String targetGroup;
    private String immunization;
    private String adverseReactions;

    // Constructor
    public VaccineDetails() {}

    public VaccineDetails(int id, int idVaccine, String targetGroup, String immunization, String adverseReactions) {
        this.id = id;
        this.idVaccine = idVaccine;
        this.targetGroup = targetGroup;
        this.immunization = immunization;
        this.adverseReactions = adverseReactions;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public void setIdVaccine(int idVaccine) {
        this.idVaccine = idVaccine;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getImmunization() {
        return immunization;
    }

    public void setImmunization(String immunization) {
        this.immunization = immunization;
    }

    public String getAdverseReactions() {
        return adverseReactions;
    }

    public void setAdverseReactions(String adverseReactions) {
        this.adverseReactions = adverseReactions;
    }
}

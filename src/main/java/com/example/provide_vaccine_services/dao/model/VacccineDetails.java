package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class VacccineDetails implements Serializable {
    private int id;
    private int idVaccine;
    private String targetGroup;
    private String immunization;
    private String adverseReactions;

    public VacccineDetails(int idVaccine, String targetGroup, String adverseReactions, String immunization) {
        this.idVaccine = idVaccine;
        this.targetGroup = targetGroup;
        this.adverseReactions = adverseReactions;
        this.immunization = immunization;
    }

    public VacccineDetails(int id, int idVaccine, String targetGroup, String immunization, String adverseReactions) {
        this.id = id;
        this.idVaccine = idVaccine;
        this.targetGroup = targetGroup;
        this.immunization = immunization;
        this.adverseReactions = adverseReactions;
    }

    public int getId() {
        return id;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public String getImmunization() {
        return immunization;
    }

    public String getAdverseReactions() {
        return adverseReactions;
    }
}

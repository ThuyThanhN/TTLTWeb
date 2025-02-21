package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class VaccineContents implements Serializable {
    private int id;
    private int idDetail;
    private String origin;
    private String administrationRoute;
    private String contraindications;
    private String precaution;
    private String drugInteractions;
    private String sideEffects;
    private String name; // Tên vắc-xin
    private String description; // Mô tả vắc-xin
    private String imageUrl; // Đường dẫn ảnh


//CONTRUSTER LAY CA NAME VA DECRIPTION CUA VACIINE
    public VaccineContents() {
    }
    public VaccineContents(int idDetail, String origin, String administrationRoute, String contraindications, String precaution, String drugInteractions, String sideEffects) {
        this.idDetail = idDetail;
        this.origin = origin;
        this.administrationRoute = administrationRoute;
        this.contraindications = contraindications;
        this.precaution = precaution;
        this.drugInteractions = drugInteractions;
        this.sideEffects = sideEffects;
    }
    public VaccineContents(int id, int idDetail, String origin, String administrationRoute, String contraindications,
                           String precaution, String drugInteractions, String sideEffects, String name, String description) {
        this.id = id;
        this.idDetail = idDetail;
        this.origin = origin;
        this.administrationRoute = administrationRoute;
        this.contraindications = contraindications;
        this.precaution = precaution;
        this.drugInteractions = drugInteractions;
        this.sideEffects = sideEffects;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public VaccineContents(int id, int idDetail, String origin, String administrationRoute, String contraindications, String precaution, String drugInteractions, String sideEffects) {
        this.id = id;
        this.idDetail = idDetail;
        this.origin = origin;
        this.administrationRoute = administrationRoute;
        this.contraindications = contraindications;
        this.precaution = precaution;
        this.drugInteractions = drugInteractions;
        this.sideEffects = sideEffects;
    }
    public VaccineContents(int id, int idDetail, String origin, String administrationRoute,
                           String contraindications, String precaution, String drugInteractions,
                           String sideEffects, String name, String description, String imageUrl) {
        this.id = id;
        this.idDetail = idDetail;
        this.origin = origin;
        this.administrationRoute = administrationRoute;
        this.contraindications = contraindications;
        this.precaution = precaution;
        this.drugInteractions = drugInteractions;
        this.sideEffects = sideEffects;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public String getOrigin() {
        return origin;
    }

    public String getAdministrationRoute() {
        return administrationRoute;
    }

    public String getContraindications() {
        return contraindications;
    }

    public String getPrecaution() {
        return precaution;
    }

    public String getDrugInteractions() {
        return drugInteractions;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setAdministrationRoute(String administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public void setPrecaution(String precaution) {
        this.precaution = precaution;
    }

    public void setDrugInteractions(String drugInteractions) {
        this.drugInteractions = drugInteractions;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;}

    // Getter & Setter cho imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

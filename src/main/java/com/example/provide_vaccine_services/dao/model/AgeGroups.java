package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.util.List;

public class AgeGroups implements Serializable {
    private int id;
    private String name;
    private List<VaccinePackages> packages; // Danh sách gói vắc xin liên quan

    public AgeGroups() {
    }

    public AgeGroups(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AgeGroups(String name) {
        this.name = name;
    }

    public AgeGroups(int id, String name, List<VaccinePackages> packages) {
        this.id = id;
        this.name = name;
        this.packages = packages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VaccinePackages> getPackages() {
        return packages;
    }

    public void setPackages(List<VaccinePackages> packages) {
        this.packages = packages;
    }
}

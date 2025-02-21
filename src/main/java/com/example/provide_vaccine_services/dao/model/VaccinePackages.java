package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.util.List;

public class VaccinePackages implements Serializable {
    private int id;
    private String name;
    private String description;
    private float totalPrice;
    private List<Vaccines> vaccines;

    public VaccinePackages(int id, String name, String description, float totalPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice; // Khởi tạo giá
    }

    public VaccinePackages(String name, String description, float totalPrice) {
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice; // Khởi tạo giá
    }

    // Getter và Setter
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public VaccinePackages(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public VaccinePackages(int id, String name, String description, List<Vaccines> vaccines) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vaccines = vaccines;
    }

    public VaccinePackages(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

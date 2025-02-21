package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class Suppliers implements Serializable {
    private int id;
    private String name;
    private String countryOfOrigin;

    public Suppliers() {
    }

    public Suppliers(String name, String countryOfOrigin) {
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
    }

    public Suppliers(int id, String name, String countryOfOrigin) {
        this.id = id;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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

    @Override
    public String toString() {
        return "Suppliers [id=" + id + ", name=" + name + ", countryOfOrigin=" + countryOfOrigin + "]";
    }
}

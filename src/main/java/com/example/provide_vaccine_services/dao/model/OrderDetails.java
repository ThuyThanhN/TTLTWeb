package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class OrderDetails implements Serializable {
        private int id;
        private int idOrder;
        private int idVaccine;
        private int idPackage;
        private int quantityOrder;
        private float price;

    // Constructor đầy đủ với cả vắc xin và gói
    public OrderDetails(int idOrder, int idVaccine, int idPackage, int quantityOrder, float price) {
        this.idOrder = idOrder;
        this.idVaccine = idVaccine;
        this.idPackage = idPackage;
        this.quantityOrder = quantityOrder;
        this.price = price;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public void setIdVaccine(int idVaccine) {
        this.idVaccine = idVaccine;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(int idPackage) {
        this.idPackage = idPackage;
    }

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}


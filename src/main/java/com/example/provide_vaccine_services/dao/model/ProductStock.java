package com.example.provide_vaccine_services.dao.model;

import java.time.LocalDateTime;

public class ProductStock {
    int id;
    int vaccineId;
    String productName;
    int loss;
    LocalDateTime expired;
    double totalPrice;

    public ProductStock() {

    }
    public ProductStock(int vaccineId, String productName,double totalPrice, int loss, LocalDateTime expired) {
        this.vaccineId = vaccineId;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.loss = loss;
        this.expired = expired;
    }


    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}

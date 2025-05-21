package com.example.provide_vaccine_services.dao.model;

import java.time.LocalDateTime;

public class Transaction {
    int transactionId;
    int warehouseId;
    int vaccineId;
    String type;
    int quantity;
    LocalDateTime date;
    Users user; // person who created the transaction

    public Transaction(int transactionId, int warehouseId, int vaccineId, String type, int quantity, LocalDateTime date, Users user) {
        this.transactionId = transactionId;
        this.warehouseId = warehouseId;
        this.vaccineId = vaccineId;
        this.type = type;
        this.quantity = quantity;
        this.date = date;
        this.user = user;

    }

    public Transaction(int warehouseId, int vaccineId, String type, int quantity, Users user) {
        this.warehouseId = warehouseId;
        this.vaccineId = vaccineId;
        this.type = type;
        this.quantity = quantity;
        this.date = LocalDateTime.now();
        this.user = user;
    }

    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public int getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
    public int getVaccineId() {
        return vaccineId;
    }
    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Users getUser() {
        return user;

    }

    public void setUser(Users user) {
        this.user = user;
    }

}


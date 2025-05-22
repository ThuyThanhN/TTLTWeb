package com.example.provide_vaccine_services.dao.model;

import java.time.LocalDateTime;

public class Transaction {
    int transactionId;
    int vaccineId;
    String type;
    int quantity;
    LocalDateTime created_date;
    LocalDateTime expiry_date;
    Users user; // person who created the transaction


    public Transaction(int transactionId, int vaccineId, String type, int quantity, LocalDateTime expiry_date, Users user) {
        this.transactionId = transactionId;
        this.vaccineId = vaccineId;
        this.type = type;
        this.quantity = quantity;
        this.created_date = LocalDateTime.now();
        this.user = user;
        this.expiry_date = expiry_date;
    }

    public Transaction( int vaccineId, String type, int quantity, LocalDateTime expiry_date, Users user) {
        this.vaccineId = vaccineId;
        this.type = type;
        this.quantity = quantity;
        this.created_date = LocalDateTime.now();
        this.user = user;
        this.expiry_date = expiry_date;
    }

    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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
        return created_date;
    }

    public void setDate(LocalDateTime created_date) {
        this.created_date = created_date;
    }
    public Users getUser() {
        return user;

    }
    public LocalDateTime getExpiry_date() {
        return expiry_date;
    }
    public void setExpiry_date(LocalDateTime expiry_date) {
        this.expiry_date = expiry_date;
    }
    public void setUser(Users user) {
        this.user = user;
    }

}


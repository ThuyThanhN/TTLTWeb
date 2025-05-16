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


}

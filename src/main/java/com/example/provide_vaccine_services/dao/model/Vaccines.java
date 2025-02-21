package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Vaccines implements Serializable {
    private int id;
    private int idSupplier;
    private String name;
    private String description;
    private int stockQuantity;
    private float price;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;
    private String prevention;
    private String countryOfOrigin;

    private int orderCount;  // lưu trữ thông tin số lần đặt

    public Vaccines() {
    }

    public Vaccines(int id, String name, String description, int stockQuantity,
                    float price, String imageUrl, String status, LocalDateTime createdAt) {
        this.id = id;
        this.idSupplier = idSupplier;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.prevention = prevention;

    }


    public Vaccines(int idSupplier, String name, String description, int stockQuantity, float price, String imageUrl, String status, LocalDateTime createdAt, String prevention) {
        this.idSupplier = idSupplier;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.prevention = prevention;
    }

    public Vaccines(int id, int idSupplier, String name, String description, int stockQuantity, float price, String imageUrl, String status, LocalDateTime createdAt, String prevention) {
        this.id = id;
        this.idSupplier = idSupplier;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.prevention = prevention;
    }

    // Constructor đầy đủ có countryOfOrigin
    public Vaccines(int id, String name, String description, int stockQuantity, float price, String imageUrl, String status, LocalDateTime createdAt, String prevention, String countryOfOrigin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.prevention = prevention;  // Thêm phòng bệnh
        this.countryOfOrigin = countryOfOrigin;  // Thêm quốc gia xuất xứ
    }

    // Getter và Setter cho countryOfOrigin
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public int getId() {
        return id;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

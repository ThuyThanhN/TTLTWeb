package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

public class Orders implements Serializable {
    private int id;
    private int idPatient;
    private int idCenter;
    private LocalDateTime createdAt;
    private Date appointmentDate;
    private String appointmentTime;
    private String status;
    private String paymentSatus;

    private String patientName;
    private Date dateOfBirth;

    private String centerName;
    private String centerAddress;
    private String centerPhone;

    private float price;

    public Orders() {
    }

    public Orders(int id, int idPatient, int idCenter, LocalDateTime createdAt, Date appointmentDate,
                  String appointmentTime, String status, String paymentSatus, String patientName, Date dateOfBirth,
                  String centerName, String centerAddress, String centerPhone, float price) {
        this.id = id;
        this.idPatient = idPatient;
        this.idCenter = idCenter;
        this.createdAt = createdAt;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.paymentSatus = paymentSatus;
        this.patientName = patientName;
        this.dateOfBirth = dateOfBirth;
        this.centerName = centerName;
        this.centerAddress = centerAddress;
        this.centerPhone = centerPhone;
        this.price = price;
    }

    public Orders(int orderId, int idPatient, int idCenter, LocalDateTime createdAt, Date appointmentDate, String appointmentTime, String status, String paymentSatus) {
        this.id = orderId;
        this.idPatient = idPatient;
        this.idCenter = idCenter;
        this.createdAt = createdAt;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.paymentSatus = paymentSatus;
    }

    public Orders(int idCenter, int idPatient, LocalDateTime createdAt, Date appointmentDate, String appointmentTime, String status, String paymentSatus) {
        this.idCenter = idCenter;
        this.idPatient = idPatient;
        this.createdAt = createdAt;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.paymentSatus = paymentSatus;
    }

    public int getId() {
        return id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentSatus() {
        return paymentSatus;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentSatus(String paymentSatus) {
        this.paymentSatus = paymentSatus;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterPhone() {
        return centerPhone;
    }

    public void setCenterPhone(String centerPhone) {
        this.centerPhone = centerPhone;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", idPatient=" + idPatient +
                ", idCenter=" + idCenter +
                ", createdAt=" + createdAt +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", status='" + status + '\'' +
                ", paymentSatus='" + paymentSatus + '\'' +
                ", patientName='" + patientName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", centerName='" + centerName + '\'' +
                ", centerAddress='" + centerAddress + '\'' +
                ", centerPhone='" + centerPhone + '\'' +
                ", price=" + price +
                '}';
    }

}

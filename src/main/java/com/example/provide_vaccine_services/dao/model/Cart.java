package com.example.provide_vaccine_services.dao.model;

import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.VaccinePackageDao;
import com.example.provide_vaccine_services.dao.db.DBConnect;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Cart implements Serializable {
    Patients patients; // table 1
    ContactPersons contactPersons; // table 2
    Orders orders ; //  table 3
    OrderDetails orderDetails; //  table 4
    // session 1 patient
    Map<Integer, Patients> integerPatientsMap;
    // session 2 ContactPersons
    Map<Integer, ContactPersons> integerContactPersonsMap;
    ///  session 3 order-orderdetail
    Map<Orders, List<OrderDetails>> ordersOrderDetailsMap;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Map<Orders, List<OrderDetails>> getOrdersOrderDetailsMap() {
        return ordersOrderDetailsMap;
    }

    public void setOrdersOrderDetailsMap(Map<Orders, List<OrderDetails>> ordersOrderDetailsMap) {
        this.ordersOrderDetailsMap = ordersOrderDetailsMap;
    }

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }

    public ContactPersons getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(ContactPersons contactPersons) {
        this.contactPersons = contactPersons;
    }

    public Map<Integer, Patients> getIntegerPatientsMap() {
        return integerPatientsMap;
    }

    public void setIntegerPatientsMap(Map<Integer, Patients> integerPatientsMap) {
        this.integerPatientsMap = integerPatientsMap;
    }

    public Map<Integer, ContactPersons> getIntegerContactPersonsMap() {
        return integerContactPersonsMap;
    }

    public void setIntegerContactPersonsMap(Map<Integer, ContactPersons> integerContactPersonsMap) {
        this.integerContactPersonsMap = integerContactPersonsMap;
    }

    public Cart() {
    }
    // Tính tổng giá trị giỏ hàng
    public float calculateTotal(int orderId) {
        // Tìm Orders với orderId tương ứng
        Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                .filter(o -> o.getId() == orderId)
                .findFirst();

        if (order.isPresent()) {
            // Tính tổng giá
            return (float)ordersOrderDetailsMap.get(order.get()).stream()
                    .mapToDouble(od -> od.getPrice() * od.getQuantityOrder())
                    .sum();
        }
        // Nếu không tìm thấy Orders với orderId
        return 0;
    }

    // lay ten vaccine theo id vaccine
    public String getVaccineName(int vaccineId){
        VaccineDao vaccineDao = new VaccineDao();
        return vaccineDao.getVaccineName(vaccineId);
    }
    // lay ten package theo id vaccine
    public String getVaccinePackageName(int packageId){
        VaccinePackageDao vaccinePackageDao = new VaccinePackageDao();
        return vaccinePackageDao.getPackageName(packageId);
    }

    // Tạo khóa duy nhất dựa trên id vaccine hoặc package
    private int generateKey(OrderDetails od) {
        if (od.getIdVaccine() != 0) {
            return od.getIdVaccine(); // Khóa cho vắc xin lẻ
        }
        return -od.getIdPackage(); // Khóa cho gói vắc xin, dùng dấu "-" để phân biệt
    }
}


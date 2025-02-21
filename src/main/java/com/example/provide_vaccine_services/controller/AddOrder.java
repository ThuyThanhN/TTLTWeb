package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "AddOrder", value = "/addOrder")
public class AddOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        Users users = (Users) session.getAttribute("user");
        if (users == null) {

            throw new ServletException("User is not logged in");
        }
        int userId = users.getId();
        System.out.println("userId---" + userId);

        //  Nguoi tiem
        String namePatient = request.getParameter("patient-name");
        String date = request.getParameter("date");
        String gender = request.getParameter("gender");
        String identifier = request.getParameter("identifier");
        String address = request.getParameter("address");
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");

        Date dateOfBirth = Date.valueOf(date);
        PatientDao patientDao = new PatientDao();
        int idPatient = patientDao.insertPatient(new Patients(namePatient, dateOfBirth, gender, identifier, address, province, district, ward));

        //  Nguoi lien he
        String nameContact = request.getParameter("contact_name");
        String relationship = request.getParameter("relationship");
        String phone = request.getParameter("contact_phone");
        ContactPersonDao cpDao = new ContactPersonDao();
        cpDao.insertContact(new ContactPersons(userId, idPatient, nameContact, relationship, phone));

        //  Don hang
        int idCenter = Integer.parseInt(request.getParameter("center-select"));
        LocalDateTime createdAt = LocalDateTime.now();
        String preferredDate = request.getParameter("preferred_date");
        Date appointmentDate = Date.valueOf(preferredDate);
        String appointmentTime = request.getParameter("vaccination_time");
        String status = "Chưa được duyệt";   // Mặc định là "Chưa được duyệt"
        String paymentStatus = "Chưa thanh toán";  // Mặc định là "Chưa thanh toán"

        request.setAttribute("date", appointmentDate);
        request.setAttribute("time", appointmentTime);

        OrderDao orderDao = new OrderDao();
        int idOrder = orderDao.insertOrder(new Orders(1,idPatient, idCenter, createdAt, appointmentDate, appointmentTime, status, paymentStatus));

        Orders order = new Orders(1,idPatient, idCenter, createdAt, appointmentDate, appointmentTime, status, paymentStatus);
        System.out.println("Order Details: ");
        System.out.println("Patient ID: " + order.getIdPatient());
        System.out.println("Center ID: " + order.getIdCenter());
        System.out.println("Created At: " + order.getCreatedAt());
        System.out.println("Appointment Date: " + order.getAppointmentDate());
        System.out.println("Appointment Time: " + order.getAppointmentTime());

        //  Don hang chi tiet
        OrderDetailDao odd = new OrderDetailDao();
        String[] selectedPackages = request.getParameterValues("selectedPackages");
        String[] selectedVaccines = request.getParameterValues("selectedVaccines");

        if (selectedPackages != null) {
            for (String packageId : selectedPackages) {
                System.out.println("Selected package: " + packageId);
            }
        }
        if (selectedVaccines != null) {
            for (String vaccineId : selectedVaccines) {
                System.out.println("Selected vaccine: " + vaccineId);
            }
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        // Kiểm tra và thêm các gói vắc xin
        if (selectedPackages != null && selectedPackages.length > 0) {
            // Đếm số lần xuất hiện của mỗi gói
            Map<String, Long> packageCount = Arrays.stream(selectedPackages)
                    .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

            // Lặp qua các gói vắc xin đã chọn và gọi insertDetail
            for (Map.Entry<String, Long> entry : packageCount.entrySet()) {
                int packageId = Integer.parseInt(entry.getKey());

                float packagePrice = odd.getPackagePrice(packageId);

//                database
//                odd.insertDetail(idOrder, 0, packageId, quantity);
                OrderDetails orderDetails = new OrderDetails(idOrder, 0, packageId, 1, packagePrice);
//                cart.add(orderDetails);
            }
        } else {
            System.out.println("No packages selected.");
        }

        // Kiểm tra và thêm các vaccine lẻ
        if (selectedVaccines != null && selectedVaccines.length > 0) {
            // Đếm số lần xuất hiện của mỗi vaccine lẻ
            Map<String, Long> vaccineCount = Arrays.stream(selectedVaccines)
                    .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

            // Lặp qua các vaccine đã chọn và gọi insertDetail
            for (Map.Entry<String, Long> entry : vaccineCount.entrySet()) {
                int vaccineId = Integer.parseInt(entry.getKey());

                // Lấy giá của vaccine (giả sử có phương thức getPriceByVaccineId)
                float vaccinePrice = odd.getVaccinePrice(vaccineId);

                // Database
                // odd.insertDetail(idOrder, vaccineId, 0, quantity);

                // Thêm vào giỏ hàng với giá
                OrderDetails orderDetails = new OrderDetails(idOrder, vaccineId, 0, 1, vaccinePrice);
//                cart.add(orderDetails);
            }
        } else {
            System.out.println("No vaccines selected.");
        }

        // Sau khi thêm OrderDetails vào cart, kiểm tra lại giỏ hàng
//        System.out.println("Cart contents after adding items:");
//        if (cart != null) {
//            for (Map.Entry<Integer, OrderDetails> entry : cart.getItems().entrySet()) {
//                OrderDetails od = entry.getValue();
//                System.out.println("OrderDetails - Order ID: " + od.getIdOrder() +
//                        ", Vaccine ID: " + od.getIdVaccine() +
//                        ", Package ID: " + od.getIdPackage() +
//                        ", Quantity: " + od.getQuantityOrder() +
//                        ", Price: " + od.getPrice());
//            }
//        } else {
//            System.out.println("No items in the cart.");
//        }

        session.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("shopping_cart.jsp");
        dispatcher.forward(request, response);
    }
}
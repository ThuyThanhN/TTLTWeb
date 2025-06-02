package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "ShoppingCart", value = "/shoppingCart")
public class ShoppingCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        Users users = (Users) session.getAttribute("user");
        double totalBill = 0;

//        if (users == null) {
//            throw new ServletException("User is not logged in");
//        }

        // Chỉ lấy userId nếu đã đăng nhập
        Integer userId = null;
        if (users != null) {
            userId = users.getId();
            System.out.println("userId---" + userId);
        }

        String options = request.getParameter("options");
        String cartId = request.getParameter("cartId");

        // get cart session neu ko co thi tao model cart moi
        List<Integer> listCart = (List<Integer>) session.getAttribute("listCart");
        if (listCart == null) {
            listCart = new ArrayList<>();
        }
        Map<Integer, Patients> integerPatientsMap = (Map<Integer, Patients>) session.getAttribute("integerPatientsMap");
        if (integerPatientsMap == null) {
            integerPatientsMap = new HashMap<>();
        }
        Map<Integer, ContactPersons> integerContactPersonsMap = (Map<Integer, ContactPersons>) session.getAttribute("integerContactPersonsMap");
        if (integerContactPersonsMap == null) {
            integerContactPersonsMap = new HashMap<>();
        }
        Map<Orders, List<OrderDetails>> ordersOrderDetailsMap = (Map<Orders, List<OrderDetails>>) session.getAttribute("ordersOrderDetailsMap");
        if (ordersOrderDetailsMap == null) {
            ordersOrderDetailsMap = new HashMap<>();
        }

        // neu remove
        if (options != null && cartId != null && options.equalsIgnoreCase("remove")) {
            Integer cartIdInt = Integer.parseInt(cartId);
            Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartIdInt)
                    .findFirst();
            if (order.isPresent()) {
                // remove item trong list cart theo cart id
                listCart.remove(cartIdInt);
                // remove item trong list patient theo cart id
                integerPatientsMap.remove(cartIdInt);
                // remove item trong list contact theo cart id
                integerContactPersonsMap.remove(cartIdInt);
                // remove item trong list orders theo cart id
                ordersOrderDetailsMap.remove(order.get());

                // chỉnh sửa lại giá cua totalBill
                for (Map.Entry<Orders, List<OrderDetails>> entry : ordersOrderDetailsMap.entrySet()) {
                    List<OrderDetails> details = entry.getValue();
                    for (OrderDetails detail : details) {
                        totalBill += detail.getPrice();
                    }
                }
            }


            session.setAttribute("listCart", listCart);
            session.setAttribute("integerPatientsMap", integerPatientsMap);
            session.setAttribute("integerContactPersonsMap", integerContactPersonsMap);
            session.setAttribute("ordersOrderDetailsMap", ordersOrderDetailsMap);
            request.setAttribute("totalBill", totalBill);

            response.sendRedirect("shoppingCart");


        } else {
            // neu vao cart mac dinh
            if (listCart == null) {
                listCart = new ArrayList<>();
            }

            //
            // Kiểm tra nếu giỏ hàng trống thì chuyển đến trang "noOrder.jsp"
            if (ordersOrderDetailsMap == null || ordersOrderDetailsMap.isEmpty()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("noOrder.jsp");
                dispatcher.forward(request, response);
                return; // Dừng thực thi để không tiếp tục xử lý bên dưới
            }

            // chỉnh sửa lại giá cua totalBill
            for (Map.Entry<Orders, List<OrderDetails>> entry : ordersOrderDetailsMap.entrySet()) {
                List<OrderDetails> details = entry.getValue();
                for (OrderDetails detail : details) {
                    totalBill += detail.getPrice();
                    System.out.println("totalBill---" + totalBill + " ---- detail:" + detail);
                }
            }

            Cart cart = new Cart();
            cart.setIntegerPatientsMap(integerPatientsMap);
            cart.setIntegerContactPersonsMap(integerContactPersonsMap);
            cart.setOrdersOrderDetailsMap(ordersOrderDetailsMap);
            request.setAttribute("cart", cart);
            request.setAttribute("listCart", listCart);
            request.setAttribute("totalBill", totalBill);

            RequestDispatcher dispatcher = request.getRequestDispatcher("shopping_cart.jsp");
            dispatcher.forward(request, response);
        }
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

        // lấy danh sách sản phẩm trong list card
        List<Integer> listCart = (List<Integer>) session.getAttribute("listCart");

        // chua co thi tao moi
        if (listCart == null) {
            listCart = new ArrayList<>();
            session.setAttribute("listCart", listCart);
        }

        Map<Integer, Patients> integerPatientsMap =
                (Map<Integer, Patients>) session.getAttribute("integerPatientsMap");
        Map<Integer, ContactPersons> integerContactPersonsMap =
                (Map<Integer, ContactPersons>) session.getAttribute("integerContactPersonsMap");
        Map<Orders, List<OrderDetails>> ordersOrderDetailsMap =
                (Map<Orders, List<OrderDetails>>) session.getAttribute("ordersOrderDetailsMap");

        for (int i = 0; i < listCart.size(); i++) {
            // them patient
            PatientDao patientDao = new PatientDao();
            Patients patients = integerPatientsMap.get(listCart.get(i));
            if (patients == null) {
                break; //  den item gio hang tiep theo khi patient ko co trong session
            }
            int idPatient = patientDao.insertPatient(new Patients(patients.getFullname(), patients.getDateOfBirth(),
                    patients.getGender(), patients.getIdentification(), patients.getAddress(), patients.getProvince(),
                    patients.getDistrict(), patients.getWard()));
            System.out.println("idPatient " + idPatient);

            //them contact
            ContactPersonDao cpDao = new ContactPersonDao();
            ContactPersons contactPersons = integerContactPersonsMap.get(listCart.get(i));
            cpDao.insertContact(new ContactPersons(userId, idPatient, contactPersons.getFullname(), contactPersons.getRelationship(), contactPersons.getPhone()));

            System.out.println("contactPersons " + contactPersons.getFullname());

            // them orders
            OrderDao orderDao = new OrderDao();
            int finalI = i;
            int cartItemId = listCart.get(i);
            Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartItemId)
                    .findFirst();
            if (order.isPresent()) {
                Orders foundOrder = order.get();
                int idOrder = orderDao.insertOrder(new Orders(listCart.get(i), idPatient, foundOrder.getIdCenter(), foundOrder.getCreatedAt(),
                        foundOrder.getAppointmentDate(), foundOrder.getAppointmentTime(), foundOrder.getStatus(),
                        foundOrder.getPaymentStatus()));
                System.out.println("idOrder" + idOrder);

                // them orderdetail
                OrderDetailDao odd = new OrderDetailDao();
                VaccineDao vaccineDao = new VaccineDao();

                List<OrderDetails> orderDetails = ordersOrderDetailsMap.get(foundOrder);
                for (OrderDetails oddOrderDetail : orderDetails) {
                    int result = odd.insertDetailFull(idOrder, oddOrderDetail.getIdVaccine(), oddOrderDetail.getIdPackage(),
                            oddOrderDetail.getQuantityOrder(), oddOrderDetail.getPrice());
                    System.out.println("orderDetails" + result);

                    // trừ số lượng vaccine đã đặt
                    int quantity = -oddOrderDetail.getQuantityOrder();
                    vaccineDao.updateQuantity(oddOrderDetail.getIdVaccine(), quantity);
                }
            }
        }

        session.removeAttribute("listCart");
        session.removeAttribute("integerPatientsMap");
        session.removeAttribute("integerContactPersonsMap");
        session.removeAttribute("ordersOrderDetailsMap");

        if(users.getRole() == 1) {
            response.sendRedirect("table-data-order");
        } else {
            response.sendRedirect("success.jsp");
        }
    }
}
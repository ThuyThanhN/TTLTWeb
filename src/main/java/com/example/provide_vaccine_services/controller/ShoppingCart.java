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
        // Đặt mã hóa ký tự request thành UTF-8 để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // Lấy đối tượng user đã đăng nhập từ session (có thể null nếu chưa đăng nhập)
        Users users = (Users) session.getAttribute("user");
        double totalBill = 0;

        // Lấy userId nếu đã đăng nhập, null nếu chưa đăng nhập
        Integer userId = null;
        if (users != null) {
            userId = users.getId();
            System.out.println("userId---" + userId);
        }

        // Lấy tham số options và cartId từ request (dùng để xử lý xóa sản phẩm)
        String options = request.getParameter("options");
        String cartId = request.getParameter("cartId");

        // Lấy các đối tượng giỏ hàng từ session, tạo mới nếu chưa tồn tại
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

        // Nếu có yêu cầu xóa sản phẩm khỏi giỏ hàng
        if (options != null && cartId != null && options.equalsIgnoreCase("remove")) {
            Integer cartIdInt = Integer.parseInt(cartId);

            // Tìm order tương ứng trong map dựa trên cartId
            Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartIdInt)
                    .findFirst();
            if (order.isPresent()) {
                // Xóa cartId khỏi các map và list tương ứng
                listCart.remove(cartIdInt);
                integerPatientsMap.remove(cartIdInt);
                integerContactPersonsMap.remove(cartIdInt);
                ordersOrderDetailsMap.remove(order.get());

                // Cập nhật lại tổng tiền totalBill sau khi xóa
                for (Map.Entry<Orders, List<OrderDetails>> entry : ordersOrderDetailsMap.entrySet()) {
                    List<OrderDetails> details = entry.getValue();
                    for (OrderDetails detail : details) {
                        totalBill += detail.getPrice();
                    }
                }
            }

            // Cập nhật lại session với các đối tượng giỏ hàng mới
            session.setAttribute("listCart", listCart);
            session.setAttribute("integerPatientsMap", integerPatientsMap);
            session.setAttribute("integerContactPersonsMap", integerContactPersonsMap);
            session.setAttribute("ordersOrderDetailsMap", ordersOrderDetailsMap);
            // Đặt tổng tiền vào request attribute để hiển thị
            request.setAttribute("totalBill", totalBill);

            // Chuyển hướng về lại trang giỏ hàng
            response.sendRedirect("shoppingCart");

        } else {
            // Trường hợp truy cập bình thường vào giỏ hàng
            if (listCart == null) {
                listCart = new ArrayList<>();
            }

            // Kiểm tra nếu giỏ hàng trống thì chuyển đến trang "noOrder.jsp"
            if (ordersOrderDetailsMap == null || ordersOrderDetailsMap.isEmpty()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("noOrder.jsp");
                dispatcher.forward(request, response);
                return; // Dừng xử lý để không tiếp tục bên dưới
            }

            // Tính tổng tiền totalBill dựa trên tất cả orderDetails trong giỏ hàng
            for (Map.Entry<Orders, List<OrderDetails>> entry : ordersOrderDetailsMap.entrySet()) {
                List<OrderDetails> details = entry.getValue();
                for (OrderDetails detail : details) {
                    totalBill += detail.getPrice();
                    System.out.println("totalBill---" + totalBill + " ---- detail:" + detail);
                }
            }

            // Tạo đối tượng Cart để gói dữ liệu giỏ hàng
            Cart cart = new Cart();
            cart.setIntegerPatientsMap(integerPatientsMap);
            cart.setIntegerContactPersonsMap(integerContactPersonsMap);
            cart.setOrdersOrderDetailsMap(ordersOrderDetailsMap);

            // Đặt các thuộc tính vào request để JSP hiển thị
            request.setAttribute("cart", cart);
            request.setAttribute("listCart", listCart);
            request.setAttribute("totalBill", totalBill);

            // Forward tới trang hiển thị giỏ hàng
            RequestDispatcher dispatcher = request.getRequestDispatcher("shopping_cart.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt mã hóa ký tự request UTF-8
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // Lấy user đăng nhập từ session, nếu chưa đăng nhập thì lỗi
        Users users = (Users) session.getAttribute("user");
        if (users == null) {
            throw new ServletException("User is not logged in");
        }

        int userId = users.getId();
        System.out.println("userId---" + userId);

        // Lấy danh sách sản phẩm trong giỏ hàng từ session, tạo mới nếu chưa có
        List<Integer> listCart = (List<Integer>) session.getAttribute("listCart");

        // chua co thi tao moi
        if (listCart == null) {
            listCart = new ArrayList<>();
            session.setAttribute("listCart", listCart);
        }

        // Lấy các map chứa thông tin patients, contact persons và order details từ session
        Map<Integer, Patients> integerPatientsMap = (Map<Integer, Patients>) session.getAttribute("integerPatientsMap");
        Map<Integer, ContactPersons> integerContactPersonsMap = (Map<Integer, ContactPersons>) session.getAttribute("integerContactPersonsMap");
        Map<Orders, List<OrderDetails>> ordersOrderDetailsMap = (Map<Orders, List<OrderDetails>>) session.getAttribute("ordersOrderDetailsMap");

        // Khởi tạo các DAO để thao tác database
        PatientDao patientDao = new PatientDao();
        ContactPersonDao cpDao = new ContactPersonDao();
        OrderDao orderDao = new OrderDao();
        OrderDetailDao odd = new OrderDetailDao();
        VaccineDao vaccineDao = new VaccineDao();
        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        // Duyệt từng sản phẩm trong giỏ hàng để lưu dữ liệu vào database
        for (int i = 0; i < listCart.size(); i++) {
            // Lấy thông tin bệnh nhân từ map
            Patients patients = integerPatientsMap.get(listCart.get(i));
            if (patients == null) {
                // Nếu không có bệnh nhân cho sản phẩm này thì bỏ qua và tiếp tục
                continue;
            }
            // Thêm bệnh nhân mới vào DB, nhận về idPatient
            int idPatient = patientDao.insertPatient(new Patients(patients.getFullname(), patients.getDateOfBirth(),
                    patients.getGender(), patients.getIdentification(), patients.getAddress(), patients.getProvince(),
                    patients.getDistrict(), patients.getWard()));
            System.out.println("idPatient " + idPatient);

            // Lấy thông tin người liên hệ
            ContactPersons contactPersons = integerContactPersonsMap.get(listCart.get(i));
            // Thêm người liên hệ vào DB liên kết với user và bệnh nhân
            cpDao.insertContact(new ContactPersons(userId, idPatient, contactPersons.getFullname(), contactPersons.getRelationship(), contactPersons.getPhone()));
            System.out.println("contactPersons " + contactPersons.getFullname());

            // Tìm đơn hàng tương ứng trong map theo cartId
            int cartItemId = listCart.get(i);
            Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartItemId)
                    .findFirst();

            if (order.isPresent()) {
                Orders foundOrder = order.get();
                // Thêm đơn hàng mới vào DB, nhận về idOrder
                int idOrder = orderDao.insertOrder(new Orders(listCart.get(i), idPatient, foundOrder.getIdCenter(), foundOrder.getCreatedAt(),
                        foundOrder.getAppointmentDate(), foundOrder.getAppointmentTime(), foundOrder.getStatus(),
                        foundOrder.getPaymentStatus()));
                System.out.println("idOrder" + idOrder);

                // Thêm chi tiết đơn hàng cho từng vaccine/package
                List<OrderDetails> orderDetails = ordersOrderDetailsMap.get(foundOrder);
                for (OrderDetails oddOrderDetail : orderDetails) {
                    int result = odd.insertDetailFull(idOrder, oddOrderDetail.getIdVaccine(), oddOrderDetail.getIdPackage(),
                            oddOrderDetail.getQuantityOrder(), oddOrderDetail.getPrice());
                    System.out.println("orderDetails" + result);

                    // trừ số lượng vaccine đã đặt
                    int quantity = -oddOrderDetail.getQuantityOrder();
                    if(oddOrderDetail.getIdPackage() < 0) {
                        vaccineDao.updateQuantity(oddOrderDetail.getIdVaccine(), quantity);
                    }

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
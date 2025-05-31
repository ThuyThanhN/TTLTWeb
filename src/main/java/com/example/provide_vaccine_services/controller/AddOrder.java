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
        // Không xử lý GET trong servlet này
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

        LogDao logDao = new LogDao();
        String userIp = request.getRemoteAddr();

        try {
            // Ghi log bắt đầu thêm đơn hàng
            logDao.insertLog("INFO", "User id " + userId + " bắt đầu thêm đơn hàng", users.getEmail(), userIp);

            // Lấy thông tin người tiêm
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

            // Ghi log đã thêm người tiêm
            logDao.insertLog("INFO", "Thêm người tiêm: " + namePatient + ", ID: " + idPatient, users.getEmail(), userIp);

            // Lấy thông tin người liên hệ
            String nameContact = request.getParameter("contact_name");
            String relationship = request.getParameter("relationship");
            String phone = request.getParameter("contact_phone");
            ContactPersonDao cpDao = new ContactPersonDao();
            cpDao.insertContact(new ContactPersons(userId, idPatient, nameContact, relationship, phone));

            // Ghi log đã thêm người liên hệ
            logDao.insertLog("INFO", "Thêm người liên hệ: " + nameContact + " cho bệnh nhân ID: " + idPatient, users.getEmail(), userIp);

            // Lấy thông tin đơn hàng
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
            int idOrder = orderDao.insertOrder(new Orders(1, idPatient, idCenter, createdAt, appointmentDate, appointmentTime, status, paymentStatus));

            // Ghi log đã tạo đơn hàng
            logDao.insertLog("INFO", "Tạo đơn hàng ID: " + idOrder + " cho bệnh nhân ID: " + idPatient, users.getEmail(), userIp);

            // Log chi tiết đơn hàng
            Orders order = new Orders(1, idPatient, idCenter, createdAt, appointmentDate, appointmentTime, status, paymentStatus);
            logDao.insertLog("INFO", "Order Details - Patient ID: " + order.getIdPatient() + ", Center ID: " + order.getIdCenter()
                            + ", Created At: " + order.getCreatedAt() + ", Appointment Date: " + order.getAppointmentDate() + ", Appointment Time: " + order.getAppointmentTime(),
                    users.getEmail(), userIp);

            // Lấy chi tiết đơn hàng
            OrderDetailDao odd = new OrderDetailDao();
            String[] selectedPackages = request.getParameterValues("selectedPackages");
            String[] selectedVaccines = request.getParameterValues("selectedVaccines");

            if (selectedPackages != null) {
                for (String packageId : selectedPackages) {
                    logDao.insertLog("INFO", "Chọn gói tiêm: " + packageId, users.getEmail(), userIp);
                }
            }
            if (selectedVaccines != null) {
                for (String vaccineId : selectedVaccines) {
                    logDao.insertLog("INFO", "Chọn vắc xin: " + vaccineId, users.getEmail(), userIp);
                }
            }

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            // Xử lý thêm các gói vắc xin
            if (selectedPackages != null && selectedPackages.length > 0) {
                Map<String, Long> packageCount = Arrays.stream(selectedPackages)
                        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

                for (Map.Entry<String, Long> entry : packageCount.entrySet()) {
                    int packageId = Integer.parseInt(entry.getKey());
                    float packagePrice = odd.getPackagePrice(packageId);
                    OrderDetails orderDetails = new OrderDetails(idOrder, 0, packageId, 1, packagePrice);
                    // Giả sử có insertDetail thật sự:
                    // odd.insertDetail(idOrder, 0, packageId, 1);
                    // Ghi log thêm chi tiết đơn hàng
                    logDao.insertLog("INFO", "Thêm chi tiết đơn hàng: packageId=" + packageId + ", orderId=" + idOrder, users.getEmail(), userIp);
                }
            } else {
                logDao.insertLog("INFO", "Không có gói tiêm được chọn trong đơn hàng.", users.getEmail(), userIp);
            }

            // Xử lý thêm các vaccine lẻ
            if (selectedVaccines != null && selectedVaccines.length > 0) {
                Map<String, Long> vaccineCount = Arrays.stream(selectedVaccines)
                        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

                for (Map.Entry<String, Long> entry : vaccineCount.entrySet()) {
                    int vaccineId = Integer.parseInt(entry.getKey());
                    float vaccinePrice = odd.getVaccinePrice(vaccineId);
                    OrderDetails orderDetails = new OrderDetails(idOrder, vaccineId, 0, 1, vaccinePrice);
                    // odd.insertDetail(idOrder, vaccineId, 0, 1);
                    logDao.insertLog("INFO", "Thêm chi tiết đơn hàng: vaccineId=" + vaccineId + ", orderId=" + idOrder, users.getEmail(), userIp);
                }
            } else {
                logDao.insertLog("INFO", "Không có vắc xin lẻ được chọn trong đơn hàng.", users.getEmail(), userIp);
            }

            session.setAttribute("cart", cart);
            RequestDispatcher dispatcher = request.getRequestDispatcher("shopping_cart.jsp");
            dispatcher.forward(request, response);

            // Ghi log hoàn thành xử lý đơn hàng
            logDao.insertLog("INFO", "Hoàn thành xử lý đơn hàng ID: " + idOrder, users.getEmail(), userIp);

        } catch (SQLException e) {
            throw new ServletException("Lỗi khi thêm đơn hàng", e);
        }
    }
}

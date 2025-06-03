/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.example.provide_vaccine_services.Service.vnpay;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.OrderDetails;
import com.example.provide_vaccine_services.dao.model.Orders;
import com.example.provide_vaccine_services.dao.model.Vaccines;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VnpayReturn extends HttpServlet {
    OrderDao orderDao = new OrderDao();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        try ( PrintWriter out = response.getWriter()) {

            System.out.println("=== THÔNG TIN NHẬN TỪ VNPAY ===");


            Map fields = new HashMap();
            System.out.println("fieldName" + ": " + "fieldValue"); // ✅ log từng param

            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                System.out.println(fieldName + ": " + fieldValue); // ✅ log từng param

                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            System.out.println(">>> Bắt đầu kiểm tra chữ ký HMAC...");


            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);

            System.out.println(">>> Hash từ VNPAY: " + vnp_SecureHash);
            System.out.println(">>> Hash từ hệ thống: " + signValue);


            if (signValue.equals(vnp_SecureHash)) {

                System.out.println(">>> ✅ CHỮ KÝ HỢP LỆ");

                String paymentCode = request.getParameter("vnp_TransactionNo");

                String orderId = request.getParameter("vnp_TxnRef");
                String transStatus = request.getParameter("vnp_TransactionStatus");

                System.out.println(">>> Mã giao dịch: " + paymentCode);
                System.out.println(">>> Mã đơn hàng (TxnRef): " + orderId);
                System.out.println(">>> Trạng thái giao dịch: " + transStatus);


                OrderDao orderDao = new OrderDao();
                Orders order = orderDao.getOrderById(Integer.parseInt(orderId));

                boolean transSuccess = false;

                // kiểm tra thành công hay không
                if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {

                    System.out.println(">>> Giao dịch THÀNH CÔNG");

                    // cập nhật trạng thái thanh toán
                    order.setPaymentStatus("Đã thanh toán");
                    transSuccess = true;
                    VaccineDao vaccineDao = new VaccineDao();
                    System.out.println(">>> Cập nhật số lượng vaccine:");

                    // vaccineID, quantity
                    Map<Integer, Integer> vaccineMap = orderDao.getVaccineAndQuantity(Integer.parseInt(orderId));
                    for (Map.Entry<Integer, Integer> entry : vaccineMap.entrySet()) {
                        System.out.println(" - Vaccine ID: " + entry.getKey() + ", Số lượng: -" + entry.getValue());

                        vaccineDao.updateQuantity(entry.getKey(), 0 - entry.getValue());
                    }


                } else {
                    System.out.println(">>> Giao dịch KHÔNG THÀNH CÔNG (status != 00)");

                    order.setPaymentStatus("Chưa thanh toán");
                }
                orderDao.updateOrderStatus(order);
                request.setAttribute("transResult", transSuccess);
                request.getRequestDispatcher("payment-result.jsp").forward(request, response);
            } else {
                System.out.println(">>> ❌ CHỮ KÝ KHÔNG HỢP LỆ - GIAO DỊCH BỊ TỪ CHỐI");

                //RETURN PAGE ERROR
                System.out.println("GD KO HOP LE (invalid signature)");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);



    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

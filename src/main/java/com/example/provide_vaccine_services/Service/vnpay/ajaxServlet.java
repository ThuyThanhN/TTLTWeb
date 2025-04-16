/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.provide_vaccine_services.Service.vnpay;

import com.example.provide_vaccine_services.dao.ContactPersonDao;
import com.example.provide_vaccine_services.dao.OrderDao;
import com.example.provide_vaccine_services.dao.OrderDetailDao;
import com.example.provide_vaccine_services.dao.PatientDao;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author CTT VNPAY
 */
public class ajaxServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        /**
         * Đầu tiên thêm các thuộc tính ( đơn đặt hàng ) vào database với trạng thái chưa thanh toán
         * ví dụ: Patient, Order, OrderDetail, ...
         *
         * lấy tổng tiền của đơn hàng và id bỏ vào Map
         *
         *  sau đó sử dụng vnpay để thanh toán
         *
         */

        // nếu user chưa đăng nhập thì trả về login
        Users user = (Users) session.getAttribute("user");
        if(user == null) {
            resp.sendRedirect("login");
            return;
        }

        /**
         * có 2 cách thanh toán nên nếu
         * - đặt và thanh toán ngay -> cần tạo order mới và thanh toán
         * - thanh toán từ danh sách đặt lịch -> không cần tạo order mới, chỉ cần thanh toán
         *
         * sau đó lấy ra được Id của Order
         *
         */
        String idParam = req.getParameter("id");
        int idOrder;
        if(idParam == null ||  idParam.isEmpty()) {
            // nếu không có id -> thêm mới vào database
            idOrder = addOrderToDB(session);
        } else {
            // có id -> có thể lấy từ database không cần thêm mới
            idOrder = Integer.parseInt(idParam);
        }

        // nếu id order không tồn tại thì trả về lỗi
        if(idOrder == -1) {
            resp.sendRedirect(".../error404.jsp");
            return;
        }

        // lấy order từ idOrder
        OrderDao orderDao = new OrderDao();
        Orders order = orderDao.getOrderById(idOrder);
        OrderDetailDao odd = new OrderDetailDao();

        // nếu không tim được order thì trả về
        if(order == null) {
            resp.sendRedirect(".../error404.jsp");
            return;
        }


        // lấy tổng tiền thanh toán
        float amountFloat = odd.getOrderPriceByOrderId(order.getId());
        Double amountDouble = (double) amountFloat;


        // thực hiện thanh toán bằng vnPay từ id và amountDouble ( giá tiền )

        // phiên bản
        String vnp_Version = "2.1.0";
        // Lệnh thanh toán
        String vnp_Command = "pay";
        // Loại đơn hàng
        String orderType = "other";

        // Tổng tiền cần thanh toán, chuyển đổi từ double sang long (đơn vị là đồng)
        long amount = (long) (amountDouble*100);
        // Lấy mã ngân hàng
        String bankCode = req.getParameter("bankCode");
        
        // Mã vnp_TxnRef tham chiếu của giao dịch tại hệ thống của merchant. Mã này là duy nhất dùng để phân biệt các đơn hàng gửi sang VNPAY 
        // MÃ NÀY KHÔNG ĐƯỢC TRÙNG NHAU
        String vnp_TxnRef = String.valueOf(order.getId());
        // địa chỉ IP ngươời dùng
        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        // dùng map lưu các thuộc tính để xử lí thanh toán
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        // Nếu có mã ngân hàng, thêm vào tham số
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        // Lấy ngôn ngữ từ yêu cầu, nếu không có thì mặc định là tiếng Việt
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }

        // Đường dẫn trả về sau khi thanh toán
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        // Thêm địa chỉ IP vào tham số
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // thời gian theo múi giờ GMT+7
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);


        // Thêm thời gian hết hạn giao dịch (sau 15 phút )
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);


        // Sắp xếp các trường tham số theo thứ tự
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        // Tạo chuỗi hash và chuỗi truy vấn
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // Tạo URL truy vấn
        String queryUrl = query.toString();
        // Tạo mã bảo mật bằng HMAC SHA-512
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        // Thêm mã bảo mật vào URL truy vấn
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

//        JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));

        // Chuyển hướng người dùng đến URL thanh toán
        resp.sendRedirect(paymentUrl);
    }


    /**
     * phương thức này nhận vào session để thêm Order, Patient, Orderdetails, ContactPerson vào DB
     * sau đó trả về id của Order vừa thêm
     *
     * @param session
     * @return
     *  -1: chưa được thêm vào DB
     *
     */
    public int addOrderToDB( HttpSession session ) {
        int idOrder = -1;
        Users user = (Users) session.getAttribute("user");


        // lấy danh sách sản phẩm trong list card
        List<Integer> listCart = (List<Integer>) session.getAttribute("listCart");

        // chưa có thì tạo listCart mới
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
            //them contact
            ContactPersonDao cpDao = new ContactPersonDao();
            ContactPersons contactPersons = integerContactPersonsMap.get(listCart.get(i));
            cpDao.insertContact(new ContactPersons(user.getId(), idPatient, contactPersons.getFullname(), contactPersons.getRelationship(), contactPersons.getPhone()));

            // them orders
            OrderDao orderDao = new OrderDao();
            int finalI = i;
            int cartItemId = listCart.get(i);
            Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartItemId)
                    .findFirst();
            if (order.isPresent()) {
                Orders foundOrder = order.get();
                idOrder = orderDao.insertOrder(new Orders(listCart.get(i), idPatient, foundOrder.getIdCenter(), foundOrder.getCreatedAt(),
                        foundOrder.getAppointmentDate(), foundOrder.getAppointmentTime(), foundOrder.getStatus(),
                        foundOrder.getPaymentStatus()));
                // them orderdetail
                OrderDetailDao odd = new OrderDetailDao();
                List<OrderDetails> orderDetails = ordersOrderDetailsMap.get(foundOrder);
                for (OrderDetails oddOrderDetail : orderDetails) {
                    int result = odd.insertDetailFull(idOrder, oddOrderDetail.getIdVaccine(), oddOrderDetail.getIdPackage(),
                            oddOrderDetail.getQuantityOrder(), oddOrderDetail.getPrice());
                }
            }
        }

        // bỏ các thuộc tính khỏi session
        session.removeAttribute("listCart");
        session.removeAttribute("integerPatientsMap");
        session.removeAttribute("integerContactPersonsMap");
        session.removeAttribute("ordersOrderDetailsMap");

        return idOrder;
    }

}

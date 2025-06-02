package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.*;
import com.example.provide_vaccine_services.dao.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "DosingSchedule", value = "/dosing_schedule")
public class DosingSchedule extends HttpServlet {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cartId = request.getParameter("cartId");

        int cartIdInt;
        if (isNumeric(cartId)) {
            cartIdInt = Integer.parseInt(cartId);
        } else {
            cartIdInt = 0;
        }
        // moi them
        HttpSession session = request.getSession();
        List<Integer> listCart = (List<Integer>) session.getAttribute("listCart");
        if (listCart != null && listCart.contains(cartIdInt)) {
            for (Integer c : listCart) {
                System.out.println("--cartId: " + c);
            }

            Map<Integer, Patients> integerPatientsMap = (Map<Integer, Patients>) session.getAttribute("integerPatientsMap");
            Patients patient = integerPatientsMap.get(cartIdInt);

            Map<Integer, ContactPersons> integerContactPersonsMap =
                    (Map<Integer, ContactPersons>) session.getAttribute("integerContactPersonsMap");
            ContactPersons contactPerson = integerContactPersonsMap.get(cartIdInt);

            Map<Orders, List<OrderDetails>> ordersOrderDetailsMap =
                    (Map<Orders, List<OrderDetails>>) session.getAttribute("ordersOrderDetailsMap");

            Orders order = null;
            List<OrderDetails> orderDetailsList = null;
            // Tìm Orders với orderId tương ứng
            Optional<Orders> ordersOptional = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartIdInt)
                    .findFirst();
            boolean isSingleVaccine = false;
            boolean isPackageVaccine = false;
            if (ordersOptional.isPresent()) {
                // Lấy danh sách OrderDetails tương ứng
                orderDetailsList = ordersOrderDetailsMap.get(ordersOptional.get());

                // Kiểm tra nếu là vaccine lẻ hoặc gói
                isSingleVaccine = orderDetailsList.stream().allMatch(od -> od.getIdVaccine() != 0);
                isPackageVaccine = orderDetailsList.stream().allMatch(od -> od.getIdPackage() != 0);
                order = ordersOptional.get();
            }

            request.setAttribute("cartId", cartId);
            request.setAttribute("patient", patient);
            request.setAttribute("contact", contactPerson);
            request.setAttribute("order", order);
            request.setAttribute("isSingleVaccine", isSingleVaccine);
            request.setAttribute("isPackageVaccine", isPackageVaccine);
            request.setAttribute("orderDetailsCart", orderDetailsList);
        } else {
            System.out.println("listCart khong ton tai trong session.");
        }

        CenterDao centerDao = new CenterDao();
        VaccineDao vaccine = new VaccineDao();
        AgeGroupDao ageDao = new AgeGroupDao();
        PackageAgeDao paDao = new PackageAgeDao();
        VaccinePackageDao vpaDao = new VaccinePackageDao();

        List<AgeGroups> ages = ageDao.getVaccinePackagesByAge();
        List<Centers> centers = centerDao.getAll();
        List<Vaccines> vaccines = vaccine.getAll();
        List<PackageAges> pas = paDao.getAll();
        List<VaccinePackages> vpas = vpaDao.getAll();

        Map<Integer, Boolean> mapVaccine = new HashMap<>();
        for(VaccinePackages vp : vpas) {
            boolean isValid = vpaDao.isValidpackage(vp.getId());
            mapVaccine.put(vp.getId(), isValid);
        }

        for(Map.Entry<Integer, Boolean> entry : mapVaccine.entrySet()) {
            System.out.println("mapVaccine: " + entry.getKey());
            System.out.println("isValid: " + entry.getValue());
        }

        request.setAttribute("mapVaccine", mapVaccine);
        request.setAttribute("centers", centers);
        request.setAttribute("ages", ages);
        request.setAttribute("vaccines", vaccines);
        request.setAttribute("pas", pas);

        Map<Integer, Integer> vaccineQuantityMap = vaccines.stream()
                .collect(Collectors.toMap(Vaccines::getId, Vaccines::getStockQuantity));



        request.getRequestDispatcher("dosing_schedule.jsp").forward(request, response);
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
        List<Integer> listCart = (List<Integer>) session.getAttribute("listCart");

        String cartIdParam = request.getParameter("cartId");
        System.out.println(request.getQueryString());
        System.out.println(request.getParameterMap());
        int cartId;

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


        //  Nguoi lien he
        String nameContact = request.getParameter("contact_name");
        String relationship = request.getParameter("relationship");
        String phone = request.getParameter("contact_phone");

        // trung tam, thoi gian tiem, phương thức thanh toán
        int idCenter = Integer.parseInt(request.getParameter("center-select"));
        LocalDateTime createdAt = LocalDateTime.now();
        String preferredDate = request.getParameter("preferred_date");
        Date appointmentDate = Date.valueOf(preferredDate);
        String appointmentTime = request.getParameter("vaccination_time");
        String status = "Chưa được duyệt";   // Mặc định là "Chưa được duyệt"
        String paymentStatus = "Chưa thanh toán";;  // Mặc định là "Chưa thanh toán"


        // loai vaccine
        String[] selectedPackages = request.getParameterValues("selectedPackages");
        String[] selectedVaccines = request.getParameterValues("selectedVaccines");

        // nếu đã tồn tại cardID ( không phải đki mới mà là chỉnh sửa/xoá/.... )
        if (cartIdParam != null && isNumeric(cartIdParam)) {
            cartId = Integer.parseInt(cartIdParam);
            Map<Integer, Patients> integerPatientsMap =
                    (Map<Integer, Patients>) session.getAttribute("integerPatientsMap");
            Map<Integer, ContactPersons> integerContactPersonsMap =
                    (Map<Integer, ContactPersons>) session.getAttribute("integerContactPersonsMap");
            Map<Orders, List<OrderDetails>> ordersOrderDetailsMap =
                    (Map<Orders, List<OrderDetails>>) session.getAttribute("ordersOrderDetailsMap");

            Optional<Orders> order = ordersOrderDetailsMap.keySet().stream()
                    .filter(o -> o.getId() == cartId)
                    .findFirst();
            // update item gio hang
            Patients patient = integerPatientsMap.get(cartId);
            patient.setFullname(namePatient);
            patient.setDateOfBirth(dateOfBirth);
            patient.setGender(gender);
            patient.setIdentification(identifier);
            patient.setAddress(address);
            patient.setProvince(province);
            patient.setWard(ward);
            patient.setDistrict(district);
            integerPatientsMap.put(cartId, patient);

            ContactPersons contactPersons = integerContactPersonsMap.get(cartId);
            contactPersons.setFullname(nameContact);
            contactPersons.setRelationship(relationship);
            contactPersons.setPhone(phone);
            integerContactPersonsMap.put(cartId, contactPersons);

            if(order.isPresent()){
                Orders orders = order.get();
                ordersOrderDetailsMap.remove(orders); // xoa item ra khoi map

                orders.setIdCenter(idCenter);
                orders.setAppointmentDate(appointmentDate);
                orders.setAppointmentTime(appointmentTime);

                OrderDetailDao odd = new OrderDetailDao();
//              List<OrderDetails> oddList = ordersOrderDetailsMap.get(orders);
                List<OrderDetails> ordersOrderDetailsList = new ArrayList<>();
                if (selectedPackages != null && selectedPackages.length > 0) {
                    // Đếm số lần xuất hiện của mỗi gói
                    Map<String, Long> packageCount = Arrays.stream(selectedPackages)
                            .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

                    // Lặp qua các gói vắc xin đã chọn và gọi insertDetail
                    for (Map.Entry<String, Long> entry : packageCount.entrySet()) {
                        int packageId = Integer.parseInt(entry.getKey());

                        float packagePrice = odd.getPackagePrice(packageId);

                        ///  4 table
                        OrderDetails orderDetails = new OrderDetails(cartId, 0, packageId, 1, packagePrice);
                        // id cart update id order sau, lay theo id cua session
                        ordersOrderDetailsList.add(orderDetails);
                    }
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

                        // Thêm vào giỏ hàng với giá
                        OrderDetails orderDetails = new OrderDetails(cartId, vaccineId, 0, 1, vaccinePrice);
                        // id cart update id order sau, lay theo id cua session
                        ordersOrderDetailsList.add(orderDetails);
                    }
                }
                ordersOrderDetailsMap.put(orders, ordersOrderDetailsList);
            }
            session.setAttribute("listCart", listCart);
            session.setAttribute("integerPatientsMap", integerPatientsMap);
            session.setAttribute("integerContactPersonsMap", integerContactPersonsMap);
            session.setAttribute("ordersOrderDetailsMap", ordersOrderDetailsMap);
        } else {
            Map<Integer, Patients> integerPatientsMap =
                    (Map<Integer, Patients>) session.getAttribute("integerPatientsMap");
            int idCart = 1;
            // chua co thi tao moi
            if (listCart == null || listCart.isEmpty()) {
                listCart = new ArrayList<Integer>();
                listCart.add(idCart);
                integerPatientsMap = new HashMap<>();
            } else {
                // loop cart kiem tra xem session tao nhung ko co thong tin cart
                for (Integer cartTmp : listCart) {
                    Patients tmpPatient = null;
                    if (integerPatientsMap != null) {
                        tmpPatient = integerPatientsMap.get(cartTmp);
                    } else {
                        integerPatientsMap = new HashMap<>();
                    }
                    if (tmpPatient == null) {
                        listCart.remove(cartTmp.intValue());
                    }
                    // neu ko co patient tuc la ko co thong tin cart thi xoa cartdId khoi list
                }
                int lastCart = listCart.getLast();
                idCart = lastCart + 1; // tang len 1, add them 1 item vao cart
                listCart.add(idCart);
            }
            session.setAttribute("listCart", listCart);

            System.out.println("userId---" + userId);

            /// 1 table
            Patients patient = new Patients(namePatient, dateOfBirth, gender, identifier, address, province, ward, district);
            //Map<Integer, Patients> integerPatientsMap = new HashMap<>();
            integerPatientsMap.put(idCart, patient);
            session.setAttribute("integerPatientsMap", integerPatientsMap);

            ///  2 table
            Map<Integer, ContactPersons> integerContactPersonsMap =
                    (Map<Integer, ContactPersons>) session.getAttribute("integerContactPersonsMap");

            ContactPersons contactPersons = new ContactPersons(userId, 1, nameContact, relationship, phone); // id patient 1 update sau
            // neu chua co contact thi tao map moi, co roi thi se ghi de tiep vao session
            if (integerContactPersonsMap == null) {
                integerContactPersonsMap = new HashMap<>();
            }
            integerContactPersonsMap.put(idCart, contactPersons);
            session.setAttribute("integerContactPersonsMap", integerContactPersonsMap);

            //  Don hang
            Map<Orders, List<OrderDetails>> ordersOrderDetailsMap =
                    (Map<Orders, List<OrderDetails>>) session.getAttribute("ordersOrderDetailsMap");
            // neu chua co order thi tao map moi, co roi thi se ghi de tiep vao session
            if (ordersOrderDetailsMap == null) {
                ordersOrderDetailsMap = new HashMap<>();
            }

            request.setAttribute("date", appointmentDate);
            request.setAttribute("time", appointmentTime);

            Orders orders = new Orders(idCart, 1, idCenter, createdAt, appointmentDate, appointmentTime, status, paymentStatus); // id patient 1 update sau

            //  Don hang chi tiet
            OrderDetailDao odd = new OrderDetailDao();
            List<OrderDetails> ordersOrderDetailsList = new ArrayList<>();
            // Kiểm tra và thêm các gói vắc xin
            if (selectedPackages != null && selectedPackages.length > 0) {
                // Đếm số lần xuất hiện của mỗi gói
                Map<String, Long> packageCount = Arrays.stream(selectedPackages)
                        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

                // Lặp qua các gói vắc xin đã chọn và gọi insertDetail
                for (Map.Entry<String, Long> entry : packageCount.entrySet()) {
                    int packageId = Integer.parseInt(entry.getKey());

                    float packagePrice = odd.getPackagePrice(packageId);

                    ///  4 table
                    OrderDetails orderDetails = new OrderDetails(idCart, 0, packageId, 1, packagePrice);
                    // id cart update id order sau, lay theo id cua session
                    ordersOrderDetailsList.add(orderDetails);
                }
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

                    // Thêm vào giỏ hàng với giá
                    OrderDetails orderDetails = new OrderDetails(idCart, vaccineId, 0, 1, vaccinePrice);
                    // id cart update id order sau, lay theo id cua session
                    ordersOrderDetailsList.add(orderDetails);
                }
            }

            ordersOrderDetailsMap.put(orders, ordersOrderDetailsList);
            session.setAttribute("ordersOrderDetailsMap", ordersOrderDetailsMap);
        }

        // Sau khi thêm OrderDetails vào cart, kiểm tra lại giỏ hàng
        response.sendRedirect("shoppingCart");


    }
}
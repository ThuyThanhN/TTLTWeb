package com.example.provide_vaccine_services.controller;

import com.example.provide_vaccine_services.dao.ProductStockDAO;
import com.example.provide_vaccine_services.dao.TransactionDAO;
import com.example.provide_vaccine_services.dao.VaccineDao;
import com.example.provide_vaccine_services.dao.model.ProductStock;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@WebServlet(name = "ImportExcel", value = "/admin/ImportExcelServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,    // 2MB
        maxFileSize = 1024 * 1024 * 10,         // 10MB
        maxRequestSize = 1024 * 1024 * 50)      // 50MB
public class ImportTransactionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("excelFile"); // Lấy file upload
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();

            try (Workbook workbook = WorkbookFactory.create(inputStream)) {

                // lấy sheet đầu của excel
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // bỏ qua header

                    int vaccineId = (int) getCellNumericValue(row.getCell(1));
                    String type = getCellStringValue(row.getCell(2));
                    type = (type.equals("Nhập")) ? "1" : "2";
                    int quantity = (int) getCellNumericValue(row.getCell(3));
                    Date expiryDate = getCellDateValue(row.getCell(4));

                    System.out.println( vaccineId + ", " + type + ", " + quantity + ", " + expiryDate);


                    // Chuyển Date sang LocalDateTime
                    LocalDateTime expiryDateTime = expiryDate.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    Users user = (Users) request.getSession().getAttribute("user");

                    Transaction t = new Transaction(vaccineId, type, quantity, expiryDateTime, user);

                    addTransactionExcel(t);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
                response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Upload thành công");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Không có file được tải lên");
        }
    }


    // các  hàm thao tác  excel
    private String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
        else if (cell.getCellType() == CellType.NUMERIC) return String.valueOf((int) cell.getNumericCellValue());
        else return "";
    }

    private double getCellNumericValue(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
        else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private Date getCellDateValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        }
        return null;
    }

    private void addTransactionExcel(Transaction t) {

        // thêm transaction
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.insert(t);

        // cộng vào productStock
        VaccineDao vaccineDao = new VaccineDao();
        ProductStockDAO productStockDAO = new ProductStockDAO();
        ProductStock existingStock = null;
        try {
            existingStock = productStockDAO.findByVaccineId(t.getVaccineId());
            int delta = (t.getType().equals("1")) ? t.getQuantity() : -t.getQuantity();
            System.out.println(t.getType());

            if (existingStock != null) {
                System.out.println("Existing stock is not null");
                productStockDAO.updateQuantity(t.getVaccineId(), delta);
            } else {
                System.out.println("Existing stock is null");
                // nếu chưa có, tạo mới. Cần truyền thêm productName và expired từ form hoặc truy DB vaccine
                ProductStock newStock = new ProductStock(
                        t.getVaccineId(),
                        vaccineDao.getVaccineById(t.getVaccineId()).getName(),
                        vaccineDao.getVaccineById(t.getVaccineId()).getPrice() * t.getQuantity(),
                        0,               // loss ban đầu = 0
                        t.getExpiry_date()
                );
                productStockDAO.insert(newStock, delta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

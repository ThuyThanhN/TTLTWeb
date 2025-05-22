package com.example.provide_vaccine_services.dao;

import com.example.provide_vaccine_services.dao.db.DBConnect;
import com.example.provide_vaccine_services.dao.model.ProductStock;
import com.example.provide_vaccine_services.dao.model.Transaction;
import com.example.provide_vaccine_services.dao.model.Vaccines;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductStockDAO {

    public List<ProductStock> getAll() {

        List<ProductStock> productStocks = new ArrayList<ProductStock>();
        try {
            String sql = "select * from productstock";
            PreparedStatement pst = DBConnect.get(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int vaccine_id = rs.getInt("idVaccine");
                String productName = rs.getString("name");
                double totalPrice = rs.getDouble("totalPrice");
                int loss = rs.getInt("loss");
                // Dung Timestamp lay gia tri => Chuyen doi sang LocalDateTime
                Timestamp timestamp = rs.getTimestamp("expired");
                LocalDateTime expired = timestamp != null ? timestamp.toLocalDateTime() : null;

                ProductStock productStock = new ProductStock(vaccine_id, productName,totalPrice, loss, expired);

                productStocks.add(productStock);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productStocks;


    }

    public ProductStock findByVaccineId(int vaccineId) throws SQLException {
        String sql = "SELECT * FROM productstock WHERE idVaccine = ?";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, vaccineId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                ProductStock ps = new ProductStock();
                ps.setVaccineId(rs.getInt("idVaccine"));
                ps.setProductName(rs.getString("name"));
                ps.setTotalPrice(rs.getDouble("totalPrice"));
                ps.setLoss(rs.getInt("loss"));
                ps.setExpired(rs.getTimestamp("expired").toLocalDateTime());
                return ps;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void updateQuantity(int vaccineId, int delta) throws SQLException {
        String sql = "UPDATE productStock SET quantity = quantity + ? WHERE idVaccine = ?";
        try  {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, vaccineId);
            pst.setInt(1, delta);
            pst.setInt(2, vaccineId);
            pst.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(ProductStock stock, int delta) throws SQLException {

        String sql = "INSERT INTO productstock (idVaccine, name, totalPrice, loss, expired) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = DBConnect.get(sql);
            pst.setInt(1, stock.getVaccineId());
            pst.setString(2, stock.getProductName());

            // tính tổng tiền
            VaccineDao vaccineDao = new VaccineDao();
            Vaccines v = vaccineDao.getVaccineById(stock.getVaccineId());
            double totalPrice = v.getPrice() * delta;
            pst.setDouble(3, totalPrice);
            pst.setInt(4, stock.getLoss());
            pst.setTimestamp(6, Timestamp.valueOf(stock.getExpired()));
            pst.executeUpdate();

            vaccineDao.updateQuantity(v.getId(), v.getStockQuantity() + delta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

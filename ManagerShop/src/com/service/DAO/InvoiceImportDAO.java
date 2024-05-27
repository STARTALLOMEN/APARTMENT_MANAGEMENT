package com.service.DAO;

import com.service.entity.InvoiceImport;
import com.service.helper.jdbcHelper;
import com.service.utils.XDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceImportDAO extends ShopDAO<InvoiceImport, Integer> {

    @Override
    public void insert(InvoiceImport e) {
        String sql = "INSERT INTO `InvoiceImportPr`(`dateCreateInvoice`, `statusPay`, `idAdmin`, `idSupplier`, `description`) VALUES(?, ?, ?, ?, ?)";
        jdbcHelper.update(sql, e.getDateCreate(), e.isStatusPay(), e.getIdUser(), e.getIdSupplier(), e.getDesc());
    }

    @Override
    public void update(InvoiceImport e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceImport> selectAll() {
        String sql = "SELECT I.*, `name`, S.`nameMaterial` FROM `InvoiceImportPr` I JOIN `User` U ON U.`idUser` = I.`idAdmin`"
                + "JOIN `Supplier` S ON S.`idSupplier` = I.`idSupplier` ORDER BY `idInvoice` DESC";
        return selectBySql(sql);
    }

    @Override
    public InvoiceImport selectById(Integer k) {
        String sql = "SELECT I.*, `name`, S.`nameMaterial` FROM `InvoiceImportPr` I JOIN `User` U ON U.`idUser` = I.`idAdmin`"
                + "JOIN `Supplier` S ON S.`idSupplier` = I.`idSupplier` WHERE I.`idInvoice` = ?";
        List<InvoiceImport> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<InvoiceImport> selectBySql(String sql, Object... args) {
        List<InvoiceImport> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceImport i = new InvoiceImport();
                i.setDateCreate(rs.getString("dateCreateInvoice"));
                i.setStatusPay(rs.getBoolean("statusPay"));
                i.setIdUser(rs.getInt("idAdmin"));
                i.setIdSupplier(rs.getInt("idSupplier"));
                i.setDesc(rs.getString("description"));
                i.setId(rs.getInt("idInvoice"));
                i.setNameUser(rs.getString("name"));
                i.setNameSupplier(rs.getString("nameMaterial"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalPage(String Stringdate) {
        ResultSet rs;
        if (!Stringdate.isEmpty()) {
            Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT COUNT(*) AS `soLuong` FROM `InvoiceImportPr` WHERE `dateCreateInvoice` BETWEEN ? AND ?";
            try {
                rs = jdbcHelper.query(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 86399999));
                while (rs.next()) {
                    return rs.getInt("soLuong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "SELECT COUNT(*) AS `soLuong` FROM `InvoiceImportPr`";
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<InvoiceImport> pagingPage(int page, int pageSize, String Stringdate) {
        if (!Stringdate.isEmpty()) {
            Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT I.*, `name`, S.`nameMaterial` FROM `InvoiceImportPr` I JOIN `User` U ON U.`idUser` = I.`idAdmin`"
                    + "JOIN `Supplier` S ON S.`idSupplier` = I.`idSupplier` WHERE `dateCreateInvoice` BETWEEN ? AND ?"
                    + "ORDER BY I.`idInvoice` DESC"
                    + "LIMIT ?, ?";
            return selectBySql(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 86399999), (page - 1) * pageSize, pageSize);
        }
        String sql = "SELECT I.*, `name`, S.`nameMaterial` FROM `InvoiceImportPr` I JOIN `User` U ON U.`idUser` = I.`idAdmin`"
                + "JOIN `Supplier` S ON S.`idSupplier` = I.`idSupplier` ORDER BY I.`idInvoice` DESC"
                + "LIMIT ?, ?";
        return selectBySql(sql, (page - 1) * pageSize, pageSize);
    }

    public Float getTotalMoney(Integer idInvoice) {
        String sql = "SELECT `idInvoice`, SUM(D.`quatity` * D.`priceImport`) AS `totalCash` FROM `detailsInvoiceImportPr` D"
                + "GROUP BY `idInvoice`"
                + "HAVING `idInvoice` = ?";
        try {
            ResultSet rs = jdbcHelper.query(sql, idInvoice);
            while (rs.next()) {
                return rs.getFloat("totalCash");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//public List<InvoiceImport> fillDate(java.util.Date date) {
//    String sql = "SELECT I.*, U.`name`, S.`nameMaterial` " +
//                 "FROM `InvoiceImportPr` I " +
//                 "JOIN `User` U ON U.`idUser` = I.`idAdmin` " +
//                 "JOIN `Supplier` S ON S.`idSupplier` = I.`idSupplier` " +
//                 "WHERE DATE(I.`dateCreateInvoice`) = ? " +
//                 "ORDER BY I.`idInvoice` DESC";
//    return selectBySql(sql, new java.sql.Date(date.getTime()));
}


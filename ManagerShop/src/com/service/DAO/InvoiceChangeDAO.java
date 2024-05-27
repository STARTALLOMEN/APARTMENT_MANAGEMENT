package com.service.DAO;

import com.service.entity.InvoiceChange;
import com.service.helper.jdbcHelper;
import com.service.utils.XDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceChangeDAO extends ShopDAO<InvoiceChange, Integer> {

    @Override
    public void insert(InvoiceChange e) {
        String sql = "INSERT INTO `InvoiceChangeProducts`(`idInvoiceSell`, `idCustomer`, `description`, `idUser`, `dateCreateInvoice`) VALUES(?, ?, ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdInvoiceSell(), e.getIdCustomer(), e.getDescription(), e.getIdUser(), e.getDateCreateInvoiceReturn());
    }

    @Override
    public void update(InvoiceChange e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceChange> selectAll() {
        String sql = "SELECT * FROM `InvoiceChangeProducts` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceChangeProducts`.`idCustomer` ORDER BY `idInvoiceChangeProducts` DESC";
        return selectBySql(sql);
    }

    @Override
    public InvoiceChange selectById(Integer k) {
        String sql = "SELECT * FROM `InvoiceChangeProducts` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceChangeProducts`.`idCustomer` WHERE `InvoiceChangeProducts`.`idInvoiceChangeProducts` = ?";
        List<InvoiceChange> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<InvoiceChange> selectBySql(String sql, Object... args) {
        List<InvoiceChange> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceChange p = new InvoiceChange();
                p.setId(rs.getInt("idInvoiceChangeProducts"));
                p.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                p.setDateCreateInvoiceReturn(rs.getString("dateCreateInvoice"));
                p.setIdCustomer(rs.getInt("idCustomer"));
                p.setDescription(rs.getString("description"));
                p.setNameCustomer(rs.getString("name"));
                p.setIdUser(rs.getInt("idUser"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalPage(String Stringdate) {
        ResultSet rs;
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT COUNT(*) AS `soLuong` FROM `InvoiceChangeProducts` WHERE `dateCreateInvoice` BETWEEN ? AND ?";
            try {
                rs = jdbcHelper.query(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 86399999));
                while (rs.next()) {
                    return rs.getInt("soLuong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "SELECT COUNT(*) AS `soLuong` FROM `InvoiceChangeProducts`";
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

    public List<InvoiceChange> pagingPage(int page, int pageSize, String Stringdate) {
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT * FROM `InvoiceChangeProducts` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceChangeProducts`.`idCustomer`"
                    + "WHERE `dateCreateInvoice` BETWEEN ? AND ?"
                    + "ORDER BY `idInvoiceChangeProducts` DESC"
                    + "LIMIT ?, ?";
            return selectBySql(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 86399999), (page - 1) * pageSize, pageSize);
        }
        String sql = "SELECT * FROM `InvoiceChangeProducts` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceChangeProducts`.`idCustomer`"
                + "ORDER BY `idInvoiceChangeProducts` DESC"
                + "LIMIT ?, ?";
        return selectBySql(sql, (page - 1) * pageSize, pageSize);
    }
}
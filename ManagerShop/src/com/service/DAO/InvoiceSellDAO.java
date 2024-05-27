
package com.service.DAO;

import com.service.entity.InvoiceSell;
import com.service.helper.jdbcHelper;
import com.service.utils.XDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class InvoiceSellDAO extends ShopDAO<InvoiceSell, Integer> {

    @Override
    public void insert(InvoiceSell e) {
        String sql = "INSERT INTO `InvoiceSell`"
                + "(`idCustomer`, `idHumanSell`, `idVoucher`, `dateCreateInvoice`, `description`, `statusPay`, `statusInvoice`, `totalMoney`, `moneyCustom`, `moneyReturn`)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdCustomer(), e.getIdHumanSell(), e.getIdVoucher(), e.getDateCreateInvoice(), e.getDescription(), e.isStatusPay(), e.isStatusInvoice(), e.getPrice(), e.getMoneyCustomer(), e.getMoneyReturn());
    }

    @Override
    public void update(InvoiceSell e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceSell> selectAll() {
        String sql = "SELECT * FROM `InvoiceSell` JOIN `User` ON `User`.`idUser` = `InvoiceSell`.`idHumanSell` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceSell`.`idCustomer` ORDER BY `idInvoiceSell` DESC";
        return selectBySql(sql);
    }

    @Override
    public InvoiceSell selectById(Integer k) {
        String sql = "SELECT * FROM `InvoiceSell` JOIN `User` ON `User`.`idUser` = `InvoiceSell`.`idHumanSell` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceSell`.`idCustomer`"
                + "WHERE `idInvoiceSell` = ?";
        List<InvoiceSell> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<InvoiceSell> selectBySql(String sql, Object... args) {
        List<InvoiceSell> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceSell i = new InvoiceSell();
                i.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                i.setIdCustomer(rs.getInt("idCustomer"));
                i.setIdHumanSell(rs.getInt("idHumanSell"));
                i.setIdVoucher(rs.getInt("idVoucher"));
                i.setDateCreateInvoice(rs.getString("dateCreateInvoice"));
                i.setDescription(rs.getString("description"));
                i.setStatusPay(rs.getBoolean("statusPay"));
                i.setStatusInvoice(rs.getBoolean("statusInvoice"));
                i.setNameCustomer(rs.getString("name"));
                i.setNameUser(rs.getString("name"));
                i.setPrice(rs.getDouble("totalMoney"));
                i.setMoneyCustomer(rs.getDouble("moneyCustom"));
                i.setMoneyReturn(rs.getDouble("moneyReturn"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Float getTotalMoney(Integer idInvoice) {
        String sql = "SELECT `idInvoiceSell`, SUM(`detailsInvoiceSELL`.`quatity` * `price`) AS `Total`"
                + "FROM `detailsInvoiceSELL`"
                + "GROUP BY `idInvoiceSell`"
                + "HAVING `idInvoiceSell` = ?";
        try {
            ResultSet rs = jdbcHelper.query(sql, idInvoice);
            while (rs.next()) {
                return rs.getFloat("Total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int totalPage(String Stringdate) {
        ResultSet rs;
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT COUNT(*) AS `soLuong` FROM `InvoiceSell` WHERE `dateCreateInvoice` BETWEEN ? AND ?";
            try {
                rs = jdbcHelper.query(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 86399999));
                while (rs.next()) {
                    return rs.getInt("soLuong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "SELECT COUNT(*) AS `soLuong` FROM `InvoiceSell`";
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

    public List<InvoiceSell> pagingPage(int page, int pageSize, String Stringdate) {
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT * FROM `InvoiceSell` JOIN `User` ON `User`.`idUser` = `InvoiceSell`.`idHumanSell` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceSell`.`idCustomer`"
                    + "WHERE `dateCreateInvoice` BETWEEN ? AND ?"
                    + "ORDER BY `idInvoiceSell` DESC"
                    + "LIMIT ?, ?";
            return selectBySql(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 86399999), (page - 1) * pageSize, pageSize);
        }
        String sql = "SELECT * FROM `InvoiceSell` JOIN `User` ON `User`.`idUser` = `InvoiceSell`.`idHumanSell` JOIN `Customer` ON `Customer`.`idCustomer` = `InvoiceSell`.`idCustomer`"
                + "ORDER BY `idInvoiceSell` DESC"
                + "LIMIT ?, ?";
        return selectBySql(sql, (page - 1) * pageSize, pageSize);
    }

//    public List<Integer> selectByMonths(int month) {
//        String sql = "SELECT * FROM dbo.InvoiceSell WHERE MONTH(dateCreateInvoice) = ?";
//        return selectBySql(sql, month);
//    }
//    String sql = " SELECT * FROM dbo.InvoiceSell JOIN dbo.[User] ON [User].idUser = InvoiceSell.idHumanSell JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer\n"
//            + "                 WHERE  dateCreateInvoice BETWEEN '" + date + "00:00:00.000'" + "AND '" + date + "23:59:59.000'"
//            + "order by idInvoiceSell desc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
//
//    String sql = " SELECT * FROM dbo.InvoiceSell JOIN dbo.[User] ON [User].idUser = InvoiceSell.idHumanSell JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer \n"
//            + " where dateCreateInvoice =?\n"
//            + "order by idInvoiceSell desc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
}

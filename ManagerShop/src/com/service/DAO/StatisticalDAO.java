
package com.service.DAO;

import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class StatisticalDAO {

    List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getSalesStatisticalDAO(Integer year, Integer month) {
        String sql = "{CALL sp_statistical(?,?)}";
        String[] cols = {"idProduct", "nameProduct", "quantitySell"};
        return getListOfArray(sql, cols, year, month);
    }

    public List<Object[]> getSalesStatisticalRevenue(Integer year) {
        String sql = "{CALL sp_revenue(?)}";
        String[] cols = {"MonthDate", "quantity", "totalSell", "totalReturn", "revenue"};
        return getListOfArray(sql, cols, year);
    }

    public List<Object[]> getQuantityBuy() {
        String sql = "{CALL sp_Quantity}";
        String[] cols = {"name", "gender", "phoneNumber", "sumBuy"};
        return getListOfArray(sql, cols);
    }

    public int getSumCustomer() {
        ResultSet rs;
        String sql = "SELECT COUNT(idCustomer) AS SumCustomer FROM Customer";
        try {
            rs = jdbcHelper.query(sql);
            if (rs.next()) {
                return rs.getInt("SumCustomer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getRevenueDate() {
        ResultSet rs;
        String sql = "SELECT COALESCE(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) - SUM(COALESCE(totalReturn, 0)), SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity)) AS revenue "
                + "FROM detailsInvoiceSELL "
                + "JOIN InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell "
                + "LEFT JOIN InvoiceReturn ON InvoiceReturn.idInvoiceSell = InvoiceSell.idInvoiceSell "
                + "WHERE YEAR(InvoiceSell.dateCreateInvoice) = YEAR(CURRENT_DATE()) "
                + "AND MONTH(InvoiceSell.dateCreateInvoice) = MONTH(CURRENT_DATE()) "
                + "AND DAY(InvoiceSell.dateCreateInvoice) = DAY(CURRENT_DATE())";
        try {
            rs = jdbcHelper.query(sql);
            if (rs.next()) {
                return rs.getInt("revenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getInventory() {
        ResultSet rs;
        String sql = "SELECT SUM(quatity) AS inventory FROM detailsProduct";
        try {
            rs = jdbcHelper.query(sql);
            if (rs.next()) {
                return rs.getInt("inventory");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getQuantityDate() {
        ResultSet rs;
        String sql = "SELECT SUM(quatity) AS Quantity "
                + "FROM InvoiceSell "
                + "JOIN detailsInvoiceSELL ON detailsInvoiceSELL.idInvoiceSell = InvoiceSell.idInvoiceSell "
                + "WHERE YEAR(dateCreateInvoice) = YEAR(CURRENT_DATE()) "
                + "AND MONTH(dateCreateInvoice) = MONTH(CURRENT_DATE()) "
                + "AND DAY(dateCreateInvoice) = DAY(CURRENT_DATE())";
        try {
            rs = jdbcHelper.query(sql);
            if (rs.next()) {
                return rs.getInt("Quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT YEAR(dateCreateInvoice) FROM InvoiceSell ORDER BY YEAR(dateCreateInvoice) DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> selectMonths(int year) {
        String sql = "SELECT MONTH(dateCreateInvoice) FROM InvoiceSell WHERE YEAR(dateCreateInvoice) = ? "
                + "GROUP BY MONTH(dateCreateInvoice)";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, year);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getSelectImport(int month, int year) throws Exception {
        String sql = "SELECT COALESCE(SUM(quatity * priceImport), 0) AS moneyImport "
                + "FROM InvoiceImportPr "
                + "JOIN detailsInvoiceImportPr ON detailsInvoiceImportPr.idInvoice = InvoiceImportPr.idInvoice "
                + "WHERE MONTH(dateCreateInvoice) = ? AND YEAR(dateCreateInvoice) = ? "
                + "GROUP BY MONTH(dateCreateInvoice)";
        int moneyImport = 0;
        ResultSet rs = jdbcHelper.query(sql, month, year);
        if (rs.next()) {
            moneyImport = rs.getInt("moneyImport");
        }
        rs.getStatement().getConnection().close();
        return moneyImport;
    }
}
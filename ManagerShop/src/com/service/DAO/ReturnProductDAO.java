package com.service.DAO;

import com.service.entity.InvoiceRetuns;
import com.service.entity.ProductItem;
import com.service.helper.jdbcHelper;
import com.service.utils.XDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReturnProductDAO extends ShopDAO<InvoiceRetuns, Integer> {

    @Override
    public void insert(InvoiceRetuns e) {
        String sql = "INSERT INTO InvoiceReturn(idInvoiceSell, idCustomer, description, totalReturn, idUser, dateCreateInvoice) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdInvoiceSell(), e.getIdCustomer(), e.getDescription(), e.getTotalReturn(), e.getIdUser(), e.getDateCreateInvoiceReturn());
    }

    @Override
    public void update(InvoiceRetuns e) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceRetuns> selectAll() {
        String sql = "SELECT * FROM InvoiceReturn JOIN Customer ON Customer.idCustomer = InvoiceReturn.idCustomer ORDER BY idInvoiceReturn DESC";
        return selectBySql(sql);
    }

    @Override
    public InvoiceRetuns selectById(Integer k) {
        String sql = "SELECT * FROM InvoiceReturn JOIN Customer ON Customer.idCustomer = InvoiceReturn.idCustomer WHERE idInvoiceReturn = ?";
        List<InvoiceRetuns> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<InvoiceRetuns> selectBySql(String sql, Object... args) {
        List<InvoiceRetuns> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceRetuns p = new InvoiceRetuns();
                p.setIdInvoiceRetuns(rs.getInt("idInvoiceReturn"));
                p.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                p.setDateCreateInvoiceReturn(rs.getString("dateCreateInvoice"));
                p.setIdCustomer(rs.getInt("idCustomer"));
                p.setTotalReturn(rs.getDouble("totalReturn"));
                p.setDescription(rs.getString("description"));
                p.setNameCustomer(rs.getString("name"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    protected List<ProductItem> selectBySql1(String sql, Object... args) {
        List<ProductItem> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ProductItem p = new ProductItem();
                p.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                p.setId(rs.getInt("idPrDetails"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quatity"));
                p.setSize(rs.getString("valueSize"));
                p.setColor(rs.getString("valueColor"));
                p.setMaterial(rs.getString("valueMaterial"));
                p.setProductName(rs.getString("nameProduct"));
                p.setNameCustomer(rs.getString("name"));
                p.setIdCustomer(rs.getInt("idCustomer"));
                p.setDateCreateInvoice(rs.getString("dateCreateInvoice"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ProductItem> selectByIdInvoiceReturn(int id) {
        String sql = "SELECT InvoiceSell.idInvoiceSell, idPrDetails, nameProduct, detailsInvoiceSELL.quatity, valueSize, valueColor, valueMaterial, detailsInvoiceSELL.price, name, Customer.idCustomer, dateCreateInvoice " +
                     "FROM detailsInvoiceSELL " +
                     "JOIN InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell " +
                     "JOIN Customer ON Customer.idCustomer = InvoiceSell.idCustomer " +
                     "JOIN detailsProduct ON detailsProduct.idPrDetails = detailsInvoiceSELL.idPrDetails " +
                     "JOIN Products ON Products.idProduct = detailsProduct.idProduct " +
                     "JOIN Size ON Size.idSize = detailsProduct.idSize " +
                     "JOIN Color ON Color.idColor = detailsProduct.idColor " +
                     "JOIN Material ON Material.idMaterial = detailsProduct.idMaterial " +
                     "WHERE detailsInvoiceSELL.idInvoiceSell = ? AND detailsInvoiceSELL.quatity > 0";

        return selectBySql1(sql, id);
    }

    public void sellProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsInvoiceSELL SET quatity = quatity - ? WHERE idInvoiceSell = ?";
        jdbcHelper.update(sql, quantity, id);
    }

    public int totalPage(String Stringdate) {
        ResultSet rs;
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT COUNT(*) AS soLuong FROM InvoiceReturn WHERE dateCreateInvoice BETWEEN ? AND ?";
            try {
                rs = jdbcHelper.query(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 24 * 60 * 60 * 1000 - 1));
                while (rs.next()) {
                    return rs.getInt("soLuong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "SELECT COUNT(*) AS soLuong FROM InvoiceReturn";
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

    public List<InvoiceRetuns> pagingPage(int page, int pageSize, String Stringdate) {
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT * FROM InvoiceReturn JOIN Customer ON Customer.idCustomer = InvoiceReturn.idCustomer " +
                         "WHERE dateCreateInvoice BETWEEN ? AND ? " +
                         "ORDER BY idInvoiceReturn DESC LIMIT ? OFFSET ?";
            return selectBySql(sql, new java.sql.Timestamp(date.getTime()), new java.sql.Timestamp(date.getTime() + 24 * 60 * 60 * 1000 - 1), pageSize, (page - 1) * pageSize);
        }
        String sql = "SELECT * FROM InvoiceReturn JOIN Customer ON Customer.idCustomer = InvoiceReturn.idCustomer " +
                     "ORDER BY idInvoiceReturn DESC LIMIT ? OFFSET ?";
        return selectBySql(sql, pageSize, (page - 1) * pageSize);
    }
}

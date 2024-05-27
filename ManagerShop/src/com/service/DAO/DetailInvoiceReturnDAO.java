
package com.service.DAO;

import com.service.entity.DetailInvoiceReturn;
import com.service.helper.jdbcHelper;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class DetailInvoiceReturnDAO extends ShopDAO<DetailInvoiceReturn, Integer> {

    @Override
    public void insert(DetailInvoiceReturn e) {
        String sql = "INSERT INTO DetailInvoiceReturn "
                + "(idInvoiceReturn, idPrDetails, quatity, price) "
                + "VALUES "
                + "((SELECT idInvoiceReturn FROM InvoiceReturn ORDER BY idInvoiceReturn DESC LIMIT 1), ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdPrDetails(), e.getQuatity(), e.getPrice());
    }

    @Override
    public void update(DetailInvoiceReturn e) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailInvoiceReturn> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailInvoiceReturn selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailInvoiceReturn> selectBySql(String sql, Object... args) {
        List<DetailInvoiceReturn> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailInvoiceReturn de = new DetailInvoiceReturn();
                de.setIdDetailInvoiceReturn(rs.getInt("idDetailInvoiceReturn"));
                de.setQuatity(rs.getInt("quatity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setNameCustomer(rs.getString("name"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailInvoiceReturn> selectByIdInvoice(int id) {
        String sql = "SELECT * FROM InvoiceReturn "
                + "JOIN DetailInvoiceReturn ON DetailInvoiceReturn.idInvoiceReturn = InvoiceReturn.idInvoiceReturn "
                + "JOIN Customer ON Customer.idCustomer = InvoiceReturn.idCustomer "
                + "JOIN detailsProduct ON detailsProduct.idPrDeltails = DetailInvoiceReturn.idPrDetails "
                + "JOIN Products ON Products.idProduct = detailsProduct.idProduct "
                + "JOIN Size ON Size.idSize = detailsProduct.idSize "
                + "JOIN Material ON Material.idMaterial = detailsProduct.idMaterial "
                + "JOIN Color ON Color.idColor = detailsProduct.idColor "
                + "WHERE DetailInvoiceReturn.idInvoiceReturn = ?";
        return selectBySql(sql, id);
    }
}
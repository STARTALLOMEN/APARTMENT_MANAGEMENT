
package com.service.DAO;

import com.service.entity.DetailInvoiceSell;
import com.service.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DetailInvoiceSellDAO extends ShopDAO<DetailInvoiceSell, Integer> {

    @Override
    public void insert(DetailInvoiceSell e) {
        String sql = "INSERT INTO detailsInvoiceSELL\n "
                + "(idInvoiceSell, idPrDetails, quatity, price)\n"
                + "VALUES\n"
                + "((SELECT idInvoiceSell FROM InvoiceSell ORDER BY idInvoiceSell DESC LIMIT 1), ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdPrDetails(), e.getQuantity(), e.getPrice());
    }

    @Override
    public void update(DetailInvoiceSell e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailInvoiceSell> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailInvoiceSell selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailInvoiceSell> selectBySql(String sql, Object... args) {
        List<DetailInvoiceSell> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailInvoiceSell de = new DetailInvoiceSell();
                de.setIdDetailsInvoiceSell(rs.getInt("idDetailsInvoiceSell"));
                de.setQuantity(rs.getInt("quatity"));
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

    public List<DetailInvoiceSell> selectByIdInvoice(int id) {
        String sql = "SELECT idDetailsInvoiceSell, nameProduct, name, valueSize, valueColor, valueMaterial, detailsInvoiceSell.quatity, detailsInvoiceSell.price " +
                     "FROM detailsInvoiceSell " +
                     "JOIN InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSell.idInvoiceSell " +
                     "JOIN Customer ON Customer.idCustomer = InvoiceSell.idCustomer " +
                     "JOIN detailsProduct ON detailsProduct.idPrDeltails = detailsInvoiceSell.idPrDetails " +
                     "JOIN Products ON Products.idProduct = detailsProduct.idProduct " +
                     "JOIN Size ON Size.idSize = detailsProduct.idSize " +
                     "JOIN Color ON Color.idColor = detailsProduct.idColor " +
                     "JOIN Material ON Material.idMaterial = detailsProduct.idMaterial " +
                     "WHERE detailsInvoiceSell.idInvoiceSell = ?";
        return selectBySql(sql, id);
    }
}

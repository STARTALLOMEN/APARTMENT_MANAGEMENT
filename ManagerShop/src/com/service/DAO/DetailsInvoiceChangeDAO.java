
package com.service.DAO;

import com.service.entity.DetailsInvoiceChange;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetailsInvoiceChangeDAO extends ShopDAO<DetailsInvoiceChange, Integer> {

    @Override
    public void insert(DetailsInvoiceChange e) {
        String sql = "INSERT INTO DetailsInvoiceChange "
                + "(idInvoiceChangeProducts, idDetailsPr, quantity) "
                + "VALUES((SELECT idInvoiceChangeProducts FROM InvoiceChangeProducts ORDER BY idInvoiceChangeProducts DESC LIMIT 1), ?, ?)";
        jdbcHelper.update(sql, e.getIdProductItem(), e.getQuantity());
    }

    @Override
    public void update(DetailsInvoiceChange e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DetailsInvoiceChange> selectAll() {
        String sql = "SELECT * FROM DetailsInvoiceChange De "
                + "JOIN InvoiceChangeProducts I ON I.idInvoiceChangeProducts = De.idInvoiceChangeProducts "
                + "INNER JOIN detailsProduct D ON De.idDetailsPr = D.idPrDeltails "
                + "INNER JOIN Size S ON D.idSize = S.idSize "
                + "INNER JOIN Material M ON M.idMaterial = D.idMaterial "
                + "INNER JOIN Color C ON C.idColor = D.idColor "
                + "INNER JOIN Products P ON P.idProduct = D.idProduct";
        return selectBySql(sql);
    }

    @Override
    public DetailsInvoiceChange selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected List<DetailsInvoiceChange> selectBySql(String sql, Object... args) {
        List<DetailsInvoiceChange> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailsInvoiceChange de = new DetailsInvoiceChange();
                de.setIdInvoiceChange(rs.getInt("idInvoiceChangeProducts"));
                de.setQuantity(rs.getInt("quantity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setId(rs.getInt("id"));
                de.setIdProductItem(rs.getInt("idDetailsPr"));
                de.setNameProduct(rs.getString("nameProduct"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailsInvoiceChange> selectByIdInvoiceChange(Integer k) {
        String sql = "SELECT * FROM DetailsInvoiceChange De "
                + "JOIN InvoiceChangeProducts I ON I.idInvoiceChangeProducts = De.idInvoiceChangeProducts "
                + "INNER JOIN detailsProduct D ON De.idDetailsPr = D.idPrDeltails "
                + "INNER JOIN Size S ON D.idSize = S.idSize "
                + "INNER JOIN Material M ON M.idMaterial = D.idMaterial "
                + "INNER JOIN Color C ON C.idColor = D.idColor "
                + "INNER JOIN Products P ON P.idProduct = D.idProduct "
                + "WHERE De.idInvoiceChangeProducts = ?";
        return selectBySql(sql, k);
    }
}
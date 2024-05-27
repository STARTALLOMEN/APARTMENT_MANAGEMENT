/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.DAO;


import com.service.entity.DetailsChangeProducts;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DetailsChangeProductDAO extends ShopDAO<DetailsChangeProducts, Integer> {

    @Override
    public void insert(DetailsChangeProducts e) {
        String sql = "INSERT INTO DetailsChangeProducts "
                + "(idDetailsInvoiceChange, idDetailsPr, quantity) "
                + "VALUES((SELECT id FROM DetailsInvoiceChange ORDER BY id DESC LIMIT 1), ?, ?)";
        jdbcHelper.update(sql, e.getIdProductItem(), e.getQuantity());
    }

    @Override
    public void update(DetailsChangeProducts e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DetailsChangeProducts> selectAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DetailsChangeProducts selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected List<DetailsChangeProducts> selectBySql(String sql, Object... args) {
        List<DetailsChangeProducts> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailsChangeProducts de = new DetailsChangeProducts();
                de.setIdDetailsInvoiceChange(rs.getInt("idDetailsInvoiceChange"));
                de.setQuantity(rs.getInt("quantity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setIdProductItem(rs.getInt("idDetailsPr"));
                de.setId(rs.getInt("id"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailsChangeProducts> selectByIdDetailsInvoiceChange(Integer k) {
        String sql = "SELECT * FROM DetailsChangeProducts De "
                + "INNER JOIN detailsProduct D ON De.idDetailsPr = D.idPrDeltails "
                + "INNER JOIN Size S ON D.idSize = S.idSize "
                + "INNER JOIN Material M ON M.idMaterial = D.idMaterial "
                + "INNER JOIN Color C ON C.idColor = D.idColor "
                + "INNER JOIN Products P ON P.idProduct = D.idProduct "
                + "WHERE De.idDetailsInvoiceChange = ?";
        return selectBySql(sql, k);
    }
}
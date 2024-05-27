
package com.service.DAO;

import com.service.entity.DetailInvoiceImport;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DetailInvoiceImportDAO extends ShopDAO<DetailInvoiceImport, Integer> {

    @Override
    public void insert(DetailInvoiceImport e) {
        String sql = "INSERT INTO detailsInvoiceImportPr(idInvoice, idPrDeltails, quatity, priceImport) "
                + "VALUES((SELECT idInvoice FROM InvoiceImportPr ORDER BY idInvoice DESC LIMIT 1), ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdProductItem(), e.getQuantity(), e.getPrice());
    }

    @Override
    public void update(DetailInvoiceImport e) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailInvoiceImport> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailInvoiceImport selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailInvoiceImport> selectBySql(String sql, Object... args) {
        List<DetailInvoiceImport> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailInvoiceImport de = new DetailInvoiceImport();
                de.setId(rs.getInt("detailsInvoice"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setValueSize(rs.getString("valueSize"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setQuantity(rs.getInt("quatity"));
                de.setPrice(rs.getInt("priceImport"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailInvoiceImport> selectByIdInvoice(Integer idInvoice) {
        String sql = "SELECT D.detailsInvoice, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, D.quatity, D.priceImport "
                + "FROM detailsInvoiceImportPr D "
                + "JOIN detailsProduct De ON De.idPrDeltails = D.idPrDeltails "
                + "JOIN Products P ON De.idProduct = P.idProduct "
                + "JOIN Size S ON S.idSize = De.idSize "
                + "JOIN Color C ON C.idColor = De.idColor "
                + "JOIN Material M ON M.idMaterial = De.idMaterial "
                + "WHERE D.idInvoice = ?";
        return selectBySql(sql, idInvoice);
    }
}
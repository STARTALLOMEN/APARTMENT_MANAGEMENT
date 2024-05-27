
package com.service.DAO;

import com.service.entity.ProductItemImage;
import com.service.helper.jdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductItemImageDAO extends ShopDAO<ProductItemImage, Integer> {

    @Override
    public void insert(ProductItemImage e) {
        String sql = "INSERT INTO ImageProducts (valueImage) VALUES (?)";
        jdbcHelper.update(sql, e.getValue());
    }

    @Override
    public void update(ProductItemImage e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ProductItemImage> selectAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProductItemImage selectById(Integer k) {
        String sql = "SELECT * FROM Products JOIN List ON List.idList = Products.idList WHERE idProduct = ?";
        List<ProductItemImage> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ProductItemImage> selectBySql(String sql, Object... args) {
        List<ProductItemImage> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ProductItemImage p = new ProductItemImage();
                p.setIdProductItem(rs.getInt("idPrDeltails"));
                p.setValue(rs.getString("valueImage"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
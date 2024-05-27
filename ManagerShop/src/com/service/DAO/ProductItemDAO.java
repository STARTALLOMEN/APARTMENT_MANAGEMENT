package com.service.DAO;

import com.service.entity.ProductItem;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductItemDAO extends ShopDAO<ProductItem, Integer> {

    @Override
    public void insert(ProductItem e) {
        String sql = "INSERT INTO detailsProduct(idProduct, idSize, idColor, idMaterial, price, quatity, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdProduct(), e.getIdSize(), e.getIdColor(), e.getIdMaterial(), e.getPrice(), e.getQuantity(), e.isStatus());
    }

    @Override
    public void update(ProductItem e) {
        String sql = "UPDATE detailsProduct SET idSize = ?, idColor = ?, idMaterial = ?, price = ? WHERE idPrDeltails = ?";
        jdbcHelper.update(sql, e.getIdSize(), e.getIdColor(), e.getIdMaterial(), e.getPrice(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        String sql = "CALL PRDelete(?)";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<ProductItem> selectAll() {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList ORDER BY idPrDeltails DESC";
        return selectBySql(sql);
    }

    public ProductItem selectImprotProductID(int id) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList WHERE D.idPrDeltails = ?";
        List<ProductItem> list = selectBySql(sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ProductItem> selectImprotProductKey(String key) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList WHERE P.nameProduct LIKE ?";
        return selectBySql(sql, "%" + key + "%");
    }

    public List<ProductItem> selectAllSell() {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList " +
                     "WHERE D.status = 1 AND D.quatity > 0 ORDER BY idPrDeltails DESC";
        return selectBySql(sql);
    }

    @Override
    public ProductItem selectById(Integer k) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList WHERE D.idPrDeltails = ?";
        List<ProductItem> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ProductItem> selectByKey(String k) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList WHERE P.nameProduct LIKE ?";
        return selectBySql(sql, "%" + k + "%");
    }

    @Override
    protected List<ProductItem> selectBySql(String sql, Object... args) {
        List<ProductItem> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ProductItem p = new ProductItem();
                p.setId(rs.getInt("idPrDeltails"));
                p.setIdProduct(rs.getInt("idProduct"));
                p.setIdSize(rs.getInt("idSize"));
                p.setIdColor(rs.getInt("idColor"));
                p.setIdMaterial(rs.getInt("idMaterial"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quatity"));
                p.setStatus(rs.getBoolean("status"));
                p.setSize(rs.getString("valueSize"));
                p.setColor(rs.getString("valueColor"));
                p.setMaterial(rs.getString("valueMaterial"));
                p.setProductName(rs.getString("nameProduct"));
                p.setCategoryName(rs.getString("nameList"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void importProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct SET quatity = quatity + ? WHERE idPrDeltails = ?";
        jdbcHelper.update(sql, quantity, id);
    }

    public void sellProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct SET quatity = quatity - ? WHERE idPrDeltails = ?";
        jdbcHelper.update(sql, quantity, id);
    }
    
    public void updateQuantity(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct SET quatity = ? WHERE idPrDeltails = ?";
        jdbcHelper.update(sql, quantity, id);
    }

    public void returnProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct SET quatity = quatity + ? WHERE idPrDeltails = ?";
        jdbcHelper.update(sql, quantity, id);
    }

    public List<ProductItem> selectByKeyWordSell(String keyword) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList " +
                     "WHERE D.status = 1 AND D.quatity > 0 AND P.nameProduct LIKE ?";
        return selectBySql(sql, "%" + keyword + "%");
    }

    public List<ProductItem> selectByPrice(float price, int idOld) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList " +
                     "WHERE price <= ? AND D.idPrDeltails NOT IN (?) AND D.status = 1 AND D.quatity > 0";
        return selectBySql(sql, price, idOld);
    }

    public List<ProductItem> selectByPropertieProductItem(int quantity, String keyword) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList ";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        switch (keyword) {
            case "Above":
                sb.append("WHERE D.quatity >= ?");
                break;
            case "Below":
                sb.append("WHERE D.quatity <= ?");
                break;    
            case "StillRemailProductItem":
                sb.append("WHERE D.quatity > 0");
                return selectBySql(sb.toString());
            case "OutOfStock":
                sb.append("WHERE D.quatity = 0");
                return selectBySql(sb.toString());
            case "OderByPriceDesc":
                sb.append("ORDER BY D.price DESC");
                return selectBySql(sb.toString());
            case "OderByPriceAsc":
                sb.append("ORDER BY D.price ASC");
                return selectBySql(sb.toString());
            case "StatusTrue":
                sb.append("WHERE D.status = 1");
                return selectBySql(sb.toString());
            case "StatusFalse":
                sb.append("WHERE D.status = 0");
                return selectBySql(sb.toString());
            case "ByProduct":
                sb.append("WHERE D.idProduct = ?");
                break;
        }
        return selectBySql(sb.toString(), quantity);
    }

    public List<ProductItem> selectByRemainQuantity(int k) {
        String sql = "SELECT D.*, P.nameProduct, S.valueSize, C.valueColor, M.valueMaterial, nameList, quatity FROM detailsProduct D " +
                     "INNER JOIN Size S ON D.idSize = S.idSize " +
                     "INNER JOIN Material M ON M.idMaterial = D.idMaterial " +
                     "INNER JOIN Color C ON C.idColor = D.idColor " +
                     "INNER JOIN Products P ON P.idProduct = D.idProduct " +
                     "INNER JOIN List L ON L.idList = P.idList WHERE D.quatity = 0";
        return selectBySql(sql, k);
    }
}

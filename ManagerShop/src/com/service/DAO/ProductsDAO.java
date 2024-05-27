package com.service.DAO;

import com.service.entity.Products;
import com.service.helper.jdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO extends ShopDAO<Products, Integer> {

    @Override
    public void insert(Products e) {
        String sql = "INSERT INTO Products (idList, nameProduct, description, status) VALUES (?, ?, ?, ?)";
        jdbcHelper.update(sql, e.getIdList(), e.getNameProduct(), e.getDescription(), e.isStatus());
    }

    @Override
    public void update(Products e) {
        String sql = "UPDATE Products SET idList = ?, nameProduct = ?, description = ?, status = ? WHERE idProduct = ?";
        jdbcHelper.update(sql, e.getIdList(), e.getNameProduct(), e.getDescription(), e.isStatus(), e.getIdProduct());
    }

    @Override
    public void delete(Integer k) {
        String sql = "UPDATE Products SET statusDelete = 0 WHERE idProduct = ?";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<Products> selectAll() {
        String sql = "SELECT * FROM Products p JOIN List l ON l.idList = p.idList WHERE p.statusDelete = 1 AND l.status = 1 ORDER BY p.idProduct DESC";
        return selectBySql(sql);
    }

    @Override
    public Products selectById(Integer k) {
        String sql = "SELECT * FROM Products p JOIN List l ON l.idList = p.idList WHERE p.idProduct = ?";
        List<Products> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Products selectByName(String name) {
        String sql = "SELECT * FROM Products p JOIN List l ON l.idList = p.idList WHERE p.nameProduct = ?";
        List<Products> list = selectBySql(sql, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Products> selectBySql(String sql, Object... args) {
        List<Products> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Products p = new Products();
                p.setIdProduct(rs.getInt("idProduct"));
                p.setNameProduct(rs.getString("nameProduct"));
                p.setDescription(rs.getString("description"));
                p.setIdList(rs.getInt("idList"));
                p.setNameList(rs.getString("nameList"));
                p.setStatus(rs.getBoolean("status"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Products> selectByKeyWord(String keyword) {
        String sql = "SELECT * FROM Products p JOIN List l ON l.idList = p.idList WHERE p.nameProduct LIKE ? AND l.status = 1 AND p.statusDelete = 1";
        return selectBySql(sql, "%" + keyword + "%");
    }
}
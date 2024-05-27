
package com.service.DAO;

import com.service.entity.Material;
import com.service.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MaterialDAO extends ShopDAO<Material, Integer> {

    @Override
    public void insert(Material e) {
        String sql = "INSERT INTO `material` VALUES(?)";
        jdbcHelper.update(sql, e.getValueMaterial());
    }

    @Override
    public void update(Material e) {
        String sql = "UPDATE `material` SET `valueMaterial` = ? WHERE `idMaterial` = ?";
        jdbcHelper.update(sql, e.getValueMaterial(), e.getIdMaterial());
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Material> selectAll() {
        String sql = "SELECT * FROM `material`";
        return selectBySql(sql);
    }

    @Override
    public Material selectById(Integer k) {
        String sql = "SELECT * FROM `material` WHERE `idMaterial` = ?";
        List<Material> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Material> selectBySql(String sql, Object... args) {
        List<Material> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Material m = new Material();
                m.setIdMaterial(rs.getInt("idMaterial"));
                m.setValueMaterial(rs.getString("valueMaterial"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Material selectByName(String name) {
        String sql = "SELECT * FROM `material` WHERE `valueMaterial` = ?";
        List<Material> list = selectBySql(sql, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
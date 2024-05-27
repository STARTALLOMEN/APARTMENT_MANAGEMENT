
package com.service.DAO;

import com.service.entity.Supplier;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao extends ShopDAO<Supplier, Integer> {

    @Override
    public void insert(Supplier e) {
        String sql = "INSERT INTO Supplier "
                + "(nameMaterial, phoneNumber, address) "
                + "VALUES (?, ?, ?)";
        jdbcHelper.update(sql, e.getNameMaterial(), e.getPhoneNumber(), e.getAddress());
    }

    @Override
    public void update(Supplier e) {
        String sql = "UPDATE Supplier SET nameMaterial = ?, phoneNumber = ?, address = ? WHERE idSupplier = ?";
        jdbcHelper.update(sql, e.getNameMaterial(), e.getPhoneNumber(), e.getAddress(), e.getIdSupplier());
    }

    @Override
    public void delete(Integer k) {
        String sql = "DELETE FROM Supplier WHERE idSupplier = ?";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<Supplier> selectAll() {
        String sql = "SELECT * FROM Supplier ORDER BY idSupplier DESC";
        return selectBySql(sql);
    }

    @Override
    public Supplier selectById(Integer k) {
        String sql = "SELECT * FROM Supplier WHERE idSupplier = ?";
        List<Supplier> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Supplier> selectBySql(String sql, Object... args) {
        List<Supplier> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setIdSupplier(rs.getInt("idSupplier"));
                s.setNameMaterial(rs.getString("nameMaterial"));
                s.setPhoneNumber(rs.getString("phoneNumber"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Supplier> selectByKeyWord(String key) {
        String sql = "SELECT * FROM Supplier WHERE nameMaterial LIKE ?";
        return selectBySql(sql, "%" + key + "%");
    }
}
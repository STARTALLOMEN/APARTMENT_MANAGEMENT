
package com.service.DAO;

import com.service.entity.Size;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SizeDAO extends ShopDAO<Size, Integer> {

    @Override
    public void insert(Size e) {
        String sql = "INSERT INTO Size(valueSize) VALUES(?)";
        jdbcHelper.update(sql, e.getValueSize());
    }

    @Override
    public void update(Size e) {
        String sql = "UPDATE Size SET valueSize = ? WHERE idSize = ?";
        jdbcHelper.update(sql, e.getValueSize(), e.getIdSize());
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Size> selectAll() {
        String sql = "SELECT * FROM Size";
        return selectBySql(sql);
    }

    @Override
    public Size selectById(Integer k) {
        String sql = "SELECT * FROM Size WHERE idSize = ?";
        List<Size> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Size selectByName(String name) {
        String sql = "SELECT * FROM Size WHERE valueSize = ?";
        List<Size> list = selectBySql(sql, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Size> selectBySql(String sql, Object... args) {
        List<Size> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Size s = new Size();
                s.setIdSize(rs.getInt("idSize"));
                s.setValueSize(rs.getString("valueSize"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
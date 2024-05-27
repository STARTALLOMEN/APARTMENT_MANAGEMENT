
package com.service.DAO;

import com.service.entity.Customer;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO extends ShopDAO<Customer, Integer> {

    private static final String INSERT_SQL = "INSERT INTO Customer (`name`, phoneNumber, gender, `address`) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM Customer ORDER BY idCustomer DESC";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM Customer WHERE idCustomer=?";
    private static final String DELETE_SQL = "DELETE FROM Customer WHERE idCustomer=?";
    private static final String UPDATE_SQL = "UPDATE Customer SET `name`=?, phoneNumber=?, gender=?, `address`=? WHERE idCustomer=?";

    @Override
    public void insert(Customer e) {
        jdbcHelper.update(INSERT_SQL, e.getName(), e.getPhoneNumber(), e.getGender(), e.getAddress());
    }

    @Override
    public void update(Customer e) {
        jdbcHelper.update(UPDATE_SQL, e.getName(), e.getPhoneNumber(), e.getGender(), e.getAddress(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        jdbcHelper.update(DELETE_SQL, k);
    }

    @Override
    public List<Customer> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Customer selectById(Integer k) {
        List<Customer> list = selectBySql(SELECT_BY_ID_SQL, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Customer> selectBySql(String sql, Object... args) {
        List<Customer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("idCustomer"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                c.setPhoneNumber(rs.getString("phoneNumber"));
                c.setGender(rs.getBoolean("gender"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Customer> selectByKeyWord(String keyword) {
        String sql = "SELECT * FROM Customer WHERE `name` LIKE ? ORDER BY idCustomer DESC";
        return selectBySql(sql, "%" + keyword + "%");
    }
}

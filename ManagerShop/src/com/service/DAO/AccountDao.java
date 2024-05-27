
package com.service.DAO;

import com.service.entity.Account;
import com.service.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AccountDao extends ShopDAO<Account, Integer> {

    private String INSERT_SQL_ACCOUNT = "INSERT INTO Account\n"
            + "(idUser,Username,password)\n"
            + "VALUES((SELECT idUser FROM USER ORDER BY idUser DESC LIMIT 1), ?, ?)";
    private String UPDATE_SQL = "";
    private String DELETE_SQL = "";
    private String SELECT_ALL_SQL = "SELECT * FROM Account";
    private String SELECT_BY_ID = "SELECT * FROM Account WHERE idAccount = ?";

    @Override
    public void insert(Account e) {
        jdbcHelper.update(INSERT_SQL_ACCOUNT, e.getUserName(), e.getPassWord());
    }

    @Override
    public void update(Account e) {
        String sql = "UPDATE Account SET password = ? WHERE idUser = ?";
        jdbcHelper.update(sql, e.getPassWord(), e.getIdUser());
    }

    @Override
    public List<Account> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    public List<Account> selectAllUP(Integer i) {
        return this.selectBySql("select * from Account where idUser = ?", i);
    }

    @Override
    protected List<Account> selectBySql(String sql, Object... args) {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Account a = new Account();
                a.setIdAcount(rs.getInt("idAccount"));
                a.setIdUser(rs.getInt("idUser"));
                a.setUserName(rs.getString("username"));
                a.setPassWord(rs.getString("password"));
                list.add(a);
            }
            rs.getStatement().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account selectById(Integer k) {
        List<Account> list = this.selectBySql(SELECT_BY_ID, k);
        return list.size()>0?list.get(0):null;
    }

    public Account selectByIdUser(Integer k) {
        List<Account> list = this.selectBySql("select * from Account where idUser = ?", k);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public Account selectById(String k) {
        String sql = "SELECT Account.* FROM Account JOIN User ON User.idUser = Account.idUser WHERE username = ? AND status = 1";
        List<Account> list = this.selectBySql(sql, k);
        return list.size()>0?list.get(0):null;
    }
}

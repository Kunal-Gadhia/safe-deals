/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp4
 */
@Repository
public class UserDAL {

    public static final class Columns {

        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String USER_TYPE = "user_type";
    }

    public static final String TABLE_NAME = "user";

    private final SimpleJdbcInsert insertUser;
    private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    private EmployeeDAL employeeDAL;
    @Autowired
    public UserDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.USERNAME,
                        Columns.PASSWORD,
                        Columns.USER_TYPE
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<User> findAll(Integer offset) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ORDER BY " + Columns.ID + " DESC LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(User.class));
    }

    public User findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    public User findByUsername(String username) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.USERNAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> findByNameLike(String username) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(username) LIKE?";
        String userNameLike = "%" + username.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{userNameLike}, new BeanPropertyRowMapper<>(User.class));
    }
    
    public User insert(User user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.USERNAME, user.getUsername());
        parameters.put(Columns.PASSWORD, user.getPassword());
        parameters.put(Columns.USER_TYPE, user.getUserType().name());
        Number newId = insertUser.executeAndReturnKey(parameters);
        user = findById(newId.intValue());
        return user;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted=? WHERE " + Columns.ID + "=?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public User update(User user) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.USERNAME + " = ?,"
                + Columns.PASSWORD + " = ?, "
                + Columns.USER_TYPE + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery,
                new Object[]{
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserType().name(),
                    user.getId()
                });
        user = findById(user.getId());
        return user;
    }

}

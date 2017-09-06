/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.pricerange;

import java.util.Date;
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
 * @author hp-pc
 */
@Repository
public class PriceRangeDAL {

    public static final String TABLE_NAME = "price_range_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertPriceRange;

    public static final class Columns {

        public static final String ID = "id";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public PriceRangeDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertPriceRange = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.PRICE,
                        Columns.DESCRIPTION,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<PriceRange> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(PriceRange.class));
    }

    public List<PriceRange> findAllList() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(PriceRange.class));
    }

    public List<PriceRange> findByMinBudget(Integer minBudget) {
        Integer mainMinBudget = (minBudget * 2);
        System.out.println("Main Min Budget :" + mainMinBudget);
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PRICE + "<= ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{mainMinBudget}, new BeanPropertyRowMapper<>(PriceRange.class));
    }

    public PriceRange findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(PriceRange.class));
    }

    public PriceRange insert(PriceRange priceRange) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.PRICE, priceRange.getPrice());
        parameters.put(Columns.DESCRIPTION, priceRange.getDescription());
        parameters.put(Columns.USER_ID, priceRange.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertPriceRange.executeAndReturnKey(parameters);
        priceRange = findById(newId.intValue());
        return priceRange;
    }

    public PriceRange update(PriceRange priceRange) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.PRICE + " = ? ,"
                + Columns.DESCRIPTION + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            priceRange.getPrice(),
            priceRange.getDescription(),
            priceRange.getUserId(),
            new Date(),
            priceRange.getId()});
        priceRange = findById(priceRange.getId());
        return priceRange;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.pricerange;

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
    };

    @Autowired
    public PriceRangeDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertPriceRange = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.PRICE)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<PriceRange> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(PriceRange.class));
    }
    
    public List<PriceRange> findByMinBudget(Integer minBudget) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND "+Columns.PRICE+"<= ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{minBudget}, new BeanPropertyRowMapper<>(PriceRange.class));
    }

    public PriceRange findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(PriceRange.class));
    }

    public PriceRange insert(PriceRange priceRange) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.PRICE, priceRange.getPrice());

        Number newId = insertPriceRange.executeAndReturnKey(parameters);
        priceRange = findById(newId.intValue());
        return priceRange;
    }

    public PriceRange update(PriceRange priceRange) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.PRICE + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            priceRange.getPrice(),
            priceRange.getId()});
        priceRange = findById(priceRange.getId());
        return priceRange;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

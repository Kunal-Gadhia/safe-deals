/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.coordinate;

import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CoordinateDAL {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final class Columns {

        public static final String ID = "id";
        public static final String LOCATION_ID = "location_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
       
    }

    public static final String TABLE_NAME = "co_ordinate";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertCoordinate;
    
     @Autowired
    public CoordinateDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertCoordinate = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.LOCATION_ID,
                        Columns.LATITUDE,
                        Columns.LONGITUDE
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }
    
    public List<Coordinate> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Coordinate.class));
    }

    public Coordinate findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Coordinate.class));
    }
    
    public List<Coordinate> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(Coordinate.class));
    }
    
     public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

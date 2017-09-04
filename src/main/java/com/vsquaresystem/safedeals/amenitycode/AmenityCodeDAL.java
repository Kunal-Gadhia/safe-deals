/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.amenitycode;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author hp2
 */
@Repository
public class AmenityCodeDAL {

    public static final String TABLE_NAME = "amenity_code";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAmenityCode;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final static class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AMENITY_DETAIL_TAB = "amenity_detail_tab";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";

    };

    @Autowired
    public AmenityCodeDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertAmenityCode = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.AMENITY_DETAIL_TAB,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<AmenityCode> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE DELETED = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public List<AmenityCode> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public List<AmenityCode> findByTabName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.AMENITY_DETAIL_TAB + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public List<AmenityCode> findAmenities() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.AMENITY_DETAIL_TAB + " = 'AMENITIES'";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public List<AmenityCode> findWorkplaces() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.AMENITY_DETAIL_TAB + " = 'WORKPLACES'";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public List<AmenityCode> findProjects() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.AMENITY_DETAIL_TAB + " = 'PROJECTS'";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public List<AmenityCode> findProperties() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.AMENITY_DETAIL_TAB + " = 'PROPERTIES'";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public AmenityCode findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public AmenityCode findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(AmenityCode.class));
    }

    public AmenityCode insert(AmenityCode amenityCode) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, amenityCode.getName());
        parameters.put(Columns.AMENITY_DETAIL_TAB, amenityCode.getAmenityDetailTab().name());
        parameters.put(Columns.USER_ID, amenityCode.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertAmenityCode.executeAndReturnKey(parameters);
        amenityCode = findById(newId.intValue());
        return amenityCode;
    }

    public AmenityCode update(AmenityCode amenityCode) {
        logger.info("Amenity Code :{}", amenityCode);
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.AMENITY_DETAIL_TAB + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP + "=? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            amenityCode.getName(),
            amenityCode.getAmenityDetailTab().name(),
            amenityCode.getUserId(),
            new Date(),
            amenityCode.getId()});
        amenityCode = findById(amenityCode.getId());
        return amenityCode;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }

}

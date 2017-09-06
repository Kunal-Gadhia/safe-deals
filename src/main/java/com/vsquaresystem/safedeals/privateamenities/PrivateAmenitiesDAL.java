/*
 * To change this license header, choose License Headers in PrivateAmenities Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.privateamenities;

import com.vsquaresystem.safedeals.amenitycode.AmenityCodeDAL;
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
 * @author hp
 */
@Repository
public class PrivateAmenitiesDAL {

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    }

    public static final String TABLE_NAME = "private_amenities_master";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertPrivateAmenities;

    @Autowired
    public PrivateAmenitiesDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertPrivateAmenities = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<PrivateAmenities> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(PrivateAmenities.class));
    }

    public PrivateAmenities findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(PrivateAmenities.class));
    }

    public PrivateAmenities findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(PrivateAmenities.class));
    }

    public List<PrivateAmenities> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(PrivateAmenities.class));
    }

    public PrivateAmenities insert(PrivateAmenities project) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, project.getName());
        parameters.put(Columns.DESCRIPTION, project.getDescription());
        parameters.put(Columns.USER_ID, project.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertPrivateAmenities.executeAndReturnKey(parameters);
        project = findById(newId.intValue());
        return project;
    }

    public PrivateAmenities update(PrivateAmenities project) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.DESCRIPTION + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " =? WHERE "
                + Columns.ID + " =?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            project.getName(),
            project.getDescription(),
            project.getUserId(),
            new Date(),
            project.getId()});

        project = findById(project.getId());
        return project;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }

}

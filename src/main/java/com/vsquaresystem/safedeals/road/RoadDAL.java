/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.road;

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
public class RoadDAL {

    public static final String TABLE_NAME = "road_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertRoad;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final static class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SIZE = "size";
        public static final String CITY_ID = "city_id";
        public static final String ROAD_TYPE = "road_type";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public RoadDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertRoad = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.SIZE,
                        Columns.CITY_ID,
                        Columns.ROAD_TYPE,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Road> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE DELETED = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Road.class));
    }

    public List<Road> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Road.class));
    }

    public Road findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Road.class));
    }

    public Road findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Road.class));
    }

    public Road insert(Road road) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, road.getName());
        parameters.put(Columns.SIZE, road.getSize());
        parameters.put(Columns.CITY_ID, road.getCityId());
        parameters.put(Columns.ROAD_TYPE, road.getRoadType().name());
//        parameters.put(Columns.AMENITY_DETAIL_TAB, road.getAmenityDetailTab().name());
        parameters.put(Columns.USER_ID, road.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertRoad.executeAndReturnKey(parameters);
        road = findById(newId.intValue());
        return road;
    }

    public Road update(Road road) {
        logger.info("Amenity Code :{}", road);
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.SIZE + " = ?,"
                + Columns.CITY_ID + " = ?,"
                + Columns.ROAD_TYPE + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + "=? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            road.getName(),
            road.getSize(),
            road.getCityId(),
            road.getRoadType().name(),
            road.getUserId(),
            new Date(),
            road.getId()});
        road = findById(road.getId());
        return road;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

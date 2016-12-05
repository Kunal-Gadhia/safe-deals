package com.vsquaresystem.safedeals.transportation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TransportationDAL {

    public static final String TABLE_NAME = "transportation";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertTransportation;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String LOCATION_ID = "location_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    };
    
     @Autowired
    public TransportationDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertTransportation = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.LOCATION_ID,
                        Columns.LATITUDE,
                        Columns.LONGITUDE
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }
    
    public List<Transportation> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Transportation.class));
    }
    
    public Transportation insert(Transportation transportation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, transportation.getName());
        parameters.put(Columns.LOCATION_ID, transportation.getLocation_id());
        parameters.put(Columns.LATITUDE, transportation.getLatitude());
        parameters.put(Columns.LONGITUDE, transportation.getLongitude());
        Number newId = insertTransportation.executeAndReturnKey(parameters);
        transportation = findById(newId.intValue());
        return transportation;
    }
    
    public Transportation update(Transportation transportation) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " 
                + Columns.NAME + " = ? ,"
                + Columns.LOCATION_ID + " = ? ,"
                + Columns.LATITUDE + " = ? ,"
                + Columns.LONGITUDE + " = ? ,"
                + "WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            transportation.getName(),
            transportation.getLocation_id(),
            transportation.getLatitude(),
            transportation.getLongitude(),
            transportation.getId()
        });
        transportation = findById(transportation.getId());
        return transportation;
    }

    public Transportation findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Transportation.class));
    }
    
     public List<Transportation> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(Transportation.class));
    }
    
     public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

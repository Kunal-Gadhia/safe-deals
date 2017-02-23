package com.vsquaresystem.safedeals.city;

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
public class CityDAL {

    public static final String TABLE_NAME = "city";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertCity;

    public final static class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COUNTRY_ID = "country_id";
        public static final String STATE_ID = "state_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String DESCRIPTION = "description";
        public static final String RADIUS = "radius";
        public static final String IMAGE_URL = "image_url";
    };

    @Autowired
    public CityDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertCity = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.COUNTRY_ID,
                        Columns.STATE_ID,
                        Columns.LATITUDE,
                        Columns.LONGITUDE,
                        Columns.DESCRIPTION,
                        Columns.RADIUS,
                        Columns.IMAGE_URL)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<City> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE DELETED = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(City.class));
    }

    public List<City> findAllCities() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(City.class));
    }

    public List<City> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(City.class));
    }

    public City findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(City.class));
    }

    public City findByCityName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(City.class));
    }

    public City insert(City city) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, city.getName());
        parameters.put(Columns.COUNTRY_ID, city.getCountryId());
        parameters.put(Columns.STATE_ID, city.getStateId());
        parameters.put(Columns.LATITUDE, city.getLatitude());
        parameters.put(Columns.LONGITUDE, city.getLongitude());
        parameters.put(Columns.DESCRIPTION, city.getDescription());
        parameters.put(Columns.RADIUS, city.getRadius());
        parameters.put(Columns.IMAGE_URL, city.getImageUrl());
        Number newId = insertCity.executeAndReturnKey(parameters);
        city = findById(newId.intValue());
        return city;
    }

    public List<City> findByNameAndStateId(String name, Integer stateId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.STATE_ID + " = ? AND LOWER(name) LIKE ?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{stateId, nameLike}, new BeanPropertyRowMapper<>(City.class));
    }

    public List<City> findByNameAndCountryId(String name, Integer countryId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.COUNTRY_ID + " = ? AND LOWER(name) LIKE ?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{countryId, nameLike}, new BeanPropertyRowMapper<>(City.class));
    }

    public List<City> findByStateId(Integer stateId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.STATE_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{stateId}, new BeanPropertyRowMapper<>(City.class));
    }

    public City update(City city) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?, "
                + Columns.COUNTRY_ID + " = ?, "
                + Columns.STATE_ID + " = ?, "
                + Columns.LATITUDE + " = ?, "
                + Columns.LONGITUDE + " = ?, "
                + Columns.DESCRIPTION + " = ?, "
                + Columns.RADIUS + " = ?, "
                + Columns.IMAGE_URL + "=?  WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            city.getName(),
            city.getCountryId(),
            city.getStateId(),
            city.getLatitude(),
            city.getLongitude(),
            city.getDescription(),
            city.getRadius(),
            city.getImageUrl(),
            city.getId()});
        city = findById(city.getId());
        return city;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

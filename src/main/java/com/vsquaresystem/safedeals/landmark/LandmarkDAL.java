package com.vsquaresystem.safedeals.landmark;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class LandmarkDAL {

    public static final String TABLE_NAME = "landmark";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertLandmark;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_ID = "location_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    };

    @Autowired
    public LandmarkDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertLandmark = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.CITY_ID,
                        Columns.LOCATION_ID,
                        Columns.LATITUDE,
                        Columns.LONGITUDE
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }
    public List<Landmark> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Landmark.class));
    }

    public Landmark findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Landmark.class));
    }
    
    public Landmark insert(Landmark landmark) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, landmark.getName());
        parameters.put(Columns.CITY_ID, landmark.getCityId());
        parameters.put(Columns.LOCATION_ID, landmark.getLocationId());
        parameters.put(Columns.LATITUDE, landmark.getLatitude());
        parameters.put(Columns.LONGITUDE, landmark.getLongitude());

        Number newId = insertLandmark.executeAndReturnKey(parameters);
        landmark = findById(newId.intValue());
        return landmark;
    }

    public Landmark update(Landmark landmark) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + "=?, "
                + Columns.CITY_ID + "=?, "
                + Columns.LOCATION_ID + "=?, "
                + Columns.LATITUDE + "=?, "
                + Columns.LONGITUDE + "=?, "
                + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            landmark.getName(),
            landmark.getCityId(),
            landmark.getLocationId(),
            landmark.getLatitude(),
            landmark.getLongitude(),
            landmark.getId()
        });
        landmark = findById(landmark.getId());
        return landmark;
    }
    
     public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

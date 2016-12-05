package com.vsquaresystem.safedeals.amenitydetail;

import java.util.ArrayList;
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

@Repository
public class AmenityDetailDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TABLE_NAME = "amenity_detail";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAmenityDetail;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String MOBILE_NUMBER = "mobile_number";
        public static final String LOCATION_ID = "location_id";
        public static final String AMENITY_ID = "amenity_id";                
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";        
        private static final String CITY_ID = "city_id";
    };

    @Autowired
    public AmenityDetailDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertAmenityDetail = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(Columns.NAME,
                        Columns.ADDRESS,
                        Columns.PHONE_NUMBER,
                        Columns.MOBILE_NUMBER,
                        Columns.LOCATION_ID,
                        Columns.AMENITY_ID,                        
                        Columns.LATITUDE,
                        Columns.LONGITUDE,
                        Columns.CITY_ID)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<AmenityDetail> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(AmenityDetail.class));
    }

    public AmenityDetail findByAmenityDetailName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(AmenityDetail.class));
    }

    public List<AmenityDetail> findAllAmenityDetails() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(AmenityDetail.class));
    }

    public AmenityDetail findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(AmenityDetail.class));
    }

    public AmenityDetail insert(AmenityDetail amenitydetail) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, amenitydetail.getName());
        parameters.put(Columns.ADDRESS, amenitydetail.getAddress());
        parameters.put(Columns.PHONE_NUMBER, amenitydetail.getPhoneNumber());
        parameters.put(Columns.MOBILE_NUMBER, amenitydetail.getMobileNumber());
        parameters.put(Columns.LOCATION_ID, amenitydetail.getLocationId());
        parameters.put(Columns.AMENITY_ID, amenitydetail.getAmenityId());                      
        parameters.put(Columns.LATITUDE, amenitydetail.getLatitude());
        parameters.put(Columns.LONGITUDE, amenitydetail.getLongitude());
        parameters.put(Columns.CITY_ID, amenitydetail.getCityId());
        Number newId = insertAmenityDetail.executeAndReturnKey(parameters);
        amenitydetail = findById(newId.intValue());
        return amenitydetail;
    }

    public List<AmenityDetail> findByADFilter(AmenityDetailFilter amenityDetailFilter) {
        String sqlString = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND ";
        List<Object> parameters = new ArrayList<>();

        if (amenityDetailFilter.getLocationId() != null) {
            sqlString = " AND " + Columns.LOCATION_ID + " = ? ";
            parameters.add(amenityDetailFilter.getLocationId());
        }
        if (amenityDetailFilter.getAmenityId() != null) {
            sqlString = " AND " + Columns.AMENITY_ID + " = ? ";
            parameters.add(amenityDetailFilter.getAmenityId()
            );
        }
        if (amenityDetailFilter.getName() != null) {
            sqlString = " AND " + Columns.NAME + " = ? ";
            parameters.add(amenityDetailFilter.getName());
        }
        return jdbcTemplate.query(sqlString, parameters.toArray(), new BeanPropertyRowMapper<>(AmenityDetail.class));
    }
    
    public List<AmenityDetail> findByAmenityIdCityId(Integer amenityId, Integer cityId) {
        String sqlString = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND "
                +Columns.AMENITY_ID+ "=? AND "
                +Columns.CITY_ID+ "=?";
        return jdbcTemplate.query(sqlString, new Object[]{amenityId, cityId}, new BeanPropertyRowMapper<>(AmenityDetail.class));
    }

    public List<AmenityDetail> findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(AmenityDetail.class));
    }

    public AmenityDetail update(AmenityDetail amenitydetail) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.ADDRESS + " = ?,"
                + Columns.PHONE_NUMBER + " = ?,"
                + Columns.MOBILE_NUMBER + " = ?,"
                + Columns.LOCATION_ID + " = ?,"
                + Columns.AMENITY_ID + " = ?,"                
                + Columns.LATITUDE + " = ?,"
                + Columns.LONGITUDE + " = ?,"
                + Columns.CITY_ID + "=? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery,
                new Object[]{
                    amenitydetail.getName(),
                    amenitydetail.getAddress(),
                    amenitydetail.getPhoneNumber(),
                    amenitydetail.getMobileNumber(),
                    amenitydetail.getLocationId(),
                    amenitydetail.getAmenityId(),                    
                    amenitydetail.getLatitude(),
                    amenitydetail.getLongitude(),                    
                    amenitydetail.getCityId(),                    
                    amenitydetail.getId()
                }
        );
        amenitydetail = findById(amenitydetail.getId());
        return amenitydetail;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

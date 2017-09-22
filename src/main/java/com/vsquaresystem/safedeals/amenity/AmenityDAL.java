package com.vsquaresystem.safedeals.amenity;

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

@Repository
public class AmenityDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String TABLE_NAME = "amenity";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAmenity;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String AMENITY_CODE_ID = "amenity_code_id";
        public static final String AMENITY_TYPE = "amenity_type";
        public static final String ICON = "icon";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public AmenityDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertAmenity = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.AMENITY_CODE_ID,
                        Columns.AMENITY_TYPE,
                        Columns.ICON,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Amenity> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public Amenity findByAmenityName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public List<Amenity> findByAmenityCode(Integer amenityCodeId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.AMENITY_CODE_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{amenityCodeId}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    //For basic and luxury amenities

    public List<Amenity> findByAmenityCodeAndAmenityNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public List<Amenity> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public List<Amenity> findAllAmenities() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public Amenity findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public Amenity insert(Amenity amenity) {
        logger.info("Amenity Object DAL :{}", amenity);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, amenity.getName());
        parameters.put(Columns.DESCRIPTION, amenity.getDescription());
        parameters.put(Columns.AMENITY_CODE_ID, amenity.getAmenityCodeId());
        parameters.put(Columns.AMENITY_TYPE, amenity.getAmenityType().name());
        parameters.put(Columns.ICON, amenity.getIcon());
        parameters.put(Columns.USER_ID, amenity.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertAmenity.executeAndReturnKey(parameters);
        amenity = findById(newId.intValue());
        return amenity;
    }

    public List<Amenity> findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Amenity.class));
    }

    public Amenity update(Amenity amenity) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.DESCRIPTION + " = ?,"
                + Columns.AMENITY_CODE_ID + " = ?,"
                + Columns.AMENITY_TYPE + " = ?,"
                + Columns.ICON + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            amenity.getName(),
            amenity.getDescription(),
            amenity.getAmenityCodeId(),
            amenity.getAmenityType().name(),
            amenity.getIcon(),
            amenity.getUserId(),
            new Date(),
            amenity.getId()
        });
        amenity = findById(amenity.getId());
        return amenity;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }

}

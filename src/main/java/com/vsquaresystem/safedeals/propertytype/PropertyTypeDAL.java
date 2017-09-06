package com.vsquaresystem.safedeals.propertytype;

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
public class PropertyTypeDAL {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String TABLE_NAME = "property_type";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertPropertyType;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NUMBER_OF_BHK = "number_of_bhk";
        public static final String CARPET_AREA = "carpet_area";
        public static final String PRICE_RANGE = "price_range";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";

    };

    @Autowired
    public PropertyTypeDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertPropertyType = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NUMBER_OF_BHK,
                        Columns.CARPET_AREA,
                        Columns.PRICE_RANGE,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<PropertyType> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public List<PropertyType> findAllEntries() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public PropertyType findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public PropertyType findByNumberOfBhk(Integer numberOfBhk) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NUMBER_OF_BHK + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{numberOfBhk}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public List<PropertyType> findByNumberOfBhkLike(String numberOfBhk) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(number_of_bhk) LIKE?";
        String nameLike = "%" + numberOfBhk.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public PropertyType insert(PropertyType propertyType) {
        logger.info("Insert Object :{}", propertyType);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NUMBER_OF_BHK, propertyType.getNumberOfBhk());
        parameters.put(Columns.CARPET_AREA, propertyType.getCarpetArea());
        parameters.put(Columns.PRICE_RANGE, propertyType.getPriceRange());
        parameters.put(Columns.USER_ID, propertyType.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertPropertyType.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public PropertyType update(PropertyType propertyType) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NUMBER_OF_BHK + " = ?,"
                + Columns.CARPET_AREA + " = ?,"
                + Columns.PRICE_RANGE + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            propertyType.getNumberOfBhk(),
            propertyType.getCarpetArea(),
            propertyType.getPriceRange(),
            propertyType.getUserId(),
            new Date(),
            propertyType.getId()
        });
        propertyType = findById(propertyType.getId());
        return propertyType;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }

}

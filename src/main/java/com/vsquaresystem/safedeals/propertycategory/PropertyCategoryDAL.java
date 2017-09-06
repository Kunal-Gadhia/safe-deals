/*
 * To change this license header, choose License Headers in Property Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.propertycategory;

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
 * @author hp
 */
@Repository
public class PropertyCategoryDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String TABLE_NAME = "property_category";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertPropertyCategory;

    public static final class Columns {

        public static final String ID = "id";
        public static final String CATEGORY = "category";
        public static final String DESCRIPTION = "description";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public PropertyCategoryDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertPropertyCategory = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(Columns.CATEGORY,
                        Columns.DESCRIPTION,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<PropertyCategory> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(PropertyCategory.class));
    }

    public List<PropertyCategory> findByPropertyCategoryLike(String category) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(category) LIKE?";
        String categoryLike = "%" + category.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{categoryLike}, new BeanPropertyRowMapper<>(PropertyCategory.class));
    }

    public PropertyCategory findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(PropertyCategory.class));
    }

    public PropertyCategory insert(PropertyCategory propertyCategory) {
        logger.info("PropertyCategory Object DAL :{}", propertyCategory);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.CATEGORY, propertyCategory.getCategory());
        parameters.put(Columns.DESCRIPTION, propertyCategory.getDescription());
        parameters.put(Columns.USER_ID, propertyCategory.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertPropertyCategory.executeAndReturnKey(parameters);
        propertyCategory = findById(newId.intValue());
        return propertyCategory;
    }

    public PropertyCategory update(PropertyCategory propertyCategory) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.CATEGORY + " = ?,"
                + Columns.DESCRIPTION + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            propertyCategory.getCategory(),
            propertyCategory.getDescription(),
            propertyCategory.getUserId(),
            new Date(),
            propertyCategory.getId()
        });
        propertyCategory = findById(propertyCategory.getId());
        return propertyCategory;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }

}

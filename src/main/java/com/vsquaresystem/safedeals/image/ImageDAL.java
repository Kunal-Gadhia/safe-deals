/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.image;

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
public class ImageDAL {

    public static final String TABLE_NAME = "image";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertImage;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PROJECT_ID = "project_id";
        public static final String PROPERTY_ID = "property_id";
        public static final String LOCATION_ID = "location_id";
        public static final String DOCUMENT_NAME = "document_name";
        private static final String PHOTO_PATH = "photo_path";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public ImageDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertImage = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.PROJECT_ID,
                        Columns.PROPERTY_ID,
                        Columns.LOCATION_ID,
                        Columns.DOCUMENT_NAME,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Image> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Image.class));
    }

    public List<Image> findByProjectId(Integer projectId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PROJECT_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{projectId}, new BeanPropertyRowMapper<>(Image.class));
    }

    public List<Image> findByPropertyId(Integer propertyId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PROPERTY_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{propertyId}, new BeanPropertyRowMapper<>(Image.class));
    }

    public List<Image> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(Image.class));
    }

    public Image findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Image.class));
    }

    public Image findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Image.class));
    }

    public Image insert(Image image) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, image.getName());
        parameters.put(Columns.PROJECT_ID, image.getProjectId());
        parameters.put(Columns.PROPERTY_ID, image.getPropertyId());
        parameters.put(Columns.LOCATION_ID, image.getLocationId());
        parameters.put(Columns.DOCUMENT_NAME, image.getDocumentName().name());
        parameters.put(Columns.USER_ID, image.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertImage.executeAndReturnKey(parameters);
        image = findById(newId.intValue());
        return image;
    }

    public Image update(Image image) {
        String path = image.getPhotoPath().get(0).toString().replace("\\", "\\\\");
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.PROJECT_ID + " = ? ,"
                + Columns.PROPERTY_ID + " = ? ,"
                + Columns.LOCATION_ID + " = ? , "
                + Columns.DOCUMENT_NAME + " = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ?,"
                + Columns.PHOTO_PATH + " = '" + path + "' WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            image.getName(),
            image.getProjectId(),
            image.getPropertyId(),
            image.getLocationId(),
            image.getDocumentName().name(),
            image.getUserId(),
            new Date(),
            image.getId()});
        image = findById(image.getId());
        return image;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

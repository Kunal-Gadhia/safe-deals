/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.image;

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
        private static final String PHOTO_PATH = "photo_path";
    };

    @Autowired
    public ImageDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertImage = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.PROJECT_ID,
                        Columns.PROPERTY_ID
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Image> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Image.class));
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
                + Columns.PHOTO_PATH + " = '" + path + "' WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            image.getName(),
            image.getProjectId(),
            image.getPropertyId(),
            image.getId()});
        image = findById(image.getId());
        return image;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

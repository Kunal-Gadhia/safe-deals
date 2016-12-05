package com.vsquaresystem.safedeals.locationcategory;

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
public class LocationCategoryDAL {

    public static final String TABLE_NAME = "location_category";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertLocationCategory;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    };

    @Autowired
    public LocationCategoryDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertLocationCategory = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<LocationCategory> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(LocationCategory.class));
    }

    public LocationCategory findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(LocationCategory.class));
    }
    
    public LocationCategory findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(LocationCategory.class));
    }
    
    public List<LocationCategory> findByNameLike(String name) {
    String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
    String nameLike = "%" + name.toLowerCase() + "%";
    return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(LocationCategory.class));
    }
    
    public LocationCategory insert(LocationCategory locationCategory) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, locationCategory.getName());
        parameters.put(Columns.DESCRIPTION, locationCategory.getDescription());
        Number newId = insertLocationCategory.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public LocationCategory update(LocationCategory locationCategory) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            locationCategory.getName(),
            locationCategory.getDescription(),
            locationCategory.getId()
        });
        locationCategory = findById(locationCategory.getId());
        return locationCategory;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

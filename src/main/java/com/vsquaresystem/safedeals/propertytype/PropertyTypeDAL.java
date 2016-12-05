package com.vsquaresystem.safedeals.propertytype;

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
public class PropertyTypeDAL {

    public static final String TABLE_NAME = "property_type";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertPropertyType;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NUMBER_OF_ROOMS = "number_of_rooms";
        public static final String CARPET_AREA = "carpet_area";
    };

    @Autowired
    public PropertyTypeDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertPropertyType = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NUMBER_OF_ROOMS,
                        Columns.CARPET_AREA)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<PropertyType> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public PropertyType findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(PropertyType.class));
    }
    public PropertyType findByNumberOfRooms(Integer numberOfRooms) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NUMBER_OF_ROOMS + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{numberOfRooms}, new BeanPropertyRowMapper<>(PropertyType.class));
    }

    public PropertyType insert(PropertyType propertyType) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NUMBER_OF_ROOMS, propertyType.getNumberOfRooms());
        parameters.put(Columns.CARPET_AREA, propertyType.getCarpetArea());
        Number newId = insertPropertyType.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public PropertyType update(PropertyType propertyType) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NUMBER_OF_ROOMS + " = ?,"
                + Columns.CARPET_AREA + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            propertyType.getNumberOfRooms(),
            propertyType.getCarpetArea(),
            propertyType.getId()
        });
        propertyType = findById(propertyType.getId());
        return propertyType;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

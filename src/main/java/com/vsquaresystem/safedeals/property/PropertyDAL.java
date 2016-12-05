package com.vsquaresystem.safedeals.property;

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
public class PropertyDAL {

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String PROPERTY_COST = "property_cost";
        public static final String LOCATION_ID = "location_id";
        public static final String CITY_ID = "city_id";
        public static final String PROPERTY_TYPE = "property_type";
        public static final String DATE_OF_CONSTRUCTION = "date_of_construction";
        public static final String DATE_OF_POSSESSION = "date_of_possession";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    public static final String TABLE_NAME = "property";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProperty;

    @Autowired
    public PropertyDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertProperty = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.PROPERTY_COST,
                        Columns.LOCATION_ID,
                        Columns.CITY_ID,
                        Columns.PROPERTY_TYPE,
                        Columns.DATE_OF_CONSTRUCTION,
                        Columns.DATE_OF_POSSESSION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Property> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Property.class));
    }

    public Property findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Property.class));
    }
    
    public List<Property> findByPropertyCost(Double propertyCost) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PROPERTY_COST + " <= ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{propertyCost}, new BeanPropertyRowMapper<>(Property.class));
    }
    
    public Property insert(Property property) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, property.getName());
        parameters.put(Columns.DESCRIPTION, property.getDescription());
        parameters.put(Columns.PROPERTY_COST, property.getPropertyCost());
        parameters.put(Columns.LOCATION_ID, property.getLocationId());
        parameters.put(Columns.CITY_ID, property.getCityId());
        parameters.put(Columns.PROPERTY_TYPE, property.getPropertyType());
        parameters.put(Columns.DATE_OF_CONSTRUCTION, property.getDateOfConstruction());
        parameters.put(Columns.DATE_OF_POSSESSION, property.getDateOfPossession());
        parameters.put(Columns.LATITUDE, property.getLatitude());
        parameters.put(Columns.LONGITUDE, property.getLongitude());
        Number newId = insertProperty.executeAndReturnKey(parameters);
        property = findById(newId.intValue());
        return property;
    }
    
    public List<Property> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(Property.class));
    }

    
    public Property update(Property property) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.DESCRIPTION + " =?,"
                + Columns.PROPERTY_COST + " =?,"
                + Columns.LOCATION_ID + " =?,"
                + Columns.CITY_ID + " =?,"
                + Columns.PROPERTY_TYPE + " =?,"
                + Columns.DATE_OF_CONSTRUCTION + " =?,"
                + Columns.DATE_OF_POSSESSION + " =?,"
                + Columns.LATITUDE + " =?,"
                + Columns.LONGITUDE + " =? WHERE "
                + Columns.ID + " =?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            property.getName(),
            property.getDescription(),
            property.getPropertyCost(),
            property.getLocationId(),
            property.getCityId(),
            property.getPropertyType(),
            property.getDateOfConstruction(),
            property.getDateOfPossession(),
            property.getLatitude(),
            property.getLongitude(),
            property.getId()});
        property = findById(property.getId());
        return property;
    }
    
     public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public List<Property> findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Property.class));
    }
}

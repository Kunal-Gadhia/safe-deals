package com.vsquaresystem.safedeals.builder;

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
public class BuilderDAL {
    
    public static final String TABLE_NAME = "builder";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBuilder;
    
    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String ADDRESS = "address";
        public static final String CITY_ID = "city_id";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String PROJECT_NAME = "project_name";
        public static final String PROPERTY_NAME = "property_name";
        
    };
    
    @Autowired
    public BuilderDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertBuilder = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.ADDRESS,
                        Columns.CITY_ID,
                        Columns.PHONE_NUMBER,
                        Columns.PROJECT_NAME,
                        Columns.PROPERTY_NAME
                )
                        
                .usingGeneratedKeyColumns(Columns.ID);
    }
    
    public List<Builder> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Builder.class));
    }
    
    public List<Builder> findAllBuilders() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Builder.class));
    }
    
    public Builder findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Builder.class));
    }
    
    public Builder insert(Builder builder) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, builder.getName());
        parameters.put(Columns.DESCRIPTION, builder.getDescription());
        parameters.put(Columns.ADDRESS, builder.getAddress());
        parameters.put(Columns.CITY_ID, builder.getCityId());
        parameters.put(Columns.PHONE_NUMBER, builder.getPhoneNumber());
        parameters.put(Columns.PROJECT_NAME, builder.getProjectName());
        parameters.put(Columns.PROPERTY_NAME, builder.getPropertyName());


        


        Number newId = insertBuilder.executeAndReturnKey(parameters);
        builder = findById(newId.intValue());
        return builder;
    }

    public Builder findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Builder.class));
    }
    
    public Builder update(Builder builder) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ," 
                + Columns.DESCRIPTION + " = ? ," 
                + Columns.ADDRESS + " = ? ," 
                + Columns.PHONE_NUMBER + " = ? ," 
                + Columns.PROJECT_NAME + " = ? ," 
                + Columns.PROPERTY_NAME + " = ? ," 
                + Columns.CITY_ID + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            builder.getName(),
            builder.getDescription(),
            builder.getAddress(),
           
            builder.getPhoneNumber(),
            builder.getProjectName(),
            builder.getPropertyName(),
             builder.getCityId(),
            builder.getId()});
        builder = findById(builder.getId());
        return builder;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}



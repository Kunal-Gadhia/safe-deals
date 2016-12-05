/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.agency;

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
 * @author ruchita
 */
@Repository
public class AgencyDAL {
    public static final String TABLE_NAME = "agency";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAgency;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String DESCRIPTION = "description";
    };
    
    @Autowired
    public AgencyDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertAgency = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(AgencyDAL.Columns.NAME,
                        Columns.ADDRESS,
                        Columns.DESCRIPTION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }
    public List<Agency> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Agency.class));
    }
    
    public List<Agency> findAllAgencies() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Agency.class));
    }
    
    
    public Agency findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Agency.class));
    }
    public Agency findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Agency.class));
    }
    
    public Agency insert(Agency agency) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, agency.getName());
        parameters.put(Columns.ADDRESS, agency.getAddress());
        parameters.put(Columns.DESCRIPTION, agency.getDescription());


        Number newId = insertAgency.executeAndReturnKey(parameters);
        agency = findById(newId.intValue());
        return agency;
    }
    
    public Agency update(Agency agency) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " 
                + Columns.NAME + " = ? ,"
                + Columns.ADDRESS + " = ? ,"
                + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            agency.getName(),
            agency.getAddress(),
            agency.getDescription(),
            agency.getId()
        });
        agency = findById(agency.getId());
        return agency;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
    
}

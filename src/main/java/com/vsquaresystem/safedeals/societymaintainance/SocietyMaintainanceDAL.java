/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.societymaintainance;

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
public class SocietyMaintainanceDAL {

    public static final String TABLE_NAME = "society_maintainance";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertSocietyMaintainance;

    public static final class Columns {

        public static final String ID = "id";
        public static final String MAINTAINANCE_NAME = "maintenance_name";
        public static final String DESCRIPTION = "description";

    };

    @Autowired
    public SocietyMaintainanceDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertSocietyMaintainance = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.MAINTAINANCE_NAME,
                        Columns.DESCRIPTION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<SocietyMaintainance> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(SocietyMaintainance.class));
    }

    public SocietyMaintainance findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(SocietyMaintainance.class));
    }

    public List<SocietyMaintainance> findByNameLike(String maintainanceName) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(maintenance_name) LIKE?";
        String nameLike = "%" + maintainanceName.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(SocietyMaintainance.class));
    }

    public SocietyMaintainance insert(SocietyMaintainance societyMaintainance) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.MAINTAINANCE_NAME, societyMaintainance.getMaintainanceName());
        parameters.put(Columns.DESCRIPTION, societyMaintainance.getDescription());
        Number newId = insertSocietyMaintainance.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public SocietyMaintainance update(SocietyMaintainance societyMaintainance) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.MAINTAINANCE_NAME + " = ?,"
                + Columns.DESCRIPTION
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            societyMaintainance.getMaintainanceName(),
            societyMaintainance.getDescription(),
            societyMaintainance.getId()
        });
        societyMaintainance = findById(societyMaintainance.getId());
        return societyMaintainance;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

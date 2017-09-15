/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.societymaintenance;

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
public class SocietyMaintenanceDAL {

    public static final String TABLE_NAME = "society_maintenance";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertSocietyMaintenance;

    public static final class Columns {

        public static final String ID = "id";
        public static final String MAINTENANCE_NAME = "maintenance_name";
        public static final String DESCRIPTION = "description";

    };

    @Autowired
    public SocietyMaintenanceDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertSocietyMaintenance = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.MAINTENANCE_NAME,
                        Columns.DESCRIPTION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<SocietyMaintenance> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(SocietyMaintenance.class));
    }

    public SocietyMaintenance findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(SocietyMaintenance.class));
    }

    public List<SocietyMaintenance> findByNameLike(String maintenanceName) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(maintenance_name) LIKE?";
        String nameLike = "%" + maintenanceName.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(SocietyMaintenance.class));
    }

    public SocietyMaintenance insert(SocietyMaintenance societyMaintenance) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.MAINTENANCE_NAME, societyMaintenance.getMaintenanceName());
        parameters.put(Columns.DESCRIPTION, societyMaintenance.getDescription());
        Number newId = insertSocietyMaintenance.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public SocietyMaintenance update(SocietyMaintenance societyMaintenance) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.MAINTENANCE_NAME + " = ?,"
                + Columns.DESCRIPTION
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            societyMaintenance.getMaintenanceName(),
            societyMaintenance.getDescription(),
            societyMaintenance.getId()
        });
        societyMaintenance = findById(societyMaintenance.getId());
        return societyMaintenance;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

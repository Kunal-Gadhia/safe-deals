/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.unit;

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
public class UnitDAL {

    public static final String TABLE_NAME = "unit_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUnit;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ABBRIVATION = "abbrivation";
    };

    @Autowired
    public UnitDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertUnit = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(UnitDAL.Columns.NAME,
                        Columns.ABBRIVATION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Unit> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Unit.class));
    }

    public Unit findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Unit.class));
    }

    public Unit findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Unit.class));
    }

    public Unit insert(Unit unit) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, unit.getName());
        parameters.put(Columns.ABBRIVATION, unit.getAbbrivation());

        Number newId = insertUnit.executeAndReturnKey(parameters);
        unit = findById(newId.intValue());
        return unit;
    }

    public Unit update(Unit unit) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.ABBRIVATION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            unit.getName(),
            unit.getAbbrivation(),
            unit.getId()
        });
        unit = findById(unit.getId());
        return unit;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

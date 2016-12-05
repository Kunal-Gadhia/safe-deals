package com.vsquaresystem.safedeals.safedealzone;

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
public class SafedealZoneDAL {

    public static final String TABLE_NAME = "safedeal_zone";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertSafedealZone;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    };

    @Autowired
    public SafedealZoneDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertSafedealZone = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<SafedealZone> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(SafedealZone.class));
    }

    public List<SafedealZone> findAllSafedealZones() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(SafedealZone.class));
    }
    
    public List<SafedealZone> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(SafedealZone.class));
    }

    public SafedealZone findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(SafedealZone.class));
    }


    public SafedealZone insert(SafedealZone safedealZone) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, safedealZone.getName());
        parameters.put(Columns.DESCRIPTION, safedealZone.getDescription());
        Number newId = insertSafedealZone.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }
    
    public SafedealZone findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(SafedealZone.class));
    }

    public SafedealZone update(SafedealZone safedealZone) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            safedealZone.getName(),
            safedealZone.getDescription(),
            safedealZone.getId()
        });
        safedealZone = findById(safedealZone.getId());
        return safedealZone;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

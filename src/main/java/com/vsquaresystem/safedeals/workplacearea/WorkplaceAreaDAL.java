package com.vsquaresystem.safedeals.workplacearea;

import java.util.Date;
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
public class WorkplaceAreaDAL {

    public static final String TABLE_NAME = "workplace_area";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertWorkplaceArea;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SIZE = "size";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";

    };

    @Autowired
    public WorkplaceAreaDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertWorkplaceArea = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.SIZE,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<WorkplaceArea> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(WorkplaceArea.class));
    }

    public List<WorkplaceArea> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(WorkplaceArea.class));
    }

    public WorkplaceArea findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(WorkplaceArea.class));
    }

    public WorkplaceArea findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(WorkplaceArea.class));
    }

    public WorkplaceArea insert(WorkplaceArea workplaceArea) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, workplaceArea.getName());
        parameters.put(Columns.SIZE, workplaceArea.getSize());
        parameters.put(Columns.USER_ID, workplaceArea.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertWorkplaceArea.executeAndReturnKey(parameters);
        workplaceArea = findById(newId.intValue());
        return workplaceArea;
    }

    public WorkplaceArea update(WorkplaceArea workplaceArea) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.SIZE + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            workplaceArea.getName(),
            workplaceArea.getSize(),
            workplaceArea.getUserId(),
            new Date(),
            workplaceArea.getId()});
        workplaceArea = findById(workplaceArea.getId());
        return workplaceArea;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

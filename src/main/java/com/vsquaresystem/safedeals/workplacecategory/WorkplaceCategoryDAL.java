
package com.vsquaresystem.safedeals.workplacecategory;

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
public class WorkplaceCategoryDAL {
    public static final String TABLE_NAME = "workplace_category";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertWorkplaceCategory;

    public static final class Columns {

        public static final String ID = "id";
        public static final String WORKPLACE_AREA_ID = "workplace_area_id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    };

    @Autowired
    public WorkplaceCategoryDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertWorkplaceCategory = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.WORKPLACE_AREA_ID,
                        Columns.NAME,
                        Columns.DESCRIPTION)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<WorkplaceCategory> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(WorkplaceCategory.class));
    }
    
    public List<WorkplaceCategory> findByNameLike(String name){
    String sqlQuery ="SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
    String nameLike = "%" + name.toLowerCase() + "%";
    return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(WorkplaceCategory.class));
    }
    
    public WorkplaceCategory findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(WorkplaceCategory.class));
    }
    
    public WorkplaceCategory findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(WorkplaceCategory.class));
    }

    public WorkplaceCategory insert(WorkplaceCategory workplaceCategory) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.WORKPLACE_AREA_ID, workplaceCategory.getWorkplaceAreaId());
        parameters.put(Columns.NAME, workplaceCategory.getName());
        parameters.put(Columns.DESCRIPTION, workplaceCategory.getDescription());
        Number newId = insertWorkplaceCategory.executeAndReturnKey(parameters);
        workplaceCategory = findById(newId.intValue());
        return workplaceCategory;
    }

    public WorkplaceCategory update(WorkplaceCategory workplaceCategory) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " 
                + Columns.WORKPLACE_AREA_ID + " = ? ," 
                + Columns.NAME + " = ? ," 
                + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            workplaceCategory.getWorkplaceAreaId(),
            workplaceCategory.getName(),
            workplaceCategory.getDescription(),
            workplaceCategory.getId()});
        workplaceCategory = findById(workplaceCategory.getId());
        return workplaceCategory;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

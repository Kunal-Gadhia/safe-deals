/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.projectcategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProjectCategoryDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String TABLE_NAME = "project_category";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProjectCategory;

    public static final class Columns {

        public static final String ID = "id";
        public static final String CATEGORY = "category";
        public static final String DESCRIPTION = "description";
    };

    @Autowired
    public ProjectCategoryDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertProjectCategory = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(Columns.CATEGORY,
                        Columns.DESCRIPTION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<ProjectCategory> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(ProjectCategory.class));
    }

    public List<ProjectCategory> findByProjectCategoryLike(String category) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(category) LIKE?";
        String categoryLike = "%" + category.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{categoryLike}, new BeanPropertyRowMapper<>(ProjectCategory.class));
    }

    public ProjectCategory findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(ProjectCategory.class));
    }

    public ProjectCategory insert(ProjectCategory projectCategory) {
        logger.info("ProjectCategory Object DAL :{}", projectCategory);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.CATEGORY, projectCategory.getCategory());
        parameters.put(Columns.DESCRIPTION, projectCategory.getDescription());
        Number newId = insertProjectCategory.executeAndReturnKey(parameters);
        projectCategory = findById(newId.intValue());
        return projectCategory;
    }

    public ProjectCategory update(ProjectCategory projectCategory) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.CATEGORY + " = ?,"
                + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            projectCategory.getCategory(),
            projectCategory.getDescription(),
            projectCategory.getId()
        });
        projectCategory = findById(projectCategory.getId());
        return projectCategory;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

package com.vsquaresystem.safedeals.project;

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
public class ProjectDAL {
    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String LOCATION_ID = "location_id";
        public static final String CITY_ID = "city_id";
        public static final String DATE_OF_CONSTRUCTION = "date_of_construction";
        public static final String DATE_OF_POSSESSION = "date_of_possession";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String PROJECT_COST = "project_cost";
    }

    public static final String TABLE_NAME = "project";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProject;

    @Autowired
    public ProjectDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertProject = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.LOCATION_ID,
                        Columns.CITY_ID,
                        Columns.DATE_OF_CONSTRUCTION,
                        Columns.DATE_OF_POSSESSION,
                        Columns.LATITUDE,
                        Columns.LONGITUDE,
                        Columns.PROJECT_COST
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Project> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Project.class));
    }

    public Project findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Project.class));
    }

    public Project insert(Project project) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, project.getName());
        parameters.put(Columns.DESCRIPTION, project.getDescription());
        parameters.put(Columns.LOCATION_ID, project.getLocationId());
        parameters.put(Columns.CITY_ID, project.getCityId());
        parameters.put(Columns.DATE_OF_CONSTRUCTION, project.getDateOfConstruction());
        parameters.put(Columns.DATE_OF_POSSESSION, project.getDateOfPossession());
        parameters.put(Columns.LATITUDE, project.getLatitude());
        parameters.put(Columns.LONGITUDE, project.getLongitude());
        parameters.put(Columns.PROJECT_COST, project.getProjectCost());
        Number newId = insertProject.executeAndReturnKey(parameters);
        project = findById(newId.intValue());
        return project;
    }
    
    public List<Project> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(Project.class));
    }
    
    public List<Project> findByProjectCost(Double projectCost) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PROJECT_COST + " <= ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{projectCost}, new BeanPropertyRowMapper<>(Project.class));
    }

    
    public Project update(Project project) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.DESCRIPTION + " =?,"
                + Columns.LOCATION_ID + " =?,"
                + Columns.CITY_ID + " =?,"
                + Columns.DATE_OF_CONSTRUCTION + " =?,"
                + Columns.DATE_OF_POSSESSION + " =?,"
                + Columns.LATITUDE + " =?,"
                + Columns.LONGITUDE + " =?,"
                + Columns.PROJECT_COST + " =? WHERE "
                + Columns.ID + " =?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            project.getName(),
            project.getDescription(),
            project.getLocationId(),
            project.getCityId(),
            project.getDateOfConstruction(),
            project.getDateOfPossession(),
            project.getLatitude(),
            project.getLongitude(),
            project.getProjectCost(),
            project.getId()});
        project = findById(project.getId());
        return project;
    }
    
     public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

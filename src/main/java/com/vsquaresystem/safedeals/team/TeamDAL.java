/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.team;

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
 * @author hp2
 */
@Repository
public class TeamDAL {

    public static final String TABLE_NAME = "team_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertTeam;

    public static final class Columns {

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String DESIGNATION = "designation";
        public static final String DESCRIPTION = "description";
        public static final String PHOTO_PATH = "photo_path";

    };

    @Autowired
    public TeamDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertTeam = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.USER_ID,
                        Columns.DESIGNATION,
                        Columns.DESCRIPTION)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Team> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Team.class));
    }

    public Team findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Team.class));
    }        

    public Team insert(Team team) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.USER_ID, team.getUserId());
        parameters.put(Columns.DESIGNATION, team.getDesignation());
        parameters.put(Columns.DESCRIPTION, team.getDescription());
//        parameters.put(Columns.PHOTO_PATH, team.getPhotoPath());

        Number newId = insertTeam.executeAndReturnKey(parameters);
        team = findById(newId.intValue());
        return team;
    }

    public Team update(Team team) {
        String path = team.getPhotoPath().get(0).toString().replace("\\", "\\\\");
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.USER_ID + " = ? ,"
                + Columns.DESIGNATION + " = ? ,"
                + Columns.DESCRIPTION + " = ? ,"
                + Columns.PHOTO_PATH + " = '" + path + "' WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            team.getUserId(),
            team.getDesignation(),
            team.getDescription(),
//            team.getPhotoPath(),
            team.getId()});
        team = findById(team.getId());
        return team;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

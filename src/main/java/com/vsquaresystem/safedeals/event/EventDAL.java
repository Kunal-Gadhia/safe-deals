/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.event;

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

/**
 *
 * @author hp2
 */
@Repository
public class EventDAL {

    public static final String TABLE_NAME = "event";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertEvent;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String VENUE = "venue";
        public static final String DESCRIPTION = "description";
        private static final String DATE = "date";
        private static final String PHOTO_PATH = "photo_path";
    };

    @Autowired
    public EventDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertEvent = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.VENUE,
                        Columns.DESCRIPTION,
                        Columns.DATE)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Event> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Event.class));
    }

    public Event findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Event.class));
    }
    
    public Event findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Event.class));
    }
    
    public List<Event> findByDate(){
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " +Columns.DATE+ " >= NOW()";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Event.class));
    }

    public Event insert(Event event) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, event.getName());
        parameters.put(Columns.VENUE, event.getVenue());
        parameters.put(Columns.DESCRIPTION, event.getDescription());
        parameters.put(Columns.DATE, event.getDate());

        Number newId = insertEvent.executeAndReturnKey(parameters);
        event = findById(newId.intValue());
        return event;
    }

    public Event update(Event event) {
        String path = event.getPhotoPath().get(0).toString().replace("\\", "\\\\");
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.VENUE + " = ? ,"
                + Columns.DESCRIPTION + " = ? ,"
                + Columns.DATE + " = ? ,"
                + Columns.PHOTO_PATH + " = '" + path + "' WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            event.getName(),
            event.getVenue(),
            event.getDescription(),
            event.getDate(),
            event.getId()});
        event = findById(event.getId());
        return event;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

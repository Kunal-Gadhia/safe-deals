/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.ourpresence;

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
public class OurPresenceDAL {

    public static final String TABLE_NAME = "our_presence_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertOurPresence;

    public static final class Columns {

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        private static final String CITY_ID = "city_id";
        private static final String ADDRESS = "address";
        private static final String CONTACT_NO = "contact_no";

    };

    @Autowired
    public OurPresenceDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertOurPresence = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.USER_ID,
                        Columns.CITY_ID,
                        Columns.ADDRESS,
                        Columns.CONTACT_NO
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<OurPresence> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(OurPresence.class));
    }

    public OurPresence findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(OurPresence.class));
    }

    public OurPresence insert(OurPresence ourPresence) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.USER_ID, ourPresence.getUserId());
        parameters.put(Columns.CITY_ID, ourPresence.getCityId());
        parameters.put(Columns.ADDRESS, ourPresence.getAddress());
        parameters.put(Columns.CONTACT_NO, ourPresence.getContactNo());

        Number newId = insertOurPresence.executeAndReturnKey(parameters);
        ourPresence = findById(newId.intValue());
        return ourPresence;
    }

    public OurPresence update(OurPresence ourPresence) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.USER_ID + " = ? ,"
                + Columns.CITY_ID + " = ? ,"
                + Columns.ADDRESS + " = ? ,"
                + Columns.CONTACT_NO + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            ourPresence.getUserId(),
            ourPresence.getCityId(),
            ourPresence.getAddress(),
            ourPresence.getContactNo(),
            ourPresence.getId()});
        ourPresence = findById(ourPresence.getId());
        return ourPresence;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

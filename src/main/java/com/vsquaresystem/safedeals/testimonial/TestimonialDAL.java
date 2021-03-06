/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.testimonial;

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
 * @author Ajay
 */
@Repository
public class TestimonialDAL {

    public static final String TABLE_NAME = "testimonial";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertTestimonial;

    public final static class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String PROFFESION = "profession";
        public static final String DESIGNATION = "designation";
        public static final String CATEGORY = "category";
        public static final String ATTACHMENT = "attachment";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";

    };

    @Autowired
    public TestimonialDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertTestimonial = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.PROFFESION,
                        Columns.DESIGNATION,
                        Columns.CATEGORY,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Testimonial> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE DELETED = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Testimonial.class));
    }

    public Testimonial findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE DELETED = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Testimonial.class));
    }

    public Testimonial findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Testimonial.class));
    }

    public Testimonial insert(TestimonialConfig testimonial) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, testimonial.getName());
        parameters.put(Columns.DESCRIPTION, testimonial.getDescription());
        parameters.put(Columns.PROFFESION, testimonial.getProfession());
        parameters.put(Columns.DESIGNATION, testimonial.getDesignation());
        parameters.put(Columns.CATEGORY, testimonial.getCategory().name());
        parameters.put(Columns.USER_ID, testimonial.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertTestimonial.executeAndReturnKey(parameters);
        Testimonial t = findById(newId.intValue());
        return t;
    }

    public List<Testimonial> findByCategory() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.CATEGORY + " = 'MEMBERS' OR " + Columns.CATEGORY + "= 'EXPERTS_VIEW'";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Testimonial.class));
    }

    //select * from testimonial WHERE category="MEMBERS" OR category= "EXPERTS_VIEW";
    public Testimonial update(Testimonial testimonial) {
        String path = testimonial.getAttachment().get(0).toString().replace("\\", "\\\\");
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?, "
                + Columns.DESCRIPTION + " = ?, "
                + Columns.PROFFESION + " = ?, "
                + Columns.DESIGNATION + " = ?, "
                + Columns.CATEGORY + " = ?, "
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ?,"
                + Columns.ATTACHMENT + " = '" + path + "' WHERE " + Columns.ID + " = ?";
        System.out.println("THIS IS TESTINOMIAL DAL" + path);
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            testimonial.getName(),
            testimonial.getDescription(),
            testimonial.getProfession(),
            testimonial.getDesignation(),
            testimonial.getCategory().name(),
            testimonial.getUserId(),
            new Date(),
            testimonial.getId()}
        );
        testimonial = findById(testimonial.getId());
        return testimonial;
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

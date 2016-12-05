/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.enquiry;

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
 * @author hp4
 */
@Repository
public class EnquiryDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String MOBILE_NO = "mobile_no";
        public static final String EMAIL = "email";
        public static final String MODE_OF_CONTACT = "mode_of_contact";
        public static final String CATEGORY = "category";
        public static final String PROPERTY_GUIDANCE = "property_guidance";
        public static final String PROPERTY_SELLING = "property_selling";
        public static final String PROPERTY_BUYING = "property_buying";

    }

    public static final String TABLE_NAME = "enquiry";
    private final SimpleJdbcInsert insertEnquiry;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EnquiryDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertEnquiry = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(EnquiryDAL.Columns.NAME,
                        EnquiryDAL.Columns.MOBILE_NO,
                        EnquiryDAL.Columns.EMAIL,
                        EnquiryDAL.Columns.MODE_OF_CONTACT,
                        EnquiryDAL.Columns.CATEGORY,
                        EnquiryDAL.Columns.PROPERTY_GUIDANCE,
                        EnquiryDAL.Columns.PROPERTY_SELLING,
                        EnquiryDAL.Columns.PROPERTY_BUYING
                )
                .usingGeneratedKeyColumns(EnquiryDAL.Columns.ID);

    }

    public List<Enquiry> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ORDER BY " + Columns.ID + " DESC LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Enquiry.class));
    }

    public Enquiry findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Enquiry.class));
    }

    public Enquiry findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Enquiry.class));
    }

    public Enquiry insert(Enquiry enquiry) {
        logger.info("Enquiry Object :{}", enquiry);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, enquiry.getName());
        parameters.put(Columns.MOBILE_NO, enquiry.getMobileNo());
        parameters.put(Columns.EMAIL, enquiry.getEmail());
        parameters.put(Columns.MODE_OF_CONTACT, enquiry.getModeOfContact().name());
        parameters.put(Columns.CATEGORY, enquiry.getCategory().name());
        parameters.put(Columns.PROPERTY_GUIDANCE, enquiry.getPropertyGuidance());
        parameters.put(Columns.PROPERTY_SELLING, enquiry.getPropertySelling());
        parameters.put(Columns.PROPERTY_BUYING, enquiry.getPropertyBuying());

        Number newId = insertEnquiry.executeAndReturnKey(parameters);
        enquiry = findById(newId.intValue());
        return enquiry;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted=? WHERE " + Columns.ID + "=?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public Enquiry update(Enquiry enquiry) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + "=?, "
                + Columns.MOBILE_NO + "=?, "
                + Columns.EMAIL + "=?, "
                + Columns.MODE_OF_CONTACT + "=?, "
                + Columns.CATEGORY + "=?, "
                + Columns.PROPERTY_GUIDANCE + "=?, "
                + Columns.PROPERTY_SELLING + "=?, "
                + Columns.PROPERTY_BUYING + " =?, WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery,
                new Object[]{
                    enquiry.getName(),
                    enquiry.getMobileNo(),
                    enquiry.getEmail(),
                    enquiry.getModeOfContact().name(),
                    enquiry.getCategory().name(),
                    enquiry.getPropertyGuidance(),
                    enquiry.getPropertySelling(),
                    enquiry.getPropertyBuying(),
                    enquiry.getId()});
        enquiry = findById(enquiry.getId());
        return enquiry;
    }

}

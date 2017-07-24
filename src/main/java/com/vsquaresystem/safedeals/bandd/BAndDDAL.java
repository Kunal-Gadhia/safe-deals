/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.bandd;

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
public class BAndDDAL {

    public static final String TABLE_NAME = "b_and_d_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertOurPresence;

    public static final class Columns {

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        private static final String MISSION = "mission";
        private static final String VISION = "vision";
        private static final String MOTO = "moto";
        private static final String FB_LINK = "fb_link";
        private static final String TWITTER_LINK = "twitter_link";
        private static final String YOU_TUBE_LINK = "you_tube_link";
        private static final String CAREER_OPENING_PARA = "career_opening_para";
        private static final String BRANCH_OFFICE_ADDRESS = "branch_office_address";
        private static final String MAIN_OFFICE_ADDRESS = "main_office_address";

    };

    @Autowired
    public BAndDDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertOurPresence = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.USER_ID,
                        Columns.MISSION,
                        Columns.VISION,
                        Columns.MOTO,
                        Columns.FB_LINK,
                        Columns.TWITTER_LINK,
                        Columns.YOU_TUBE_LINK,
                        Columns.CAREER_OPENING_PARA,
                        Columns.BRANCH_OFFICE_ADDRESS,
                        Columns.MAIN_OFFICE_ADDRESS
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<BAndD> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(BAndD.class));
    }

    public BAndD findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(BAndD.class));
    }

    public BAndD insert(BAndD bAndD) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.USER_ID, bAndD.getUserId());
        parameters.put(Columns.MISSION, bAndD.getMission());
        parameters.put(Columns.VISION, bAndD.getVision());
        parameters.put(Columns.MOTO, bAndD.getMoto());
        parameters.put(Columns.FB_LINK, bAndD.getFbLink());
        parameters.put(Columns.TWITTER_LINK, bAndD.getTwitterLink());
        parameters.put(Columns.YOU_TUBE_LINK, bAndD.getYouTubeLink());
        parameters.put(Columns.CAREER_OPENING_PARA, bAndD.getCareerOpeningPara());
        parameters.put(Columns.BRANCH_OFFICE_ADDRESS, bAndD.getBranchOfficeAddress());
        parameters.put(Columns.MAIN_OFFICE_ADDRESS, bAndD.getMainOfficeAddress());

        Number newId = insertOurPresence.executeAndReturnKey(parameters);
        bAndD = findById(newId.intValue());
        return bAndD;
    }

    public BAndD update(BAndD bAndD) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.USER_ID + " = ? ,"
                + Columns.MISSION + " = ? ,"
                + Columns.VISION + " = ? ,"
                + Columns.MOTO + " = ? ,"
                + Columns.FB_LINK + " = ? ,"
                + Columns.TWITTER_LINK + " = ? ,"
                + Columns.YOU_TUBE_LINK + " = ? ,"
                + Columns.CAREER_OPENING_PARA + " = ? ,"
                + Columns.BRANCH_OFFICE_ADDRESS + " = ? ,"
                + Columns.MAIN_OFFICE_ADDRESS + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            bAndD.getUserId(),
            bAndD.getMission(),
            bAndD.getVision(),
            bAndD.getMoto(),
            bAndD.getFbLink(),
            bAndD.getTwitterLink(),
            bAndD.getYouTubeLink(),
            bAndD.getCareerOpeningPara(),
            bAndD.getBranchOfficeAddress(),
            bAndD.getMainOfficeAddress(),
            bAndD.getId()});
        bAndD = findById(bAndD.getId());
        return bAndD;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

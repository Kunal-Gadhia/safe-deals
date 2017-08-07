/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.bankdetails;

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
public class BankDetailsDAL {

    public static final String TABLE_NAME = "bank_details";
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
        private static final String WEBSITE_LINK = "website_link";
        private static final String EMI_CALCI_LINK = "emi_calci_link";
        private static final String LOAN_ELIGIBILITY_CALCI_LINK = "loan_eligibility_calci_link";
        private static final String MAIN_OFFICE_ADDRESS = "main_office_address";
        private static final String BRANCH_OFFICE_ADDRESS = "branch_office_address";

    };

    @Autowired
    public BankDetailsDAL(DataSource dataSource) {
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
                        Columns.WEBSITE_LINK,
                        Columns.EMI_CALCI_LINK,
                        Columns.LOAN_ELIGIBILITY_CALCI_LINK,
                        Columns.MAIN_OFFICE_ADDRESS,
                        Columns.BRANCH_OFFICE_ADDRESS
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<BankDetails> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(BankDetails.class));
    }

    public BankDetails findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(BankDetails.class));
    }

    public BankDetails insert(BankDetails bankDetails) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.USER_ID, bankDetails.getUserId());
        parameters.put(Columns.MISSION, bankDetails.getMission());
        parameters.put(Columns.VISION, bankDetails.getVision());
        parameters.put(Columns.MOTO, bankDetails.getMoto());
        parameters.put(Columns.FB_LINK, bankDetails.getFbLink());
        parameters.put(Columns.TWITTER_LINK, bankDetails.getTwitterLink());
        parameters.put(Columns.YOU_TUBE_LINK, bankDetails.getYouTubeLink());
        parameters.put(Columns.WEBSITE_LINK, bankDetails.getWebsiteLink());
        parameters.put(Columns.EMI_CALCI_LINK, bankDetails.getEmiCalciLink());
        parameters.put(Columns.LOAN_ELIGIBILITY_CALCI_LINK, bankDetails.getLoanEligibilityCalciLink());
        parameters.put(Columns.MAIN_OFFICE_ADDRESS, bankDetails.getMainOfficeAddress());
        parameters.put(Columns.BRANCH_OFFICE_ADDRESS, bankDetails.getBranchOfficeAddress());

        Number newId = insertOurPresence.executeAndReturnKey(parameters);
        bankDetails = findById(newId.intValue());
        return bankDetails;
    }

    public BankDetails update(BankDetails bankDetails) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.USER_ID + " = ? ,"
                + Columns.MISSION + " = ? ,"
                + Columns.VISION + " = ? ,"
                + Columns.MOTO + " = ? ,"
                + Columns.FB_LINK + " = ? ,"
                + Columns.TWITTER_LINK + " = ? ,"
                + Columns.YOU_TUBE_LINK + " = ? ,"
                + Columns.WEBSITE_LINK + " = ? ,"
                + Columns.EMI_CALCI_LINK + " = ? ,"
                + Columns.LOAN_ELIGIBILITY_CALCI_LINK + " = ? ,"
                + Columns.MAIN_OFFICE_ADDRESS + " = ? ,"
                + Columns.BRANCH_OFFICE_ADDRESS + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            bankDetails.getUserId(),
            bankDetails.getMission(),
            bankDetails.getVision(),
            bankDetails.getMoto(),
            bankDetails.getFbLink(),
            bankDetails.getTwitterLink(),
            bankDetails.getYouTubeLink(),
            bankDetails.getWebsiteLink(),
            bankDetails.getEmiCalciLink(),
            bankDetails.getLoanEligibilityCalciLink(),
            bankDetails.getMainOfficeAddress(),
            bankDetails.getBranchOfficeAddress(),
            bankDetails.getId()});
        bankDetails = findById(bankDetails.getId());
        return bankDetails;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

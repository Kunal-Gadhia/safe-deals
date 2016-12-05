/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.branch;

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
public class BranchDAL {

    public static final String TABLE_NAME = "branch";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBranch;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        private static final String BANK_ID = "bank_id";
        private static final String IFSC_CODE = "ifsc_code";
        private static final String MICR_CODE = "micr_code";
        private static final String PHONE_NO = "phone_no";
        private static final String FAX = "fax";
        private static final String EMAIL = "email";
        private static final String LOCATION_ID = "location_id";
        private static final String ADDRESS = "address";
        private static final String CITY_ID = "city_id";
    };

    @Autowired
    public BranchDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertBranch = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.BANK_ID,
                        Columns.IFSC_CODE,
                        Columns.MICR_CODE,
                        Columns.PHONE_NO,
                        Columns.FAX,
                        Columns.EMAIL,
                        Columns.LOCATION_ID,
                        Columns.ADDRESS,
                        Columns.CITY_ID)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Branch> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Branch.class));
    }

    public Branch findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Branch.class));
    }

    public Branch findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Branch.class));
    }

    public Branch insert(Branch branch) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, branch.getName());
        parameters.put(Columns.BANK_ID, branch.getBankId());
        parameters.put(Columns.IFSC_CODE, branch.getIfscCode());
        parameters.put(Columns.MICR_CODE, branch.getMicrCode());
        parameters.put(Columns.PHONE_NO, branch.getPhoneNo());
        parameters.put(Columns.FAX, branch.getFax());
        parameters.put(Columns.EMAIL, branch.getEmail());
        parameters.put(Columns.LOCATION_ID, branch.getLocationId());
        parameters.put(Columns.ADDRESS, branch.getAddress());
        parameters.put(Columns.CITY_ID, branch.getCityId());

        Number newId = insertBranch.executeAndReturnKey(parameters);
        branch = findById(newId.intValue());
        return branch;
    }

    public Branch update(Branch branch) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.BANK_ID + " = ? ,"
                + Columns.IFSC_CODE + " = ? ,"
                + Columns.MICR_CODE + " = ? ,"
                + Columns.PHONE_NO + " = ? ,"
                + Columns.FAX + " = ? ,"
                + Columns.EMAIL + " = ? ,"
                + Columns.LOCATION_ID + " = ? ,"
                + Columns.ADDRESS + " = ? ,"
                + Columns.CITY_ID + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            branch.getName(),
            branch.getBankId(),
            branch.getIfscCode(),
            branch.getMicrCode(),
            branch.getPhoneNo(),
            branch.getFax(),
            branch.getEmail(),
            branch.getLocationId(),
            branch.getAddress(),
            branch.getCityId(),
            branch.getId()});
        branch = findById(branch.getId());
        return branch;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

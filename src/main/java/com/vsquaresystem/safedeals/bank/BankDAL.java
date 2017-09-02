package com.vsquaresystem.safedeals.bank;

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

@Repository
public class BankDAL {

    public static final String TABLE_NAME = "bank";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBank;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String LOAN_INTEREST_RATE_FOR_MALE = "loan_interest_rate_for_male";
        public static final String LOAN_INTEREST_RATE_FOR_FEMALE = "loan_interest_rate_for_female";
        public static final String MAX_AGE_FOR_SALARIED = "max_age_for_salaried";
        public static final String MAX_AGE_FOR_BUSINESSMAN = "max_age_for_businessman";
        public static final String MAX_LOAN_TENURE = "max_loan_tenure";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public BankDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertBank = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.LOAN_INTEREST_RATE_FOR_MALE,
                        Columns.LOAN_INTEREST_RATE_FOR_FEMALE,
                        Columns.MAX_AGE_FOR_SALARIED,
                        Columns.MAX_AGE_FOR_BUSINESSMAN,
                        Columns.MAX_LOAN_TENURE,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Bank> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Bank.class));
    }

    public List<Bank> findAllBanks() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Bank.class));
    }

    public List<Bank> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Bank.class));
    }

    public Bank findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Bank.class));
    }

    public Bank insert(Bank bank) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, bank.getName());
        parameters.put(Columns.LOAN_INTEREST_RATE_FOR_MALE, bank.getLoanInterestRateForMale());
        parameters.put(Columns.LOAN_INTEREST_RATE_FOR_FEMALE, bank.getLoanInterestRateForFemale());
        parameters.put(Columns.MAX_AGE_FOR_SALARIED, bank.getMaxAgeForSalaried());
        parameters.put(Columns.MAX_AGE_FOR_BUSINESSMAN, bank.getMaxAgeForBusinessman());
        parameters.put(Columns.MAX_LOAN_TENURE, bank.getMaxLoanTenure());
        parameters.put(Columns.USER_ID, bank.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertBank.executeAndReturnKey(parameters);
        bank = findById(newId.intValue());
        return bank;
    }

    public Bank findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Bank.class));
    }

    public Bank update(Bank bank) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.LOAN_INTEREST_RATE_FOR_MALE + " = ? ,"
                + Columns.LOAN_INTEREST_RATE_FOR_FEMALE + " = ? ,"
                + Columns.MAX_AGE_FOR_SALARIED + " = ? ,"
                + Columns.MAX_AGE_FOR_BUSINESSMAN + " = ? ,"
                + Columns.MAX_LOAN_TENURE + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            bank.getName(),
            bank.getLoanInterestRateForMale(),
            bank.getLoanInterestRateForFemale(),
            bank.getMaxAgeForSalaried(),
            bank.getMaxAgeForBusinessman(),
            bank.getMaxLoanTenure(),
            bank.getUserId(),
            new Date(),
            bank.getId()});
        bank = findById(bank.getId());
        return bank;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

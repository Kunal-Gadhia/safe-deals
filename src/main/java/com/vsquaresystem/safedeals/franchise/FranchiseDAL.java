
package com.vsquaresystem.safedeals.franchise;

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
public class FranchiseDAL {
    
    public static final String TABLE_NAME = "franchise";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertFranchise;
    
    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_ID = "location_id";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER1 = "phone_number1";
        public static final String PHONE_NUMBER2 = "phone_number2";
        public static final String FAX = "fax";
    };
    
    @Autowired
    public FranchiseDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertFranchise = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.ADDRESS,
                        Columns.CITY_ID,
                        Columns.LOCATION_ID,
                        Columns.EMAIL,
                        Columns.PHONE_NUMBER1,
                        Columns.PHONE_NUMBER2,
                        Columns.FAX)
                .usingGeneratedKeyColumns(Columns.ID);
    }
    
    public List<Franchise> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Franchise.class));
    }
    
    public List<Franchise> findAllFranchises() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Franchise.class));
    }
    
    public Franchise findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Franchise.class));
    }
    
    public Franchise insert(Franchise franchise) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, franchise.getName());
        parameters.put(Columns.ADDRESS, franchise.getAddress());
        parameters.put(Columns.CITY_ID, franchise.getCityId());
        parameters.put(Columns.LOCATION_ID, franchise.getLocationId());
        parameters.put(Columns.EMAIL, franchise.getEmail());
        parameters.put(Columns.PHONE_NUMBER1, franchise.getPhoneNumber1());
        parameters.put(Columns.PHONE_NUMBER2, franchise.getPhoneNumber2());
        parameters.put(Columns.FAX, franchise.getFax());


        Number newId = insertFranchise.executeAndReturnKey(parameters);
        franchise = findById(newId.intValue());
        return franchise;
    }

    public Franchise findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Franchise.class));
    }
    
    public Franchise update(Franchise franchise) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " 
                + Columns.NAME + " = ? ," 
                + Columns.ADDRESS + " = ? ," 
                + Columns.CITY_ID + " = ? ," 
                + Columns.LOCATION_ID + " = ? ," 
                + Columns.EMAIL + " = ? ," 
                + Columns.PHONE_NUMBER1 + " = ? ,"
                + Columns.PHONE_NUMBER2 + " = ? ,"
                + Columns.FAX + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            franchise.getName(),
            franchise.getAddress(),
            franchise.getCityId(),
            franchise.getLocationId(),
            franchise.getEmail(),
            franchise.getPhoneNumber1(),
            franchise.getPhoneNumber2(),
            franchise.getFax(),
            franchise.getId()});
        franchise = findById(franchise.getId());
        return franchise;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

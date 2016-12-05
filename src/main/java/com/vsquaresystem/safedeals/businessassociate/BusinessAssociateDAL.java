package com.vsquaresystem.safedeals.businessassociate;

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
public class BusinessAssociateDAL {

    public static final String TABLE_NAME = "business_associate";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBusinessAssociate;

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
    public BusinessAssociateDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertBusinessAssociate = new SimpleJdbcInsert(jdbcTemplate)
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

    public List<BusinessAssociate> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(BusinessAssociate.class));
    }

    public BusinessAssociate findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(BusinessAssociate.class));
    }
    
    public BusinessAssociate findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(BusinessAssociate.class));
    }
    
    public List<BusinessAssociate> findAllCities() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(BusinessAssociate.class));
    }

    public BusinessAssociate insert(BusinessAssociate businessAssociate) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, businessAssociate.getName());
        parameters.put(Columns.ADDRESS, businessAssociate.getAddress());
        parameters.put(Columns.CITY_ID, businessAssociate.getCityId());
        parameters.put(Columns.LOCATION_ID, businessAssociate.getLocationId());
        parameters.put(Columns.EMAIL, businessAssociate.getEmail());
        parameters.put(Columns.PHONE_NUMBER1, businessAssociate.getPhoneNumber1());
        parameters.put(Columns.PHONE_NUMBER2, businessAssociate.getPhoneNumber2());
        parameters.put(Columns.FAX, businessAssociate.getFax());
        Number newId = insertBusinessAssociate.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public BusinessAssociate update(BusinessAssociate businessAssociate) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.ADDRESS + " = ?,"
                + Columns.CITY_ID + " = ?,"
                + Columns.LOCATION_ID + " = ?,"
                + Columns.EMAIL + " = ?,"
                + Columns.PHONE_NUMBER1 + " = ?,"
                + Columns.PHONE_NUMBER2 + " = ?,"
                + Columns.FAX + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            businessAssociate.getName(),
            businessAssociate.getAddress(),
            businessAssociate.getCityId(),
            businessAssociate.getLocationId(),
            businessAssociate.getEmail(),
            businessAssociate.getPhoneNumber1(),
            businessAssociate.getPhoneNumber2(),
            businessAssociate.getFax(),
            businessAssociate.getId()
        });
        businessAssociate = findById(businessAssociate.getId());
        return businessAssociate;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

package com.vsquaresystem.safedeals.transportation;

import com.vsquaresystem.safedeals.privateamenities.PrivateAmenities;
import com.vsquaresystem.safedeals.privateamenities.PrivateAmenitiesDAL;
import static com.vsquaresystem.safedeals.privateamenities.PrivateAmenitiesDAL.TABLE_NAME;
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
public class TransportationDAL {

    public static final String TABLE_NAME = "transportation_master";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertTransportation;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";
    };

    @Autowired
    public TransportationDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertTransportation = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Transportation> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Transportation.class));
    }

    public Transportation findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Transportation.class));
    }

    public List<Transportation> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Transportation.class));
    }

    public Transportation insert(Transportation transportation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, transportation.getName());
        parameters.put(Columns.USER_ID, transportation.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());
        Number newId = insertTransportation.executeAndReturnKey(parameters);
        transportation = findById(newId.intValue());
        return transportation;
    }

    public Transportation update(Transportation transportation) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            transportation.getName(),
            transportation.getUserId(),
            new Date(),
            transportation.getId()
        });
        transportation = findById(transportation.getId());
        return transportation;
    }

    public Transportation findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Transportation.class));
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }
}

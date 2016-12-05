package com.vsquaresystem.safedeals.readyreckoner;

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
public class ReadyreckonerDAL {

    public static final String TABLE_NAME = "ready_reckoner";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertReadyreckoner;

    public static final class Columns {

        public static final String ID = "id";
        public static final String LOCATION_ID = "location_id";
        public static final String RR_YEAR = "rr_year";
        public static final String RR_RATE_LAND = "rr_rate_land";
        public static final String RR_RATE_PLOT = "rr_rate_plot";
        public static final String RR_RATE_APARTMENT = "rr_rate_apartment";

    };

    @Autowired
    public ReadyreckonerDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertReadyreckoner = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.LOCATION_ID,
                        Columns.RR_YEAR,
                        Columns.RR_RATE_LAND,
                        Columns.RR_RATE_PLOT,
                        Columns.RR_RATE_APARTMENT
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Readyreckoner> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Readyreckoner.class));
    }

    public Readyreckoner findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Readyreckoner.class));
    }

    public List<Readyreckoner> getAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Readyreckoner.class));
    }

    public void truncateAll() {
        String sqlQuery = "TRUNCATE " + TABLE_NAME;
        jdbcTemplate.update(sqlQuery, new Object[]{});
    }

    public Readyreckoner insert(Readyreckoner readyreckoner) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.LOCATION_ID, readyreckoner.getLocationId());
       parameters.put(Columns.RR_YEAR, readyreckoner.getRrYear());
        parameters.put(Columns.RR_RATE_LAND, readyreckoner.getRrRateLand());
        parameters.put(Columns.RR_RATE_PLOT, readyreckoner.getRrRatePlot());
        parameters.put(Columns.RR_RATE_APARTMENT, readyreckoner.getRrRateApartment());

        Number newId = insertReadyreckoner.executeAndReturnKey(parameters);
        readyreckoner = findById(newId.intValue());
        return readyreckoner;
    }

    public List<Readyreckoner> findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND LOWER(name) LIKE ?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Readyreckoner.class));
    }

    public Readyreckoner update(Readyreckoner readyreckoner) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " 
                + Columns.LOCATION_ID + " = ?, " 
                + Columns.RR_YEAR + " = ?, "
                + Columns.RR_RATE_LAND + " = ?, "
                + Columns.RR_RATE_PLOT + " = ?, "
                + Columns.RR_RATE_APARTMENT + " = ? WHERE "
                + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            readyreckoner.getLocationId(),
            readyreckoner.getRrYear(),
            readyreckoner.getRrRateLand(),
            readyreckoner.getRrRatePlot(),
            readyreckoner.getRrRateApartment(),
            readyreckoner.getId()
        });
        readyreckoner = findById(readyreckoner.getId());
        return readyreckoner;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

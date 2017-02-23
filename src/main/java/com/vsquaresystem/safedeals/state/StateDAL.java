package com.vsquaresystem.safedeals.state;

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

@Repository
public class StateDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TABLE_NAME = "state";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertState;

    @Autowired
    public StateDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertState = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.COUNTRY_ID,
                        Columns.DESCRIPTION,
                        Columns.LATITUDE,
                        Columns.LONGITUDE)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COUNTRY_ID = "country_id";
        public static final String DESCRIPTION = "description";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    };

    public List<State> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(State.class));
    }

    public State findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(State.class));
    }
    
    public State findByState(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(State.class));
    }

    public State insert(State state) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", state.getName());
        parameters.put("country_id", state.getCountryId());
        parameters.put("description", state.getDescription());
        parameters.put("latitude", state.getLatitude());
        parameters.put("longitude", state.getLongitude());
        Number newId = insertState.executeAndReturnKey(parameters);
        state = findById(newId.intValue());
        return state;
    }

    public List<State> findByNameAndCountryId(String name, Integer countryId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND country_id = ? AND LOWER(name) LIKE ?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{countryId, nameLike}, new BeanPropertyRowMapper<>(State.class));
    }

    public List<State> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND LOWER(name) LIKE ?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(State.class));
    }

    public List<State> findByCountryId(Integer countryId) {
        logger.info("logger:::", countryId);
        System.out.println("com.vsquaresystem.safedeals.state.StateDAL.findByCountryId()" + countryId);
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND country_id = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{countryId}, new BeanPropertyRowMapper<>(State.class));
    }

    public State update(State state) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ?, "
                + Columns.COUNTRY_ID + " = ?, "
                + Columns.DESCRIPTION + " = ?, "
                + Columns.LATITUDE + " = ?, "
                + Columns.LONGITUDE + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{state.getName(),
            state.getCountryId(),
            state.getDescription(),
            state.getLatitude(),
            state.getLongitude(),
            state.getId()});
        state = findById(state.getId());
        return state;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

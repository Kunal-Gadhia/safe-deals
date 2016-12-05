package com.vsquaresystem.safedeals.hospital;

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
public class HospitalDAL {

    public static final String TABLE_NAME = "hospital";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertHospital;

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SPECIALITY = "speciality";
        public static final String SERVICE = "service";
        public static final String LOCATION_ID = "location_id";
    };

    @Autowired
    public HospitalDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertHospital = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.SPECIALITY,
                        Columns.SERVICE,
                        Columns.LOCATION_ID)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Hospital> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Hospital.class));
    }

    public Hospital findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Hospital.class));
    }

    public Hospital findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Hospital.class));
    }

    public Hospital insert(Hospital hospital) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, hospital.getName());
        parameters.put(Columns.SPECIALITY, hospital.getSpeciality());
        parameters.put(Columns.SERVICE, hospital.getService());
        parameters.put(Columns.LOCATION_ID, hospital.getLocationId());
        Number newId = insertHospital.executeAndReturnKey(parameters);
        hospital = findById(newId.intValue());
        return hospital;
    }

    public Hospital update(Hospital hospital) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ,"
                + Columns.SPECIALITY + " = ? ,"
                + Columns.SERVICE + " = ? ,"
                + Columns.LOCATION_ID + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            hospital.getName(),
            hospital.getSpeciality(),
            hospital.getService(),
            hospital.getLocationId(),
            hospital.getId()});
        hospital = findById(hospital.getId());
        return hospital;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

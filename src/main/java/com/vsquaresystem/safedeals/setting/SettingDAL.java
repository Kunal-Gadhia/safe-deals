package com.vsquaresystem.safedeals.setting;

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
public class SettingDAL {

    public static final String TABLE_NAME = "setting";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertSetting;

    public static final class Columns {

        public static final String ID = "id";
        public static final String SETTING_KEY = "setting_key";
        public static final String SETTING_VALUE = "setting_value";
        public static final String DESCRIPTION = "description";
    }

    @Autowired
    public SettingDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertSetting = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.SETTING_KEY,
                        Columns.SETTING_VALUE,
                        Columns.DESCRIPTION)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Setting> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Setting.class));
    }

    public Setting findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Setting.class));
    }

    public Setting insert(Setting setting) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.SETTING_KEY, setting.getSettingKey().name());
        parameters.put(Columns.SETTING_VALUE, setting.getSettingValue());
        parameters.put(Columns.DESCRIPTION, setting.getDescription());
        Number newId = insertSetting.executeAndReturnKey(parameters);
        setting = findById(newId.intValue());
        return setting;
    }

    public Setting update(Setting setting) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET " + Columns.SETTING_KEY + " = ?, " + Columns.SETTING_VALUE + "  = ?, " + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{setting.getSettingKey().name(),
            setting.getSettingValue(),
            setting.getDescription(),
            setting.getId()});
        setting = findById(setting.getId());
        return setting;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public Setting findByKey(SettingKey key) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + Columns.SETTING_KEY + " = ?";
        return jdbcTemplate.queryForObject(
                sqlQuery,
                new Object[]{key.name()},
                new BeanPropertyRowMapper<>(Setting.class));
    }

    public void truncate() {
        String sqlQuery = "TRUNCATE TABLE " + TABLE_NAME;
        jdbcTemplate.execute(sqlQuery);
    }

}

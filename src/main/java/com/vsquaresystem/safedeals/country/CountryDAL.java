
package com.vsquaresystem.safedeals.country;

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
public class CountryDAL {
    
        private final Logger logger = LoggerFactory.getLogger(getClass());
        
        public static final class Columns {

		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String CALLING_CODE = "calling_code";
		public static final String DESCRIPTION = "description";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
	}
        
        public static final String TABLE_NAME = "country";
        private final SimpleJdbcInsert insertCountry;
        private final JdbcTemplate jdbcTemplate;
        
        
        @Autowired
	public CountryDAL(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		insertCountry = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName(TABLE_NAME)
			.usingColumns(
				Columns.NAME,
				Columns.CALLING_CODE,
                                Columns.DESCRIPTION,
                                Columns.LATITUDE,
                                Columns.LONGITUDE)
			.usingGeneratedKeyColumns(Columns.ID);
	}

	public List<Country> findAll(Integer offset) {
		String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
		return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Country.class));
	}

	public Country findById(Integer id) {
		String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
		return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Country.class));
	}

	public Country insert(Country country) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(Columns.NAME, country.getName());
		parameters.put(Columns.CALLING_CODE, country.getCallingCode());
		parameters.put(Columns.DESCRIPTION, country.getDescription());
		parameters.put(Columns.LATITUDE, country.getLatitude());
		parameters.put(Columns.LONGITUDE, country.getLongitude());
		Number newId = insertCountry.executeAndReturnKey(parameters);
		country = findById(newId.intValue());
                logger.info("line 59:{}", country);
		return country;
	}

	public List<Country> findByName(String name) {
		String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND LOWER(name) LIKE ?";
		String nameLike = "" + name.toLowerCase() + "%";
		return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Country.class));
	}

	public Country update(Country country) {
		String sqlQuery = "UPDATE " + TABLE_NAME + " SET " 
                        + Columns.NAME + " = ?, " 
                        + Columns.CALLING_CODE + " = ?, " 
                        + Columns.DESCRIPTION + " = ?, "
                        + Columns.LATITUDE + " = ?, "
                        + Columns.LONGITUDE + " = ? WHERE " + Columns.ID + " = ?";
		Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{country.getName(),
			country.getCallingCode(),
			country.getDescription(),
			country.getLatitude(),
			country.getLongitude(),
			country.getId()});
		country = findById(country.getId());
		return country;
	}

	public void delete(Integer id) {
		String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
		jdbcTemplate.update(sqlQuery, new Object[]{true, id});
	}
}
        

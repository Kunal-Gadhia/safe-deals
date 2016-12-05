package com.vsquaresystem.safedeals.salaryrange;

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
public class SalaryRangeDAL {

    public static final String TABLE_NAME = "salary_range";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertSalaryRange;

    public static final class Columns {

        public static final String ID = "id";
        public static final String MIN_SALARY = "min_salary";
        public static final String MAX_SALARY = "max_salary";
        public static final String DESCRIPTION = "description";
    };

    @Autowired
    public SalaryRangeDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertSalaryRange = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.MIN_SALARY,
                        Columns.MAX_SALARY,
                        Columns.DESCRIPTION)
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<SalaryRange> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(SalaryRange.class));
    }

    public List<SalaryRange> findByDescriptionLike(String description) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(description) LIKE?";
        String descriptionLike = "%" + description.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{descriptionLike}, new BeanPropertyRowMapper<>(SalaryRange.class));
    }

    public SalaryRange findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(SalaryRange.class));
    }

    public SalaryRange insert(SalaryRange salaryrange) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.MIN_SALARY, salaryrange.getMinSalary());
        parameters.put(Columns.MAX_SALARY, salaryrange.getMaxSalary());
        parameters.put(Columns.DESCRIPTION, salaryrange.getDescription());
        Number newId = insertSalaryRange.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }

    public SalaryRange update(SalaryRange salaryrange) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.MIN_SALARY + " = ?,"
                + Columns.MAX_SALARY + " = ?,"
                + Columns.DESCRIPTION + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            salaryrange.getMinSalary(),
            salaryrange.getMaxSalary(),
            salaryrange.getDescription(),
            salaryrange.getId()
        });
        salaryrange = findById(salaryrange.getId());
        return salaryrange;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

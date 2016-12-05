package com.vsquaresystem.safedeals.workplacecategorydetail;

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
public class WorkplaceCategoryDetailDAL {
    
    public static final String TABLE_NAME ="workplace_category_detail";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertWorkplaceCategoryDetail;
    
    public static final class Columns {

        public static final String ID = "id";
        public static final String WORKPLACE_CATEGORY_ID ="workplace_category_Id";
        public static final String SALARY_RANGE_ID = "salary_range_id";
        public static final String NUMBER_OF_EMPLOYEE ="number_of_employee";
        public static final String NUMBER_OF_STUDENT ="number_of_student";
        public static final String NUMBER_OF_BED ="number_of_bed";
    };
    
     @Autowired
    public WorkplaceCategoryDetailDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertWorkplaceCategoryDetail = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.WORKPLACE_CATEGORY_ID,
                        Columns.SALARY_RANGE_ID,
                        Columns.NUMBER_OF_EMPLOYEE,
                        Columns.NUMBER_OF_STUDENT,
                        Columns.NUMBER_OF_BED)   
                .usingGeneratedKeyColumns(Columns.ID);
    }
    
    public List<WorkplaceCategoryDetail> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(WorkplaceCategoryDetail.class));
    }
    
    public WorkplaceCategoryDetail findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(WorkplaceCategoryDetail.class));
    }
    
    public WorkplaceCategoryDetail insert(WorkplaceCategoryDetail workplaceCategoryDetail) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.WORKPLACE_CATEGORY_ID, workplaceCategoryDetail.getWorkplaceCategoryId());
        parameters.put(Columns.SALARY_RANGE_ID, workplaceCategoryDetail.getSalaryRangeId());
        parameters.put(Columns.NUMBER_OF_EMPLOYEE, workplaceCategoryDetail.getNumberOfEmployee());
        parameters.put(Columns.NUMBER_OF_STUDENT, workplaceCategoryDetail.getNumberOfStudent());
        parameters.put(Columns.NUMBER_OF_BED, workplaceCategoryDetail.getNumberOfBed());
        Number newId = insertWorkplaceCategoryDetail.executeAndReturnKey(parameters);
        return findById(newId.intValue());
    }
    
    public WorkplaceCategoryDetail update(WorkplaceCategoryDetail workplaceCategoryDetail) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.WORKPLACE_CATEGORY_ID + " = ?,"
                + Columns.SALARY_RANGE_ID + " = ?,"
                + Columns.NUMBER_OF_EMPLOYEE + " = ?,"
                + Columns.NUMBER_OF_STUDENT + " = ?,"
                + Columns.NUMBER_OF_BED + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            workplaceCategoryDetail.getWorkplaceCategoryId(),
            workplaceCategoryDetail.getSalaryRangeId(),
            workplaceCategoryDetail.getNumberOfEmployee(),
            workplaceCategoryDetail.getNumberOfStudent(),
            workplaceCategoryDetail.getNumberOfBed(),
            workplaceCategoryDetail.getId()
        });
        workplaceCategoryDetail = findById(workplaceCategoryDetail.getId());
        return workplaceCategoryDetail;
    }
    
    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}


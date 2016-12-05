
package com.vsquaresystem.safedeals.incomeslab;

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
public class IncomeslabDAL {
    
     public static final String TABLE_NAME="income_slab";
        private final JdbcTemplate jdbcTemplate;
        private final SimpleJdbcInsert insertIncome_slab;
        
        public static final class Columns{
            
                public static final String ID="id";
                public static final String MIN_RANGE="min_range";
                public static final String MAX_RANGE="max_range";
                public static final String BANK_ID="bank_id";
                public static final String PERCENTAGE_DEDUCTION="percentage_deduction";
                
        };              

        @Autowired
        public IncomeslabDAL(DataSource dataSource) {
            jdbcTemplate = new JdbcTemplate(dataSource);
            insertIncome_slab= new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(TABLE_NAME)
                    .usingColumns(
                            IncomeslabDAL.Columns.MIN_RANGE,
                            IncomeslabDAL.Columns.MAX_RANGE,
                            IncomeslabDAL.Columns.BANK_ID,
                            IncomeslabDAL.Columns.PERCENTAGE_DEDUCTION)
                            .usingGeneratedKeyColumns(Columns.ID);        
        }
        
        public List<Incomeslab> findAll(Integer offset) {
            String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
            return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Incomeslab.class));
        }
        
        public Incomeslab findById(Integer id) {
		String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
		return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Incomeslab.class));
	}
        
        public Incomeslab insert(Incomeslab incomeslab) {
            Map<String, Object>parameters = new HashMap<>();
            parameters.put(IncomeslabDAL.Columns.MIN_RANGE,  incomeslab.getMinRange());
            parameters.put(IncomeslabDAL.Columns.MAX_RANGE,  incomeslab.getMaxRange());
            parameters.put(IncomeslabDAL.Columns.BANK_ID,  incomeslab.getBankId());
            parameters.put(IncomeslabDAL.Columns.PERCENTAGE_DEDUCTION,  incomeslab.getPercentageDeduction());
            
            
            Number newId = insertIncome_slab.executeAndReturnKey(parameters);
        	incomeslab = findById(newId.intValue());
		return incomeslab;
        }
        
        public List<Incomeslab> findByBankId(Integer bankId) {
		String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.BANK_ID + " = ?";
		
		return jdbcTemplate.query(sqlQuery, new Object[]{bankId}, new BeanPropertyRowMapper<>(Incomeslab.class));
	}

        public Incomeslab update(Incomeslab incomeslab) {
		String sqlQuery = "UPDATE " + TABLE_NAME + " SET " + IncomeslabDAL.Columns.MIN_RANGE + " = ?, " + IncomeslabDAL.Columns.MAX_RANGE + " = ?, "+ IncomeslabDAL.Columns.BANK_ID  + " = ?, "+ IncomeslabDAL.Columns.PERCENTAGE_DEDUCTION  + " = ? WHERE " + IncomeslabDAL.Columns.ID + " = ?";
		Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
                        incomeslab.getMinRange(),
                        incomeslab.getMaxRange(),
                        incomeslab.getBankId(),
                        incomeslab.getPercentageDeduction()});
                        
                incomeslab = findById(incomeslab.getId());
                return incomeslab;
        }
        
        public void delete(Integer id) {
		String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
		jdbcTemplate.update(sqlQuery, new Object[]{true, id});
	}
}
    


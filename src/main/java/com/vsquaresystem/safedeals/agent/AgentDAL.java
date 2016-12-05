
package com.vsquaresystem.safedeals.agent;

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
public class AgentDAL {
    
    public static final String TABLE_NAME = "agent";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAgent;
    
    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String ADDRESS = "address";
        public static final String AGENCY_ID = "agency_id";
        
    };
    
    @Autowired
    public AgentDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertAgent = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.DESCRIPTION,
                        Columns.ADDRESS,
                        Columns.AGENCY_ID)
                        
                .usingGeneratedKeyColumns(Columns.ID);
    }
    
    public List<Agent> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Agent.class));
    }
    
    public List<Agent> findAllAgents() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(Agent.class));
    }
    
    public Agent findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Agent.class));
    }
    
    public Agent insert(Agent agent) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, agent.getName());
        parameters.put(Columns.DESCRIPTION, agent.getDescription());
        parameters.put(Columns.ADDRESS, agent.getAddress());
        parameters.put(Columns.AGENCY_ID, agent.getAgencyId());
        


        Number newId = insertAgent.executeAndReturnKey(parameters);
        agent = findById(newId.intValue());
        return agent;
    }

    public Agent findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, new BeanPropertyRowMapper<>(Agent.class));
    }
    
    public Agent update(Agent agent) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " = ? ," 
                + Columns.DESCRIPTION + " = ? ," 
                + Columns.ADDRESS + " = ? ," 
                + Columns.AGENCY_ID + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            agent.getName(),
            agent.getDescription(),
            agent.getAddress(),
            agent.getAgencyId(),
            agent.getId()});
        agent = findById(agent.getId());
        return agent;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}


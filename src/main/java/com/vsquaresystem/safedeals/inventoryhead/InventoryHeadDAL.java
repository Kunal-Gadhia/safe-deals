package com.vsquaresystem.safedeals.inventoryhead;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class InventoryHeadDAL {

    public static final String TABLE_NAME = "inventory_head";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertInventoryHead;

    public static final class Columns {

        public static final String ID = "id";
        public static final String PROJECT_ID = "project_id";
        public static final String NO_OF_BHK = "no_of_bhk";
        public static final String PROPERTY_CATEGORY_ID = "property_category_id";
        public static final String TOTAL_AREA = "total_area";
        public static final String PRICE_PER_SQFT = "price_per_sqft";
        public static final String OFFERED_PRICE = "offered_price";
        public static final String BUILDING_NAME = "building_name";
        public static final String TOTAL_UNITS = "total_uints";
    };

    @Autowired
    public InventoryHeadDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertInventoryHead = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.PROJECT_ID,
                        Columns.NO_OF_BHK,
                        Columns.PROPERTY_CATEGORY_ID,
                        Columns.TOTAL_AREA,
                        Columns.PRICE_PER_SQFT,
                        Columns.OFFERED_PRICE,
                        Columns.BUILDING_NAME,
                        Columns.TOTAL_UNITS
                )
                .usingGeneratedKeyColumns(Columns.ID);

    }

    public List<InventoryHead> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(InventoryHead.class));
    }

    public InventoryHead findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(InventoryHead.class));
    }

    public InventoryHead insert(InventoryHead inventoryHead) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.PROJECT_ID, inventoryHead.getProjectId());
        parameters.put(Columns.NO_OF_BHK, inventoryHead.getNoOfBhk());
        parameters.put(Columns.PROPERTY_CATEGORY_ID, inventoryHead.getPropertyCategoryId());
        parameters.put(Columns.TOTAL_AREA, inventoryHead.getTotalArea());
        parameters.put(Columns.PRICE_PER_SQFT, inventoryHead.getPricePerSqft());
        parameters.put(Columns.OFFERED_PRICE, inventoryHead.getOfferedPrice());
        parameters.put(Columns.BUILDING_NAME, inventoryHead.getBuildingName());
        parameters.put(Columns.TOTAL_UNITS, inventoryHead.getTotalUnits());

        Number newId = insertInventoryHead.executeAndReturnKey(parameters);
        inventoryHead = findById(newId.intValue());
        return inventoryHead;
    }

    public InventoryHead update(InventoryHead inventoryHead) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.PROJECT_ID + "=?, "
                + Columns.NO_OF_BHK + "=?, "
                + Columns.PROPERTY_CATEGORY_ID + "=?, "
                + Columns.TOTAL_AREA + "=?, "
                + Columns.PRICE_PER_SQFT + "=?, "
                + Columns.OFFERED_PRICE + "=?, "
                + Columns.BUILDING_NAME + "=?, "
                + Columns.TOTAL_UNITS + "=?, "
                + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            inventoryHead.getProjectId(),
            inventoryHead.getNoOfBhk(),
            inventoryHead.getPropertyCategoryId(),
            inventoryHead.getTotalArea(),
            inventoryHead.getPricePerSqft(),
            inventoryHead.getOfferedPrice(),
            inventoryHead.getBuildingName(),
            inventoryHead.getTotalUnits(),
            inventoryHead.getId()
        });
        inventoryHead = findById(inventoryHead.getId());
        return inventoryHead;
    }
    
     public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

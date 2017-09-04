package com.vsquaresystem.safedeals.inventory;

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
public class InventoryDAL {

    public static final String TABLE_NAME = "inventory";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertInventory;

    public static final class Columns {

        public static final String ID = "id";
        public static final String PROJECT_ID = "project_id";
        public static final String NO_OF_BHK = "no_of_bhk";
        public static final String PROPERTY_TYPE_ID = "property_type_id";
        public static final String TOTAL_UNITS = "total_units";
        public static final String UNIT_NO = "unit_no";
        public static final String FLOOR_NO = "floor_no";
        public static final String BUILDING_NAME = "building_name";
        public static final String PRICE_PER_SQFT = "price_per_sqft";
        public static final String TOTAL_AREA = "total_area";
        public static final String OFFERED_PRICE = "offered_price";
        public static final String NO_OF_BALCONY = "no_of_balcony";
        public static final String NO_OF_WASHROOM = "no_of_washroom";
        public static final String OPEN_TERRACE = "open_terrace";
        public static final String IS_AVAILABLE = "is_available";
        public static final String IS_RESERVED = "is_reserved";
        public static final String IS_SOLD = "is_sold";

    };

    @Autowired
    public InventoryDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertInventory = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.PROJECT_ID,
                        Columns.NO_OF_BHK,
                        Columns.PROPERTY_TYPE_ID,
                        Columns.TOTAL_UNITS,
                        Columns.UNIT_NO,
                        Columns.FLOOR_NO,
                        Columns.BUILDING_NAME,
                        Columns.PRICE_PER_SQFT,
                        Columns.TOTAL_AREA,
                        Columns.OFFERED_PRICE,
                        Columns.NO_OF_BALCONY,
                        Columns.NO_OF_WASHROOM,
                        Columns.OPEN_TERRACE,
                        Columns.IS_AVAILABLE,
                        Columns.IS_RESERVED,
                        Columns.IS_SOLD
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Inventory> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Inventory.class));
    }

    public Inventory findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Inventory.class));
    }

    public Inventory insert(Inventory inventory) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.PROJECT_ID, inventory.getProjectId());
        parameters.put(Columns.NO_OF_BHK, inventory.getNoOfBhk());
        parameters.put(Columns.PROPERTY_TYPE_ID, inventory.getPropertyTypeId());
        parameters.put(Columns.TOTAL_UNITS, inventory.getTotalUnits());
        parameters.put(Columns.UNIT_NO, inventory.getUnitNo());
        parameters.put(Columns.FLOOR_NO, inventory.getFloorNo());
        parameters.put(Columns.BUILDING_NAME, inventory.getBuildingName());
        parameters.put(Columns.PRICE_PER_SQFT, inventory.getPricePerSqft());
        parameters.put(Columns.TOTAL_AREA, inventory.getTotalArea());
        parameters.put(Columns.OFFERED_PRICE, inventory.getOfferedPrice());
        parameters.put(Columns.NO_OF_BALCONY, inventory.getNoOfBalcony());
        parameters.put(Columns.NO_OF_WASHROOM, inventory.getNoOfWashroom());
        if (inventory.getOpenTerrace() == null) {
            parameters.put(Columns.OPEN_TERRACE, 0);
        } else {
            parameters.put(Columns.OPEN_TERRACE, inventory.getOpenTerrace());
        }
        if (inventory.getIsAvailable() == null) {
            parameters.put(Columns.IS_AVAILABLE, 0);
        } else {
            parameters.put(Columns.IS_AVAILABLE, inventory.getIsAvailable());
        }
        if (inventory.getIsReserved() == null) {
            parameters.put(Columns.IS_RESERVED, 0);
        } else {
            parameters.put(Columns.IS_RESERVED, inventory.getIsReserved());
        }
        if (inventory.getIsSold() == null) {
            parameters.put(Columns.IS_SOLD, 0);
        } else {
            parameters.put(Columns.IS_SOLD, inventory.getIsSold());
        }
        Number newId = insertInventory.executeAndReturnKey(parameters);
        inventory = findById(newId.intValue());
        return inventory;
    }

//    public Inventory update(Inventory inventory) {
//        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
//                + Columns.PROJECT_ID + " = ?, "
//                + Columns.NO_OF_BHK + " = ?, "
//                + Columns.PROPERTY_TYPE_ID + " = ?, "
//                + Columns.TOTAL_UNITS + " = ?, "
//                + Columns.UNIT_NO + " = ?, "
//                + Columns.FLOOR_NO + " = ?, "
//                + Columns.BUILDING_NAME + " = ?, "
//                + Columns.PRICE_PER_SQFT + " = ?, "
//                + Columns.TOTAL_AREA + " = ?, "
//                + Columns.OFFERED_PRICE + " = ?, "
//                + Columns.NO_OF_BALCONY + " = ?, "
//                + Columns.NO_OF_WASHROOM + " = ?, "
//                + Columns.OPEN_TERRACE + " = ?, "
//                + Columns.IS_AVAILABLE + " = ?, "
//                + Columns.IS_RESERVED + " = ?, "
//                + Columns.IS_SOLD + " = ? WHERE " 
//                + Columns.ID + " = ?";
//        jdbcTemplate.update(sqlQuery, new Object[]{
//            inventory.getProjectId(),
//            inventory.getNoOfBhk(),
//            inventory.getPropertyTypeId(),
//            inventory.getTotalUnits(),
//            inventory.getUnitNo(),
//            inventory.getFloorNo(),
//            inventory.getBuildingName(),
//            inventory.getPricePerSqft(),
//            inventory.getTotalArea(),
//            inventory.getOfferedPrice(),
//            inventory.getNoOfBalcony(),
//            inventory.getNoOfWashroom(),
//            inventory.getOpenTerrace(),
//            inventory.getIsAvailable(),
//            inventory.getIsReserved(),
//            inventory.getIsSold()
//        });
//        inventory = findById(inventory.getId());
//        return inventory;
//    }
    public Inventory update(Inventory inventory) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.PROJECT_ID + "=?, "
                + Columns.NO_OF_BHK + "=?, "
                + Columns.PROPERTY_TYPE_ID + "=?, "
                + Columns.TOTAL_UNITS + "=?, "
                + Columns.UNIT_NO + "=?, "
                + Columns.FLOOR_NO + "=?, "
                + Columns.BUILDING_NAME + "=?, "
                + Columns.PRICE_PER_SQFT + "=?, "
                + Columns.TOTAL_AREA + "=?, "
                + Columns.OFFERED_PRICE + "=?, "
                + Columns.NO_OF_BALCONY + "=?, "
                + Columns.NO_OF_WASHROOM + "=?, "
                + Columns.OPEN_TERRACE + "=?, "
                + Columns.IS_AVAILABLE + "=?, "
                + Columns.IS_RESERVED + "=?, "
                + Columns.IS_SOLD + "=?  WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery,
                new Object[]{
                    inventory.getProjectId(),
                    inventory.getNoOfBhk(),
                    inventory.getPropertyTypeId(),
                    // Inventory.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(Inventory.getLocationCategories()),
                    inventory.getTotalUnits(),
                    inventory.getUnitNo(),
                    inventory.getFloorNo(),
                    inventory.getBuildingName(),
                    inventory.getPricePerSqft(),
                    inventory.getTotalArea(),
                    inventory.getOfferedPrice(),
                    inventory.getNoOfBalcony(),
                    inventory.getNoOfWashroom(),
                    inventory.getOpenTerrace(),
                    inventory.getIsAvailable(),
                    inventory.getIsReserved(),
                    inventory.getIsSold(),
                    inventory.getId()
                }
        );
        inventory = findById(inventory.getId());
        return inventory;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

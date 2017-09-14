/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.inventorydetail;

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

/**
 *
 * @author hp
 */
@Repository
public class InventoryDetailDAL {

    public static final String TABLE_NAME = "inventory_detail";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertInventoryDetail;

    public static final class Columns {

        public static final String ID = "id";
        public static final String INVENTORY_HEAD_ID = "inventory_head_id";
        public static final String DESCRIPTION = "description";
        public static final String FACING = "facing";
        public static final String FLOOR_NO = "floor_no";
        public static final String NO_OF_BALCONY = "no_of_balcony";
        public static final String NO_OF_WASHROOM = "no_of_washroom";
        public static final String OPEN_TERRACE = "open_terrace";
        public static final String OPEN_LAND = "open_land";
        public static final String IS_AVAILABLE = "is_available";
        public static final String IS_RESERVED = "is_reserved";
        public static final String IS_SOLD = "is_sold";
        public static final String OFFER_AMOUNT = "offer_amount";
        public static final String VALIDITY = "validity";
        public static final String EXTRA_CHARGES = "extra_charges";
        public static final String UNIT_NO = "unit_no";

    };

    @Autowired
    public InventoryDetailDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertInventoryDetail = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.INVENTORY_HEAD_ID,
                        Columns.DESCRIPTION,
                        Columns.FACING,
                        Columns.FLOOR_NO,
                        Columns.NO_OF_BALCONY,
                        Columns.NO_OF_WASHROOM,
                        Columns.OPEN_TERRACE,
                        Columns.OPEN_LAND,
                        Columns.IS_AVAILABLE,
                        Columns.IS_RESERVED,
                        Columns.IS_SOLD,
                        Columns.OFFER_AMOUNT,
                        Columns.VALIDITY,
                        Columns.EXTRA_CHARGES,
                        Columns.UNIT_NO
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<InventoryDetail> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(InventoryDetail.class));
    }
    
    public List<InventoryDetail> findByInventoryHeadId(Integer inventoryHeadId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.INVENTORY_HEAD_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{inventoryHeadId}, new BeanPropertyRowMapper<>(InventoryDetail.class));
    }

    public InventoryDetail findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(InventoryDetail.class));
    }

    public InventoryDetail insert(InventoryDetail inventoryDetail) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.INVENTORY_HEAD_ID, inventoryDetail.getInventoryHeadId());
        parameters.put(Columns.DESCRIPTION, inventoryDetail.getDescription());
        parameters.put(Columns.FACING, inventoryDetail.getFacing().name());
        parameters.put(Columns.FLOOR_NO, inventoryDetail.getFloorNo());
        parameters.put(Columns.NO_OF_BALCONY, inventoryDetail.getNoOfBalcony());
        parameters.put(Columns.NO_OF_WASHROOM, inventoryDetail.getNoOfWashroom());

        if (inventoryDetail.getOpenTerrace() == null) {
            parameters.put(Columns.OPEN_TERRACE, 0);
        } else {
            parameters.put(Columns.OPEN_TERRACE, inventoryDetail.getOpenTerrace());
        }
        if (inventoryDetail.getOpenLand() == null) {
            parameters.put(Columns.OPEN_TERRACE, 0);
        } else {
            parameters.put(Columns.OPEN_TERRACE, inventoryDetail.getOpenLand());
        }
        if (inventoryDetail.getIsAvailable() == null) {
            parameters.put(Columns.IS_AVAILABLE, 0);
        } else {
            parameters.put(Columns.IS_AVAILABLE, inventoryDetail.getIsAvailable());
        }
        if (inventoryDetail.getIsReserved() == null) {
            parameters.put(Columns.IS_RESERVED, 0);
        } else {
            parameters.put(Columns.IS_RESERVED, inventoryDetail.getIsReserved());
        }
        if (inventoryDetail.getIsSold() == null) {
            parameters.put(Columns.IS_SOLD, 0);
        } else {
            parameters.put(Columns.IS_SOLD, inventoryDetail.getIsSold());
        }
        parameters.put(Columns.OFFER_AMOUNT, inventoryDetail.getOfferAmount());
        parameters.put(Columns.VALIDITY, inventoryDetail.getValidity());
        parameters.put(Columns.EXTRA_CHARGES, inventoryDetail.getExtraCharges());
        parameters.put(Columns.UNIT_NO, inventoryDetail.getUnitNo());
        Number newId = insertInventoryDetail.executeAndReturnKey(parameters);
        inventoryDetail = findById(newId.intValue());
        return inventoryDetail;
    }

    public InventoryDetail update(InventoryDetail inventoryDetail) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.INVENTORY_HEAD_ID + "=?, "
                + Columns.DESCRIPTION + "=?, "
                + Columns.FACING + "=?, "
                + Columns.FLOOR_NO + "=?, "
                + Columns.NO_OF_BALCONY + "=?, "
                + Columns.NO_OF_WASHROOM + "=?, "
                + Columns.OPEN_TERRACE + "=?, "
                + Columns.OPEN_LAND + "=?, "
                + Columns.IS_AVAILABLE + "=?, "
                + Columns.IS_RESERVED + "=?, "
                + Columns.IS_SOLD + "=?, "
                + Columns.OFFER_AMOUNT + "=?, "
                + Columns.VALIDITY + "=?, "
                + Columns.EXTRA_CHARGES + "=?, "
                + Columns.UNIT_NO + "=?  WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery,
                new Object[]{
                    inventoryDetail.getInventoryHeadId(),
                    inventoryDetail.getDescription(),
                    inventoryDetail.getFacing().name(),
                    inventoryDetail.getFloorNo(),
                    inventoryDetail.getNoOfBalcony(),
                    inventoryDetail.getNoOfWashroom(),
                    inventoryDetail.getOpenTerrace(),
                    inventoryDetail.getOpenLand(),
                    inventoryDetail.getIsAvailable(),
                    inventoryDetail.getIsReserved(),
                    inventoryDetail.getIsSold(),
                    inventoryDetail.getOfferAmount(),
                    inventoryDetail.getValidity(),
                    inventoryDetail.getExtraCharges(),
                    inventoryDetail.getUnitNo(),
                    inventoryDetail.getId()
                }
        );
        inventoryDetail = findById(inventoryDetail.getId());
        return inventoryDetail;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
}

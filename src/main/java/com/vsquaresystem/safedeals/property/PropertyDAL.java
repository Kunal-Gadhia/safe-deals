package com.vsquaresystem.safedeals.property;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsquaresystem.safedeals.city.City;
import com.vsquaresystem.safedeals.city.CityDAL;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyDAL {

    @Autowired
    private CityDAL cityDAL;

    private static Integer srNumber = 0;

    public static final class Columns {

        public static final String ID = "id";
        public static final String PROPERTY_ID = "property_id";
        public static final String NAME = "name";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_ID = "location_id";
        public static final String SUB_LOCATION = "sub_location";
        public static final String PROPERTY_TYPE = "type_of_property";
        public static final String PROPERTY_SIZE = "size_of_property";
        public static final String SIZE = "size";
        public static final String PRICE_RANGE = "price_range";
        public static final String CONSTRUCTION_STAGE = "construction_stage";
        public static final String POSSESSION_DATE = "possession_date";
        public static final String YEAR_OF_CONSTRUCTION = "year_of_construction";
        public static final String FLOOR_NUMBER = "floor_number";
        public static final String TOTAL_FLOORS = "total_floors";
        public static final String ENTRY_FACING = "entry_facing";
//      public static final String BUILDING_CONDITION = "building_condition";
        public static final String PROJECT_ID = "project_id";
        public static final String MAJOR_APPROACH_ROAD = "major_approach_road";
        //    public static final String PUBLIC_TRANSPORT = "public_transport";
        public static final String DISCOUNT = "discount";
        public static final String OFFER_VALID_TILL = "offer_valid_till";
        public static final String OFFERED_PRICE = "offered_price";
        public static final String PAYMENT_SCHEDULE = "payment_schedule";
        public static final String DOWNPAYMENT = "downpayment";
        public static final String WORKPLACES = "workplaces";
        public static final String PROJECTS_NEARBY = "projects_nearby";
        public static final String BASIC_AMENITIES = "basic_amenities";
        public static final String LUXURY_AMENITIES = "luxury_amenities";
        public static final String OWNERSHIP_PROOF = "ownership_proof";
        public static final String APPROVED_BANKS = "approved_banks";
        public static final String SD_VERIFIED = "sd_verified";
        public static final String PRIVATE_AMENITIES = "private_amenities";
        //commission spelling
        public static final String SELLER_COMMISION_AGREEMENT = "seller_commision_agreement";
        public static final String SALABLE_AREA = "salable_area";
        public static final String CARPET_AREA = "carpet_area";
        public static final String BUILD_UP_AREA = "build_up_area";
        public static final String BALCONY_COUNT = "balcony_count";
        public static final String TOILET_COUNT = "toilet_count";
        public static final String OPEN_TERRACE = "open_terrace";
        public static final String OPEN_LAND = "open_land";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String FEATURED_PROPERTY = "featured_property";
        public static final String BUS = "bus";
        public static final String AUTO = "auto";
        public static final String TAXI = "taxi";
        public static final String METRO = "metro";
        public static final String DISTANCE = "distance";
        public static final String UNIT = "unit";
        public static final String BOOKING_AMOUNT = "booking_amount";
        public static final String START_OF_CONSTRUCTION = "start_of_construction";
        public static final String COMPLETION_OF_PLINTH = "completion_of_plinth";
        public static final String EACH_SLAB = "each_slab";
        public static final String BRICK_WORK = "brick_work";
        public static final String PLASTERING = "plastering";
        public static final String FINISHING_WORK = "finishing_work";

        public static final String BRICK_WORK_PLASTERING = "bwp";
        public static final String FLOORING = "flooring";

        public static final String KITCHEN = "kitchen";
        public static final String SANITORY = "sanitory";
        public static final String DOORS = "doors";
        public static final String WINDOWS = "windows";
        public static final String PAINTINGS = "paintings";
        public static final String FALSE_CEILING = "false_ceiling";
        public static final String ELECTRIC_FITTINGS = "electric_fittings";
        public static final String LIFT = "lift";
        public static final String STRUCTURE = "structure";
        public static final String WALLS = "walls";
        public static final String WATER_PROOFING = "water_proofing";

    }

    public static final String TABLE_NAME = "property";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProperty;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PropertyDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertProperty = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.PROPERTY_ID,
                        Columns.CITY_ID,
                        Columns.LOCATION_ID,
                        Columns.SUB_LOCATION,
                        Columns.PROPERTY_TYPE,
                        Columns.PROPERTY_SIZE,
                        Columns.SIZE,
                        Columns.PRICE_RANGE,
                        Columns.CONSTRUCTION_STAGE,
                        Columns.POSSESSION_DATE,
                        Columns.YEAR_OF_CONSTRUCTION,
                        Columns.FLOOR_NUMBER,
                        Columns.TOTAL_FLOORS,
                        Columns.ENTRY_FACING,
                        //                        Columns.BUILDING_CONDITION,
                        Columns.PROJECT_ID,
                        Columns.MAJOR_APPROACH_ROAD,
                        //        Columns.PUBLIC_TRANSPORT,
                        Columns.DISCOUNT,
                        Columns.OFFERED_PRICE,
                        Columns.OFFER_VALID_TILL,
                        Columns.PAYMENT_SCHEDULE,
                        Columns.DOWNPAYMENT,
                        Columns.WORKPLACES,
                        Columns.PROJECTS_NEARBY,
                        Columns.BASIC_AMENITIES,
                        Columns.LUXURY_AMENITIES,
                        Columns.APPROVED_BANKS,
                        Columns.SD_VERIFIED,
                        Columns.PRIVATE_AMENITIES,
                        Columns.SELLER_COMMISION_AGREEMENT,
                        Columns.SALABLE_AREA,
                        Columns.CARPET_AREA,
                        Columns.BUILD_UP_AREA,
                        Columns.BALCONY_COUNT,
                        Columns.TOILET_COUNT,
                        Columns.OPEN_TERRACE,
                        Columns.OPEN_LAND,
                        Columns.LATITUDE,
                        Columns.LONGITUDE,
                        Columns.FEATURED_PROPERTY,
                        Columns.BUS,
                        Columns.AUTO,
                        Columns.TAXI,
                        Columns.METRO,
                        Columns.DISTANCE,
                        Columns.UNIT,
                        Columns.BOOKING_AMOUNT,
                        Columns.START_OF_CONSTRUCTION,
                        Columns.COMPLETION_OF_PLINTH,
                        Columns.EACH_SLAB,
                        Columns.BRICK_WORK,
                        Columns.PLASTERING,
                        Columns.FINISHING_WORK,
                        Columns.BRICK_WORK_PLASTERING,
                        Columns.FLOORING,
                        Columns.KITCHEN,
                        Columns.SANITORY,
                        Columns.DOORS,
                        Columns.WINDOWS,
                        Columns.PAINTINGS,
                        Columns.FALSE_CEILING,
                        Columns.ELECTRIC_FITTINGS,
                        Columns.LIFT,
                        Columns.STRUCTURE,
                        Columns.WALLS,
                        Columns.WATER_PROOFING
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Property> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, propertyRowMapper);
    }

    public Property findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, propertyRowMapper);
    }

    public List findSerialNo(String letter) {
        String sqlQuery = "SELECT " + Columns.PROPERTY_ID + " FROM " + TABLE_NAME + " WHERE deleted= FALSE AND " + Columns.PROPERTY_ID + " LIKE ? ";
        String letterLike = letter + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{letterLike}, new BeanPropertyRowMapper<>(Property.class));
    }

    public Property insert(Property property) throws JsonProcessingException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, property.getName());
        //parameters.put(Columns.PROPERTY_ID, property.getPropertyId());
        parameters.put(Columns.CITY_ID, property.getCityId());
        parameters.put(Columns.LOCATION_ID, property.getLocationId());
        parameters.put(Columns.SUB_LOCATION, property.getSubLocation());
        parameters.put(Columns.PROPERTY_TYPE, property.getPropertyType().name());
        parameters.put(Columns.PROPERTY_SIZE, property.getPropertySize());
        parameters.put(Columns.SIZE, property.getSize());
        parameters.put(Columns.PRICE_RANGE, property.getPriceRange());
        parameters.put(Columns.CONSTRUCTION_STAGE, property.getConstructionStage().name());
        parameters.put(Columns.POSSESSION_DATE, property.getPossessionDate());
        parameters.put(Columns.YEAR_OF_CONSTRUCTION, property.getYearOfConstruction());
        parameters.put(Columns.FLOOR_NUMBER, property.getFloorNumber());
        parameters.put(Columns.TOTAL_FLOORS, property.getTotalFloors());
        parameters.put(Columns.ENTRY_FACING, property.getEntryFacing().name());
//      parameters.put(Columns.BUILDING_CONDITION, property.getBuildingCondition().name() == "" ? null : mapper.writeValueAsString("null"));        
        parameters.put(Columns.PROJECT_ID, property.getProjectId());
        parameters.put(Columns.MAJOR_APPROACH_ROAD, property.getMajorApproachRoad());
//        parameters.put(Columns.PUBLIC_TRANSPORT, property.getPublicTransport() == null ? "[]" : mapper.writeValueAsString(property.getPublicTransport()));
        parameters.put(Columns.DISCOUNT, property.getDiscount());
        parameters.put(Columns.OFFERED_PRICE, property.getOfferedPrice());
        parameters.put(Columns.OFFER_VALID_TILL, property.getOfferValidTill());
        parameters.put(Columns.PAYMENT_SCHEDULE, property.getPaymentSchedule());
        parameters.put(Columns.DOWNPAYMENT, property.getDownpayment());
        parameters.put(Columns.WORKPLACES, property.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(property.getWorkplaces()));
        parameters.put(Columns.PROJECTS_NEARBY, property.getProjectsNearby() == null ? "[]" : mapper.writeValueAsString(property.getProjectsNearby()));
        parameters.put(Columns.BASIC_AMENITIES, property.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(property.getBasicAmenities()));
        parameters.put(Columns.LUXURY_AMENITIES, property.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(property.getLuxuryAmenities()));
        parameters.put(Columns.APPROVED_BANKS, property.getApprovedBanks() == null ? "[]" : mapper.writeValueAsString(property.getApprovedBanks()));

        if (property.getSdVerified() == null) {
            parameters.put(Columns.SD_VERIFIED, 0);
        } else {
            parameters.put(Columns.SD_VERIFIED, property.getSdVerified());

        }
        parameters.put(Columns.PRIVATE_AMENITIES, property.getPrivateAmenities() == null ? "[]" : mapper.writeValueAsString(property.getPrivateAmenities()));
        parameters.put(Columns.SELLER_COMMISION_AGREEMENT, property.getSellerCommisionAgreement());
        parameters.put(Columns.SALABLE_AREA, property.getSalableArea());
        parameters.put(Columns.CARPET_AREA, property.getCarpetArea());
        parameters.put(Columns.BUILD_UP_AREA, property.getBuildUpArea());
        parameters.put(Columns.BALCONY_COUNT, property.getBalconyCount());
        parameters.put(Columns.TOILET_COUNT, property.getToiletCount());
        parameters.put(Columns.OPEN_TERRACE, property.getOpenTerrace());
        parameters.put(Columns.OPEN_LAND, property.getOpenLand());
        parameters.put(Columns.LATITUDE, property.getLatitude());
        parameters.put(Columns.LONGITUDE, property.getLongitude());

        if (property.getFeaturedProperty() == null) {
            parameters.put(Columns.FEATURED_PROPERTY, 0);
        } else {
            parameters.put(Columns.FEATURED_PROPERTY, property.getFeaturedProperty());
        }

        //  parameters.put(Columns.FEATURED_PROPERTY, property.getFeaturedProperty());
        if (property.getBus() == null) {
            parameters.put(Columns.BUS, 0);
        } else {
            parameters.put(Columns.BUS, property.getBus());
        }

        if (property.getAuto() == null) {
            parameters.put(Columns.AUTO, 0);
        } else {
            parameters.put(Columns.AUTO, property.getAuto());
        }

        if (property.getTaxi() == null) {
            parameters.put(Columns.TAXI, 0);
        } else {
            parameters.put(Columns.TAXI, property.getTaxi());
        }

        if (property.getMetro() == null) {
            parameters.put(Columns.METRO, 0);
        } else {
            parameters.put(Columns.METRO, property.getMetro());
        }

        parameters.put(Columns.DISTANCE, property.getDistance());
        parameters.put(Columns.UNIT, property.getUnit());

        City c = cityDAL.findById(property.getCityId());
        String cityName = c.getName();

        List cityN = findSerialNo(String.valueOf(cityName).substring(0, 3));
        if (cityN.size() <= 0) {
            srNumber = 1;
        } else {
            srNumber = cityN.size() + 1;
        }
        String propertyId = String.valueOf(cityName).substring(0, 3) + srNumber;
        parameters.put(Columns.PROPERTY_ID, propertyId);

        parameters.put(Columns.BOOKING_AMOUNT, property.getBookingAmount());
        parameters.put(Columns.START_OF_CONSTRUCTION, property.getStartOfConstruction());
        parameters.put(Columns.COMPLETION_OF_PLINTH, property.getCompletionOfPlinth());
        parameters.put(Columns.EACH_SLAB, property.getEachSlab());
        parameters.put(Columns.BRICK_WORK, property.getBrickWork());
        parameters.put(Columns.PLASTERING, property.getPlastering());
        parameters.put(Columns.FINISHING_WORK, property.getFinishingWork());

        parameters.put(Columns.BRICK_WORK_PLASTERING, property.getBrickWorkPlastering());
        parameters.put(Columns.FLOORING, property.getFlooring());
        parameters.put(Columns.KITCHEN, property.getKitchen());
        parameters.put(Columns.SANITORY, property.getSanitory());
        parameters.put(Columns.DOORS, property.getDoors());
        parameters.put(Columns.WINDOWS, property.getWindows());
        parameters.put(Columns.PAINTINGS, property.getPaintings());

        parameters.put(Columns.FALSE_CEILING, property.getFalseCeiling());
        parameters.put(Columns.ELECTRIC_FITTINGS, property.getElectricFitings());
        parameters.put(Columns.LIFT, property.getLift());
        parameters.put(Columns.STRUCTURE, property.getStructure());
        parameters.put(Columns.WALLS, property.getWalls());
        parameters.put(Columns.WATER_PROOFING, property.getWaterProofing());

        Number newId = insertProperty.executeAndReturnKey(parameters);
        property = findById(newId.intValue());
        return property;
    }

    public List<Property> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, propertyRowMapper);
    }

    public List<Property> findByCityAndLocation(Integer locationId, Integer cityId, Integer propertySize) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND "
                + Columns.LOCATION_ID + " =? AND "
                + Columns.CITY_ID + " =? AND "
                + Columns.PROPERTY_SIZE + " =?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId, cityId, propertySize}, propertyRowMapper);
    }

    public List<Property> findByMinAndMaxBudget(Integer minBudget, Integer maxBudget, Integer propertySize) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.OFFERED_PRICE + " >= ? AND "
                + Columns.OFFERED_PRICE + "<=? AND "
                + Columns.PROPERTY_SIZE + " =?";
        return jdbcTemplate.query(sqlQuery, new Object[]{minBudget, maxBudget, propertySize}, propertyRowMapper);
    }

    public List<Property> findByFilters(Integer cityId, Integer locationId, Integer propertySize, Integer minBudget, Integer maxBudget) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND "
                + Columns.CITY_ID + "= ? AND "
                + Columns.LOCATION_ID + "= ? AND "
                + Columns.PROPERTY_SIZE + "= ? AND "
                + Columns.OFFERED_PRICE + ">= ? AND "
                + Columns.OFFERED_PRICE + "<= ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{cityId, locationId, propertySize, minBudget, maxBudget}, propertyRowMapper);

    }

    public Property update(Property property) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.PROPERTY_ID + " =?,"
                + Columns.CITY_ID + " =?,"
                + Columns.LOCATION_ID + " =?,"
                + Columns.SUB_LOCATION + " =?,"
                + Columns.PROPERTY_TYPE + " =?,"
                + Columns.PROPERTY_SIZE + " =?,"
                + Columns.SIZE + " =?,"
                + Columns.PRICE_RANGE + " =?,"
                + Columns.CONSTRUCTION_STAGE + " =?,"
                + Columns.POSSESSION_DATE + " =?,"
                + Columns.YEAR_OF_CONSTRUCTION + " =?,"
                + Columns.FLOOR_NUMBER + " =?,"
                + Columns.TOTAL_FLOORS + " =?,"
                + Columns.ENTRY_FACING + " =?,"
                //                + Columns.BUILDING_CONDITION + " =?,"
                + Columns.PROJECT_ID + " =?,"
                + Columns.MAJOR_APPROACH_ROAD + " =?,"
                //                + Columns.PUBLIC_TRANSPORT + " =?,"
                + Columns.OFFERED_PRICE + " =?,"
                + Columns.DISCOUNT + " =?,"
                + Columns.OFFER_VALID_TILL + " =?,"
                + Columns.PAYMENT_SCHEDULE + " =?,"
                + Columns.DOWNPAYMENT + " =?,"
                + Columns.WORKPLACES + " =?,"
                + Columns.PROJECTS_NEARBY + " =?,"
                + Columns.BASIC_AMENITIES + " =?,"
                + Columns.LUXURY_AMENITIES + " =?,"
                + Columns.OWNERSHIP_PROOF + " =?,"
                + Columns.APPROVED_BANKS + " =?,"
                + Columns.SD_VERIFIED + " =?,"
                + Columns.PRIVATE_AMENITIES + " =?,"
                + Columns.SELLER_COMMISION_AGREEMENT + " =?,"
                + Columns.SALABLE_AREA + " =?,"
                + Columns.CARPET_AREA + " =?,"
                + Columns.BUILD_UP_AREA + " =?,"
                + Columns.BALCONY_COUNT + " =?,"
                + Columns.TOILET_COUNT + " =?,"
                + Columns.OPEN_TERRACE + " =?,"
                + Columns.OPEN_LAND + " =?,"
                + Columns.LATITUDE + " =?,"
                + Columns.LONGITUDE + " =?,"
                + Columns.FEATURED_PROPERTY + " =?,"
                + Columns.BUS + " =?,"
                + Columns.AUTO + " =?,"
                + Columns.TAXI + " =?,"
                + Columns.METRO + " =?,"
                + Columns.DISTANCE + " =?,"
                + Columns.UNIT + " =?,"
                + Columns.BOOKING_AMOUNT + " =?,"
                + Columns.START_OF_CONSTRUCTION + " =?,"
                + Columns.COMPLETION_OF_PLINTH + " =?,"
                + Columns.EACH_SLAB + " =?,"
                + Columns.BRICK_WORK + " =?,"
                + Columns.PLASTERING + " =?,"
                + Columns.FINISHING_WORK + " =?,"
                + Columns.BRICK_WORK_PLASTERING + " =?,"
                + Columns.FLOORING + " =?,"
                + Columns.KITCHEN + " =?,"
                + Columns.SANITORY + " =?,"
                + Columns.DOORS + " =?,"
                + Columns.WINDOWS + " =?,"
                + Columns.PAINTINGS + " =?,"
                + Columns.FALSE_CEILING + " =?,"
                + Columns.ELECTRIC_FITTINGS + " =?,"
                + Columns.LIFT + " =?,"
                + Columns.STRUCTURE + " =?,"
                + Columns.WALLS + " =?,"
                + Columns.WATER_PROOFING + " =? WHERE "
                + Columns.ID + " =?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            property.getName(),
            property.getPropertyId(),
            property.getCityId(),
            property.getLocationId(),
            property.getSubLocation(),
            property.getPropertyType().name(),
            property.getPropertySize(),
            property.getSize(),
            property.getPriceRange(),
            property.getConstructionStage().name(),
            property.getPossessionDate(),
            property.getYearOfConstruction(),
            property.getFloorNumber(),
            property.getTotalFloors(),
            property.getEntryFacing().name(),
            //            property.getBuildingCondition().name(),
            property.getProjectId(),
            property.getMajorApproachRoad(),
            //            property.getPublicTransport() == null ? "[]" : mapper.writeValueAsString(property.getPublicTransport()),
            property.getOfferedPrice(),
            property.getDiscount(),
            property.getOfferValidTill(),
            property.getPaymentSchedule(),
            property.getDownpayment(),
            property.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(property.getWorkplaces()),
            property.getProjectsNearby() == null ? "[]" : mapper.writeValueAsString(property.getProjectsNearby()),
            property.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(property.getBasicAmenities()),
            property.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(property.getLuxuryAmenities()),
            property.getOwnershipProof() == null ? "[]" : mapper.writeValueAsString(property.getOwnershipProof()),
            property.getApprovedBanks() == null ? "[]" : mapper.writeValueAsString(property.getApprovedBanks()),
            property.getSdVerified(),
            property.getPrivateAmenities() == null ? "[]" : mapper.writeValueAsString(property.getPrivateAmenities()),
            property.getSellerCommisionAgreement(),
            property.getSalableArea(),
            property.getCarpetArea(),
            property.getBuildUpArea(),
            property.getBalconyCount(),
            property.getToiletCount(),
            property.getOpenTerrace(),
            property.getOpenLand(),
            property.getLatitude(),
            property.getLongitude(),
            property.getFeaturedProperty(),
            property.getBus(),
            property.getAuto(),
            property.getTaxi(),
            property.getMetro(),
            property.getDistance(),
            property.getUnit(),
            property.getBookingAmount(),
            property.getStartOfConstruction(),
            property.getCompletionOfPlinth(),
            property.getEachSlab(),
            property.getBrickWork(),
            property.getPlastering(),
            property.getFinishingWork(),
            property.getBrickWorkPlastering(),
            property.getFlooring(),
            property.getKitchen(),
            property.getSanitory(),
            property.getDoors(),
            property.getWindows(),
            property.getPaintings(),
            property.getFalseCeiling(),
            property.getElectricFitings(),
            property.getLift(),
            property.getStructure(),
            property.getWalls(),
            property.getWaterProofing(),
            property.getId()});
        property = findById(property.getId());
        return property;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public List<Property> findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{name}, propertyRowMapper);
    }

    public List<Property> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, propertyRowMapper);
    }

    private final RowMapper<Property> propertyRowMapper = new RowMapper<Property>() {

        @Override
        public Property mapRow(ResultSet rs, int i) throws SQLException {
            Property property = new Property();
            property.setId(rs.getInt(Columns.ID));
            property.setPropertyId(rs.getString(Columns.PROPERTY_ID));

            property.setName(rs.getString(Columns.NAME));
            property.setSubLocation(rs.getString(Columns.SUB_LOCATION));
            property.setCityId(rs.getInt(Columns.CITY_ID));
            if (rs.wasNull()) {
                property.setCityId(null);
            }

            property.setLocationId(rs.getInt(Columns.LOCATION_ID));
            if (rs.wasNull()) {
                property.setLocationId(null);
            }

            if (rs.getString(Columns.PROPERTY_TYPE) != null) {
                property.setPropertyType(PropertyType.valueOf(rs.getString(Columns.PROPERTY_TYPE)));
            }

            property.setPropertySize(rs.getInt(Columns.PROPERTY_SIZE));
            if (rs.wasNull()) {
                property.setPropertySize(null);
            }
//            property.setPropertySize(rs.getInt(Columns.PROPERTY_SIZE));

            property.setSize(rs.getDouble(Columns.SIZE));

            property.setPriceRange(rs.getDouble(Columns.PRICE_RANGE));

            if (rs.getString(Columns.CONSTRUCTION_STAGE) != null) {
                property.setConstructionStage(ConstructionStage.valueOf(rs.getString(Columns.CONSTRUCTION_STAGE)));
            }

            property.setPossessionDate(rs.getDate(Columns.POSSESSION_DATE));

            property.setYearOfConstruction(rs.getDate(Columns.YEAR_OF_CONSTRUCTION));

            property.setFloorNumber(rs.getInt(Columns.FLOOR_NUMBER));

            property.setTotalFloors(rs.getInt(Columns.TOTAL_FLOORS));

            if (rs.getString(Columns.ENTRY_FACING) != null) {
                property.setEntryFacing(EntryFacing.valueOf(rs.getString(Columns.ENTRY_FACING)));
            }

//            if (rs.getString(Columns.BUILDING_CONDITION) != null) {
//                property.setBuildingCondition(BuildingCondition.valueOf(rs.getString(Columns.BUILDING_CONDITION)));
//            }
            property.setProjectId(rs.getInt(Columns.PROJECT_ID));
            if (rs.wasNull()) {
                property.setProjectId(null);
            }

            property.setMajorApproachRoad(rs.getInt(Columns.MAJOR_APPROACH_ROAD));
            if (rs.wasNull()) {
                property.setMajorApproachRoad(null);
            }

//            String publicTransportList = rs.getString(Columns.PUBLIC_TRANSPORT);
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                List<Integer> publicTransport = mapper.readValue(publicTransportList, new TypeReference<List<Integer>>() {
//                });
//                property.setPublicTransport(publicTransport);
//            } catch (IOException ex) {
//                throw new RuntimeException("Error parsing publicTransportList: '" + publicTransportList + "' ", ex);
//            }
//            property.setCompletionDate(rs.getDate(PropertyDAL.Columns.COMPLETION_DATE));
//            property.setTotalBuildings(rs.getInt(PropertyDAL.Columns.TOTAL_BUILDINGS));
//            property.setTotalFloors(rs.getInt(PropertyDAL.Columns.TOTAL_FLOORS));
//            property.setTotalUnits(rs.getInt(PropertyDAL.Columns.TOTAL_UNITS));
            property.setOfferedPrice(rs.getDouble(Columns.OFFERED_PRICE));
            property.setDiscount(rs.getDouble(Columns.DISCOUNT));
            property.setOfferValidTill(rs.getDate(Columns.OFFER_VALID_TILL));
            property.setPaymentSchedule(rs.getString(Columns.PAYMENT_SCHEDULE));
            property.setDownpayment(rs.getInt(Columns.DOWNPAYMENT));
            String workplacesList = rs.getString(Columns.WORKPLACES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> workplace = mapper.readValue(workplacesList, new TypeReference<List<Integer>>() {
                });
                property.setWorkplaces(workplace);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing workplacesList: '" + workplacesList + "' ", ex);
            }
            String projectsNearbyList = rs.getString(Columns.PROJECTS_NEARBY);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> projectNearby = mapper.readValue(projectsNearbyList, new TypeReference<List<Integer>>() {
                });
                property.setProjectsNearby(projectNearby);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing projectsNearbyList: '" + projectsNearbyList + "' ", ex);
            }
            String basicAmenitiesList = rs.getString(Columns.BASIC_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> basicAmenities = mapper.readValue(basicAmenitiesList, new TypeReference<List<Integer>>() {
                });
                property.setBasicAmenities(basicAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing basicAmenitiesList: '" + basicAmenitiesList + "' ", ex);
            }
            String luxuryAmenitiesList = rs.getString(Columns.LUXURY_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> luxuryAmenities = mapper.readValue(luxuryAmenitiesList, new TypeReference<List<Integer>>() {
                });
                property.setLuxuryAmenities(luxuryAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing luxuryAmenitiesList: '" + luxuryAmenitiesList + "' ", ex);
            }
            String ownershipProofList = rs.getString(Columns.OWNERSHIP_PROOF);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> ownershipProof = mapper.readValue(ownershipProofList, new TypeReference<List<String>>() {
                });
                property.setOwnershipProof(ownershipProof);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing ownershipProofList: '" + ownershipProofList + "' ", ex);
            }
            String approvedBanksList = rs.getString(Columns.APPROVED_BANKS);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> approvedBanks = mapper.readValue(approvedBanksList, new TypeReference<List<Integer>>() {
                });
                property.setApprovedBanks(approvedBanks);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing approvedBanksList: '" + approvedBanksList + "' ", ex);
            }
            property.setSdVerified(rs.getBoolean(Columns.SD_VERIFIED));

            String privateAmenitiesList = rs.getString(Columns.PRIVATE_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> privateAmenities = mapper.readValue(privateAmenitiesList, new TypeReference<List<Integer>>() {
                });
                property.setPrivateAmenities(privateAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing privateAmenitiesList: '" + privateAmenitiesList + "' ", ex);
            }

            property.setSellerCommisionAgreement(rs.getBoolean(Columns.SELLER_COMMISION_AGREEMENT));
            // property.setProjectTestimonial(rs.getString(PropertyDAL.Columns.PROJECT_TESTIMONIAL));
            property.setSalableArea(rs.getDouble(Columns.SALABLE_AREA));
            property.setCarpetArea(rs.getDouble(Columns.CARPET_AREA));
            property.setBuildUpArea(rs.getDouble(Columns.BUILD_UP_AREA));
            property.setBalconyCount(rs.getInt(Columns.BALCONY_COUNT));
            property.setToiletCount(rs.getInt(Columns.TOILET_COUNT));
            property.setOpenTerrace(rs.getBoolean(Columns.OPEN_TERRACE));
            property.setOpenLand(rs.getBoolean(Columns.OPEN_LAND));
            property.setLatitude(rs.getDouble(Columns.LATITUDE));
            property.setLongitude(rs.getDouble(Columns.LONGITUDE));
            property.setFeaturedProperty(rs.getBoolean(Columns.FEATURED_PROPERTY));
            property.setBus(rs.getBoolean(Columns.BUS));
            property.setAuto(rs.getBoolean(Columns.AUTO));
            property.setTaxi(rs.getBoolean(Columns.TAXI));
            property.setMetro(rs.getBoolean(Columns.METRO));
            property.setDistance(rs.getDouble(Columns.DISTANCE));
            property.setUnit(rs.getInt(Columns.UNIT));
            property.setBookingAmount(rs.getDouble(Columns.BOOKING_AMOUNT));
            property.setStartOfConstruction(rs.getDouble(Columns.START_OF_CONSTRUCTION));
            property.setCompletionOfPlinth(rs.getDouble(Columns.COMPLETION_OF_PLINTH));
            property.setEachSlab(rs.getDouble(Columns.EACH_SLAB));
            property.setBrickWork(rs.getDouble(Columns.BRICK_WORK));
            property.setPlastering(rs.getDouble(Columns.PLASTERING));
            property.setFinishingWork(rs.getDouble(Columns.FINISHING_WORK));
            property.setBrickWorkPlastering(rs.getString(Columns.BRICK_WORK_PLASTERING));
            property.setFlooring(rs.getString(Columns.FLOORING));
            property.setKitchen(rs.getString(Columns.KITCHEN));
            property.setSanitory(rs.getString(Columns.SANITORY));
            property.setDoors(rs.getString(Columns.DOORS));
            property.setWindows(rs.getString(Columns.WINDOWS));
            property.setPaintings(rs.getString(Columns.PAINTINGS));
            property.setFalseCeiling(rs.getString(Columns.FALSE_CEILING));
            property.setElectricFitings(rs.getString(Columns.ELECTRIC_FITTINGS));
            property.setLift(rs.getString(Columns.LIFT));
            property.setStructure(rs.getString(Columns.STRUCTURE));
            property.setWalls(rs.getString(Columns.WALLS));
            property.setWaterProofing(rs.getString(Columns.WATER_PROOFING));

            //  property.setTotalArea(rs.getDouble(Columns.TOTAL_AREA));
            return property;
        }

    };
}

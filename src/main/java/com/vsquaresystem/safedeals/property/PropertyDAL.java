package com.vsquaresystem.safedeals.property;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyDAL {

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_ID = "location_id";
        public static final String PROPERTY_TYPE = "type_of_property";
        public static final String PROPERTY_SIZE = "size_of_property";
        public static final String SIZE = "size";
        public static final String PRICE_RANGE = "price_range";
        public static final String BUILDING_AGE = "age_of_building";
        public static final String TOTAL_FLOORS = "total_floors";
        public static final String ENTRY_FACING = "entry_facing";

        public static final String BUILDING_CONDITION = "building_condition";
        public static final String PROJECT_ID = "project_id";
        public static final String MAJOR_APPROACH_ROAD = "major_approach_road";
        public static final String PUBLIC_TRANSPORT = "public_transport";
        public static final String OFFERED_PRICE = "offered_price";
        public static final String DISCOUNT = "discount";
        public static final String OFFER_VALID_TILL = "offer_valid_till";
        public static final String PAYMENT_SCHEDULE = "payment_schedule";
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
                        Columns.CITY_ID,
                        Columns.LOCATION_ID,
                        Columns.PROPERTY_TYPE,
                        Columns.PROPERTY_SIZE,
                        Columns.SIZE,
                        Columns.PRICE_RANGE,
                        Columns.BUILDING_AGE,
                        Columns.TOTAL_FLOORS,
                        Columns.ENTRY_FACING,
                        Columns.BUILDING_CONDITION,
                        Columns.PROJECT_ID,
                        Columns.MAJOR_APPROACH_ROAD,
                        Columns.PUBLIC_TRANSPORT,
                        Columns.OFFERED_PRICE,
                        Columns.DISCOUNT,
                        Columns.OFFER_VALID_TILL,
                        Columns.PAYMENT_SCHEDULE,
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
                        Columns.LONGITUDE
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

    public Property insert(Property property) throws JsonProcessingException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, property.getName());
        parameters.put(Columns.CITY_ID, property.getCityId());
        parameters.put(Columns.LOCATION_ID, property.getLocationId());
        parameters.put(Columns.PROPERTY_TYPE, property.getPropertyType().name());
        parameters.put(Columns.PROPERTY_SIZE, property.getPropertySize());
        parameters.put(Columns.SIZE, property.getSize());
        parameters.put(Columns.PRICE_RANGE, property.getPriceRange());
        parameters.put(Columns.BUILDING_AGE, property.getBuildingAge());
        parameters.put(Columns.TOTAL_FLOORS, property.getTotalFloors());
        parameters.put(Columns.ENTRY_FACING, property.getEntryFacing().name());
        parameters.put(Columns.BUILDING_CONDITION, property.getBuildingCondition().name());
        parameters.put(Columns.PROJECT_ID, property.getProjectId());
        parameters.put(Columns.MAJOR_APPROACH_ROAD, property.getMajorApproachRoad());
        parameters.put(Columns.PUBLIC_TRANSPORT, property.getPublicTransport() == null ? "[]" : mapper.writeValueAsString(property.getPublicTransport()));
        parameters.put(Columns.OFFERED_PRICE, property.getOfferedPrice());
        parameters.put(Columns.DISCOUNT, property.getDiscount());
        parameters.put(Columns.OFFER_VALID_TILL, property.getOfferValidTill());
        parameters.put(Columns.PAYMENT_SCHEDULE, property.getPaymentSchedule());
        parameters.put(Columns.WORKPLACES, property.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(property.getWorkplaces()));
        parameters.put(Columns.PROJECTS_NEARBY, property.getProjectsNearby() == null ? "[]" : mapper.writeValueAsString(property.getProjectsNearby()));
        parameters.put(Columns.BASIC_AMENITIES, property.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(property.getBasicAmenities()));
        parameters.put(Columns.LUXURY_AMENITIES, property.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(property.getLuxuryAmenities()));
        parameters.put(Columns.APPROVED_BANKS, property.getApprovedBanks() == null ? "[]" : mapper.writeValueAsString(property.getApprovedBanks()));
        parameters.put(Columns.SD_VERIFIED, property.getSdVerified());
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
        Number newId = insertProperty.executeAndReturnKey(parameters);
        property = findById(newId.intValue());
        return property;
    }

    public List<Property> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, propertyRowMapper);
    }

    public List<Property> findByCityAndLocation(Integer locationId, Integer cityId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " =? AND " + Columns.CITY_ID + " =? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId, cityId}, propertyRowMapper);
    }

    public List<Property> findByMinAndMaxBudget(Integer minBudget, Integer maxBudget) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.OFFERED_PRICE + " >= ? AND " + Columns.OFFERED_PRICE + "<=?";
        return jdbcTemplate.query(sqlQuery, new Object[]{minBudget, maxBudget}, propertyRowMapper);
    }

    public Property update(Property property) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.CITY_ID + " =?,"
                + Columns.LOCATION_ID + " =?,"
                + Columns.PROPERTY_TYPE + " =?,"
                + Columns.PROPERTY_SIZE + " =?,"
                + Columns.SIZE + " =?,"
                + Columns.PRICE_RANGE + " =?,"
                + Columns.BUILDING_AGE + " =?,"
                + Columns.TOTAL_FLOORS + " =?,"
                + Columns.ENTRY_FACING + " =?,"
                + Columns.BUILDING_CONDITION + " =?,"
                + Columns.PROJECT_ID + " =?,"
                + Columns.MAJOR_APPROACH_ROAD + " =?,"
                + Columns.PUBLIC_TRANSPORT + " =?,"
                + Columns.OFFERED_PRICE + " =?,"
                + Columns.DISCOUNT + " =?,"
                + Columns.OFFER_VALID_TILL + " =?,"
                + Columns.PAYMENT_SCHEDULE + " =?,"
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
                + Columns.LONGITUDE + " =? WHERE "
                + Columns.ID + " =?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            property.getName(),
            property.getCityId(),
            property.getLocationId(),
            property.getPropertyType().name(),
            property.getPropertySize(),
            property.getSize(),
            property.getPriceRange(),
            property.getBuildingAge(),
            property.getTotalFloors(),
            property.getEntryFacing().name(),
            property.getBuildingCondition().name(),
            property.getProjectId(),
            property.getMajorApproachRoad(),
            property.getPublicTransport() == null ? "[]" : mapper.writeValueAsString(property.getPublicTransport()),
            property.getOfferedPrice(),
            property.getDiscount(),
            property.getOfferValidTill(),
            property.getPaymentSchedule(),
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

            property.setName(rs.getString(Columns.NAME));

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

            property.setBuildingAge(rs.getDate(Columns.BUILDING_AGE));

            property.setTotalFloors(rs.getInt(Columns.TOTAL_FLOORS));

            if (rs.getString(Columns.ENTRY_FACING) != null) {
                property.setEntryFacing(EntryFacing.valueOf(rs.getString(Columns.ENTRY_FACING)));
            }

            if (rs.getString(Columns.BUILDING_CONDITION) != null) {
                property.setBuildingCondition(BuildingCondition.valueOf(rs.getString(Columns.BUILDING_CONDITION)));
            }

            property.setProjectId(rs.getInt(Columns.PROJECT_ID));
            if (rs.wasNull()) {
                property.setProjectId(null);
            }

            property.setMajorApproachRoad(rs.getInt(Columns.MAJOR_APPROACH_ROAD));
            if (rs.wasNull()) {
                property.setMajorApproachRoad(null);
            }

            String publicTransportList = rs.getString(Columns.PUBLIC_TRANSPORT);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> publicTransport = mapper.readValue(publicTransportList, new TypeReference<List<Integer>>() {
                });
                property.setPublicTransport(publicTransport);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing publicTransportList: '" + publicTransportList + "' ", ex);
            }

//            property.setCompletionDate(rs.getDate(PropertyDAL.Columns.COMPLETION_DATE));
//            property.setTotalBuildings(rs.getInt(PropertyDAL.Columns.TOTAL_BUILDINGS));
//            property.setTotalFloors(rs.getInt(PropertyDAL.Columns.TOTAL_FLOORS));
//            property.setTotalUnits(rs.getInt(PropertyDAL.Columns.TOTAL_UNITS));
            property.setOfferedPrice(rs.getDouble(Columns.OFFERED_PRICE));
            property.setDiscount(rs.getDouble(Columns.DISCOUNT));
            property.setOfferValidTill(rs.getDate(Columns.OFFER_VALID_TILL));
            property.setPaymentSchedule(rs.getString(Columns.PAYMENT_SCHEDULE));
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

            return property;
        }

    };
}

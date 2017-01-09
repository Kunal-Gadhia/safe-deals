package com.vsquaresystem.safedeals.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ProjectDAL {

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STATE_ID = "state_id";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_ID = "location_id";
        public static final String SUB_LOCATION = "sub_location";
        public static final String PROJECT_TYPE = "project_type";
        public static final String PROJECT_SCALE = "project_scale";
        public static final String PROPERTIES_TYPE = "properties_type";
        public static final String BOOKING_START_DATE = "booking_start_date";
        public static final String CONSTRUCTION_STAGE = "construction_stage";
        public static final String COMPLETION_DATE = "completion_date";
        public static final String TOTAL_BUILDINGS = "total_buildings";
        public static final String TOTAL_FLOORS = "total_floors";
        public static final String IMAGES = "images";
        public static final String VIDEOS = "videos";
        public static final String TOTAL_UNITS = "total_units";
        public static final String MAJOR_APPROACH_ROAD = "major_approach_road";
        public static final String PUBLIC_TRANSPORT = "public_transport";
        public static final String OFFERED_PRICE = "offered_price";
        public static final String DISCOUNT = "discount";
        public static final String OFFER_VALID_TILL = "offer_valid_till";
        public static final String PAYMENT_SCHEDULE = "payment_schedule";
        public static final String WORKPLACES = "workplaces";
        public static final String BASIC_AMENITIES = "basic_amenities";
        public static final String LUXURY_AMENITIES = "luxury_amenities";
        public static final String OWNERSHIP_PROOF = "ownership_proof";
        public static final String APPROVED_BANKS = "approved_banks";
        public static final String SD_VERIFIED = "sd_verified";
        public static final String PRIVATE_AMENITIES = "private_amenities";
        public static final String PROJECT_TESTIMONIAL = "project_testimonial";
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

    public static final String TABLE_NAME = "project";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProject;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ProjectDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertProject = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.STATE_ID,
                        Columns.CITY_ID,
                        Columns.LOCATION_ID,
                        Columns.SUB_LOCATION,
                        Columns.PROJECT_TYPE,
                        Columns.PROJECT_SCALE,
                        Columns.PROPERTIES_TYPE,
                        Columns.BOOKING_START_DATE,
                        Columns.CONSTRUCTION_STAGE,
                        Columns.COMPLETION_DATE,
                        Columns.TOTAL_BUILDINGS,
                        Columns.TOTAL_FLOORS,
                        Columns.IMAGES,
                        Columns.VIDEOS,
                        Columns.TOTAL_UNITS,
                        Columns.MAJOR_APPROACH_ROAD,
                        Columns.PUBLIC_TRANSPORT,
                        Columns.OFFERED_PRICE,
                        Columns.DISCOUNT,
                        Columns.OFFER_VALID_TILL,
                        Columns.PAYMENT_SCHEDULE,
                        Columns.WORKPLACES,
                        Columns.BASIC_AMENITIES,
                        Columns.LUXURY_AMENITIES,
                        Columns.OWNERSHIP_PROOF,
                        Columns.APPROVED_BANKS,
                        Columns.SD_VERIFIED,
                        Columns.PRIVATE_AMENITIES,
                        Columns.PROJECT_TESTIMONIAL,
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

    public List<Project> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(Project.class));
    }

    public Project findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Project.class));
    }

    public Project insert(Project project) throws JsonProcessingException {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, project.getName());
        parameters.put(Columns.STATE_ID, project.getStateId());
        parameters.put(Columns.CITY_ID, project.getCityId());
        parameters.put(Columns.LOCATION_ID, project.getLocationId());
        parameters.put(Columns.SUB_LOCATION, project.getSubLocation());
        parameters.put(Columns.PROJECT_TYPE, project.getProjectType());
        parameters.put(Columns.PROJECT_SCALE, project.getProjectScale());
        parameters.put(Columns.PROPERTIES_TYPE, project.getPropertiesType() == null ? "[]" : mapper.writeValueAsString(project.getPropertiesType()));
        parameters.put(Columns.BOOKING_START_DATE, project.getBookingStartDate());
        parameters.put(Columns.CONSTRUCTION_STAGE, project.getConstructionStage());
        parameters.put(Columns.COMPLETION_DATE, project.getCompletionDate());
        parameters.put(Columns.TOTAL_BUILDINGS, project.getTotalBuildings());
        parameters.put(Columns.TOTAL_FLOORS, project.getTotalFloors());
        parameters.put(Columns.IMAGES, project.getImages() == null ? "[]" : mapper.writeValueAsString(project.getImages()));
        parameters.put(Columns.VIDEOS, project.getVideos() == null ? "[]" : mapper.writeValueAsString(project.getVideos()));
        parameters.put(Columns.TOTAL_UNITS, project.getTotalUnits());
        parameters.put(Columns.MAJOR_APPROACH_ROAD, project.getMajorApproachRoad());
        parameters.put(Columns.PUBLIC_TRANSPORT, project.getPublicTransport() == null ? "[]" : mapper.writeValueAsString(project.getPublicTransport()));
        parameters.put(Columns.OFFERED_PRICE, project.getOfferedPrice());
        parameters.put(Columns.DISCOUNT, project.getDiscount());
        parameters.put(Columns.OFFER_VALID_TILL, project.getOfferValidTill());
        parameters.put(Columns.PAYMENT_SCHEDULE, project.getPaymentSchedule());
        parameters.put(Columns.WORKPLACES, project.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(project.getWorkplaces()));
        parameters.put(Columns.BASIC_AMENITIES, project.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(project.getBasicAmenities()));
        parameters.put(Columns.LUXURY_AMENITIES, project.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(project.getLuxuryAmenities()));
        parameters.put(Columns.OWNERSHIP_PROOF, project.getOwnershipProof() == null ? "[]" : mapper.writeValueAsString(project.getOwnershipProof()));
        parameters.put(Columns.APPROVED_BANKS, project.getApprovedBanks() == null ? "[]" : mapper.writeValueAsString(project.getApprovedBanks()));
        parameters.put(Columns.SD_VERIFIED, project.getSdVerified());
        parameters.put(Columns.PRIVATE_AMENITIES, project.getPrivateAmenities() == null ? "[]" : mapper.writeValueAsString(project.getPrivateAmenities()));
        parameters.put(Columns.PROJECT_TESTIMONIAL, project.getProjectTestimonial());
        parameters.put(Columns.SALABLE_AREA, project.getSalableArea());
        parameters.put(Columns.CARPET_AREA, project.getCarpetArea());
        parameters.put(Columns.BUILD_UP_AREA, project.getBuildUpArea());
        parameters.put(Columns.BALCONY_COUNT, project.getBalconyCount());
        parameters.put(Columns.TOILET_COUNT, project.getToiletCount());
        parameters.put(Columns.OPEN_TERRACE, project.getOpenTerrace());
        parameters.put(Columns.OPEN_LAND, project.getOpenLand());
        parameters.put(Columns.LATITUDE, project.getLatitude());
        parameters.put(Columns.LONGITUDE, project.getLongitude());
        Number newId = insertProject.executeAndReturnKey(parameters);
        project = findById(newId.intValue());
        return project;
    }

    public List<Project> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(Project.class));
    }

//    public List<Project> findByProjectCost(Double projectCost) {
//        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PROJECT_COST + " <= ?";
//        return jdbcTemplate.query(sqlQuery, new Object[]{projectCost}, new BeanPropertyRowMapper<>(Project.class));
//    }
    public Project update(Project project) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.STATE_ID + " =?,"
                + Columns.CITY_ID + " =?,"
                + Columns.LOCATION_ID + " =?,"
                + Columns.SUB_LOCATION + " =?,"
                + Columns.PROJECT_TYPE + " =?,"
                + Columns.PROJECT_SCALE + " =?,"
                + Columns.PROPERTIES_TYPE + " =?,"
                + Columns.BOOKING_START_DATE + " =?,"
                + Columns.CONSTRUCTION_STAGE + " =?,"
                + Columns.COMPLETION_DATE + " =?,"
                + Columns.TOTAL_BUILDINGS + " =?,"
                + Columns.TOTAL_FLOORS + " =?,"
                + Columns.IMAGES + " =?,"
                + Columns.VIDEOS + " =?,"
                + Columns.TOTAL_UNITS + " =?,"
                + Columns.MAJOR_APPROACH_ROAD + " =?,"
                + Columns.PUBLIC_TRANSPORT + " =?,"
                + Columns.OFFERED_PRICE + " =?,"
                + Columns.DISCOUNT + " =?,"
                + Columns.OFFER_VALID_TILL + " =?,"
                + Columns.PAYMENT_SCHEDULE + " =?,"
                + Columns.WORKPLACES + " =?,"
                + Columns.BASIC_AMENITIES + " =?,"
                + Columns.LUXURY_AMENITIES + " =?,"
                + Columns.OWNERSHIP_PROOF + " =?,"
                + Columns.APPROVED_BANKS + " =?,"
                + Columns.SD_VERIFIED + " =?,"
                + Columns.PRIVATE_AMENITIES + " =?,"
                + Columns.PROJECT_TESTIMONIAL + " =?,"
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
            project.getName(),
            project.getStateId(),
            project.getCityId(),
            project.getLocationId(),
            project.getSubLocation(),
            project.getProjectType(),
            project.getProjectScale(),
            project.getPropertiesType() == null ? "[]" : mapper.writeValueAsString(project.getPropertiesType()),
            project.getBookingStartDate(),
            project.getConstructionStage(),
            project.getCompletionDate(),
            project.getTotalBuildings(),
            project.getTotalFloors(),
            project.getImages() == null ? "[]" : mapper.writeValueAsString(project.getImages()),
            project.getVideos() == null ? "[]" : mapper.writeValueAsString(project.getVideos()),
            project.getTotalUnits(),
            project.getMajorApproachRoad(),
            project.getPublicTransport() == null ? "[]" : mapper.writeValueAsString(project.getPublicTransport()),
            project.getOfferedPrice(),
            project.getDiscount(),
            project.getOfferValidTill(),
            project.getPaymentSchedule(),
            project.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(project.getWorkplaces()),
            project.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(project.getBasicAmenities()),
            project.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(project.getLuxuryAmenities()),
            project.getOwnershipProof() == null ? "[]" : mapper.writeValueAsString(project.getOwnershipProof()),
            project.getApprovedBanks() == null ? "[]" : mapper.writeValueAsString(project.getApprovedBanks()),
            project.getSdVerified(),
            project.getPrivateAmenities() == null ? "[]" : mapper.writeValueAsString(project.getPrivateAmenities()),
            project.getProjectTestimonial(),
            project.getSalableArea(),
            project.getCarpetArea(),
            project.getBuildUpArea(),
            project.getBalconyCount(),
            project.getToiletCount(),
            project.getOpenTerrace(),
            project.getOpenLand(),
            project.getLatitude(),
            project.getLongitude(),
            project.getId()});
        project = findById(project.getId());
        return project;
    }

    public List<Project> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, new BeanPropertyRowMapper<>(Project.class));
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

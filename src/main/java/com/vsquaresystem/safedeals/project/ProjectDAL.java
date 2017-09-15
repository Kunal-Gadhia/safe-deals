package com.vsquaresystem.safedeals.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsquaresystem.safedeals.city.City;
import com.vsquaresystem.safedeals.city.CityDAL;
import com.vsquaresystem.safedeals.property.PropertyDAL;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDAL {

    @Autowired
    private CityDAL cityDAL;

    private static Integer srNumber = 0;

    public static final String TABLE_NAME = "project";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProject;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final class Columns {

        public static final String ID = "id";
        public static final String PROJECT_ID = "project_id";
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
        public static final String TOTAL_UNITS = "total_units";
        public static final String MAJOR_APPROACH_ROAD = "major_approach_road";
        public static final String OFFERED_PRICE = "offered_price";
        public static final String DISCOUNT = "discount";
        public static final String OFFER_VALID_TILL = "offer_valid_till";
        //       public static final String PAYMENT_SCHEDULE = "payment_schedule";
        public static final String WORKPLACES = "workplaces";
        public static final String BASIC_AMENITIES = "basic_amenities";
        public static final String LUXURY_AMENITIES = "luxury_amenities";
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
        public static final String FEATURED_PROJECT = "featured_project";
        public static final String BUS = "bus";
        public static final String AUTO = "auto";
        public static final String TAXI = "taxi";
        public static final String METRO = "metro";
        public static final String DISTANCE = "distance";
        public static final String UNIT = "unit";
        public static final String TOTAL_AREA = "total_area";

        public static final String MUTATION_COPY = "mutation_copy";
        public static final String SALE_DEED = "sale_deed";
        public static final String DEVELOPMENT_AGREEMENT = "development_agreement";
        public static final String POWER_OF_AUTHORITY = "power_of_authority";
        public static final String TAX_RECEIPT = "tax_receipt";
        public static final String LAYOUT_SANCTION = "layout_sanction";
        public static final String DEVELOPMENT_PLAN = "development_plan";
        public static final String RELEASE_LETTER = "release_letter";
        public static final String BUILDING_SANCTION = "building_sanction";
        public static final String COMPLETION_CERTIFICATE = "completion_certificate";
        public static final String OCCUPANCY_CERTIFICATE = "occupancy_certificate";
        public static final String BIRD_EYE_VIEW = "bird_eye_view";
        public static final String ELEVATION = "elevation";
        public static final String FLOOR_PLANS = "floor_plans";
        public static final String OTHER_IMAGES = "other_images";

        public static final String BOOKING_AMOUNT = "booking_amount";
        public static final String START_OF_CONSTRUCTION = "start_of_construction";
        public static final String COMPLETION_OF_PLINTH = "completion_of_plinth";
        public static final String EACH_SLAB = "each_slab";
        public static final String BRICK_WORK = "brick_work";
        public static final String PLASTERING = "plastering";
        public static final String FINISHING_WORK = "finishing_work";
        public static final String SOCIETY_MAINTENANCE = "society_maintenance";
        public static final String SOCIETY_MAINTENANCE_AMOUNT = "society_maintenance_amount";
        public static final String SOCIETY_MAINTENANCE_DURATION = "society_maintenance_duration";
    }

    @Autowired
    public ProjectDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertProject = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.PROJECT_ID,
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
                        Columns.TOTAL_UNITS,
                        Columns.MAJOR_APPROACH_ROAD,
                        Columns.OFFERED_PRICE,
                        Columns.DISCOUNT,
                        Columns.OFFER_VALID_TILL,
                        //                        Columns.PAYMENT_SCHEDULE,
                        Columns.WORKPLACES,
                        Columns.BASIC_AMENITIES,
                        Columns.LUXURY_AMENITIES,
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
                        Columns.LONGITUDE,
                        Columns.FEATURED_PROJECT,
                        Columns.BUS,
                        Columns.AUTO,
                        Columns.TAXI,
                        Columns.METRO,
                        Columns.DISTANCE,
                        Columns.UNIT,
                        Columns.TOTAL_AREA,
                        Columns.MUTATION_COPY,
                        Columns.SALE_DEED,
                        Columns.DEVELOPMENT_AGREEMENT,
                        Columns.POWER_OF_AUTHORITY,
                        Columns.TAX_RECEIPT,
                        Columns.LAYOUT_SANCTION,
                        Columns.DEVELOPMENT_PLAN,
                        Columns.RELEASE_LETTER,
                        Columns.BUILDING_SANCTION,
                        Columns.COMPLETION_CERTIFICATE,
                        Columns.OCCUPANCY_CERTIFICATE,
                        Columns.BIRD_EYE_VIEW,
                        Columns.ELEVATION,
                        Columns.FLOOR_PLANS,
                        Columns.OTHER_IMAGES,
                        Columns.BOOKING_AMOUNT,
                        Columns.START_OF_CONSTRUCTION,
                        Columns.COMPLETION_OF_PLINTH,
                        Columns.EACH_SLAB,
                        Columns.BRICK_WORK,
                        Columns.PLASTERING,
                        Columns.FINISHING_WORK,
                        Columns.SOCIETY_MAINTENANCE,
                        Columns.SOCIETY_MAINTENANCE_AMOUNT,
                        Columns.SOCIETY_MAINTENANCE_DURATION
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Project> findAll(Integer offset) {
        System.out.println("Offset :" + offset);
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        System.out.println("Sql Query :" + sqlQuery);
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, projectRowMapper);
    }

    public Project findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, projectRowMapper);
    }

    public List findSerialNo(String letter) {
        String sqlQuery = "SELECT " + Columns.PROJECT_ID + " FROM " + TABLE_NAME + " WHERE deleted= FALSE AND " + Columns.PROJECT_ID + " LIKE ? ";
        String letterLike = letter + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{letterLike}, new BeanPropertyRowMapper<>(Project.class));
    }

    public Project insert(Project project) throws JsonProcessingException {
        logger.info("Project :{}", project);
        System.out.println("Kunal" + project);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, project.getName());
        parameters.put(Columns.STATE_ID, project.getStateId());
        parameters.put(Columns.CITY_ID, project.getCityId());
        parameters.put(Columns.LOCATION_ID, project.getLocationId());
        parameters.put(Columns.SUB_LOCATION, project.getSubLocation());
        parameters.put(Columns.PROJECT_TYPE, project.getProjectType().name());
        parameters.put(Columns.PROJECT_SCALE, project.getProjectScale().name());
        parameters.put(Columns.PROPERTIES_TYPE, project.getPropertiesType() == null ? "[]" : mapper.writeValueAsString(project.getPropertiesType()));
        parameters.put(Columns.BOOKING_START_DATE, project.getBookingStartDate());
        parameters.put(Columns.CONSTRUCTION_STAGE, project.getConstructionStage().name());
        parameters.put(Columns.COMPLETION_DATE, project.getCompletionDate());
        parameters.put(Columns.TOTAL_BUILDINGS, project.getTotalBuildings());
        parameters.put(Columns.TOTAL_FLOORS, project.getTotalFloors());
        parameters.put(Columns.TOTAL_UNITS, project.getTotalUnits());
        parameters.put(Columns.MAJOR_APPROACH_ROAD, project.getMajorApproachRoad());
        parameters.put(Columns.OFFERED_PRICE, project.getOfferedPrice());
        parameters.put(Columns.DISCOUNT, project.getDiscount());
        parameters.put(Columns.OFFER_VALID_TILL, project.getOfferValidTill());
//        parameters.put(Columns.PAYMENT_SCHEDULE, project.getPaymentSchedule());
        parameters.put(Columns.WORKPLACES, project.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(project.getWorkplaces()));
        parameters.put(Columns.BASIC_AMENITIES, project.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(project.getBasicAmenities()));
        parameters.put(Columns.LUXURY_AMENITIES, project.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(project.getLuxuryAmenities()));
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

        if (project.getFeaturedProject() == null) {
            parameters.put(Columns.FEATURED_PROJECT, 0);
        } else {
            parameters.put(Columns.FEATURED_PROJECT, project.getFeaturedProject());
        }

        if (project.getBus() == null) {
            parameters.put(Columns.BUS, 0);
        } else {
            parameters.put(Columns.BUS, project.getBus());
        }

        if (project.getAuto() == null) {
            parameters.put(Columns.AUTO, 0);
        } else {
            parameters.put(Columns.AUTO, project.getAuto());
        }

        if (project.getTaxi() == null) {
            parameters.put(Columns.TAXI, 0);
        } else {
            parameters.put(Columns.TAXI, project.getTaxi());
        }

        if (project.getMetro() == null) {
            parameters.put(Columns.METRO, 0);
        } else {
            parameters.put(Columns.METRO, project.getMetro());
        }

        parameters.put(Columns.DISTANCE, project.getDistance());
        parameters.put(Columns.UNIT, project.getUnit());
        parameters.put(Columns.TOTAL_AREA, project.getTotalArea());

        City c = cityDAL.findById(project.getCityId());
        String cityName = c.getName();

        List cityN = findSerialNo(String.valueOf(cityName).substring(0, 3));
        if (cityN.size() <= 0) {
            srNumber = 1;
        } else {
            srNumber = cityN.size() + 1;
        }
        String projectId = String.valueOf(cityName).substring(0, 3) + String.valueOf(project.getSubLocation()).substring(0, 3) + srNumber;
        parameters.put(PropertyDAL.Columns.PROJECT_ID, projectId);
        parameters.put(Columns.MUTATION_COPY, project.getMutationCopy() == null ? "[]" : mapper.writeValueAsString(project.getMutationCopy()));
        parameters.put(Columns.SALE_DEED, project.getSaleDeed() == null ? "[]" : mapper.writeValueAsString(project.getSaleDeed()));
        parameters.put(Columns.DEVELOPMENT_AGREEMENT, project.getDevelopmentAgreement() == null ? "[]" : mapper.writeValueAsString(project.getDevelopmentAgreement()));
        parameters.put(Columns.POWER_OF_AUTHORITY, project.getPowerOfAuthority() == null ? "[]" : mapper.writeValueAsString(project.getPowerOfAuthority()));
        parameters.put(Columns.TAX_RECEIPT, project.getTaxReceipt() == null ? "[]" : mapper.writeValueAsString(project.getTaxReceipt()));
        parameters.put(Columns.LAYOUT_SANCTION, project.getLayoutSanction() == null ? "[]" : mapper.writeValueAsString(project.getLayoutSanction()));
        parameters.put(Columns.DEVELOPMENT_PLAN, project.getDevelopmentPlan() == null ? "[]" : mapper.writeValueAsString(project.getDevelopmentPlan()));
        parameters.put(Columns.RELEASE_LETTER, project.getReleaseLetter() == null ? "[]" : mapper.writeValueAsString(project.getReleaseLetter()));
        parameters.put(Columns.BUILDING_SANCTION, project.getBuildingSanction() == null ? "[]" : mapper.writeValueAsString(project.getBuildingSanction()));
        parameters.put(Columns.COMPLETION_CERTIFICATE, project.getCompletionCertificate() == null ? "[]" : mapper.writeValueAsString(project.getCompletionCertificate()));
        parameters.put(Columns.OCCUPANCY_CERTIFICATE, project.getOccupancyCertificate() == null ? "[]" : mapper.writeValueAsString(project.getOccupancyCertificate()));
        parameters.put(Columns.BIRD_EYE_VIEW, project.getBirdEyeView() == null ? "[]" : mapper.writeValueAsString(project.getBirdEyeView()));
        parameters.put(Columns.ELEVATION, project.getElevation() == null ? "[]" : mapper.writeValueAsString(project.getElevation()));
        parameters.put(Columns.FLOOR_PLANS, project.getFloorPlans() == null ? "[]" : mapper.writeValueAsString(project.getFloorPlans()));
        parameters.put(Columns.OTHER_IMAGES, project.getOtherImages() == null ? "[]" : mapper.writeValueAsString(project.getOtherImages()));

        parameters.put(Columns.BOOKING_AMOUNT, project.getBookingAmount());
        parameters.put(Columns.START_OF_CONSTRUCTION, project.getStartOfConstruction());
        parameters.put(Columns.COMPLETION_OF_PLINTH, project.getCompletionOfPlinth());
        parameters.put(Columns.EACH_SLAB, project.getEachSlab());
        parameters.put(Columns.BRICK_WORK, project.getBrickWork());
        parameters.put(Columns.PLASTERING, project.getPlastering());
        parameters.put(Columns.FINISHING_WORK, project.getFinishingWork());
        parameters.put(Columns.SOCIETY_MAINTENANCE, project.getSocietyMaintenances() == null ? "[]" : mapper.writeValueAsString(project.getSocietyMaintenances()));
        parameters.put(Columns.SOCIETY_MAINTENANCE_AMOUNT, project.getSocietyMaintenanceAmount());
        parameters.put(Columns.SOCIETY_MAINTENANCE_DURATION, project.getSocietyMaintenanceDuration());

        Number newId = insertProject.executeAndReturnKey(parameters);
        project = findById(newId.intValue());
        return project;
    }

    public List<Project> findByLocationId(Integer locationId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, projectRowMapper);
    }

//    public List<Project> findByProjectCost(Double projectCost) {
//        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.PROJECT_COST + " <= ?";
//        return jdbcTemplate.query(sqlQuery, new Object[]{projectCost}, new BeanPropertyRowMapper<>(Project.class));
//    }
    public Project update(Project project) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + " =?,"
                + Columns.PROJECT_ID + " =?,"
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
                + Columns.TOTAL_UNITS + " =?,"
                + Columns.MAJOR_APPROACH_ROAD + " =?,"
                + Columns.OFFERED_PRICE + " =?,"
                + Columns.DISCOUNT + " =?,"
                + Columns.OFFER_VALID_TILL + " =?,"
                //                + Columns.PAYMENT_SCHEDULE + " =?,"
                + Columns.WORKPLACES + " =?,"
                + Columns.BASIC_AMENITIES + " =?,"
                + Columns.LUXURY_AMENITIES + " =?,"
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
                + Columns.LONGITUDE + " =?,"
                + Columns.FEATURED_PROJECT + " =?,"
                + Columns.BUS + " =?,"
                + Columns.AUTO + " =?,"
                + Columns.TAXI + " =?,"
                + Columns.METRO + " =?,"
                + Columns.DISTANCE + " =?,"
                + Columns.UNIT + " =?,"
                + Columns.TOTAL_AREA + " =?,"
                + Columns.MUTATION_COPY + " =?,"
                + Columns.SALE_DEED + " =?,"
                + Columns.DEVELOPMENT_AGREEMENT + " =?,"
                + Columns.POWER_OF_AUTHORITY + " =?,"
                + Columns.TAX_RECEIPT + " =?,"
                + Columns.LAYOUT_SANCTION + " =?,"
                + Columns.DEVELOPMENT_PLAN + " =?,"
                + Columns.RELEASE_LETTER + " =?,"
                + Columns.BUILDING_SANCTION + " =?,"
                + Columns.COMPLETION_CERTIFICATE + " =?,"
                + Columns.OCCUPANCY_CERTIFICATE + " =?,"
                + Columns.BIRD_EYE_VIEW + " =?,"
                + Columns.ELEVATION + " =?,"
                + Columns.FLOOR_PLANS + " =?,"
                + Columns.OTHER_IMAGES + " =?,"
                + Columns.BOOKING_AMOUNT + " =?,"
                + Columns.START_OF_CONSTRUCTION + " =?,"
                + Columns.COMPLETION_OF_PLINTH + " =?,"
                + Columns.EACH_SLAB + " =?,"
                + Columns.BRICK_WORK + " =?,"
                + Columns.PLASTERING + " =?,"
                + Columns.FINISHING_WORK + " =?,"
                + Columns.SOCIETY_MAINTENANCE + " =?,"
                + Columns.SOCIETY_MAINTENANCE_AMOUNT + " =?,"
                + Columns.SOCIETY_MAINTENANCE_DURATION + " =? WHERE "
                + Columns.ID + " =?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            project.getName(),
            project.getProjectId(),
            project.getStateId(),
            project.getCityId(),
            project.getLocationId(),
            project.getSubLocation(),
            project.getProjectType().name(),
            project.getProjectScale().name(),
            project.getPropertiesType() == null ? "[]" : mapper.writeValueAsString(project.getPropertiesType()),
            project.getBookingStartDate(),
            project.getConstructionStage().name(),
            project.getCompletionDate(),
            project.getTotalBuildings(),
            project.getTotalFloors(),
            project.getTotalUnits(),
            project.getMajorApproachRoad(),
            project.getOfferedPrice(),
            project.getDiscount(),
            project.getOfferValidTill(),
            //            project.getPaymentSchedule(),
            project.getWorkplaces() == null ? "[]" : mapper.writeValueAsString(project.getWorkplaces()),
            project.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(project.getBasicAmenities()),
            project.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(project.getLuxuryAmenities()),
            //      project.getOwnershipProof() == null ? "[]" : mapper.writeValueAsString(project.getOwnershipProof()),
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
            project.getFeaturedProject(),
            project.getBus(),
            project.getAuto(),
            project.getTaxi(),
            project.getMetro(),
            project.getDistance(),
            project.getUnit(),
            project.getTotalArea(),
            project.getMutationCopy() == null ? "[]" : mapper.writeValueAsString(project.getMutationCopy()),
            project.getSaleDeed() == null ? "[]" : mapper.writeValueAsString(project.getSaleDeed()),
            project.getDevelopmentAgreement() == null ? "[]" : mapper.writeValueAsString(project.getDevelopmentAgreement()),
            project.getPowerOfAuthority() == null ? "[]" : mapper.writeValueAsString(project.getPowerOfAuthority()),
            project.getTaxReceipt() == null ? "[]" : mapper.writeValueAsString(project.getTaxReceipt()),
            project.getLayoutSanction() == null ? "[]" : mapper.writeValueAsString(project.getLayoutSanction()),
            project.getDevelopmentAgreement() == null ? "[]" : mapper.writeValueAsString(project.getDevelopmentAgreement()),
            project.getReleaseLetter() == null ? "[]" : mapper.writeValueAsString(project.getReleaseLetter()),
            project.getBuildingSanction() == null ? "[]" : mapper.writeValueAsString(project.getBuildingSanction()),
            project.getCompletionCertificate() == null ? "[]" : mapper.writeValueAsString(project.getCompletionCertificate()),
            project.getOccupancyCertificate() == null ? "[]" : mapper.writeValueAsString(project.getOccupancyCertificate()),
            project.getBirdEyeView() == null ? "[]" : mapper.writeValueAsString(project.getBirdEyeView()),
            project.getElevation() == null ? "[]" : mapper.writeValueAsString(project.getElevation()),
            project.getFloorPlans() == null ? "[]" : mapper.writeValueAsString(project.getFloorPlans()),
            project.getOtherImages() == null ? "[]" : mapper.writeValueAsString(project.getOtherImages()),
            project.getBookingAmount(),
            project.getStartOfConstruction(),
            project.getCompletionOfPlinth(),
            project.getEachSlab(),
            project.getBrickWork(),
            project.getPlastering(),
            project.getFinishingWork(),
            project.getSocietyMaintenances() == null ? "[]" : mapper.writeValueAsString(project.getSocietyMaintenances()),
            project.getSocietyMaintenanceAmount(),
            project.getSocietyMaintenanceDuration(),
            project.getId()}
        );
        project = findById(project.getId());
        return project;
    }

    public List<Project> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "%" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, projectRowMapper);
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    private final RowMapper<Project> projectRowMapper = new RowMapper<Project>() {

        @Override
        public Project mapRow(ResultSet rs, int i) throws SQLException {
            Project project = new Project();
            project.setId(rs.getInt(Columns.ID));
            project.setProjectId(rs.getString(Columns.PROJECT_ID));
            project.setName(rs.getString(Columns.NAME));
            project.setStateId(rs.getInt(Columns.STATE_ID));
            if (rs.wasNull()) {
                project.setStateId(null);
            }
            project.setCityId(rs.getInt(Columns.CITY_ID));
            if (rs.wasNull()) {
                project.setCityId(null);
            }
            project.setLocationId(rs.getInt(Columns.LOCATION_ID));
            if (rs.wasNull()) {
                project.setLocationId(null);
            }
            project.setSubLocation(rs.getString(Columns.SUB_LOCATION));
            if (rs.getString(Columns.PROJECT_TYPE) != null) {
                project.setProjectType(ProjectType.valueOf(rs.getString(Columns.PROJECT_TYPE)));
            }
            if (rs.getString(Columns.PROJECT_SCALE) != null) {
                project.setProjectScale(ProjectScale.valueOf(rs.getString(Columns.PROJECT_SCALE)));
            }
            String propertiesTypeList = rs.getString(Columns.PROPERTIES_TYPE);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> propertiesType = mapper.readValue(propertiesTypeList, new TypeReference<List<Integer>>() {
                });
                project.setPropertiesType(propertiesType);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing propertiesTypeList: '" + propertiesTypeList + "' ", ex);
            }
            project.setBookingStartDate(rs.getDate(Columns.BOOKING_START_DATE));
            if (rs.getString(Columns.CONSTRUCTION_STAGE) != null) {
                project.setConstructionStage(ConstructionStage.valueOf(rs.getString(Columns.CONSTRUCTION_STAGE)));
            }
            project.setCompletionDate(rs.getDate(Columns.COMPLETION_DATE));
            project.setTotalBuildings(rs.getInt(Columns.TOTAL_BUILDINGS));
            project.setTotalFloors(rs.getInt(Columns.TOTAL_FLOORS));
            project.setTotalUnits(rs.getInt(Columns.TOTAL_UNITS));
            project.setMajorApproachRoad(rs.getInt(Columns.MAJOR_APPROACH_ROAD));
            if (rs.wasNull()) {
                project.setMajorApproachRoad(null);
            }
//            String publicTransportList = rs.getString(Columns.PUBLIC_TRANSPORT);
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                List<Integer> publicTransport = mapper.readValue(publicTransportList, new TypeReference<List<Integer>>() {
//                });
//                project.setPublicTransport(publicTransport);
//            } catch (IOException ex) {
//                throw new RuntimeException("Error parsing publicTransportList: '" + publicTransportList + "' ", ex);
//            }
            project.setOfferedPrice(rs.getDouble(Columns.OFFERED_PRICE));
            project.setDiscount(rs.getDouble(Columns.DISCOUNT));
            project.setOfferValidTill(rs.getDate(Columns.OFFER_VALID_TILL));
//            project.setPaymentSchedule(rs.getString(Columns.PAYMENT_SCHEDULE));
            String workplacesList = rs.getString(Columns.WORKPLACES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> workplace = mapper.readValue(workplacesList, new TypeReference<List<Integer>>() {
                });
                project.setWorkplaces(workplace);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing workplacesList: '" + workplacesList + "' ", ex);
            }
            String basicAmenitiesList = rs.getString(Columns.BASIC_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> basicAmenities = mapper.readValue(basicAmenitiesList, new TypeReference<List<Integer>>() {
                });
                project.setBasicAmenities(basicAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing basicAmenitiesList: '" + basicAmenitiesList + "' ", ex);
            }
            String luxuryAmenitiesList = rs.getString(Columns.LUXURY_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> luxuryAmenities = mapper.readValue(luxuryAmenitiesList, new TypeReference<List<Integer>>() {
                });
                project.setLuxuryAmenities(luxuryAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing luxuryAmenitiesList: '" + luxuryAmenitiesList + "' ", ex);
            }
//            String ownershipProofList = rs.getString(Columns.OWNERSHIP_PROOF);
//            try {
//                ObjectMapper mapper = new ObjectMapper();
//                List<String> ownershipProof = mapper.readValue(ownershipProofList, new TypeReference<List<String>>() {
//                });
//                project.setOwnershipProof(ownershipProof);
//            } catch (IOException ex) {
//                throw new RuntimeException("Error parsing ownershipProofList: '" + ownershipProofList + "' ", ex);
//            }
            String approvedBanksList = rs.getString(Columns.APPROVED_BANKS);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> approvedBanks = mapper.readValue(approvedBanksList, new TypeReference<List<Integer>>() {
                });
                project.setApprovedBanks(approvedBanks);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing approvedBanksList: '" + approvedBanksList + "' ", ex);
            }
            project.setSdVerified(rs.getBoolean(Columns.SD_VERIFIED));
            String privateAmenitiesList = rs.getString(Columns.PRIVATE_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> privateAmenities = mapper.readValue(privateAmenitiesList, new TypeReference<List<Integer>>() {
                });
                project.setPrivateAmenities(privateAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing privateAmenitiesList: '" + privateAmenitiesList + "' ", ex);
            }
            project.setProjectTestimonial(rs.getString(Columns.PROJECT_TESTIMONIAL));
            project.setSalableArea(rs.getDouble(Columns.SALABLE_AREA));
            project.setCarpetArea(rs.getDouble(Columns.CARPET_AREA));
            project.setBuildUpArea(rs.getDouble(Columns.BUILD_UP_AREA));
            project.setBalconyCount(rs.getInt(Columns.BALCONY_COUNT));
            project.setToiletCount(rs.getInt(Columns.TOILET_COUNT));
            project.setOpenTerrace(rs.getBoolean(Columns.OPEN_TERRACE));
            project.setOpenLand(rs.getBoolean(Columns.OPEN_LAND));
            project.setLatitude(rs.getDouble(Columns.LATITUDE));
            project.setLongitude(rs.getDouble(Columns.LONGITUDE));
            project.setFeaturedProject(rs.getBoolean(Columns.FEATURED_PROJECT));
            project.setBus(rs.getBoolean(Columns.BUS));
            project.setAuto(rs.getBoolean(Columns.AUTO));
            project.setTaxi(rs.getBoolean(Columns.TAXI));
            project.setMetro(rs.getBoolean(Columns.METRO));
            project.setDistance(rs.getDouble(Columns.DISTANCE));
            project.setUnit(rs.getInt(Columns.UNIT));
            project.setTotalArea(rs.getDouble(Columns.TOTAL_AREA));

            String mutationCopyList = rs.getString(Columns.MUTATION_COPY);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> mutationCopy = mapper.readValue(mutationCopyList, new TypeReference<List<String>>() {
                });
                project.setMutationCopy(mutationCopy);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing ownershipProofList: '" + mutationCopyList + "' ", ex);
            }

            String saleDeedList = rs.getString(Columns.SALE_DEED);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> saleDeed = mapper.readValue(saleDeedList, new TypeReference<List<String>>() {
                });
                project.setSaleDeed(saleDeed);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing saleDeedList: '" + saleDeedList + "' ", ex);
            }

            String developmentAgreementList = rs.getString(Columns.DEVELOPMENT_AGREEMENT);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> developmentAgreement = mapper.readValue(developmentAgreementList, new TypeReference<List<String>>() {
                });
                project.setDevelopmentAgreement(developmentAgreement);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing developmentAgreementList: '" + developmentAgreementList + "' ", ex);
            }

            String powerOfAuthorityList = rs.getString(Columns.POWER_OF_AUTHORITY);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> powerOfAuthority = mapper.readValue(powerOfAuthorityList, new TypeReference<List<String>>() {
                });
                project.setPowerOfAuthority(powerOfAuthority);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing powerOfAuthorityList: '" + powerOfAuthorityList + "' ", ex);
            }

            String taxReceiptList = rs.getString(Columns.TAX_RECEIPT);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> taxReceipt = mapper.readValue(taxReceiptList, new TypeReference<List<String>>() {
                });
                project.setTaxReceipt(taxReceipt);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing taxReceiptList: '" + taxReceiptList + "' ", ex);
            }

            String layoutSanctionList = rs.getString(Columns.LAYOUT_SANCTION);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> layoutSanction = mapper.readValue(layoutSanctionList, new TypeReference<List<String>>() {
                });
                project.setLayoutSanction(layoutSanction);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing layoutSanctiontList: '" + layoutSanctionList + "' ", ex);
            }

            String developmentPlanList = rs.getString(Columns.DEVELOPMENT_PLAN);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> developmentPlan = mapper.readValue(developmentPlanList, new TypeReference<List<String>>() {
                });
                project.setDevelopmentPlan(developmentPlan);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing developmentPlanList: '" + developmentPlanList + "' ", ex);
            }

            String releaseLetterList = rs.getString(Columns.RELEASE_LETTER);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> releaseLetter = mapper.readValue(releaseLetterList, new TypeReference<List<String>>() {
                });
                project.setReleaseLetter(releaseLetter);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing releaseLetterList: '" + releaseLetterList + "' ", ex);
            }

            String buildingSanctionList = rs.getString(Columns.BUILDING_SANCTION);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> buildingSanction = mapper.readValue(buildingSanctionList, new TypeReference<List<String>>() {
                });
                project.setBuildingSanction(buildingSanction);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing buildingSanctionList: '" + buildingSanctionList + "' ", ex);
            }

            String completionCertificateList = rs.getString(Columns.COMPLETION_CERTIFICATE);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> completionCertificate = mapper.readValue(completionCertificateList, new TypeReference<List<String>>() {
                });
                project.setCompletionCertificate(completionCertificate);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing completionCertificateList: '" + completionCertificateList + "' ", ex);
            }

            String occupancyCertificateList = rs.getString(Columns.OCCUPANCY_CERTIFICATE);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> occupancyCertificate = mapper.readValue(occupancyCertificateList, new TypeReference<List<String>>() {
                });
                project.setOccupancyCertificate(occupancyCertificate);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing occupancyCertificateList: '" + occupancyCertificateList + "' ", ex);
            }

            String birdEyeViewList = rs.getString(Columns.BIRD_EYE_VIEW);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> birdEyeView = mapper.readValue(birdEyeViewList, new TypeReference<List<String>>() {
                });
                project.setBirdEyeView(birdEyeView);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing birdEyeViewList: '" + birdEyeViewList + "' ", ex);
            }

            String elevationList = rs.getString(Columns.ELEVATION);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> elevation = mapper.readValue(elevationList, new TypeReference<List<String>>() {
                });
                project.setElevation(elevation);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing elevationList: '" + elevationList + "' ", ex);
            }

            String floorPlansList = rs.getString(Columns.FLOOR_PLANS);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> floorPlans = mapper.readValue(floorPlansList, new TypeReference<List<String>>() {
                });
                project.setFloorPlans(floorPlans);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing floorPlansList: '" + floorPlansList + "' ", ex);
            }

            String otherImagesList = rs.getString(Columns.OTHER_IMAGES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> otherImages = mapper.readValue(otherImagesList, new TypeReference<List<String>>() {
                });
                project.setOtherImages(otherImages);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing otherImagesList: '" + otherImagesList + "' ", ex);
            }

            project.setBookingAmount(rs.getDouble(Columns.BOOKING_AMOUNT));
            project.setStartOfConstruction(rs.getDouble(Columns.START_OF_CONSTRUCTION));
            project.setCompletionOfPlinth(rs.getDouble(Columns.COMPLETION_OF_PLINTH));
            project.setEachSlab(rs.getDouble(Columns.EACH_SLAB));
            project.setBrickWork(rs.getDouble(Columns.BRICK_WORK));
            project.setPlastering(rs.getDouble(Columns.PLASTERING));
            project.setFinishingWork(rs.getDouble(Columns.FINISHING_WORK));

            String societyMaintenanceList = rs.getString(Columns.SOCIETY_MAINTENANCE);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> societyMaintenances = mapper.readValue(societyMaintenanceList, new TypeReference<List<Integer>>() {
                });
                project.setSocietyMaintenances(societyMaintenances);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing societyMaintenanceList: '" + societyMaintenanceList + "' ", ex);
            }

            project.setSocietyMaintenanceAmount(rs.getDouble(Columns.SOCIETY_MAINTENANCE_AMOUNT));
            project.setSocietyMaintenanceDuration(rs.getDouble(Columns.SOCIETY_MAINTENANCE_DURATION));

            return project;
        }
//
//        @Override
//        public Project mapRow(ResultSet rs, int i) throws SQLException {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }

    };

}

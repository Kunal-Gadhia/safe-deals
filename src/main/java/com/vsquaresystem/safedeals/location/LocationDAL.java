package com.vsquaresystem.safedeals.location;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final class Columns {

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_TYPE_ID = "location_type_id";
        public static final String LOCATION_CATEGORIES = "location_categories";
        public static final String SAFEDEAL_ZONE_ID = "safedeal_zone_id";
        public static final String MAJOR_APPROACH_ROAD = "major_approach_road";
        public static final String ADVANTAGE = "advantage";
        public static final String DISADVANTAGE = "disadvantage";
        public static final String POPULATION = "population";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String DESCRIPTION = "description";
        public static final String MIGRATION_RATE_PER_ANNUM = "migration_rate_per_annum";
        public static final String DISTANCE_FROM_CENTRE_OF_CITY = "distance_from_centre_of_city";
        public static final String IS_COMMERCIAL_CENTER = "is_commercial_center";
        public static final String DISTANCE_FROM_COMMERCIAL_CENTER = "distance_from_commercial_center";
        public static final String DEMAND_POTENTIAL = "demand_potential";
        public static final String POWER_PLANT = "power_plant";
        public static final String MEDICINE_INDUSTRY = "medicine_industry";
        public static final String STEEL_INDUSTRY = "steel_industry";
        public static final String FILTHY_LAKE = "filthy_lake";
        public static final String LOW_LYING_AREA = "low_lying_area";
        public static final String DUMP_YARD = "dump_yard";
        public static final String STP = "stp";
        public static final String IMAGE_URL = "image_url";
        public static final String BUS = "bus";
        public static final String AUTO = "auto";
        public static final String TAXI = "taxi";
        public static final String METRO = "metro";
        public static final String CORPORATIN_SUPPLY = "corporation_supply";
        public static final String BOREWELL = "borewell";
        public static final String OPEN_WELL = "open_well";
        public static final String DISTANCE = "distance";
        public static final String UNIT = "unit";
        public static final String BASIC_AMENITIES = "basic_amenities";
        public static final String LUXURY_AMENITIES = "luxury_amenities";

    }

    public static final String TABLE_NAME = "location";

    private final SimpleJdbcInsert insertLocation;
    private final JdbcTemplate jdbcTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public LocationDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertLocation = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.NAME,
                        Columns.CITY_ID,
                        Columns.LOCATION_TYPE_ID,
                        Columns.LOCATION_CATEGORIES,
                        Columns.SAFEDEAL_ZONE_ID,
                        Columns.MAJOR_APPROACH_ROAD,
                        Columns.ADVANTAGE,
                        Columns.DISADVANTAGE,
                        Columns.POPULATION,
                        Columns.LATITUDE,
                        Columns.LONGITUDE,
                        Columns.DESCRIPTION,
                        Columns.MIGRATION_RATE_PER_ANNUM,
                        Columns.DISTANCE_FROM_CENTRE_OF_CITY,
                        Columns.IS_COMMERCIAL_CENTER,
                        Columns.DISTANCE_FROM_COMMERCIAL_CENTER,
                        Columns.DEMAND_POTENTIAL,
                        Columns.POWER_PLANT,
                        Columns.MEDICINE_INDUSTRY,
                        Columns.STEEL_INDUSTRY,
                        Columns.FILTHY_LAKE,
                        Columns.LOW_LYING_AREA,
                        Columns.DUMP_YARD,
                        Columns.STP,
                        Columns.IMAGE_URL,
                        Columns.BUS,
                        Columns.AUTO,
                        Columns.TAXI,
                        Columns.METRO,
                        Columns.CORPORATIN_SUPPLY,
                        Columns.BOREWELL,
                        Columns.OPEN_WELL,
                        Columns.DISTANCE,
                        Columns.UNIT,
                        Columns.BASIC_AMENITIES,
                        Columns.LUXURY_AMENITIES
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<Location> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ORDER BY " + Columns.ID + " DESC LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, locationRowMapper);
    }

    public List<Location> findAllLocations() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, locationRowMapper);
    }

    public List<Location> findByNameLike(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND lower(name) LIKE?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{nameLike}, locationRowMapper);
    }

    public Location findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, locationRowMapper);
    }

    public Location findByName(String name) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.NAME + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{name}, locationRowMapper);
    }

    public Location findAllCommercialCenters(Integer cityId) {

        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE city_id = " + Columns.CITY_ID + " deleted = FALSE AND " + "is_commercial_center = true" + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{cityId}, locationRowMapper);
    }

    public List<Location> findByNameAndCityId(String name, Integer cityId) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.CITY_ID + " = ? AND LOWER(name) LIKE ?";
        String nameLike = "" + name.toLowerCase() + "%";
        return jdbcTemplate.query(sqlQuery, new Object[]{cityId, nameLike}, locationRowMapper);
    }

    public Location insert(Location location) throws JsonProcessingException {
        logger.info("location object in DAL line95 {}", location);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.NAME, location.getName());
        parameters.put(Columns.CITY_ID, location.getCityId());
        parameters.put(Columns.LOCATION_TYPE_ID, location.getLocationTypeId());
        parameters.put(Columns.LOCATION_CATEGORIES, location.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(location.getLocationCategories()));
        parameters.put(Columns.SAFEDEAL_ZONE_ID, location.getSafedealZoneId());
        parameters.put(Columns.MAJOR_APPROACH_ROAD, location.getMajorApproachRoad());
        parameters.put(Columns.ADVANTAGE, location.getAdvantage());
        parameters.put(Columns.DISADVANTAGE, location.getDisadvantage());
        parameters.put(Columns.POPULATION, location.getPopulation());
        parameters.put(Columns.LATITUDE, location.getLatitude());
        parameters.put(Columns.LONGITUDE, location.getLongitude());
        parameters.put(Columns.DESCRIPTION, location.getDescription());
        parameters.put(Columns.MIGRATION_RATE_PER_ANNUM, location.getMigrationRatePerAnnum().name());
        parameters.put(Columns.DISTANCE_FROM_CENTRE_OF_CITY, location.getDistanceFromCentreOfCity());
        parameters.put(Columns.IS_COMMERCIAL_CENTER, location.getIsCommercialCenter());
        parameters.put(Columns.DISTANCE_FROM_COMMERCIAL_CENTER, location.getDistanceFromCommercialCenter());
        parameters.put(Columns.DEMAND_POTENTIAL, location.getDemandPotential().name());
        if (location.getPowerPlant() == null) {
            parameters.put(Columns.POWER_PLANT, 0);
        } else {
            parameters.put(Columns.POWER_PLANT, location.getPowerPlant());
        }

        if (location.getMedicineIndustry() == null) {
            parameters.put(Columns.MEDICINE_INDUSTRY, 0);
        } else {
            parameters.put(Columns.MEDICINE_INDUSTRY, location.getMedicineIndustry());
        }

        if (location.getSteelIndustry() == null) {
            parameters.put(Columns.STEEL_INDUSTRY, 0);
        } else {
            parameters.put(Columns.STEEL_INDUSTRY, location.getSteelIndustry());
        }

        if (location.getFilthyLake() == null) {
            parameters.put(Columns.FILTHY_LAKE, 0);
        } else {
            parameters.put(Columns.FILTHY_LAKE, location.getFilthyLake());
        }

        if (location.getLowLyingArea() == null) {
            parameters.put(Columns.LOW_LYING_AREA, 0);
        } else {
            parameters.put(Columns.LOW_LYING_AREA, location.getLowLyingArea());
        }

        if (location.getDumpYard() == null) {
            parameters.put(Columns.DUMP_YARD, 0);
        } else {
            parameters.put(Columns.DUMP_YARD, location.getDumpYard());
        }

        if (location.getStp() == null) {
            parameters.put(Columns.STP, 0);
        } else {
            parameters.put(Columns.STP, location.getStp());
        }

        parameters.put(Columns.IMAGE_URL, location.getImageUrl());

        if (location.getBus() == null) {
            parameters.put(Columns.BUS, 0);
        } else {
            parameters.put(Columns.BUS, location.getBus());
        }

        if (location.getAuto() == null) {
            parameters.put(Columns.AUTO, 0);
        } else {
            parameters.put(Columns.AUTO, location.getAuto());
        }

        if (location.getTaxi() == null) {
            parameters.put(Columns.TAXI, 0);
        } else {
            parameters.put(Columns.TAXI, location.getTaxi());
        }

        if (location.getMetro() == null) {
            parameters.put(Columns.METRO, 0);
        } else {
            parameters.put(Columns.METRO, location.getMetro());
        }

        if (location.getCorporationSupply() == null) {
            parameters.put(Columns.CORPORATIN_SUPPLY, 0);
        } else {
            parameters.put(Columns.CORPORATIN_SUPPLY, location.getCorporationSupply());
        }

        if (location.getBorewell() == null) {
            parameters.put(Columns.BOREWELL, 0);
        } else {
            parameters.put(Columns.BOREWELL, location.getBorewell());
        }

        if (location.getOpenWell() == null) {
            parameters.put(Columns.OPEN_WELL, 0);
        } else {
            parameters.put(Columns.OPEN_WELL, location.getOpenWell());
        }

        parameters.put(Columns.DISTANCE, location.getDistance());
        parameters.put(Columns.UNIT, location.getUnit());
        parameters.put(Columns.BASIC_AMENITIES, location.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(location.getBasicAmenities()));
        parameters.put(Columns.LUXURY_AMENITIES, location.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(location.getLuxuryAmenities()));

        System.out.println("param" + parameters);
        Number newId = insertLocation.executeAndReturnKey(parameters);
        location = findById(newId.intValue());
        return location;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted=? WHERE " + Columns.ID + "=?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

    public Location update(Location location) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.NAME + "=?, "
                + Columns.CITY_ID + "=?, "
                + Columns.LOCATION_TYPE_ID + "=?, "
                + Columns.LOCATION_CATEGORIES + "=?, "
                + Columns.SAFEDEAL_ZONE_ID + "=?, "
                + Columns.MAJOR_APPROACH_ROAD + "=?, "
                + Columns.ADVANTAGE + "=?, "
                + Columns.DISADVANTAGE + "=?, "
                + Columns.POPULATION + "=?, "
                + Columns.LATITUDE + "=?, "
                + Columns.LONGITUDE + "=?, "
                + Columns.DESCRIPTION + "=?, "
                + Columns.MIGRATION_RATE_PER_ANNUM + "=?, "
                + Columns.DISTANCE_FROM_CENTRE_OF_CITY + "=?, "
                + Columns.IS_COMMERCIAL_CENTER + "=?, "
                + Columns.DISTANCE_FROM_COMMERCIAL_CENTER + "=?, "
                + Columns.DEMAND_POTENTIAL + "=?, "
                + Columns.POWER_PLANT + "=?, "
                + Columns.MEDICINE_INDUSTRY + "=?, "
                + Columns.STEEL_INDUSTRY + "=?, "
                + Columns.FILTHY_LAKE + "=?, "
                + Columns.LOW_LYING_AREA + "=?, "
                + Columns.DUMP_YARD + "=?, "
                + Columns.STP + "=?, "
                + Columns.IMAGE_URL + "=?, "
                + Columns.BUS + "=?, "
                + Columns.AUTO + "=?, "
                + Columns.TAXI + "=?, "
                + Columns.METRO + "=?, "
                + Columns.CORPORATIN_SUPPLY + "=?, "
                + Columns.BOREWELL + "=?, "
                + Columns.OPEN_WELL + "=?, "
                + Columns.DISTANCE + "=?, "
                + Columns.UNIT + "=?, "
                + Columns.BASIC_AMENITIES + "=?, "
                + Columns.LUXURY_AMENITIES + "=?  WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery,
                new Object[]{
                    location.getName(),
                    location.getCityId(),
                    location.getLocationTypeId(),
                    location.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(location.getLocationCategories()),
                    location.getSafedealZoneId(),
                    location.getMajorApproachRoad(),
                    location.getAdvantage(),
                    location.getDisadvantage(),
                    location.getPopulation(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getDescription(),
                    location.getMigrationRatePerAnnum().name(),
                    location.getDistanceFromCentreOfCity(),
                    location.getIsCommercialCenter(),
                    location.getDistanceFromCommercialCenter(),
                    location.getDemandPotential().name(),
                    //                     if (location.getCorporationSupply() == null) {
                    //            parameters.put(Columns.CORPORATIN_SUPPLY, 0);
                    //        } else {
                    //            parameters.put(Columns.CORPORATIN_SUPPLY, location.getCorporationSupply());
                    //        }                    
                    location.getPowerPlant(),
                    location.getMedicineIndustry(),
                    location.getSteelIndustry(),
                    location.getFilthyLake(),
                    location.getLowLyingArea(),
                    location.getDumpYard(),
                    location.getStp(),
                    location.getImageUrl(),
                    location.getBus(),
                    location.getAuto(),
                    location.getTaxi(),
                    location.getMetro(),
                    location.getCorporationSupply(),
                    location.getBorewell(),
                    location.getOpenWell(),
                    location.getDistance(),
                    location.getUnit(),
                    location.getBasicAmenities() == null ? "[]" : mapper.writeValueAsString(location.getBasicAmenities()),
                    location.getLuxuryAmenities() == null ? "[]" : mapper.writeValueAsString(location.getLuxuryAmenities()),
                    location.getId()
                }
        );
        location = findById(location.getId());
        return location;
    }

    private final RowMapper<Location> locationRowMapper = new RowMapper<Location>() {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt(Columns.ID));
            location.setDescription(rs.getString(Columns.DESCRIPTION));
            location.setName(rs.getString(Columns.NAME));
            location.setCityId(rs.getInt(Columns.CITY_ID));
            if (rs.wasNull()) {
                location.setCityId(null);
            }
            location.setLocationTypeId(rs.getInt(Columns.LOCATION_TYPE_ID));
            if (rs.wasNull()) {
                location.setLocationTypeId(null);
            }
            String locationCategoryList = rs.getString(Columns.LOCATION_CATEGORIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> locationCategories = mapper.readValue(locationCategoryList, new TypeReference<List<Integer>>() {
                });
//                logger.info("locationCategories:{}", locationCategories);
                location.setLocationCategories(locationCategories);

            } catch (IOException ex) {
                throw new RuntimeException("Error parsing locationCategoriesList: '" + locationCategoryList + "' ", ex);
            }

            location.setSafedealZoneId(rs.getInt(Columns.SAFEDEAL_ZONE_ID));
            if (rs.wasNull()) {
                location.setSafedealZoneId(null);
            }

            location.setMajorApproachRoad(rs.getString(Columns.MAJOR_APPROACH_ROAD));
            location.setAdvantage(rs.getString(Columns.ADVANTAGE));
            location.setDisadvantage(rs.getString(Columns.DISADVANTAGE));
            location.setPopulation(rs.getInt(Columns.POPULATION));
            location.setLatitude(rs.getDouble(Columns.LATITUDE));
            location.setLongitude(rs.getDouble(Columns.LONGITUDE));
            if (rs.getString(Columns.MIGRATION_RATE_PER_ANNUM) != null) {
                location.setMigrationRatePerAnnum(MigrationRatePerAnnum.valueOf(rs.getString(Columns.MIGRATION_RATE_PER_ANNUM)));
            }
            location.setDistanceFromCentreOfCity(rs.getDouble(Columns.DISTANCE_FROM_CENTRE_OF_CITY));
            location.setIsCommercialCenter(rs.getBoolean(Columns.IS_COMMERCIAL_CENTER));
            location.setDistanceFromCommercialCenter(rs.getDouble(Columns.DISTANCE_FROM_COMMERCIAL_CENTER));
            if (rs.getString(Columns.DEMAND_POTENTIAL) != null) {
                location.setDemandPotential(DemandPotential.valueOf(rs.getString(Columns.DEMAND_POTENTIAL)));
            }
            location.setPowerPlant(rs.getBoolean(Columns.POWER_PLANT));
            location.setMedicineIndustry(rs.getBoolean(Columns.MEDICINE_INDUSTRY));
            location.setSteelIndustry(rs.getBoolean(Columns.STEEL_INDUSTRY));
            location.setFilthyLake(rs.getBoolean(Columns.FILTHY_LAKE));
            location.setLowLyingArea(rs.getBoolean(Columns.LOW_LYING_AREA));
            location.setDumpYard(rs.getBoolean(Columns.DUMP_YARD));
            location.setStp(rs.getBoolean(Columns.STP));
            location.setImageUrl(rs.getString(Columns.IMAGE_URL));
            location.setBus(rs.getBoolean(Columns.BUS));
            location.setAuto(rs.getBoolean(Columns.AUTO));
            location.setTaxi(rs.getBoolean(Columns.TAXI));
            location.setMetro(rs.getBoolean(Columns.METRO));
            location.setCorporationSupply(rs.getBoolean(Columns.CORPORATIN_SUPPLY));
            location.setBorewell(rs.getBoolean(Columns.BOREWELL));
            location.setOpenWell(rs.getBoolean(Columns.OPEN_WELL));
            location.setDistance(rs.getDouble(Columns.DISTANCE));
            location.setUnit(rs.getInt(Columns.UNIT));

            if (rs.wasNull()) {
                location.setUnit(null);
            }

            String basicAmenitiesList = rs.getString(Columns.BASIC_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> basicAmenities = mapper.readValue(basicAmenitiesList, new TypeReference<List<Integer>>() {
                });
                location.setBasicAmenities(basicAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing basicAmenitiesList: '" + basicAmenitiesList + "' ", ex);
            }
            String luxuryAmenitiesList = rs.getString(Columns.LUXURY_AMENITIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> luxuryAmenities = mapper.readValue(luxuryAmenitiesList, new TypeReference<List<Integer>>() {
                });
                location.setLuxuryAmenities(luxuryAmenities);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing luxuryAmenitiesList: '" + luxuryAmenitiesList + "' ", ex);
            }
            return location;
        }

    };
}

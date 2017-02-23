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
        public static final String SOURCE_OF_WATER = "source_of_water";
        public static final String PUBLIC_TRANSPORT = "public_transport";
        public static final String MIGRATION_RATE_PER_ANNUM = "migration_rate_per_annum";
        public static final String DISTANCE_FROM_CENTRE_OF_CITY = "distance_from_centre_of_city";
        public static final String IS_COMMERCIAL_CENTER = "is_commercial_center";
        public static final String DISTANCE_FROM_COMMERCIAL_CENTER = "distance_from_commercial_center";
        public static final String IMAGE_URL = "image_url";

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
                        Columns.SOURCE_OF_WATER,
                        Columns.PUBLIC_TRANSPORT,
                        Columns.MIGRATION_RATE_PER_ANNUM,
                        Columns.DISTANCE_FROM_CENTRE_OF_CITY,
                        Columns.IS_COMMERCIAL_CENTER,
                        Columns.DISTANCE_FROM_COMMERCIAL_CENTER,
                        Columns.IMAGE_URL
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
        parameters.put(Columns.SOURCE_OF_WATER, location.getSourceOfWater());
        parameters.put(Columns.PUBLIC_TRANSPORT, location.getPublicTransport());
        parameters.put(Columns.MIGRATION_RATE_PER_ANNUM, location.getMigrationRatePerAnnum());
        parameters.put(Columns.DISTANCE_FROM_CENTRE_OF_CITY, location.getDistanceFromCentreOfCity());
        parameters.put(Columns.IS_COMMERCIAL_CENTER, location.getIsCommercialCenter());
        parameters.put(Columns.DISTANCE_FROM_COMMERCIAL_CENTER, location.getDistanceFromCommercialCenter());
        parameters.put(Columns.IMAGE_URL, location.getImageUrl());

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
                + Columns.SOURCE_OF_WATER + "=?, "
                + Columns.PUBLIC_TRANSPORT + "=?, "
                + Columns.MIGRATION_RATE_PER_ANNUM + "=?, "
                + Columns.DISTANCE_FROM_CENTRE_OF_CITY + "=?, "
                + Columns.IS_COMMERCIAL_CENTER + "=?, "
                + Columns.DISTANCE_FROM_COMMERCIAL_CENTER + "=?, "
                + Columns.IMAGE_URL + "=?  WHERE " + Columns.ID + " = ?";
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
                    location.getSourceOfWater().name(),
                    location.getPublicTransport().name(),
                    location.getMigrationRatePerAnnum(),
                    location.getDistanceFromCentreOfCity(),
                    location.getIsCommercialCenter(),
                    location.getDistanceFromCommercialCenter(),
                    location.getImageUrl(),
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
            if (rs.getString(Columns.SOURCE_OF_WATER) != null) {
                location.setSourceOfWater(SourceOfWater.valueOf(rs.getString(Columns.SOURCE_OF_WATER)));
            }
            if (rs.getString(Columns.PUBLIC_TRANSPORT) != null) {
                location.setPublicTransport(PublicTransport.valueOf(rs.getString(Columns.PUBLIC_TRANSPORT)));
            }
            location.setMigrationRatePerAnnum(rs.getDouble(Columns.MIGRATION_RATE_PER_ANNUM));
            location.setDistanceFromCentreOfCity(rs.getDouble(Columns.DISTANCE_FROM_CENTRE_OF_CITY));
            location.setIsCommercialCenter(rs.getBoolean(Columns.IS_COMMERCIAL_CENTER));
            location.setDistanceFromCommercialCenter(rs.getDouble(Columns.DISTANCE_FROM_COMMERCIAL_CENTER));
            location.setImageUrl(rs.getString(Columns.IMAGE_URL));

            return location;
        }

    };
}

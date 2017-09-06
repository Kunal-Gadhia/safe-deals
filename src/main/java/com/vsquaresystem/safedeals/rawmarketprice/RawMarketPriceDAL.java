/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.rawmarketprice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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

/**
 *
 * @author hp-pc
 */
@Repository
public class RawMarketPriceDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TABLE_NAME = "raw_market_price";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertRawMarketPrice;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final class Columns {

        public static final String ID = "id";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION_NAME = "location_name";
        public static final String SAFEDEAL_ZONE_ID = "safedeal_zone_id";
        public static final String LOCATION_TYPE_ID = "location_type_id";
        public static final String LOCATION_CATEGORIES = "location_categories";
        public static final String DESCRIPTION = "description";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String MP_AGRI_LAND_LOWEST = "mp_agri_land_lowest";
        public static final String MP_AGRI_LAND_HIGHEST = "mp_agri_land_highest";
        public static final String MP_PLOT_LOWEST = "mp_plot_lowest";
        public static final String MP_PLOT_HIGHEST = "mp_plot_highest";
        public static final String MP_RESIDENTIAL_LOWEST = "mp_residential_lowest";
        public static final String MP_RESIDENTIAL_HIGHEST = "mp_residential_highest";
        public static final String MP_COMMERCIAL_LOWEST = "mp_commercial_lowest";
        public static final String MP_COMMERCIAL_HIGHEST = "mp_commercial_highest";
        public static final String IS_COMMERCIAL_CENTER = "is_commercial_center";
        public static final String MAJOR_APPROACH_ROAD = "major_approach_road";
        public static final String SOURCE_OF_WATER = "source_of_water";
        public static final String PUBLIC_TRANSPORT = "public_transport";
        public static final String ADVANTAGE = "advantage";
        public static final String DISADVANTAGE = "disadvantage";
        public static final String POPULATION = "population";
        public static final String MIGRATION_RATE = "migration_rate";
        public static final String USER_ID = "user_id";
        public static final String LAST_UPDATED_TIME_STAMP = "last_updated_time_stamp";

    };

    @Autowired
    public RawMarketPriceDAL(DataSource dataSource) {
        logger.info("dataSource in constructor {}", dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertRawMarketPrice = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.CITY_ID,
                        Columns.LOCATION_NAME,
                        Columns.SAFEDEAL_ZONE_ID,
                        Columns.LOCATION_TYPE_ID,
                        Columns.LOCATION_CATEGORIES,
                        Columns.DESCRIPTION,
                        Columns.YEAR,
                        Columns.MONTH,
                        Columns.MP_AGRI_LAND_LOWEST,
                        Columns.MP_AGRI_LAND_HIGHEST,
                        Columns.MP_PLOT_LOWEST,
                        Columns.MP_PLOT_HIGHEST,
                        Columns.MP_RESIDENTIAL_LOWEST,
                        Columns.MP_RESIDENTIAL_HIGHEST,
                        Columns.MP_COMMERCIAL_LOWEST,
                        Columns.MP_COMMERCIAL_HIGHEST,
                        Columns.IS_COMMERCIAL_CENTER,
                        Columns.MAJOR_APPROACH_ROAD,
                        Columns.SOURCE_OF_WATER,
                        Columns.PUBLIC_TRANSPORT,
                        Columns.ADVANTAGE,
                        Columns.DISADVANTAGE,
                        Columns.POPULATION,
                        Columns.MIGRATION_RATE,
                        Columns.USER_ID,
                        Columns.LAST_UPDATED_TIME_STAMP
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<RawMarketPrice> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, rawMarketPriceRowMapper);
    }

    public List<RawMarketPrice> getAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, rawMarketPriceRowMapper);
    }

    public List<RawMarketPrice> findAllRawMarketPrice() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, rawMarketPriceRowMapper);
    }

    public void truncateAll() {
        String sqlQuery = "TRUNCATE " + TABLE_NAME;
        jdbcTemplate.update(sqlQuery, new Object[]{});
    }

    public RawMarketPrice findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, rawMarketPriceRowMapper);
    }

    public final class DistinctLocations {

        private Integer cityId;
        private String locationName;
        private Integer safedealZoneId;
        private Integer locationTypeId;
        private List<Integer> locationCategories;
        private String sourceOfWater;
        private String publicTransport;
        private String advantage;
        private String disadvantage;
        private String majorApproachRoad;
        private String description;
        private Integer population;
        private Boolean isCommercialCenter;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public Integer getSafedealZoneId() {
            return safedealZoneId;
        }

        public void setSafedealZoneId(Integer safedealZoneId) {
            this.safedealZoneId = safedealZoneId;
        }

        public Integer getLocationTypeId() {
            return locationTypeId;
        }

        public void setLocationTypeId(Integer locationTypeId) {
            this.locationTypeId = locationTypeId;
        }

        public List<Integer> getLocationCategories() {
            return locationCategories;
        }

        public void setLocationCategories(List<Integer> locationCategories) {
            this.locationCategories = locationCategories;
        }

        public String getSourceOfWater() {
            return sourceOfWater;
        }

        public void setSourceOfWater(String sourceOfWater) {
            this.sourceOfWater = sourceOfWater;
        }

        public String getPublicTransport() {
            return publicTransport;
        }

        public void setPublicTransport(String publicTransport) {
            this.publicTransport = publicTransport;
        }

        public String getAdvantage() {
            return advantage;
        }

        public void setAdvantage(String advantage) {
            this.advantage = advantage;
        }

        public String getDisadvantage() {
            return disadvantage;
        }

        public void setDisadvantage(String disadvantage) {
            this.disadvantage = disadvantage;
        }

        public String getMajorApproachRoad() {
            return majorApproachRoad;
        }

        public void setMajorApproachRoad(String majorApproachRoad) {
            this.majorApproachRoad = majorApproachRoad;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getPopulation() {
            return population;
        }

        public void setPopulation(Integer population) {
            this.population = population;
        }

        public Boolean getIsCommercialCenter() {
            return isCommercialCenter;
        }

        public void setIsCommercialCenter(Boolean isCommercialCenter) {
            this.isCommercialCenter = isCommercialCenter;
        }

        @Override
        public String toString() {
            return "DistinctLocations{" + "cityId=" + cityId + ", locationName=" + locationName + ", safedealZoneId=" + safedealZoneId + ", locationTypeId=" + locationTypeId + ", locationCategories=" + locationCategories + ", sourceOfWater=" + sourceOfWater + ", publicTransport=" + publicTransport + ", advantage=" + advantage + ", disadvantage=" + disadvantage + ", majorApproachRoad=" + majorApproachRoad + ", description=" + description + ", population=" + population + ", isCommercialCenter=" + isCommercialCenter + '}';
        }

    }

    public List<DistinctLocations> findByUniqueLocation() {
        String sqlQuery = "SELECT distinct(location_name), city_id, safedeal_zone_id, location_type_id, location_categories, source_of_water, public_transport, advantage, disadvantage, population, major_approach_road, description, is_commercial_center  FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, distinctLocationsMapper);
    }

    public List<DistinctLocations> findByUniqueLocationWithCityId(Integer cityId) {
        String sqlQuery = "SELECT distinct(location_name), city_id, safedeal_zone_id, location_type_id, location_categories, source_of_water, public_transport, advantage, disadvantage, population, major_approach_road, description, is_commercial_center  FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.CITY_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{cityId}, distinctLocationsMapper);
    }

    public void delete(Integer id, Integer userId) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? ,"
                + Columns.USER_ID + " = ? ,"
                + Columns.LAST_UPDATED_TIME_STAMP + " = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, userId, new Date(), id});
    }

    public RawMarketPrice update(RawMarketPrice rawmarketPrice) throws JsonProcessingException {
        System.out.println("update raw master264" + rawmarketPrice);
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.CITY_ID + " = ?, "
                + Columns.LOCATION_NAME + " = ?, "
                + Columns.SAFEDEAL_ZONE_ID + " = ?, "
                + Columns.LOCATION_TYPE_ID + " = ?, "
                + Columns.LOCATION_CATEGORIES + " = ?, "
                + Columns.DESCRIPTION + " = ?, "
                + Columns.YEAR + " = ?, "
                + Columns.MONTH + " = ?, "
                + Columns.MP_AGRI_LAND_LOWEST + " = ?, "
                + Columns.MP_AGRI_LAND_HIGHEST + " = ?, "
                + Columns.MP_PLOT_LOWEST + " = ?, "
                + Columns.MP_PLOT_HIGHEST + " = ?, "
                + Columns.MP_RESIDENTIAL_LOWEST + " = ?, "
                + Columns.MP_RESIDENTIAL_HIGHEST + " = ?, "
                + Columns.MP_COMMERCIAL_LOWEST + " = ?, "
                + Columns.MP_COMMERCIAL_HIGHEST + " = ?, "
                + Columns.IS_COMMERCIAL_CENTER + " = ?, "
                + Columns.MAJOR_APPROACH_ROAD + " = ?, "
                + Columns.SOURCE_OF_WATER + " = ?, "
                + Columns.PUBLIC_TRANSPORT + " = ?, "
                + Columns.ADVANTAGE + " = ?, "
                + Columns.DISADVANTAGE + " = ?, "
                + Columns.POPULATION + " = ?, "
                + Columns.MIGRATION_RATE + " = ?,"
                + Columns.USER_ID + " = ?,"
                + Columns.LAST_UPDATED_TIME_STAMP
                + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            rawmarketPrice.getCityId(),
            rawmarketPrice.getLocationName(),
            rawmarketPrice.getSafedealZoneId(),
            rawmarketPrice.getLocationTypeId(),
            rawmarketPrice.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(rawmarketPrice.getLocationCategories()),
            rawmarketPrice.getDescription(),
            rawmarketPrice.getYear(),
            rawmarketPrice.getMonth(),
            rawmarketPrice.getMpAgriLandLowest(),
            rawmarketPrice.getMpAgriLandHighest(),
            rawmarketPrice.getMpPlotLowest(),
            rawmarketPrice.getMpPlotHighest(),
            rawmarketPrice.getMpResidentialLowest(),
            rawmarketPrice.getMpResidentialHighest(),
            rawmarketPrice.getMpCommercialLowest(),
            rawmarketPrice.getMpCommercialHighest(),
            rawmarketPrice.getIsCommercialCenter(),
            rawmarketPrice.getMajorApproachRoad(),
            rawmarketPrice.getSourceOfWater(),
            rawmarketPrice.getPublicTransport(),
            rawmarketPrice.getAdvantage(),
            rawmarketPrice.getDisadvantage(),
            rawmarketPrice.getPopulation(),
            rawmarketPrice.getMigrationRate(),
            rawmarketPrice.getUserId(),
            new Date(),
            rawmarketPrice.getId()});
        rawmarketPrice = findById(rawmarketPrice.getId());
        return rawmarketPrice;
    }

    public RawMarketPrice insert(RawMarketPrice rawmarketPrice) throws JsonProcessingException {
        logger.info("rawReadyReckoner in DAL {}", rawmarketPrice);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.CITY_ID, rawmarketPrice.getCityId());
        parameters.put(Columns.LOCATION_NAME, rawmarketPrice.getLocationName());
        parameters.put(Columns.SAFEDEAL_ZONE_ID, rawmarketPrice.getSafedealZoneId());
        parameters.put(Columns.LOCATION_TYPE_ID, rawmarketPrice.getLocationTypeId());
        parameters.put(Columns.LOCATION_CATEGORIES, rawmarketPrice.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(rawmarketPrice.getLocationCategories()));
        parameters.put(Columns.DESCRIPTION, rawmarketPrice.getDescription());
        parameters.put(Columns.YEAR, rawmarketPrice.getYear());
        parameters.put(Columns.MONTH, rawmarketPrice.getMonth());
        parameters.put(Columns.MP_AGRI_LAND_LOWEST, rawmarketPrice.getMpAgriLandLowest());
        parameters.put(Columns.MP_AGRI_LAND_HIGHEST, rawmarketPrice.getMpAgriLandHighest());
        parameters.put(Columns.MP_PLOT_LOWEST, rawmarketPrice.getMpPlotLowest());
        parameters.put(Columns.MP_PLOT_HIGHEST, rawmarketPrice.getMpPlotHighest());
        parameters.put(Columns.MP_RESIDENTIAL_LOWEST, rawmarketPrice.getMpResidentialLowest());
        parameters.put(Columns.MP_RESIDENTIAL_HIGHEST, rawmarketPrice.getMpResidentialHighest());
        parameters.put(Columns.MP_COMMERCIAL_LOWEST, rawmarketPrice.getMpCommercialLowest());
        parameters.put(Columns.MP_COMMERCIAL_HIGHEST, rawmarketPrice.getMpCommercialHighest());
        parameters.put(Columns.IS_COMMERCIAL_CENTER, rawmarketPrice.getIsCommercialCenter());
        parameters.put(Columns.MAJOR_APPROACH_ROAD, rawmarketPrice.getMajorApproachRoad());
        parameters.put(Columns.SOURCE_OF_WATER, rawmarketPrice.getSourceOfWater());
        parameters.put(Columns.PUBLIC_TRANSPORT, rawmarketPrice.getPublicTransport());
        parameters.put(Columns.ADVANTAGE, rawmarketPrice.getAdvantage());
        parameters.put(Columns.DISADVANTAGE, rawmarketPrice.getDisadvantage());
        parameters.put(Columns.POPULATION, rawmarketPrice.getPopulation());
        parameters.put(Columns.MIGRATION_RATE, rawmarketPrice.getMigrationRate());
        parameters.put(Columns.USER_ID, rawmarketPrice.getUserId());
        parameters.put(Columns.LAST_UPDATED_TIME_STAMP, new Date());

        Number newId = insertRawMarketPrice.executeAndReturnKey(parameters);
        rawmarketPrice = findById(newId.intValue());
        return rawmarketPrice;
    }

    private final RowMapper<RawMarketPriceDAL.DistinctLocations> distinctLocationsMapper = new RowMapper<RawMarketPriceDAL.DistinctLocations>() {

        @Override
        public RawMarketPriceDAL.DistinctLocations mapRow(ResultSet rs, int i) throws SQLException {
            RawMarketPriceDAL.DistinctLocations distinctLocations = new RawMarketPriceDAL.DistinctLocations();

            distinctLocations.setCityId(rs.getInt(RawMarketPriceDAL.Columns.CITY_ID));
            if (rs.wasNull()) {
                distinctLocations.setCityId(null);
            }
            distinctLocations.setLocationTypeId(rs.getInt(RawMarketPriceDAL.Columns.LOCATION_TYPE_ID));
            if (rs.wasNull()) {
                distinctLocations.setLocationTypeId(null);
            }
            String locationCategoryList = rs.getString(RawMarketPriceDAL.Columns.LOCATION_CATEGORIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> locationCategories = mapper.readValue(locationCategoryList, new TypeReference<List<Integer>>() {
                });
                logger.info("locationCategories:{}", locationCategories);
                distinctLocations.setLocationCategories(locationCategories);

            } catch (IOException ex) {
                throw new RuntimeException("Error parsing locationCategoriesList: '" + locationCategoryList + "' ", ex);
            }

            distinctLocations.setSafedealZoneId(rs.getInt(RawMarketPriceDAL.Columns.SAFEDEAL_ZONE_ID));
            if (rs.wasNull()) {
                distinctLocations.setSafedealZoneId(null);
            }

            distinctLocations.setLocationName(rs.getString(RawMarketPriceDAL.Columns.LOCATION_NAME));
            distinctLocations.setAdvantage(rs.getString(RawMarketPriceDAL.Columns.ADVANTAGE));
            distinctLocations.setDisadvantage(rs.getString(RawMarketPriceDAL.Columns.DISADVANTAGE));
            distinctLocations.setPopulation(rs.getInt(RawMarketPriceDAL.Columns.POPULATION));
            distinctLocations.setSourceOfWater(rs.getString(RawMarketPriceDAL.Columns.SOURCE_OF_WATER));
            distinctLocations.setPublicTransport(rs.getString(RawMarketPriceDAL.Columns.PUBLIC_TRANSPORT));
            distinctLocations.setMajorApproachRoad(rs.getString(RawMarketPriceDAL.Columns.MAJOR_APPROACH_ROAD));
            distinctLocations.setDescription(rs.getString(RawMarketPriceDAL.Columns.DESCRIPTION));
            distinctLocations.setIsCommercialCenter(rs.getBoolean(RawMarketPriceDAL.Columns.IS_COMMERCIAL_CENTER));

            System.out.println("::::::distinctLocations::::" + distinctLocations);
            return distinctLocations;
        }

    };

    private final RowMapper<RawMarketPrice> rawMarketPriceRowMapper = new RowMapper<RawMarketPrice>() {

        @Override
        public RawMarketPrice mapRow(ResultSet rs, int i) throws SQLException {
            RawMarketPrice rawMarketPrice = new RawMarketPrice();
            rawMarketPrice.setId(rs.getInt(RawMarketPriceDAL.Columns.ID));

            rawMarketPrice.setCityId(rs.getInt(RawMarketPriceDAL.Columns.CITY_ID));
            if (rs.wasNull()) {
                rawMarketPrice.setCityId(null);
            }
            rawMarketPrice.setLocationTypeId(rs.getInt(RawMarketPriceDAL.Columns.LOCATION_TYPE_ID));
            if (rs.wasNull()) {
                rawMarketPrice.setLocationTypeId(null);
            }
            String locationCategoryList = rs.getString(RawMarketPriceDAL.Columns.LOCATION_CATEGORIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> locationCategories = mapper.readValue(locationCategoryList, new TypeReference<List<Integer>>() {
                });
                logger.info("locationCategories:{}", locationCategories);
                rawMarketPrice.setLocationCategories(locationCategories);

            } catch (IOException ex) {
                throw new RuntimeException("Error parsing locationCategoriesList: '" + locationCategoryList + "' ", ex);
            }

            rawMarketPrice.setSafedealZoneId(rs.getInt(RawMarketPriceDAL.Columns.SAFEDEAL_ZONE_ID));
            if (rs.wasNull()) {
                rawMarketPrice.setSafedealZoneId(null);
            }
            rawMarketPrice.setLocationName(rs.getString(RawMarketPriceDAL.Columns.LOCATION_NAME));
            rawMarketPrice.setMpAgriLandLowest(rs.getDouble(RawMarketPriceDAL.Columns.MP_AGRI_LAND_LOWEST));
            rawMarketPrice.setMpAgriLandHighest(rs.getDouble(RawMarketPriceDAL.Columns.MP_AGRI_LAND_HIGHEST));
            rawMarketPrice.setMpPlotLowest(rs.getDouble(RawMarketPriceDAL.Columns.MP_PLOT_LOWEST));
            rawMarketPrice.setMpPlotHighest(rs.getDouble(RawMarketPriceDAL.Columns.MP_PLOT_HIGHEST));
            rawMarketPrice.setMpResidentialLowest(rs.getDouble(RawMarketPriceDAL.Columns.MP_RESIDENTIAL_LOWEST));
            rawMarketPrice.setMpResidentialHighest(rs.getDouble(RawMarketPriceDAL.Columns.MP_RESIDENTIAL_HIGHEST));
            rawMarketPrice.setMpCommercialLowest(rs.getDouble(RawMarketPriceDAL.Columns.MP_COMMERCIAL_LOWEST));
            rawMarketPrice.setMpCommercialHighest(rs.getDouble(RawMarketPriceDAL.Columns.MP_COMMERCIAL_HIGHEST));
            rawMarketPrice.setIsCommercialCenter(rs.getBoolean(RawMarketPriceDAL.Columns.IS_COMMERCIAL_CENTER));
            rawMarketPrice.setMajorApproachRoad(rs.getString(RawMarketPriceDAL.Columns.MAJOR_APPROACH_ROAD));
            rawMarketPrice.setSourceOfWater(rs.getString(RawMarketPriceDAL.Columns.SOURCE_OF_WATER));
            rawMarketPrice.setPublicTransport(rs.getString(RawMarketPriceDAL.Columns.PUBLIC_TRANSPORT));
            rawMarketPrice.setAdvantage(rs.getString(RawMarketPriceDAL.Columns.ADVANTAGE));
            rawMarketPrice.setDisadvantage(rs.getString(RawMarketPriceDAL.Columns.DISADVANTAGE));
            rawMarketPrice.setPopulation(rs.getInt(RawMarketPriceDAL.Columns.POPULATION));
            rawMarketPrice.setMigrationRate(rs.getInt(RawMarketPriceDAL.Columns.MIGRATION_RATE));

            rawMarketPrice.setUserId(rs.getInt(RawMarketPriceDAL.Columns.USER_ID));
            if (rs.wasNull()) {
                rawMarketPrice.setUserId(null);
            }

            rawMarketPrice.setLastUpdatedTimeStamp(rs.getTimestamp(Columns.LAST_UPDATED_TIME_STAMP));

            return rawMarketPrice;
        }

    };

}

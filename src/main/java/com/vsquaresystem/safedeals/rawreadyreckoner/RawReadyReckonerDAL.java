package com.vsquaresystem.safedeals.rawreadyreckoner;

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
public class RawReadyReckonerDAL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TABLE_NAME = "raw_ready_reckoner";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertRawReadyreckoner;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static final class Columns {

        public static final String ID = "id";
        public static final String CITY_ID = "city_id";
        public static final String LOCATION = "location";
        public static final String SAFEDEAL_ZONE_ID = "safedeal_zone_id";
        public static final String LOCATION_TYPE_ID = "location_type_id";
        public static final String LOCATION_CATEGORIES = "location_categories";
        public static final String RR_YEAR = "rr_year";
        public static final String RR_RATE_LAND = "rr_rate_land";
        public static final String RR_RATE_PLOT = "rr_rate_plot";
        public static final String RR_RATE_APARTMENT = "rr_rate_apartment";

    };

    @Autowired
    public RawReadyReckonerDAL(DataSource dataSource) {
        logger.info("dataSource in constructor {}", dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertRawReadyreckoner = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(
                        Columns.CITY_ID,
                        Columns.LOCATION,
                        Columns.SAFEDEAL_ZONE_ID,
                        Columns.LOCATION_TYPE_ID,
                        Columns.LOCATION_CATEGORIES,
                        Columns.RR_YEAR,
                        Columns.RR_RATE_LAND,
                        Columns.RR_RATE_PLOT,
                        Columns.RR_RATE_APARTMENT
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<RawReadyReckoner> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ? ";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, rawReadyReckonerRowMapper);
    }

    public List<RawReadyReckoner> getAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, rawReadyReckonerRowMapper);
    }

    public void truncateAll() {
        String sqlQuery = "TRUNCATE " + TABLE_NAME;
        jdbcTemplate.update(sqlQuery, new Object[]{});
    }

    public RawReadyReckoner findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, rawReadyReckonerRowMapper);
    }
    
    public final class DistinctLocations {

        private Integer cityId;
        private String location;
        private Integer safedealZoneId;
        private Integer locationTypeId;
        private List<Integer> locationCategories;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        @Override
        public String toString() {
            return "DistinctLocations{" + "cityId=" + cityId + ", location=" + location + ", safedealZoneId=" + safedealZoneId + ", locationTypeId=" + locationTypeId + ", locationCategories=" + locationCategories + '}';
        }

    }

    public List<DistinctLocations> findByUniqueLocation() {
        String sqlQuery = "SELECT distinct(location), city_id, safedeal_zone_id, location_type_id, location_categories FROM " + TABLE_NAME + " WHERE deleted = FALSE";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, distinctLocationsMapper);
    }

    

    public RawReadyReckoner insert(RawReadyReckoner rawReadyReckoner) throws JsonProcessingException {
        logger.info("rawReadyReckoner in DAL {}", rawReadyReckoner);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.CITY_ID, rawReadyReckoner.getCityId());
        parameters.put(Columns.LOCATION, rawReadyReckoner.getLocation());
        parameters.put(Columns.SAFEDEAL_ZONE_ID, rawReadyReckoner.getSafedealZoneId());
        parameters.put(Columns.LOCATION_TYPE_ID, rawReadyReckoner.getLocationTypeId());
        parameters.put(Columns.LOCATION_CATEGORIES, rawReadyReckoner.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(rawReadyReckoner.getLocationCategories()));
        parameters.put(Columns.RR_YEAR, rawReadyReckoner.getRrYear());
        parameters.put(Columns.RR_RATE_LAND, rawReadyReckoner.getRrRateLand());
        parameters.put(Columns.RR_RATE_PLOT, rawReadyReckoner.getRrRatePlot());
        parameters.put(Columns.RR_RATE_APARTMENT, rawReadyReckoner.getRrRateApartment());

        Number newId = insertRawReadyreckoner.executeAndReturnKey(parameters);
        rawReadyReckoner = findById(newId.intValue());
        return rawReadyReckoner;
    }

    public RawReadyReckoner update(RawReadyReckoner rawReadyReckoner) throws JsonProcessingException {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.CITY_ID + " = ?, "
                + Columns.LOCATION + " = ?, "
                + Columns.SAFEDEAL_ZONE_ID + " = ?, "
                + Columns.LOCATION_TYPE_ID + " = ?, "
                + Columns.LOCATION_CATEGORIES + " = ?, "
                + Columns.RR_YEAR + " = ?, "
                + Columns.RR_RATE_LAND + " = ?, "
                + Columns.RR_RATE_PLOT + " = ?, "
                + Columns.RR_RATE_APARTMENT + " = ? WHERE "
                + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{
            rawReadyReckoner.getCityId(),
            rawReadyReckoner.getLocation(),
            rawReadyReckoner.getSafedealZoneId(),
            rawReadyReckoner.getLocationTypeId(),
            rawReadyReckoner.getLocationCategories() == null ? "[]" : mapper.writeValueAsString(rawReadyReckoner.getLocationCategories()),
            rawReadyReckoner.getRrYear(),
            rawReadyReckoner.getRrRateLand(),
            rawReadyReckoner.getRrRatePlot(),
            rawReadyReckoner.getRrRateApartment(),
            rawReadyReckoner.getId()
        });
        rawReadyReckoner = findById(rawReadyReckoner.getId());
        return rawReadyReckoner;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }
    
    private final RowMapper<DistinctLocations> distinctLocationsMapper = new RowMapper<DistinctLocations>() {

        @Override
        public DistinctLocations mapRow(ResultSet rs, int i) throws SQLException {
            DistinctLocations distinctLocations = new DistinctLocations();
           

            distinctLocations.setCityId(rs.getInt(Columns.CITY_ID));
            if (rs.wasNull()) {
                distinctLocations.setCityId(null);
            }
            distinctLocations.setLocationTypeId(rs.getInt(Columns.LOCATION_TYPE_ID));
            if (rs.wasNull()) {
                distinctLocations.setLocationTypeId(null);
            }
            String locationCategoryList = rs.getString(Columns.LOCATION_CATEGORIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> locationCategories = mapper.readValue(locationCategoryList, new TypeReference<List<Integer>>() {
                });
                logger.info("locationCategories:{}", locationCategories);
                distinctLocations.setLocationCategories(locationCategories);

            } catch (IOException ex) {
                throw new RuntimeException("Error parsing locationCategoriesList: '" + locationCategoryList + "' ", ex);
            }

            distinctLocations.setSafedealZoneId(rs.getInt(Columns.SAFEDEAL_ZONE_ID));
            if (rs.wasNull()) {
                distinctLocations.setSafedealZoneId(null);
            }
            distinctLocations.setLocation(rs.getString(Columns.LOCATION));
            
            System.out.println("::::::distinctLocations::::" + distinctLocations);
            return distinctLocations;
        }

    };

    private final RowMapper<RawReadyReckoner> rawReadyReckonerRowMapper = new RowMapper<RawReadyReckoner>() {

        @Override
        public RawReadyReckoner mapRow(ResultSet rs, int i) throws SQLException {
            RawReadyReckoner rawReadyReckoner = new RawReadyReckoner();
            rawReadyReckoner.setId(rs.getInt(Columns.ID));

            rawReadyReckoner.setCityId(rs.getInt(Columns.CITY_ID));
            if (rs.wasNull()) {
                rawReadyReckoner.setCityId(null);
            }
            rawReadyReckoner.setLocationTypeId(rs.getInt(Columns.LOCATION_TYPE_ID));
            if (rs.wasNull()) {
                rawReadyReckoner.setLocationTypeId(null);
            }
            String locationCategoryList = rs.getString(Columns.LOCATION_CATEGORIES);
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> locationCategories = mapper.readValue(locationCategoryList, new TypeReference<List<Integer>>() {
                });
                logger.info("locationCategories:{}", locationCategories);
                rawReadyReckoner.setLocationCategories(locationCategories);

            } catch (IOException ex) {
                throw new RuntimeException("Error parsing locationCategoriesList: '" + locationCategoryList + "' ", ex);
            }

            rawReadyReckoner.setSafedealZoneId(rs.getInt(Columns.SAFEDEAL_ZONE_ID));
            if (rs.wasNull()) {
                rawReadyReckoner.setSafedealZoneId(null);
            }
            rawReadyReckoner.setLocation(rs.getString(Columns.LOCATION));
            rawReadyReckoner.setRrYear(rs.getDouble(Columns.RR_YEAR));
            rawReadyReckoner.setRrRateLand(rs.getDouble(Columns.RR_RATE_LAND));
            rawReadyReckoner.setRrRatePlot(rs.getDouble(Columns.RR_RATE_PLOT));
            rawReadyReckoner.setRrRateApartment(rs.getDouble(Columns.RR_RATE_APARTMENT));

            return rawReadyReckoner;
        }

    };
}

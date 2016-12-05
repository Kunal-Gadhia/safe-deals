package com.vsquaresystem.safedeals.marketprice;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MarketPriceDAL {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String TABLE_NAME = "market_price";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMarketPrice;

    public static final class Columns {

        public static final String ID = "id";
        public static final String LOCATION_ID = "location_id";
        public static final String CITY_ID = "city_id";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String MP_AGRI_LAND_LOWEST = "mp_agri_land_lowest";
        public static final String MP_AGRI_LAND_HIGHEST = "mp_agri_land_highest";
        public static final String MP_AGRI_LAND_AVERAGE = "mp_agri_land_average";
        public static final String MP_PLOT_LOWEST = "mp_plot_lowest";
        public static final String MP_PLOT_HIGHEST = "mp_plot_highest";
        public static final String MP_PLOT_AVERAGE = "mp_plot_average";
        public static final String MP_RESIDENTIAL_LOWEST = "mp_residential_lowest";
        public static final String MP_RESIDENTIAL_HIGHEST = "mp_residential_highest";
        public static final String MP_RESIDENTIAL_AVERAGE = "mp_residential_average";
        public static final String MP_COMMERCIAL_LOWEST = "mp_commercial_lowest";
        public static final String MP_COMMERCIAL_HIGHEST = "mp_commercial_highest";
        public static final String MP_COMMERCIAL_AVERAGE = "mp_commercial_average";

    };

    @Autowired
    public MarketPriceDAL(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertMarketPrice = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(MarketPriceDAL.Columns.LOCATION_ID,
                        Columns.CITY_ID,
                        Columns.YEAR,
                        Columns.MONTH,
                        Columns.MP_AGRI_LAND_LOWEST,
                        Columns.MP_AGRI_LAND_HIGHEST,
                        Columns.MP_AGRI_LAND_AVERAGE,
                        Columns.MP_PLOT_LOWEST,
                        Columns.MP_PLOT_HIGHEST,
                        Columns.MP_PLOT_AVERAGE,
                        Columns.MP_RESIDENTIAL_LOWEST,
                        Columns.MP_RESIDENTIAL_HIGHEST,
                        Columns.MP_RESIDENTIAL_AVERAGE,
                        Columns.MP_COMMERCIAL_LOWEST,
                        Columns.MP_COMMERCIAL_HIGHEST,
                        Columns.MP_COMMERCIAL_AVERAGE
                )
                .usingGeneratedKeyColumns(Columns.ID);
    }

    public List<MarketPrice> findAll(Integer offset) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE LIMIT 5 OFFSET ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{offset}, new BeanPropertyRowMapper<>(MarketPrice.class));
    }

    public List<MarketPrice> getAll() {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE ";
        return jdbcTemplate.query(sqlQuery, new Object[]{}, new BeanPropertyRowMapper<>(MarketPrice.class));
    }

    public List<MarketPrice> findByLocation(Integer locationId) {
        logger.info("Location Id :" + locationId);
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.LOCATION_ID + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{locationId}, new BeanPropertyRowMapper<>(MarketPrice.class));
    }

    public void truncateAll() {
        String sqlQuery = "TRUNCATE " + TABLE_NAME;
        jdbcTemplate.update(sqlQuery, new Object[]{});
    }

    public MarketPrice findById(Integer id) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.ID + " = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(MarketPrice.class));
    }

    public List<MarketPrice> findByRequirement(Integer cityId, Integer locationMinBudget, Integer locationMaxBudget) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.CITY_ID + " = ? AND " + Columns.MP_RESIDENTIAL_LOWEST + " >= ? AND "
                + Columns.MP_RESIDENTIAL_LOWEST + " < ? AND " + Columns.YEAR + " = ? AND " + Columns.MONTH + " = ?";
        return jdbcTemplate.query(sqlQuery, new Object[]{cityId, locationMinBudget, locationMaxBudget, year, month}, new BeanPropertyRowMapper<>(MarketPrice.class));
    }

    public List<MarketPrice> findByRequirementFilter(Integer cityId, Integer locationMinBudget, Integer locationMaxBudget) {
        String sqlQuery = "SELECT * FROM " + TABLE_NAME + " WHERE deleted = FALSE AND " + Columns.CITY_ID + " = ? AND " + Columns.MP_RESIDENTIAL_LOWEST + " >= ? AND "
                + Columns.MP_RESIDENTIAL_LOWEST + " < ?";
        System.out.println("sqlQuery " + sqlQuery);
        return jdbcTemplate.query(sqlQuery, new Object[]{cityId, locationMinBudget, locationMaxBudget}, new BeanPropertyRowMapper<>(MarketPrice.class));
    }

    public MarketPrice insert(MarketPrice marketprice) {
//        logger.info("marketprice:::::line59", marketprice);
//        System.out.println("are we inside market insert method" + marketprice);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Columns.LOCATION_ID, marketprice.getLocationId());
        parameters.put(Columns.CITY_ID, marketprice.getCityId());
        parameters.put(Columns.YEAR, marketprice.getYear());
        parameters.put(Columns.MONTH, marketprice.getMonth());
        parameters.put(Columns.MP_AGRI_LAND_LOWEST, marketprice.getMpAgriLandLowest());
        parameters.put(Columns.MP_AGRI_LAND_HIGHEST, marketprice.getMpAgriLandHighest());
        parameters.put(Columns.MP_AGRI_LAND_AVERAGE, (marketprice.getMpAgriLandHighest() + marketprice.getMpAgriLandLowest()) / 2);
        parameters.put(Columns.MP_PLOT_LOWEST, marketprice.getMpPlotLowest());
        parameters.put(Columns.MP_PLOT_HIGHEST, marketprice.getMpPlotHighest());
        parameters.put(Columns.MP_PLOT_AVERAGE, (marketprice.getMpPlotHighest() + marketprice.getMpPlotLowest()) / 2);
        parameters.put(Columns.MP_RESIDENTIAL_LOWEST, marketprice.getMpResidentialLowest());
        parameters.put(Columns.MP_RESIDENTIAL_HIGHEST, marketprice.getMpResidentialHighest());
        parameters.put(Columns.MP_RESIDENTIAL_AVERAGE, (marketprice.getMpResidentialHighest() + marketprice.getMpResidentialLowest()) / 2);
        parameters.put(Columns.MP_COMMERCIAL_LOWEST, marketprice.getMpCommercialLowest());
        parameters.put(Columns.MP_COMMERCIAL_HIGHEST, marketprice.getMpCommercialHighest());
        parameters.put(Columns.MP_COMMERCIAL_AVERAGE, (marketprice.getMpCommercialHighest() + marketprice.getMpCommercialLowest()) / 2);
//        System.out.println("insert param" + parameters);
        Number newId = insertMarketPrice.executeAndReturnKey(parameters);
        marketprice = findById(newId.intValue());
        return marketprice;
    }

    public MarketPrice update(MarketPrice marketprice) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET "
                + Columns.LOCATION_ID + " = ?, "
                + Columns.CITY_ID + " = ?, "
                + Columns.YEAR + " = ?, "
                + Columns.MONTH + "= ?, "
                + Columns.MP_AGRI_LAND_LOWEST + " = ?, "
                + Columns.MP_AGRI_LAND_HIGHEST + " = ?, "
                + Columns.MP_AGRI_LAND_AVERAGE + " = ?, "
                + Columns.MP_PLOT_LOWEST + " = ?, "
                + Columns.MP_PLOT_HIGHEST + " = ?, "
                + Columns.MP_PLOT_AVERAGE + " = ?, "
                + Columns.MP_RESIDENTIAL_LOWEST + " = ?, "
                + Columns.MP_RESIDENTIAL_HIGHEST + " = ?, "
                + Columns.MP_RESIDENTIAL_AVERAGE + " = ?, "
                + Columns.MP_COMMERCIAL_LOWEST + " = ?, "
                + Columns.MP_COMMERCIAL_HIGHEST + " = ?, "
                + Columns.MP_COMMERCIAL_AVERAGE + " = ? WHERE " + Columns.ID + " = ?";
        Number updatedCount = jdbcTemplate.update(sqlQuery, new Object[]{
            marketprice.getLocationId(),
            marketprice.getCityId(),
            marketprice.getYear(),
            marketprice.getMonth(),
            marketprice.getMpAgriLandLowest(),
            marketprice.getMpAgriLandHighest(),
            (marketprice.getMpAgriLandHighest() + marketprice.getMpAgriLandLowest()) / 2,
            marketprice.getMpPlotLowest(),
            marketprice.getMpPlotHighest(),
            (marketprice.getMpPlotHighest() + marketprice.getMpPlotLowest()) / 2,
            marketprice.getMpResidentialLowest(),
            marketprice.getMpResidentialHighest(),
            (marketprice.getMpResidentialHighest() + marketprice.getMpResidentialLowest()) / 2,
            marketprice.getMpCommercialLowest(),
            marketprice.getMpCommercialHighest(),
            (marketprice.getMpCommercialHighest() + marketprice.getMpCommercialLowest()) / 2,
            marketprice.getId()});
        marketprice = findById(marketprice.getId());
        return marketprice;
    }

    public void delete(Integer id) {
        String sqlQuery = "UPDATE " + TABLE_NAME + " SET deleted = ? WHERE " + Columns.ID + " = ?";
        jdbcTemplate.update(sqlQuery, new Object[]{true, id});
    }

}

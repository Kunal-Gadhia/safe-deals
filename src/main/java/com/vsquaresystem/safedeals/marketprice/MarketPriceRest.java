package com.vsquaresystem.safedeals.marketprice;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/market_price")

public class MarketPriceRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MarketPriceDAL marketpriceDal;

    @Autowired
    private MarketPriceService marketpriceservice;

    @RequestMapping(method = RequestMethod.GET)
    public List<MarketPrice> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return marketpriceDal.findAll(offset);
    }

    @RequestMapping(value = "/market_prices", method = RequestMethod.GET)
    public List<MarketPrice> getAll() {
        return marketpriceDal.getAll();
    }

    @RequestMapping(value = "/find_by_location", method = RequestMethod.GET)
    public List<MarketPrice> findByLocation(@RequestParam("locationId") Integer locationId) {
        return marketpriceDal.findByLocation(locationId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MarketPrice findById(@PathVariable("id") Integer id) {
        return marketpriceDal.findById(id);
    }

    @RequestMapping(value = "/find_by_requirement", method = RequestMethod.GET)
    public List<MarketPrice> findByRequirement(
            @RequestParam("cityId") Integer cityId,
            @RequestParam("locationMinBudget") Integer locationMinBudget,
            @RequestParam("locationMaxBudget") Integer locationMaxBudget) {
        return marketpriceDal.findByRequirement(cityId, locationMinBudget, locationMaxBudget);
    }

    @RequestMapping(value = "/find_by_requiremen_filter", method = RequestMethod.GET)
    public List<MarketPrice> findByRequirementFilter(
            @RequestParam("cityId") Integer cityId,
            @RequestParam("locationMinBudget") Integer locationMinBudget,
            @RequestParam("locationMaxBudget") Integer locationMaxBudget) {
        return marketpriceDal.findByRequirementFilter(cityId, locationMinBudget, locationMaxBudget);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MarketPrice insert(@RequestBody MarketPrice marketprice) {
        return marketpriceDal.insert(marketprice);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        marketpriceDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public MarketPrice update(@RequestBody MarketPrice marketprice) {
        return marketpriceDal.update(marketprice);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public Boolean uploadAttachment(@RequestParam MultipartFile attachment) throws IOException {
//        logger.info("attachment in rest line56 {}", attachment);
        return marketpriceservice.insertAttachments(attachment);
    }

    @RequestMapping(value = "/read")
    public Boolean checkExcelData() throws IOException {
        logger.info("CHECK EXCEL DATA {}");
        return marketpriceservice.checkExistingData();
    }

    @RequestMapping(value = "/save_excel")
    public Boolean saveExcelData() throws IOException {
        logger.info("SAVE EXCEL DATA {line78}");
        System.out.println("REST SAVE EXCEL");
        marketpriceservice.saveExcelToDatabase();
        return true;
    }
}

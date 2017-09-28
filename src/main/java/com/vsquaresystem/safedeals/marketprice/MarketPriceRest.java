package com.vsquaresystem.safedeals.marketprice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private UserDAL userDAL;

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

    @RequestMapping(value = "/find_by_location/year", method = RequestMethod.GET)
    public List<MarketPrice> findByLocationAndYear(@RequestParam("locationId") Integer locationId, @RequestParam("year") Integer year) {
        return marketpriceDal.findByLocationAndYear(locationId, year);
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
    public MarketPrice insert(@RequestBody MarketPrice marketprice,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        marketprice.setUserId(user.getId());
        return marketpriceDal.insert(marketprice);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        marketpriceDal.delete(id, user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public MarketPrice update(@RequestBody MarketPrice marketprice,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        marketprice.setUserId(user.getId());
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

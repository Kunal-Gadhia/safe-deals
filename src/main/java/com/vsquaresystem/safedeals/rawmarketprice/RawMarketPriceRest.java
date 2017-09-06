/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.rawmarketprice;

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

/**
 *
 * @author hp-pc
 */
@RestController
@RequestMapping("/rawmarketprice")
public class RawMarketPriceRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RawMarketPriceDAL rawmarketpriceDAL;

    @Autowired
    private UserDAL userDAL;

    @Autowired
    private RawMarketPriceService rawmarketpriceservice;

    @RequestMapping(method = RequestMethod.GET)
    public List<RawMarketPrice> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {

        return rawmarketpriceDAL.findAll(offset);
    }

    @RequestMapping(value = "/raw_market_prices", method = RequestMethod.GET)
    public List<RawMarketPrice> getAll() {
        return rawmarketpriceDAL.getAll();
    }

    @RequestMapping(value = "/raw_market_price", method = RequestMethod.GET)
    public List<RawMarketPrice> findAllRawMarketPrice() {
        return rawmarketpriceDAL.findAllRawMarketPrice();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RawMarketPrice findById(@PathVariable("id") Integer id) {
        return rawmarketpriceDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RawMarketPrice insert(@RequestBody RawMarketPrice rawMarketPrice,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        rawMarketPrice.setUserId(user.getId());
        return rawmarketpriceDAL.insert(rawMarketPrice);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        rawmarketpriceDAL.delete(id, user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RawMarketPrice update(@RequestBody RawMarketPrice rawMarketPrice,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        rawMarketPrice.setUserId(user.getId());
        return rawmarketpriceDAL.update(rawMarketPrice);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public Boolean uploadAttachment(@RequestParam MultipartFile attachment) throws IOException {
        logger.info("attachment in rest line56 {}", attachment);
        return rawmarketpriceservice.insertAttachments(attachment);
    }

    @RequestMapping(value = "/read")
    public Boolean checkExcelData() throws IOException {
        logger.info("CHECK EXCEL DATA {}");
        return rawmarketpriceservice.checkExistingData();
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Boolean exportAllRawMarketPrice() throws IOException {
        logger.info("exportExcelData EXCEL DATA {}");
        return rawmarketpriceservice.exportExcel();
    }

    @RequestMapping(value = "/save_excel")
    public Boolean saveExcelData() throws IOException {
        logger.info("SAVE EXCEL DATA {line78}");
        System.out.println("REST SAVE EXCEL");
        rawmarketpriceservice.saveExcelToDatabase();
        return true;

    }

    @RequestMapping(value = "/find_by_unique_location", method = RequestMethod.GET)
    public List<RawMarketPriceDAL.DistinctLocations> findByUniqueLocation() {
        return rawmarketpriceDAL.findByUniqueLocation();
    }

    @RequestMapping(value = "/find_by_unique_location_city", method = RequestMethod.GET)
    public List<RawMarketPriceDAL.DistinctLocations> findByUniqueLocationWithCityId(@RequestParam("cityId") Integer cityId) {
        logger.info("City Id :" + cityId);
        return rawmarketpriceDAL.findByUniqueLocationWithCityId(cityId);
    }
//    
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public RawMarketPrice update(@RequestBody RawMarketPrice rawMarketPrice)  throws JsonProcessingException, ParseException {
//        return rawmarketpriceDAL.update(rawMarketPrice);
//    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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

/**
 *
 * @author hp4
 */
@RestController
@RequestMapping("/location")
public class LocationRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LocationDAL locationDAL;

    @Autowired
    private LocationService locationservice;

//    @RequestMapping(value = "/current", method = RequestMethod.GET)
//    public org.springframework.security.core.locationdetails.Location getPrincipal(
//            @AuthenticationPrincipal org.springframework.security.core.locationdetails.Location location) {
//        return location;
//    }
    @RequestMapping(method = RequestMethod.GET)
    public List<Location> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        logger.info("Coming to find all rest");
        return locationDAL.findAll(offset);
    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public List<Location> findAllLocations() {
        return locationDAL.findAllLocations();
    }

    @RequestMapping(value = "/find/location_like", method = RequestMethod.GET)
    public List<Location> findByNameLike(@RequestParam("name") String name) {
        logger.info("Name in REST :" + name);
        return locationDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Boolean exportExcelData() throws IOException {
        logger.info("exportExcelData EXCEL DATA {}");
        return locationservice.exportExcel();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Location findById(@PathVariable("id") Integer id) throws SQLException {
        return locationDAL.findById(id);

    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<Location> findByNameAndCityId(
            @RequestParam("name") String name,
            @RequestParam("cityId") Integer cityId) {
        return locationDAL.findByNameAndCityId(name, cityId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Location insert(@RequestBody Location location) throws JsonProcessingException, ParseException {
        logger.info("location obj in rest {}", location);
        return locationDAL.insert(location);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Location update(@RequestBody Location location) throws Exception {
        return locationDAL.update(location);
    }

//    @RolesAllowed("ROLE_SUPER_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) throws Exception {
        locationDAL.delete(id);
    }

    @RequestMapping(value = "/find/location", method = RequestMethod.GET)
    public Location findByName(@RequestParam("location") String location) throws Exception {
        return locationDAL.findByName(location);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public Boolean uploadAttachment(@RequestParam MultipartFile attachment) throws IOException {
        logger.info("attachment in rest line56 {}", attachment);
        return locationservice.insertAttachments(attachment);
    }

    @RequestMapping(value = "/save_excel")
    public Boolean saveExcelData() throws IOException {
        logger.info("SAVE EXCEL DATA {line78}");
        System.out.println("REST SAVE EXCEL");
        locationservice.saveExcelToDatabase();
        return true;
    }

}

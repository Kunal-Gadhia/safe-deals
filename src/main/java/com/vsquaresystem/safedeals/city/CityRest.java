/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.city;

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

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/city")
public class CityRest {

    @Autowired
    private CityDAL cityDAL;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityService cityservice;

    @RequestMapping(method = RequestMethod.GET)
    public List<City> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return cityDAL.findAll(offset);

    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public List<City> findAllCities() {
        return cityDAL.findAllCities();
    }

    @RequestMapping(value = "/find/city_like", method = RequestMethod.GET)
    public List<City> findByNameLike(@RequestParam("name") String name) {
        return cityDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public City findById(@PathVariable("id") Integer id) {

        return cityDAL.findById(id);
    }

    @RequestMapping(value = "/find_by_name", method = RequestMethod.GET)
    public City findByCityName(@RequestParam("name") String name) {
        return cityDAL.findByCityName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public City insert(@RequestBody City city) {
        return cityDAL.insert(city);
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Boolean exportExcelData() throws IOException {
        logger.info("export city ExcelData EXCEL DATA {}");
        return cityservice.exportExcel();
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<City> findByNameAndStateId(
            @RequestParam("name") String name,
            @RequestParam("stateId") Integer stateId) {
        return cityDAL.findByNameAndStateId(name, stateId);
    }

    @RequestMapping(value = "/find/country/name", method = RequestMethod.GET)
    public List<City> findByNameAndCountryId(
            @RequestParam("name") String name,
            @RequestParam("countryId") Integer countryId) {
        return cityDAL.findByNameAndCountryId(name, countryId);
    }

    @RequestMapping(value = "/find/state_id", method = RequestMethod.GET)
    public List<City> findByStateId(@RequestParam("stateId") Integer stateId) {
        return cityDAL.findByStateId(stateId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        cityDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public City update(@RequestBody City city) {
        return cityDAL.update(city);
    }
}

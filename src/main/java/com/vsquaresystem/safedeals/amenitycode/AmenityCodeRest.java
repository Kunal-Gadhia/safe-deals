/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.amenitycode;

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
 * @author hp2
 */
@RestController
@RequestMapping("/amenity_code")
public class AmenityCodeRest {

    @Autowired
    private AmenityCodeDAL amenityCodeDAL;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<AmenityCode> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return amenityCodeDAL.findAll(offset);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<AmenityCode> findByNameLike(@RequestParam("name") String name) {
        return amenityCodeDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public AmenityCode findById(@PathVariable("id") Integer id) {

        return amenityCodeDAL.findById(id);
    }

    @RequestMapping(value = "/find_by_name", method = RequestMethod.GET)
    public AmenityCode findByName(@RequestParam("name") String name) {
        return amenityCodeDAL.findByName(name);
    }

    @RequestMapping(value = "/find_by_tab_name", method = RequestMethod.GET)
    public List<AmenityCode> findByTabName(@RequestParam("name") String name) {
        return amenityCodeDAL.findByTabName(name);
    }
    
    @RequestMapping(value = "/find_amenities", method = RequestMethod.GET)
    public List<AmenityCode> findAmenities() {
        return amenityCodeDAL.findAmenities();
    }
    
    @RequestMapping(value = "/find_workplaces", method = RequestMethod.GET)
    public List<AmenityCode> findWorkplaces() {
        return amenityCodeDAL.findWorkplaces();
    }
    
    @RequestMapping(value = "/find_projects", method = RequestMethod.GET)
    public List<AmenityCode> findProjects() {
        return amenityCodeDAL.findProjects();
    }
    
    @RequestMapping(value = "/find_properties", method = RequestMethod.GET)
    public List<AmenityCode> findProperties() {
        return amenityCodeDAL.findProperties();
    }

    @RequestMapping(method = RequestMethod.POST)
    public AmenityCode insert(@RequestBody AmenityCode amenityCode) {
        return amenityCodeDAL.insert(amenityCode);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        amenityCodeDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public AmenityCode update(@RequestBody AmenityCode amenityCode) {
        return amenityCodeDAL.update(amenityCode);
    }
}

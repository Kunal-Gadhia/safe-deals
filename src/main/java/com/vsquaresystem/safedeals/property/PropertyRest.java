/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.property;

import java.util.List;
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
@RequestMapping("/property")
public class PropertyRest {

    @Autowired
    private PropertyDAL propertyDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Property> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return propertyDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Property findById(@PathVariable("id") Integer id) {
        return propertyDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Property insert(@RequestBody Property property) {
        return propertyDal.insert(property);
    }

    @RequestMapping(value = "/find/location_id", method = RequestMethod.GET)
    public List<Property> findByLocationId(@RequestParam("locationId") Integer locationId) {
        return propertyDal.findByLocationId(locationId);
    }
    @RequestMapping(value = "/find/property_cost", method = RequestMethod.GET)
    public List<Property> findByPropertyCost(@RequestParam("propertyCost") Double propertyCost) {
        return propertyDal.findByPropertyCost(propertyCost);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<Property> findByName(@RequestParam("name") String name) {
        return propertyDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        propertyDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Property update(@RequestBody Property property) {
        return propertyDal.update(property);
    }
}

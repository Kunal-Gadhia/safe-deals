/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.locationtype;

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
 * @author lenovo-user
 */
@RestController
@RequestMapping("/location_type")
public class LocationTypeRest {

    @Autowired
    private LocationTypeDAL locationTypeDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<LocationType> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return locationTypeDAL.findAll(offset);

    }

    @RequestMapping(value = "/locationtypes", method = RequestMethod.GET)
    public List<LocationType> findAllLocationTypes() {
        return locationTypeDAL.findAllLocationTypes();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public LocationType findById(@PathVariable("id") Integer id) {
        return locationTypeDAL.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public LocationType findByName(@RequestParam("name") String name) {
        return locationTypeDAL.findByName(name);
    }

    @RequestMapping(value = "/find/location_type_like", method = RequestMethod.GET)
    public List<LocationType> findByNameLike(@RequestParam("name") String name) {
        return locationTypeDAL.findByNameLike(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public LocationType insert(@RequestBody LocationType locationType) {
        return locationTypeDAL.insert(locationType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public LocationType update(@RequestBody LocationType locationType) {
        return locationTypeDAL.update(locationType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        locationTypeDAL.delete(id);
    }

}

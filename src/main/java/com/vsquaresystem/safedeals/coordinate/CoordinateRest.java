/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.coordinate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinate")
public class CoordinateRest {

    @Autowired
    private CoordinateDAL coordinateDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Coordinate> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return coordinateDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Coordinate findById(@PathVariable("id") Integer id) {
        return coordinateDal.findById(id);
    }

    @RequestMapping(value = "/find/location_id", method = RequestMethod.GET)
    public List<Coordinate> findByLocationId(@RequestParam("locationId") Integer locationId) {
        return coordinateDal.findByLocationId(locationId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        coordinateDal.delete(id);
    }

}

/*
 * To change this license header, choose License Headers in PrivateAmenities Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.privateamenities;

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
@RequestMapping("/private_amenities")
public class PrivateAmenitiesRest {

    @Autowired
    private PrivateAmenitiesDAL privateAmenitiesDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<PrivateAmenities> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return privateAmenitiesDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PrivateAmenities findById(@PathVariable("id") Integer id) {
        return privateAmenitiesDAL.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public PrivateAmenities findByName(@RequestParam("name") String name) {
        return privateAmenitiesDAL.findByName(name);
    }
    
    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<PrivateAmenities> findByNameLike(@RequestParam("name") String name) {
        return privateAmenitiesDAL.findByNameLike(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public PrivateAmenities insert(@RequestBody PrivateAmenities privateAmenities) {
        return privateAmenitiesDAL.insert(privateAmenities);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        privateAmenitiesDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public PrivateAmenities update(@RequestBody PrivateAmenities privateAmenities) {
        return privateAmenitiesDAL.update(privateAmenities);
    }
}

/*
 * To change this license header, choose License Headers in PrivateAmenities Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.privateamenities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private UserDAL userDAL;

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
    public PrivateAmenities insert(@RequestBody PrivateAmenities privateAmenities,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        privateAmenities.setUserId(user.getId());
        return privateAmenitiesDAL.insert(privateAmenities);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        privateAmenitiesDAL.delete(id, user.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public PrivateAmenities update(@RequestBody PrivateAmenities privateAmenities,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        privateAmenities.setUserId(user.getId());
        return privateAmenitiesDAL.update(privateAmenities);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.societymaintenance;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/society_maintainance")
public class SocietyMaintainanceRest {

    @Autowired
    private SocietyMaintainanceDAL societyMaintainanceDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<SocietyMaintainance> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return societyMaintainanceDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SocietyMaintainance findById(@PathVariable("id") Integer id) {
        return societyMaintainanceDAL.findById(id);
    }

    @RequestMapping(value = "/find/maintenance_name_like", method = RequestMethod.GET)
    public List<SocietyMaintainance> findByNameLike(@RequestParam("maintainanceName") String maintainanceName) {
        return societyMaintainanceDAL.findByNameLike(maintainanceName);
    }

    @RequestMapping(method = RequestMethod.POST)
    public SocietyMaintainance insert(@RequestBody SocietyMaintainance societyMaintainance) throws JsonProcessingException {
        return societyMaintainanceDAL.insert(societyMaintainance);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public SocietyMaintainance update(@RequestBody SocietyMaintainance societyMaintainance) throws JsonProcessingException {
        return societyMaintainanceDAL.update(societyMaintainance);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        societyMaintainanceDAL.delete(id);
    }
}

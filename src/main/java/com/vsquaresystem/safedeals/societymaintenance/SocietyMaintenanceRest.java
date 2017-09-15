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
@RequestMapping("/society_maintenance")
public class SocietyMaintenanceRest {

    @Autowired
    private SocietyMaintenanceDAL societyMaintenanceDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<SocietyMaintenance> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return societyMaintenanceDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SocietyMaintenance findById(@PathVariable("id") Integer id) {
        return societyMaintenanceDAL.findById(id);
    }

    @RequestMapping(value = "/find/maintenance_name_like", method = RequestMethod.GET)
    public List<SocietyMaintenance> findByNameLike(@RequestParam("maintenanceName") String maintenanceName) {
        return societyMaintenanceDAL.findByNameLike(maintenanceName);
    }

    @RequestMapping(method = RequestMethod.POST)
    public SocietyMaintenance insert(@RequestBody SocietyMaintenance societyMaintenance) throws JsonProcessingException {
        return societyMaintenanceDAL.insert(societyMaintenance);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public SocietyMaintenance update(@RequestBody SocietyMaintenance societyMaintenance) throws JsonProcessingException {
        return societyMaintenanceDAL.update(societyMaintenance);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        societyMaintenanceDAL.delete(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.agency;

import com.vsquaresystem.safedeals.franchise.Franchise;
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
@RequestMapping("/agency")
    
public class AgencyRest {
@Autowired
    private AgencyDAL agencyDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Agency> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return agencyDal.findAll(offset);

    }
    @RequestMapping(value = "/agencies", method = RequestMethod.GET)
    public List<Agency> findAllAgencies(){
        return agencyDal.findAllAgencies();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Agency findById(@PathVariable("id") Integer id) {
        return agencyDal.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Agency findByName(@RequestParam("name") String name) {
        return agencyDal.findByName(name);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Agency insert(@RequestBody Agency agency) {
        return agencyDal.insert(agency);
    }   

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        agencyDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Agency update(@RequestBody Agency agency) {
        return agencyDal.update(agency);
    }
}

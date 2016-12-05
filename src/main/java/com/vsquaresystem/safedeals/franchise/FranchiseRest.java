/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.franchise;

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
 * @author Pratik
 */
@RestController
@RequestMapping("/franchise")
public class FranchiseRest {
    
    @Autowired
    private FranchiseDAL franchiseDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Franchise> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return franchiseDal.findAll(offset);

    }

    @RequestMapping(value = "/franchises", method = RequestMethod.GET)
    public List<Franchise> findAllFranchises() {
        return franchiseDal.findAllFranchises();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Franchise findById(@PathVariable("id") Integer id) {
        return franchiseDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Franchise insert(@RequestBody Franchise franchise) {
        return franchiseDal.insert(franchise);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Franchise findByName(@RequestParam("name") String name) {
        return franchiseDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        franchiseDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Franchise update(@RequestBody Franchise franchise) {
        return franchiseDal.update(franchise);
    }
    
}

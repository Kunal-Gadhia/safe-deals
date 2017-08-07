/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.franchisedetails;

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
@RequestMapping("/franchise_details")
public class FranchiseDetailsRest {

    @Autowired
    private FranchiseDetailsDAL franchiseDetailsDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<FranchiseDetails> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return franchiseDetailsDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FranchiseDetails findById(@PathVariable("id") Integer id) {
        return franchiseDetailsDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public FranchiseDetails insert(@RequestBody FranchiseDetails franchiseDetails) {
        return franchiseDetailsDAL.insert(franchiseDetails);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public FranchiseDetails update(@RequestBody FranchiseDetails franchiseDetails) {
        return franchiseDetailsDAL.update(franchiseDetails);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        franchiseDetailsDAL.delete(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.businessassociates;

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
@RequestMapping("/business_associates")
public class BusinessAssociatesRest {

    @Autowired
    private BusinessAssociatesDAL businessAssociatesDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<BusinessAssociates> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return businessAssociatesDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BusinessAssociates findById(@PathVariable("id") Integer id) {
        return businessAssociatesDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BusinessAssociates insert(@RequestBody BusinessAssociates businessAssociates) {
        return businessAssociatesDAL.insert(businessAssociates);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public BusinessAssociates update(@RequestBody BusinessAssociates businessAssociates) {
        return businessAssociatesDAL.update(businessAssociates);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        businessAssociatesDAL.delete(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.bandd;

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
@RequestMapping("/b_and_d")
public class BAndDRest {

    @Autowired
    private BAndDDAL bAndDDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<BAndD> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return bAndDDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BAndD findById(@PathVariable("id") Integer id) {
        return bAndDDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BAndD insert(@RequestBody BAndD bAndD) {
        return bAndDDAL.insert(bAndD);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public BAndD update(@RequestBody BAndD bAndD) {
        return bAndDDAL.update(bAndD);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        bAndDDAL.delete(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.builder;

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
@RequestMapping("/builder")
public class BuilderRest {
    
    @Autowired
    private BuilderDAL builderDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Builder> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return builderDal.findAll(offset);

    }

    @RequestMapping(value = "/builders", method = RequestMethod.GET)
    public List<Builder> findAllBuilders() {
        return builderDal.findAllBuilders();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Builder findById(@PathVariable("id") Integer id) {
        return builderDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Builder insert(@RequestBody Builder builder) {
        return builderDal.insert(builder);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Builder findByName(@RequestParam("name") String name) {
        return builderDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        builderDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Builder update(@RequestBody Builder builder) {
        return builderDal.update(builder);
    }
    
}


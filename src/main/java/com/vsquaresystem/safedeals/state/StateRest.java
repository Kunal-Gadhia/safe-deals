/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.state;

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
@RequestMapping("/state")
public class StateRest {

    @Autowired
    private StateDAL stateDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<State> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return stateDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public State findById(@PathVariable("id") Integer id) {
        return stateDAL.findById(id);
    }

    @RequestMapping(value = "/find/state", method = RequestMethod.GET)
    public State findByState(@RequestParam("name") String name) {
        return stateDAL.findByState(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public State insert(@RequestBody State state) {
        return stateDAL.insert(state);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<State> findByNameAndCountryId(
            @RequestParam("name") String name,
            @RequestParam("countryId") Integer countryId) {
        return stateDAL.findByNameAndCountryId(name, countryId);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<State> findByNameLike(@RequestParam("name") String name) {
        return stateDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/find/country_id", method = RequestMethod.GET)
    public List<State> findByCountryId(@RequestParam("countryId") Integer countryId) {
        return stateDAL.findByCountryId(countryId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        stateDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public State update(@RequestBody State state) {
        return stateDAL.update(state);
    }

}

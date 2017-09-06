/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.road;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author hp2
 */
@RestController
@RequestMapping("/road")
public class RoadRest {

    @Autowired
    private RoadDAL roadDAL;

    @Autowired
    private UserDAL userDAL;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<Road> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return roadDAL.findAll(offset);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<Road> findByNameLike(@RequestParam("name") String name) {
        return roadDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public Road findById(@PathVariable("id") Integer id) {

        return roadDAL.findById(id);
    }

    @RequestMapping(value = "/find_by_name", method = RequestMethod.GET)
    public Road findByName(@RequestParam("name") String name) {
        return roadDAL.findByName(name);
    }

//    @RequestMapping(value = "/find_by_tab_name", method = RequestMethod.GET)
//    public List<Road> findByNameLike(@RequestParam("name") String name) {
//        return roadDAL.findByNameLike(name);
//    }        
    @RequestMapping(method = RequestMethod.POST)
    public Road insert(@RequestBody Road road,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        road.setUserId(user.getId());
        return roadDAL.insert(road);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        roadDAL.delete(id, user.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Road update(@RequestBody Road road,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        road.setUserId(user.getId());
        return roadDAL.update(road);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.road.Road;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.util.List;
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
 * @author hp
 */
@RestController
@RequestMapping("/unit")
public class UnitRest {

    @Autowired
    private UnitDAL unitDAL;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<Unit> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return unitDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Unit findById(@PathVariable("id") Integer id) {
        return unitDAL.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Unit findByName(@RequestParam("name") String name) {
        return unitDAL.findByName(name);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<Unit> findByNameLike(@RequestParam("name") String name) {
        return unitDAL.findByNameLike(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Unit insert(@RequestBody Unit unit,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        unit.setUserId(user.getId());
        return unitDAL.insert(unit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        unitDAL.delete(id, user.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Unit update(@RequestBody Unit unit,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        unit.setUserId(user.getId());
        return unitDAL.update(unit);
    }
}

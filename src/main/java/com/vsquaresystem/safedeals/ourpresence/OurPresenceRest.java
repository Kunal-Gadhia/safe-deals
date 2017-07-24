/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.ourpresence;

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
@RequestMapping("/our_presence")
public class OurPresenceRest {

    @Autowired
    private OurPresenceDAL ourPresenceDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<OurPresence> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return ourPresenceDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OurPresence findById(@PathVariable("id") Integer id) {
        return ourPresenceDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public OurPresence insert(@RequestBody OurPresence ourPresence) {
        return ourPresenceDAL.insert(ourPresence);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public OurPresence update(@RequestBody OurPresence ourPresence) {
        return ourPresenceDAL.update(ourPresence);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        ourPresenceDAL.delete(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.event;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/event")
public class EventRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EventDAL eventDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return eventDal.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event findById(@PathVariable("id") Integer id) {
        return eventDal.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Event findByName(@RequestParam("name") String name) {
        return eventDal.findByName(name);
    }

    @RequestMapping(value = "/find/date", method = RequestMethod.GET)
    public List<Event> findByDate() {        
        return eventDal.findByDate();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Event insert(@RequestBody Event event) {
        return eventDal.insert(event);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Event update(@RequestBody Event event) {
        return eventDal.update(event);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        eventDal.delete(id);
    }

}

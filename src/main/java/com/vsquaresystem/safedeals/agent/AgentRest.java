/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.agent;

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
@RequestMapping("/agent")
public class AgentRest {
    
    @Autowired
    private AgentDAL agentDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Agent> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return agentDal.findAll(offset);

    }

    @RequestMapping(value = "/agents", method = RequestMethod.GET)
    public List<Agent> findAllAgents() {
        return agentDal.findAllAgents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Agent findById(@PathVariable("id") Integer id) {
        return agentDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Agent insert(@RequestBody Agent agent) {
        return agentDal.insert(agent);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Agent findByName(@RequestParam("name") String name) {
        return agentDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        agentDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Agent update(@RequestBody Agent agent) {
        return agentDal.update(agent);
    }
    
}

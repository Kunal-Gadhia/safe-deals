/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.branch;

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
 * @author hp2
 */
@RestController
@RequestMapping("/branch")
public class BranchRest {

    @Autowired
    private BranchDAL branchDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Branch> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return branchDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Branch findById(@PathVariable("id") Integer id) {
        return branchDal.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Branch findByName(@RequestParam("name") String name) {
        return branchDal.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Branch insert(@RequestBody Branch branch) {
        return branchDal.insert(branch);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Branch update(@RequestBody Branch branch) {
        return branchDal.update(branch);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        branchDal.delete(id);
    }

}

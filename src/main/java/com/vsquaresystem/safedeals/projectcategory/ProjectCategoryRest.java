/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.projectcategory;

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
 * @author hp
 */
@RestController
@RequestMapping("/project_category")
public class ProjectCategoryRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ProjectCategoryDAL projectCategoryDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProjectCategory> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return projectCategoryDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProjectCategory findById(@PathVariable("id") Integer id) {
        return projectCategoryDAL.findById(id);
    }

    @RequestMapping(value = "/find/project_category_like", method = RequestMethod.GET)
    public List<ProjectCategory> findByProjectCategoryLike(@RequestParam("category") String category) {
        return projectCategoryDAL.findByProjectCategoryLike(category);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProjectCategory insert(@RequestBody ProjectCategory projectCategory) {
        logger.info("Project category Object REST :{}", projectCategory);
        return projectCategoryDAL.insert(projectCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        projectCategoryDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ProjectCategory update(@RequestBody ProjectCategory projectCategory) {
        return projectCategoryDAL.update(projectCategory);
    }

}

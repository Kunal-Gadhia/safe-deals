package com.vsquaresystem.safedeals.project;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@RestController
@RequestMapping("/project")
public class ProjectRest {

    @Autowired
    private ProjectDAL projectDal;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<Project> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return projectDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project findById(@PathVariable("id") Integer id) {
        return projectDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Project insert(@RequestBody Project project) throws JsonProcessingException {
        logger.info("Insert REST:{}", project);
        return projectDal.insert(project);
    }

    @RequestMapping(value = "/find/location_id", method = RequestMethod.GET)
    public List<Project> findByLocationId(@RequestParam("locationId") Integer locationId) {
        return projectDal.findByLocationId(locationId);
    }

//    @RequestMapping(value = "/find/project_cost", method = RequestMethod.GET)
//    public List<Project> findByProjectCost(@RequestParam("projectCost") Double projectCost) {
//        return projectDal.findByProjectCost(projectCost);
//    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        projectDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Project update(@RequestBody Project project) throws JsonProcessingException {
        return projectDal.update(project);
    }
}

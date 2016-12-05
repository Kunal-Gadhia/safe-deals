package com.vsquaresystem.safedeals.workplacecategory;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workplace_category")
public class WorkplaceCategoryRest {
    @Autowired
    private WorkplaceCategoryDAL workplaceCategoryDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<WorkplaceCategory> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return workplaceCategoryDal.findAll(offset);
    }
    
    @RequestMapping(value = "/find/workplace_category_like", method = RequestMethod.GET)
    public List<WorkplaceCategory> findByNameLike(@RequestParam("name") String name){
        return workplaceCategoryDal.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WorkplaceCategory findById(@PathVariable("id") Integer id) {
        return workplaceCategoryDal.findById(id);
    }
    
    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public WorkplaceCategory findByName(@RequestParam("name") String name) {
        return workplaceCategoryDal.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public WorkplaceCategory insert(@RequestBody WorkplaceCategory workplaceCategory) {
        return workplaceCategoryDal.insert(workplaceCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        workplaceCategoryDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public WorkplaceCategory update(@RequestBody WorkplaceCategory workplaceCategory) {
        return workplaceCategoryDal.update(workplaceCategory);
    }
    
}

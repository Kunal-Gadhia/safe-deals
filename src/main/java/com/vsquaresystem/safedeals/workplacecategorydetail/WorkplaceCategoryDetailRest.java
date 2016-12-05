package com.vsquaresystem.safedeals.workplacecategorydetail;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workplace_category_detail")
public class WorkplaceCategoryDetailRest {
    
    @Autowired
    private WorkplaceCategoryDetailDAL workplaceCategoryDetailDal;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<WorkplaceCategoryDetail> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return workplaceCategoryDetailDal.findAll(offset);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WorkplaceCategoryDetail findById(@PathVariable("id") Integer id) {
        return workplaceCategoryDetailDal.findById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public WorkplaceCategoryDetail insert(@RequestBody WorkplaceCategoryDetail workplaceCategoryDetail) {
        return workplaceCategoryDetailDal.insert(workplaceCategoryDetail);
    }
    
     @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public WorkplaceCategoryDetail update(@RequestBody WorkplaceCategoryDetail workplaceCategoryDetail) {
        return workplaceCategoryDetailDal.update(workplaceCategoryDetail);
    }
        
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        workplaceCategoryDetailDal.delete(id);
    }
}

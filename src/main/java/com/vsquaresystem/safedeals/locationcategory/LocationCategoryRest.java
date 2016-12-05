package com.vsquaresystem.safedeals.locationcategory;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/location_category")
public class LocationCategoryRest {
    
    @Autowired
    private LocationCategoryDAL locationCategoryDAL;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<LocationCategory> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return locationCategoryDAL.findAll(offset);

    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public LocationCategory findById(@PathVariable("id") Integer id) {
        return locationCategoryDAL.findById(id);
    }
    
    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public LocationCategory findByName(@RequestParam("name") String name) {
        return locationCategoryDAL.findByName(name);
    }
    
    @RequestMapping(value = "/find/location_category_like", method = RequestMethod.GET)
    public List<LocationCategory> findByNameLike(@RequestParam("name") String name) {
        return locationCategoryDAL.findByNameLike(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public LocationCategory insert(@RequestBody LocationCategory locationCategory) {
        return locationCategoryDAL.insert(locationCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public LocationCategory update(@RequestBody LocationCategory locationCategory) {
        return locationCategoryDAL.update(locationCategory);
    }
        
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        locationCategoryDAL.delete(id);
    }
    
}



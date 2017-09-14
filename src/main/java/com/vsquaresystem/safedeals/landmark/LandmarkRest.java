package com.vsquaresystem.safedeals.landmark;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/landmark")

public class LandmarkRest {
    
    @Autowired
    private LandmarkDAL landmarkDAL;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Landmark> findAll(
    @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return landmarkDAL.findAll(offset);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Landmark findById(@PathVariable("id") Integer id) {
        return landmarkDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Landmark insert(@RequestBody Landmark landmark) throws JsonProcessingException {
        return landmarkDAL.insert(landmark);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Landmark update(@RequestBody Landmark landmark) throws JsonProcessingException {
        return landmarkDAL.update(landmark);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        landmarkDAL.delete(id);
    }
}

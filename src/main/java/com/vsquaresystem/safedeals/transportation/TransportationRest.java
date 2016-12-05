
package com.vsquaresystem.safedeals.transportation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transportation")
public class TransportationRest {
    
    @Autowired
    private TransportationDAL transportationDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Transportation> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return transportationDal.findAll(offset);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Transportation insert(@RequestBody Transportation transportation) {
        return transportationDal.insert(transportation);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transportation findById(@PathVariable("id") Integer id) {
        return transportationDal.findById(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Transportation update(@RequestBody Transportation transportation) {
        return transportationDal.update(transportation);
    }

    @RequestMapping(value = "/find/location_id", method = RequestMethod.GET)
    public List<Transportation> findByLocationId(@RequestParam("locationId") Integer locationId) {
        return transportationDal.findByLocationId(locationId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        transportationDal.delete(id);
    }

}

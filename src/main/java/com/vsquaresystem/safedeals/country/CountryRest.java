
package com.vsquaresystem.safedeals.country;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryRest {
    
    @Autowired
    private CountryDAL countryDAL;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Country> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {

        return countryDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public Country findById(@PathVariable("id") Integer id) {

        return countryDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Country insert(@RequestBody Country country) {
        return countryDAL.insert(country);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<Country> findByName(@RequestParam("name") String name) {
        return countryDAL.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        countryDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Country update(@RequestBody Country country) {
        return countryDAL.update(country);
    }

    
}

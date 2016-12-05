package com.vsquaresystem.safedeals.hospital;

import com.vsquaresystem.safedeals.branch.Branch;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospital")
public class HospitalRest {

    @Autowired
    private HospitalDAL hospitalDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<Hospital> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return hospitalDal.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Hospital findById(@PathVariable("id") Integer id) {
        return hospitalDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Hospital insert(@RequestBody Hospital hospital) {
        return hospitalDal.insert(hospital);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Hospital findByName(@RequestParam("name") String name) {
        return hospitalDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        hospitalDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Hospital update(@RequestBody Hospital hospital) {
        return hospitalDal.update(hospital);
    }
}

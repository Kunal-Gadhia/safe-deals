package com.vsquaresystem.safedeals.salaryrange;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary_range")
public class SalaryRangeRest {

    @Autowired
    private SalaryRangeDAL salaryRangeDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<SalaryRange> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return salaryRangeDal.findAll(offset);

    }

    @RequestMapping(value = "/find/salary_range_like", method = RequestMethod.GET)
    public List<SalaryRange> findByDescriptionLike(@RequestParam("description") String description) {
        return salaryRangeDal.findByDescriptionLike(description);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SalaryRange findById(@PathVariable("id") Integer id) {
        return salaryRangeDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public SalaryRange insert(@RequestBody SalaryRange salaryRange) {
        return salaryRangeDal.insert(salaryRange);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public SalaryRange update(@RequestBody SalaryRange salaryRange) {
        return salaryRangeDal.update(salaryRange);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        salaryRangeDal.delete(id);
    }
}

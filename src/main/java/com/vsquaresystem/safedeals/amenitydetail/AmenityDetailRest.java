package com.vsquaresystem.safedeals.amenitydetail;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amenitydetail")
public class AmenityDetailRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmenityDetailService amenitydetailservice;

    @Autowired
    private AmenityDetailDAL amenitydetailDal;

    @RequestMapping(method = RequestMethod.GET)
    public List<AmenityDetail> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return amenitydetailDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AmenityDetail findById(@PathVariable("id") Integer id) {
        return amenitydetailDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public AmenityDetail insert(@RequestBody AmenityDetail amenitydetail) {
        return amenitydetailDal.insert(amenitydetail);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<AmenityDetail> findByName(@RequestParam("name") String name) {
        return amenitydetailDal.findByName(name);
    }

    @RequestMapping(value = "/find/amenity_detail_name", method = RequestMethod.GET)
    public AmenityDetail findByAmenityDetailName(@RequestParam("name") String name) {
        return amenitydetailDal.findByAmenityDetailName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        amenitydetailDal.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public AmenityDetail update(@RequestBody AmenityDetail amenitydetail) {
        logger.info("amenitydetail", amenitydetail);
        return amenitydetailDal.update(amenitydetail);
    }

    @RequestMapping(value = "/ad_filter", method = RequestMethod.GET)
    public List<AmenityDetail> findByADFilter(
            @ModelAttribute AmenityDetailFilter amenityDetailFilter) {
        return amenitydetailDal.findByADFilter(amenityDetailFilter);
    }
    
    @RequestMapping(value = "/find/amenity_id/city_id", method = RequestMethod.GET)
    public List<AmenityDetail> findByAmenityIdCityId(@RequestParam("amenityId") Integer amenityId, @RequestParam("cityId") Integer cityId) {
        return amenitydetailDal.findByAmenityIdCityId(amenityId, cityId);
    }


    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Boolean exportExcelData() throws IOException {
        logger.info("exportExcelData EXCEL DATA {}");
        return amenitydetailservice.exportExcel();
    }
}

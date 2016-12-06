/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.enquiry;

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

/**
 *
 * @author hp4
 */
@RestController
@RequestMapping("/enquiry")
public class EnquiryRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnquiryDAL enquiryDAL;

    @Autowired
    private EnquiryService enquiryService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Enquiry> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return enquiryDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Enquiry findById(@PathVariable("id") Integer id) {
        return enquiryDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Enquiry insert(@RequestBody Enquiry enquiry) {
        logger.info("Enquiry REST:{}", enquiry);
        return enquiryDAL.insert(enquiry);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Enquiry findByName(@RequestParam("name") String name) {
        return enquiryDAL.findByName(name);
    }

    @RequestMapping(value = "/send_sms", method = RequestMethod.POST)
    public String sendSms() {
        // logger.info("client no rest ", clientNumber);
        EnquiryService.sendSms();
        return "greeting";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        enquiryDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Enquiry update(@RequestBody Enquiry enquiry) {
        return enquiryDAL.update(enquiry);
    }

}

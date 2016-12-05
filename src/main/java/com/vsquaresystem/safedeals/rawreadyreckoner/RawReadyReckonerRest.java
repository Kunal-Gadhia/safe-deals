package com.vsquaresystem.safedeals.rawreadyreckoner;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.text.ParseException;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rawreadyreckoner")
public class RawReadyReckonerRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RawReadyReckonerDAL rawreadyreckonerDAL;
    
     @Autowired
    private RawReadyReckonerService rawreadyreckonerservice;

    @RequestMapping(method = RequestMethod.GET)
    public List<RawReadyReckoner> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {

        return rawreadyreckonerDAL.findAll(offset);
    }
    @RequestMapping(value = "/raw_ready_reckoners",method = RequestMethod.GET)
    public List<RawReadyReckoner> getAll() {
        return rawreadyreckonerDAL.getAll();
    }
    
    @RequestMapping(value = "/find_by_unique_location",method = RequestMethod.GET)
    public List<RawReadyReckonerDAL.DistinctLocations> findByUniqueLocation() {
        return rawreadyreckonerDAL.findByUniqueLocation();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RawReadyReckoner findById(@PathVariable("id") Integer id) {
        return rawreadyreckonerDAL.findById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public RawReadyReckoner insert(@RequestBody RawReadyReckoner rawreadyReckoner)  throws JsonProcessingException, ParseException  {
        return rawreadyreckonerDAL.insert(rawreadyReckoner);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        rawreadyreckonerDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RawReadyReckoner update(@RequestBody RawReadyReckoner rawreadyReckoner)  throws JsonProcessingException, ParseException {
        return rawreadyreckonerDAL.update(rawreadyReckoner);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public Boolean uploadAttachment(@RequestParam MultipartFile attachment) throws IOException {
        logger.info("attachment in rest line56 {}", attachment);
        return rawreadyreckonerservice.insertAttachments(attachment);
    }   
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Boolean saveExcelData() throws IOException {
        logger.info("SAVE EXCEL DATA {}");
         return rawreadyreckonerservice.checkExistingDataa();
           }   

}

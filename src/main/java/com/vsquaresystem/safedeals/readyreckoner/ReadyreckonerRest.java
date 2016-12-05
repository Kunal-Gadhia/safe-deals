
package com.vsquaresystem.safedeals.readyreckoner;

import java.io.IOException;
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
@RequestMapping("/readyreckoner")
public class ReadyreckonerRest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReadyreckonerDAL readyreckonerDAL;
    
    @Autowired
    private ReadyReckonerService readyreckonerservice;
   
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Readyreckoner> findAll(@RequestParam(value = "offset",required = false, defaultValue = "0") Integer offset){
        
        return readyreckonerDAL.findAll(offset);
        
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    
    public Readyreckoner findById (@PathVariable("id") Integer id) {

        return readyreckonerDAL.findById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Readyreckoner insert(@RequestBody Readyreckoner readyreckoner) {
        return readyreckonerDAL.insert(readyreckoner);
    }
    
//    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
//    public List<Readyreckoner> findByName(@RequestParam("name") String name) {
//        return readyreckonerDAL.findByName(name);
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        readyreckonerDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Readyreckoner update(@RequestBody Readyreckoner readyreckoner) {
        return readyreckonerDAL.update(readyreckoner);
    }
    
    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public Boolean uploadAttachment(@RequestParam MultipartFile attachment) throws IOException {
        logger.info("attachment in rest line56 {}", attachment);
        return readyreckonerservice.insertAttachments(attachment);
    }   
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Boolean saveExcelData() throws IOException {
        logger.info("SAVE EXCEL DATA {}");
         return readyreckonerservice.checkExistingDataa();
           } 
}
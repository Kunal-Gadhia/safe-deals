/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.property;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import static org.apache.poi.hwpf.model.FileInformationBlock.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/property")
public class PropertyRest {

    @Autowired
    private PropertyDAL propertyDAL;

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Property> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return propertyDAL.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Property findById(@PathVariable("id") Integer id) {
        return propertyDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Property insert(@RequestBody Property property) throws JsonProcessingException {
        return propertyDAL.insert(property);
    }

    @RequestMapping(value = "/find/location_id", method = RequestMethod.GET)
    public List<Property> findByLocationId(@RequestParam("locationId") Integer locationId) {
        return propertyDAL.findByLocationId(locationId);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public List<Property> findByName(@RequestParam("name") String name) {
        return propertyDAL.findByName(name);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<Property> findByNameLike(@RequestParam("name") String name) {
        return propertyDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        propertyDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Property update(@RequestBody Property property) throws JsonProcessingException {
        return propertyDAL.update(property);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.POST)
    public Property uploadAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER+++++++++++++++++" + attachment.getName());
        return propertyService.insertAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.GET)
    public void getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File photoFile = propertyService.getPhoto(id);
        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
        response.setContentLengthLong(photoFile.length());
        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
    }
}

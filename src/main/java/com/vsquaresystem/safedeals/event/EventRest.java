/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author hp2
 */
@RestController
@RequestMapping("/event")
public class EventRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private EventDAL eventDal;

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return eventDal.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Event findById(@PathVariable("id") Integer id) {
        return eventDal.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Event findByName(@RequestParam("name") String name) {
        return eventDal.findByName(name);
    }

    @RequestMapping(value = "/find/date", method = RequestMethod.GET)
    public List<Event> findByDate() {
        return eventDal.findByDate();
    }
    
    @RequestMapping(value = "/find/concluded", method = RequestMethod.GET)
    public List<Event> findConcludedEvents() {
        return eventDal.findConcludedEvents();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Event insert(@RequestBody Event event) {
        return eventDal.insert(event);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Event update(@RequestBody Event event) {
        return eventDal.update(event);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        eventDal.delete(id);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.POST)
    public Event uploadAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER+++++++++++++++++" + attachment.getName());
        return eventService.insertAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.GET)
    public void getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File photoFile = eventService.getPhoto(id);
        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
        response.setContentLengthLong(photoFile.length());
        logger.debug("filename: {}, size: {}", photoFile.getAbsoluteFile(), photoFile.length());
        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
    }

}

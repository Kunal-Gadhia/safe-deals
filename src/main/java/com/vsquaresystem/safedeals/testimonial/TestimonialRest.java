/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.testimonial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.amenity.Amenity;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 * @author Ajay
 */
@RestController
@RequestMapping("/testimonial")
public class TestimonialRest {

    @Autowired
    TestimonialDAL testimonialDAL;

    @Autowired
    private UserDAL userDAL;

    @Autowired
    TestimonialService testimonialService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<Testimonial> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return testimonialDAL.findAll(offset);

    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public Testimonial findById(@RequestParam("id") Integer id) {
        return testimonialDAL.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Testimonial findByName(@RequestParam("name") String name) {
        return testimonialDAL.findByName(name);
    }

    @RequestMapping(value = "/find/category", method = RequestMethod.GET)
    public List<Testimonial> findByCategory() {
        return testimonialDAL.findByCategory();
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.POST)
    public Testimonial uploadAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER+++++++++++++++++" + attachment.getName());
        return testimonialService.insertAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.GET)
    public void getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File photoFile = testimonialService.getPhoto(id);
        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
        response.setContentLengthLong(photoFile.length());
        logger.debug("filename: {}, size: {}", photoFile.getAbsoluteFile(), photoFile.length());
        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Testimonial insert(@RequestBody TestimonialConfig testinomialConfig,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        testinomialConfig.setUserId(user.getId());
        return testimonialDAL.insert(testinomialConfig);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        testimonialDAL.delete(id, user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Testimonial update(@RequestBody Testimonial testimonial,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        testimonial.setUserId(user.getId());
        return testimonialDAL.update(testimonial);
    }
}

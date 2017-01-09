/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.image;

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
 * @author hp
 */
@RestController
@RequestMapping("/image")
public class ImageRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ImageDAL imageDal;

    @Autowired
    private ImageService imageService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Image> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return imageDal.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Image findById(@PathVariable("id") Integer id) {
        return imageDal.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Image findByName(@RequestParam("name") String name) {
        return imageDal.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Image insert(@RequestBody Image image) {
        return imageDal.insert(image);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Image update(@RequestBody Image image) {
        return imageDal.update(image);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        imageDal.delete(id);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.POST)
    public Image uploadAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER+++++++++++++++++" + attachment.getName());
        return imageService.insertAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.GET)
    public void getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File photoFile = imageService.getPhoto(id);
        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
        response.setContentLengthLong(photoFile.length());
        logger.debug("filename: {}, size: {}", photoFile.getAbsoluteFile(), photoFile.length());
        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.testimonial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.util.AttachmentUtils;
import com.vsquaresystem.safedeals.util.PhotoUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ajay
 */
@Service
@Transactional
public class TestimonialService {

    public final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AttachmentUtils attachmentUtils;

    @Autowired
    TestimonialDAL testimonialDAL;

    @Autowired
    PhotoUtils photoUtils;

    public Testimonial insertAttachments(Integer testimonialId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Testimonial testimonial = testimonialDAL.findById(testimonialId);
//        if (testimonial.getAttachment().get(0).length()>0) {
//            boolean b = attachmentUtils.deleteAttachmentByAttachmentTypeAndEntityId(
//                    testimonial.getAttachment().get(0).toString(),
//                    AttachmentUtils.AttachmentType.TESTIMONIAL,
//                    testimonial.getId());
//        }
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.TESTIMONIAL,
                testimonial.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        testimonial.setAttachment(attachments);
//
        testimonialDAL.update(testimonial);
        return testimonial;
    }

    public File getPhoto(Integer testimonialId) throws FileNotFoundException, IOException {
        Testimonial testimonial = testimonialDAL.findById(testimonialId);
        return photoUtils.getPhoto(testimonial);
    }

    public File getImage(Testimonial testimonial) throws IOException {
        if (testimonial.getAttachment().size() != 0) {
            String PHOTO_FILE_NAME = testimonial.getAttachment().get(0).toString();
            File photoFile = photoUtils.getPhotoFile(testimonial);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("images/default.png").getFile());
            return photoFiles;
        }
    }
}

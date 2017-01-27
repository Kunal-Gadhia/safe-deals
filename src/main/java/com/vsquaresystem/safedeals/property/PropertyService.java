/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.property;

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
 * @author hp
 */
@Service
@Transactional
public class PropertyService {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AttachmentUtils attachmentUtils;

    @Autowired
    PhotoUtils photoUtils;

    @Autowired
    PropertyDAL propertyDAL;

    public Property insertAttachments(Integer propertyId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Property property = propertyDAL.findById(propertyId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROPERTY,
                property.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUT FILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        property.setOwnershipProof(attachments);
//
        propertyDAL.update(property);
        return property;
    }

    public File getPhoto(Integer propertyId) throws FileNotFoundException, IOException {
        Property property = propertyDAL.findById(propertyId);
        return photoUtils.getPropertyPhoto(property);
    }

    public File getProperty(Property property) throws IOException {
        if (property.getOwnershipProof().size() != 0) {
            String PHOTO_FILE_NAME = property.getOwnershipProof().get(0).toString();
            File photoFile = photoUtils.getPropertyPhoto(property);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("properties/default.png").getFile());
            return photoFiles;
        }
    }
}

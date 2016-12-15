/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.event;

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
public class EventService {
    public final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AttachmentUtils attachmentUtils;    

    @Autowired
    PhotoUtils photoUtils;
    
    @Autowired
    EventDAL eventDAL;

    public Event insertAttachments(Integer eventId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Event event = eventDAL.findById(eventId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.EVENT,
                event.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        event.setPhotoPath(attachments);
//
        eventDAL.update(event);
        return event;
    }

    public File getPhoto(Integer eventId) throws FileNotFoundException, IOException {
        Event event = eventDAL.findById(eventId);
        return photoUtils.getEventPhoto(event);
    }

    public File getImage(Event event) throws IOException {
        if (event.getPhotoPath().size() != 0) {
            String PHOTO_FILE_NAME = event.getPhotoPath().get(0).toString();
            File photoFile = photoUtils.getEventPhotoFile(event);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("images/default.png").getFile());
            return photoFiles;
        }
    }
}

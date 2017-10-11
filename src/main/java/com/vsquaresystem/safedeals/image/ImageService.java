/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.util.AttachmentUtils;
import com.vsquaresystem.safedeals.util.PhotoUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class ImageService {
    
    @Autowired
    AttachmentUtils attachmentUtils;

    @Autowired
    PhotoUtils photoUtils;

    @Autowired
    ImageDAL imageDAL;

    public Image insertAttachments(Integer imageId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Image image = imageDAL.findById(imageId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.IMAGE,
                image.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUT FILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        image.setPhotoPath(attachments);
        imageDAL.update(image);
        return image;
    }

    public File getPhoto(Integer imageId) throws FileNotFoundException, IOException {
        Image image = imageDAL.findById(imageId);
        return photoUtils.getImagePhoto(image);
    }

    public File getImage(Image image) throws IOException {
        if (image.getPhotoPath().size() != 0) {
            String PHOTO_FILE_NAME = image.getPhotoPath().get(0).toString();
            File photoFile = photoUtils.getImagePhotoFile(image);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("images/default.png").getFile());
            return photoFiles;
        }
    }
}

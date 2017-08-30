///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.vsquaresystem.safedeals.util;

import com.vsquaresystem.safedeals.event.Event;
import com.vsquaresystem.safedeals.image.Image;
import com.vsquaresystem.safedeals.project.Project;
import com.vsquaresystem.safedeals.property.Property;
import com.vsquaresystem.safedeals.team.Team;
import com.vsquaresystem.safedeals.testimonial.Testimonial;
import com.vsquaresystem.safedeals.util.AttachmentUtils.AttachmentType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
//
///**
// *
// * @author ruchita
// * Modified by Sudarshan Bhalerao
// */
//

@Service
public class PhotoUtils {
//    
//    private static final String PHOTO_FILE_NAME = "photo";
//
//    @Autowired
//    private AttachmentUtils attachmentUtils;
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    public File getPhoto(Integer videoId) throws FileNotFoundException, IOException {
//        File photoFile = getPhotoFile(videoId);
//        if (!photoFile.exists()) {
//            throw new FileNotFoundException();
//        }
//        return photoFile;
//    }
//
//    public File setPhoto(
//            InputStream inputStream,
//            Integer videoId)
//            throws IOException {
//
//        File photoFile = getPhotoFile(videoId);
//        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));        
//        return photoFile;
//    }
//
//    private File getPhotoFile(Integer videoId) throws IOException{
//        File videoDir = attachmentUtils.getDirectoryByAttachmentTypeAndVideoId(AttachmentType.VIDEO, videoId);
//        logger.info("videoDir for photo", videoDir);
//        return new File(videoDir, PHOTO_FILE_NAME);
//    }
//    

    private String PHOTO_FILE_NAME = "";
    @Autowired
    private AttachmentUtils attachmentUtils;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public File getPhoto(Testimonial testimonial) throws FileNotFoundException, IOException {
        if (testimonial.getAttachment() != null) {
            PHOTO_FILE_NAME = testimonial.getAttachment().get(0).toString();
        }
        File photoFile = getPhotoFile(testimonial);
        return photoFile;
    }

    public File setPhoto(
            InputStream inputStream,
            Testimonial testimonial)
            throws IOException {

        File photoFile = getPhotoFile(testimonial);
        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));
        return photoFile;
    }

    public File getPhotoFile(Testimonial testimonial) throws IOException {
        File testimonialDir = attachmentUtils.getDirectoryByAttachmentTypeAndEntityId(AttachmentType.TESTIMONIAL, testimonial.getId(), true);
        return new File(testimonialDir, PHOTO_FILE_NAME);
    }

    /////////////////////////////////////////////////
    public File getTeamPhoto(Team team) throws FileNotFoundException, IOException {
        if (team.getPhotoPath() != null) {
            PHOTO_FILE_NAME = team.getPhotoPath().get(0).toString();
        }
        File photoFile = getTeamPhotoFile(team);
        return photoFile;
    }

    public File setTeamPhoto(
            InputStream inputStream,
            Team team)
            throws IOException {

        File photoFile = getTeamPhotoFile(team);
        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));
        return photoFile;
    }

    public File getTeamPhotoFile(Team team) throws IOException {
        File teamDir = attachmentUtils.getDirectoryByAttachmentTypeAndEntityId(AttachmentType.TEAM, team.getId(), true);
        return new File(teamDir, PHOTO_FILE_NAME);
    }

    /////////////////////////////////////////////////
    public File getEventPhoto(Event event) throws FileNotFoundException, IOException {
        if (event.getPhotoPath() != null) {
            PHOTO_FILE_NAME = event.getPhotoPath().get(0).toString();
        }
        File photoFile = getEventPhotoFile(event);
        return photoFile;
    }

    public File setEventPhoto(
            InputStream inputStream,
            Event event)
            throws IOException {

        File photoFile = getEventPhotoFile(event);
        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));
        return photoFile;
    }

    public File getEventPhotoFile(Event event) throws IOException {
        File eventDir = attachmentUtils.getDirectoryByAttachmentTypeAndEntityId(AttachmentType.EVENT, event.getId(), true);
        return new File(eventDir, PHOTO_FILE_NAME);
    }
    ///////////////////////////////////////////////

    public File getProjectMutationCopyPhoto(Project project) throws FileNotFoundException, IOException {
        if (project.getMutationCopy()!= null) {
            PHOTO_FILE_NAME = project.getMutationCopy().get(0).toString();
        }
        File photoFile = getProjectMutationCopyPhoto(project);
        return photoFile;
    }

    public File setProjectMutationCopyPhoto(
            InputStream inputStream,
            Project project)
            throws IOException {

        File photoFile = getProjectMutationCopyPhotoFile(project);
        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));
        return photoFile;
    }

    public File getProjectMutationCopyPhotoFile(Project project) throws IOException {
        File projectMutationCopyDir = attachmentUtils.getDirectoryByAttachmentTypeAndEntityId(AttachmentType.PROJECT_MUTATION_COPY, project.getId(), true);
        return new File(projectMutationCopyDir, PHOTO_FILE_NAME);
    }

    ///////////////////////////////////////////////
    public File getImagePhoto(Image image) throws FileNotFoundException, IOException {
        if (image.getPhotoPath() != null) {
            PHOTO_FILE_NAME = image.getPhotoPath().get(0).toString();
        }
        File photoFile = getImagePhotoFile(image);
        return photoFile;
    }

    public File setImagePhoto(
            InputStream inputStream,
            Image image)
            throws IOException {

        File photoFile = getImagePhotoFile(image);
        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));
        return photoFile;
    }

    public File getImagePhotoFile(Image image) throws IOException {
        File imageDir = attachmentUtils.getDirectoryByAttachmentTypeAndEntityId(AttachmentType.IMAGE, image.getId(), true);
        return new File(imageDir, PHOTO_FILE_NAME);
    }

    /////////////////////////////////////////////////
    public File getPropertyPhoto(Property property) throws FileNotFoundException, IOException {
        if (property.getOwnershipProof() != null) {
            PHOTO_FILE_NAME = property.getOwnershipProof().get(0).toString();
        }
        File photoFile = getPropertyPhotoFile(property);
        return photoFile;
    }

    public File setPropertyPhoto(
            InputStream inputStream,
            Property property)
            throws IOException {

        File photoFile = getPropertyPhotoFile(property);
        FileCopyUtils.copy(inputStream, new FileOutputStream(photoFile));
        return photoFile;
    }

    public File getPropertyPhotoFile(Property property) throws IOException {
        File propertyDir = attachmentUtils.getDirectoryByAttachmentTypeAndEntityId(AttachmentType.PROPERTY, property.getId(), true);
        return new File(propertyDir, PHOTO_FILE_NAME);
    }

    ///////////////////////////////////////////////
}

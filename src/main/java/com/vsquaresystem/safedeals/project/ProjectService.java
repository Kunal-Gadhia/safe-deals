/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.project;

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
 * @author hp-pc
 */
@Service
@Transactional
public class ProjectService {

    public final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AttachmentUtils attachmentUtils;

    @Autowired
    PhotoUtils photoUtils;

    @Autowired
    ProjectDAL projectDAL;

    public Project insertMutationCopyAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT_MUTATION_COPY,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setMutationCopy(attachments);
//
        projectDAL.update(project);
        return project;
    }

    public File getMutationCopyPhoto(Integer projectId) throws FileNotFoundException, IOException {
        Project project = projectDAL.findById(projectId);
        return photoUtils.getProjectMutationCopyPhoto(project);
    }

    public File getMutationCopyImage(Project project) throws IOException {
        if (project.getMutationCopy().size() != 0) {
            String PHOTO_FILE_NAME = project.getMutationCopy().get(0).toString();
            File photoFile = photoUtils.getProjectMutationCopyPhoto(project);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("images/default.png").getFile());
            return photoFiles;
        }
    }

    /////sale deed/////
    public Project insertSaleDeedAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT_SALE_DEED,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setSaleDeed(attachments);
//
        projectDAL.update(project);
        return project;
    }

    public File getSaleDeedPhoto(Integer projectId) throws FileNotFoundException, IOException {
        Project project = projectDAL.findById(projectId);
        return photoUtils.getProjectSaleDeedPhoto(project);
    }

    public File getSaleDeedImage(Project project) throws IOException {
        if (project.getSaleDeed().size() != 0) {
            String PHOTO_FILE_NAME = project.getSaleDeed().get(0).toString();
            File photoFile = photoUtils.getProjectSaleDeedPhoto(project);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("images/default.png").getFile());
            return photoFiles;
        }
    }
}

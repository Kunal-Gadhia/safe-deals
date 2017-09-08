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
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.MUTATION_COPY,
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
    public Project insertSaledeedAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.SALE_DEED,
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

    ///////////Development Agreement///////////////////    
    public Project insertDevelopmentAgreementAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.DEVELOPMENT_AGREEMENT,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setDevelopmentAgreement(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Power Of Authority///////////////////    
    public Project insertPowerOfAuthorityAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.POWER_OF_AUTHORITY,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setPowerOfAuthority(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Development Agreement///////////////////    
    public Project insertTaxReceiptAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.TAX_RECEIPT,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setTaxReceipt(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Layout Sanction///////////////////    
    public Project insertLayoutSanctionAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.LAYOUT_SANCTION,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setLayoutSanction(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Development Plan///////////////////    
    public Project insertDevelopmentPlanAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.DEVELOPMENT_PLAN,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setDevelopmentPlan(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Release Letter///////////////////    
    public Project insertReleaseLetterAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.RELEASE_LETTER,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setReleaseLetter(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Building Sanction///////////////////    
    public Project insertBuildingSanctionAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.BUILDING_SANCTION,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setBuildingSanction(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Completion Certificate///////////////////    
    public Project insertCompletionCertificateAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.COMPLETION_CERTIFICATE,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setCompletionCertificate(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Occupancy Certificate///////////////////    
    public Project insertOccupancyCertificateAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.OCCUPANCY_CERTIFICATE,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setOccupancyCertificate(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Bird Eye View///////////////////    
    public Project insertBirdEyeViewAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.BIRD_EYE_VIEW,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setBirdEyeView(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Elevation///////////////////    
    public Project insertElevationAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.ELEVATION,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setElevation(attachments);
//
        projectDAL.update(project);
        return project;
    }

    ///////////Floor Plan///////////////////
    public Project insertFloorPlanAttachments(Integer projectId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Project project = projectDAL.findById(projectId);
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.PROJECT,
                AttachmentUtils.DocumentType.FLOOR_PLAN,
                project.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        project.setFloorPlans(attachments);
//
        projectDAL.update(project);
        return project;
    }

}

package com.vsquaresystem.safedeals.project;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@RestController
@RequestMapping("/project")
public class ProjectRest {

    @Autowired
    private ProjectDAL projectDAL;

    @Autowired
    private ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET)
    public List<Project> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return projectDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project findById(@PathVariable("id") Integer id) {
        return projectDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Project insert(@RequestBody Project project) throws JsonProcessingException {
        return projectDAL.insert(project);
    }

    @RequestMapping(value = "/find/location_id", method = RequestMethod.GET)
    public List<Project> findByLocationId(@RequestParam("locationId") Integer locationId) {
        return projectDAL.findByLocationId(locationId);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<Project> findByNameLike(@RequestParam("name") String name) {
        return projectDAL.findByNameLike(name);
    }

//    @RequestMapping(value = "/find/project_cost", method = RequestMethod.GET)
//    public List<Project> findByProjectCost(@RequestParam("projectCost") Double projectCost) {
//        return projectDAL.findByProjectCost(projectCost);
//    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        projectDAL.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Project update(@RequestBody Project project) throws JsonProcessingException {
        return projectDAL.update(project);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.POST)
    public Project uploadAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER+++++++++++++++++" + attachment.getName());
        return projectService.insertMutationCopyAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/sale_deed/attachment", method = RequestMethod.POST)
    public Project uploadSaleDeedAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertSaledeedAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/development_agreement/attachment", method = RequestMethod.POST)
    public Project uploadDevelopmentAgreementAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertDevelopmentAgreementAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/power_of_authority/attachment", method = RequestMethod.POST)
    public Project uploadPowerOfAuthorityAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertPowerOfAuthorityAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/tax_receipt/attachment", method = RequestMethod.POST)
    public Project uploadTaxReceiptAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertTaxReceiptAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/layout_sanction/attachment", method = RequestMethod.POST)
    public Project uploadLayoutSanctionAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertLayoutSanctionAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/development_plan/attachment", method = RequestMethod.POST)
    public Project uploadDevelopmentPlanAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertDevelopmentPlanAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/release_letter/attachment", method = RequestMethod.POST)
    public Project uploadReleaseLetterAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertReleaseLetterAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/building_sanction/attachment", method = RequestMethod.POST)
    public Project uploadBuildingSanctionAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertBuildingSanctionAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/completion_certificate/attachment", method = RequestMethod.POST)
    public Project uploadCompletionCertificateAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertCompletionCertificateAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/occupancy_certificate/attachment", method = RequestMethod.POST)
    public Project uploadOccupancyCertificateAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertOccupancyCertificateAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/bird_eye_view/attachment", method = RequestMethod.POST)
    public Project uploadBirdEyeViewAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertBirdEyeViewAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/elevation/attachment", method = RequestMethod.POST)
    public Project uploadElevationAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertElevationAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/floor_plan/attachment", method = RequestMethod.POST)
    public Project uploadFloorPlanAttachment(
            @PathVariable Integer id,
            @RequestParam MultipartFile attachment
    ) throws IOException {
        System.out.println("MULTIPART ATTACHMENT LOGGER in Sale Deed+++++++++++++++++" + attachment.getName());
        return projectService.insertFloorPlanAttachments(id, attachment);
    }

    @RequestMapping(value = "/{id}/attachment", method = RequestMethod.GET)
    public void getAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File photoFile = projectService.getMutationCopyPhoto(id);
        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
        response.setContentLengthLong(photoFile.length());
        logger.debug("filename: {}, size: {}", photoFile.getAbsoluteFile(), photoFile.length());
        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
    }

//    //// Sale Deed
//    @RequestMapping(value = "/{id}/sale/attachment", method = RequestMethod.POST)
//    public Project uploadSaleDeedAttachment(
//            @PathVariable Integer id,
//            @RequestParam MultipartFile attachment
//    ) throws IOException {
//        System.out.println("MULTIPART ATTACHMENT LOGGER+++++++++++++++++" + attachment.getName());
//        return projectService.insertSaleDeedAttachments(id, attachment);
//    }
//
//    @RequestMapping(value = "/{id}/sale/attachment", method = RequestMethod.GET)
//    public void getSaleDeedAttachment(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        File photoFile = projectService.getSaleDeedPhoto(id);
//        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
//        response.setContentLengthLong(photoFile.length());
//        logger.debug("filename: {}, size: {}", photoFile.getAbsoluteFile(), photoFile.length());
//        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
//    }
}

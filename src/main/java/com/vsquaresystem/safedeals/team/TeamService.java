/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.team;

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
public class TeamService {

    public final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AttachmentUtils attachmentUtils;

    @Autowired
    TeamDAL teamDAL;

    @Autowired
    PhotoUtils photoUtils;

    public Team insertAttachments(Integer teamId, MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Team team = teamDAL.findById(teamId);
//        if (team.getAttachment().get(0).length()>0) {
//            boolean b = attachmentUtils.deleteAttachmentByAttachmentTypeAndEntityId(
//                    team.getAttachment().get(0).toString(),
//                    AttachmentUtils.AttachmentType.TESTIMONIAL,
//                    team.getId());
//        }
        Boolean isView = false;
        File outputFile = attachmentUtils.storeAttachmentByAttachmentTypeAndEntityId(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.TEAM,
                team.getId(),
                isView
        );
        System.out.println("THIS IS OUTPUTFILE==================" + outputFile.toString());
        List<String> attachments = new ArrayList<>();
        attachments.add(outputFile.getName().toString());
        team.setPhotoPath(attachments);
//
        teamDAL.update(team);
        return team;
    }

    public File getPhoto(Integer teamId) throws FileNotFoundException, IOException {
        Team team = teamDAL.findById(teamId);
        return photoUtils.getTeamPhoto(team);
    }

    public File getImage(Team team) throws IOException {
        if (team.getPhotoPath().size() != 0) {
            String PHOTO_FILE_NAME = team.getPhotoPath().get(0).toString();
            File photoFile = photoUtils.getTeamPhotoFile(team);
            return photoFile;
        } else {
            File photoFiles = new File(getClass().getResource("images/default.png").getFile());
            return photoFiles;
        }
    }
}

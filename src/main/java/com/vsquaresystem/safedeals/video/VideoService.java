///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vsquaresystem.safedeals.video;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.vsquaresystem.safedeals.util.PhotoUtils;
//import com.vsquaresystem.safedeals.util.VideoUtils;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// *
// * @author ruchita
// */
//@Service
//@Transactional
//public class VideoService {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//    
//    private final File defaultPhoto = new File(getClass().getResource("/images/4.jpg").getFile());
//    private final File defaultVideo = new File(getClass().getResource("/images/1.jpg").getFile());
//
//    @Autowired
//    private VideoDAL videoDAL;
//
////    @Autowired
////    private AttachmentUtils attachmentUtils;
//
//    @Autowired
//    private PhotoUtils photoUtils;
//    
//    @Autowired
//    private VideoUtils videoUtils;
//
//    @Transactional(readOnly = false)
//    
//    public Video setPhoto(Integer videoId, MultipartFile photoMultipartFile) throws JsonProcessingException, IOException {
//        Video video = videoDAL.findById(videoId);
//        File photoFile = photoUtils.setPhoto(photoMultipartFile.getInputStream(), videoId);
//        video.setImgUrl(photoFile.getName());
//        return videoDAL.update(video);
//    }
//
//    public File getPhoto(Integer videoId) throws FileNotFoundException, IOException {
//        try {
//            return photoUtils.getPhoto(videoId);
//        } catch (FileNotFoundException fnfe) {
//            //if photo not set or found, then return the default photo for user
//            return defaultPhoto;
//        }
//    }
//    
//    public Video setVideo(Integer videoId, MultipartFile photoMultipartFile) throws JsonProcessingException, IOException {
//        Video video = videoDAL.findById(videoId);
//        File videoFile = videoUtils.setVideo(photoMultipartFile.getInputStream(), videoId);
//        logger.info("videoFile line no  67{}", videoFile);
//         video.setUrl(videoFile.getName());
//        return videoDAL.update(video);
//    }
//    
//    public File getVideo(Integer videoId) throws FileNotFoundException, IOException {
//        try {
//            return videoUtils.getVideo(videoId);
//        } catch (FileNotFoundException fnfe) {
//            //if photo not set or found, then return the default photo for user
//            return defaultVideo;
//        }
//    }
//
//    public Video disable(Integer videoId) throws JsonProcessingException {
//        Video video = videoDAL.findById(videoId);
//        video.setIsDefault(true);
//        return videoDAL.update(video);
//    }
//
//    public Video enable(Integer videoId) throws JsonProcessingException {
//        Video video = videoDAL.findById(videoId);
//        video.setIsDefault(false);
//        return videoDAL.update(video);
//    }
//    
//}

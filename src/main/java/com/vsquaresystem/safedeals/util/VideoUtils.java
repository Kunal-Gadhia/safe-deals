///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vsquaresystem.safedeals.util;
//
//import com.vsquaresystem.safedeals.util.AttachmentUtils.AttachmentType;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.FileCopyUtils;
//
///**
// *
// * @author lenovo-user
// */
//@Service
//public class VideoUtils {
//    private static final String VIDEO_FILE_NAME = "video";
//
//    @Autowired
//    private AttachmentUtils attachmentUtils;
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//    
//    
//    public File getVideo(Integer videoId) throws FileNotFoundException, IOException {
//        File videoFile = getVideoFile(videoId);
//        if (!videoFile.exists()) {
//            throw new FileNotFoundException();
//        }
//        return videoFile;
//    }
//    
//    public File setVideo(
//            InputStream inputStream,
//            Integer videoId)
//            throws IOException{
//        File videoFile = getVideoFile(videoId);
//        FileCopyUtils.copy(inputStream, new FileOutputStream(videoFile));        
//        return videoFile; 
//    }
//    private File getVideoFile(Integer videoId) throws IOException{
//        File videoDir = attachmentUtils.getDirectoryByAttachmentTypeAndVideoId(AttachmentType.VIDEO, videoId);
//        logger.info("videoDir", videoDir);
//        return new File(videoDir, VIDEO_FILE_NAME);
//    }
//}

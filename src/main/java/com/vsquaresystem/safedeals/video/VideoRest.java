package com.vsquaresystem.safedeals.video;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VideoDAL videoDal;

    @Autowired
    private UserDAL userDAL;

//    @Autowired
//    private VideoService videoService;
    @RequestMapping(method = RequestMethod.GET)
    public List<Video> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        logger.info("video object for find all {}", videoDal.findAll(offset));
        return videoDal.findAll(offset);

    }

    @RequestMapping(value = "/videos", method = RequestMethod.GET)
    public List<Video> findAllVideos() {
        return videoDal.findAllVideos();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Video findById(@PathVariable("id") Integer id) {
        return videoDal.findById(id);
    }

    @RequestMapping(value = "/find/projectId", method = RequestMethod.GET)
    public List<Video> findByProjectId(@RequestParam("projectId") Integer projectId) {
        return videoDal.findByProjectId(projectId);
    }

    @RequestMapping(value = "/find/propertyId", method = RequestMethod.GET)
    public List<Video> findByPropertyId(@RequestParam("propertyId") Integer propertyId) {
        return videoDal.findByPropertyId(propertyId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Video insert(@RequestBody Video video,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        video.setUserId(user.getId());
        logger.info("video object {}", video);
        return videoDal.insert(video);
    }

    @RequestMapping(value = "/find_intro_video", method = RequestMethod.GET)
    public Video findIntroVideo() {
        return videoDal.findIntroVideo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        videoDal.delete(id, user.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Video update(@RequestBody Video video,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        video.setUserId(user.getId());
        System.out.println("are we in rest");
        return videoDal.update(video);
    }

    @RequestMapping(value = "/find/video_name", method = RequestMethod.GET)
    public Video findByVideoName(@RequestParam("name") String name) {
        return videoDal.findByVideoName(name);
    }

//    @RequestMapping(value = "/{id}/setPhoto", method = RequestMethod.POST)
//    public void setPhoto(@PathVariable Integer id, @RequestParam MultipartFile photo) throws IOException {
//        System.out.println("are we in set photo");
//        videoService.setPhoto(id, photo);
//    }
//
//    @RequestMapping(value = "/{id}/photo", method = RequestMethod.GET)
//    public void getPhoto(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        File photoFile = videoService.getPhoto(id);
//        response.setContentType(Files.probeContentType(Paths.get(photoFile.getAbsolutePath())));
//        response.setContentLengthLong(photoFile.length());
//        logger.debug("filename: {}, size: {}", photoFile.getAbsoluteFile(), photoFile.length());
//        FileCopyUtils.copy(new FileInputStream(photoFile), response.getOutputStream());
//    }
//    
//    @RequestMapping(value = "/{id}/setVideo", method = RequestMethod.POST)
//    public void setVideo(@PathVariable Integer id, @RequestParam MultipartFile video) throws IOException {
//        System.out.println("are we in set video");
//        videoService.setVideo(id, video);
//    }
//    
//    
//    @RequestMapping(value = "/{id}/video", method = RequestMethod.GET)
//    public void getVideo(@PathVariable Integer id, HttpServletResponse response) throws IOException {
//        File videoFile = videoService.getVideo(id);
//        response.setContentType(Files.probeContentType(Paths.get(videoFile.getAbsolutePath())));
//        response.setContentLengthLong(videoFile.length());
//        logger.debug("filename: {}, size: {}", videoFile.getAbsoluteFile(), videoFile.length());
//        FileCopyUtils.copy(new FileInputStream(videoFile), response.getOutputStream());
//    }
//
//
//    @RequestMapping(value = "/{id}/disable", method = RequestMethod.POST)
//    public Video disable(@PathVariable("id") Integer id) throws JsonProcessingException {
//        return videoService.disable(id);
//    }
//
//    @RequestMapping(value = "/{id}/enable", method = RequestMethod.POST)
//    public Video enable(@PathVariable("id") Integer id) throws JsonProcessingException {
//        return videoService.enable(id);
//    }
}

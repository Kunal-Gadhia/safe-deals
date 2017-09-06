package com.vsquaresystem.safedeals.readyreckoner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/readyreckoner")
public class ReadyreckonerRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReadyreckonerDAL readyreckonerDAL;

    @Autowired
    private ReadyReckonerService readyreckonerservice;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<Readyreckoner> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {

        return readyreckonerDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public Readyreckoner findById(@PathVariable("id") Integer id) {

        return readyreckonerDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Readyreckoner insert(@RequestBody Readyreckoner readyreckoner,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        readyreckoner.setUserId(user.getId());
        return readyreckonerDAL.insert(readyreckoner);
    }

//    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
//    public List<Readyreckoner> findByName(@RequestParam("name") String name) {
//        return readyreckonerDAL.findByName(name);
//    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        readyreckonerDAL.delete(id, user.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Readyreckoner update(@RequestBody Readyreckoner readyreckoner,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        readyreckoner.setUserId(user.getId());
        return readyreckonerDAL.update(readyreckoner);
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public Boolean uploadAttachment(@RequestParam MultipartFile attachment) throws IOException {
        logger.info("attachment in rest line56 {}", attachment);
        return readyreckonerservice.insertAttachments(attachment);
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Boolean saveExcelData() throws IOException {
        logger.info("SAVE EXCEL DATA {}");
        return readyreckonerservice.checkExistingDataa();
    }
}

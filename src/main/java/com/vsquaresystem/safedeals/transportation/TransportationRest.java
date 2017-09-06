package com.vsquaresystem.safedeals.transportation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.user.User;
import com.vsquaresystem.safedeals.user.UserDAL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transportation")
public class TransportationRest {

    @Autowired
    private TransportationDAL transportationDAL;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<Transportation> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return transportationDAL.findAll(offset);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Transportation insert(@RequestBody Transportation transportation,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        transportation.setUserId(user.getId());
        return transportationDAL.insert(transportation);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Transportation findById(@PathVariable("id") Integer id) {
        return transportationDAL.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Transportation findByName(@RequestParam("name") String name) {
        return transportationDAL.findByName(name);
    }

    @RequestMapping(value = "/find/name_like", method = RequestMethod.GET)
    public List<Transportation> findByNameLike(@RequestParam("name") String name) {
        return transportationDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Transportation update(@RequestBody Transportation transportation,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        transportation.setUserId(user.getId());
        return transportationDAL.update(transportation);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        transportationDAL.delete(id, user.getId());
    }

}

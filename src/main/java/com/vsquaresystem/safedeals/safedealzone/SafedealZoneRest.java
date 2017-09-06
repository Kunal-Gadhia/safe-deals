package com.vsquaresystem.safedeals.safedealzone;

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
@RequestMapping("/safedeal_zone")
public class SafedealZoneRest {

    @Autowired
    private SafedealZoneDAL safedealZoneDal;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<SafedealZone> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return safedealZoneDal.findAll(offset);
    }

    @RequestMapping(value = "/safedealzones", method = RequestMethod.GET)
    public List<SafedealZone> findAllSafedealZones() {
        return safedealZoneDal.findAllSafedealZones();
    }

    @RequestMapping(value = "/find/safedealzones_like", method = RequestMethod.GET)
    public List<SafedealZone> findByNameLike(@RequestParam("name") String name) {
        return safedealZoneDal.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SafedealZone findById(@PathVariable("id") Integer id) {
        return safedealZoneDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public SafedealZone insert(@RequestBody SafedealZone safedealZone,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        safedealZone.setUserId(user.getId());
        return safedealZoneDal.insert(safedealZone);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public SafedealZone findByName(@RequestParam("name") String name) {
        return safedealZoneDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public SafedealZone update(@RequestBody SafedealZone safedealZone,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        safedealZone.setUserId(user.getId());
        return safedealZoneDal.update(safedealZone);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        safedealZoneDal.delete(id, user.getId());

    }
}

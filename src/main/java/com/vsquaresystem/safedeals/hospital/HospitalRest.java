package com.vsquaresystem.safedeals.hospital;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.branch.Branch;
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
@RequestMapping("/hospital")
public class HospitalRest {

    @Autowired
    private HospitalDAL hospitalDal;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<Hospital> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return hospitalDal.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Hospital findById(@PathVariable("id") Integer id) {
        return hospitalDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Hospital insert(@RequestBody Hospital hospital,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        hospital.setUserId(user.getId());
        return hospitalDal.insert(hospital);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Hospital findByName(@RequestParam("name") String name) {
        return hospitalDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        hospitalDal.delete(id, user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Hospital update(@RequestBody Hospital hospital,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        hospital.setUserId(user.getId());
        return hospitalDal.update(hospital);
    }
}

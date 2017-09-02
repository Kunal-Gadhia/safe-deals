package com.vsquaresystem.safedeals.businessassociate;

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
@RequestMapping("/business_associate")
public class BusinessAssociateRest {

    @Autowired
    private BusinessAssociateDAL businessAssociateDal;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<BusinessAssociate> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return businessAssociateDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BusinessAssociate findById(@PathVariable("id") Integer id) {
        return businessAssociateDal.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public BusinessAssociate findByName(@RequestParam("name") String name) {
        return businessAssociateDal.findByName(name);
    }

    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    public List<BusinessAssociate> findAllCities() {
        return businessAssociateDal.findAllCities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public BusinessAssociate insert(@RequestBody BusinessAssociate businessAssociate,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        businessAssociate.setUserId(user.getId());
        return businessAssociateDal.insert(businessAssociate);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public BusinessAssociate update(@RequestBody BusinessAssociate businessAssociate,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        businessAssociate.setUserId(user.getId());
        return businessAssociateDal.update(businessAssociate);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        businessAssociateDal.delete(id, user.getId());
    }
}

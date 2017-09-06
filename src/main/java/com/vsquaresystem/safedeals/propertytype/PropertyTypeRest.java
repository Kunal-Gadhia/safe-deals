package com.vsquaresystem.safedeals.propertytype;

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
@RequestMapping("/property_type")
public class PropertyTypeRest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PropertyTypeDAL propertyTypeDal;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<PropertyType> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return propertyTypeDal.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PropertyType findById(@PathVariable("id") Integer id) {
        return propertyTypeDal.findById(id);
    }

    @RequestMapping(value = "/find/all_entries", method = RequestMethod.GET)
    public List<PropertyType> findByNumberOfBhk() {
        return propertyTypeDal.findAllEntries();
    }

    @RequestMapping(value = "/find/number_of_bhk", method = RequestMethod.GET)
    public PropertyType findByNumberOfBhk(@RequestParam("numberOfbhk") Integer numberOfBhk) {
        logger.info("are we here");
        return propertyTypeDal.findByNumberOfBhk(numberOfBhk);
    }

    @RequestMapping(value = "/find/number_of_bhk_like", method = RequestMethod.GET)
    public List<PropertyType> findByNumberOfBhkLike(@RequestParam("numberOfBhkLike") String numberOfBhkLike) {
        return propertyTypeDal.findByNumberOfBhkLike(numberOfBhkLike);
    }

    @RequestMapping(method = RequestMethod.POST)
    public PropertyType insert(@RequestBody PropertyType propertyType,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        propertyType.setUserId(user.getId());
        return propertyTypeDal.insert(propertyType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public PropertyType update(@RequestBody PropertyType propertyType,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        propertyType.setUserId(user.getId());
        return propertyTypeDal.update(propertyType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        propertyTypeDal.delete(id, user.getId());

    }
}

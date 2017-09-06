/*
 * To change this license header, choose License Headers in Property Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.propertycategory;

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

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/property_category")
public class PropertyCategoryRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertyCategoryDAL propertyCategoryDAL;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<PropertyCategory> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return propertyCategoryDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PropertyCategory findById(@PathVariable("id") Integer id) {
        return propertyCategoryDAL.findById(id);
    }

    @RequestMapping(value = "/find/property_category_like", method = RequestMethod.GET)
    public List<PropertyCategory> findByPropertyCategoryLike(@RequestParam("category") String category) {
        return propertyCategoryDAL.findByPropertyCategoryLike(category);
    }

    @RequestMapping(method = RequestMethod.POST)
    public PropertyCategory insert(@RequestBody PropertyCategory propertyCategory,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        propertyCategory.setUserId(user.getId());
        logger.info("Property category Object REST :{}", propertyCategory);
        return propertyCategoryDAL.insert(propertyCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        propertyCategoryDAL.delete(id, user.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public PropertyCategory update(@RequestBody PropertyCategory propertyCategory,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        propertyCategory.setUserId(user.getId());
        return propertyCategoryDAL.update(propertyCategory);
    }

}

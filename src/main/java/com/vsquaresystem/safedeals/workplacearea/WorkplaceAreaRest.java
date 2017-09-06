package com.vsquaresystem.safedeals.workplacearea;

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
@RequestMapping("/workplace_area")
public class WorkplaceAreaRest {

    @Autowired
    private WorkplaceAreaDAL workplaceAreaDAL;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<WorkplaceArea> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return workplaceAreaDAL.findAll(offset);
    }

    @RequestMapping(value = "/find/workplace_area_like", method = RequestMethod.GET)
    public List<WorkplaceArea> findByNameLike(@RequestParam("name") String name) {
        return workplaceAreaDAL.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WorkplaceArea findById(@PathVariable("id") Integer id) {
        return workplaceAreaDAL.findById(id);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public WorkplaceArea findByName(@RequestParam("name") String name) {
        return workplaceAreaDAL.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public WorkplaceArea insert(@RequestBody WorkplaceArea workplaceArea,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        workplaceArea.setUserId(user.getId());
        return workplaceAreaDAL.insert(workplaceArea);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public WorkplaceArea update(@RequestBody WorkplaceArea WorkplaceArea,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        WorkplaceArea.setUserId(user.getId());
        return workplaceAreaDAL.update(WorkplaceArea);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        workplaceAreaDAL.delete(id, user.getId());
    }
}

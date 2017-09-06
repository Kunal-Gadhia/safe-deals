package com.vsquaresystem.safedeals.workplacecategorydetail;

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
@RequestMapping("/workplace_category_detail")
public class WorkplaceCategoryDetailRest {

    @Autowired
    private WorkplaceCategoryDetailDAL workplaceCategoryDetailDal;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<WorkplaceCategoryDetail> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return workplaceCategoryDetailDal.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WorkplaceCategoryDetail findById(@PathVariable("id") Integer id) {
        return workplaceCategoryDetailDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public WorkplaceCategoryDetail insert(@RequestBody WorkplaceCategoryDetail workplaceCategoryDetail,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        workplaceCategoryDetail.setUserId(user.getId());
        return workplaceCategoryDetailDal.insert(workplaceCategoryDetail);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public WorkplaceCategoryDetail update(@RequestBody WorkplaceCategoryDetail workplaceCategoryDetail,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        workplaceCategoryDetail.setUserId(user.getId());
        return workplaceCategoryDetailDal.update(workplaceCategoryDetail);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        workplaceCategoryDetailDal.delete(id, user.getId());
    }
}

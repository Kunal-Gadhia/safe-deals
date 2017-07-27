package com.vsquaresystem.safedeals.user;

import java.sql.SQLException;
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
@RequestMapping("/user")
public class UserRest {

//    @Autowired
//    private UserService userService;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public org.springframework.security.core.userdetails.User getPrincipal(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return user;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) throws SQLException {
        return userDAL.findAll(offset);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Integer id) throws SQLException {

        return userDAL.findById(id);

    }
    
    @RequestMapping(method = RequestMethod.POST)
    public User insert(@RequestBody User user) {
        return userDAL.insert(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public User update(@RequestBody User user) {
        return userDAL.update(user);
    }

//    @RolesAllowed("ROLE_SUPER_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) throws Exception {
        userDAL.delete(id);
    }

    @RequestMapping(value = "/find/username", method = RequestMethod.GET)

    public User findByUsername(@RequestParam("username") String username) throws Exception {

        return userDAL.findByUsername(username);

    }
    
    @RequestMapping(value = "/find/user_like", method = RequestMethod.GET)
    public List<User> findByNameLike(@RequestParam("username") String username) {
        return userDAL.findByNameLike(username);
    }
    
    @RequestMapping(value = "/find/unapproved_user", method = RequestMethod.GET)
    public List<User> findUnapprovedUser() {
        return userDAL.findUnapprovedUser();
    }
    
    @RequestMapping(value = "/count/unapproved_user", method = RequestMethod.GET)
    public Integer countUnapprovedUser() {
        return userDAL.countUnapprovedUser();
    }

}

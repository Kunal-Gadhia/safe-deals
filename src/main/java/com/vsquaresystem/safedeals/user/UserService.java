/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp-pc
 */
@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserDAL userDAL;
    
    @Autowired
    private RoleHierarchy roleHierarchy;
    
    private List<User> users;
    
    public List<User> findByRole(UserType userType) {
        List<User> found = new ArrayList<>();
        
        for(User u : users) {
            if(u.getUserType().equals(userType)) {
                found.add(u);
            }
        }
        return found;
    }
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        try {
            User user = userDAL.findByUsername(string);

            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getUserType().name());

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(sga);

            UserDetails ud = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),                    
                    roleHierarchy.getReachableGrantedAuthorities(authorities));
            return ud;
        } catch (EmptyResultDataAccessException se) {
            se.printStackTrace();
            throw new UsernameNotFoundException("SQL Error", se);
        }
    }
}

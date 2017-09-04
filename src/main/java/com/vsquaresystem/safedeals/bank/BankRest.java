/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.bank;

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
@RequestMapping("/bank")
public class BankRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BankDAL bankDal;

    @Autowired
    private UserDAL userDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bank> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return bankDal.findAll(offset);
    }

    @RequestMapping(value = "/banks", method = RequestMethod.GET)
    public List<Bank> findAllBanks() {
        return bankDal.findAllBanks();
    }

    @RequestMapping(value = "/find/bank_like", method = RequestMethod.GET)
    public List<Bank> findByNameLike(@RequestParam("name") String name) {
        return bankDal.findByNameLike(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bank findById(@PathVariable("id") Integer id) {
        return bankDal.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Bank insert(@RequestBody Bank bank,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        bank.setUserId(user.getId());
        return bankDal.insert(bank);
    }

    @RequestMapping(value = "/find/name", method = RequestMethod.GET)
    public Bank findByName(@RequestParam("name") String name) {
        logger.info("Name in Brach :" + name);
        return bankDal.findByName(name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        bankDal.delete(id, user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Bank update(@RequestBody Bank bank,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) throws JsonProcessingException {
        User user = userDAL.findByUsername(currentUser.getUsername());
        bank.setUserId(user.getId());
        return bankDal.update(bank);
    }
}

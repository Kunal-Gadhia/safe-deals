/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.bankdetails;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/bank_details")
public class BankDetailsRest {

    @Autowired
    private BankDetailsDAL bankDetailsDAL;

    @RequestMapping(method = RequestMethod.GET)
    public List<BankDetails> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset) {
        return bankDetailsDAL.findAll(offset);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BankDetails findById(@PathVariable("id") Integer id) {
        return bankDetailsDAL.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public BankDetails insert(@RequestBody BankDetails bankDetails) {
        return bankDetailsDAL.insert(bankDetails);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public BankDetails update(@RequestBody BankDetails bankDetails) {
        return bankDetailsDAL.update(bankDetails);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) {
        bankDetailsDAL.delete(id);
    }
}

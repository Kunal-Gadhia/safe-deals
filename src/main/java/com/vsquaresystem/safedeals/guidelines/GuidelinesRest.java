/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.guidelines;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp-pc
 */
@RestController
@RequestMapping("/guidelines")
public class GuidelinesRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GuidelinesService guidelinesservice;
    

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public Boolean BudgetReport(@RequestParam("name") String name,
                            @RequestParam("email") String email,
                            @RequestParam("cashInHand") String cashInHand,
                            @RequestParam("loanEligibility") String loanEligibility,
                            @RequestParam("grossBudget") String grossBudget,
                            @RequestParam("emiEligibility") String emiEligibility,
                            @RequestParam("eligiblePropertyValue") String eligiblePropertyValue)  throws AddressException, MessagingException {
        System.out.println("getGuidelinesReport REST line 40 {}SOP NAME" + name);
        System.out.println("getGuidelinesReport REST line 41 {}SOP EMAIL" + email);
        System.out.println("getGuidelinesReport REST line 42 {}SOP cash" + cashInHand);
        System.out.println("getGuidelinesReport REST line 43 {}SOP loanEligibility" + loanEligibility);
        System.out.println("getGuidelinesReport REST line 44 {}SOP grossBudget" + grossBudget);
        System.out.println("getGuidelinesReport REST line 45 {}SOP emiEligibility" + emiEligibility);
        System.out.println("getGuidelinesReport REST line 46 {}SOP eligiblePropertyValue" + eligiblePropertyValue);
       return guidelinesservice.sendBudgetReportEmail(name, email, cashInHand, loanEligibility, grossBudget, emiEligibility, eligiblePropertyValue);
    }
}

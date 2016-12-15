/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp2
 */
@RestController
@RequestMapping("/mail_service")
public class MailRest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    MailService mailService;
    
    @RequestMapping(value = "/send_mail", method = RequestMethod.POST)
    public String sendMail(String mailId) {
        // logger.info("client no rest ", clientNumber);
        mailService.sendMail(mailId);        
        return "greeting";
    }
        
}

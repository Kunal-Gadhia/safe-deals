/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ruchita
 */
@Configuration
public class GeneralConfiguration {
    
    @Bean
    public DateFormat ProjectDetailDateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }
}

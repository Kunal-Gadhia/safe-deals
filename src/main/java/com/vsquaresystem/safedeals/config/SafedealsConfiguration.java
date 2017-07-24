package com.vsquaresystem.safedeals.config;

import com.vsquaresystem.safedeals.user.UserType;
import java.io.IOException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;


/**
 *
 * @author ruchita
 */
@Configuration
@ComponentScan(basePackages = "com.vsquaresystem.safedeals")
public class SafedealsConfiguration {
   @Bean
    public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();
        ppc.setLocations(
                resourceLoader.getResource(System.getProperty("SAFEDEALS_CONFIGURATION_FILE")));
        return ppc;
    }
    
    @Bean
    public static RoleHierarchy roleHierarchy() {

        String roleHierarchyStringRepresentation
                = UserType.UT_SUPER_ADMIN + " > " + UserType.UT_BUILDER + "\n"
                + UserType.UT_BUILDER + " = " + UserType.UT_BANK + "\n"
                + UserType.UT_BANK + " = " + UserType.UT_BUSINESS_ASSOCIATE + "\n"
                + UserType.UT_BUSINESS_ASSOCIATE + " = " + UserType.UT_FRANCHISE;

        //logger.info("Registered Role Hierarchy: \n{}", roleHierarchyStringRepresentation);
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(roleHierarchyStringRepresentation);
        return roleHierarchy;
    }
}

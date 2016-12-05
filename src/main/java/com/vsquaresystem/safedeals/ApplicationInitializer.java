package com.vsquaresystem.safedeals;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext sc) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.getEnvironment().setActiveProfiles("container");
		ctx.setConfigLocation("com.vsquaresystem.safedeals.config");
		sc.addListener(new ContextLoaderListener(ctx));
		ServletRegistration.Dynamic dispatcher = sc.addServlet("spring", new DispatcherServlet(ctx));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/rest/*");

	}
}

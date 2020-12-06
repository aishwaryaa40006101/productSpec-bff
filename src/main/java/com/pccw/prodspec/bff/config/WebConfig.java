
package com.pccw.prodspec.bff.config;

/*
 * Copyright (c) 2020,L&T Technology Services.
 * All Rights Reserved.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configurations class
 * 
 * @author 20023424
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	BuildProperties buildProperties;

	/**
	 * Method to resolve CORS issue for the HTTP methods
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods(RequestMethod.GET.toString(),
				RequestMethod.POST.toString(), RequestMethod.PATCH.toString(), RequestMethod.DELETE.toString());
	}

	/**
	 * Method for resource handlers
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}

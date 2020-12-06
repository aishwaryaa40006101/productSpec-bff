package com.pccw.prodspec.bff.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * Application configurations class
 * 
 * @author 20023424
 *
 */
@Configuration
@PropertySource("classpath:undertow-config.properties")

public class AppConfig {

	
}

package com.learning.springboot.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configurable
public class SwaggerConfig {

	public static final String SWAGGER_API_VERSION = "1.0";
	public static final String LICENSE_TEXT = "License";
	public static final String TITLE = "Learning Spring Boot";
	public static final String DESCRIPTION = "This application is for learning purpose only.";
	public static final String NAME = "Pawan Kumar";
	public static final String URL = "www.learning-springboot.com";
	public static final String EMAIL = "joginder.pawan@gmail.com";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaData()).select()
				.apis(RequestHandlerSelectors.basePackage("com.learning.springboot.controller")).build();
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).contact(new Contact(NAME, URL, EMAIL))
				.license(LICENSE_TEXT).version(SWAGGER_API_VERSION).build();
	}
}

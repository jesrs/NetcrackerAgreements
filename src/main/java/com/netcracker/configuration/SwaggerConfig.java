package com.netcracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * Configuration of Swagger
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		
	    // Specifies the package of the controllers
		return new Docket (DocumentationType.SWAGGER_2)
				.select()	
				.apis(
						RequestHandlerSelectors
						.basePackage("com.netcracker.controllers"))
				.paths(PathSelectors.any())
				.build();
	}

}

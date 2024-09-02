package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfig {
	

	@Bean
	  public OpenAPI springShopOpenAPI() {
		
		
		String schemeName="bearerScheme";
		
	//direct enter only token in authorize 
	      return new OpenAPI()
	    		  .addSecurityItem(new SecurityRequirement()
	    				  .addList(schemeName))
	    		  .components(new Components()
	    		  .addSecuritySchemes(schemeName,new SecurityScheme()
	    		 .name(schemeName)
	    		 .type(SecurityScheme.Type.HTTP)
	    		 .bearerFormat("JWT")
	    		 .scheme("bearer")
	    		 ))
	              .info(new Info().title("Blogging Application:Backend Project")
	              .description("This is developed by Learn Code With Naeem")
	              .version("1.0")
	              .contact(new Contact().name("Naeem").email("naeemkuraishi0078@gmail.com"))
	              .license(new License().name("Apache").url("https//localhost:8090/blog apis")))
	              .externalDocs(new ExternalDocumentation()
	              .description("BlogApplication Documentation")
	              .url("https://blogApp.naeem.github.org/docs"));
	  }
}
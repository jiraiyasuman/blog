package com.blog_post.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(
						new Info()
						.title("POST SERVICE API")
						.version("1.0.0")
						.description("CQRS Blog Post Microservice")
						.contact(
								new Contact()
								.name("Blog Team")
								.email("suman.talukdar53@gmail.com")
								)
						).externalDocs(
								new ExternalDocumentation().description("Documentation")
								);
				
	}
}
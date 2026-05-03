package com.blog.blog_login_module.util;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		OpenAPI openAPI = new OpenAPI();
		Info info = new Info();
		info.setTitle("BLOGS LOGIN MICROSERVICE API");
		info.setDescription("BLOGS LOGIN MICROSERVICE API");
		info.setVersion("1.0.0");
		info.setTermsOfService("https://portfolio-five-self-43.vercel.app/");
		info.setContact(
				new Contact().email("suman.talukdar53@gmail.com").name("Suman Talukdar").url("https://portfolio-five-self-43.vercel.app/contact.html")
				);
		info.setLicense(new License().name("BLOG LOGIN 1.0").url("https://portfolio-five-self-43.vercel.app/"));
		List<Server> serverList = List.of(new Server().description("DEV").url("http://localhost:8082"),
				new Server().description("TEST").url("http://localhost:8082"),
				new Server().description("PROD").url("http://localhost:8082"));
		
		SecurityScheme securityScheme = new SecurityScheme().name("Authorization").scheme("bearer").type(Type.HTTP).
				bearerFormat("JWT").in(In.HEADER);
		Components component = new Components().addSecuritySchemes("Token", securityScheme);
		openAPI.setServers(serverList);
		openAPI.setInfo(info);
		openAPI.setComponents(component);
		openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));
		return openAPI;
	}
}

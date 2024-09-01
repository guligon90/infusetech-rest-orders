package com.infusetech.rest.orders;

import java.util.Properties;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@SpringBootApplication
public class OrdersApplication {

	public static final Properties projectProperties = new Properties();

	public static void main(String[] args) {
		try {
			projectProperties.load(OrdersApplication
				.class
				.getClassLoader()
				.getResourceAsStream("project.properties")
			);

			SpringApplication.run(OrdersApplication.class, args);
		} catch (Exception e) {
			System.out.println("Erro ao iniciar o web service: " + e.getMessage());
		}
	}

	private static License buildLicense() {
		return new License()
			.name(projectProperties.getProperty("swagger.license.name"))
			.url(projectProperties.getProperty("swagger.license.url"));
	}

	private static Contact buildContact() {
		return new Contact()
			.name(projectProperties.getProperty("swagger.contact.name"))
			.email(projectProperties.getProperty("swagger.contact.email"))
			.url(projectProperties.getProperty("swagger.contact.url"));
	}

	private static Info buildInformation(Contact contact, License license) {
		return new Info()
			.title(projectProperties.getProperty("swagger.info.title"))
			.description(projectProperties.getProperty("swagger.info.description"))
			.contact(contact)
			.license(license)
			.version(projectProperties.getProperty("version"));

	}

	private static OperationCustomizer buildOperationCustomizer() {
		return (operation, handlerMethod) -> {
			operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));

			return operation;
		};
	}

	@Bean
	GroupedOpenApi ordersGroup() {
		License license = buildLicense();
		Contact contact = buildContact();

		return GroupedOpenApi
			.builder()
			.group("orders")
			.addOperationCustomizer(buildOperationCustomizer())
			.addOpenApiCustomizer(openApi -> openApi.info(buildInformation(contact, license)))
			.build();
	}
}

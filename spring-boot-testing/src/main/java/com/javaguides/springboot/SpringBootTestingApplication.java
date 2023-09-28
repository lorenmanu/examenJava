package com.javaguides.springboot;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "SpringBootTestingApplication for do Exam",
				description = "Aplicación en Spring Boot para gestionar precios basados en etiquetas, lista de productos ...",
				version = "v1.0",
				contact = @Contact(
						name = "Lorenzo",
						email = "lorenrr1@gmail.com"
				),
				license = @License(
						name = "Apache 2.0"
				)
		)
)
@SpringBootApplication
public class SpringBootTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestingApplication.class, args);
	}

}

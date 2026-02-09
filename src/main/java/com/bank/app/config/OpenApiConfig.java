package com.bank.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "AI-Powered Smart Banking Backend API", version = "1.0", description = "Smart Banking Backend with AI-powered transaction categorization, "
        +
        "fraud detection, and financial insights using Spring Boot Microservices", contact = @Contact(name = "Banking API Support", email = "support@smartbanking.com")), servers = {
                @Server(description = "Local Development Server", url = "http://localhost:8080")
        })
@SecurityScheme(name = "bearerAuth", description = "JWT authentication", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}

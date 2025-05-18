package com.citizen.engagement_system_be.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        description = "Secure your API with a JWT token for user authentication. Please provide a token in a header for accessing protected endpoints.",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerApiDoc {

    @Value("${app.server.url}")
    private String serverUrl;

    @Bean
    @Profile("dev")  // Profile for local development
    public OpenAPI openAPIForLocal() {
        return new OpenAPI()
                .info(new Info()
                        .title("🚀 Welcome to the Swagger documentation for Citizen Engagement System backend apis.")
                        .description("Welcome to the Swagger documentation for Citizen Engagement System apis.")
                        .version("1.0")
                )
                .servers(List.of(
                        new Server().url(serverUrl).description("Local Development Environment")
                ));
    }

    @Bean
    @Profile("prod")  // Profile for production environment
    public OpenAPI openAPIForProd() {
        return new OpenAPI()
                .info(new Info()
                        .title("🚀 Welcome to the Swagger documentation for Citizen Engagement System backend apis.")
                        .description("Welcome to the Swagger documentation for Citizen Engagement System backend apis.")
                        .version("1.0")
                )
                .servers(List.of(
                        new Server().url(serverUrl).description("Production Environment")
                ));
    }

    @Bean
    @Profile("test")  // Profile for testing environment
    public OpenAPI openAPIForTest() {
        return new OpenAPI()
                .info(new Info()
                        .title("🚀 Welcome to the Swagger documentation for Citizen Engagement System backend apis.")
                        .description("Welcome to the Swagger documentation for Citizen Engagement System backend apis.")
                        .version("1.0")
                )
                .servers(List.of(
                        new Server().url(serverUrl).description("Testing Environment")
                ));
    }
}

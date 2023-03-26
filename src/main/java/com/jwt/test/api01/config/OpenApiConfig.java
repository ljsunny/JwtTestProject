package com.jwt.test.api01.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {@Configuration
@OpenAPIDefinition(info = @Info(
        title = "API Documentation",
        version = "1.0.0",
        description = "Example API documentation"))
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components())
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8080").description("Local Server"),
                        new Server().url("https://example.com").description("Production Server")
                ))
                .paths(new Paths());
    }
}
}

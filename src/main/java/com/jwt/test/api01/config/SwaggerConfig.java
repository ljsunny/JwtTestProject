package com.jwt.test.api01.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
@OpenAPIDefinition(
        info = @Info(title = "Zerock App",version = "v1"))
@RequiredArgsConstructor
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
public Docket api(){
    return new Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(List.of(apiKey()))
            .securityContexts(List.of(securityContext()))
            .apiInfo(apiInfo());
}

private ApiInfo apiInfo(){
    return new ApiInfoBuilder()
            .title("Boot API 01 Project Swagger")
            .build();
}

private ApiKey apiKey(){
    return new ApiKey("Authentication","Bearer Token","header");
}

private SecurityContext securityContext(){
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .operationSelector(selector-> selector.requestMappingPattern().startsWith("/api/"))
            .build();
}
private List<SecurityReference> defaultAuth(){
    AuthorizationScope authorizationScope = new AuthorizationScope("global","global error");
    
    return List.of(new SecurityReference("Authorization", new AuthorizationScope[]{authorizationScope}));
}
}

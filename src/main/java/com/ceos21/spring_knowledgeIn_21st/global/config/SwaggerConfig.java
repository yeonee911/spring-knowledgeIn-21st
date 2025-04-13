package com.ceos21.spring_knowledgeIn_21st.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@OpenAPIDefinition(
        info = @Info(
                title = "네이버 지식인 API 명세서",
                description = "ceos 21 BE : 네이버 지식인 클론 코딩"
        )
)

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(securityRequirement())
                .components(authSetting());
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes(
                        "Authorization",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name(AUTHORIZATION));
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(AUTHORIZATION);
    }
}

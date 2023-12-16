package com.nawaz.watsapp.web.clone.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nawaz
 */
@Configuration
public class SwagggerConfig {
    String schemeName = "bearer";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                .addList(schemeName))
                .components(new Components()
                           .addSecuritySchemes(schemeName,
                            new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat("JWT")
                                        .scheme("bearer")))
                .info(new Info()
                                         .title("Chat Application")
                                         .description("Rest API")
                                         .version("1.0")
                                         .contact(new Contact()
                                         .name("Nawaz Quazi")
                                         .email("nawazquazi356@gmail.com")))
                .externalDocs(new ExternalDocumentation());
    }

}

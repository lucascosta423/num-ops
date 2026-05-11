package com.main.numOps.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .tags(List.of(
                        new Tag().name("AUTH").description("Authentication"),
                        new Tag().name("USER").description("Management Users"),
                        new Tag().name("PROVIDER").description("Management Providers"),
                        new Tag().name("CUSTOMER").description("Management Customer"),
                        new Tag().name("TICKET").description("Management Tickets"),
                        new Tag().name("DIDs CONFIGURED").description("Active DIDs management"),
                        new Tag().name("PORTABILITY").description("Management Providers"),
                        new Tag().name("DIDs AVAILABLE").description("Management Available DID pool"),
                        new Tag().name("OPERATORS").description("Management Providers")
                ))
                .info(new Info()
                        .title("NumOps API")
                        .version("1.0")
                        .description("Documentação da API com Swagger OpenAPI"));
    }
}

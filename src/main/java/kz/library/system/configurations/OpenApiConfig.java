package kz.library.system.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {

        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Library System Service API")
                        .description(
                                "Library System. ")
                        .contact(new Contact()
                                .name("LibSystem")
                        )
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }

}

package vn.unigap.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
  @Bean
  public OpenAPI myOpenAPI() {
    OpenAPI openAPI = new OpenAPI();

    Contact contact = new Contact();
    contact.setEmail("ducjavadev@gmail.com");
    contact.setName("Duong Minh Duc");
    contact.setUrl("https://www.sample.com");

    License mitLicense =
        new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
    Info info =
        new Info()
            .title("Recruitment Service API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage tutorials.")
            .termsOfService("https://www.sample.com/terms")
            .license(mitLicense);
    openAPI.setInfo(info);
    openAPI
        .components(
            new Components()
                .addSecuritySchemes(
                    "JWTToken",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .scheme("bearer")))
        .addSecurityItem(new SecurityRequirement().addList("JWTToken"));
    return openAPI;
  }
}

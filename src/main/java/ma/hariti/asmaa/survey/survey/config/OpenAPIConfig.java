package ma.hariti.asmaa.survey.survey.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Survey API Documentation")
                        .version("1.0")
                        .description("API documentation for the Survey application")
                        .contact(new Contact()
                                .name("Asmaa")
                                .email("haritiasmae@gmail.com")));
    }
}
package cl.gamecenter.control_tiempo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Control de Tiempo — GameCenter")
                        .version("1.0.0")
                        .description("API para gestión de sesiones de juego activas y control de tiempo"));
    }
}
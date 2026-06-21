package cl.gamecenter.pago.config;

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
                        .title("MS Pagos — GameCenter")
                        .version("1.0.0")
                        .description("API para gestión de pagos de sesiones y membresías con validación de promociones"));
    }
}
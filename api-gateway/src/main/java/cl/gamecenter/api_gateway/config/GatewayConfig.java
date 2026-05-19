package cl.gamecenter.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("usuario-service", r -> r
                        .path("/api/usuarios/**", "/api/roles/**", "/api/auth/**")
                        .uri("lb://USUARIO"))
                .route("estacion-service", r -> r
                        .path("/api/estaciones/**", "/api/tipos-estacion/**")
                        .uri("lb://ESTACION"))
                .route("reserva-service", r -> r
                        .path("/api/reservas/**")
                        .uri("lb://RESERVA"))
                .route("control-tiempo-service", r -> r
                        .path("/api/sesiones/**")
                        .uri("lb://CONTROL-TIEMPO"))
                .route("membresia-service", r -> r
                        .path("/api/membresias/**", "/api/tipos-membresia/**")
                        .uri("lb://MEMBRESIA"))
                .build();
    }
}
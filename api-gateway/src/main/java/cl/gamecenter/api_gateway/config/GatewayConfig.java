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
                        .path("/api/v1/usuarios/**", "/api/v1/roles/**", "/api/v1/auth/**")
                        .uri("lb://usuario"))
                .route("estacion-service", r -> r
                        .path("/api/v1/estaciones/**", "/api/v1/tipos-estacion/**")
                        .uri("lb://estacion"))
                .route("reserva-service", r -> r
                        .path("/api/v1/reservas/**")
                        .uri("lb://reserva"))
                .route("control-tiempo-service", r -> r
                        .path("/api/v1/sesiones/**")
                        .uri("lb://control-tiempo"))
                .route("membresia-service", r -> r
                        .path("/api/v1/membresias/**", "/api/v1/tipos-membresia/**")
                        .uri("lb://membresia"))
                .route("pago-service", r -> r
                        .path("/api/v1/pagos/**")
                        .uri("lb://pago"))
                .route("lista-espera-service", r -> r
                        .path("/api/v1/entradas-espera/**", "/api/v1/config-espera/**")
                        .uri("lb://lista-espera"))
                .route("notificacion-service", r -> r
                        .path("/api/v1/notificaciones/**")
                        .uri("lb://notificacion"))
                .route("reporte-service", r -> r
                        .path("/api/v1/reportes-ocupacion/**", "/api/v1/snapshots-ingreso/**")
                        .uri("lb://reporte"))
                .route("promocion-service", r -> r
                        .path("/api/v1/promociones/**", "/api/v1/codigos-descuento/**", "/api/v1/usos-promocion/**")
                        .uri("lb://promocion"))
                .build();
    }
}

CREATE TABLE sesion (
                        id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
                        reserva_id         BIGINT NOT NULL,
                        estacion_id        BIGINT NOT NULL,
                        usuario_id         BIGINT NOT NULL,
                        inicio_real        DATETIME NOT NULL,
                        fin_real           DATETIME,
                        minutos_consumidos INT,
                        tarifa_por_hora    DECIMAL(10,2) NOT NULL,
                        total_calculado    DECIMAL(10,2),
                        estado             ENUM('ACTIVA','CERRADA','CANCELADA') NOT NULL DEFAULT 'ACTIVA'
);
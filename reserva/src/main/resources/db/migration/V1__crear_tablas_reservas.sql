CREATE TABLE reserva (
                         id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                         usuario_id   BIGINT NOT NULL,
                         estacion_id  BIGINT NOT NULL,
                         fecha_inicio DATETIME NOT NULL,
                         fecha_fin    DATETIME NOT NULL,
                         estado       ENUM('PENDIENTE','CONFIRMADA','EN_CURSO','COMPLETADA','CANCELADA') NOT NULL DEFAULT 'PENDIENTE',
                         notas        TEXT,
                         created_at   DATETIME NOT NULL
);
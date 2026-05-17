CREATE TABLE notificacion (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id  BIGINT          NOT NULL,
    tipo        VARCHAR(50)     NOT NULL,
    titulo      VARCHAR(255)    NOT NULL,
    mensaje     VARCHAR(500)    NOT NULL,
    leida       TINYINT(1)      NOT NULL,
    canal       VARCHAR(50)     NOT NULL,
    created_at  DATETIME        NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- usuario_id 1=ADMIN, 2=CLIENTE, 3=OPERADOR

INSERT INTO notificacion (id, usuario_id, tipo, titulo, mensaje, leida, canal, created_at) VALUES
(1, 2, 'LISTA_ESPERA', 'Turno disponible', 'Hay una estación Consola PS5 disponible para ti.', 0, 'APP', '2026-05-16 11:00:00'),
(2, 2, 'PAGO', 'Pago confirmado', 'Tu pago por sesión de gaming fue procesado correctamente.', 1, 'EMAIL', '2026-05-10 16:05:00'),
(3, 2, 'PROMOCION', 'Código aplicado', 'Se aplicó el descuento VERANO2026 en tu última sesión.', 0, 'APP', '2026-05-10 18:31:00'),
(4, 1, 'SISTEMA', 'Resumen diario', 'Reporte de ocupación del local generado.', 1, 'EMAIL', '2026-05-16 08:00:00'),
(5, 3, 'LISTA_ESPERA', 'En cola VR', 'Estás en lista de espera para Realidad Virtual.', 0, 'PUSH', '2026-05-16 11:31:00');

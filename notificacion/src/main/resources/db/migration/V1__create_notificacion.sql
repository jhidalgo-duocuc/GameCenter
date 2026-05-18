CREATE TABLE notificacion (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id  BIGINT          NOT NULL,
    tipo        ENUM('LISTA_ESPERA', 'PAGO', 'PROMOCION', 'SISTEMA') NOT NULL,
    titulo      VARCHAR(255)    NOT NULL,
    mensaje     VARCHAR(500)    NOT NULL,
    leida       TINYINT(1)      NOT NULL,
    canal       ENUM('APP', 'EMAIL', 'PUSH') NOT NULL,
    created_at  DATETIME        NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

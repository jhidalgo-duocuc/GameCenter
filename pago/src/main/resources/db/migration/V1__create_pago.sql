CREATE TABLE pago (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id          BIGINT          NOT NULL,
    tipo                ENUM('SESION', 'MEMBRESIA') NOT NULL,
    sesion_id           BIGINT          NULL,
    membresia_id        BIGINT          NULL,
    promocion_id        BIGINT          NULL,
    monto_bruto         DECIMAL(19, 2)  NOT NULL,
    descuento_aplicado  DECIMAL(19, 2)  NOT NULL,
    monto_final         DECIMAL(19, 2)  NOT NULL,
    metodo_pago         ENUM('EFECTIVO', 'TARJETA', 'TRANSFERENCIA', 'QR') NOT NULL,
    estado              ENUM('PENDIENTE', 'COMPLETADO', 'RECHAZADO', 'ANULADO') NOT NULL,
    referencia_externa  VARCHAR(255)    NOT NULL,
    fecha_pago          DATETIME        NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

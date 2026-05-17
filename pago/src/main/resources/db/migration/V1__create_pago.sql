CREATE TABLE pago (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id          BIGINT          NOT NULL,
    tipo                VARCHAR(50)     NOT NULL,
    sesion_id           BIGINT          NULL,
    membresia_id        BIGINT          NULL,
    promocion_id        BIGINT          NULL,
    monto_bruto         DECIMAL(19, 2)  NOT NULL,
    descuento_aplicado  DECIMAL(19, 2)  NOT NULL,
    monto_final         DECIMAL(19, 2)  NOT NULL,
    metodo_pago         VARCHAR(50)     NOT NULL,
    estado              VARCHAR(50)     NOT NULL,
    referencia_externa  VARCHAR(255)    NOT NULL,
    fecha_pago          DATETIME        NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- usuario_id 1=ADMIN, 2=CLIENTE, 3=OPERADOR (convención, MS usuario)
-- sesion_id 1-3 (control-tiempo) | membresia_id 1 plan Básico (tipo_membresia id 1)
-- promocion_id 1-3 (mismo archivo en db_promociones)

INSERT INTO pago (id, usuario_id, tipo, sesion_id, membresia_id, promocion_id, monto_bruto, descuento_aplicado, monto_final, metodo_pago, estado, referencia_externa, fecha_pago) VALUES
(1, 2, 'SESION', 1, NULL, NULL, 5000.00, 0.00, 5000.00, 'TARJETA', 'COMPLETADO', 'PAY-SES-001', '2026-05-10 16:00:00'),
(2, 2, 'MEMBRESIA', NULL, 1, 2, 15000.00, 1500.00, 13500.00, 'TRANSFERENCIA', 'COMPLETADO', 'PAY-MEM-001', '2026-05-08 10:30:00'),
(3, 2, 'SESION', 2, NULL, 1, 6000.00, 900.00, 5100.00, 'QR', 'COMPLETADO', 'PAY-SES-002', '2026-05-10 18:30:00'),
(4, 3, 'SESION', 3, NULL, NULL, 8000.00, 0.00, 8000.00, 'EFECTIVO', 'PENDIENTE', 'PAY-SES-003', '2026-05-15 20:00:00');

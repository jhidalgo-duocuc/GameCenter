CREATE TABLE promocion (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    nombre          VARCHAR(255)    NOT NULL,
    descripcion     VARCHAR(500)    NOT NULL,
    tipo            ENUM('PORCENTAJE', 'MONTO_FIJO') NOT NULL,
    descuento_pct   DECIMAL(19, 2)  NOT NULL,
    fecha_inicio    DATE            NOT NULL,
    fecha_fin       DATE            NOT NULL,
    activo          TINYINT(1)      NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE codigo_descuento (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    promocion_id    BIGINT          NOT NULL,
    codigo          VARCHAR(255)    NOT NULL,
    usos_max        INT             NOT NULL,
    usos_actuales   INT             NOT NULL,
    activo          TINYINT(1)      NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_codigo_descuento_codigo (codigo),
    CONSTRAINT fk_codigo_descuento_promocion
        FOREIGN KEY (promocion_id) REFERENCES promocion (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE uso_promocion (
    id                  BIGINT      NOT NULL AUTO_INCREMENT,
    codigo_descuento_id BIGINT      NOT NULL,
    usuario_id          BIGINT      NOT NULL,
    pago_id             BIGINT      NOT NULL,
    usado_en            DATETIME    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_uso_promocion_codigo_descuento
        FOREIGN KEY (codigo_descuento_id) REFERENCES codigo_descuento (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Promociones y códigos iniciales (catálogo)
INSERT INTO promocion (nombre, descripcion, tipo, descuento_pct, fecha_inicio, fecha_fin, activo) VALUES
('Verano Gaming', 'Descuento de temporada en sesiones', 'PORCENTAJE', 15.00, '2026-01-01', '2026-12-31', 1),
('Bienvenida', 'Descuento para nuevos clientes', 'PORCENTAJE', 10.00, '2026-01-01', '2026-12-31', 1),
('VR Night', 'Promoción nocturna en estaciones VR', 'PORCENTAJE', 20.00, '2026-01-01', '2026-12-31', 1);

INSERT INTO codigo_descuento (promocion_id, codigo, usos_max, usos_actuales, activo) VALUES
(1, 'VERANO2026', 100, 0, 1),
(2, 'BIENVENIDA10', 50, 0, 1),
(3, 'VRNIGHT', 30, 0, 1);

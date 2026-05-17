CREATE TABLE reporte_ocupacion (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    estacion_id         BIGINT          NOT NULL,
    fecha               DATE            NOT NULL,
    horas_ocupadas      DECIMAL(19, 2)  NOT NULL,
    horas_disponibles   DECIMAL(19, 2)  NOT NULL,
    pct_ocupacion       DECIMAL(19, 2)  NOT NULL,
    ingresos_dia        DECIMAL(19, 2)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE snapshot_ingreso (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    periodo             VARCHAR(50)     NOT NULL,
    total_sesiones      INT             NOT NULL,
    total_membresias    INT             NOT NULL,
    ingresos_brutos     DECIMAL(19, 2)  NOT NULL,
    descuentos_total    DECIMAL(19, 2)  NOT NULL,
    ingresos_netos      DECIMAL(19, 2)  NOT NULL,
    generated_at        DATETIME        NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- estacion_id 1-3 (una por tipo_estacion: PC Gaming, PS5, VR)

INSERT INTO reporte_ocupacion (id, estacion_id, fecha, horas_ocupadas, horas_disponibles, pct_ocupacion, ingresos_dia) VALUES
(1, 1, '2026-05-15', 8.00, 4.00, 66.67, 20000.00),
(2, 2, '2026-05-15', 6.00, 6.00, 50.00, 18000.00),
(3, 3, '2026-05-15', 10.00, 2.00, 83.33, 40000.00),
(4, 1, '2026-05-16', 4.00, 8.00, 33.33, 10000.00);

INSERT INTO snapshot_ingreso (id, periodo, total_sesiones, total_membresias, ingresos_brutos, descuentos_total, ingresos_netos, generated_at) VALUES
(1, '2026-05', 45, 8, 850000.00, 65000.00, 785000.00, '2026-05-16 00:00:00'),
(2, '2026-04', 38, 5, 720000.00, 42000.00, 678000.00, '2026-05-01 00:00:00');

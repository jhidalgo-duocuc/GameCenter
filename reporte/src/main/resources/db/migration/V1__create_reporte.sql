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

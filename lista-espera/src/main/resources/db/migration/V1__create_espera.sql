CREATE TABLE config_espera (
    id                      BIGINT      NOT NULL AUTO_INCREMENT,
    minutos_para_confirmar  INT         NOT NULL,
    max_intentos            INT         NOT NULL,
    activo                  TINYINT(1)  NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE entrada_espera (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    usuario_id          BIGINT          NOT NULL,
    tipo_estacion_id    BIGINT          NOT NULL,
    posicion            INT             NOT NULL,
    estado              VARCHAR(50)     NOT NULL,
    fecha_ingreso       DATETIME        NOT NULL,
    fecha_notificacion  DATETIME        NULL,
    expira_en           DATETIME        NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

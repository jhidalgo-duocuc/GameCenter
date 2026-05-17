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

-- usuario_id 2=CLIENTE, 3=OPERADOR | tipo_estacion_id 1=PC Gaming, 2=PS5, 3=VR

INSERT INTO config_espera (id, minutos_para_confirmar, max_intentos, activo) VALUES
(1, 15, 3, 1);

INSERT INTO entrada_espera (id, usuario_id, tipo_estacion_id, posicion, estado, fecha_ingreso, fecha_notificacion, expira_en) VALUES
(1, 2, 1, 1, 'ESPERANDO', '2026-05-16 10:00:00', NULL, NULL),
(2, 2, 2, 1, 'NOTIFICADO', '2026-05-16 09:30:00', '2026-05-16 11:00:00', '2026-05-16 11:15:00'),
(3, 3, 3, 1, 'ESPERANDO', '2026-05-16 11:30:00', NULL, NULL),
(4, 2, 1, 2, 'ATENDIDO', '2026-05-15 18:00:00', '2026-05-15 18:20:00', '2026-05-15 18:35:00');

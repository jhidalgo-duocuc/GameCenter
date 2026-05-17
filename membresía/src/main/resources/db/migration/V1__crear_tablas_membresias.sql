CREATE TABLE tipo_membresia (
                                id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                                nombre          VARCHAR(100) NOT NULL,
                                descripcion     VARCHAR(255),
                                precio_mensual  DECIMAL(10,2) NOT NULL,
                                horas_incluidas INT NOT NULL,
                                descuento_pct   DECIMAL(5,2) NOT NULL,
                                activo          BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE membresia (
                           id                BIGINT AUTO_INCREMENT PRIMARY KEY,
                           usuario_id        BIGINT NOT NULL,
                           tipo_membresia_id BIGINT NOT NULL,
                           fecha_inicio      DATE NOT NULL,
                           fecha_fin         DATE NOT NULL,
                           estado            ENUM('ACTIVA','VENCIDA','CANCELADA') NOT NULL DEFAULT 'ACTIVA',
                           created_at        DATETIME NOT NULL,
                           CONSTRAINT fk_membresia_tipo FOREIGN KEY (tipo_membresia_id) REFERENCES tipo_membresia(id)
);

-- Planes iniciales
INSERT INTO tipo_membresia (nombre, descripcion, precio_mensual, horas_incluidas, descuento_pct, activo) VALUES
                                                                                                             ('Básico', 'Plan básico con 10 horas incluidas', 15000.00, 10, 5.00, true),
                                                                                                             ('Pro', 'Plan pro con 25 horas incluidas', 30000.00, 25, 15.00, true),
                                                                                                             ('Elite', 'Plan elite con horas ilimitadas', 50000.00, 999, 25.00, true);
CREATE TABLE tipo_estacion (
                               id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                               nombre       VARCHAR(100) NOT NULL,
                               descripcion  VARCHAR(255),
                               precio_hora  DECIMAL(10,2) NOT NULL,
                               activo       BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE estacion (
                          id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                          tipo_estacion_id BIGINT NOT NULL,
                          nombre           VARCHAR(100) NOT NULL,
                          especificaciones TEXT,
                          estado           ENUM('DISPONIBLE','OCUPADA','MANTENIMIENTO','INACTIVA') NOT NULL DEFAULT 'DISPONIBLE',
                          updated_at       DATETIME,
                          CONSTRAINT fk_estacion_tipo FOREIGN KEY (tipo_estacion_id) REFERENCES tipo_estacion(id)
);

-- Tipos de estación iniciales
INSERT INTO tipo_estacion (nombre, descripcion, precio_hora, activo) VALUES
                                                                         ('PC Gaming', 'Computador de alto rendimiento para gaming', 2500.00, true),
                                                                         ('Consola PS5', 'PlayStation 5 con televisor 4K', 3000.00, true),
                                                                         ('Realidad Virtual', 'Experiencia VR con Meta Quest', 4000.00, true);
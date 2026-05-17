CREATE TABLE rol (
                     id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                     nombre      VARCHAR(50)  NOT NULL,
                     descripcion VARCHAR(255)
);

CREATE TABLE usuario (
                         id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                         rol_id      BIGINT       NOT NULL,
                         nombre      VARCHAR(100) NOT NULL,
                         apellido    VARCHAR(100) NOT NULL,
                         email       VARCHAR(150) NOT NULL UNIQUE,
                         password    VARCHAR(255) NOT NULL,
                         telefono    VARCHAR(20),
                         activo      BOOLEAN      NOT NULL DEFAULT TRUE,
                         created_at  DATETIME     NOT NULL,
                         CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE token_auth (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            usuario_id  BIGINT       NOT NULL,
                            token       VARCHAR(512) NOT NULL UNIQUE,
                            tipo        ENUM('ACCESS','REFRESH','RESET_PASSWORD') NOT NULL,
                            expira_en   DATETIME,
                            usado       BOOLEAN      NOT NULL DEFAULT FALSE,
                            CONSTRAINT fk_token_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Roles iniciales
INSERT INTO rol (nombre, descripcion) VALUES
                                          ('ADMIN', 'Administrador del sistema'),
                                          ('CLIENTE', 'Cliente del gaming center'),
                                          ('OPERADOR', 'Operador de estaciones');
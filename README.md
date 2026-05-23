# GameCenter — Sistema de Gestión de Sala de Juegos

> Plataforma para administrar estaciones de juego, reservas, membresías, pagos y reportes en un gaming center moderno.  
> Proyecto semestral — **Full Stack I**, 2026.

---

## Tabla de contenidos

1. [Descripción del proyecto](#descripción-del-proyecto)
2. [Integrantes](#integrantes)
3. [Stack tecnológico](#stack-tecnológico)
4. [Arquitectura](#arquitectura)
5. [Clonar el repositorio](#clonar-el-repositorio)
6. [Requisitos previos](#requisitos-previos)
7. [Configuración de MySQL](#configuración-de-mysql)
8. [Cómo levantar el proyecto](#cómo-levantar-el-proyecto)
9. [Bases de datos y tablas](#bases-de-datos-y-tablas)
10. [Documentación de APIs REST](#documentación-de-apis-rest)
11. [API Gateway y rutas](#api-gateway-y-rutas)
12. [Autenticación](#autenticación)
13. [Comunicación entre microservicios](#comunicación-entre-microservicios)
14. [Verificación con Postman / cURL](#verificación-con-postman--curl)
15. [Estructura del repositorio](#estructura-del-repositorio)
16. [Estado del proyecto](#estado-del-proyecto)

---

## Descripción del proyecto

**GameCenter** es un sistema de gestión para negocios tipo ciber café o sala de consolas. Permite:

- Registrar y autenticar usuarios con roles (ADMIN, CLIENTE, OPERADOR).
- Administrar estaciones de juego (PC, consolas, VR) y su disponibilidad.
- Crear reservas y controlar sesiones activas con cálculo de tiempo y tarifa.
- Gestionar membresías con planes y descuentos.
- Procesar pagos de sesiones y membresías, aplicando promociones.
- Mantener una lista de espera cuando no hay estaciones disponibles.
- Enviar notificaciones a los clientes.
- Generar reportes de ocupación e ingresos.

Cada microservicio es **independiente**, tiene su **propia base de datos MySQL** y se registra en **Eureka** para ser descubierto por el **API Gateway** y por **OpenFeign**.

---

## Integrantes

| Nombre | Rol |
|--------|-----|
| Julian Hidalgo | Desarrollo |
| Jose Antinao | Desarrollo |

---

## Stack tecnológico

| Tecnología | Versión / detalle |
|------------|-------------------|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Cloud | 2025.1.1 |
| Spring Cloud Gateway | WebFlux (api-gateway) |
| Netflix Eureka | Service discovery |
| OpenFeign | Comunicación síncrona entre MS |
| Spring Data JPA | Persistencia |
| Flyway | Migraciones de base de datos |
| MySQL | 8.x / 9.x |
| Maven | 3.9.15 (incluye wrapper `mvnw` por módulo) |
| Lombok | Reducción de boilerplate |

> **Nota:** Las credenciales de base de datos están en `application.properties` de cada módulo, configuradas para **entorno de desarrollo local**.

---

## Arquitectura

```text
                         ┌─────────────────────┐
                         │   Cliente / Postman │
                         └──────────┬──────────┘
                                    │ HTTP
                         ┌──────────▼──────────┐
                         │   API Gateway :8080 │
                         └──────────┬──────────┘
                                    │ lb://servicio (Eureka)
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
     ┌────────▼────────┐   ┌────────▼────────┐   ┌───────▼────────┐
     │  Microservicios │   │  Microservicios │   │ Microservicios   │
     │    :8081–8090   │◄──┤   OpenFeign     ├──►│   :8081–8090     │
     └────────┬────────┘   └─────────────────┘   └──────────────────┘
              │
     ┌────────▼────────┐         ┌──────────────────┐
     │  MySQL (10 BD)  │         │ Eureka Server    │
     │  db_*           │         │ :8761            │
     └─────────────────┘         └──────────────────┘
```

### Mapa de servicios

| Módulo | Carpeta | Nombre Eureka | Puerto | Base de datos |
|--------|---------|---------------|--------|---------------|
| Eureka Server | `eureka-server` | `eureka-server` | 8761 | — |
| API Gateway | `api-gateway` | `api-gateway` | 8080 | — |
| MS-01 Usuario & Auth | `usuario` | `usuario` | 8081 | `db_usuarios` |
| MS-02 Estaciones | `estacion` | `estacion` | 8082 | `db_estaciones` |
| MS-03 Reservas | `reserva` | `reserva` | 8083 | `db_reservas` |
| MS-04 Control de Tiempo | `control-tiempo` | `control-tiempo` | 8084 | `db_sesiones` |
| MS-05 Membresías | `membresía` | `membresia` | 8085 | `db_membresias` |
| MS-06 Pagos | `pago` | `pago` | 8086 | `db_pagos` |
| MS-07 Lista de Espera | `lista-espera` | `lista-espera` | 8087 | `db_espera` |
| MS-08 Notificaciones | `notificacion` | `notificacion` | 8088 | `db_notificaciones` |
| MS-09 Reportes | `reporte` | `reporte` | 8089 | `db_reportes` |
| MS-10 Promociones | `promocion` | `promocion` | 8090 | `db_promociones` |

Todas las APIs usan el prefijo **`/api/v1/`**.

- **Acceso externo (recomendado):** `http://localhost:8080/api/v1/...`
- **Acceso directo al microservicio:** `http://localhost:808X/api/v1/...`

---

## Clonar el repositorio

```bash
git clone https://github.com/jhidalgo-duocuc/GameCenter.git
cd GameCenter
```

En Windows (PowerShell):

```powershell
git clone https://github.com/jhidalgo-duocuc/GameCenter.git
cd GameCenter
```

El repositorio contiene **12 módulos Maven independientes** (no hay `pom.xml` raíz). Cada carpeta de microservicio incluye su propio `mvnw` / `mvnw.cmd`.

---

## Requisitos previos

Instalar antes de ejecutar:

| Software | Versión mínima |
|----------|----------------|
| JDK | 21 |
| MySQL Server | 8.0+ |
| Git | Cualquier versión reciente |
| Maven | 3.9+ *(opcional; usar `./mvnw` incluido)* |
| IDE | IntelliJ IDEA, VS Code + Extension Pack for Java, o Cursor |

Verificar Java:

```bash
java -version
# Debe mostrar version 21
```

---

## Configuración de MySQL

### 1. Crear usuario y bases de datos

Conectarse a MySQL como administrador y ejecutar:

```sql
CREATE USER IF NOT EXISTS 'adminapp'@'localhost' IDENTIFIED BY 'Admin1234!';
GRANT ALL PRIVILEGES ON *.* TO 'adminapp'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS db_usuarios;
CREATE DATABASE IF NOT EXISTS db_estaciones;
CREATE DATABASE IF NOT EXISTS db_reservas;
CREATE DATABASE IF NOT EXISTS db_sesiones;
CREATE DATABASE IF NOT EXISTS db_membresias;
CREATE DATABASE IF NOT EXISTS db_pagos;
CREATE DATABASE IF NOT EXISTS db_espera;
CREATE DATABASE IF NOT EXISTS db_notificaciones;
CREATE DATABASE IF NOT EXISTS db_reportes;
CREATE DATABASE IF NOT EXISTS db_promociones;
```

### 2. Credenciales usadas por los microservicios

| Parámetro | Valor |
|-----------|-------|
| Host | `localhost:3306` |
| Usuario | `adminapp` |
| Contraseña | `Admin1234!` |

### 3. Migraciones automáticas (Flyway)

Al iniciar cada microservicio, **Flyway** ejecuta automáticamente los scripts en `src/main/resources/db/migration/` y crea las tablas con datos iniciales donde corresponda. **No es necesario importar SQL manualmente** después de crear las bases de datos.

---

## Cómo levantar el proyecto

### Orden recomendado de arranque

1. **MySQL** — bases de datos creadas y en ejecución.
2. **Eureka Server** — puerto `8761`.
3. **Microservicios de negocio** — puertos `8081` a `8090` *(el orden entre ellos es flexible)*.
4. **API Gateway** — puerto `8080` *(requiere que Eureka tenga servicios registrados)*.

### Comando por módulo

Desde la carpeta de cada módulo:

**Linux / macOS:**

```bash
cd eureka-server
./mvnw spring-boot:run
```

**Windows (PowerShell):**

```powershell
cd eureka-server
.\mvnw.cmd spring-boot:run
```

Repetir para cada módulo en una terminal separada:

```text
eureka-server      → 8761
usuario            → 8081
estacion           → 8082
reserva            → 8083
control-tiempo     → 8084
membresía          → 8085
pago               → 8086
lista-espera       → 8087
notificacion       → 8088
reporte            → 8089
promocion          → 8090
api-gateway        → 8080
```

### Verificar que todo esté en marcha

| Recurso | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| API Gateway | http://localhost:8080/api/v1/... *(ver ejemplos abajo)* |

En Eureka deben aparecer registrados los servicios `USUARIO`, `ESTACION`, `RESERVA`, etc.

### Ejecución desde IDE (VS Code / Cursor)

1. Abrir la carpeta raíz `GameCenter`.
2. Esperar a que el **Java Language Server** importe todos los proyectos Maven.
3. Usar **Run and Debug** con las configuraciones en `.vscode/launch.json`.
4. Asegurarse de usar **JDK 21** (no Java 8).

> **Importante:** El módulo `api-gateway` debe reconocerse como proyecto Maven (`projectName: api-gateway`). Si no compila, ejecutar **Java: Clean Java Language Server Workspace** y reimportar.

---

## Bases de datos y tablas

Cada microservicio sigue el patrón **Database per Service**. Las tablas se crean vía Flyway al primer arranque.

### `db_usuarios` — MS Usuario & Auth

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `rol` | Roles del sistema | `id`, `nombre`, `descripcion` |
| `usuario` | Cuentas de usuario | `id`, `rol_id`, `nombre`, `apellido`, `email`, `password`, `telefono`, `activo`, `created_at` |
| `token_auth` | Tokens de sesión | `id`, `usuario_id`, `token`, `tipo` (ACCESS/REFRESH/RESET_PASSWORD), `expira_en`, `usado` |

**Datos iniciales:** roles `ADMIN`, `CLIENTE`, `OPERADOR`.

---

### `db_estaciones` — MS Estaciones

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `tipo_estacion` | Categorías de estación | `id`, `nombre`, `descripcion`, `precio_hora`, `activo` |
| `estacion` | Estaciones físicas | `id`, `tipo_estacion_id`, `nombre`, `especificaciones`, `estado` (DISPONIBLE/OCUPADA/MANTENIMIENTO/INACTIVA) |

**Datos iniciales:** PC Gaming, Consola PS5, Realidad Virtual.

---

### `db_reservas` — MS Reservas

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `reserva` | Reservas de estaciones | `id`, `usuario_id`, `estacion_id`, `fecha_inicio`, `fecha_fin`, `estado`, `notas`, `created_at` |

**Estados:** PENDIENTE, CONFIRMADA, EN_CURSO, COMPLETADA, CANCELADA.

---

### `db_sesiones` — MS Control de Tiempo

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `sesion` | Sesiones de juego activas/cerradas | `id`, `reserva_id`, `estacion_id`, `usuario_id`, `inicio_real`, `fin_real`, `minutos_consumidos`, `tarifa_por_hora`, `total_calculado`, `estado` |

**Estados:** ACTIVA, CERRADA, CANCELADA.

---

### `db_membresias` — MS Membresías

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `tipo_membresia` | Planes disponibles | `id`, `nombre`, `descripcion`, `precio_mensual`, `horas_incluidas`, `descuento_pct`, `activo` |
| `membresia` | Membresías de usuarios | `id`, `usuario_id`, `tipo_membresia_id`, `fecha_inicio`, `fecha_fin`, `estado`, `created_at` |

**Datos iniciales:** planes Básico, Pro, Elite.

---

### `db_pagos` — MS Pagos

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `pago` | Transacciones | `id`, `usuario_id`, `tipo` (SESION/MEMBRESIA), `sesion_id`, `membresia_id`, `promocion_id`, `monto_bruto`, `descuento_aplicado`, `monto_final`, `metodo_pago`, `estado`, `referencia_externa`, `fecha_pago` |

---

### `db_espera` — MS Lista de Espera

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `config_espera` | Reglas de la cola | `id`, `minutos_para_confirmar`, `max_intentos`, `activo` |
| `entrada_espera` | Entradas en cola | `id`, `usuario_id`, `tipo_estacion_id`, `posicion`, `estado`, `fecha_ingreso`, `fecha_notificacion`, `expira_en` |

**Datos iniciales:** configuración por defecto (15 min para confirmar, 3 intentos).

---

### `db_notificaciones` — MS Notificaciones

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `notificacion` | Mensajes al usuario | `id`, `usuario_id`, `tipo`, `titulo`, `mensaje`, `leida`, `canal`, `created_at` |

**Tipos:** LISTA_ESPERA, PAGO, PROMOCION, SISTEMA. **Canales:** APP, EMAIL, PUSH.

---

### `db_reportes` — MS Reportes

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `reporte_ocupacion` | Ocupación diaria por estación | `id`, `estacion_id`, `fecha`, `horas_ocupadas`, `horas_disponibles`, `pct_ocupacion`, `ingresos_dia` |
| `snapshot_ingreso` | Resumen de ingresos por período | `id`, `periodo`, `total_sesiones`, `total_membresias`, `ingresos_brutos`, `descuentos_total`, `ingresos_netos`, `generated_at` |

---

### `db_promociones` — MS Promociones

| Tabla | Descripción | Campos principales |
|-------|-------------|-------------------|
| `promocion` | Campañas promocionales | `id`, `nombre`, `descripcion`, `tipo`, `descuento_pct`, `fecha_inicio`, `fecha_fin`, `activo` |
| `codigo_descuento` | Códigos de descuento | `id`, `promocion_id`, `codigo`, `usos_max`, `usos_actuales`, `activo` |
| `uso_promocion` | Registro de uso | `id`, `codigo_descuento_id`, `usuario_id`, `pago_id`, `usado_en` |

**Datos iniciales:** promociones Verano Gaming, Bienvenida, VR Night con códigos `VERANO2026`, `BIENVENIDA10`, `VRNIGHT`.

---

## Documentación de APIs REST

**Base URL (Gateway):** `http://localhost:8080`  
**Base URL (directo):** `http://localhost:808X`  
**Prefijo común:** `/api/v1`

### MS-01 — Usuario & Auth (`:8081`)

#### Autenticación — `/api/v1/auth`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/auth/login` | Iniciar sesión |
| `POST` | `/api/v1/auth/logout` | Cerrar sesión (header `Authorization: Bearer <token>`) |

#### Usuarios — `/api/v1/usuarios`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/usuarios/registro` | Registrar nuevo usuario |
| `GET` | `/api/v1/usuarios` | Listar usuarios |
| `GET` | `/api/v1/usuarios/{id}` | Obtener por ID |
| `GET` | `/api/v1/usuarios/email/{email}` | Obtener por email |
| `PATCH` | `/api/v1/usuarios/{id}/desactivar` | Desactivar usuario |

#### Roles — `/api/v1/roles`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/roles` | Crear rol |
| `GET` | `/api/v1/roles` | Listar roles |
| `GET` | `/api/v1/roles/{id}` | Obtener rol por ID |

---

### MS-02 — Estaciones (`:8082`)

#### Estaciones — `/api/v1/estaciones`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/estaciones` | Crear estación |
| `GET` | `/api/v1/estaciones` | Listar todas |
| `GET` | `/api/v1/estaciones/{id}` | Obtener por ID |
| `GET` | `/api/v1/estaciones/disponibles` | Listar disponibles |
| `GET` | `/api/v1/estaciones/tipo/{tipoEstacionId}` | Por tipo |
| `GET` | `/api/v1/estaciones/tipo/{tipoEstacionId}/disponibles` | Disponibles por tipo |
| `PUT` | `/api/v1/estaciones/{id}/estado` | Cambiar estado |

#### Tipos de estación — `/api/v1/tipos-estacion`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/tipos-estacion` | Crear tipo |
| `GET` | `/api/v1/tipos-estacion` | Listar todos |
| `GET` | `/api/v1/tipos-estacion/activos` | Listar activos |
| `GET` | `/api/v1/tipos-estacion/{id}` | Obtener por ID |
| `PATCH` | `/api/v1/tipos-estacion/{id}/desactivar` | Desactivar tipo |

---

### MS-03 — Reservas (`:8083`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/reservas` | Crear reserva |
| `GET` | `/api/v1/reservas` | Listar reservas |
| `GET` | `/api/v1/reservas/{id}` | Obtener por ID |
| `GET` | `/api/v1/reservas/usuario/{usuarioId}` | Reservas de un usuario |
| `PATCH` | `/api/v1/reservas/{id}/confirmar` | Confirmar reserva |
| `PATCH` | `/api/v1/reservas/{id}/cancelar` | Cancelar reserva |

---

### MS-04 — Control de Tiempo (`:8084`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/sesiones` | Iniciar sesión |
| `PATCH` | `/api/v1/sesiones/{id}/cerrar` | Cerrar sesión |
| `GET` | `/api/v1/sesiones` | Listar sesiones |
| `GET` | `/api/v1/sesiones/{id}` | Obtener por ID |
| `GET` | `/api/v1/sesiones/activas` | Sesiones activas |
| `GET` | `/api/v1/sesiones/usuario/{usuarioId}` | Sesiones de un usuario |

---

### MS-05 — Membresías (`:8085`)

#### Membresías — `/api/v1/membresias`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/membresias` | Contratar membresía |
| `GET` | `/api/v1/membresias/{id}` | Obtener por ID |
| `GET` | `/api/v1/membresias/usuario/{usuarioId}` | Membresías del usuario |
| `GET` | `/api/v1/membresias/usuario/{usuarioId}/activa` | Membresía activa |
| `PATCH` | `/api/v1/membresias/{id}/cancelar` | Cancelar membresía |

#### Tipos de membresía — `/api/v1/tipos-membresia`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/tipos-membresia` | Crear plan |
| `GET` | `/api/v1/tipos-membresia` | Listar planes |
| `GET` | `/api/v1/tipos-membresia/activos` | Planes activos |
| `GET` | `/api/v1/tipos-membresia/{id}` | Obtener por ID |
| `PATCH` | `/api/v1/tipos-membresia/{id}/desactivar` | Desactivar plan |

---

### MS-06 — Pagos (`:8086`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/pagos` | Registrar pago |
| `GET` | `/api/v1/pagos` | Listar pagos |
| `GET` | `/api/v1/pagos/{id}` | Obtener por ID |
| `PUT` | `/api/v1/pagos/{id}` | Actualizar pago |
| `DELETE` | `/api/v1/pagos/{id}` | Eliminar pago |

---

### MS-07 — Lista de Espera (`:8087`)

#### Entradas — `/api/v1/entradas-espera`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/entradas-espera` |
| `GET` | `/api/v1/entradas-espera` |
| `GET` | `/api/v1/entradas-espera/{id}` |
| `PUT` | `/api/v1/entradas-espera/{id}` |
| `DELETE` | `/api/v1/entradas-espera/{id}` |

#### Configuración — `/api/v1/config-espera`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/config-espera` |
| `GET` | `/api/v1/config-espera` |
| `GET` | `/api/v1/config-espera/{id}` |
| `PUT` | `/api/v1/config-espera/{id}` |
| `DELETE` | `/api/v1/config-espera/{id}` |

---

### MS-08 — Notificaciones (`:8088`)

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/notificaciones` |
| `GET` | `/api/v1/notificaciones` |
| `GET` | `/api/v1/notificaciones/{id}` |
| `PUT` | `/api/v1/notificaciones/{id}` |
| `DELETE` | `/api/v1/notificaciones/{id}` |

---

### MS-09 — Reportes (`:8089`)

#### Ocupación — `/api/v1/reportes-ocupacion`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/reportes-ocupacion` |
| `GET` | `/api/v1/reportes-ocupacion` |
| `GET` | `/api/v1/reportes-ocupacion/{id}` |
| `PUT` | `/api/v1/reportes-ocupacion/{id}` |
| `DELETE` | `/api/v1/reportes-ocupacion/{id}` |

#### Snapshots de ingreso — `/api/v1/snapshots-ingreso`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/snapshots-ingreso` |
| `GET` | `/api/v1/snapshots-ingreso` |
| `GET` | `/api/v1/snapshots-ingreso/{id}` |
| `PUT` | `/api/v1/snapshots-ingreso/{id}` |
| `DELETE` | `/api/v1/snapshots-ingreso/{id}` |

---

### MS-10 — Promociones (`:8090`)

#### Promociones — `/api/v1/promociones`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/promociones` |
| `GET` | `/api/v1/promociones` |
| `GET` | `/api/v1/promociones/{id}` |
| `PUT` | `/api/v1/promociones/{id}` |
| `DELETE` | `/api/v1/promociones/{id}` |

#### Códigos de descuento — `/api/v1/codigos-descuento`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/codigos-descuento` |
| `GET` | `/api/v1/codigos-descuento` |
| `GET` | `/api/v1/codigos-descuento/{id}` |
| `PUT` | `/api/v1/codigos-descuento/{id}` |
| `DELETE` | `/api/v1/codigos-descuento/{id}` |

#### Usos de promoción — `/api/v1/usos-promocion`

| Método | Ruta |
|--------|------|
| `POST` | `/api/v1/usos-promocion` |
| `GET` | `/api/v1/usos-promocion` |
| `GET` | `/api/v1/usos-promocion/{id}` |
| `PUT` | `/api/v1/usos-promocion/{id}` |
| `DELETE` | `/api/v1/usos-promocion/{id}` |

---

## API Gateway y rutas

El gateway en `api-gateway` enruta peticiones externas hacia los microservicios registrados en Eureka:

| Ruta Gateway | Paths | Destino Eureka |
|--------------|-------|----------------|
| `usuario-service` | `/api/v1/usuarios/**`, `/api/v1/roles/**`, `/api/v1/auth/**` | `lb://usuario` |
| `estacion-service` | `/api/v1/estaciones/**`, `/api/v1/tipos-estacion/**` | `lb://estacion` |
| `reserva-service` | `/api/v1/reservas/**` | `lb://reserva` |
| `control-tiempo-service` | `/api/v1/sesiones/**` | `lb://control-tiempo` |
| `membresia-service` | `/api/v1/membresias/**`, `/api/v1/tipos-membresia/**` | `lb://membresia` |
| `pago-service` | `/api/v1/pagos/**` | `lb://pago` |
| `lista-espera-service` | `/api/v1/entradas-espera/**`, `/api/v1/config-espera/**` | `lb://lista-espera` |
| `notificacion-service` | `/api/v1/notificaciones/**` | `lb://notificacion` |
| `reporte-service` | `/api/v1/reportes-ocupacion/**`, `/api/v1/snapshots-ingreso/**` | `lb://reporte` |
| `promocion-service` | `/api/v1/promociones/**`, `/api/v1/codigos-descuento/**`, `/api/v1/usos-promocion/**` | `lb://promocion` |

Configuración adicional en `application.properties`:

```properties
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

---

## Autenticación

El módulo `usuario` implementa autenticación **custom** (sin Spring Security global):

- Contraseñas hasheadas con **BCrypt**.
- Login genera un **token UUID** almacenado en `token_auth` (vigencia ~8 horas).
- Logout invalida el token vía header `Authorization: Bearer <token>`.
- Roles disponibles: `ADMIN`, `CLIENTE`, `OPERADOR`.

> Los demás microservicios **no validan el token** en esta versión. La autenticación está centralizada en el MS de usuario para el flujo de login/registro.

### Ejemplo — Registro de usuario

```http
POST http://localhost:8080/api/v1/usuarios/registro
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "password": "password123",
  "telefono": "+56912345678",
  "rolId": 2
}
```

### Ejemplo — Login

```http
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "email": "juan@example.com",
  "password": "password123"
}
```

### Ejemplo — Logout

```http
POST http://localhost:8080/api/v1/auth/logout
Authorization: Bearer <token_recibido_en_login>
```

---

## Comunicación entre microservicios

Los microservicios se comunican de forma **síncrona** mediante **OpenFeign**, resolviendo destinos por nombre en Eureka:

| Cliente Feign | Microservicio que lo usa | Destino |
|---------------|--------------------------|---------|
| `EstacionClient` | reserva, control-tiempo, reporte, lista-espera | `estacion` |
| `UsuarioClient` | pago, promocion, notificacion, lista-espera | `usuario` |
| `SesionClient` | pago | `control-tiempo` |
| `MembresiaClient` | pago | `membresia` |
| `PromocionClient` | pago | `promocion` |
| `PagoClient` | promocion | `pago` |
| `NotificacionClient` | lista-espera | `notificacion` |

---

## Verificación con Postman / cURL

### 1. Verificar Eureka

Abrir en navegador: http://localhost:8761

### 2. Listar estaciones disponibles

```bash
curl http://localhost:8080/api/v1/estaciones/disponibles
```

### 3. Listar tipos de membresía activos

```bash
curl http://localhost:8080/api/v1/tipos-membresia/activos
```

### 4. Listar promociones

```bash
curl http://localhost:8080/api/v1/promociones
```

### 5. Flujo completo sugerido para revisión

1. Registrar un usuario (`POST /api/v1/usuarios/registro`).
2. Iniciar sesión (`POST /api/v1/auth/login`).
3. Consultar estaciones disponibles (`GET /api/v1/estaciones/disponibles`).
4. Crear una reserva (`POST /api/v1/reservas`).
5. Confirmar reserva (`PATCH /api/v1/reservas/{id}/confirmar`).
6. Iniciar sesión de juego (`POST /api/v1/sesiones`).
7. Registrar un pago (`POST /api/v1/pagos`).
8. Consultar reportes (`GET /api/v1/reportes-ocupacion`).

---

## Estructura del repositorio

```text
GameCenter/
├── api-gateway/          # Spring Cloud Gateway (:8080)
├── eureka-server/        # Service Discovery (:8761)
├── usuario/              # MS-01 Usuario & Auth (:8081)
├── estacion/             # MS-02 Estaciones (:8082)
├── reserva/              # MS-03 Reservas (:8083)
├── control-tiempo/       # MS-04 Control de Tiempo (:8084)
├── membresía/            # MS-05 Membresías (:8085)
├── pago/                 # MS-06 Pagos (:8086)
├── lista-espera/         # MS-07 Lista de Espera (:8087)
├── notificacion/         # MS-08 Notificaciones (:8088)
├── reporte/              # MS-09 Reportes (:8089)
├── promocion/            # MS-10 Promociones (:8090)
└── README.md
```

Cada módulo contiene:

```text
src/main/java/          # Código fuente (controllers, services, entities, clients)
src/main/resources/
  ├── application.properties
  └── db/migration/     # Scripts Flyway (V1__*.sql)
pom.xml
mvnw / mvnw.cmd
```

---

## Estado del proyecto

| Aspecto | Estado |
|---------|--------|
| Microservicios core | Implementados (10 MS + Gateway + Eureka) |
| Bases de datos | 10 BD MySQL con Flyway |
| API REST documentada | Sí (este README) |
| Service Discovery | Eureka |
| API Gateway | Spring Cloud Gateway |
| Autenticación | Login/logout con token (MS Usuario) |
| Mensajería Kafka | No implementado *(planeado a futuro)* |
| Docker Compose | No incluido |

**Proyecto en desarrollo activo** — Full Stack I, 2026.

---

## Contacto

Repositorio: https://github.com/jhidalgo-duocuc/GameCenter

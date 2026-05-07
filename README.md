# 🎮 GameCenter — Sistema de Gestión de Sala de Juegos

> Plataforma para administrar estaciones de juego, reservas, membresías y pagos en un gaming center moderno. Desarrollado como proyecto semestral para la asignatura Full Stack I.

---

## ¿Qué es esto?

GamingCenter es un sistema completo de gestión para negocios tipo ciber café o sala de consolas. Los clientes pueden reservar estaciones de juego por tiempo, el sistema controla la sesión activa, aplica descuentos según membresía y gestiona una lista de espera automática cuando no hay disponibilidad.

El dueño del local tiene acceso a un panel de reportes con ocupación por estación, ingresos por período y estadísticas de uso.

---

## Arquitectura

El sistema está construido sobre una arquitectura de **microservicios con Spring Boot**, donde cada módulo es independiente, maneja su propia base de datos y se comunica con los demás a través de APIs REST (OpenFeign) y eventos asincrónicos (Apache Kafka).

```text
API Gateway (:8080)
    │
    ├── MS-01  Usuario & Auth        :8081
    ├── MS-02  Estaciones            :8082
    ├── MS-03  Reservas              :8083
    ├── MS-04  Control de Tiempo     :8084
    ├── MS-05  Membresías            :8085
    ├── MS-06  Pagos                 :8086
    ├── MS-07  Lista de Espera       :8087
    ├── MS-08  Notificaciones        :8088
    ├── MS-09  Reportes              :8089
    └── MS-10  Promociones           :8090

Eureka Server (:8761) — service discovery
```

---

## Integrantes

- Julian Hidalgo
- Jose Antinao

---

## Microservicios

- Usuario & Auth
- Estaciones
- Reservas
- Control de Tiempo
- Membresías
- Pagos
- Lista de Espera
- Notificaciones
- Reportes
- Promociones

---

## Cómo levantar el proyecto

```bash
git clone https://github.com/jhidalgo-duocuc/GameCenter.git
cd GameCenter
```

> Documentación completa de instalación disponible próximamente en `/docs`.

---

## Estado del proyecto

En desarrollo — proyecto semestral Full Stack I, 2026.
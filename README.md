# 🍺 Licor Marketplace 24/7 – Backend Microservicios

> Arquitectura de microservicios construida con **Java 17 + Spring Boot 3 + PostgreSQL + RabbitMQ**

---

## 📐 Arquitectura

```
Cliente (App / Web)
        │
   [API Gateway :8080]  ← JWT Auth + Rate Limiting + Routing
        │
   ┌────┴────────────────────────────────────────────────┐
   │                                                     │
[Usuarios :8081]   [Catálogo :8082]   [Pedidos :8083]
[Pagos :8084]      [Inventario :8085] [Tiendas :8086]
[Envíos :8087]     [Notificaciones :8088]
   │
[PostgreSQL x8]    [RabbitMQ :5672]
```

## 🛠️ Stack Tecnológico (100% Free / Open Source)

| Capa              | Tecnología                         |
|-------------------|------------------------------------|
| Lenguaje          | Java 17                            |
| Framework         | Spring Boot 3.2                    |
| API Gateway       | Spring Cloud Gateway               |
| Autenticación     | JWT (jjwt 0.12)                    |
| Base de datos      | PostgreSQL 15                      |
| Migraciones       | Flyway                             |
| Mensajería        | RabbitMQ 3                         |
| Contenedores      | Docker + Docker Compose            |
| Docs API          | Swagger / OpenAPI 3 (SpringDoc)    |
| Build             | Maven                              |

---

## 🚀 Inicio Rápido

### Prerrequisitos
- Docker & Docker Compose instalados
- Java 17 + Maven (solo para desarrollo local)

### 1. Clonar y levantar con Docker

```bash
git clone <tu-repo>
cd licor-marketplace

# Levantar TODO (bases de datos + broker + servicios)
docker-compose up -d

# Ver logs
docker-compose logs -f api-gateway
```

### 2. Verificar que todo está arriba

```bash
# Health checks
curl http://localhost:8080/actuator/health   # Gateway
curl http://localhost:8081/actuator/health   # Usuarios
curl http://localhost:8082/actuator/health   # Catálogo

# RabbitMQ Management UI
open http://localhost:15672   # user: guest / pass: guest
```

### 3. Probar la API

```bash
# Registrar usuario
curl -X POST http://localhost:8080/api/usuarios/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@test.com",
    "password": "password123",
    "telefono": "3001234567",
    "fechaNac": "1995-05-15"
  }'

# Login
curl -X POST http://localhost:8080/api/usuarios/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "juan@test.com", "password": "password123"}'

# Usar el token en rutas protegidas
curl http://localhost:8080/api/pedidos \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 📡 Puertos de cada servicio

| Servicio              | Puerto | Swagger UI                          |
|-----------------------|--------|-------------------------------------|
| API Gateway           | 8080   | —                                   |
| Usuarios Service      | 8081   | http://localhost:8081/swagger-ui.html |
| Catálogo Service      | 8082   | http://localhost:8082/swagger-ui.html |
| Pedidos Service       | 8083   | http://localhost:8083/swagger-ui.html |
| Pagos Service         | 8084   | http://localhost:8084/swagger-ui.html |
| Inventario Service    | 8085   | http://localhost:8085/swagger-ui.html |
| Tiendas Service       | 8086   | http://localhost:8086/swagger-ui.html |
| Envíos Service        | 8087   | http://localhost:8087/swagger-ui.html |
| Notificaciones Service| 8088   | http://localhost:8088/swagger-ui.html |
| RabbitMQ UI           | 15672  | http://localhost:15672               |

---

## 🗂️ Estructura del Proyecto

```
licor-marketplace/
├── docker-compose.yml
├── pom.xml                          ← Parent Maven
├── api-gateway/
│   └── src/main/java/com/licor/gateway/
│       ├── ApiGatewayApplication.java
│       └── filter/AuthFilter.java
├── usuarios-service/
│   └── src/main/java/com/licor/usuariosservice/
│       ├── controller/UsuarioController.java
│       ├── service/UsuarioService.java
│       ├── repository/UsuarioRepository.java
│       ├── model/Usuario.java
│       ├── dto/UsuarioDto.java
│       ├── config/{SecurityConfig, JwtUtil}.java
│       └── exception/GlobalExceptionHandler.java
├── catalogo-service/
├── pedidos-service/
├── pagos-service/
├── inventario-service/
├── tiendas-service/
├── envios-service/
└── notificaciones-service/
```

---

## 🔄 Flujo de Compra (Resumen técnico)

```
1. POST /api/usuarios/auth/login          → JWT token
2. GET  /api/catalogo/productos           → Explorar productos
3. POST /api/pedidos                      → Crear pedido (requiere JWT)
4. GET  /api/inventario/verificar/{id}   → Verificar stock
5. POST /api/pagos                        → Procesar pago
6. POST /api/envios                       → Crear envío
7. (automático) Notificaciones via RabbitMQ
```

---

## ⚖️ Consideraciones Legales (Colombia)

- ✅ Verificación de **mayoría de edad (18+)** en registro
- ✅ Validación de horarios de venta por zona
- ✅ Pagos seguros con referencia externa
- ✅ Registro de movimientos de inventario para auditoría

---

## 🔜 Próximos pasos sugeridos

1. Implementar los controllers de Catálogo, Pedidos, Pagos
2. Agregar Spring Security por roles (ADMIN, TIENDA, CLIENTE)
3. Integrar pasarela de pagos (MercadoPago — tiene SDK Java gratis)
4. Configurar envío de emails con JavaMail + Gmail SMTP
5. Dockerizar y desplegar en Railway / Render (gratuitos)

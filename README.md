# TelcoNova Backend - Sistema de Gestión de Órdenes de Trabajo

Backend desarrollado en Spring Boot para la gestión de órdenes de trabajo, técnicos, asignaciones y reportes.

---

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [API Endpoints](#api-endpoints)
- [Modelos de Datos](#modelos-de-datos)
- [Autenticación y Seguridad](#autenticación-y-seguridad)
- [Base de Datos](#base-de-datos)
- [Configuración](#configuración)

---

## 🚀 Características

### Gestión de Usuarios
- ✅ Registro y autenticación con JWT
- ✅ Roles de usuario (supervisor, admin)
- ✅ Tokens de acceso seguros

### Gestión de Técnicos
- ✅ CRUD completo de técnicos
- ✅ Especialidades y zonas de trabajo
- ✅ Control de carga de trabajo
- ✅ Estado de disponibilidad

### Gestión de Órdenes de Trabajo
- ✅ Creación y actualización de órdenes
- ✅ Estados: pendiente, asignada, en progreso, completada
- ✅ Prioridades: baja, media, alta
- ✅ Filtrado por estado y zona
- ✅ Asignación a técnicos

### Sistema de Asignaciones
- ✅ **Asignación Manual**: Asignar orden a técnico específico
- ✅ **Asignación Automática**: Algoritmo inteligente que considera:
  - Especialidad del técnico
  - Carga de trabajo actual
  - Proximidad de zona
- ✅ Actualización automática de workload

### Sistema de Reportes
- ✅ Generación de métricas por técnico
- ✅ Filtros por fecha, tipo de servicio y zona
- ✅ Guardado de reportes históricos
- ✅ Consulta de historial con paginación
- ✅ Eliminación de reportes

### Sistema de Notificaciones
- ✅ Notificaciones por email (simuladas)
- ✅ Notificaciones por SMS (simuladas)
- ✅ Envío automático al asignar órdenes

---

## 🛠 Tecnologías

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Security** - Autenticación y autorización
- **JWT (JSON Web Tokens)** - Tokens de acceso
- **Spring Data JPA** - ORM
- **H2 Database** - Base de datos en memoria (desarrollo)
- **Lombok** - Reducción de código boilerplate
- **Jackson** - Serialización JSON
- **Maven** - Gestión de dependencias
- **Swagger/OpenAPI** - Documentación de API

---

## 📦 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- Puerto 8080 disponible

---

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio
```bash
cd BackendFabrica
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### 4. Acceder a la consola H2 (opcional)
URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:telconova`
- Usuario: `sa`
- Contraseña: (vacío)

### 5. Acceder a Swagger UI (opcional)
URL: `http://localhost:8080/swagger-ui.html`

---

## 📁 Estructura del Proyecto

```
BackendFabrica/
├── src/main/java/com/telconovaP7F22025/demo/
│   ├── config/
│   │   └── SecurityConfig.java          # Configuración de seguridad y CORS
│   ├── controller/
│   │   ├── AutController.java           # Endpoints de autenticación
│   │   ├── TechController.java          # Endpoints de técnicos
│   │   ├── OrderController.java         # Endpoints de órdenes
│   │   ├── AssignmentController.java    # Endpoints de asignaciones
│   │   ├── ReportController.java        # Endpoints de reportes
│   │   └── NotificationController.java  # Endpoints de notificaciones
│   ├── dto/
│   │   ├── assignment/
│   │   │   └── AssignmentRequest.java
│   │   ├── notification/
│   │   │   └── NotificationRequest.java
│   │   └── report/
│   │       └── ReportRequest.java
│   ├── model/
│   │   ├── User.java                    # Entidad de usuario
│   │   ├── Technician.java              # Entidad de técnico
│   │   ├── Order.java                   # Entidad de orden de trabajo
│   │   └── Report.java                  # Entidad de reporte
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── TechnicianRepository.java
│   │   ├── OrderRepository.java
│   │   └── ReportRepository.java
│   ├── security/
│   │   ├── JwtTokenProvider.java        # Generación y validación de JWT
│   │   ├── JwtAuthenticationFilter.java # Filtro de autenticación
│   │   └── CustomUserDetailsService.java
│   ├── service/
│   │   ├── ReportService.java
│   │   ├── AssignmentService.java
│   │   └── NotificationService.java
│   └── service/impl/
│       ├── ReportServiceImpl.java
│       ├── AssignmentServiceImpl.java
│       └── NotificationServiceImpl.java
├── src/main/resources/
│   ├── application.properties           # Configuración de la aplicación
│   ├── schema.sql                       # Esquema de base de datos
│   └── data.sql                         # Datos iniciales
└── pom.xml                              # Dependencias Maven
```

---

## 🔌 API Endpoints

### Autenticación

#### POST `/api/auth/login`
Iniciar sesión y obtener token JWT.

**Request:**
```json
{
  "email": "test@example.com",
  "password": "secret"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "email": "test@example.com"
}
```

#### POST `/api/auth/register`
Registrar nuevo usuario.

**Request:**
```json
{
  "email": "nuevo@example.com",
  "password": "password123"
}
```

---

### Técnicos

#### GET `/api/technicians/all`
Obtener todos los técnicos.

**Response:**
```json
[
  {
    "idTecnico": 1,
    "nameTecnico": "Juan Perez",
    "zoneTecnico": "Norte",
    "specialtyTecnico": "Electricidad",
    "workloadTecnico": "3"
  }
]
```

#### POST `/api/technicians/create`
Crear nuevo técnico.

**Request:**
```json
{
  "nameTecnico": "Maria Gomez",
  "zoneTecnico": "Sur",
  "specialtyTecnico": "Plomería",
  "workloadTecnico": "0"
}
```

---

### Órdenes de Trabajo

#### GET `/api/orders/all`
Obtener todas las órdenes (con filtros opcionales).

**Query Parameters:**
- `status` (opcional): pending, assigned, in_progress, completed
- `zona` (opcional): Norte, Sur, Este, Oeste, Centro

**Response:**
```json
[
  {
    "id": "ORD-001",
    "zona": "Norte",
    "servicio": "Electricidad",
    "descripcion": "Instalación de panel eléctrico",
    "nombreCliente": "Carlos López",
    "direccion": "Calle Principal 123",
    "prioridad": "high",
    "status": "pending",
    "assignedTo": null,
    "asignadoEn": null,
    "asignadoPor": null,
    "creadoEn": "2024-01-15T10:00:00"
  }
]
```

#### GET `/api/orders/{id}`
Obtener orden específica por ID.

#### POST `/api/orders/create`
Crear nueva orden de trabajo.

**Request:**
```json
{
  "zona": "Norte",
  "servicio": "Electricidad",
  "descripcion": "Reparación de cableado",
  "nombreCliente": "Ana Martínez",
  "direccion": "Av. Central 456",
  "prioridad": "medium"
}
```

#### PUT `/api/orders/update/{id}`
Actualizar orden existente.

#### DELETE `/api/orders/delete/{id}`
Eliminar orden.

---

### Asignaciones

#### POST `/api/assignments/manual`
Asignar orden manualmente a un técnico.

**Request:**
```json
{
  "idOrden": "ORD-001",
  "idTecnico": "1"
}
```

**Response:**
```json
{
  "id": "ORD-001",
  "status": "assigned",
  "assignedTo": "1",
  "asignadoEn": "2024-01-15T14:30:00",
  "asignadoPor": "user-001"
}
```

#### POST `/api/assignments/automatic`
Asignar orden automáticamente al mejor técnico disponible.

**Request:**
```json
{
  "idOrden": "ORD-002"
}
```

**Algoritmo de Asignación Automática:**
1. Filtra técnicos por especialidad requerida
2. Prioriza técnicos de la misma zona
3. Selecciona el técnico con menor carga de trabajo
4. Actualiza automáticamente el workload

---

### Reportes

#### GET `/api/reports/technician-metrics`
Obtener métricas de técnicos con filtros.

**Query Parameters:**
- `startDate`: Fecha inicio (formato: YYYY-MM-DD)
- `endDate`: Fecha fin (formato: YYYY-MM-DD)
- `serviceType`: Tipo de servicio (opcional, default: "all")
- `zone`: Zona (opcional, default: "all")

**Response:**
```json
{
  "success": true,
  "data": {
    "metrics": [
      {
        "technicianId": "1",
        "technicianName": "Juan Perez",
        "zone": "Norte",
        "specialty": "Electricidad",
        "totalOrders": 15,
        "completedOrders": 12,
        "inProgressOrders": 3,
        "avgResolutionTime": 2.5
      }
    ],
    "summary": {
      "totalOrders": 15,
      "totalCompleted": 12,
      "totalInProgress": 3,
      "avgResolutionTime": 2.5
    }
  }
}
```

#### POST `/api/reports/save`
Guardar reporte generado.

**Request:**
```json
{
  "nombreReporte": "Reporte Mensual Enero 2024",
  "filtros": {
    "startDate": "2024-01-01",
    "endDate": "2024-01-31",
    "serviceType": "all",
    "zone": "all"
  },
  "metricas": [...],
  "resumen": {
    "totalOrders": 50,
    "totalCompleted": 45,
    "totalInProgress": 5,
    "avgResolutionTime": 2.8
  }
}
```

**Response:**
```json
{
  "success": true,
  "message": "Reporte guardado exitosamente",
  "data": {
    "idReporte": "RPT-1234567890",
    "nombreReporte": "Reporte Mensual Enero 2024",
    "filtros": "{...}",
    "metricas": "[...]",
    "resumen": "{...}",
    "creadoEn": "2024-01-31T15:00:00",
    "creadoPor": "user-001"
  }
}
```

#### GET `/api/reports/history`
Obtener historial de reportes guardados.

**Query Parameters:**
- `page`: Número de página (default: 1)
- `limit`: Elementos por página (default: 10)
- `sortBy`: Campo para ordenar (default: "creadoEn")
- `sortOrder`: Orden asc/desc (default: "desc")

**Response:**
```json
{
  "success": true,
  "data": {
    "reports": [...],
    "pagination": {
      "currentPage": 1,
      "totalPages": 3,
      "totalReports": 25,
      "limit": 10
    }
  }
}
```

#### GET `/api/reports/history/{reportId}`
Obtener detalle de reporte específico.

#### DELETE `/api/reports/history/{reportId}`
Eliminar reporte.

---

### Notificaciones

#### POST `/api/notifications/send`
Enviar notificación a técnico.

**Request:**
```json
{
  "idOrden": "ORD-001",
  "idTecnico": "1",
  "canales": ["email", "sms"]
}
```

> **Nota:** Las notificaciones están simuladas mediante logging. En producción se integraría con servicios reales de email/SMS.

---

## 📊 Modelos de Datos

### User (Usuario)
```java
{
  "id": Long,
  "email": String,
  "password": String (encriptado),
  "role": String
}
```

### Technician (Técnico)
```java
{
  "idTecnico": Long,
  "nameTecnico": String,
  "zoneTecnico": String,
  "specialtyTecnico": String,
  "workloadTecnico": String
}
```

### Order (Orden de Trabajo)
```java
{
  "id": String,
  "zona": String,
  "servicio": String,
  "descripcion": String,
  "nombreCliente": String,
  "direccion": String,
  "prioridad": String,
  "status": String,
  "assignedTo": String,
  "asignadoEn": LocalDateTime,
  "asignadoPor": String,
  "creadoEn": LocalDateTime
}
```

### Report (Reporte)
```java
{
  "idReporte": String,
  "nombreReporte": String,
  "filtros": String (JSON),
  "metricas": String (JSON),
  "resumen": String (JSON),
  "creadoEn": LocalDateTime,
  "creadoPor": String
}
```

---

## 🔐 Autenticación y Seguridad

### JWT (JSON Web Tokens)

El sistema utiliza JWT para autenticación stateless:

1. **Login**: Usuario envía credenciales → Backend genera JWT
2. **Requests**: Cliente incluye JWT en header `Authorization: Bearer <token>`
3. **Validación**: Backend valida token en cada request

### Configuración de Seguridad

**Endpoints Públicos:**
- `/api/auth/**` - Login y registro
- `/h2-console/**` - Consola H2 (solo desarrollo)
- `/swagger-ui/**` - Documentación API

**Endpoints Protegidos:**
- Todos los demás requieren token JWT válido

### CORS

Configurado para permitir requests desde:
- `http://localhost:5173` (Vite default)
- `http://localhost:8081` (Frontend actual)

**Métodos permitidos:** GET, POST, PUT, DELETE, PATCH, OPTIONS

**Headers permitidos:** Authorization, Content-Type, X-Auth-Token

---

## 💾 Base de Datos

### H2 Database (Desarrollo)

**Configuración:**
- Modo: In-memory
- URL: `jdbc:h2:mem:telconova`
- Usuario: `sa`
- Contraseña: (vacío)

### Esquema de Tablas

#### usuarios
```sql
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
```

#### tecnicos
```sql
CREATE TABLE tecnicos (
    id_tecnico BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_tecnico VARCHAR(255) NOT NULL,
    zone_tecnico VARCHAR(100) NOT NULL,
    specialty_tecnico VARCHAR(100) NOT NULL,
    workload_tecnico VARCHAR(50) DEFAULT '0'
);
```

#### ordenes
```sql
CREATE TABLE ordenes (
    id VARCHAR(64) PRIMARY KEY,
    zona VARCHAR(100) NOT NULL,
    servicio VARCHAR(100) NOT NULL,
    descripcion TEXT,
    nombre_cliente VARCHAR(255),
    direccion VARCHAR(255),
    prioridad VARCHAR(20),
    status VARCHAR(50) NOT NULL,
    assigned_to VARCHAR(64),
    asignado_en TIMESTAMP,
    asignado_por VARCHAR(64),
    creado_en TIMESTAMP NOT NULL
);
```

#### reportes
```sql
CREATE TABLE reportes (
    id_reporte VARCHAR(64) PRIMARY KEY,
    nombre_reporte VARCHAR(255) NOT NULL,
    filtros TEXT,
    metricas TEXT,
    resumen TEXT,
    creado_en TIMESTAMP NOT NULL,
    creado_por VARCHAR(64) NOT NULL
);
```

### Datos Iniciales

El sistema carga automáticamente datos de prueba desde `data.sql`:
- 1 usuario de prueba: `test@example.com` / `secret`
- 6 técnicos con diferentes especialidades y zonas
- 8 órdenes de trabajo de ejemplo

---

## ⚙️ Configuración

### application.properties

```properties
# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:telconova
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# SQL Initialization
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:/data.sql
spring.sql.init.schema-locations=classpath:/schema.sql

# JWT
app.jwt.secret=TelcoNovaSecretKeyForJWTTokenGenerationAndValidation2024
app.jwt.expiration=86400000
```

### Variables de Entorno (Producción)

Para producción, configurar:
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/telconova
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=your_password
export JWT_SECRET=your_production_secret_key
```

---

## 🧪 Testing

### Ejecutar Tests
```bash
mvn test
```

### Compilar sin Tests
```bash
mvn clean compile -DskipTests
```

---

## 📝 Notas de Desarrollo

### Convenciones de Código
- **Nombres de campos**: Español (zona, servicio, nombreCliente, etc.)
- **Nombres de clases/métodos**: Inglés (OrderController, saveReport, etc.)
- **DTOs**: Records de Java para inmutabilidad
- **Servicios**: Interfaces + Implementaciones

### Logging
- Nivel INFO para operaciones normales
- Nivel ERROR para excepciones
- JwtAuthenticationFilter incluye logging detallado

### Manejo de Errores
- Excepciones capturadas en controllers
- Respuestas JSON consistentes con `success` y `message`
- Status codes HTTP apropiados

---

## 🚀 Despliegue en Producción

### 1. Cambiar a Base de Datos Persistente
Actualizar `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/telconova
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### 2. Configurar JWT Secret Seguro
```properties
app.jwt.secret=${JWT_SECRET}
```

### 3. Deshabilitar H2 Console
```properties
spring.h2.console.enabled=false
```

### 4. Actualizar CORS
```java
configuration.setAllowedOrigins(Arrays.asList("https://your-domain.com"));
```

### 5. Compilar JAR
```bash
mvn clean package -DskipTests
```

### 6. Ejecutar
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## 📞 Soporte

Para preguntas o problemas, contactar al equipo de desarrollo.

---

## 📄 Licencia

Proyecto privado - TelcoNova © 2024

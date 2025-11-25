# TelcoNova Backend

Sistema backend para gestión de órdenes de trabajo y asignación de técnicos.

## 🚀 Despliegue en Producción

**URL de Producción:** https://telconova-backend.onrender.com

**Health Check:** https://telconova-backend.onrender.com/api/health

---

## 🛠️ Tecnologías

- **Framework:** Spring Boot 3.5.6
- **Java:** 17
- **Base de Datos Desarrollo:** H2 (en memoria)
- **Base de Datos Producción:** PostgreSQL
- **Autenticación:** JWT
- **Email:** Resend API
- **Documentación API:** Swagger/OpenAPI
- **Build:** Maven
- **Deployment:** Docker en Render

---

## 📦 Dependencias Principales

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- H2 Database (desarrollo)
- PostgreSQL Driver (producción)
- JWT (io.jsonwebtoken)
- Resend Java SDK
- Lombok
- Swagger/OpenAPI

---

## 🔧 Configuración

### Desarrollo Local

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Telconova-App/telconova-backend.git
   cd telconova-backend
   ```

2. **Configurar variables de entorno:**
   ```bash
   cp .env.example .env
   # Editar .env con tus valores
   ```

3. **Ejecutar:**
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder:**
   - API: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
   - Swagger UI: http://localhost:8080/swagger-ui.html

### Producción (Render)

**Variables de Entorno Requeridas:**

```bash
# Spring Profile
SPRING_PROFILES_ACTIVE=prod

# Database (usar External Database URL de Render con prefijo jdbc:)
SPRING_DATASOURCE_URL=jdbc:postgresql://[host]:[port]/[database]?user=[username]&password=[password]

# JWT
JWT_SECRET=[your-secure-random-string-min-256-bits]

# Resend Email
RESEND_API_KEY=your_resend_api_key
RESEND_FROM_EMAIL=onboarding@resend.dev
RESEND_FROM_NAME=Acme

# CORS
FRONTEND_URL=https://telconova-frontend.vercel.app

# Server
PORT=8080
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/telconovaP7F22025/demo/
├── config/              # Configuración (Security, CORS)
├── controller/          # Controladores REST
│   ├── AutController.java
│   ├── TechController.java
│   ├── OrderController.java
│   ├── AssignmentController.java
│   ├── NotificationController.java
│   ├── ReportController.java
│   ├── HealthController.java
│   └── TestController.java
├── dto/                 # Data Transfer Objects
├── model/               # Entidades JPA
│   ├── User.java
│   ├── Technician.java
│   ├── Order.java
│   └── Report.java
├── repository/          # Repositorios JPA
├── security/            # JWT y filtros de seguridad
├── service/             # Lógica de negocio
│   ├── impl/
│   │   ├── EmailServiceImpl.java
│   │   ├── AssignmentServiceImpl.java
│   │   └── ...
└── TelconovaP7F2Application.java

src/main/resources/
├── application.properties           # Configuración desarrollo
├── application-prod.properties      # Configuración producción
├── schema.sql                       # Schema de base de datos
└── data.sql                         # Datos iniciales
```

---

## 🔐 Autenticación

El sistema usa JWT (JSON Web Tokens) para autenticación.

### Login

```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "secret"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "email": "test@example.com"
}
```

### Uso del Token

Incluir en el header de las peticiones:
```
Authorization: Bearer {token}
```

---

## 📡 API Endpoints

### Autenticación
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro de usuario

### Técnicos
- `GET /api/technicians/all` - Listar todos los técnicos
- `POST /api/technicians/create` - Crear técnico

### Órdenes de Trabajo
- `GET /api/orders/all` - Listar todas las órdenes
- `GET /api/orders/{id}` - Obtener orden por ID
- `POST /api/orders/create` - Crear orden
- `PUT /api/orders/update/{id}` - Actualizar orden
- `DELETE /api/orders/delete/{id}` - Eliminar orden

### Asignaciones
- `POST /api/assignments/manual` - Asignación manual
- `POST /api/assignments/automatic` - Asignación automática

### Notificaciones
- `POST /api/notifications/send` - Enviar notificación por email

### Reportes
- `GET /api/reports/technician-metrics` - Métricas de técnicos
- `POST /api/reports/save` - Guardar reporte
- `GET /api/reports/history` - Historial de reportes
- `GET /api/reports/history/{id}` - Detalle de reporte
- `DELETE /api/reports/history/{id}` - Eliminar reporte

### Health Check
- `GET /api/health` - Estado del servicio

### Testing
- `POST /api/test/send-email` - Probar envío de email

---

## 📧 Sistema de Notificaciones

El sistema usa **Resend** para enviar notificaciones por email cuando se asigna una orden a un técnico.

**Configuración:**
- API Key de Resend en variable de entorno
- Email de origen: `onboarding@resend.dev` (tier gratuito)
- Para producción: verificar dominio personalizado en Resend

**Email de prueba verificado:** `telconovas@gmail.com`

---

## 🗄️ Base de Datos

### Desarrollo (H2)
- Base de datos en memoria
- Se reinicia en cada ejecución
- Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:telconova`

### Producción (PostgreSQL)
- PostgreSQL en Render
- Datos persistentes
- Migraciones automáticas con Hibernate
- Schema definido en `schema.sql`
- Datos iniciales en `data.sql` (solo primera ejecución)

### Datos Iniciales

**Usuario de prueba:**
- Email: `test@example.com`
- Password: `secret`
- Rol: Administrator

**Técnicos:**
- Juan Perez (telconovas@gmail.com) - Zona Centro
- Maria Garcia - Zona Norte
- Carlos Rodriguez - Zona Sur
- Ana Martinez - Zona Este

---

## 🔒 Seguridad

### CORS
- Configurado dinámicamente con variable `FRONTEND_URL`
- Permite credenciales
- Todos los controladores tienen `@CrossOrigin`
- OPTIONS requests permitidos explícitamente

### Endpoints Públicos
- `/api/auth/**` - Autenticación
- `/api/health` - Health check
- `/api/test/**` - Testing (solo desarrollo)
- `/h2-console/**` - H2 console (solo desarrollo)

### Endpoints Protegidos
Todos los demás endpoints requieren JWT válido.

---

## 🐳 Docker

### Dockerfile

```dockerfile
# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build y Run Local

```bash
# Build
docker build -t telconova-backend .

# Run
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e JWT_SECRET=your-secret \
  -e RESEND_API_KEY=your-key \
  telconova-backend
```

---

## 🧪 Testing

### Test de Email

```bash
POST /api/test/send-email
Content-Type: application/json

{
  "to": "telconovas@gmail.com",
  "subject": "Test",
  "message": "Test message"
}
```

### Health Check

```bash
GET /api/health

Response:
{
  "service": "TelcoNova Backend",
  "version": "1.0.0",
  "status": "UP"
}
```

---

## 📊 Monitoreo

### Logs en Render
- Acceder a dashboard de Render
- Ver logs en tiempo real
- Buscar errores y warnings

### Métricas
- Health check endpoint para uptime monitoring
- Logs de CORS para debugging
- Logs de email service

---

## 🚨 Troubleshooting

### Error de Conexión a Base de Datos
1. Verificar `SPRING_DATASOURCE_URL` tiene formato correcto
2. Asegurar que incluye `jdbc:` al inicio
3. Verificar puerto `:5432` en la URL
4. Confirmar credenciales correctas

### Error 403 CORS
1. Verificar `FRONTEND_URL` está configurada
2. Revisar logs para ver URL de CORS
3. Asegurar que todos los controladores tienen `@CrossOrigin`
4. Verificar que Render ha redesplegado

### Email No Se Envía
1. Verificar `RESEND_API_KEY` es correcta
2. Confirmar email destino está verificado (tier gratuito)
3. Revisar dashboard de Resend
4. Verificar `RESEND_FROM_EMAIL` y `RESEND_FROM_NAME`

---

## 📝 Notas de Desarrollo

### Perfiles de Spring
- **default:** Desarrollo local con H2
- **prod:** Producción con PostgreSQL

### Cambiar entre Perfiles

```bash
# Desarrollo
mvn spring-boot:run

# Producción local
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 🔗 Enlaces

- **Producción:** https://telconova-backend.onrender.com
- **GitHub:** https://github.com/Telconova-App/telconova-backend
- **Frontend:** https://telconova-frontend.vercel.app
- **Resend Dashboard:** https://resend.com/dashboard

---

## 📄 Licencia

Este proyecto es privado y de uso interno.

---

## 👥 Equipo

Desarrollado para TelcoNova - Sistema de Gestión de Órdenes de Trabajo

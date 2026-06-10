# E-Commerce Backend

Full-featured e-commerce REST API built with Spring Boot 3.2, Spring Security (JWT), JPA/Hibernate, and PostgreSQL.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Framework | Spring Boot 3.2.5 (Java 21) |
| Security | JWT (access + refresh tokens) |
| Database | PostgreSQL via JPA/Hibernate |
| Build | Gradle |
| API Docs | Swagger UI / API_DOCUMENTATION.md |

## Quick Start (Local)

### Prerequisites
- Java 21
- PostgreSQL running on `localhost:5432`
- Database `ecommerce_db` (created automatically with `ddl-auto: update`)

### Run
```bash
./gradlew bootRun
```

The server starts at `http://localhost:4999/api`.

### Default Credentials
| Role | Email | Password |
|------|-------|----------|
| Admin | admin@ecommerce.com | admin123 |
| Customer | john@example.com | customer123 |

## Configuration

All config in `src/main/resources/application.yml`. Key overridable env vars:

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `8080` | Server port |
| `JDBC_DATABASE_URL` | `jdbc:postgresql://localhost:5432/ecommerce_db` | DB connection |
| `JDBC_DATABASE_USERNAME` | `postgres` | DB user |
| `JDBC_DATABASE_PASSWORD` | `Lik@2003` | DB password |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000,http://localhost:4200,http://localhost:5173` | Allowed CORS origins |
| `JWT_SECRET` | *(default 256-bit)* | JWT signing key |

> **Railway / production:** The app auto-detects `DATABASE_URL` (PostgreSQL connection string) at startup and parses it into the JDBC equivalent with `sslmode=require`.

## API Documentation

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for full endpoint reference, request/response schemas, and error codes.

**Swagger UI** (when running locally): `http://localhost:4999/api/swagger-ui.html`

### Key Endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/auth/login` | No | Login, returns JWT |
| POST | `/auth/register` | No | Register new user |
| POST | `/auth/refresh-token` | No | Refresh access token |
| GET | `/products` | No | List products (paginated) |
| GET | `/products/{id}` | No | Product detail |
| GET | `/admin/**` | Admin | Admin management endpoints |
| GET | `/reports/sales` | Admin | Sales report |

## Docker

```bash
# Build & run with PostgreSQL
docker compose up --build
```

## Deployment (Railway)

1. Push to GitHub
2. Create Railway project → **Deploy from GitHub repo**
3. Add **PostgreSQL** plugin
4. The app auto-detects `DATABASE_URL` — **no manual env vars needed**
5. Railway auto-deploys on every push

### Optional Env Vars on Railway
- `CORS_ALLOWED_ORIGINS` — set to your Vercel/frontend URL
- `JWT_SECRET` — custom signing key

## Project Structure

```
src/main/java/com/ecommerce/
├── config/        # App config, CORS, Swagger, DataSeeder
├── constant/      # Security & app constants
├── controller/    # REST controllers
├── dto/           # Request/Response DTOs
├── entity/        # JPA entities
├── enums/         # Enumerations (Role, Status, etc.)
├── exception/     # Custom exceptions & global handler
├── repository/    # Spring Data JPA repositories
├── security/      # JWT filter, provider, auth entry point
├── service/       # Business logic interfaces & implementations
└── util/          # Utility classes
```

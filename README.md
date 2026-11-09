# 📚 BiblioHex — Sistema de préstamos de biblioteca

Aplicación web con **Arquitectura Hexagonal**, **Código Limpio**, **Refactoring** y principios **SOLID**.

| Capa | Tecnología |
|------|-----------|
| Backend | Spring Boot 3 · Java 17 · Spring Data JPA |
| Frontend | React 18 · Vite |
| Base de datos | H2 en memoria (reemplazable por PostgreSQL/MongoDB) |

## Arquitectura hexagonal

```
            ┌──────────────────────── Backend ────────────────────────┐
 React ──►  │ adapter.in.web        application            domain     │
 (HTTP)     │ Controllers ───►  Services (casos de uso) ──► Book/Loan │
            │                        │                    ports.in    │
            │                        ▼                    ports.out ◄─┼── adapter.out.persistence
            │                  usa PUERTOS OUT                        │   (JPA / H2)
            └─────────────────────────────────────────────────────────┘
```

**Regla de dependencia:** las flechas apuntan hacia el dominio. `domain` no importa Spring, JPA ni nada externo.

```
com.biblioteca
├── domain
│   ├── model        Book, Loan (reglas de negocio)
│   ├── exception    excepciones de negocio
│   └── port
│       ├── in       casos de uso (RegisterBook, LendBook, ReturnBook, ...)
│       └── out      BookRepositoryPort, LoanRepositoryPort, ClockPort
├── application.service   BookService, LoanService (implementan puertos IN)
└── infrastructure
    ├── adapter.in.web            controllers, DTOs, manejo de errores
    ├── adapter.out.persistence   entidades JPA, adaptadores, mapper
    └── config                    BeanConfig (composition root)
```

> Nota de diseño: los servicios usan `@Transactional` (solo la anotación de `spring-tx`).
> Es un compromiso pragmático y consciente; el dominio sigue 100 % libre de frameworks.

## Evidencia de SOLID

| Principio | Dónde se ve |
|-----------|-------------|
| **S**ingle Responsibility | `PersistenceMapper` solo traduce; `GlobalExceptionHandler` solo maneja errores; cada caso de uso tiene su interfaz |
| **O**pen/Closed | Para cambiar a MongoDB se crea otro adaptador de `BookRepositoryPort`; no se modifica el dominio ni los servicios |
| **L**iskov | `InMemoryRepositories` (tests) y `*PersistenceAdapter` son intercambiables detrás del mismo puerto |
| **I**nterface Segregation | Un puerto de entrada por caso de uso; los controllers dependen solo de lo que usan |
| **D**ependency Inversion | Los servicios dependen de `*Port` (abstracciones); `BeanConfig` inyecta las implementaciones |

## Evidencia de Código Limpio

- Nombres que revelan intención (`markReturned`, `ensureIsbnIsUnique`, `askMemberAndLend`).
- Métodos cortos con una sola tarea; sin comentarios que expliquen código confuso.
- Sin números mágicos (`LOAN_DAYS`), sin `null` como flujo (`Optional`), excepciones con significado.
- Fábricas estáticas (`Book.register`, `Loan.start`) que garantizan objetos válidos.
- Tests con nombres que describen el comportamiento.

## Evidencia de Refactoring (ejemplo)

**Antes** — servicio con `if` anidados y lógica de negocio fuera del dominio:

```java
if (book.isAvailable()) {
    book.setAvailable(false);
    ...
} else {
    throw new RuntimeException("no disponible");
}
```

**Después** — la regla vive en el dominio (*Tell, Don't Ask*):

```java
book.lend();   // Book valida y lanza BookNotAvailableException
```

Otros refactorings aplicados: *Extract Method* (`findBook`, `ensureIsbnIsUnique`), *Replace Magic Number with Constant*,
*Introduce Parameter Object* (`LendBookCommand`), *Extract Class* (`PersistenceMapper`).


## Cómo ejecutar

```bash
# Backend (puerto 8080)
cd backend
mvn spring-boot:run
mvn test            # pruebas del núcleo sin base de datos

# Frontend (puerto 5173)
cd frontend
npm install
npm run dev
```

## API

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/books` | Registrar libro |
| GET | `/api/books` | Listar libros |
| POST | `/api/loans` | Prestar libro `{bookId, memberName}` |
| PUT | `/api/loans/{id}/return` | Devolver libro |
| GET | `/api/loans` | Listar préstamos |

Errores: `400` datos inválidos · `404` no existe · `409` regla de negocio violada.

## Subir a GitHub

```bash
git init
git add .
git commit -m "feat: proyecto base con arquitectura hexagonal"
git branch -M main
git remote add origin https://github.com/<usuario>/biblioteca-hexagonal.git
git push -u origin main
```

## Ideas para ampliar

1. Agregar el agregado `Member` con su propio repositorio.
2. Límite de préstamos por socio (nueva regla en el dominio).
3. Segundo adaptador de persistencia (MongoDB) activado con un perfil de Spring.
4. Tests de integración con `@WebMvcTest` y pruebas con Vitest en el frontend.

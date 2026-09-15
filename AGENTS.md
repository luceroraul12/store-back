# AGENTS.md

Spring Boot 2.7.4 / Java 17 single-module Maven project ("scrapping"). REST backend for a distributor product catalog: products are imported from distributor Excel files or scraped from distributor websites; also customers, carts/orders, discounts, stores (`client`), suppliers, and JWT auth.

## Build & test

- `./mvnw` has no exec bit and no system `mvn` is installed — always run `bash ./mvnw ...`.
- Tests are stale and fail (expired hardcoded JWT in `JwtUtilServiceTest`, NPEs from unset `@Autowired`/`ExternalProduct` in `PdfServiceImplTest`/`InventorySystemImplTest`, and `ScrappingApplicationTests` needs a live DB). Builds must use `bash ./mvnw clean package -DskipTests` (the Dockerfile does the same). Don't assume `test` is green.
- Focused verification: `bash ./mvnw test -Dtest='PdfServiceImplTest#precioNormal' -DfailIfNoTests=false`.
- Host JDK is 21; the project targets Java 17 and compiles fine as-is.

## Database & migrations

- App requires an already-initialized Postgres: `spring.datasource.url=jdbc:postgresql://localhost:5432/pasionaria` (user `postgres`, password `homito`), with `spring.jpa.hibernate.ddl-auto=validate`. The DB must be present or the app won't start.
- Schema lives in `src/main/resources/sql/<version>/` (currently `1.38`) as plain SQL. There is NO automatic migration runner (no Flyway/Liquibase, no `spring.sql.init`): scripts are applied by hand, bumping the version dir each time. When you change an entity you must also write SQL matching it, or startup validation fails. Next version is `1.39`. Some filenames contain spaces (e.g. `01 - schema.sql`).
- Local dev DB: `docker compose -f dev.docker-compose.yml up -d` exposes `6500:5432` — the app default is `5432`, so run with `-Dspring.datasource.url=jdbc:postgresql://localhost:6500/pasionaria` (or override via env `SPRING_DATASOURCE_URL`). The full `docker-compose.yml` uses prebuilt `front-parcial`/`back-parcial` images and its own DB service.

## Architecture / conventions

- Everything lives under package `distribuidora.scrapping` (`controller`, `services`, `repositories`, `entities`, `dto`, `security`, `util/converters`). Services are `Interface` + `*ServiceImpl` pairs; DTO<->entity mapping is hand-written converters in `util/converters`.
- Field injection (`@Autowired`) and Lombok throughout.
- Auth is JWT (`security/SpringSecurityConfig`): only `/customer/**` and `/login/**` are public; `/inventory-system/**`, `/order/**`, `/lookup/**` require the `ALL` authority; CORS allows `http://localhost:4200`.
- Distributors are a pluggable system: each distributor has a `ProductSearcher` implementation registered in `UpdaterServiceImpl` (Villares & Indias read uploaded Excel via `MultipartFile`; La Granja, Facundo, Don Gaspar scrape live sites via jsoup). To add a distributor: new `*Entidad` in `entities/productos/especificos`, a service extending `ProductSearcherExcel` or `ProductSearcherWeb`, an entry in `UpdaterServiceImpl`, and a `DatosDistribuidora` row + `LOOKUP` values. Distributor codes live in `configs/Constants.java`.
- `src/main/resources/static/*.html` and `*.xls` are site snapshots / sample import files used as parsing references.

## Gotchas

- A private SSH key (`homitowen`, `homitowen.pub`) is accidentally committed at the repo root. Don't commit new secrets or keys.
- Active dev branch is `development`; `main` is the production/stable branch.
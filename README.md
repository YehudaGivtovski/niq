# Personalized Data API

## Requirements
- Java 21
- Docker

## Setup PostgreSQL with Docker

1. **Pull PostgreSQL Image:**
    ```sh
    docker pull postgres:14.5
    ```

2. **Create Docker Volume:**
    ```sh
    docker volume create postgres_data
    ```

3. **Run PostgreSQL Container:**
    ```sh
    docker run --name postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 -v postgres_data:/var/lib/postgresql/data postgres:14.5
    ```

4. **Edit PostgreSQL Configuration:**

    - Update the following settings in `postgresql.conf`:

        ```plaintext
        # Memory settings
        shared_buffers = 2GB
        How much memory PostgreSQL will use for caching data.
        The default value is typically quite low, but for a production environment, it is recommended to set this to 25% of the system's total RAM.

        work_mem = 64MB
        Amount of memory to be used by internal sort operations and hash tables before writing to temporary disk files.
        This memory is allocated per operation, so complex queries might use multiple times this amount of memory.
        Increasing this value can speed up sorts, hash joins, and similar operations that do not fit into memory.

        effective_cache_size = 6GB
        How much memory is available for disk caching by the operating system and within the database itself - Does NOT reserve or allocate actual memory. It is purely an advisory setting.
        It provides an estimate to the query planner about the total memory available for caching by the operating system and PostgreSQL. This helps the planner in making informed decisions.
        It is usually recommended to set effective_cache_size to about 50%-75% of the total system memory.

        # Enable extensions
        shared_preload_libraries = 'pg_stat_statements'
        pg_stat_statements extension help to track execution statistics of all SQL statements executed by a server.
        Can help you identify slow queries that could benefit from caching or optimization.
        ```

## API Documentation
- Access the Swagger UI at: [http://localhost:8080/api/swagger-ui/index.html#/](http://localhost:8080/api/swagger-ui/index.html#/)

## Performance Enhancements
- **Indexing:** Added indexing for entities to speed up database queries.
- **Caching:** Used `@Cacheable` annotations to cache frequently accessed data.
- **Mapping:** Utilized `MapStruct` for efficient object mapping.

# Database Tables

## ProductEntity
| Field      | Type   | Key |
|------------|--------|-----|
| productId  | String | PK  |
| category   | String |     |
| brand      | String |     |

## ShopperShelfItemEntity
| Field           | Type   | Key |
|-----------------|--------|-----|
| id              | Long   | PK  |
| productId       | String | FK  |
| relevancyScore  | Double |     |
| shopperId       | String | FK  |

## ShopperEntity
| Field      | Type   | Key |
|------------|--------|-----|
| shopperId  | String | PK  |
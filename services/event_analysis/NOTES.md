# Coding Interview Strategy
> A strategy for approaching a backend coding interview (e.g. 3-hour take-home or live session).
> The goal is to always have something runnable and demonstrable at every stage, even if incomplete.

---

## Step 1 — Init the project
Go to https://start.spring.io/ and generate a Spring Boot project with:
- **Language**: Java
- **Build**: Gradle
- **Dependencies**: Spring Web, Spring Data JPA, Validation, Actuator, Lombok, H2 (test), PostgreSQL

---

## Step 2 — Add Makefile
Create a `Makefile` at the project root to simplify common commands:
```makefile
build:
    ./gradlew build -x test

test:
    ./gradlew cleanTest test

clean:
    ./gradlew clean

run:
    ./gradlew bootRun
```

---

## Step 3 — List the APIs
Define each API with its method, path, request name and response name before writing any code.
API:
| POST | `/events` | `CreateEventRequest` | `CreateEventResponse` |

DTO: CreateEventRequest/Response

Entity: Event


---

## Step 4 — Add packages (no code yet)
Create the package structure under `src/main/java/com/example/`:
```
controller/
service/
repository/
model/
dto/
```

---

## Step 5 — Add DTOs and model
Define all request/response records and the JPA entity before writing any logic.

**DTOs** — one record per request/response, use `@JsonProperty` for snake_case fields:
```java
public record CreateXxxRequest(
    @NotBlank @JsonProperty("field_name") String fieldName
) {}

public record CreateXxxResponse(
    @JsonProperty("id") UUID id
) {}
```

**Model** — JPA entity with `@Entity`, `@Table`, fields matching the DB schema:
```java
@Entity
@Table(name = "table_name")
public class Xxx {
    @Id
    private UUID id;
    // fields...
}
```

---

## Step 6 — Add controllers with dummy responses
Create one controller per API group. Return hardcoded dummy responses to verify routing works.

```java
@PostMapping
public ResponseEntity<CreateEventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
    return ResponseEntity.ok(new CreateEventResponse("dummy-id"));
}
```

---

## Step 7 — Run build and test
Verify the project compiles and the context loads:
```bash
make build
make test
```
Fix any compilation or context errors before moving on.

---

## Step 8 — Add service interface
Define what methods the service layer needs — no implementation yet:

```java
public interface EventService {
    CreateEventResponse createEvent(CreateEventRequest request);
    GetEventResponse getEvent(UUID eventId);
    EventSummaryResponse getSummary(EventSummaryQuery query);
    TopEventsResponse getTopEvents(TopEventsQuery query);
}
```

---

## Step 9 — Add service implementation with dummy returns
Implement the interface with hardcoded returns:

```java
@Service
public class EventServiceImpl implements EventService {
    @Override
    public CreateEventResponse createEvent(CreateEventRequest request) {
        return new CreateEventResponse("dummy-id");
    }
    // ...
}
```

---

## Step 10 — Wire service into controller
Inject the service via constructor and replace dummy controller logic:

```java
private final EventService eventService;

public EventController(EventService eventService) {
    this.eventService = eventService;
}
```

---

## Step 11 — Add model and repository
- Finalize the `Event` entity with proper JPA annotations
- Add a Spring Data JPA repository interface:
```java
public interface EventRepository extends JpaRepository<Event, UUID> { }
```

---

## Step 12 — Implement service with real logic
Replace dummy returns in `EventServiceImpl` with real DB calls via the repository.

---

## Step 13 — Add unit tests (if time allows)
Add controller tests using `MockMvcBuilders.standaloneSetup()` and service tests with Mockito.

```bash
make test
```

---

## Step 14 — Add error handling (if time allows)
Add a `@ControllerAdvice` to return proper HTTP status codes:
- `404` when event not found
- `400` for validation failures

---

## Step 15 — Deploy and manually check health
Deploy to the app platform and verify the health endpoint:
```
GET /actuator/health → { "status": "UP" }
```
Debug any issues in the deployed environment.

---

## Step 16 — Add integration tests (if time allows)
Add integration tests that run against the full Spring context and verify end-to-end API behavior. Run them on every code change.

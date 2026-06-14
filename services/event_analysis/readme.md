## Example 1 — Event Analytics Service (DO-style)

**Source:** ChatGPT-generated, modeled on DigitalOcean interview style
**Difficulty:** Medium — 4 endpoints, persistence required
**Best for:** Most realistic match to what DO will likely ask

### Background

Your team is building a lightweight event analytics platform used by customers to submit application events and retrieve usage statistics. Customers send events through a REST API. The platform stores those events and provides aggregated reporting.

### Endpoints

```
POST /events
Body: {
  "customer_id": "cust-123",
  "event_type": "login",
  "timestamp": "2026-06-12T10:00:00Z",
  "metadata": { "ip": "10.0.0.1" }   ← optional
}
Response: 201 — { "event_id": "uuid" }
```

```
GET /events/{event_id}
Response: 200 — { event_id, customer_id, event_type, timestamp, metadata }
Response: 404 — if not found
```

```
GET /summary?customer_id=cust-123&start_time=...&end_time=...
Response: 200 — {
  "customer_id": "cust-123",
  "total_events": 150,
  "event_breakdown": { "login": 100, "purchase": 25, "logout": 25 }
}
```

```
GET /top-events?limit=10
Response: 200 — {
  "results": [
    { "event_type": "login", "count": 120 },
    { "event_type": "purchase", "count": 50 }
  ]
}
```

```
GET /health
Response: 200 — { "status": "healthy" }
```

### Validation Rules
- `customer_id` required → 400
- `event_type` required → 400
- `timestamp` required, cannot be in the future → 400
- `metadata` optional

### Non-Functional Requirements
- Data must survive service restarts (real DB, not H2)
- Environment-based configuration (DB URL, port, log level)
- Structured logging: event received, validation failure, errors
- Automated tests: validation, API behavior, business logic
- Deploy to DigitalOcean, provide public URL

### Discussion Topics (for 30-min review)
- What did you prioritize? What did you defer?
- How would you scale to 1M events/day? 100M/day?
- How would you handle duplicate events?
- How would you monitor latency, error rates, ingestion volume?

---

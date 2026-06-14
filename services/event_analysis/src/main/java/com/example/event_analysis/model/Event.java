package com.example.event_analysis.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "events", 
    indexes = {
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_timestamp", columnList = "timestamp")
    }
)
public class Event {
    
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "type")
    private String type;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata")
    private Map<String, String> metadata;

    @SuppressWarnings("suppress_unused")
    protected Event() {}

    public Event(UUID event_id, UUID customerId, String type, LocalDate timestamp, Map<String, String> metadata) {
        this.id = event_id;
        this.customerId = customerId;
        this.type = type;
        this.timestamp = timestamp;
        this.metadata = metadata;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}

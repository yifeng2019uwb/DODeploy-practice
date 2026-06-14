package com.example.event_analysis.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetEventResponse(
    @JsonProperty("event_id")    UUID event_id,
    @JsonProperty("customer_id") UUID customer_id,
    @JsonProperty("event_type")  String event_type,
    @JsonProperty("timestamp")   LocalDate timestamp,
    @JsonProperty("metadata")    Map<String, String> metadata
) {}

package com.example.event_analysis.dto;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;


public record EventSummaryResponse(
    @JsonProperty("customer_id")     UUID customer_id,
    @JsonProperty("total_events")    int total_events,
    @JsonProperty("event_breakdown") Map<String, Integer> event_breakdown
) {}

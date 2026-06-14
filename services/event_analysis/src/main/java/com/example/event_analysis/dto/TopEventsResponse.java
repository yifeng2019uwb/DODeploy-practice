package com.example.event_analysis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TopEventsResponse(
    @JsonProperty("results") List<Item> results
) {
    public record Item(
        @JsonProperty("event_type") String event_type,
        @JsonProperty("count")      int count
    ) {}
}

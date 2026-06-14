package com.example.event_analysis.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record TopEventsQuery(
    @NotNull
    @JsonProperty("customer_id") 
    UUID customer_id,
    
    @JsonProperty("limit")
    int limit
) {}

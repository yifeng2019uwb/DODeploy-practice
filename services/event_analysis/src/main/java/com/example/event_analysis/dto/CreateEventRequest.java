package com.example.event_analysis.dto;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEventRequest(
        @NotNull
        @JsonProperty("customer_id")   UUID customer_id,
                
        @NotBlank
        @JsonProperty("event_type")    String event_type,
        
        @NotNull
        @JsonProperty("timestamp")     LocalDate timestamp,

        // @JsonProperty("meta_date")     MetaData meta_data
        @JsonProperty("metadata")      Map<String, String> metadata
)  {   
}


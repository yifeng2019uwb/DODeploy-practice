package com.example.event_analysis.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record EventSummaryQuery(

    @NotNull
    UUID customer_id,
    
    LocalDate start_time,
    LocalDate end_time
) {}

package com.example.event_analysis.service;

import java.util.UUID;

import org.jspecify.annotations.Nullable;

import com.example.event_analysis.dto.CreateEventRequest;
import com.example.event_analysis.dto.CreateEventResponse;
import com.example.event_analysis.dto.EventSummaryQuery;
import com.example.event_analysis.dto.EventSummaryResponse;
import com.example.event_analysis.dto.TopEventsResponse;
import com.example.event_analysis.dto.GetEventResponse;



public interface EventService {

    CreateEventResponse createEvent(CreateEventRequest request);

    GetEventResponse getEventById(UUID id);

    EventSummaryResponse getSummary(EventSummaryQuery query);

    TopEventsResponse getTopEvents(UUID customerId, int limit);
    
}

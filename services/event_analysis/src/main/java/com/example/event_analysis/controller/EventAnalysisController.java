package com.example.event_analysis.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_analysis.dto.CreateEventRequest;
import com.example.event_analysis.dto.CreateEventResponse;
import com.example.event_analysis.dto.EventSummaryQuery;
import com.example.event_analysis.dto.EventSummaryResponse;
import com.example.event_analysis.dto.GetEventResponse;
import com.example.event_analysis.dto.TopEventsQuery;
import com.example.event_analysis.dto.TopEventsResponse;
import com.example.event_analysis.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/events")
public class EventAnalysisController {

    private final EventService eventService;

    public EventAnalysisController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<CreateEventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        return  ResponseEntity.ok(eventService.createEvent(request));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetEventResponse> getEvent(@PathVariable UUID eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    // @GetMapping
    // public ResponseEntity<List<GetEventResponse>> getEventsByCustomer(@RequestParam String customer_id) {
    //     return ResponseEntity.ok(List.of());
    // }

    @GetMapping("/summary")
    public ResponseEntity<EventSummaryResponse> getSummary(@ModelAttribute EventSummaryQuery query) {
        return ResponseEntity.ok(eventService.getSummary(query));
    }

    @GetMapping("/top-events")
    public ResponseEntity<TopEventsResponse> getTopEvents(@ModelAttribute TopEventsQuery query) {
        return ResponseEntity.ok(eventService.getTopEvents(query.customer_id(), query.limit()));
    }
}

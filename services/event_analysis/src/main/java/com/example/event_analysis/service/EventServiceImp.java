package com.example.event_analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.event_analysis.Exception.ValidationException;
import com.example.event_analysis.dto.CreateEventRequest;
import com.example.event_analysis.dto.CreateEventResponse;
import com.example.event_analysis.dto.EventSummaryQuery;
import com.example.event_analysis.dto.EventSummaryResponse;
import com.example.event_analysis.dto.GetEventResponse;
import com.example.event_analysis.dto.TopEventsResponse;
import com.example.event_analysis.dto.TopEventsResponse.Item;
import com.example.event_analysis.model.Event;
import com.example.event_analysis.repository.EventDao;


@Service
public class EventServiceImp implements EventService{

    private final EventDao eventDao;

    public EventServiceImp(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public GetEventResponse getEventById(UUID id) {
        // TODO Auto-generated method stub
        try {
            Optional<Event> event = eventDao.findById(id);
            return event.map(e -> new GetEventResponse(
                e.getId(),
                e.getCustomerId(),
                e.getType(),
                e.getTimestamp(),
                e.getMetadata()
            )).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        } catch (Exception e) {
            System.out.println("getEventById error: " + e.getMessage());
            throw new ValidationException("Error occurred while fetching event");
        }
    }


    @Override
    public CreateEventResponse createEvent(CreateEventRequest request) {
        // TODO Auto-generated method stub
        try {
            Event event = new Event(UUID.randomUUID(), request.customer_id(), request.event_type(), request.timestamp(), request.metadata());
            Event savedEvent = eventDao.save(event);
            return new CreateEventResponse(savedEvent.getId());
        } catch (Exception e) {
            System.out.println("createEvent error: " + e.getMessage());
            throw new ValidationException("Error occurred while creating event");
        }

    }

    @Override
    public EventSummaryResponse getSummary(EventSummaryQuery query) {
        try {
            List<Event> events = eventDao.findEventsByCustomerIdAndTimestampBetween(query.customer_id(), query.start_time(), query.end_time());
            return new EventSummaryResponse(query.customer_id(), events.size(), getSummaryOfEvent(events));
        } catch (Exception e) {
            System.out.println("getSummary error: " + e.getMessage());

            throw new ValidationException("Error occurred while fetching events");
        }
    }

    @Override
    public TopEventsResponse getTopEvents(UUID customerId, int limit) {
        // TODO Auto-generated method stub
        try {
            List<Event> events = eventDao.findTopEventsByCustomerId(customerId, limit);
            return new TopEventsResponse(getItemsFromMap(getSummaryOfEvent(events)));
        } catch (Exception e) {
            System.out.println("getTopEvents error: " + e.getMessage());
            throw new ValidationException("Error occurred while fetching top events");
        }
    }

    private Map<String, Integer> getSummaryOfEvent(List<Event> events) {
        Map<String, Integer> map = new HashMap<>();
        for (Event event : events) {
            map.put(event.getType(), map.getOrDefault(event.getType(), 0) + 1);
        }
        return map;
    }

    private List<Item> getItemsFromMap(Map<String, Integer> summary) {
        return summary.entrySet().stream()
                .map(entry -> new Item(entry.getKey(), entry.getValue()))
                .toList();
    }

}

package com.example.event_analysis.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_analysis.model.Event;

@Repository
public interface EventDao extends JpaRepository<Event, UUID> {

    Optional<Event> findEventById(UUID id);

    List<Event> findEventsByCustomerId(UUID customerId);

    List<Event> findEventsByCustomerIdAndTimestampBetween(UUID customerId, LocalDate startDate, LocalDate endDate);

    List<Event> findTopEventsByCustomerId(UUID customerId, int limit);
    
}

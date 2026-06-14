package com.example.event_analysis.repository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.event_analysis.model.Event;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(MockitoExtension.class)
public class EventDaoTest {
    
    @Mock
    private EventDao eventDao;

    private static final UUID CUSTOMER_ID = UUID.randomUUID();

    private Event newEvent(UUID id, String type) {
        return new Event(id, CUSTOMER_ID, type, LocalDate.now(), Map.of());
    }

    @Test
    void findById_returnsEvent() {
        UUID id = UUID.randomUUID();
        Event event = newEvent(id, "test");
        when(eventDao.findById(id)).thenReturn(Optional.of(event));

        // assertThat(eventDao.findById(id)).isPresent();
        // verify(eventDao).findById(id);

        Optional<Event> result = eventDao.findById(id);
        assertThat(result).isPresent();
        assertEquals(id, result.get().getId());
        assertEquals("test", result.get().getType());
    }

}

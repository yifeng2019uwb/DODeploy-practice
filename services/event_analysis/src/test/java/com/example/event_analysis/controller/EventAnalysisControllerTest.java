package com.example.event_analysis.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.event_analysis.dto.CreateEventRequest;
import com.example.event_analysis.service.EventService;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventAnalysisControllerTest {

    private static final String PATH_URL = "/api/v1/events";

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        EventService eventService = Mockito.mock(EventService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new EventAnalysisController(eventService))
            .setValidator(new Validator() {
                @Override public boolean supports(Class<?> clazz) { return true; }
                @Override public void validate(Object target, Errors errors) { }
            })
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void create_event_succeed() throws Exception {
        UUID customer_id = UUID.randomUUID();
        CreateEventRequest request = new CreateEventRequest(
            customer_id, "log", LocalDate.now(), new HashMap<>());

        mockMvc.perform(post(PATH_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

    }
}

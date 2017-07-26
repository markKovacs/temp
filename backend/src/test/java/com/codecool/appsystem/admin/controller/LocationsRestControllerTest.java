package com.codecool.appsystem.admin.controller;


import com.codecool.MockData;
import com.codecool.appsystem.admin.model.Location;
import com.codecool.appsystem.admin.repository.LocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocationsRestControllerTest extends MockData {

    @Autowired
    MockMvc mockMvc;

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    LocationsRestController locationsRestController;

    List<Location> mockLocations = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationsRestController).build();

        mockLocations.add(location);

        Mockito.when(locationRepository.findAll()).thenReturn(mockLocations);
    }


    @Test
    public void getAllLocations() throws Exception {
        Mockito.when(locationRepository.findAll()).thenReturn(mockLocations);

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("BUD")))
                .andExpect(jsonPath("$[0].name", is("Budapest")));

        verify(locationRepository, times(1)).findAll();
        verifyNoMoreInteractions(locationRepository);
    }


}
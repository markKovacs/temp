package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.service.ApplicationScreeningService;
import com.codecool.appsystem.admin.service.MotivationsByLocationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DashboardRestControllerTest {

    @Mock
    private MotivationsByLocationService userMotivationService;

    @Mock
    private ApplicationScreeningService screeningService;

    @InjectMocks
    private DashboardRestController dashboardRestController;

    @Autowired
    private MockMvc mockMvc;

    private List<MotivationDTO> mockMotivationDTOs = new ArrayList<>();
    private List<ScreeningDTO> mockScreeningDTOs = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardRestController).build();

        MotivationDTO motivationDTO = new MotivationDTO();
        motivationDTO.setAdminId(100);
        motivationDTO.setIsVideo(true);
        motivationDTO.setName("Motivation");
        this.mockMotivationDTOs.add(motivationDTO);

        ScreeningDTO screeningDTO = new ScreeningDTO();
        screeningDTO.setAdminId(100);
        screeningDTO.setAge(20);
        screeningDTO.setGender("Female");
        screeningDTO.setName("Titania");
        this.mockScreeningDTOs.add(screeningDTO);
    }

    @Test
    public void applicantsByLocation() throws Exception {
        when(userMotivationService.applicantsByLocation("BUD")).thenReturn(mockMotivationDTOs);

        mockMvc.perform(get("/api/dashboard/motivation?location=BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Motivation")))
                .andExpect(jsonPath("$[0].adminId", is(100)))
                .andExpect(jsonPath("$[0].isVideo", is(true)));

        verify(userMotivationService, times(1)).applicantsByLocation("BUD");
        verifyNoMoreInteractions(userMotivationService);


    }

    @Test
    public void scheduledScreenings() throws Exception {
        when(screeningService.find("BUD")).thenReturn(mockScreeningDTOs);

        mockMvc.perform(get("/api/dashboard/screening?location=BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Titania")))
                .andExpect(jsonPath("$[0].adminId", is(100)))
                .andExpect(jsonPath("$[0].age", is(20)));

        verify(screeningService, times(1)).find("BUD");
        verifyNoMoreInteractions(screeningService);


    }

}
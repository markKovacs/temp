package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.service.ApplicationScreeningService;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScreeningControllerTest {

    @Mock
    private ApplicationScreeningService screeningService;

    @InjectMocks
    private ScreeningController screeningController;

    private MockMvc mockMvc;
    private Date mockDate = new Date();
    private ScreeningDTO mockScreeningDTO = new ScreeningDTO();
    private List<ScreeningDTO> mockScreeningDTOs = new ArrayList<>();
    private RestResponseDTO mockRestResponseDTO = RestResponseDTO.buildSuccess();
    private List<ScreeningTimeAssingmentDTO> mockScreeningTimeAssingmentDTOs = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(screeningController).build();

        mockDate.getTime();

        mockScreeningDTO.setName("Elek");
        mockScreeningDTO.setGender("Male");
        mockScreeningDTO.setAge(20);
        mockScreeningDTO.setAdminId(100);
        mockScreeningDTOs.add(mockScreeningDTO);

        ScreeningTimeAssingmentDTO mockScreeningTimeAssingmentDTO = new ScreeningTimeAssingmentDTO();
        mockScreeningTimeAssingmentDTO.setId(1);
        mockScreeningTimeAssingmentDTO.setTime(new Date());
        mockScreeningTimeAssingmentDTOs.add(mockScreeningTimeAssingmentDTO);
    }

    @Test
    public void getScreeningInfo() throws Exception {
        when(screeningService.find("BUD", false)).thenReturn(mockScreeningDTOs);

        mockMvc.perform(get("/api/screening/list?location=BUD&signedback=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Elek")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[0].adminId", is(100)));

        verify(screeningService, times(1)).find("BUD", false);
        verifyNoMoreInteractions(screeningService);
    }

    @Test
    public void getScreeningCandidates() throws Exception {
        when(screeningService.getCandidates("BUD")).thenReturn(mockScreeningDTOs);

        mockMvc.perform(get("/api/screening/candidates?location=BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Elek")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[0].adminId", is(100)));

        verify(screeningService, times(1)).getCandidates("BUD");
        verifyNoMoreInteractions(screeningService);

    }

    @Test
    public void saveGroupTimes() throws Exception {

        RestResponseDTO result = screeningController.saveGroupTimes(mockScreeningTimeAssingmentDTOs);

        verify(screeningService, times(1)).saveGroupScreeningTime(mockScreeningTimeAssingmentDTOs);
        verifyNoMoreInteractions(screeningService);

        assertEquals(mockRestResponseDTO,result);
    }

    @Test
    public void savePersonalTimes() throws Exception {

        RestResponseDTO result = screeningController.savePersonalTimes(mockScreeningTimeAssingmentDTOs);

        verify(screeningService, times(1)).savePersonalScreeningTime(mockScreeningTimeAssingmentDTOs);
        verifyNoMoreInteractions(screeningService);

        assertEquals(mockRestResponseDTO,result);

    }

}
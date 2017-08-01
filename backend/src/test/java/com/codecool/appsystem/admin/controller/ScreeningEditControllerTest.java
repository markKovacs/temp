package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningStepCriteria;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScreeningEditControllerTest {

    @Mock
    private ScreeningEditService screeningEditService;

    @InjectMocks
    private ScreeningEditController screeningEditController;

    private MockMvc mockMvc;

    private ScreeningStep mockScreeningStep = new ScreeningStep();
    private List<ScreeningStep> mockScreeningSteps = new ArrayList<>();
    private ScreeningStepEvaluationDTO mockScreeningStepEvaluationDTO = new ScreeningStepEvaluationDTO();
    private RestResponseDTO mockRestResponseDTO = RestResponseDTO.buildSuccess();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(screeningEditController).build();

        mockScreeningStep.setEnabled(true);
        mockScreeningStep.setId("1");
        mockScreeningStep.setLocationId("BUD");
        mockScreeningStep.setName("name");
        mockScreeningStep.setCriterias(Arrays.asList(new ScreeningStepCriteria()));
        mockScreeningSteps.add(mockScreeningStep);

        mockScreeningStepEvaluationDTO.setAge(20);
        mockScreeningStepEvaluationDTO.setName("name");
        mockScreeningStepEvaluationDTO.setScreeningStep(new ApplicantsScreeningStep());
    }

    @Test
    public void findByLocation() throws Exception {
        when(screeningEditService.findByLocationId("BUD")).thenReturn(mockScreeningSteps);

        mockMvc.perform(get("/api/editscreening?location=BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].enabled", is(true)));

        verify(screeningEditService, times(1)).findByLocationId("BUD");
        verifyNoMoreInteractions(screeningEditService);

    }

    @Test
    public void saveScreening() throws Exception {

        RestResponseDTO result = screeningEditController.saveScreening(mockScreeningSteps);

        verify(screeningEditService, times(1)).saveScreening(mockScreeningSteps);
        verifyNoMoreInteractions(screeningEditService);

        assertEquals(mockRestResponseDTO, result);

    }

}
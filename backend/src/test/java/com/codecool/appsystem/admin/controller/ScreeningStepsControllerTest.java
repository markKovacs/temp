package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningGrade;
import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningStepCriteria;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import com.codecool.appsystem.admin.service.ScreeningEvalService;
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

public class ScreeningStepsControllerTest {
    @Mock
    private ScreeningEditService screeningEditService;

    @Mock
    private ScreeningEvalService screeningEvalService;

    @InjectMocks
    private ScreeningStepsController screeningStepsController;

    private MockMvc mockMvc;

    private ScreeningStep mockScreeningStep = new ScreeningStep();
    private ScreeningGrade mockScreeningGrade = new ScreeningGrade();
    private ApplicantsScreeningStep mockApplicantsScreeningStep = new ApplicantsScreeningStep();
    private List<ScreeningStep> mockScreeningSteps = new ArrayList<>();
    private ScreeningStepEvaluationDTO mockScreeningStepEvaluationDTO = new ScreeningStepEvaluationDTO();
    private RestResponseDTO mockRestResponseDTO = RestResponseDTO.buildSuccess();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(screeningStepsController).build();

        mockScreeningGrade.setAccepted(true);
        mockScreeningGrade.setAdminId(100);

        mockApplicantsScreeningStep.setApplicationId("email@gmail.com");
        mockApplicantsScreeningStep.setId("2");
        mockApplicantsScreeningStep.setPoints(2);


        mockScreeningStep.setEnabled(true);
        mockScreeningStep.setId("2");
        mockScreeningStep.setLocationId("MSC");
        mockScreeningStep.setName("name2");
        mockScreeningStep.setCriterias(Arrays.asList(new ScreeningStepCriteria()));
        mockScreeningSteps.add(mockScreeningStep);

        mockScreeningStepEvaluationDTO.setAge(20);
        mockScreeningStepEvaluationDTO.setName("name2");
        mockScreeningStepEvaluationDTO.setScreeningStep(mockApplicantsScreeningStep);
    }
    @Test
    public void findByLocationId() throws Exception {
        when(screeningEditService.findByLocationId("MSC")).thenReturn(mockScreeningSteps);

        mockMvc.perform(get("/api/screeningsteps?location=MSC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("name2")))
                .andExpect(jsonPath("$[0].id", is("2")))
                .andExpect(jsonPath("$[0].enabled", is(true)));

        verify(screeningEditService, times(1)).findByLocationId("MSC");
        verifyNoMoreInteractions(screeningEditService);
    }

    @Test
    public void findForApplicant() throws Exception {
        when(screeningEditService.findForApplicant(100, "1")).thenReturn(mockScreeningStepEvaluationDTO);

        mockMvc.perform(get("/api/evalscreening/100?step=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name", is("name2")))
                .andExpect(jsonPath("$.age", is(20)));

        verify(screeningEditService, times(1)).findForApplicant(100, "1");
        verifyNoMoreInteractions(screeningEditService);
    }

    @Test
    public void saveEvaluation() throws Exception {

        RestResponseDTO result = screeningStepsController.saveEvaluation(mockApplicantsScreeningStep);

        verify(screeningEditService, times(1)).saveEvaluation(mockApplicantsScreeningStep);
        verifyNoMoreInteractions(screeningEditService);

        assertEquals(mockRestResponseDTO, result);
    }

    @Test
    public void applicantsByLocation() throws Exception {

        RestResponseDTO result = screeningStepsController.applicantsByLocation(mockScreeningGrade);

        verify(screeningEvalService, times(1)).gradeScreening(mockScreeningGrade);
        verifyNoMoreInteractions(screeningEvalService);

        assertEquals(mockRestResponseDTO, result);
    }

}
package com.codecool.appsystem.admin.controller;


import com.codecool.MockData;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.service.ApplicantListingService;
import com.codecool.appsystem.admin.service.applicantDetails.ApplicantDetailsService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicantsControllerTest extends MockData{

    @Mock
    ApplicantListingService applicantListingService;

    @Mock
    ApplicantDetailsService applicantDetailsService;

    private List<ApplicantInfoDTO> mockApplicantInfosDTO = new ArrayList<>();
    private ApplicantDetailsDTO mockApplicantDetailsDTO;
    private ApplicantInfoDTO applicantInfoDTO;

    @InjectMocks
    ApplicantsController applicantsController;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(applicantsController).build();
        this.applicantInfoDTO = new ApplicantInfoDTO("toth",100, "BUD", "English", 1L, false, new Date(), "email@gmail.com");

        this.mockApplicantInfosDTO.add(applicantInfoDTO);

        this.mockApplicantDetailsDTO = new ApplicantDetailsDTO();
        mockApplicantDetailsDTO.setAdminId(100);
        mockApplicantDetailsDTO.setDateOfBirth(2000);
        mockApplicantDetailsDTO.setFamilyName("Toth");
        mockApplicantDetailsDTO.setGivenName("Akos");
        mockApplicantDetailsDTO.setLocation("BUD");
    }

    @Test
    public void getAllApplicants() throws Exception{
        when(applicantListingService.getApplicationData("BUD")).thenReturn(mockApplicantInfosDTO);

        mockMvc.perform(get("/api/applicants?location=BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("toth")))
                .andExpect(jsonPath("$[0].adminId", is(100)));

        verify(applicantListingService, times(1)).getApplicationData("BUD");
        verifyNoMoreInteractions(applicantListingService);

    }

    @Test
    public void getSingleApplicantInfo() throws Exception{
        when(applicantDetailsService.provideInfo(100)).thenReturn(mockApplicantDetailsDTO);

        mockMvc.perform(get("/api/applicants/100"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.adminId", is(100)))
                .andExpect(jsonPath("$.familyName", is("Toth")))
                .andExpect(jsonPath("$.givenName", is("Akos")))
                .andExpect(jsonPath("$.location", is("BUD")));

        verify(applicantDetailsService, times(1)).provideInfo(100);
        verifyNoMoreInteractions(applicantDetailsService);

    }
}
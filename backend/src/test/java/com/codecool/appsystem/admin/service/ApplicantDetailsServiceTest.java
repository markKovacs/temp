package com.codecool.appsystem.admin.service;


import com.codecool.MockData;
import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.TestResultDTO;
import com.codecool.appsystem.admin.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicantDetailsServiceTest extends MockData {

    @Mock
    private UserRepository userRepo;
    @Mock
    private ApplicationRepository applicationRepo;
    @Mock
    private ApplicantsScreeningStepRepository applicantsScreeningStepRepository;
    @Mock
    private ApplicationScreeningInfoRepository appScrInfoRepo;
    @Mock
    private TestResultRepository testResultRepository;
    @Mock
    private TestRepository testRepository;


    @InjectMocks
    ApplicantDetailsService applicantDetailsService;

    private List<ApplicantsScreeningStep> mockApplicantsScreeningSteps = new ArrayList<>();
    private ApplicantsScreeningStep mockApplicantsScreeningStep = new ApplicantsScreeningStep();
    private ApplicationScreeningInfo mockApplicationScreeningInfo = new ApplicationScreeningInfo();
    private ApplicantDetailsDTO mockApplicantDetailsDTO = ApplicantDetailsDTO.builder().build();



    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        mockApplicantsScreeningStep.setPoints(100);
        mockApplicantsScreeningStep.setId("1");
        mockApplicantsScreeningStep.setApplication(application);
        mockApplicantsScreeningStep.setInterviewer("interviewer");

        mockApplicantsScreeningSteps.add(mockApplicantsScreeningStep);
        TestResultDTO mockTestResultDTO = TestResultDTO.builder().build();
        mockTestResultDTO.setId("0");
        mockTestResultDTO.setPassed(true);
        mockTestResultDTO.setPercent(100d);
        mockTestResultDTO.setPercent(100d);
        mockTestResultDTO.setName("Test Mock");
        mockTestResultDTO.setIsPending(false);
        mockTestResultDTO.setAnswer("bla blabla");
        mockTestResultDTO.setIsMotivation(true);

        mockApplicantDetailsDTO.setId(1);
        mockApplicantDetailsDTO.setDateOfBirth(1991);
        mockApplicantDetailsDTO.setGivenName("Doe");
        mockApplicantDetailsDTO.setFamilyName("John");
        mockApplicantDetailsDTO.setTimesApplied(0);

        //mockApplicantDetailsDTO.setScreeningSteps(mockApplicantsScreeningSteps);


        mockApplicationScreeningInfo.setApplication(application);
        mockApplicationScreeningInfo.setId("1");
        mockApplicationScreeningInfo.setMapLocation("map");

    }

    @Test
    public void provideInfo(){

        when(userRepo.findOne(user.getId())).thenReturn(user);

        when(applicantsScreeningStepRepository.findByApplicationId(application.getId())).thenReturn(mockApplicantsScreeningSteps);

        when(testResultRepository.findByApplicationId(application.getId())).thenReturn(Arrays.asList(testResult));

        ApplicantDetailsDTO result = applicantDetailsService.provideInfo(1);

        verify(userRepo, times(1)).findOne(user.getId());
        verifyNoMoreInteractions(userRepo);

        assertEquals(mockApplicantDetailsDTO,result);

    }



}
package com.codecool.appsystem.admin.service;


import com.codecool.MockData;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class ApplicantListingServiceTest extends MockData {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private TestResultRepository testResultRepo;

    @InjectMocks
    private ApplicantListingService applicantListingService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnApplicant(){
        List<TestResult> fourTests = Arrays.asList(testResult,testResult1,testResult2);

        Mockito.when(userRepository.findByLocationId(location.getId())).thenReturn(Arrays.asList(user));
        Mockito.when(applicationRepository.findByApplicantIdAndActive(user.getId(),Boolean.TRUE)).thenReturn(application);
        Mockito.when(applicationRepository.countByApplicantId(user.getId())).thenReturn(1L);
        Mockito.when(testResultRepo.findByApplicationId(application.getId())).thenReturn(fourTests);

        List<ApplicantInfoDTO> applInfo = applicantListingService.getApplicationData(location.getId(), null);

        Assert.assertEquals(1,applInfo.size());
    }
}
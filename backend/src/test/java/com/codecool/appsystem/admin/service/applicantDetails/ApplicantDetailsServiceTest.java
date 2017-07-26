package com.codecool.appsystem.admin.service.applicantDetails;


import com.codecool.MockData;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ApplicantDetailsServiceTest extends MockData {

    @Mock
    private UserRepository userRepo;
    @Mock
    private ApplicationRepository applicationRepo;
    @Mock
    private ApplicationScreeningInfoRepository appScrInfoRepo;
    @Mock
    private TestResultRepository testResRepository;
    @Mock
    private TestRepository testRepo;

    @InjectMocks
    ApplicantDetailsService applicantDetailsService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnApplicant(){
        List<TestResult> testResults = new ArrayList<>();
        testResults.add(testResult);
        testResults.add(testResult1);
        testResults.add(testResult2);
        testResults.add(testResult3);

        Mockito.when(userRepo.findByAdminId(user.getAdminId())).thenReturn(user);
        Mockito.when(applicationRepo.findByApplicantIdAndActive(user.getId(),true)).thenReturn(application);
        Mockito.when(appScrInfoRepo.findByApplicationId(application.getId())).thenReturn(applicationScreeningInfo);
        Mockito.when(applicationRepo.countByApplicantId(user.getId())).thenReturn((long) 1);

        Mockito.when(testResRepository.findByApplicationId(application.getId())).thenReturn(testResults);

        Mockito.when(testRepo.findOne(testResult2.getTestId()).getName()).thenReturn("asdasd");

        ApplicantDetailsDTO appDTO = applicantDetailsService.provideInfo(user.getAdminId());

        Assert.assertNotNull(appDTO);
    }

}
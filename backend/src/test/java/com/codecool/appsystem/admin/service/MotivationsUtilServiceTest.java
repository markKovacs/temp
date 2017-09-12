package com.codecool.appsystem.admin.service;

import com.codecool.MockData;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class MotivationsUtilServiceTest extends MockData {

    @Mock
    TestRepository testRepository;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    TestResultRepository testResultRepository;

    @InjectMocks
    MotivationsUtilService motivationsUtilService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void ungradedUsersTest() {
        List<TestResult> fourTests = Arrays.asList(testResult,testResult1,testResult2,testResult3);

        when(testRepository.findByMotivationVideoAndLocationId(true,location.getId())).thenReturn(test2);
        when(applicationRepository.findByApplicantIdAndActiveIsTrue(user.getId())).thenReturn(application);
        when(testResultRepository.findByApplicationId(application.getId())).thenReturn(fourTests);

        List<MotivationDTO> dtoList = motivationsUtilService.getUngradedUsers(Collections.singletonList(user),location.getId());

        System.out.println(dtoList);

        Assert.assertEquals(1,dtoList.size());
    }

    @Test
    public void noUngradedVideoTest() {
        testResult3.setPassed(true);
        List<TestResult> fourTests = Arrays.asList(testResult,testResult1,testResult2,testResult3);

        when(testRepository.findByMotivationVideoAndLocationId(true,location.getId())).thenReturn(test2);
        when(applicationRepository.findByApplicantIdAndActiveIsTrue(user.getId())).thenReturn(application);
        when(testResultRepository.findByApplicationId(application.getId())).thenReturn(fourTests);

        List<MotivationDTO> dtoList = motivationsUtilService.getUngradedUsers(Collections.singletonList(user),location.getId());
        Assert.assertEquals(0,dtoList.size());
    }
}
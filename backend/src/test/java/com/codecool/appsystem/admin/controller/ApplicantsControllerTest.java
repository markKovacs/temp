package com.codecool.appsystem.admin.controller;


import com.codecool.MockData;
import com.codecool.appsystem.admin.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicantsControllerTest extends MockData{

    @Mock
    UserRepository userRepository;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    private ApplicationScreeningInfoRepository appScrInfoRepo;

    @Mock
    private TestResultRepository testResRepository;

    @Mock
    TestRepository testRepository;

    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    ApplicantsController applicantsController;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

//        Mockito.when(userRepository.findByAdminId(1)).thenReturn(user);
//        Mockito.when(applicationRepository.findByApplicantIdAndActive(user.getId(),true)).thenReturn(application);
//        Mockito.when(appScrInfoRepo.findByApplicationId("1")).thenReturn(applicationScreeningInfo);
//        Mockito.when(testResRepository.findByApplicationIdAndPassed(application.getId(), true)).thenReturn(Arrays.asList(testResult));
//        Mockito.when(testRepository.findByLocationId("BUD")).thenReturn(Arrays.asList(test));

    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("api/applicants?location=BUD")).andExpect(status().is2xxSuccessful());
    }
}
package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.Question;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.model.dto.TestDTO;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private QuestionController questionController;

    @Autowired
    private MockMvc mockMvc;

    private RestResponseDTO mockRestResponseDTO = RestResponseDTO.buildSuccess();
    private List<TestDTO> mockTestDTOs = new ArrayList<>();
    private Question mockQuestion = new Question();
    private String mockRequestQuestionBody;
    private com.codecool.appsystem.admin.model.Test mockTest;
    private List<com.codecool.appsystem.admin.model.Test> mockTests = new ArrayList<>();
    private List<Question> mockQuestions = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();

        TestDTO mockTestDTO = new TestDTO();
        mockTestDTO.setDescription("description");
        mockTestDTO.setEnabled(true);
        mockTestDTO.setEstimatedTime(10);
        mockTestDTO.setId("1");
        mockTestDTO.setLocationId("BUD");
        mockTestDTO.setMotivationVideo(false);
        mockTestDTO.setName("Test");
        mockTestDTO.setOrderInBundle(1);
        this.mockTestDTOs.add(mockTestDTO);

        this.mockQuestion.setDescription("description");
        this.mockQuestion.setEnabled(true);
        this.mockQuestion.setEstimatedTime(10);
        this.mockQuestion.setId("1");
        this.mockQuestion.setLocationId("BUD");
        this.mockQuestion.setMotivationVideo(false);
        this.mockQuestion.setName("Test");
        this.mockQuestion.setOrderInBundle(1);

        mockTest = new com.codecool.appsystem.admin.model.Test();
        mockTest.setDescription("description");
        mockTest.setEnabled(true);
        mockTest.setEstimatedTime(10);
        mockTest.setId("1");
        mockTest.setLocationId("BUD");
        mockTest.setMotivationVideo(false);
        mockTest.setName("Test");
        mockTest.setOrderInBundle(1);
        mockTests.add(mockTest);

        ObjectMapper mapper = new ObjectMapper();
        mockRequestQuestionBody = mapper.writeValueAsString(mockQuestion);

        mockQuestions.add(mockQuestion);
    }

    @Test
    public void applicantsByLocation() throws Exception {

        RestResponseDTO result = questionController.applicantsByLocation(mockQuestion);

        verify(questionService, times(1)).saveCorrectAnswers(mockQuestion);
        verify(questionService, times(1)).saveTest(mockQuestion);
        verifyNoMoreInteractions(questionService);

        assertEquals(mockRestResponseDTO,result);

    }

    @Test
    public void getQuestionByLocation() throws Exception {
        when(testRepository.findByLocationIdOrderByOrderInBundleAsc("BUD")).thenReturn(mockTests);
        when(questionService.createQuestionsFromLocationId(mockTests)).thenReturn(mockQuestions);
        when(questionService.createQuestionDTO(mockTest, mockQuestion)).thenReturn(mockTestDTOs.get(0));

        mockMvc.perform(get("/api/question/BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].locationId", is("BUD")));

        verify(testRepository, times(1)).findByLocationIdOrderByOrderInBundleAsc("BUD");
        verifyNoMoreInteractions(testRepository);

        verify(questionService, times(1)).createQuestionsFromLocationId(mockTests);
        verify(questionService, times(mockTests.size())).createQuestionDTO(mockTest, mockQuestion);
        verifyNoMoreInteractions(questionService);

    }


}
package com.codecool.appsystem.admin.service;


import com.codecool.MockData;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class QuestionServiceTest extends MockData {

    @InjectMocks
    QuestionService questionService;

    @Mock
    TestRepository testRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() throws JsonProcessingException {
        when(testRepository.save(test2)).thenReturn(test2);

        questionService.saveTest(question);

        ArgumentCaptor<com.codecool.appsystem.admin.model.Test> testArg = ArgumentCaptor.forClass(com.codecool.appsystem.admin.model.Test.class);
        verify(testRepository, times(1)).save(testArg.capture());
        verifyNoMoreInteractions(testRepository);
    }

}
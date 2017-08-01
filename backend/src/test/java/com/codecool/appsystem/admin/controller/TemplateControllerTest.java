package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.EmailTemplate;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.repository.EmailTemplateRepository;
import com.codecool.appsystem.admin.service.TemplateService;
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
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TemplateControllerTest {

    @Mock
    private EmailTemplateRepository emailTemplateRepository;

    @Mock
    private TemplateService templateService;

    @InjectMocks
    private TemplateController templateController;

    private MockMvc mockMvc;
    private EmailTemplate mockEmailTemplate = new EmailTemplate();
    private List<EmailTemplate> mockEmailTemplates = new ArrayList<>();

    private RestResponseDTO mockRestResponseDTO = RestResponseDTO.buildSuccess();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();

        mockEmailTemplate.setTemplate("template");
        mockEmailTemplate.setModel("model");
        mockEmailTemplate.setId("1");
        mockEmailTemplate.setLocationId("BUD");
        mockEmailTemplate.setMaster(false);
        mockEmailTemplate.setName("name");
        mockEmailTemplates.add(mockEmailTemplate);
    }

    @Test
    public void getAllLocations() throws Exception {

        when(emailTemplateRepository.findByLocationId("BUD")).thenReturn(mockEmailTemplates);

        mockMvc.perform(get("/api/templates?location=BUD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].master", is(false)));

        verify(emailTemplateRepository, times(1)).findByLocationId("BUD");
        verifyNoMoreInteractions(emailTemplateRepository);
    }

    @Test
    public void getAllLocations1() throws Exception {

        RestResponseDTO result = templateController.getAllLocations(mockEmailTemplate);

        verify(templateService, times(1)).modifyTemplate(mockEmailTemplate);
        verifyNoMoreInteractions(templateService);

        assertEquals(mockRestResponseDTO,result);
    }

}
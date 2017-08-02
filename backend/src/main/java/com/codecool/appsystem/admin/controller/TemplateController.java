package com.codecool.appsystem.admin.controller;


import com.codecool.appsystem.admin.model.EmailTemplate;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.repository.EmailTemplateRepository;
import com.codecool.appsystem.admin.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class TemplateController {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value="/templates", method = RequestMethod.GET)
    public List<EmailTemplate> getAllLocations(@RequestParam("location") String locationId) {
        return emailTemplateRepository.findByLocationIdOrderByMasterDesc(locationId);
    }

    @RequestMapping(value="/templates/save", method = RequestMethod.POST)
    public RestResponseDTO getAllLocations(@RequestBody EmailTemplate emailTemplate) {

        templateService.modifyTemplate(emailTemplate);
        return RestResponseDTO.buildSuccess();
    }




}

package com.codecool.appsystem.admin.controller;


import com.codecool.appsystem.admin.model.EmailTemplate;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.repository.EmailTemplateRepository;
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

    @RequestMapping(value="/templates", method = RequestMethod.GET)
    public List<EmailTemplate> getAllLocations(@RequestParam("location") String locationId) {
        return emailTemplateRepository.findByLocationId(locationId);
    }

    @RequestMapping(value="/templates/save", method = RequestMethod.POST)
    public RestResponseDTO getAllLocations(@RequestBody EmailTemplate emailTemplate) {

        EmailTemplate emailTemplate1 = emailTemplateRepository.findOne(emailTemplate.getId());
        emailTemplate.setModel(emailTemplate.getModel());
        emailTemplate.setTemplate(emailTemplate.getTemplate());
        emailTemplateRepository.save(emailTemplate);
        return RestResponseDTO.buildSuccess();
    }




}
